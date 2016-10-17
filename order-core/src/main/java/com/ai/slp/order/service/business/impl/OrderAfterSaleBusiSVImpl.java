package com.ai.slp.order.service.business.impl;

import java.io.IOException;
import java.math.BigDecimal;
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
import com.ai.opt.sdk.util.StringUtil;
import com.ai.slp.order.api.aftersaleorder.impl.OrderAfterSaleSVImpl;
import com.ai.slp.order.api.aftersaleorder.param.OrderOFCBackRequest;
import com.ai.slp.order.api.aftersaleorder.param.OrderReturnRequest;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.constants.OrdersConstants.OrdOdStateChg;
import com.ai.slp.order.dao.mapper.bo.OrdBalacneIf;
import com.ai.slp.order.dao.mapper.bo.OrdOdFeeProd;
import com.ai.slp.order.dao.mapper.bo.OrdOdFeeTotal;
import com.ai.slp.order.dao.mapper.bo.OrdOdProd;
import com.ai.slp.order.dao.mapper.bo.OrdOdProdCriteria;
import com.ai.slp.order.dao.mapper.bo.OrdOdProdCriteria.Criteria;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.dao.mapper.bo.OrdOrderCriteria;
import com.ai.slp.order.service.atom.interfaces.IOrdBalacneIfAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdFeeProdAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdFeeTotalAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdProdAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.ai.slp.order.service.business.interfaces.IOrderAfterSaleBusiSV;
import com.ai.slp.order.service.business.interfaces.IOrderFrameCoreSV;
import com.ai.slp.order.util.CommonCheckUtils;
import com.ai.slp.order.util.JfAndAmountExchangeUtil;
import com.ai.slp.order.util.SequenceUtil;
import com.ai.slp.order.util.ValidateUtils;
import com.ai.slp.order.vo.OFCAfterSaleOrderCreateRequest;
import com.ai.slp.order.vo.OrderAfterSaleApplyItemsVo;
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
	
	@Autowired
	private IOrdOdFeeProdAtomSV ordOdFeeProdAtomSV;
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
		backOrder.setRemark("");
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
		//优惠券 消费积分 赠送积分比例
		BigDecimal couponFeeRate=BigDecimal.valueOf(ordOdProd.getCouponFee()).divide(new BigDecimal(ordOdProd.getBuySum()),2,BigDecimal.ROUND_HALF_UP);
		BigDecimal JfFeeRate=BigDecimal.valueOf(ordOdProd.getJfFee()).divide(new BigDecimal(ordOdProd.getBuySum()),2,BigDecimal.ROUND_HALF_UP);
		BigDecimal giveJfRate=BigDecimal.valueOf(ordOdProd.getJf()).divide(new BigDecimal(ordOdProd.getBuySum()),2,BigDecimal.ROUND_HALF_UP);
		BigDecimal operDiscountFeeRate=BigDecimal.valueOf(ordOdProd.getOperDiscountFee()).divide(new BigDecimal(ordOdProd.getBuySum()),2,BigDecimal.ROUND_HALF_UP);
		BigDecimal discountFeeRate=BigDecimal.valueOf(ordOdProd.getDiscountFee()).divide(new BigDecimal(ordOdProd.getBuySum()),2,BigDecimal.ROUND_HALF_UP);
		long backCouponFee=(couponFeeRate.multiply(new BigDecimal(prodSum))).longValue();  //该商品数目下的退款优惠券
		long backJfFee=(JfFeeRate.multiply(new BigDecimal(prodSum))).longValue();      //该商品数目下的退款消费积分
		long backGiveJf=(giveJfRate.multiply(new BigDecimal(prodSum))).longValue();	//该商品数目下的退款赠送积分
		long backOperDiscountFee=(operDiscountFeeRate.multiply(new BigDecimal(prodSum))).longValue();	//该商品数目下的减免费用 
		long discountFee=(discountFeeRate.multiply(new BigDecimal(prodSum))).longValue();	//该商品数目下的优惠金额
		backOrdOdProd.setCouponFee(backCouponFee);
		backOrdOdProd.setJfFee(backJfFee); 
		backOrdOdProd.setJf(backGiveJf);
		backOrdOdProd.setOperDiscountFee(backOperDiscountFee);
		backOrdOdProd.setDiscountFee(discountFee);
		backOrdOdProd.setAdjustFee(backTotalFee-backOrdOdProd.getDiscountFee());
		backOrdOdProd.setState(OrdersConstants.OrdOdProd.State.RETURN);
		backOrdOdProd.setUpdateTime(DateUtil.getSysDate());
		backOrdOdProd.setProdDetalId(SequenceUtil.createProdDetailId());
		backOrdOdProd.setOrderId(backOrderId);
		backOrdOdProd.setUpdateOperId(request.getOperId());
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
		rdOrdOdFeeTotal.setDiscountFee(backOrdOdProd.getDiscountFee());
		rdOrdOdFeeTotal.setOperDiscountFee(backOrdOdProd.getOperDiscountFee());
		rdOrdOdFeeTotal.setPaidFee(0);
		rdOrdOdFeeTotal.setFreight(0);//运费暂不做运算
		rdOrdOdFeeTotal.setUpdateTime(DateUtil.getSysDate());
		ordOdFeeTotalAtomSV.insertSelective(rdOrdOdFeeTotal);
		/* 生成退款费用明细表*/
		List<OrdOdFeeProd> feeProdList = ordOdFeeProdAtomSV.selectByOrderId(order.getOrderId());
		for (OrdOdFeeProd ordOdFeeProd : feeProdList) {
			OrdOdFeeProd subOrdOdFeeProd=new OrdOdFeeProd();
			subOrdOdFeeProd.setOrderId(backOrderId);
			if(OrdersConstants.OrdOdFeeProd.PayStyle.JF.equals(ordOdFeeProd.getPayStyle())) {
				subOrdOdFeeProd.setPayStyle(ordOdFeeProd.getPayStyle());
				subOrdOdFeeProd.setPaidFee(backJfFee);
				String rate = JfAndAmountExchangeUtil.getRate(order.getAccountId());
				if(!StringUtil.isBlank(rate)) {
					String[] split = rate.split(":");
					BigDecimal JfAmout=BigDecimal.valueOf(backJfFee).divide(new BigDecimal(split[0]),
							2,BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(split[1]),2,BigDecimal.ROUND_HALF_UP);
					subOrdOdFeeProd.setJfAmount(JfAmout.multiply(new BigDecimal(1000)).longValue());//积分对应的金额,并元转厘
				}
			}else if(OrdersConstants.OrdOdFeeProd.PayStyle.COUPON.equals(ordOdFeeProd.getPayStyle())) {
				subOrdOdFeeProd.setPayStyle(ordOdFeeProd.getPayStyle());
				subOrdOdFeeProd.setPaidFee(backCouponFee);//优惠券
			}else {
				subOrdOdFeeProd.setPayStyle(ordOdFeeProd.getPayStyle());
				subOrdOdFeeProd.setPaidFee(backTotalFee-backOrdOdProd.getDiscountFee());
			}
		}
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
		
		//组装退货申请单(OFC)
		if(OrdersConstants.OrdOrder.Flag.OFC.equals(order.getFlag())) {
			String params = getOFCAfterSaleOrderCreateParam(order,backOrderId,ordOdProd, sysDate, 
					backTotalFee, prodSum, OrdersConstants.OFCApplyType.BACK);
			Map<String, String> header=new HashMap<String, String>(); 
			header.put("appkey", OrdersConstants.OFC_APPKEY);
			//发送Post请求,并返回信息
			try {
				String strData = HttpClientUtil.sendPost(OrdersConstants.OFC_RETURN_CREATE_URL, params, header);
				JSONObject object = JSON.parseObject(strData);
				boolean val = object.getBooleanValue("IsValid");
				/*	if(!val) {
				throw new BusinessException("", "退货申请同步到OFC错误");
			}*/
			} catch (IOException | URISyntaxException e) {
				logger.error(e.getMessage());
				throw new SystemException("", "OFC同步出现异常");
			}
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
		exOrder.setRemark("");
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
		rdOrder.setRemark("");
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
		
		//组装退款申请单(OFC) TODO
		if(OrdersConstants.OrdOrder.Flag.OFC.equals(order.getFlag())) {
			String params = getOFCAfterSaleOrderCreateParam(order,rdOrderId, ordOdProd, sysDate, 
					ordOdProd.getTotalFee(), ordOdProd.getBuySum(), OrdersConstants.OFCApplyType.REFUND);
			//发送Post请求
			Map<String, String> header=new HashMap<String, String>(); 
			header.put("appkey", OrdersConstants.OFC_APPKEY);
			//发送Post请求,并返回信息
			try {
				String strData = HttpClientUtil.sendPost(OrdersConstants.OFC_RETURN_CREATE_URL, params, header);
				JSONObject object = JSON.parseObject(strData);
				boolean val = object.getBooleanValue("IsValid");
				/*if(!val) {
				throw new BusinessException("", "退款申请同步到OFC错误");
			}*/
			} catch (IOException | URISyntaxException e) {
				logger.error(e.getMessage());
				throw new SystemException("", "OFC同步出现异常");
			}
		}
	}
	
	
	@Override
	public void backStateOFC(OrderOFCBackRequest request) throws BusinessException, SystemException {
		/*参数校验*/
		ValidateUtils.validateOFCBackRequest(request);
		OrdOrderCriteria example=new OrdOrderCriteria();
		OrdOrderCriteria.Criteria criteria = example.createCriteria();
		criteria.andOrderIdEqualTo(Long.parseLong(request.getExternalOrderId()));
		criteria.andOrigOrderIdEqualTo(Long.parseLong(request.getOrderId()));
		List<OrdOrder> orderList = ordOrderAtomSV.selectByExample(example);
		if(CollectionUtil.isEmpty(orderList)) {
			throw new BusinessException(ExceptCodeConstants.Special.NO_RESULT, 
					"订单信息不存在[订单id:"+request.getOrderId()+"]");
		}
		OrdOrder ordOrder = orderList.get(0);
		ordOrder.setState(request.getState());
		ordOrder.setRemark(request.getReasonDesc());
		ordOrderAtomSV.updateById(ordOrder);
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
		if(StringUtil.isBlank(request.getOperId())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "受理工号openrId不能为空");
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
    private String getOFCAfterSaleOrderCreateParam(OrdOrder order,long subOrderId,OrdOdProd ordOdProd,
    		Timestamp sysDate,long backTotalFee,long prodSum,int type) {
		OFCAfterSaleOrderCreateRequest orderCreateRequest=new OFCAfterSaleOrderCreateRequest();
		orderCreateRequest.setOrderNo(String.valueOf(order.getOrderId())); //之前的子订单 
		orderCreateRequest.setExternalApplyNo(String.valueOf(subOrderId)); //售后订单
		orderCreateRequest.setApplyType(type);
		orderCreateRequest.setReasonType(16); //其它
		orderCreateRequest.setDescription("其它"); //TODO
		orderCreateRequest.setRefundAmount(backTotalFee/10); //分为单位
		orderCreateRequest.setApplyTime(sysDate.toString());
		orderCreateRequest.setRemark(""); 
		List<OrderAfterSaleApplyItemsVo> applyItemsVoList=new ArrayList<OrderAfterSaleApplyItemsVo>();
		OrderAfterSaleApplyItemsVo applyItemsVo=new OrderAfterSaleApplyItemsVo();
		applyItemsVo.setProductName(ordOdProd.getProdName());
		applyItemsVo.setProductCode(ordOdProd.getProdCode()); //产品编码
		applyItemsVo.setApplyQuanlity(prodSum);
		applyItemsVoList.add(applyItemsVo);
		orderCreateRequest.setItems(applyItemsVoList);
    	return JSON.toJSONString(orderCreateRequest);
    }
    
    
    /**
     * 测试OFC售后订单的创建
     */
/*    public static void main(String[] args) {
		OFCAfterSaleOrderCreateRequest orderCreateRequest=new OFCAfterSaleOrderCreateRequest();
		orderCreateRequest.setOrderNo(String.valueOf(2000001011472557l)); //之前的子订单 
		orderCreateRequest.setExternalApplyNo(String.valueOf(2000001018727740l)); //售后订单
		orderCreateRequest.setApplyType(OrdersConstants.OFCApplyType.BACK);
		orderCreateRequest.setReasonType(16); //其它
		orderCreateRequest.setDescription("其它"); //TODO
		orderCreateRequest.setRefundAmount(1); //分为单位
		orderCreateRequest.setApplyTime(DateUtil.getSysDate().toString());
		orderCreateRequest.setRemark(""); 
		List<OrderAfterSaleApplyItemsVo> applyItemsVoList=new ArrayList<OrderAfterSaleApplyItemsVo>();
		OrderAfterSaleApplyItemsVo applyItemsVo=new OrderAfterSaleApplyItemsVo();
		applyItemsVo.setProductName("1111111");
		applyItemsVo.setProductCode("CH067n"); //产品编码
		applyItemsVo.setApplyQuanlity(1);
		applyItemsVoList.add(applyItemsVo);
		//orderCreateRequest.setOrderAfterSaleApplyVo(applyVo);
		orderCreateRequest.setItems(applyItemsVoList);
		String params = JSON.toJSONString(orderCreateRequest);

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
	}*/
//	}
}
