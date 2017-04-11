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
import com.ai.opt.sdk.components.ses.SESClientFactory;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.dubbo.util.HttpClientUtil;
import com.ai.opt.sdk.util.BeanUtils;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.util.DateUtil;
import com.ai.paas.ipaas.search.vo.Result;
import com.ai.paas.ipaas.search.vo.SearchCriteria;
import com.ai.platform.common.api.cache.interfaces.ICacheSV;
import com.ai.platform.common.api.cache.param.SysParam;
import com.ai.slp.order.api.aftersaleorder.param.OrderOFCBackRequest;
import com.ai.slp.order.api.aftersaleorder.param.OrderReturnRequest;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.constants.OrdersConstants.OrdOdStateChg;
import com.ai.slp.order.constants.SearchConstants;
import com.ai.slp.order.dao.mapper.bo.OrdBalacneIf;
import com.ai.slp.order.dao.mapper.bo.OrdOdFeeTotal;
import com.ai.slp.order.dao.mapper.bo.OrdOdProd;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.search.bo.OrdProdExtend;
import com.ai.slp.order.search.bo.OrderInfo;
import com.ai.slp.order.search.bo.ProdInfo;
import com.ai.slp.order.search.dto.SearchCriteriaStructure;
import com.ai.slp.order.service.atom.interfaces.IOrdBalacneIfAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdFeeTotalAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdProdAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.ai.slp.order.service.business.impl.search.OrderSearchImpl;
import com.ai.slp.order.service.business.interfaces.IOrderAfterSaleBusiSV;
import com.ai.slp.order.service.business.interfaces.search.IOrderSearch;
import com.ai.slp.order.util.InfoTranslateUtil;
import com.ai.slp.order.util.OrderStateChgUtil;
import com.ai.slp.order.util.SequenceUtil;
import com.ai.slp.order.vo.OFCAfterSaleOrderCreateRequest;
import com.ai.slp.order.vo.OrderAfterSaleApplyItemsVo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Service
@Transactional
public class OrderAfterSaleBusiSVImpl implements IOrderAfterSaleBusiSV {
	
	private static final Logger logger=LoggerFactory.getLogger(OrderAfterSaleBusiSVImpl.class);
	
	@Autowired
	private IOrdOrderAtomSV ordOrderAtomSV;
	@Autowired
	private IOrdOdProdAtomSV ordOdProdAtomSV;
	@Autowired
	private IOrdOdFeeTotalAtomSV ordOdFeeTotalAtomSV;
	@Autowired
	private IOrdBalacneIfAtomSV ordBalacneIfAtomSV;
	
	/**
	 * 订单退货申请
	 */
	@Override
	public void back(OrderReturnRequest request,OrdOrder order,
			OrdOdProd ordOdProd) throws BusinessException, SystemException {
		long backOrderId=SequenceUtil.createOrderId();
		Timestamp sysDate=DateUtil.getSysDate();
		long backTotalFee = 0;
		/* 1.创建订单售后信息*/
		backTotalFee = this.createAfterOrderInfo(order, request,
				OrdersConstants.OrdOrder.BusiCode.UNSUBSCRIBE_ORDER, 
				ordOdProd, OrdersConstants.OrdOdProd.State.RETURN,backOrderId,sysDate);
		//TODO
		/* 2.组装退货申请单(OFC)*/
		if(OrdersConstants.OrdOrder.Flag.OFC_ACTUAL_TIME.equals(order.getFlag())) {
			String params = getOFCAfterSaleOrderCreateParam(order,backOrderId,ordOdProd, sysDate, 
					backTotalFee, request.getProdSum(), OrdersConstants.OFCApplyType.BACK);
			Map<String, String> header=new HashMap<String, String>(); 
			header.put("appkey", OrdersConstants.OFC_APPKEY);
			//发送Post请求,并返回信息
			try {
				String strData = HttpClientUtil.sendPost(OrdersConstants.OFC_RETURN_CREATE_URL, params, header);
				JSONObject object = JSON.parseObject(strData);
				boolean val = object.getBooleanValue("IsValid");
			} catch (IOException | URISyntaxException e) {
				logger.error(e.getMessage());
				throw new SystemException("", "OFC同步出现异常");
			}
		}
	}

	//订单换货申请
	@Override
	public void exchange(OrderReturnRequest request,OrdOrder order,
			OrdOdProd ordOdProd) throws BusinessException, SystemException {
		long exchangeOrderId=SequenceUtil.createOrderId();
		Timestamp sysDate=DateUtil.getSysDate();
		/* 1.创建订单售后信息*/
		this.createAfterOrderInfo(order, request,
				OrdersConstants.OrdOrder.BusiCode.EXCHANGE_ORDER, 
				ordOdProd, OrdersConstants.OrdOdProd.State.EXCHANGE,exchangeOrderId,sysDate);
	}

	//订单退款申请
	@Override
	public void refund(OrderReturnRequest request,OrdOrder order,
			OrdOdProd ordOdProd) throws BusinessException, SystemException {
		long refundOrderId=SequenceUtil.createOrderId();
		Timestamp sysDate=DateUtil.getSysDate();
		/* 1.创建订单售后信息*/
		long refundTotalFee = this.createAfterOrderInfo(order, request,
				OrdersConstants.OrdOrder.BusiCode.CANCEL_ORDER, 
				ordOdProd, OrdersConstants.OrdOdProd.State.RETURN,refundOrderId,sysDate);
		//TODO
		/* 2.组装退款申请单(OFC)*/ 
		if(OrdersConstants.OrdOrder.Flag.OFC_ACTUAL_TIME.equals(order.getFlag())) {
			String params = getOFCAfterSaleOrderCreateParam(order,refundOrderId, ordOdProd, sysDate, 
					refundTotalFee, ordOdProd.getBuySum(), OrdersConstants.OFCApplyType.REFUND);
			Map<String, String> header=new HashMap<String, String>(); 
			header.put("appkey", OrdersConstants.OFC_APPKEY);
			//发送Post请求,并返回信息
			try {
				String strData = HttpClientUtil.sendPost(OrdersConstants.OFC_RETURN_CREATE_URL, params, header);
				JSONObject object = JSON.parseObject(strData);
				boolean val = object.getBooleanValue("IsValid");
			} catch (IOException | URISyntaxException e) {
				logger.error(e.getMessage());
				throw new SystemException("", "OFC同步出现异常");
			}
		}
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
		//异步  写入订单状态变化轨迹表
		OrderStateChgUtil.trailProcess(ordOrder.getOrderId(), ordOrder.getTenantId(), orgState, 
					newState,chgDesc,null, ordOrder.getOperId(), null,sysDate);
    }
    
    /**
     * 创建售后订单信息
     */
    public long createAfterOrderInfo(OrdOrder order,OrderReturnRequest request,
    		String busiCode,OrdOdProd ordOdProd,String prodState,
    		long afterOrderId,Timestamp sysDate) {
    	/* 1.创建售后订单*/
		OrdOrder afterOrder=new OrdOrder();
		BeanUtils.copyProperties(afterOrder, order);
		afterOrder.setBusiCode(busiCode); 
		afterOrder.setOrderId(afterOrderId);
		afterOrder.setOrderTime(sysDate);
		afterOrder.setOperId(request.getOperId());
		afterOrder.setCusServiceFlag(OrdersConstants.OrdOrder.cusServiceFlag.YES);//设置售后标识 Y
		afterOrder.setOrigOrderId(order.getOrderId());
		afterOrder.setRemark(request.getAfterSaleReason()); //退款理由 
		this.insertOrderState(sysDate, afterOrder);
		long afterTotalFee=0;
		String orderState = order.getState();
		//2.创建售后订单商品
		OrdOdProd afterOrdOdProd =new OrdOdProd();
		BeanUtils.copyProperties(afterOrdOdProd, ordOdProd);
		//2.1 待配货,待出库,退款订单的情况下按照全部数量
    	if(OrdersConstants.OrdOrder.State.WAIT_DISTRIBUTION.equals(orderState)||
				OrdersConstants.OrdOrder.State.WAIT_DELIVERY.equals(orderState)||
				OrdersConstants.OrdOrder.State.WAIT_SEND.equals(orderState)||
				OrdersConstants.OrdOrder.BusiCode.CANCEL_ORDER.equals(busiCode)) {
			afterTotalFee = afterOrdOdProd.getTotalFee();
		//2.2 订单已发货之后进行售后的情况下
    	}else {
			long prodSum = request.getProdSum();
    		afterTotalFee=afterOrdOdProd.getSalePrice()*prodSum;
    		afterOrdOdProd.setBuySum(prodSum);
    		afterOrdOdProd.setTotalFee(afterTotalFee);
    		Map<String, Integer> storageNum  = (Map<String, Integer>) JSON.parse(ordOdProd.getSkuStorageId());
    		Set<String> keySet = storageNum.keySet();
    		/* sku对应的商品数量*/
    		for (String key : keySet) {
    			storageNum.put(key, (int) prodSum);
			}
    		afterOrdOdProd.setSkuStorageId(JSON.toJSONString(storageNum));
    		/* 设置优惠券 消费积分 赠送积分比例*/
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
    		long adjustFee=afterTotalFee-afterOrdOdProd.getDiscountFee();
    		afterOrdOdProd.setAdjustFee(adjustFee<0?0:adjustFee);
    	}
    	afterOrdOdProd.setState(prodState); 
		afterOrdOdProd.setUpdateTime(sysDate);
		afterOrdOdProd.setProdDetalId(SequenceUtil.createProdDetailId());
		afterOrdOdProd.setOrderId(afterOrderId);
		afterOrdOdProd.setProdDesc(request.getImageId()); //图片id
		afterOrdOdProd.setProdSn(request.getImageType()); //图片类型
		afterOrdOdProd.setUpdateOperId(request.getOperId());
		ordOdProdAtomSV.insertSelective(afterOrdOdProd);
    	/* 3.生成售后订单费用总表*/
		OrdOdFeeTotal odFeeTotal = ordOdFeeTotalAtomSV.selectByOrderId(order.getTenantId(), 
    			order.getOrderId());
		OrdOdFeeTotal rdOrdOdFeeTotal=new OrdOdFeeTotal();
    	BeanUtils.copyProperties(rdOrdOdFeeTotal, odFeeTotal);
    	rdOrdOdFeeTotal.setTotalFee(afterTotalFee);
    	rdOrdOdFeeTotal.setAdjustFee(afterOrdOdProd.getAdjustFee());
    	rdOrdOdFeeTotal.setPayFee(afterOrdOdProd.getAdjustFee());
    	rdOrdOdFeeTotal.setDiscountFee(afterOrdOdProd.getDiscountFee());
    	rdOrdOdFeeTotal.setOperDiscountFee(afterOrdOdProd.getOperDiscountFee());
    	rdOrdOdFeeTotal.setTotalJf(afterOrdOdProd.getJf());
    	rdOrdOdFeeTotal.setOrderId(afterOrderId);
    	rdOrdOdFeeTotal.setTenantId(request.getTenantId());
    	rdOrdOdFeeTotal.setPayFlag(OrdersConstants.OrdOdFeeTotal.payFlag.OUT);
    	rdOrdOdFeeTotal.setPaidFee(0);
    	rdOrdOdFeeTotal.setFreight(0);//运费暂不做运算
    	rdOrdOdFeeTotal.setUpdateTime(sysDate);
    	ordOdFeeTotalAtomSV.insertSelective(rdOrdOdFeeTotal);
    	//
    	if(!OrdersConstants.OrdOrder.BusiCode.EXCHANGE_ORDER.equals(busiCode)) {
    		/* 4.生成售后订单支付机构接口*/
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
    		balacneIf.setPayFee(afterOrdOdProd.getAdjustFee());
    		balacneIf.setCreateTime(sysDate);
    		ordBalacneIfAtomSV.insertSelective(balacneIf);
    	}
    	/* 5.更新商品为售后标识*/
    	ordOdProd.setCusServiceFlag(OrdersConstants.OrdOrder.cusServiceFlag.YES);
		ordOdProdAtomSV.updateCusServiceFlag(ordOdProd);
		/* 6.刷新搜索引擎数据*/
		this.refreshAfterData(afterOrder,afterOrdOdProd); 
		return afterTotalFee;
    }
    
    /**
     * 组装退换货申请单创建参数(OFC)
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
    
    
    //ofc售后订单状态回传
  	@Override
  	public void backStateOFC(OrderOFCBackRequest request,OrdOrder ordOrder) 
  			throws BusinessException, SystemException {
  		ordOrder.setState(request.getState());
  		ordOrder.setRemark(request.getReasonDesc());
  		//更新数据
  		ordOrderAtomSV.updateOFCOrder(ordOrder);
  	}
  	
  	//刷新售后搜索数据
  	private void  refreshAfterData(OrdOrder afterOrder,OrdOdProd afterOrdOdProd) {
  		ICacheSV iCacheSV = DubboConsumerFactory.getService(ICacheSV.class);
  		IOrderSearch orderSearch = new OrderSearchImpl();
		List<SearchCriteria> orderSearchCriteria = SearchCriteriaStructure.
				commonConditionsByOrderId(afterOrder.getParentOrderId());
		Result<OrderInfo> result = orderSearch.search(orderSearchCriteria, 0, 1, null);
		List<OrderInfo> ordList = result.getContents();
		if(CollectionUtil.isEmpty(ordList)) {
			logger.info("搜索引擎无数据! 父订单id为:"+afterOrder.getParentOrderId());
			throw new BusinessException("搜索引擎无数据! 父订单id为:"+afterOrder.getParentOrderId());
		}
		OrderInfo orderInfo = ordList.get(0);
		List<OrdProdExtend> ordextendes = orderInfo.getOrdextendes();
		OrdProdExtend prodExtend=new OrdProdExtend();
		List<ProdInfo> prodInfos=new ArrayList<ProdInfo>();
		prodExtend.setState(afterOrder.getState());
		//订单状态翻译
		SysParam sysParamState = InfoTranslateUtil.translateInfo(afterOrder.getTenantId(),
				"ORD_ORDER", "STATE",afterOrder.getState(), iCacheSV);
		prodExtend.setStatename(sysParamState == null ? "" : sysParamState.getColumnDesc());
		prodExtend.setBusicode(afterOrder.getBusiCode());
		prodExtend.setParentorderid(afterOrder.getParentOrderId());
		prodExtend.setOrderid(afterOrder.getOrderId());
		prodExtend.setRouteid(afterOrder.getRouteId());
		
		ProdInfo prodInfo=new ProdInfo();
		prodInfo.setBuysum(afterOrdOdProd.getBuySum());
		prodInfo.setProdname(afterOrdOdProd.getProdName());
		prodInfos.add(prodInfo);
		
		prodExtend.setProdsize(prodInfos.size());
		prodExtend.setProdinfos(prodInfos);
		ordextendes.add(prodExtend);
		
		orderInfo.setTotalprodsize(orderInfo.getTotalprodsize()+prodInfos.size());
		orderInfo.setOrdextendes(ordextendes);
		ordList.add(orderInfo);
		SESClientFactory.getSearchClient(SearchConstants.SearchNameSpace).bulkInsert(ordList);
  	}
}
