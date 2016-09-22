package com.ai.slp.order.service.business.impl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.opt.sdk.dubbo.util.HttpClientUtil;
import com.ai.opt.sdk.util.BeanUtils;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.util.DateUtil;
import com.ai.slp.order.api.aftersaleorder.impl.OrderAfterSaleSVImpl;
import com.ai.slp.order.api.aftersaleorder.param.OrderReturnRequest;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.constants.OrdersConstants.OrdOdStateChg;
import com.ai.slp.order.dao.mapper.bo.OrdBalacneIf;
import com.ai.slp.order.dao.mapper.bo.OrdOdFeeTotal;
import com.ai.slp.order.dao.mapper.bo.OrdOdProd;
import com.ai.slp.order.dao.mapper.bo.OrdOdProdCriteria;
import com.ai.slp.order.dao.mapper.bo.OrdOdProdCriteria.Criteria;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.service.atom.interfaces.IOrdBalacneIfAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdFeeTotalAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdProdAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.ai.slp.order.service.business.interfaces.IOrderAfterSaleBusiSV;
import com.ai.slp.order.service.business.interfaces.IOrderFrameCoreSV;
import com.ai.slp.order.util.CommonCheckUtils;
import com.ai.slp.order.util.SequenceUtil;
import com.ai.slp.order.vo.OFCAfterSaleOrderCreateRequest;
import com.ai.slp.order.vo.OrderAfterSaleApplyItemsVo;
import com.ai.slp.order.vo.OrderAfterSaleApplyVo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Service
@Transactional
public class OrderAfterSaleBusiSVImpl implements IOrderAfterSaleBusiSV {
	
	private static final Logger logger=LoggerFactory.getLogger(OrderAfterSaleSVImpl.class);
	
	@Autowired
	private IOrdOrderAtomSV ordOrderAtomSV;
	
	@Autowired
	private IOrdOdProdAtomSV ordOdProdAtomSV;
	
	@Autowired
	private IOrderFrameCoreSV orderFrameCoreSV;
	
	@Autowired
	private IOrdOdFeeTotalAtomSV ordOdFeeTotalAtomSV;
	
	@Autowired
	private IOrdBalacneIfAtomSV ordBalacneIfAtomSV;

	@Override
	public void back(OrderReturnRequest request) throws BusinessException, SystemException {
		/* 1.参数校验及返回子订单下的商品信息*/
		List<OrdOdProd> ordOdProds = this.checkParams(request);
		//检验商品数量
		long prodSum = request.getProdSum();
		if(prodSum==0) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "退货商品数量不能为空");
		}
		/* 2.查询该商品的子订单*/
		OrdOrder order = this.getOrdOrder(request);
		/* 3.商品明细信息查询*/
		OrdOdProd ordOdProd = this.getOrdOdProd(request);
		if(!ordOdProds.contains(ordOdProd)) {
			throw new BusinessException("", 
					"商品与订单内容不一致,[订单id:"+request.getOrderId()+""
							+ ",商品明细id:"+request.getProdDetalId()+"]");
		}
		if(prodSum>ordOdProd.getBuySum()) {
			logger.error("退货的商品数量不能大于所购买的商品数量");
			throw new BusinessException("", 
					"退货的商品数量不能大于所购买的商品数量");
		}
		/* 4.生成退货订单*/
		OrdOrder backOrder=new OrdOrder();
		BeanUtils.copyProperties(backOrder, order);
		long backOrderId=SequenceUtil.createOrderId();
		Timestamp sysDate = DateUtil.getSysDate();
		backOrder.setBusiCode(OrdersConstants.OrdOrder.BusiCode.UNSUBSCRIBE_ORDER); //退货单
		backOrder.setOrderId(backOrderId); //退货单id
		backOrder.setOperId(request.getOperId());//受理工号 
		backOrder.setCusServiceFlag(OrdersConstants.OrdOrder.cusServiceFlag.YES);//是否售后标识
		backOrder.setOrigOrderId(order.getOrderId());
		this.insertOrderState(sysDate, backOrder);
		//更新商品为售后标识
		ordOdProd.setCusServiceFlag(OrdersConstants.OrdOrder.cusServiceFlag.YES);
		ordOdProdAtomSV.updateById(ordOdProd); 
		/* 5.生成退货商品明细信息*/
		OrdOdProd backOrdOdProd =new OrdOdProd();
		BeanUtils.copyProperties(backOrdOdProd, ordOdProd);
		long backTotalFee=backOrdOdProd.getSalePrice()*prodSum;
		backOrdOdProd.setBuySum(prodSum);
		backOrdOdProd.setTotalFee(backTotalFee);
		long backCouponFee=(ordOdProd.getCouponFee()/ordOdProd.getBuySum())*prodSum;  //该商品数目下的退款优惠券
		long backJfFee=(ordOdProd.getJfFee()/ordOdProd.getBuySum())*prodSum;      //该商品数目下的退款消费积分
		long backGiveJf=(ordOdProd.getJf()/ordOdProd.getBuySum())*prodSum;	//该商品数目下的退款赠送积分
		backOrdOdProd.setCouponFee(backCouponFee);
		backOrdOdProd.setJfFee(backJfFee); 
		backOrdOdProd.setJf(backGiveJf);
		//积分兑换 TODO
		backOrdOdProd.setDiscountFee(backCouponFee+backJfFee+backGiveJf); 
		backOrdOdProd.setAdjustFee(backTotalFee-backOrdOdProd.getDiscountFee());
		backOrdOdProd.setState(OrdersConstants.OrdOdProd.State.RETURN);
		backOrdOdProd.setUpdateTime(DateUtil.getSysDate());
		backOrdOdProd.setProdDetalId(SequenceUtil.createProdDetailId());
		backOrdOdProd.setOrderId(backOrderId);
		backOrdOdProd.setUpdateOperId(request.getOperId());//变更工号 
		ordOdProdAtomSV.insertSelective(backOrdOdProd);
		/* 6.生成退款订单费用总表*/
		OrdOdFeeTotal odFeeTotal = ordOdFeeTotalAtomSV.selectByOrderId(order.getTenantId(), 
				order.getOrderId());
		OrdOdFeeTotal rdOrdOdFeeTotal=new OrdOdFeeTotal();
		BeanUtils.copyProperties(rdOrdOdFeeTotal, odFeeTotal);
		rdOrdOdFeeTotal.setOrderId(backOrderId);
		rdOrdOdFeeTotal.setTenantId(request.getTenantId());
		rdOrdOdFeeTotal.setPayFlag(OrdersConstants.OrdOdFeeTotal.payFlag.OUT);
		rdOrdOdFeeTotal.setTotalFee(backTotalFee);
		rdOrdOdFeeTotal.setAdjustFee(backTotalFee-backOrdOdProd.getDiscountFee());
		rdOrdOdFeeTotal.setPayFee(backTotalFee-backOrdOdProd.getDiscountFee());
		rdOrdOdFeeTotal.setDiscountFee(ordOdProd.getDiscountFee());
		rdOrdOdFeeTotal.setOperDiscountFee(ordOdProd.getOperDiscountFee());
		rdOrdOdFeeTotal.setPaidFee(0);
		rdOrdOdFeeTotal.setFreight(0);
		rdOrdOdFeeTotal.setUpdateTime(DateUtil.getSysDate());
		ordOdFeeTotalAtomSV.insertSelective(rdOrdOdFeeTotal);
		/* 7.生成退款订单支付机构接口*/
		OrdBalacneIf ordBalacneIf = ordBalacneIfAtomSV.selectByOrderId(
				order.getTenantId(), order.getParentOrderId());
		OrdBalacneIf balacneIf=new OrdBalacneIf();
		BeanUtils.copyProperties(balacneIf, ordBalacneIf);
		Long balacneIfId = SequenceUtil.createBalacneIfId();
		balacneIf.setBalacneIfId(balacneIfId);
		balacneIf.setOrderId(backOrderId);
		balacneIf.setPayStyle(odFeeTotal.getPayStyle());
		balacneIf.setPayFee(backTotalFee-backOrdOdProd.getDiscountFee());
		balacneIf.setCreateTime(DateUtil.getSysDate());
		ordBalacneIfAtomSV.insertSelective(balacneIf);
		//组装退货申请单创建参数(OFC)
		String params = getOFCAfterSaleOrderCreateParam(order, ordOdProd, sysDate, 
				backTotalFee, prodSum, OrdersConstants.OFCApplyType.BACK);
        Map<String, String> header=new HashMap<String, String>(); 
        header.put("appkey", OrdersConstants.OFC_APPKEY);
        //发送Post请求,并返回信息
        try {
			String strData = HttpClientUtil.sendPost(OrdersConstants.OFC_RETURN_CREATE_URL, params, header);
			JSONObject object = JSON.parseObject(strData);
			boolean val = object.getBooleanValue("IsValid");
			if(!val) {
				throw new BusinessException("", "退货申请同步到OFC错误");
			}
		} catch (IOException | URISyntaxException e) {
			logger.error(e.getMessage());
			throw new SystemException("", "OFC同步出现异常");
		}
	}

	@Override
	public void exchange(OrderReturnRequest request) throws BusinessException, SystemException {
		/* 1.参数校验及返回子订单下的商品信息*/
		List<OrdOdProd> ordOdProds = this.checkParams(request);
		/* 2.查询该商品的子订单*/
		OrdOrder order = this.getOrdOrder(request);
		/* 3.商品明细信息查询*/
		OrdOdProd ordOdProd = this.getOrdOdProd(request);
		if(!ordOdProds.contains(ordOdProd)) {
			throw new BusinessException("", 
					"商品与订单内容不一致,[订单id:"+request.getOrderId()+""
							+ ",商品明细id:"+request.getProdDetalId()+"]");
		}
		/* 4.生成换货订单*/
		OrdOrder exOrder=new OrdOrder();
		BeanUtils.copyProperties(exOrder, order);
		long exOrderId=SequenceUtil.createOrderId();
		Timestamp sysDate = DateUtil.getSysDate();
		exOrder.setBusiCode(OrdersConstants.OrdOrder.BusiCode.EXCHANGE_ORDER); //换货单
		exOrder.setOrderId(exOrderId); //换货单id
		exOrder.setOperId(request.getOperId());//受理工号 
		exOrder.setCusServiceFlag(OrdersConstants.OrdOrder.cusServiceFlag.YES);//是否售后标识
		exOrder.setOrigOrderId(order.getOrderId());
		this.insertOrderState(sysDate, exOrder);
		//更新商品为售后标识
		ordOdProd.setCusServiceFlag(OrdersConstants.OrdOrder.cusServiceFlag.YES);
		ordOdProdAtomSV.updateById(ordOdProd); 
		/* 5.生成换货商品明细信息*/
		OrdOdProd backOrdOdProd =new OrdOdProd();
		BeanUtils.copyProperties(backOrdOdProd, ordOdProd);
		backOrdOdProd.setState(OrdersConstants.OrdOdProd.State.EXCHANGE);
		backOrdOdProd.setUpdateTime(DateUtil.getSysDate());
		backOrdOdProd.setProdDetalId(SequenceUtil.createProdDetailId());
		backOrdOdProd.setOrderId(exOrderId);
		backOrdOdProd.setUpdateOperId(request.getOperId());//变更工号
		ordOdProdAtomSV.insertSelective(backOrdOdProd);
	}

	@Override
	public void refund(OrderReturnRequest request) throws BusinessException, SystemException {
		/* 1.参数校验及返回子订单下的商品信息*/
		List<OrdOdProd> ordOdProds = this.checkParams(request);
		/* 2.查询该商品的子订单*/
		OrdOrder order = this.getOrdOrder(request);
		/* 3.商品明细信息查询*/
		OrdOdProd ordOdProd = this.getOrdOdProd(request);
		if(!ordOdProds.contains(ordOdProd)) {
			throw new BusinessException("", 
					"商品与订单内容不一致,[订单id:"+request.getOrderId()+""
							+ ",商品明细id:"+request.getProdDetalId()+"]");
		}
		/* 4.生成退款订单信息*/
		OrdOrder rdOrder=new OrdOrder();
		BeanUtils.copyProperties(rdOrder, order);
		long rdOrderId=SequenceUtil.createOrderId();
		Timestamp sysDate = DateUtil.getSysDate();
		rdOrder.setBusiCode(OrdersConstants.OrdOrder.BusiCode.CANCEL_ORDER); //退款单
		rdOrder.setCusServiceFlag(OrdersConstants.OrdOrder.cusServiceFlag.YES);//是否售后标识
		rdOrder.setOrderId(rdOrderId); //退款单id
		rdOrder.setOperId(request.getOperId());// 受理工号 
		rdOrder.setOrigOrderId(order.getOrderId());
		this.insertOrderState(sysDate, rdOrder);
		//更新商品为售后标识
		ordOdProd.setCusServiceFlag(OrdersConstants.OrdOrder.cusServiceFlag.YES);
		ordOdProdAtomSV.updateById(ordOdProd); 
		/* 5.生成退款商品明细信息*/
		OrdOdProd backOrdOdProd =new OrdOdProd();
		BeanUtils.copyProperties(backOrdOdProd, ordOdProd);
		backOrdOdProd.setState(OrdersConstants.OrdOdProd.State.RETURN);
		backOrdOdProd.setUpdateTime(DateUtil.getSysDate());
		backOrdOdProd.setProdDetalId(SequenceUtil.createProdDetailId());
		backOrdOdProd.setOrderId(rdOrderId);
		backOrdOdProd.setUpdateOperId(request.getOperId());//变更工号 
		ordOdProdAtomSV.insertSelective(backOrdOdProd);
		/* 6.生成退款订单费用总表*/
		OrdOdFeeTotal odFeeTotal = ordOdFeeTotalAtomSV.selectByOrderId(order.getTenantId(), 
				order.getOrderId());
		OrdOdFeeTotal rdOrdOdFeeTotal=new OrdOdFeeTotal();
		BeanUtils.copyProperties(rdOrdOdFeeTotal, odFeeTotal);
		rdOrdOdFeeTotal.setOrderId(rdOrderId);
		rdOrdOdFeeTotal.setTenantId(request.getTenantId());
		rdOrdOdFeeTotal.setPayFlag(OrdersConstants.OrdOdFeeTotal.payFlag.OUT);
		rdOrdOdFeeTotal.setTotalFee(ordOdProd.getTotalFee());
		rdOrdOdFeeTotal.setDiscountFee(ordOdProd.getDiscountFee());
		rdOrdOdFeeTotal.setOperDiscountFee(ordOdProd.getOperDiscountFee());
		rdOrdOdFeeTotal.setAdjustFee(ordOdProd.getAdjustFee());
		rdOrdOdFeeTotal.setPayFee(ordOdProd.getAdjustFee());
		rdOrdOdFeeTotal.setPaidFee(0);
		rdOrdOdFeeTotal.setFreight(0);
		rdOrdOdFeeTotal.setUpdateTime(DateUtil.getSysDate());
		ordOdFeeTotalAtomSV.insertSelective(rdOrdOdFeeTotal);
		/* 7.生成退款订单支付机构接口*/
		OrdBalacneIf ordBalacneIf = ordBalacneIfAtomSV.selectByOrderId(
				order.getTenantId(), order.getParentOrderId());
		OrdBalacneIf balacneIf=new OrdBalacneIf();
		BeanUtils.copyProperties(balacneIf, ordBalacneIf);
		Long balacneIfId = SequenceUtil.createBalacneIfId();
		balacneIf.setBalacneIfId(balacneIfId);
		balacneIf.setOrderId(rdOrderId);
		balacneIf.setPayStyle(odFeeTotal.getPayStyle());
		balacneIf.setPayFee(ordOdProd.getAdjustFee());
		balacneIf.setCreateTime(DateUtil.getSysDate());
		ordBalacneIfAtomSV.insertSelective(balacneIf);
		
		//组装退款申请单创建参数(OFC)
		String params = getOFCAfterSaleOrderCreateParam(order, ordOdProd, sysDate, 
				ordOdProd.getTotalFee(), ordOdProd.getBuySum(), OrdersConstants.OFCApplyType.REFUND);
		//发送Post请求
        Map<String, String> header=new HashMap<String, String>(); 
        header.put("appkey", OrdersConstants.OFC_APPKEY);
        //发送Post请求,并返回信息
        try {
			String strData = HttpClientUtil.sendPost(OrdersConstants.OFC_RETURN_CREATE_URL, params, header);
			JSONObject object = JSON.parseObject(strData);
			boolean val = object.getBooleanValue("IsValid");
			if(!val) {
				throw new BusinessException("", "退款申请同步到OFC错误");
			}
        } catch (IOException | URISyntaxException e) {
			logger.error(e.getMessage());
			throw new SystemException("", "OFC同步出现异常");
		} finally {
		}
	}
	
	
	/**
	 * 参数校验及获取子订单下的商品信息
	 */
	private List<OrdOdProd> checkParams(OrderReturnRequest request) {
		/* 租户非空校验*/
		CommonCheckUtils.checkTenantId(request.getTenantId(), ExceptCodeConstants.Special.PARAM_IS_NULL);
		/* 订单id非空检验*/
		if(request.getOrderId()==0) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "订单id不能为空");
		}
		/* 商户明细id非空校验*/
		if(request.getProdDetalId()==0) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "商品明细id不能为空");
		}
		OrdOdProdCriteria example=new OrdOdProdCriteria();
		Criteria criteria = example.createCriteria();
		criteria.andTenantIdEqualTo(request.getTenantId());
		criteria.andOrderIdEqualTo(request.getOrderId());
		List<OrdOdProd> ordOdProds = ordOdProdAtomSV.selectByExample(example);
		if(CollectionUtil.isEmpty(ordOdProds)) {
			logger.error("未能查询到指定的订单商品明细信息[订单id:"+request.getOrderId()+"]");
			throw new BusinessException(ExceptCodeConstants.Special.NO_RESULT, 
					"未能查询到指定的订单商品明细信息[订单id:"+request.getOrderId()+"]");
		}
		return ordOdProds;
	}
	
	
	/**
	 * 订单信息
	 */
	private OrdOrder getOrdOrder(OrderReturnRequest request) {
		OrdOrder order = ordOrderAtomSV.selectByOrderId(request.getTenantId(), request.getOrderId());
		if(order==null) {
			logger.error("订单信息不存在[订单id:"+request.getOrderId()+"]");
			throw new BusinessException(ExceptCodeConstants.Special.NO_RESULT, 
					"订单信息不存在[订单id:"+request.getOrderId()+"]");
		}
		return order;
	}
	
	/**
	 * 订单商品信息
	 */
	private OrdOdProd getOrdOdProd(OrderReturnRequest request) {
		OrdOdProd ordOdProd = ordOdProdAtomSV.selectByPrimaryKey(request.getProdDetalId());
		if(ordOdProd==null) {
			logger.error("订单商品明细不存在[订单id:"+request.getProdDetalId()+"]");
			throw new BusinessException(ExceptCodeConstants.Special.NO_RESULT, 
					"订单商品明细不存在[订单id:"+request.getProdDetalId()+"]");
		}
		return ordOdProd;
	}
	

    /**
     * 保存订单及写入订单轨迹
     */
    private void insertOrderState(Timestamp sysDate,OrdOrder ordOrder) {
    	String orgState = ordOrder.getState();
    	String newState = OrdersConstants.OrdOrder.State.REVOKE_WAIT_AUDIT;
    	ordOrder.setState(newState);
    	ordOrder.setStateChgTime(sysDate);
		ordOrderAtomSV.insertSelective(ordOrder);
		orderFrameCoreSV.ordOdStateChg(ordOrder.getOrderId(), ordOrder.getTenantId(), orgState, 
				newState,OrdOdStateChg.ChgDesc.ORDER_TO_AUDIT, ordOrder.getOperId(), null, null,sysDate);
    }
    
    /**
     * 组装退换货申请单创建参数(OCF)
     */
    private String getOFCAfterSaleOrderCreateParam(OrdOrder order,OrdOdProd ordOdProd,
    		Timestamp sysDate,long backTotalFee,long prodSum,int type) {
		OFCAfterSaleOrderCreateRequest orderCreateRequest=new OFCAfterSaleOrderCreateRequest();
		OrderAfterSaleApplyVo applyVo=new OrderAfterSaleApplyVo();
		applyVo.setOrderNo(String.valueOf(order.getOrderId())); //TODO 生成的退货订单还是之前的订单 ??
		applyVo.setExternalApplyNo("");
		applyVo.setApplyType(type);
		applyVo.setReasonType(16); //其它
		applyVo.setDescription("");
		applyVo.setRefundAmount(backTotalFee/10); //分为单位
		applyVo.setApplyTime(sysDate.toString());
		applyVo.setRemark(""); //TODO
		List<OrderAfterSaleApplyItemsVo> applyItemsVoList=new ArrayList<OrderAfterSaleApplyItemsVo>();
		OrderAfterSaleApplyItemsVo applyItemsVo=new OrderAfterSaleApplyItemsVo();
		applyItemsVo.setProductName(ordOdProd.getProdName());
		applyItemsVo.setProductCode("");
		applyItemsVo.setApplyQuanlity(prodSum);
		applyItemsVoList.add(applyItemsVo);
		orderCreateRequest.setOrderAfterSaleApplyVo(applyVo);
		orderCreateRequest.setOrderAfterSaleApplyItemsVoList(applyItemsVoList);
    	return JSON.toJSONString(orderCreateRequest);
    }
}
