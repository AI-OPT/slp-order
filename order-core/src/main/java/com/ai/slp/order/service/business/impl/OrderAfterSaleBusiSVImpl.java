package com.ai.slp.order.service.business.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.dao.mapper.bo.OrdOrderCriteria;
import com.ai.slp.order.service.atom.interfaces.IOrdBalacneIfAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdFeeProdAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdFeeTotalAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdProdAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.ai.slp.order.service.business.interfaces.IOrderAfterSaleBusiSV;
import com.ai.slp.order.service.business.interfaces.IOrderFrameCoreSV;
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
    	/* 1.参数校验*/
		ValidateUtils.validateOrderReturnRequest(request);
		/* 2.查询该商品的子订单*/
		OrdOrder order = this.getOrdOrder(request);
		/* 3.查询商品明细信息*/
		OrdOdProd ordOdProd = this.getOrdOdProd(request);
		long prodSum = request.getProdSum();
		if(prodSum>ordOdProd.getBuySum()) {
			throw new BusinessException("","退货数量不能大于实际商品数量");
		}
		long backOrderId=SequenceUtil.createOrderId();
		Timestamp sysDate=DateUtil.getSysDate();
		String state = order.getState();
		long backTotalFee = 0;
		/* 4.创建订单售后信息*/
		backTotalFee = this.createAfterOrderInfo(order, request,
				OrdersConstants.OrdOrder.BusiCode.UNSUBSCRIBE_ORDER, 
				prodSum, ordOdProd, OrdersConstants.OrdOdProd.State.RETURN,backOrderId,sysDate,state);
		//TODO
		/* 5.组装退货申请单(OFC)*/
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
		/* 1.参数校验*/
		ValidateUtils.validateOrderReturnRequest(request);
		/* 2.查询该商品的子订单*/
		OrdOrder order = this.getOrdOrder(request);
		/* 3.查询商品明细信息*/
		OrdOdProd ordOdProd = this.getOrdOdProd(request);
		long prodSum = request.getProdSum();
		if(prodSum>ordOdProd.getBuySum()) {
			logger.error("换货的数量不能大于实际的商品数量");
			throw new BusinessException("", 
					"换货的数量不能大于实际的商品数量");
		}
		long exchangeOrderId=SequenceUtil.createOrderId();
		Timestamp sysDate=DateUtil.getSysDate();
		String state = order.getState();
		/* 4.创建订单售后信息*/
		this.createAfterOrderInfo(order, request,
				OrdersConstants.OrdOrder.BusiCode.EXCHANGE_ORDER, 
				prodSum, ordOdProd, OrdersConstants.OrdOdProd.State.EXCHANGE,exchangeOrderId,sysDate,state);
	}

	
	@Override
	public void refund(OrderReturnRequest request) throws BusinessException, SystemException {
		/* 1.参数校验*/
		ValidateUtils.validateOrderReturnRequest(request);
		/* 2.查询该商品的子订单*/
		OrdOrder order = this.getOrdOrder(request);
		/* 3.查询商品明细信息*/
		OrdOdProd ordOdProd = this.getOrdOdProd(request);
		long prodSum = request.getProdSum();
		if(prodSum>ordOdProd.getBuySum()) {
			logger.error("退款的数量不能大于实际的商品数量");
			throw new BusinessException("", 
					"退款数量不能大于实际的商品数量");
		}
		long refundOrderId=SequenceUtil.createOrderId();
		Timestamp sysDate=DateUtil.getSysDate();
		/* 4.创建订单售后信息*/
		long refundTotalFee = this.createAfterOrderInfo(order, request,
				OrdersConstants.OrdOrder.BusiCode.CANCEL_ORDER, 
				prodSum, ordOdProd, OrdersConstants.OrdOdProd.State.RETURN,refundOrderId,sysDate,null);
		//TODO
		/* 5.组装退款申请单(OFC)*/ 
		if(OrdersConstants.OrdOrder.Flag.OFC.equals(order.getFlag())) {
			String params = getOFCAfterSaleOrderCreateParam(order,refundOrderId, ordOdProd, sysDate, 
					refundTotalFee, ordOdProd.getBuySum(), OrdersConstants.OFCApplyType.REFUND);
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
	 * 获取订单信息
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
	 * 获取订单商品信息
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
    	String newState=null;
    	String chgDesc=null;
    	if(OrdersConstants.OrdOrder.BusiCode.CANCEL_ORDER.equals(ordOrder.getBusiCode())) {  
    		newState=OrdersConstants.OrdOrder.State.WAIT_REPAY;
    		chgDesc=OrdOdStateChg.ChgDesc.ORDER_SELLER_CONFIRMED_WAIT_PAY;
    	}else{
    		newState = OrdersConstants.OrdOrder.State.REVOKE_WAIT_AUDIT;
    		chgDesc=OrdOdStateChg.ChgDesc.ORDER_TO_AUDIT;
    	}
    	ordOrder.setState(newState);
    	ordOrder.setStateChgTime(sysDate);
		ordOrderAtomSV.insertSelective(ordOrder);
		orderFrameCoreSV.ordOdStateChg(ordOrder.getOrderId(), ordOrder.getTenantId(), orgState, 
				newState,chgDesc, ordOrder.getOperId(), null, null,sysDate);
    }
    
    
    /**
     * 创建售后订单信息
     */
    public long createAfterOrderInfo(OrdOrder order,OrderReturnRequest request,
    		String busiCode,long prodSum,OrdOdProd ordOdProd,
    		String prodState,long afterOrderId,Timestamp sysDate,String orderState) {
    	long orderStart=System.currentTimeMillis();
		logger.info("####loadtest####开始售后订单创建，当前时间戳>>>>>>>>>>："+orderStart);
    	/* 1.创建售后订单*/
		OrdOrder afterOrder=new OrdOrder();
		BeanUtils.copyProperties(afterOrder, order);
		afterOrder.setBusiCode(busiCode); 
		afterOrder.setOrderId(afterOrderId); 
		afterOrder.setOperId(request.getOperId());
		afterOrder.setCusServiceFlag(OrdersConstants.OrdOrder.cusServiceFlag.YES);//设置售后标识 Y
		afterOrder.setOrigOrderId(order.getOrderId());
		afterOrder.setRemark(request.getAfterSaleReason()); //退款理由 
		this.insertOrderState(sysDate, afterOrder);
		long orderEnd=System.currentTimeMillis();
		logger.info("####loadtest####完成售后订单创建,当前时间戳>>>>>>>>>>："+orderEnd+",用时:"+(orderEnd-orderStart)+"毫秒");
		OrdOdFeeTotal odFeeTotal =null;
		long afterTotalFee=0;
		OrdOdProd afterOrdOdProd=null;
		OrdOdFeeTotal rdOrdOdFeeTotal=null;
		//待配货,待出库,退款订单的时候按照全部数量
    	if(orderState==null||(OrdersConstants.OrdOrder.State.WAIT_DISTRIBUTION.equals(orderState)||
				OrdersConstants.OrdOrder.State.WAIT_DELIVERY.equals(orderState)||
				OrdersConstants.OrdOrder.State.WAIT_SEND.equals(orderState)||
				OrdersConstants.OrdOrder.BusiCode.CANCEL_ORDER.equals(busiCode))) {
    		long prodStart=System.currentTimeMillis();
			logger.info("####loadtest####按照全部数量,开始售后订单商品明细创建，当前时间戳>>>>>>>>>>："+prodStart);
    		/* 2.生成商品明细信息*/
			afterOrdOdProd =new OrdOdProd();
			BeanUtils.copyProperties(afterOrdOdProd, ordOdProd);
			afterTotalFee = afterOrdOdProd.getTotalFee();
			afterOrdOdProd.setState(prodState);
			afterOrdOdProd.setUpdateTime(DateUtil.getSysDate());
			afterOrdOdProd.setProdDetalId(SequenceUtil.createProdDetailId());
			afterOrdOdProd.setOrderId(afterOrderId);
			afterOrdOdProd.setUpdateOperId(request.getOperId());//变更工号
			afterOrdOdProd.setProdDesc(request.getImageId()); //图片id
			afterOrdOdProd.setProdSn(request.getImageType()); //图片类型
			ordOdProdAtomSV.insertSelective(afterOrdOdProd);
			long prodEnd=System.currentTimeMillis();
			logger.info("####loadtest####按照全部数量,完成售后订单商品明细创建,当前时间戳>>>>>>>>>>："+prodEnd+",用时:"+(prodEnd-prodStart)+"毫秒");
			odFeeTotal = ordOdFeeTotalAtomSV.selectByOrderId(order.getTenantId(), 
					order.getOrderId());
			long feetotalStart=System.currentTimeMillis();
			logger.info("####loadtest####按照全部数量,开始售后费用总表细创建，当前时间戳>>>>>>>>>>："+feetotalStart);
			/* 生成售后费用总表信息*/
			rdOrdOdFeeTotal=new OrdOdFeeTotal();
			BeanUtils.copyProperties(rdOrdOdFeeTotal, odFeeTotal);
			rdOrdOdFeeTotal.setTotalFee(afterTotalFee);
			rdOrdOdFeeTotal.setAdjustFee(afterOrdOdProd.getAdjustFee());
			rdOrdOdFeeTotal.setPaidFee(0);
			rdOrdOdFeeTotal.setPayFee(afterOrdOdProd.getAdjustFee());
			rdOrdOdFeeTotal.setDiscountFee(afterOrdOdProd.getDiscountFee());
			rdOrdOdFeeTotal.setOperDiscountFee(afterOrdOdProd.getOperDiscountFee());
			rdOrdOdFeeTotal.setTotalJf(afterOrdOdProd.getJf());
			rdOrdOdFeeTotal.setOrderId(afterOrderId);
			rdOrdOdFeeTotal.setTenantId(request.getTenantId());
			rdOrdOdFeeTotal.setPayFlag(OrdersConstants.OrdOdFeeTotal.payFlag.OUT);
			rdOrdOdFeeTotal.setUpdateTime(DateUtil.getSysDate());
			ordOdFeeTotalAtomSV.insertSelective(rdOrdOdFeeTotal);
			long feetotalEnd=System.currentTimeMillis();
			logger.info("####loadtest####按照全部的数量,完成售后费用总表创建,当前时间戳>>>>>>>>>>："+feetotalEnd+",用时:"+(feetotalEnd-feetotalStart)+"毫秒");
			/* 4.生成退款费用明细表*/
			//this.createAfterProdFee(afterOrderId, order, afterOrdOdProd,busiCode);
    	}else {
    		long prodStart=System.currentTimeMillis();
			logger.info("####loadtest####按照输入数量,开始售后订单商品明细创建，当前时间戳>>>>>>>>>>："+prodStart);
    		/* 5.创建售后商品明细信息*/
    		afterOrdOdProd =new OrdOdProd();
    		BeanUtils.copyProperties(afterOrdOdProd, ordOdProd);
    		afterTotalFee=afterOrdOdProd.getSalePrice()*prodSum;
    		afterOrdOdProd.setBuySum(prodSum);
    		afterOrdOdProd.setTotalFee(afterTotalFee);
    		Map<String, Integer> storageNum  = (Map<String, Integer>) JSON.parse(ordOdProd.getSkuStorageId());
    		Set<String> keySet = storageNum.keySet();
    		for (String key : keySet) {
    			storageNum.put(key, (int) prodSum);
			}
    		afterOrdOdProd.setSkuStorageId(JSON.toJSONString(storageNum));
    		/* 6.设置优惠券 消费积分 赠送积分比例*/
    		BigDecimal couponFeeRate=BigDecimal.valueOf(ordOdProd.getCouponFee()).divide(new BigDecimal(ordOdProd.getBuySum()),5,BigDecimal.ROUND_HALF_UP);
    		BigDecimal JfFeeRate=BigDecimal.valueOf(ordOdProd.getJfFee()).divide(new BigDecimal(ordOdProd.getBuySum()),5,BigDecimal.ROUND_HALF_UP);
    		BigDecimal giveJfRate=BigDecimal.valueOf(ordOdProd.getJf()).divide(new BigDecimal(ordOdProd.getBuySum()),5,BigDecimal.ROUND_HALF_UP);
    		BigDecimal operDiscountFeeRate=BigDecimal.valueOf(ordOdProd.getOperDiscountFee()).divide(new BigDecimal(ordOdProd.getBuySum()),5,BigDecimal.ROUND_HALF_UP);
    		BigDecimal discountFeeRate=BigDecimal.valueOf(ordOdProd.getDiscountFee()).divide(new BigDecimal(ordOdProd.getBuySum()),5,BigDecimal.ROUND_HALF_UP);
    		long afterCouponFee=(couponFeeRate.multiply(new BigDecimal(prodSum))).longValue();  //该商品数目下的退款优惠券
    		long afterJfFee=(JfFeeRate.multiply(new BigDecimal(prodSum))).longValue();      //该商品数目下的退款消费积分
    		long afterGiveJf=(giveJfRate.multiply(new BigDecimal(prodSum))).longValue();	//该商品数目下的退款赠送积分
    		long afterOperDiscountFee=(operDiscountFeeRate.multiply(new BigDecimal(prodSum))).longValue();	//该商品数目下的减免费用 
    		long discountFee=(discountFeeRate.multiply(new BigDecimal(prodSum))).longValue();	//该商品数目下的优惠金额
    		afterOrdOdProd.setCouponFee(afterCouponFee);
    		afterOrdOdProd.setJfFee(afterJfFee); 
    		afterOrdOdProd.setJf(afterGiveJf);
    		afterOrdOdProd.setOperDiscountFee(afterOperDiscountFee);
    		afterOrdOdProd.setDiscountFee(discountFee);
    		afterOrdOdProd.setAdjustFee(afterTotalFee-afterOrdOdProd.getDiscountFee());
    		afterOrdOdProd.setState(prodState); 
    		afterOrdOdProd.setUpdateTime(DateUtil.getSysDate());
    		afterOrdOdProd.setProdDetalId(SequenceUtil.createProdDetailId());
    		afterOrdOdProd.setOrderId(afterOrderId);
    		afterOrdOdProd.setProdDesc(request.getImageId()); //图片id
			afterOrdOdProd.setProdSn(request.getImageType()); //图片类型
    		afterOrdOdProd.setUpdateOperId(request.getOperId());
    		ordOdProdAtomSV.insertSelective(afterOrdOdProd);
    		long prodEnd=System.currentTimeMillis();
			logger.info("####loadtest####按照输入数量,完成售后订单商品明细创建，当前时间戳>>>>>>>>>>："+prodEnd+",用时:"+(prodEnd-prodStart)+"毫秒");
			long feetotalStart=System.currentTimeMillis();
			logger.info("####loadtest####按照输入数量,开始售后订单费用总表创建，当前时间戳>>>>>>>>>>："+feetotalStart);
    		/* 7.生成售后订单费用总表*/
    		odFeeTotal = ordOdFeeTotalAtomSV.selectByOrderId(order.getTenantId(), 
    				order.getOrderId());
    		rdOrdOdFeeTotal=new OrdOdFeeTotal();
    		BeanUtils.copyProperties(rdOrdOdFeeTotal, odFeeTotal);
    		rdOrdOdFeeTotal.setOrderId(afterOrderId);
    		rdOrdOdFeeTotal.setTenantId(request.getTenantId());
    		rdOrdOdFeeTotal.setPayFlag(OrdersConstants.OrdOdFeeTotal.payFlag.OUT);
    		rdOrdOdFeeTotal.setTotalFee(afterTotalFee);
    		rdOrdOdFeeTotal.setAdjustFee(afterTotalFee-afterOrdOdProd.getDiscountFee());
    		rdOrdOdFeeTotal.setPayFee(afterTotalFee-afterOrdOdProd.getDiscountFee());
    		rdOrdOdFeeTotal.setDiscountFee(afterOrdOdProd.getDiscountFee());
    		rdOrdOdFeeTotal.setOperDiscountFee(afterOrdOdProd.getOperDiscountFee());
    		rdOrdOdFeeTotal.setPaidFee(0);
    		rdOrdOdFeeTotal.setFreight(0);//运费暂不做运算
    		rdOrdOdFeeTotal.setUpdateTime(DateUtil.getSysDate());
    		ordOdFeeTotalAtomSV.insertSelective(rdOrdOdFeeTotal);
    		long feetotalEnd=System.currentTimeMillis();
			logger.info("####loadtest####按照输入数量,完成售后订单费用总表创建，当前时间戳>>>>>>>>>>："+feetotalEnd+",用时:"+(feetotalEnd-feetotalStart)+"毫秒");
    		/* 8.生成售后费用明细表*/
    		//this.createAfterProdFee(afterOrderId, order, afterOrdOdProd,busiCode);
    	}
    	if(!OrdersConstants.OrdOrder.BusiCode.EXCHANGE_ORDER.equals(busiCode)) {
    		long balacneStart=System.currentTimeMillis();
			logger.info("####loadtest####按照输入数量,开始售后订单支付机构表创建，当前时间戳>>>>>>>>>>："+balacneStart);
    		/* 9.生成售后订单支付机构接口*/
    		OrdBalacneIf ordBalacneIf = ordBalacneIfAtomSV.selectByOrderId(
    				order.getTenantId(), order.getOrderId());
    		if(ordBalacneIf==null) {
    			throw new BusinessException(ExceptCodeConstants.Special.NO_RESULT,
    					"订单支付机构信息不存在[订单id:"+order.getOrderId()+"]");
    		}
    		OrdBalacneIf balacneIf=new OrdBalacneIf();
    		BeanUtils.copyProperties(balacneIf, ordBalacneIf);
    		Long balacneIfId = SequenceUtil.createBalacneIfId();
    		balacneIf.setBalacneIfId(balacneIfId);
    		balacneIf.setOrderId(afterOrderId);
    		balacneIf.setPayStyle(odFeeTotal.getPayStyle());
    		balacneIf.setPayFee(afterTotalFee-afterOrdOdProd.getDiscountFee());
    		balacneIf.setCreateTime(DateUtil.getSysDate());
    		ordBalacneIfAtomSV.insertSelective(balacneIf);
    		long balacneEnd=System.currentTimeMillis();
			logger.info("####loadtest####按照输入数量,完成售后订单支付机构表创建，当前时间戳>>>>>>>>>>："+balacneEnd+",用时:"+(balacneEnd-balacneStart)+"毫秒");
    		
    	}
    	long updateStart=System.currentTimeMillis();
		logger.info("####loadtest####开始更新子订单商品明细表信息，当前时间戳>>>>>>>>>>："+updateStart);
    	//更新商品为售后标识
    	ordOdProd.setCusServiceFlag(OrdersConstants.OrdOrder.cusServiceFlag.YES);
		ordOdProdAtomSV.updateById(ordOdProd); 
		long updateEnd=System.currentTimeMillis();
		logger.info("####loadtest####完成更新子订单商品明细表信息,当前时间戳>>>>>>>>>>："+updateEnd+",用时:"+(updateEnd-updateStart)+"毫秒");
		return afterTotalFee;
    }
    
    
    /**
     * 	生成售后费用明细表
     */
    public void createAfterProdFee(long afterOrderId,OrdOrder order,OrdOdProd afterOrdOdProd,String busiCode) {
    	if(!OrdersConstants.OrdOrder.BusiCode.EXCHANGE_ORDER.equals(busiCode)) {
    		List<OrdOdFeeProd> feeProdList = ordOdFeeProdAtomSV.selectByOrderId(order.getOrderId());
    		for (OrdOdFeeProd ordOdFeeProd : feeProdList) {
    			OrdOdFeeProd subOrdOdFeeProd=new OrdOdFeeProd();
    			subOrdOdFeeProd.setOrderId(afterOrderId);
    			if(OrdersConstants.OrdOdFeeProd.PayStyle.JF.equals(ordOdFeeProd.getPayStyle())) {
    				subOrdOdFeeProd.setPayStyle(ordOdFeeProd.getPayStyle());
    				subOrdOdFeeProd.setPaidFee(afterOrdOdProd.getJfFee());
    				String rate = order.getPointRate();
    				if(!StringUtil.isBlank(rate)) {
    					String[] split = rate.split(":");
    					BigDecimal JfAmout=BigDecimal.valueOf(afterOrdOdProd.getJfFee()).divide(new BigDecimal(split[0]),
    							5,BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(split[1]),5,BigDecimal.ROUND_HALF_UP);
    					subOrdOdFeeProd.setJfAmount(JfAmout.multiply(new BigDecimal(1000)).longValue());//积分对应的金额,并元转厘
    				}
    			}else if(OrdersConstants.OrdOdFeeProd.PayStyle.COUPON.equals(ordOdFeeProd.getPayStyle())) {
    				subOrdOdFeeProd.setPayStyle(ordOdFeeProd.getPayStyle());
    				subOrdOdFeeProd.setPaidFee(afterOrdOdProd.getCouponFee());//优惠券
    			}else {
    				subOrdOdFeeProd.setPayStyle(ordOdFeeProd.getPayStyle());
    				subOrdOdFeeProd.setPaidFee(afterOrdOdProd.getTotalFee()-afterOrdOdProd.getDiscountFee());
    			}
    			ordOdFeeProdAtomSV.insertSelective(subOrdOdFeeProd);
    		}
    	}
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
		orderCreateRequest.setDescription("其它"); 
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
		orderCreateRequest.setDescription("其它"); 
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
}
