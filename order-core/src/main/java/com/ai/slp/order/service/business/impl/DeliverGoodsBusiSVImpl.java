package com.ai.slp.order.service.business.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.util.DateUtil;
import com.ai.opt.sdk.util.StringUtil;
import com.ai.paas.ipaas.search.vo.Result;
import com.ai.paas.ipaas.search.vo.SearchCriteria;
import com.ai.platform.common.api.cache.interfaces.ICacheSV;
import com.ai.platform.common.api.cache.param.SysParam;
import com.ai.slp.order.api.delivergoods.param.DeliverGoodsRequest;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.constants.SearchConstants;
import com.ai.slp.order.constants.OrdersConstants.OrdOdStateChg;
import com.ai.slp.order.dao.mapper.bo.OrdOdLogistics;
import com.ai.slp.order.dao.mapper.bo.OrdOdProd;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.manager.ESClientManager;
import com.ai.slp.order.search.bo.OrdProdExtend;
import com.ai.slp.order.search.bo.OrderInfo;
import com.ai.slp.order.search.dto.SearchCriteriaStructure;
import com.ai.slp.order.service.atom.interfaces.IOrdOdLogisticsAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdProdAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.ai.slp.order.service.business.impl.search.OrderSearchImpl;
import com.ai.slp.order.service.business.interfaces.IDeliverGoodsBusiSV;
import com.ai.slp.order.service.business.interfaces.search.IOrderIndexBusiSV;
import com.ai.slp.order.service.business.interfaces.search.IOrderSearch;
import com.ai.slp.order.util.InfoTranslateUtil;
import com.ai.slp.order.util.OrderStateChgUtil;

@Service
@Transactional
public class DeliverGoodsBusiSVImpl implements IDeliverGoodsBusiSV {
	
	private static final Logger logger=LoggerFactory.getLogger(DeliverGoodsBusiSVImpl.class);

	@Autowired
	private IOrdOdLogisticsAtomSV ordOdLogisticsAtomSV;
	@Autowired
	private IOrdOrderAtomSV ordOrderAtomSV;
	@Autowired
	private IOrdOdProdAtomSV ordOdProdAtomSV;
	@Autowired
	IOrderIndexBusiSV orderIndexBusiSV;
	
	//订单发货
	@Override
	public void deliverGoods(DeliverGoodsRequest request,OrdOrder ordOrder) throws BusinessException, SystemException {
		List<OrdOdProd> ordOdProds = this.getOrdOdProds(request.getTenantId(), request.getOrderId());
		for (OrdOdProd ordOdProd : ordOdProds) {
			if(OrdersConstants.OrdOrder.cusServiceFlag.YES.equals(ordOdProd.getCusServiceFlag())) {
				List<OrdOrder> ordOrderList = this.createAfterOrder(ordOdProd);
				 for (OrdOrder order : ordOrderList) {
				    if(ordOrderList.size()>1) { //多个售后订单 可能存在多个第一次审核失败的情况
					   if(OrdersConstants.OrdOrder.State.AUDIT_FAILURE.equals(order.getState())) {
						   continue;
					   }
				    }
					if(!(OrdersConstants.OrdOrder.State.FINISH_REFUND.equals(order.getState())||
						OrdersConstants.OrdOrder.State.REFUND_AUDIT.equals(order.getState())||
						OrdersConstants.OrdOrder.State.EXCHANGE_AUDIT.equals(order.getState())||
						OrdersConstants.OrdOrder.State.AUDIT_AGAIN_FAILURE.equals(order.getState()))) {
						//该商品为售后标识 不可打印
						throw new BusinessException("", "订单下商品未售后完成,不可进行发货");
					}
				}
			}
		}
		/* 获取子订单下的合并订单*/
		if(ordOrder.getBatchNo()!=0) {
			List<OrdOrder> mergeOrders = ordOrderAtomSV.selectMergeOrderByBatchNo(ordOrder.getOrderId(),
					ordOrder.getTenantId(), ordOrder.getBatchNo(),OrdersConstants.OrdOrder.State.WAIT_SEND);
			for	 (OrdOrder mergeOrder : mergeOrders) {
				List<OrdOdProd> mergeOrdOdProds = this.getOrdOdProds(mergeOrder.getTenantId(), mergeOrder.getOrderId());
				for (OrdOdProd ordOdProd : mergeOrdOdProds) {
					if(OrdersConstants.OrdOrder.cusServiceFlag.YES.equals(ordOdProd.getCusServiceFlag())) {
						List<OrdOrder> ordOrderList = this.createAfterOrder(ordOdProd);
						for (OrdOrder order : ordOrderList) {
							if(ordOrderList.size()>1) { //多个售后订单 可能存在多个第一次审核失败的情况
								if(OrdersConstants.OrdOrder.State.AUDIT_FAILURE.equals(order.getState())) {
									continue;
								}
							}
							if(!(OrdersConstants.OrdOrder.State.FINISH_REFUND.equals(order.getState())||
									OrdersConstants.OrdOrder.State.REFUND_AUDIT.equals(order.getState())||
									OrdersConstants.OrdOrder.State.EXCHANGE_AUDIT.equals(order.getState())||
									OrdersConstants.OrdOrder.State.AUDIT_AGAIN_FAILURE.equals(order.getState()))) {
								//该商品为售后标识 不可打印
								throw new BusinessException("", "合并订单下商品未售后完成,不可进行发货");
							}
						}
					}
				}
				/* 更新合并订单对应的配送信息*/
				this.updateLogistics(mergeOrder, request);
			}
		}
		/* 更新订单对应的配送信息*/
		this.updateLogistics(ordOrder, request);
	}
	
	
	/**
	 * 更新订单对应配送信息
	 */
	private void updateLogistics(OrdOrder ordOrder,DeliverGoodsRequest request) {
		OrdOdLogistics ordOdLogistics = ordOdLogisticsAtomSV.selectByOrd(ordOrder.getTenantId(), ordOrder.getOrderId());
		if(ordOdLogistics==null) {
			logger.error("未能查询到指定的配送信息[订单id:"+ ordOrder.getOrderId()+" ,租户id:"+ordOrder.getTenantId()+"]");
			throw new BusinessException(ExceptCodeConstants.Special.NO_RESULT, 
					"未能查询到指定的配送信息[订单id:"+ ordOrder.getOrderId()+" ,租户id:"+ordOrder.getTenantId()+"]");
		}
		if(!StringUtil.isBlank(ordOdLogistics.getExpressOddNumber())) {
			throw new BusinessException("", "订单不能重复发货");
		}
		ordOdLogistics.setExpressId(request.getExpressId());
		ordOdLogistics.setExpressOddNumber(request.getExpressOddNumber());
		ordOdLogisticsAtomSV.updateByPrimaryKey(ordOdLogistics);
		/* 更新订单状态和订单轨迹信息*/
		this.updateOrderState(ordOrder, ordOrder.getOperId(),ordOdLogistics);
	}
	
	
	/**
     * 更新订单状态,并写入订单状态变化轨迹表
     * 
     */
    private void updateOrderState(OrdOrder ordOrder,String operId,
    		OrdOdLogistics ordOdLogistics ) {
        String orgState = ordOrder.getState();
        String newState = OrdersConstants.OrdOrder.State.WAIT_CONFIRM;
        ordOrder.setState(newState);
        Timestamp sysDate=DateUtil.getSysDate();
        ordOrder.setStateChgTime(sysDate);
        ordOrderAtomSV.updateOrderState(ordOrder);
        //刷新搜索引擎数据
		this.refreshLogisticsData(ordOrder,ordOdLogistics);
		
		//异步 写入订单状态变化轨迹表
		OrderStateChgUtil.trailProcess(ordOrder.getOrderId(), ordOrder.getTenantId(), orgState, newState,
                OrdOdStateChg.ChgDesc.ORDER_TO_FINISH_LOGISTICS_DELIVERY, null, operId, null, sysDate);
   }
    

	/**
	  * 获取订单下的商品信息
	  */
	private List<OrdOdProd> getOrdOdProds(String tenantId,long orderId) {
		List<OrdOdProd> ordOdProds = ordOdProdAtomSV.selectByOrd(tenantId, orderId);
		if(CollectionUtil.isEmpty(ordOdProds)) {
			logger.warn("未能查询到指定的订单商品明细信息[订单id:"+orderId+"]");
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
					ordOdProd.getTenantId(), ordOdProd.getOrderId());
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
	   * 刷新es引擎物流相关数据
	   * @param ordOrder
	   * @param ordOdLogistics
	   * @author caofz
	   * @ApiDocMethod
	   * @ApiCode 
	   * @RestRelativeURL
	   */
	  private void refreshLogisticsData(OrdOrder ordOrder, OrdOdLogistics ordOdLogistics) {
		ICacheSV iCacheSV = DubboConsumerFactory.getService(ICacheSV.class);
  		IOrderSearch orderSearch = new OrderSearchImpl();
		List<SearchCriteria> orderSearchCriteria = SearchCriteriaStructure.
				commonConditionsByOrderId(ordOrder.getParentOrderId());
		Result<OrderInfo> result = orderSearch.search(orderSearchCriteria, 0, 1, null);
		List<OrderInfo> ordList = result.getContents();
		if(CollectionUtil.isEmpty(ordList)) {
			throw new BusinessException("搜索引擎无数据! 父订单id为:"+ordOrder.getParentOrderId());
		}
		OrderInfo orderInfo = ordList.get(0);
		if(orderInfo!=null) {
			orderInfo.setExpressid(ordOdLogistics.getExpressId());
			orderInfo.setExpressoddnumber(ordOdLogistics.getExpressOddNumber());
			List<OrdProdExtend> ordextendes = orderInfo.getOrdextendes();
			for (OrdProdExtend ordProdExtend : ordextendes) {
				if(ordOrder.getOrderId()==ordProdExtend.getOrderid()) {
					ordProdExtend.setState(ordOrder.getState());
					//订单状态翻译
					SysParam sysParamState = InfoTranslateUtil.translateInfo(ordOrder.getTenantId(),
							"ORD_ORDER", "STATE",ordOrder.getState(), iCacheSV);
					ordProdExtend.setStatename(sysParamState == null ? "" : sysParamState.getColumnDesc());
				}
			}
		}
		ESClientManager.getSesClient(SearchConstants.SearchNameSpace).bulkInsert(ordList);
	  }
}
