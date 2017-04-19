package com.ai.slp.order.service.business.impl;

import java.math.BigDecimal;
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
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.BeanUtils;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.util.DateUtil;
import com.ai.platform.common.api.cache.interfaces.ICacheSV;
import com.ai.slp.order.api.delivergoods.param.DeliverGoodsPrintInfoVo;
import com.ai.slp.order.api.delivergoods.param.DeliverGoodsPrintInfosRequest;
import com.ai.slp.order.api.delivergoods.param.DeliverGoodsPrintRequest;
import com.ai.slp.order.api.delivergoods.param.DeliverGoodsPrintResponse;
import com.ai.slp.order.api.delivergoods.param.DeliverGoodsPrintVo;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.constants.OrdersConstants.OrdOdStateChg;
import com.ai.slp.order.dao.mapper.bo.DeliverInfoProd;
import com.ai.slp.order.dao.mapper.bo.OrdOdDeliverInfo;
import com.ai.slp.order.dao.mapper.bo.OrdOdLogistics;
import com.ai.slp.order.dao.mapper.bo.OrdOdProd;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.service.atom.interfaces.IDeliveryOrderPrintAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdLogisticsAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdProdAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.ai.slp.order.service.business.interfaces.IDeliverGoodsPrintBusiSV;
import com.ai.slp.order.service.business.interfaces.search.IOrderIndexBusiSV;
import com.ai.slp.order.util.OrderStateChgUtil;
import com.ai.slp.order.util.SequenceUtil;
import com.alibaba.fastjson.JSON;

@Service
@Transactional
public class DeliverGoodsPrintBusiSVImpl implements IDeliverGoodsPrintBusiSV {
	
	private static final Logger logger=LoggerFactory.getLogger(DeliveryOrderPrintBusiSVImpl.class);
	@Autowired
	private IOrdOrderAtomSV ordOrderAtomSV;
	@Autowired
	private IOrdOdLogisticsAtomSV ordOdLogisticsAtomSV;
	@Autowired
	private IDeliveryOrderPrintAtomSV deliveryOrderPrintAtomSV;
	@Autowired
	private IOrdOdProdAtomSV ordOdProdAtomSV;
	@Autowired
	private IOrderIndexBusiSV orderIndexBusiSV;
	
	//发货单打印查看
	@Override
	public DeliverGoodsPrintResponse deliverGoodsQuery(DeliverGoodsPrintRequest request,
			List<OrdOdDeliverInfo> deliverInfos,OrdOrder order) throws BusinessException, SystemException {
		DeliverGoodsPrintResponse response=new DeliverGoodsPrintResponse();
		Map<String,Long> prodSkuMap=new HashMap<String,Long>();
		/* 获取子订单下的售后订单商品数量*/
		prodSkuMap = this.getAfterOrderInfos(order,prodSkuMap);
		/* 获取子订单下的合并订单*/
		if(order.getBatchNo()!=0) {
			List<OrdOrder> mergeOrders = ordOrderAtomSV.selectByBatchNo(order.getOrderId(),
					order.getTenantId(), order.getBatchNo());
			for (OrdOrder mergeOrder : mergeOrders) {
				prodSkuMap = this.getAfterOrderInfos(mergeOrder,prodSkuMap);
				//TODO
				List<OrdOdDeliverInfo> merDeliverInfos =deliveryOrderPrintAtomSV.
						selectDeliverByPrintInfoByHor(mergeOrder.getOrderId(),
						OrdersConstants.OrdOdDeliverInfo.printInfo.ONE);
				deliverInfos.addAll(merDeliverInfos);
			}
		}
		List<DeliverGoodsPrintVo> list=new ArrayList<DeliverGoodsPrintVo>();
		long sum=0;
		for (OrdOdDeliverInfo ordOdDeliverInfo : deliverInfos) {
			/* 查询提发货明细信息*/
			List<DeliverInfoProd> deliverInfoProds =deliveryOrderPrintAtomSV.selectDeliverInfoProd(
					ordOdDeliverInfo.getDeliverInfoId());
			if(!CollectionUtil.isEmpty(deliverInfoProds)) {
				DeliverInfoProd deliverInfoProd = deliverInfoProds.get(0);
				DeliverGoodsPrintVo invoicePrintVo=new DeliverGoodsPrintVo();
				invoicePrintVo.setSkuId(deliverInfoProd.getSkuId());
				invoicePrintVo.setProdName(deliverInfoProd.getProdName());
				invoicePrintVo.setExtendInfo(deliverInfoProd.getExtendInfo());
				if(!prodSkuMap.isEmpty()) {
					Set<String> keySet = prodSkuMap.keySet();
					String skuId = deliverInfoProd.getSkuId();
					if(keySet.contains(skuId)) {
						Long afterBuySum = prodSkuMap.get(skuId);
						long remainSum=deliverInfoProd.getBuySum()-afterBuySum;
						if(remainSum==0) {
							continue; //执行下一个循环
						}
						sum+=remainSum;
						invoicePrintVo.setBuySum(remainSum);
						this.invoiceInfos(deliverInfoProd, invoicePrintVo, ordOdDeliverInfo, list);
					}else {
						invoicePrintVo.setBuySum(deliverInfoProd.getBuySum());
						sum+=deliverInfoProd.getBuySum();
						this.invoiceInfos(deliverInfoProd, invoicePrintVo, ordOdDeliverInfo, list);
					}
				}else {
					invoicePrintVo.setBuySum(deliverInfoProd.getBuySum());
					sum+=deliverInfoProd.getBuySum();
					this.invoiceInfos(deliverInfoProd, invoicePrintVo, ordOdDeliverInfo, list);
				}
			}
		 }
		/* 查询订单配送信息 父订单对应配送信息*/
		OrdOdLogistics ordOdLogistics = ordOdLogisticsAtomSV.selectByOrd(order.getTenantId(), order.getOrderId());
		ICacheSV iCacheSv = DubboConsumerFactory.getService("iCacheSV");
		response.setRouteId(order.getRouteId());
		response.setContactName(ordOdLogistics.getContactName());
	    response.setContactTel(ordOdLogistics.getContactTel());
	    response.setProvinceCode(ordOdLogistics.getProvinceCode()==null?"":iCacheSv.getAreaName(ordOdLogistics.getProvinceCode()));
	    response.setCityCode(ordOdLogistics.getCityCode()==null?"":iCacheSv.getAreaName(ordOdLogistics.getCityCode()));
	    response.setCountyCode(ordOdLogistics.getCountyCode()==null?"":iCacheSv.getAreaName(ordOdLogistics.getCountyCode()));
	    response.setAreaCode(ordOdLogistics.getAreaCode()==null?"":iCacheSv.getAreaName(ordOdLogistics.getAreaCode()));
	    response.setAddress(ordOdLogistics.getAddress());
		response.setOrderId(request.getOrderId());
		response.setExpressOddNumber(ordOdLogistics.getExpressOddNumber());
		response.setInvoiceDate(DateUtil.getDate());
		response.setSum(sum);
		response.setInvoicePrintVos(list);
		return response;
	}
	
	//发货单打印
	@Override
	public void deliverGoodsPrint(DeliverGoodsPrintInfosRequest request)
			throws BusinessException, SystemException {
		List<DeliverGoodsPrintInfoVo> invoicePrintVos = request.getInvoicePrintVos();
		for (DeliverGoodsPrintInfoVo invoicePrintVo : invoicePrintVos) {
			List<Long> allOrderIds = invoicePrintVo.getHorOrderId();
		  	allOrderIds.add(request.getOrderId());
		  	for (Long mergeId : allOrderIds) { 
		  		OrdOrder ordOrder = ordOrderAtomSV.selectByOrderId(request.getTenantId(), mergeId);
				if(ordOrder==null) {
					throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, 
							"未能查询到指定的订单信息[订单id:"+ mergeId+"]");
				}else if(OrdersConstants.OrdOrder.State.COMPLETED.equals(ordOrder.getState())) {
					continue;
				}
		  		List<Long> temp = new ArrayList<Long>();
				temp.addAll(allOrderIds);
				temp.remove(mergeId);
				Long invoiceInfoId = SequenceUtil.createdeliverInfoId();
				OrdOdDeliverInfo invoiceInfo=new OrdOdDeliverInfo();
				invoiceInfo.setDeliverInfoId(invoiceInfoId);
				invoiceInfo.setHorOrderId(JSON.toJSONString(temp));
				invoiceInfo.setOrderId(mergeId);
				invoiceInfo.setPrintInfo(OrdersConstants.OrdOdDeliverInfo.printInfo.TWO);
				invoiceInfo.setUpdateTime(DateUtil.getSysDate());
				deliveryOrderPrintAtomSV.insertSelective(invoiceInfo);
				DeliverInfoProd deliverInfoProd=new DeliverInfoProd();
				BeanUtils.copyProperties(deliverInfoProd, invoicePrintVo);
				deliverInfoProd.setDeliverInfoId(invoiceInfoId);
				deliveryOrderPrintAtomSV.insertSelective(deliverInfoProd);
				/* 更新合并订单状态并写入订单状态变化轨迹*/
				this.updateOrderState(ordOrder,mergeId, request.getTenantId());
				temp.clear();
			}
		}
	}
	
	 private void updateOrderState(OrdOrder ordOrder,Long mergeId,String tenantId) {
		Timestamp sysDate = DateUtil.getSysDate();
		String orgState = ordOrder.getState();
		if(OrdersConstants.OrdOrder.State.WAIT_SEND.equals(orgState)) {
			return;
		}
		String state1 = OrdersConstants.OrdOrder.State.INVOICE_FINISH_PRINT;
	    String state2 = OrdersConstants.OrdOrder.State.FINISH_DELIVERY;
	    String newState = OrdersConstants.OrdOrder.State.WAIT_SEND;
		ordOrder.setState(newState);
		ordOrder.setStateChgTime(sysDate);
		ordOrderAtomSV.updateOrderState(ordOrder);
		
		//写入搜索引擎
		orderIndexBusiSV.refreshStateData(ordOrder,null);
		//异步 写入订单状态变化轨迹表
		OrderStateChgUtil.trailProcess(ordOrder.getOrderId(), ordOrder.getTenantId(), orgState, state1,
                OrdOdStateChg.ChgDesc.INVOICE_ORDER_TO_PRINT, null, null, null, sysDate);
		OrderStateChgUtil.trailProcess(ordOrder.getOrderId(), ordOrder.getTenantId(), state1, state2,
                OrdOdStateChg.ChgDesc.ORDER_TO_FINISH_LOGISTICS_DELIVERY, null, null, null, sysDate);
		OrderStateChgUtil.trailProcess(ordOrder.getOrderId(), ordOrder.getTenantId(), state2, newState,
                OrdOdStateChg.ChgDesc.ORDER_TO_WAIT_SEND, null, null, null, sysDate);
	 }
	 
	 
	/**
	 * 获取订单下的商品信息
	 */
	private List<OrdOdProd> getOrdOdProds(String tenantId,long orderId) {
		List<OrdOdProd> ordOdProds = ordOdProdAtomSV.selectByOrd(tenantId, orderId);
		if(CollectionUtil.isEmpty(ordOdProds)) {
			logger.error("未能查询到指定的订单商品明细信息[订单id:"+orderId+"]");
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, 
					"未能查询到指定的订单商品明细信息[订单id:"+orderId+"]");
		}
		return ordOdProds;
	}
		
		
		
	  /**
	   * 获取商品对应的售后订单状态
	   */
	  public List<OrdOrder> createAfterOrder(OrdOdProd ordOdProd) {
		  	List<OrdOrder> orderList=new ArrayList<>();
		  	//获取售后订单
			List<OrdOrder> ordOrderList = ordOrderAtomSV.selectSaleOrder(
					ordOdProd.getTenantId(), ordOdProd.getOrderId());;
			if(CollectionUtil.isEmpty(ordOrderList)) {
				logger.error("没有查询到相应的售后订单详情[原始订单id:"+ordOdProd.getOrderId()+"]");
				throw new BusinessException(ExceptCodeConstants.Special.NO_RESULT, 
						"没有查询到相应的售后订单详情[原始订单id:"+ordOdProd.getOrderId()+"]");
			}
			for (OrdOrder ordOrder : ordOrderList) {
				//获取skuid对应的售后商品信息
				List<OrdOdProd> prodList = ordOdProdAtomSV.selectSaleProd(ordOrder.getTenantId(), 
						ordOrder.getOrderId(), ordOdProd.getSkuId());
				if(!CollectionUtil.isEmpty(prodList)) {
					OrdOdProd prod = prodList.get(0);
					OrdOrder order = ordOrderAtomSV.selectByOrderId(prod.getTenantId(),
							prod.getOrderId());
					orderList.add(order);
				}
			}
		return orderList;
	  }
		  
	  /**
	   * 获取售后商品的信息
	 * @return 
	   */
	  public Map<String, Long> getAfterOrderInfos(OrdOrder order,Map<String,Long> prodSkuMap) {
		  boolean flag=false;
		  List<OrdOdProd> ordOdProds = this.getOrdOdProds(order.getTenantId(),order.getOrderId());
		  for (OrdOdProd ordOdProd : ordOdProds) {
			  if(OrdersConstants.OrdOrder.cusServiceFlag.YES.equals(ordOdProd.getCusServiceFlag())) {
				  //判断该订单下的商品是否存在售后商品
				  flag=true; 
				  /* 判断该商品对应的售后订单状态*/
				  List<OrdOrder> ordOrderList = this.createAfterOrder(ordOdProd);
				  for (OrdOrder ordOrder : ordOrderList) {
					  if(ordOrderList.size()>1) { //多个售后订单 可能存在多个第一次审核失败的情况
						  if(OrdersConstants.OrdOrder.State.AUDIT_FAILURE.equals(ordOrder.getState())) {
							  continue;
						  }
					  }
					  if(!(OrdersConstants.OrdOrder.State.FINISH_REFUND.equals(ordOrder.getState())||
							  OrdersConstants.OrdOrder.State.REFUND_AUDIT.equals(ordOrder.getState())||
							  OrdersConstants.OrdOrder.State.EXCHANGE_AUDIT.equals(ordOrder.getState())||
							  OrdersConstants.OrdOrder.State.AUDIT_AGAIN_FAILURE.equals(ordOrder.getState()))) {
						  //该商品为售后标识 不可打印
						  throw new BusinessException("", "订单下商品处于售后未完成状态,不可打印");
					  }
				  }
			  }
		  }
		  if(flag) {
			  List<OrdOrder> ordOrderList =ordOrderAtomSV.selectNotAuditFailureOrd(order.getTenantId(), 
					  order.getOrderId(), OrdersConstants.OrdOrder.State.AUDIT_FAILURE);
			  if(!CollectionUtil.isEmpty(ordOrderList)) {
				  for (OrdOrder ordOrder : ordOrderList) {
					  //退费单 审核失败(213)情况下
					  if(OrdersConstants.OrdOrder.BusiCode.CANCEL_ORDER.equals(ordOrder.getBusiCode())&&
							  OrdersConstants.OrdOrder.State.AUDIT_AGAIN_FAILURE.equals(ordOrder.getState())) {
						  continue;
					  }
					  List<OrdOdProd> prodList = ordOdProdAtomSV.selectByOrd(ordOrder.getTenantId(), 
							  ordOrder.getOrderId());
					  if(!CollectionUtil.isEmpty(prodList)) {
						  OrdOdProd ordOdProd = prodList.get(0);
						  long buySum = ordOdProd.getBuySum();
						  String skuId = ordOdProd.getSkuId();
						  if(!prodSkuMap.isEmpty()) {
							  Set<String> keySet = prodSkuMap.keySet();
							  if(keySet.contains(skuId)) {
								  Long mergeBuySum = prodSkuMap.get(skuId)+buySum;
								  prodSkuMap.put(skuId, mergeBuySum);
							  }else {
								  prodSkuMap.put(skuId, buySum);
							  }
						  }else {
							  prodSkuMap.put(ordOdProd.getSkuId(), buySum);
						  }
					  }
				  }
			  }
		  }
		  return prodSkuMap;
	}
		  
		  
	/**
	 * 封装发货单打印信息
	 */
	private void invoiceInfos(DeliverInfoProd deliverInfoProd,DeliverGoodsPrintVo invoicePrintVo
			,OrdOdDeliverInfo ordOdDeliverInfo,List<DeliverGoodsPrintVo> list){ 
		if(deliverInfoProd.getSalePrice() == 0){
			invoicePrintVo.setSalePrice("0.00");;
	    }else {
	    	BigDecimal balance = BigDecimal.valueOf(deliverInfoProd.getSalePrice()).divide(new BigDecimal(1000L),2,BigDecimal.ROUND_HALF_UP);
	    	invoicePrintVo.setSalePrice(balance.toString());//厘转元
	    }
		List<Long> parseLong = (List<Long>) JSON.parse(ordOdDeliverInfo.getHorOrderId());
		invoicePrintVo.setHorOrderId(parseLong);
		list.add(invoicePrintVo);
	}
}
