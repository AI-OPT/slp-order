package com.ai.slp.order.service.business.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.sdk.components.mcs.MCSClientFactory;
import com.ai.opt.sdk.components.ses.SESClientFactory;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.util.DateUtil;
import com.ai.paas.ipaas.mcs.interfaces.ICacheClient;
import com.ai.paas.ipaas.search.common.JsonBuilder;
import com.ai.platform.common.api.cache.interfaces.ICacheSV;
import com.ai.platform.common.api.cache.param.SysParam;
import com.ai.slp.order.api.deliveryorderprint.param.DeliveryOrderPrintInfosRequest;
import com.ai.slp.order.api.deliveryorderprint.param.DeliveryOrderPrintRequest;
import com.ai.slp.order.api.deliveryorderprint.param.DeliveryOrderPrintResponse;
import com.ai.slp.order.api.deliveryorderprint.param.DeliveryOrderQueryResponse;
import com.ai.slp.order.api.deliveryorderprint.param.DeliveryProdPrintVo;
import com.ai.slp.order.constants.MonitorCoonstants;
import com.ai.slp.order.constants.OrdRuleConstants;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.constants.SearchConstants;
import com.ai.slp.order.constants.SearchFieldConfConstants;
import com.ai.slp.order.constants.OrdersConstants.OrdOdStateChg;
import com.ai.slp.order.dao.mapper.attach.OrdOrderProdAttach;
import com.ai.slp.order.dao.mapper.bo.DeliverInfoProd;
import com.ai.slp.order.dao.mapper.bo.OrdOdDeliverInfo;
import com.ai.slp.order.dao.mapper.bo.OrdOdLogistics;
import com.ai.slp.order.dao.mapper.bo.OrdOdProd;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.dao.mapper.bo.OrdRule;
import com.ai.slp.order.service.atom.interfaces.IDeliveryOrderPrintAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdLogisticsAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdProdAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdRuleAtomSV;
import com.ai.slp.order.service.business.interfaces.IDeliveryOrderPrintBusiSV;
import com.ai.slp.order.service.business.interfaces.IOrderFrameCoreSV;
import com.ai.slp.order.util.InfoTranslateUtil;
import com.ai.slp.order.util.SequenceUtil;
import com.ai.slp.order.util.ValidateUtils;
import com.alibaba.fastjson.JSON;

@Service
@Transactional
public class DeliveryOrderPrintBusiSVImpl implements IDeliveryOrderPrintBusiSV{
	
	private static final Logger logger=LoggerFactory.getLogger(DeliveryOrderPrintBusiSVImpl.class);

	@Autowired
	private IOrdOrderAtomSV ordOrderAtomSV;
	
	@Autowired
	private IOrdOdProdAtomSV ordOdProdAtomSV;
	
	@Autowired
	private IOrdOdLogisticsAtomSV ordOdLogisticsAtomSV;
	
	@Autowired
	private IDeliveryOrderPrintAtomSV deliveryOrderPrintAtomSV;
	
	@Autowired
	private IOrdRuleAtomSV ordRuleAtomSV;
	
	@Autowired
	private IOrderFrameCoreSV orderFrameCoreSV;
	
	//提货单查看
	@Override
	public DeliveryOrderQueryResponse query(DeliveryOrderPrintRequest request) {
		/* 参数校验*/
		OrdOrder order = this.checkParamAndQueryOrder(request);
		ICacheClient cacheClient = MCSClientFactory.getCacheClient(MonitorCoonstants.MONITOR_CACHE_NAMESPACE);
		DeliveryOrderQueryResponse response=new DeliveryOrderQueryResponse();
		/* 获取订单下的商品信息*/
		List<OrdOdProd> ordOdProds = ordOdProdAtomSV.selectByOrd(request.getTenantId(), request.getOrderId());
		if(CollectionUtil.isEmpty(ordOdProds)) {
			logger.warn("未能查询到指定的订单商品明细信息[订单id:"+request.getOrderId()+"]");
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, 
					"未能查询到指定的订单商品明细信息[订单id:"+request.getOrderId()+"]");
		}
		for (OrdOdProd ordOdProd : ordOdProds) {
			if(OrdersConstants.OrdOrder.cusServiceFlag.YES.equals(ordOdProd.getCusServiceFlag())) {
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
						response.setMark(OrdersConstants.printMark.NOT_PRINT);
						return response;
					}
				}
			}
		}
		/* 订单规则*/
		OrdRule ordRule = ordRuleAtomSV.getOrdRule(OrdRuleConstants.MERGE_ORDER_SETTING_ID);
		if(ordRule==null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, 
					"未能查询到指定的订单规则信息[订单规则id:"+OrdRuleConstants.MERGE_ORDER_SETTING_ID+"]");
		}
		/* 根据订单规则获取合并前后时间*/
		Timestamp timeBefore = getOrderListInTime(-ordRule.getMonitorTime(),order.getOrderTime(),ordRule.getTimeType());
		Timestamp timeAfter = getOrderListInTime(ordRule.getMonitorTime(),order.getOrderTime(),ordRule.getTimeType());
		//获取地址信息
		OrdOdLogistics odLogistics = ordOdLogisticsAtomSV.selectByOrd(order.getTenantId(), order.getOrderId());
  		if(odLogistics==null) {
  			logger.warn("未能查询到指定的订单配送信息[订单id:"+order.getOrderId()+"]");
  			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL,
  					"未能查询到指定的订单配送信息[订单id:"+order.getOrderId()+"]");
  		}
		String addressInfo = getAddressInfo(odLogistics);
		/* 组装订单商品信息集合*/
		List<OrdOrderProdAttach> originalAttachs = createOriginalAttachs(ordOdProds);
		List<Long> orderList =null;
		for (OrdOdProd ordOdProd : ordOdProds) {
			/* 多表多条件查询*/
			List<OrdOrderProdAttach> orderProdAttachs = deliveryOrderPrintAtomSV.query(request.getUserId(),
					request.getTenantId(),ordOdProd.getSkuId(),ordOdProd.getRouteId(),ordOdProd.getOrderId(),
					OrdersConstants.OrdOrder.State.WAIT_DISTRIBUTION,timeBefore,timeAfter,
					OrdersConstants.OrdOrder.cusServiceFlag.NO);
			if(!CollectionUtil.isEmpty(orderProdAttachs)) {
				/* 筛选不符合合并规则的订单*/
				orderList = this.judgeOrder(originalAttachs,orderProdAttachs,orderList,addressInfo);
				if(!CollectionUtil.isEmpty(orderProdAttachs)) {
					response.setMark(OrdersConstants.printMark.CAN_MERGE);
					//存入缓存中 
					cacheClient.set(String.valueOf(request.getOrderId()), JSON.toJSONString(orderProdAttachs));
					break;
				}else {
					response.setMark(OrdersConstants.printMark.NOT_MERGE);
				}
			}else {
				response.setMark(OrdersConstants.printMark.NOT_MERGE);
			}
		}
		return response;
	}
	
	//提货单展示
	@Override
	public DeliveryOrderPrintResponse display(DeliveryOrderPrintRequest request)
			throws BusinessException, SystemException {
		DeliveryOrderPrintResponse response=new DeliveryOrderPrintResponse();
		ICacheClient cacheClient = MCSClientFactory.getCacheClient(MonitorCoonstants.MONITOR_CACHE_NAMESPACE);
		List<DeliveryProdPrintVo> list=new ArrayList<DeliveryProdPrintVo>();
		long sum = 0;
		// 获取订单下的商品信息
		List<OrdOdProd> ordOdProds = getOrdOdProds(request);
		//获取地址信息
		OrdOdLogistics odLogistics = ordOdLogisticsAtomSV.selectByOrd(request.getTenantId(),
				request.getOrderId());
  		if(odLogistics==null) {
  			logger.warn("未能查询到指定的订单配送信息[订单id:"+request.getOrderId()+"]");
  			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL,
  					"未能查询到指定的订单配送信息[订单id:"+request.getOrderId()+"]");
  		}
		String mergeInfo = cacheClient.get(String.valueOf(request.getOrderId()));
		List<OrdOrderProdAttach> orderProdAttachs = JSON.parseArray(mergeInfo, OrdOrderProdAttach.class);
		for (OrdOdProd ordOdProd : ordOdProds) {
			long buySum=ordOdProd.getBuySum();
			sum+=buySum;
			List<Long> mergeOrderIds=new ArrayList<Long>();
			if(!CollectionUtil.isEmpty(orderProdAttachs)) {
				for (OrdOrderProdAttach ordOrderProdAttach : orderProdAttachs) {
						if(ordOdProd.getSkuId().equals(ordOrderProdAttach.getSkuId())) {
							buySum+=ordOrderProdAttach.getBuySum();
							sum+=ordOrderProdAttach.getBuySum();
							mergeOrderIds.add(ordOrderProdAttach.getOrderId());
						}
				}
			}
			// 组装订单提货明细信息
			DeliveryProdPrintVo dpVo = this.createDeliverInfoProd(ordOdProd,buySum,mergeOrderIds);
			list.add(dpVo);
		}
		response.setContactName(odLogistics.getContactName());
		response.setSum(sum);
		response.setOrderId(request.getOrderId());
		response.setDeliveryProdPrintVos(list);
		return response;
	}
	
	//提货单打印
	@Override
	public void print(DeliveryOrderPrintInfosRequest request) {
		ICacheClient cacheClient = MCSClientFactory.getCacheClient(MonitorCoonstants.MONITOR_CACHE_NAMESPACE);
		List<DeliveryProdPrintVo> deliveryProdPrintVos = request.getDeliveryProdPrintVos();
		/* 生成批次号*/
		Long batchNo = SequenceUtil.createBatchNo();
		boolean signFlag=false;
		for (DeliveryProdPrintVo deliveryProdPrintVo : deliveryProdPrintVos) {
			  List<Long> allOrderIds = deliveryProdPrintVo.getHorOrderId();
		  	  allOrderIds.add(request.getOrderId());
		  	  for (Long mergeId : allOrderIds) { 
		  		  List<Long> temp = new ArrayList<>();
				  temp.addAll(allOrderIds);
				  temp.remove(mergeId);
				  Long deliverInfoId = SequenceUtil.createdeliverInfoId();
				  /* 封装属性*/
				  OrdOdDeliverInfo record=new OrdOdDeliverInfo();
				  record.setOrderId(mergeId);
				  record.setDeliverInfoId(deliverInfoId);
				  record.setHorOrderId(JSON.toJSONString(temp));
				  record.setPrintInfo(OrdersConstants.OrdOdDeliverInfo.printInfo.ONE);
				  record.setUpdateTime(DateUtil.getSysDate());
				  deliveryOrderPrintAtomSV.insertSelective(record);
				  DeliverInfoProd deliverInfoProd=new DeliverInfoProd();
				  deliverInfoProd.setDeliverInfoId(deliverInfoId);
				  deliverInfoProd.setBuySum(deliveryProdPrintVo.getBuySum());
				  deliverInfoProd.setExtendInfo(deliveryProdPrintVo.getExtendInfo());
				  deliverInfoProd.setProdName(deliveryProdPrintVo.getProdName());
				  deliverInfoProd.setSkuId(deliveryProdPrintVo.getSkuId());
				  deliverInfoProd.setSalePrice(deliveryProdPrintVo.getSalePrice());
				  deliveryOrderPrintAtomSV.insertSelective(deliverInfoProd);
				  /* 更新合并订单状态并写入订单状态变化轨迹*/
				  if(allOrderIds.size()==1 ) { 
					  //不合并 批次号为0
					  if(signFlag) {
						  this.updateOrderState(mergeId,request.getTenantId(),batchNo);
					  }else {
						  this.updateOrderState(mergeId,request.getTenantId(),0l);
					  }
				  }else {
					  this.updateOrderState(mergeId,request.getTenantId(),batchNo);//第一次,第二次
					  signFlag=true;
				  }
				  temp.clear();
				  //清理已经打印的信息
				  cacheClient.del(String.valueOf(mergeId));
		  	  }
		}
		
	}
	
	/**
	 * 参数检验及获取订单信息
	 */
	private OrdOrder checkParamAndQueryOrder(DeliveryOrderPrintRequest request) {
		ValidateUtils.validateDeliveryOrderPrintRequest(request);
		OrdOrder order = ordOrderAtomSV.selectByOrderId(request.getTenantId(), request.getOrderId());
		if(order==null) {
			logger.warn("未能查询到指定的订单信息[订单id:"+ request.getOrderId()+"]");
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, 
					"未能查询到指定的订单信息[订单id:"+request.getTenantId()+"]");
		} 
		return order;
	}
	
	
  /**
   * 获取订单下的商品信息
   */
	private List<OrdOdProd> getOrdOdProds(DeliveryOrderPrintRequest request) {
		String tenantId = request.getTenantId();
		Long orderId = request.getOrderId();
		List<OrdOdProd> ordOdProds = ordOdProdAtomSV.selectOrdProd(tenantId, orderId, 
				OrdersConstants.OrdOrder.cusServiceFlag.NO);
		if(CollectionUtil.isEmpty(ordOdProds)) {
			logger.warn("未能查询到指定的订单商品明细信息[订单id:"+request.getOrderId()+"]");
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, 
					"未能查询到指定的订单商品明细信息[订单id:"+request.getOrderId()+"]");
		}
		return ordOdProds;
	}
	
	/**
	 *  组装订单商品信息集合
	 */
	private List<OrdOrderProdAttach> createOriginalAttachs(List<OrdOdProd> ordOdProds) {
		List<OrdOrderProdAttach> originalAttachs=new ArrayList<OrdOrderProdAttach>();
		for (OrdOdProd ordOdProd : ordOdProds) {
			if(OrdersConstants.OrdOrder.cusServiceFlag.NO.equals(ordOdProd.getCusServiceFlag())) {
				OrdOrderProdAttach attach=new OrdOrderProdAttach();
				attach.setSkuId(ordOdProd.getSkuId());
				attach.setRouteId(ordOdProd.getRouteId());
				originalAttachs.add(attach);
			}
		}
		return originalAttachs;
	}

	/**
	  * 根据订单规则获取合并时间
	  */
	 public Timestamp getOrderListInTime(Integer monitorTime,Timestamp time,
			 String timeType) {
	 	Calendar calendar = Calendar.getInstance();
	 	calendar.setTime(time);
	 	switch(timeType) {
	 		case "D":
	 			calendar.add(Calendar.DAY_OF_MONTH, monitorTime);
	 			break;
	 		case "H":
	 			calendar.add(Calendar.HOUR, monitorTime);
	 			break;
	 		default:
	 			calendar.add(Calendar.MINUTE, monitorTime);
	 			break;
	 	}
        return new Timestamp(calendar.getTimeInMillis());
	 }
	 
	 
	 /**
      * 更新订单状态
      * 
      */
	  private void updateOrderState(Long mergeId,String tenantId, 
			 Long batchNo ) {
		ICacheSV iCacheSV=DubboConsumerFactory.getService(ICacheSV.class);
		Timestamp sysDate = DateUtil.getSysDate();
		OrdOrder ordOrder = ordOrderAtomSV.selectByOrderId(tenantId, mergeId);
		if(ordOrder==null) {
			logger.warn("未能查询到指定的订单信息[订单id:"+ mergeId+"]");
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, 
					"未能查询到指定的订单信息[订单id:"+ mergeId+"]");
		}
		String orgState = ordOrder.getState();
		if(OrdersConstants.OrdOrder.State.WAIT_DELIVERY.equals(orgState)) {
			return;
		}
		String state1 = OrdersConstants.OrdOrder.State.LADING_BILL_FINISH_PRINT;
		String state2 = OrdersConstants.OrdOrder.State.FINISH_DISTRIBUTION;
		String newState = OrdersConstants.OrdOrder.State.WAIT_DELIVERY;
		ordOrder.setState(newState);
		ordOrder.setStateChgTime(sysDate);
		ordOrder.setBatchNo(batchNo);
		ordOrderAtomSV.updateById(ordOrder);
		//写入搜索引擎
		try {
			//翻译
			SysParam sysParamState = InfoTranslateUtil.translateInfo(ordOrder.getTenantId(),
					"ORD_ORDER", "STATE",ordOrder.getState(), iCacheSV);
			SESClientFactory.getSearchClient(SearchConstants.SearchNameSpace).
				upsert(String.valueOf(ordOrder.getParentOrderId()), 
						new JsonBuilder().startObject().field(SearchFieldConfConstants.ORD_EXTENDES_STATE, 
								newState).field(SearchFieldConfConstants.ORD_EXTENDES_STATE_NAME, 
										sysParamState.getColumnDesc()).endObject());
		} catch (Exception e) {
			logger.error("导入数据到搜索引擎失败.......");
			throw new SystemException("导入数据到搜索引擎失败..."+ordOrder.getParentOrderId());
		}
		// 写入订单状态变化轨迹表
		orderFrameCoreSV.ordOdStateChg(ordOrder.getOrderId(), ordOrder.getTenantId(), orgState, state1,
				OrdOdStateChg.ChgDesc.ORDER_TO_PRINT, null, null, null, sysDate);
		orderFrameCoreSV.ordOdStateChg(ordOrder.getOrderId(), ordOrder.getTenantId(), state1, state2,
				OrdOdStateChg.ChgDesc.ORDER_TO_FINISH_DISTRIBUTION, null, null, null, sysDate);
		orderFrameCoreSV.ordOdStateChg(ordOrder.getOrderId(), ordOrder.getTenantId(), state2, newState,
				OrdOdStateChg.ChgDesc.ORDER_TO_WAIT_DELIVERY, null, null, null, sysDate);
	}
	  
	  /**
	   * 组装提货单信息明细
	 * @param horOrderId 
	   */
	  private DeliveryProdPrintVo createDeliverInfoProd(OrdOdProd ordOdProd,
			  long buySum, List<Long> mergeOrderIds) {
		  DeliveryProdPrintVo dpVo=new DeliveryProdPrintVo();
		  dpVo.setBuySum(buySum);
		  dpVo.setHorOrderId(mergeOrderIds);
		  dpVo.setExtendInfo(ordOdProd.getExtendInfo());
		  dpVo.setProdName(ordOdProd.getProdName());
		  dpVo.setSkuId(ordOdProd.getSkuId());
		  dpVo.setSalePrice(ordOdProd.getSalePrice());
		  return dpVo;
	  }
	  
	  
	 /**
	  * 筛选订单
	  */
	  private List<Long> judgeOrder(List<OrdOrderProdAttach> originalAttachs,
			  List<OrdOrderProdAttach> orderProdAttachs,List<Long> orderList,String str) {
		  logger.info("筛选不符合合并规则的订单......");
		  if (null != orderProdAttachs && orderProdAttachs.size() > 0) {
			    Iterator<OrdOrderProdAttach> it = orderProdAttachs.iterator();  
			    while(it.hasNext()){
			    	OrdOrderProdAttach ordOrderProdAttach = (OrdOrderProdAttach)it.next(); 
			    	if(!CollectionUtil.isEmpty(orderList) && orderList.contains(ordOrderProdAttach.getOrderId())) {
			    		it.remove();
			    		continue;
			    	}
					List<OrdOdProd> ordOdProds = ordOdProdAtomSV.selectByOrd(ordOrderProdAttach.getTenantId(), 
							ordOrderProdAttach.getOrderId());
					/* 1.个数筛选(合并之后的订单商品个数大于原来商品个数,则不合并)*/
					if(ordOdProds.size()>originalAttachs.size()) {
						it.remove();
						continue;
					}
					/* 收货地址筛选*/
					OrdOdLogistics odLogistics = ordOdLogisticsAtomSV.selectByOrd(
							ordOrderProdAttach.getTenantId(), ordOrderProdAttach.getOrderId());
					if(odLogistics==null) {
						logger.warn("未能查询到指定的订单配送信息[订单id:"+ordOrderProdAttach.getOrderId()+"]");
						throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL,
								"未能查询到指定的订单配送信息[订单id:"+ordOrderProdAttach.getOrderId()+"]");
					}
					String sp = getAddressInfo(odLogistics);
					if(!sp.equals(str)) {
						it.remove();
						continue;
					}
					/* 2.商品包含筛选*/
					for (OrdOdProd ordOdProd : ordOdProds) {
						if(OrdersConstants.OrdOrder.cusServiceFlag.YES.equals(ordOdProd.getCusServiceFlag())) {
							/* 判断该商品对应的售后订单状态*/
							List<OrdOrder> ordOrderList = this.createAfterOrder(ordOdProd);
							for (OrdOrder ordOrder : ordOrderList) {
								if(ordOrderList.size()>1) { //售后订单 可能存在多个第一次审核失败的情况
									  if(OrdersConstants.OrdOrder.State.AUDIT_FAILURE.equals(ordOrder.getState())) {
										  continue;
									  }
								}
								if(!(OrdersConstants.OrdOrder.State.FINISH_REFUND.equals(ordOrder.getState())||
										OrdersConstants.OrdOrder.State.REFUND_AUDIT.equals(ordOrder.getState())||
										OrdersConstants.OrdOrder.State.EXCHANGE_AUDIT.equals(ordOrder.getState())||
										OrdersConstants.OrdOrder.State.AUDIT_AGAIN_FAILURE.equals(ordOrder.getState()))) {
									it.remove();
									break;
								}else {
									OrdOrderProdAttach oPAttach=new OrdOrderProdAttach();
									oPAttach.setSkuId(ordOdProd.getSkuId());
									oPAttach.setRouteId(ordOdProd.getRouteId());
									if(!originalAttachs.contains(oPAttach)) {
										if(CollectionUtil.isEmpty(orderList)) {
											orderList=new ArrayList<Long>();
										}
										orderList.add(Long.valueOf(ordOrderProdAttach.getOrderId()));
										it.remove();
										break;
									}
								}
							}
						}else {
							OrdOrderProdAttach oPAttach=new OrdOrderProdAttach();
							oPAttach.setSkuId(ordOdProd.getSkuId());
							oPAttach.setRouteId(ordOdProd.getRouteId());
							if(!originalAttachs.contains(oPAttach)) {
								if(CollectionUtil.isEmpty(orderList)) {
									orderList=new ArrayList<Long>();
								}
								orderList.add(Long.valueOf(ordOrderProdAttach.getOrderId()));
								it.remove();
								break;
							}
						}
					}
			    }
		  }
		return orderList;
	  }
	  
	  /**
	   * 获取商品对应的售后订单状态
	   */
	  public List<OrdOrder> createAfterOrder(OrdOdProd ordOdProd) {
		  	List<OrdOrder> orderList=new ArrayList<>();
		  	//获取售后订单
			List<OrdOrder> ordOrderList=ordOrderAtomSV.selectSaleOrder(ordOdProd.getTenantId(), 
					ordOdProd.getOrderId());
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
	  
  	//获取地址信息
  	private String getAddressInfo(OrdOdLogistics odLogistics) {
  		//拼接地址
  		StringBuilder oriSp=new StringBuilder();
  		oriSp.append(odLogistics.getProvinceCode()).append(odLogistics.getCityCode()).
  		append(odLogistics.getCountyCode()).append(odLogistics.getAddress());
		return oriSp.toString();
  	}
}
