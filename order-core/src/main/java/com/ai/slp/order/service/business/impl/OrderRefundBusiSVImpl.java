package com.ai.slp.order.service.business.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
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
import com.ai.paas.ipaas.search.ISearchClient;
import com.ai.paas.ipaas.search.vo.Result;
import com.ai.paas.ipaas.search.vo.SearchCriteria;
import com.ai.platform.common.api.cache.interfaces.ICacheSV;
import com.ai.platform.common.api.cache.param.SysParam;
import com.ai.slp.order.api.orderrefund.param.OrderRefundRequest;
import com.ai.slp.order.api.orderrefund.param.OrderRefuseRefundRequest;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.constants.OrdersConstants.OrdOdStateChg;
import com.ai.slp.order.constants.SearchConstants;
import com.ai.slp.order.dao.mapper.bo.OrdOdFeeTotal;
import com.ai.slp.order.dao.mapper.bo.OrdOdProd;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.manager.ESClientManager;
import com.ai.slp.order.search.bo.OrdProdExtend;
import com.ai.slp.order.search.bo.OrderInfo;
import com.ai.slp.order.search.bo.ProdInfo;
import com.ai.slp.order.search.dto.SearchCriteriaStructure;
import com.ai.slp.order.service.atom.interfaces.IOrdOdFeeTotalAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdProdAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.ai.slp.order.service.business.impl.search.OrderSearchImpl;
import com.ai.slp.order.service.business.interfaces.IOrderFrameCoreSV;
import com.ai.slp.order.service.business.interfaces.IOrderRefundBusiSV;
import com.ai.slp.order.service.business.interfaces.search.IOrderSearch;
import com.ai.slp.order.util.InfoTranslateUtil;

@Service
@Transactional
public class OrderRefundBusiSVImpl implements IOrderRefundBusiSV {

	private static Logger logger = LoggerFactory.getLogger(OrderRefundBusiSVImpl.class);
	
	@Autowired
	private IOrdOrderAtomSV ordOrderAtomSV;
	@Autowired
	private IOrderFrameCoreSV orderFrameCoreSV;
	@Autowired
	private IOrdOdProdAtomSV ordOdProdAtomSV;
	@Autowired
	private IOrdOdFeeTotalAtomSV  ordOdFeeTotalAtomSV;
	
	//同意退款
	public void partRefund(OrderRefundRequest request) throws BusinessException, SystemException {
		OrdOrder ordOrder = ordOrderAtomSV.selectByOrderId(request.getTenantId(), request.getOrderId());
		if(ordOrder==null) {
			logger.error("订单信息不存在[订单id:"+request.getOrderId()+"租户id:"+request.getTenantId()+"]");
			throw new BusinessException(ExceptCodeConstants.Special.NO_RESULT, 
					"订单信息不存在[订单id:"+request.getOrderId()+"租户id:"+request.getTenantId()+"]");
		}
		OrdOdFeeTotal ordOdFeeTotal = ordOdFeeTotalAtomSV.selectByOrderId(ordOrder.getTenantId(), 
				ordOrder.getOrderId());
		long updateMoney = request.getUpdateMoney();
		/*判断输入费用是否大于之前存在的费用*/
		//实际费用
		long adjustFee = ordOdFeeTotal.getAdjustFee();
	    BigDecimal balance = BigDecimal.valueOf(adjustFee).divide(new BigDecimal(1000L),
	    		2,BigDecimal.ROUND_HALF_UP);
        BigDecimal balance1 = balance.multiply(new BigDecimal(1000L));
		if(updateMoney>balance1.longValue()) {
			logger.error("输入的费用不能大于实际应收的费用,实际应收费用为:"+ordOdFeeTotal.getAdjustFee());
			throw new BusinessException("", "输入的费用不能大于实际应收的费用");
		}
		ordOdFeeTotal.setOperDiscountDesc(request.getUpdateReason());
		ordOdFeeTotal.setPaidFee(updateMoney);
		ordOdFeeTotal.setUpdateOperId(request.getOperId());
		ordOdFeeTotalAtomSV.updateByOrderId(ordOdFeeTotal);
		ordOrder.setReasonDesc(request.getUpdateReason());
		ordOrder.setOperId(request.getOperId());
		//ordOrderAtomSV.updateById(ordOrder);
		//优化 更新部分
		ordOrderAtomSV.updateInfoByRefund(ordOrder);
		//刷新es引擎数据
		this.refreshFeeData(ordOrder,ordOdFeeTotal);
	}
	
	//拒绝退款
	@Override
	public void refuseRefund(OrderRefuseRefundRequest request) throws BusinessException, SystemException {
		OrdOrder ordOrder = ordOrderAtomSV.selectByOrderId(request.getTenantId(), request.getOrderId());
		if(ordOrder==null) {
			logger.error("订单信息不存在[订单id:"+request.getOrderId()+"租户id:"+request.getTenantId()+"]");
			throw new BusinessException(ExceptCodeConstants.Special.NO_RESULT, 
					"订单信息不存在[订单id:"+request.getOrderId()+"租户id:"+request.getTenantId()+"]");
		}
		/* 更新订单状态和写入订单轨迹*/
		String orgState = ordOrder.getState();
		String newState=OrdersConstants.OrdOrder.State.AUDIT_AGAIN_FAILURE;
		String chgDesc=OrdOdStateChg.ChgDesc.ORDER_AUDIT_NOT_PASS;
		Timestamp sysDate=DateUtil.getSysDate();
        ordOrder.setReasonDesc(request.getRefuseReason());
        ordOrder.setState(newState);
        ordOrder.setStateChgTime(sysDate);
        ordOrder.setOperId(request.getOperId());
        ordOrderAtomSV.updateById(ordOrder);
        //订单业务类型为退货的话,第二次审核失败,则判断子订单信息
        if(OrdersConstants.OrdOrder.BusiCode.UNSUBSCRIBE_ORDER.equals(ordOrder.getBusiCode())) {
        	/* 获取子订单信息及子订单下的商品明细信息*/
    		OrdOrder order = ordOrderAtomSV.selectByOrderId(request.getTenantId(), 
    				ordOrder.getOrigOrderId());
    		List<OrdOdProd> prodList = ordOdProdAtomSV.selectByOrd(request.getTenantId(), 
    				ordOrder.getOrigOrderId());
    		boolean cusFlag=false;
    		for (OrdOdProd ordOdProd : prodList) {
    			if(OrdersConstants.OrdOrder.cusServiceFlag.YES.equals(ordOdProd.getCusServiceFlag())) {
    				cusFlag=true;
    			}else {
    				cusFlag=false;
    				break;
    			}
    		}
    		/* 获取子订单下的所有售后订单*/
    		List<OrdOrder> orderList =ordOrderAtomSV.selectSaleOrder(ordOrder.getTenantId(), ordOrder.getOrigOrderId());
    		OrdOrder parentOrder = ordOrderAtomSV.selectByOrderId(request.getTenantId(), 
    				 order.getParentOrderId()); //父订单
    		boolean flag=false;
    		for (OrdOrder afterOrdOrder : orderList) {  //表示有售后订单存在
    			String state = afterOrdOrder.getState();
    			if(OrdersConstants.OrdOrder.State.FINISH_REFUND.equals(state)||
    					OrdersConstants.OrdOrder.State.EXCHANGE_AUDIT.equals(state)||
    					OrdersConstants.OrdOrder.State.REFUND_AUDIT.equals(state)||
    					OrdersConstants.OrdOrder.State.AUDIT_FAILURE.equals(state)||
    					OrdersConstants.OrdOrder.State.AUDIT_AGAIN_FAILURE.equals(state)) { //表示售后订单为已完成状态或者审核失败
    				flag=true;
    			}else {
    				flag=false;
    				break;
    			}
    		}
    		//未发货状态时
    		if(OrdersConstants.OrdOrder.State.WAIT_DISTRIBUTION.equals(order.getState())||
    				 OrdersConstants.OrdOrder.State.WAIT_DELIVERY.equals(order.getState())||
    				 OrdersConstants.OrdOrder.State.WAIT_SEND.equals(order.getState())) {
    			 if(!CollectionUtil.isEmpty(orderList)) { //有售后订单 
    				if(flag&&cusFlag) {
    					order.setState(OrdersConstants.OrdOrder.State.COMPLETED);
    					ordOrderAtomSV.updateById(order);
    					//判断父订单下的其它子订单状态  
    					// 完成则为 父订单完成,否则父订单不变
    					boolean stateFlag = this.judgeState(order);
    					if(stateFlag) {
    						parentOrder.setState(OrdersConstants.OrdOrder.State.COMPLETED);
    						ordOrderAtomSV.updateById(parentOrder); 
    					}
    				} 
    			 }
    		 }else { //已发货状态
    			 if(!CollectionUtil.isEmpty(orderList)) {
    				 if(flag) {
	    				 order.setState(OrdersConstants.OrdOrder.State.COMPLETED);
	    				 ordOrderAtomSV.updateById(order);
	 					//判断父订单下的其它子订单状态  
	 					// 完成则为 父订单完成,否则父订单不变
	    				boolean stateFlag = this.judgeState(order);
	 					if(stateFlag) {
	 						parentOrder.setState(OrdersConstants.OrdOrder.State.COMPLETED);
	 						ordOrderAtomSV.updateById(parentOrder); 
	 					}
    				 }
    			}
    		 }
    		
    		//刷新数据 售后订单-子订单-父订单状态改变
    		this.refreshData(ordOrder, parentOrder,null,order);
    		
        }else {
        	/* 退款业务类型时  拒绝  改变原始订单的商品售后标识状态*/
    		OrdOdProd subProd = this.updateProdCusServiceFlag(ordOrder);
    		
    		//刷新数据 售后订单-售后标识改变
    		this.refreshData(ordOrder, null,subProd,null);
    		
        }
        // 写入订单状态变化轨迹表
        this.updateOrderState(ordOrder, orgState, newState, chgDesc, request.getOperId());
	}
	
    /**
     * 更新订单状态
     */
    private void updateOrderState(OrdOrder ordOrder,String 
    		orgState,String newState,String chgDesc,String operId) {
        orderFrameCoreSV.ordOdStateChg(ordOrder.getOrderId(), ordOrder.getTenantId(), orgState, newState,
        		chgDesc, null, operId, null, DateUtil.getSysDate());
    }
    
    /**
     * 审核拒绝  改变原始订单的商品售后标识状态
     * 
     */
    private OrdOdProd updateProdCusServiceFlag(OrdOrder ordOrder) {
		List<OrdOdProd> prodList = ordOdProdAtomSV.selectByOrd(ordOrder.getTenantId(), ordOrder.getOrderId());
		if(CollectionUtil.isEmpty(prodList)) {
			throw new BusinessException(ExceptCodeConstants.Special.NO_RESULT, 
					"未能查询到相关商品信息[订单id:"+ordOrder.getOrderId()+"]");
		}
		OrdOdProd ordOdProd = prodList.get(0);
		List<OrdOdProd> origProdList =ordOdProdAtomSV.selectSaleProd(ordOdProd.getTenantId(),
				ordOrder.getOrigOrderId(), ordOdProd.getSkuId());
		if(CollectionUtil.isEmpty(origProdList)) {
			throw new BusinessException(ExceptCodeConstants.Special.NO_RESULT, 
					"未能查询到相关商品信息[原始订单id:"+ordOrder.getOrigOrderId()+" ,skuId:"+ordOdProd.getSkuId()+"]");
		}
		OrdOdProd prod = origProdList.get(0);  //单个订单对应单个商品(售后)
		prod.setCusServiceFlag(OrdersConstants.OrdOrder.cusServiceFlag.NO);
		ordOdProdAtomSV.updateById(prod);
		return prod;
    }
    
    /**
     * 判断父订单下面其它子订单状态
     */
    private boolean judgeState(OrdOrder order) {
    	//父订单下的其它子订单
        List<OrdOrder> childOrders =ordOrderAtomSV.selectOtherOrders(order);
	    if(!CollectionUtil.isEmpty(childOrders)) {
	    	for (OrdOrder ordOrder : childOrders) {
	    		//其它子订单状态不是'完成'
				if(!OrdersConstants.OrdOrder.State.COMPLETED.equals(ordOrder.getState())) {
					return false;
				}
			}
	    }
	    return true;
    }
    
    
    
    public void refreshData(OrdOrder ordOrder,OrdOrder pOrder,
    		OrdOdProd subProd,OrdOrder subOrder) 
    		throws BusinessException, SystemException {
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
		//更新父订单状态
		if(pOrder!=null) {
			orderInfo.setParentorderstate(pOrder.getState());
		}
		List<OrdProdExtend> ordextendes = orderInfo.getOrdextendes();
		for (OrdProdExtend ordProdExtend : ordextendes) {
			//售后订单
			if(ordOrder.getOrderId()==ordProdExtend.getOrderid()) {
				ordProdExtend.setState(ordOrder.getState());
				//订单状态翻译
				SysParam sysParamState = InfoTranslateUtil.translateInfo(ordOrder.getTenantId(),
						"ORD_ORDER", "STATE",ordOrder.getState(), iCacheSV);
				ordProdExtend.setStatename(sysParamState == null ? "" : sysParamState.getColumnDesc());
			} 
			//子订单信息修改
			//退费时候
			if(!OrdersConstants.OrdOrder.BusiCode.UNSUBSCRIBE_ORDER.equals(ordOrder.getBusiCode()) &&
					ordOrder.getOrigOrderId()==ordProdExtend.getOrderid()) {
				//子订单商品售后标识
				List<ProdInfo> prodinfos = ordProdExtend.getProdinfos();
				if(!CollectionUtil.isEmpty(prodinfos)) {
					for (ProdInfo prodInfo : prodinfos) {
						if(prodInfo.getSkuid().equals(subProd.getSkuId()) ) {
							//售后对应单个商品
							prodInfo.setCusserviceflag(OrdersConstants.OrdOrder.cusServiceFlag.NO);
						}
					}
				}
			//退货时候 	子订单订单状态修改
			}else if(OrdersConstants.OrdOrder.BusiCode.UNSUBSCRIBE_ORDER.equals(ordOrder.getBusiCode()) &&
					ordOrder.getOrigOrderId()==ordProdExtend.getOrderid()){
				ordProdExtend.setState(subOrder.getState());
				SysParam sysParamState = InfoTranslateUtil.translateInfo(ordOrder.getTenantId(),
						"ORD_ORDER", "STATE",subOrder.getState(), iCacheSV);
				ordProdExtend.setStatename(sysParamState == null ? "" : sysParamState.getColumnDesc());
			}
		}
		ESClientManager.getSesClient(SearchConstants.SearchNameSpace).bulkInsert(ordList);
//		ISearchClient client = ESClientManager.getSesClient(SearchConstants.SearchNameSpace);
//		client.bulkInsert(ordList);
//		client.refresh();
	}
    
    
    private void refreshFeeData(OrdOrder ordOrder,OrdOdFeeTotal ordOdFeeTotal) 
    		throws BusinessException, SystemException {
  		IOrderSearch orderSearch = new OrderSearchImpl();
		List<SearchCriteria> orderSearchCriteria = SearchCriteriaStructure.
				commonConditionsByOrderId(ordOrder.getParentOrderId());
		Result<OrderInfo> result = orderSearch.search(orderSearchCriteria, 0, 1, null);
		List<OrderInfo> ordList = result.getContents();
		if(CollectionUtil.isEmpty(ordList)) {
			throw new BusinessException("搜索引擎无数据! 父订单id为:"+ordOrder.getParentOrderId());
		}
		OrderInfo orderInfo = ordList.get(0);
		List<OrdProdExtend> ordextendes = orderInfo.getOrdextendes();
		for (OrdProdExtend ordProdExtend : ordextendes) {
			//售后订单
			if(ordOrder.getOrderId()==ordProdExtend.getOrderid()) {
				//修改之后的退款金额
				ordProdExtend.setPaidfee(ordOdFeeTotal.getPaidFee());
			}
		}
	//	ESClientManager.getSesClient(SearchConstants.SearchNameSpace).bulkInsert(ordList);
		ISearchClient client = ESClientManager.getSesClient(SearchConstants.SearchNameSpace);
		client.bulkInsert(ordList);
		client.refresh();
    }
 }   
    
