package com.ai.slp.order.service.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.paas.ipaas.search.vo.Result;
import com.ai.paas.ipaas.search.vo.SearchCriteria;
import com.ai.platform.common.api.cache.interfaces.ICacheSV;
import com.ai.platform.common.api.cache.param.SysParam;
import com.ai.slp.order.api.orderstate.param.WaitRebateRequest;
import com.ai.slp.order.api.orderstate.param.WaitRebateResponse;
import com.ai.slp.order.api.sesdata.param.SesDataRequest;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.constants.SearchConstants;
import com.ai.slp.order.dao.mapper.bo.OrdOdLogistics;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.manager.ESClientManager;
import com.ai.slp.order.search.bo.OrdProdExtend;
import com.ai.slp.order.search.bo.OrderInfo;
import com.ai.slp.order.search.dto.SearchCriteriaStructure;
import com.ai.slp.order.service.atom.interfaces.IOrdOdLogisticsAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.ai.slp.order.service.business.impl.search.OrderSearchImpl;
import com.ai.slp.order.service.business.interfaces.IOrderStateBusiSV;
import com.ai.slp.order.service.business.interfaces.search.IOrderIndexBusiSV;
import com.ai.slp.order.service.business.interfaces.search.IOrderSearch;
import com.ai.slp.order.util.InfoTranslateUtil;
@Service
public class OrderStateBusiSVImpl implements IOrderStateBusiSV {

	@Autowired
	private IOrdOrderAtomSV ordOrderAtomSV;
	@Autowired
	private IOrdOdLogisticsAtomSV ordOdLogisticsAtomSV;
	@Autowired
	private IOrderIndexBusiSV orderIndexBusiSV;
	
	//待卖家收货确认状态修改 (买家发货快递填写)
	@Override
	@Transactional
	public void updateWaitSellRecieveSureState(OrdOrder ordOrder,
			OrdOdLogistics ordOdLogistics) {
		//
		this.ordOrderAtomSV.updateOrderState(ordOrder);
		//
		this.ordOdLogisticsAtomSV.insertSelective(ordOdLogistics);
		//刷新搜索引擎数据
		//this.orderIndexBusiSV.refreshStateData(ordOrder,null);
		
		this.refreshData(ordOrder, ordOdLogistics);
	}
	
	
	//待退费状态修改 
	@Override
	@Transactional
	public WaitRebateResponse updateWaitRebateState(WaitRebateRequest request) {
		WaitRebateResponse response = new WaitRebateResponse();
		//
		String tenantId = request.getTenantId();
		Long orderId = request.getOrderId();
		OrdOrder ordOrder = ordOrderAtomSV.selectByOrderId(tenantId, orderId);
		if(ordOrder==null) {
			throw new BusinessException(ExceptCodeConstants.Special.NO_RESULT, 
					"订单信息不存在");
		}
		if(OrdersConstants.OrdOrder.BusiCode.EXCHANGE_ORDER.equals(ordOrder.getBusiCode())) {
			ordOrder.setState( OrdersConstants.OrdOrder.State.REFUND_AUDIT);
			ordOrderAtomSV.updateById(ordOrder);
		//	this.ordOrderAtomSV.updateStateByOrderId(tenantId, orderId, OrdersConstants.OrdOrder.State.REFUND_AUDIT);
			//换货完成之后判断子订单下的信息
			/* 获取子订单下的所有售后订单*/
			List<OrdOrder> orderList =ordOrderAtomSV.selectSubSaleOrder(ordOrder.getOrigOrderId(),request.getOrderId());
			boolean flag=false;
			for (OrdOrder order : orderList) {  //表示有售后订单存在
				String state = order.getState();
				if(OrdersConstants.OrdOrder.State.FINISH_REFUND.equals(state)||
						OrdersConstants.OrdOrder.State.EXCHANGE_AUDIT.equals(state)||
						OrdersConstants.OrdOrder.State.REFUND_AUDIT.equals(state)||
						OrdersConstants.OrdOrder.State.AUDIT_FAILURE.equals(state)||
						OrdersConstants.OrdOrder.State.AUDIT_AGAIN_FAILURE.equals(state)) { //表示售后订单为已完成状态,审核失败
					flag=true;
				}else {
					flag=false;
					break;
				}
			}
			if(CollectionUtil.isEmpty(orderList)||flag) {
				OrdOrder order = ordOrderAtomSV.selectByOrderId(request.getTenantId(), 
						ordOrder.getOrigOrderId());
				order.setState(OrdersConstants.OrdOrder.State.COMPLETED);
				ordOrderAtomSV.updateById(order);
				//判断父订单下的其它子订单状态  
				// 完成则为 父订单完成,否则父订单不变
				boolean stateFlag = this.judgeState(order);
				if(stateFlag) {
					OrdOrder parentOrder = ordOrderAtomSV.selectByOrderId(request.getTenantId(), 
							order.getParentOrderId());
					parentOrder.setState(OrdersConstants.OrdOrder.State.COMPLETED);
					ordOrderAtomSV.updateById(parentOrder); 
				}
			}
		}else {
			ordOrder.setState(OrdersConstants.OrdOrder.State.WAIT_REPAY);
			ordOrderAtomSV.updateById(ordOrder);
			//this.ordOrderAtomSV.updateStateByOrderId(tenantId, orderId, OrdersConstants.OrdOrder.State.WAIT_REPAY);
		}
		
		// 刷新搜索引擎数据
    	SesDataRequest sesReq=new SesDataRequest();
    	sesReq.setTenantId(request.getTenantId());
    	sesReq.setParentOrderId(ordOrder.getParentOrderId());
    	this.orderIndexBusiSV.insertSesData(sesReq);
		
		return response;
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
    
    /**
     * 买家退换货刷新es引擎数据
     * @param ordOrder
     * @param afterLogistics
     * @throws BusinessException
     * @throws SystemException
     * @author caofz
     * @ApiDocMethod
     * @ApiCode 
     * @RestRelativeURL
     */
    public void refreshData(OrdOrder ordOrder,OrdOdLogistics afterLogistics) 
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
		List<OrdProdExtend> ordextendes = orderInfo.getOrdextendes();
		for (OrdProdExtend ordProdExtend : ordextendes) {
			if(ordOrder.getOrderId()==ordProdExtend.getOrderid()) {
				ordProdExtend.setState(ordOrder.getState());
				//订单状态翻译
				SysParam sysParamState = InfoTranslateUtil.translateInfo(ordOrder.getTenantId(),
						"ORD_ORDER", "STATE",ordOrder.getState(), iCacheSV);
				ordProdExtend.setStatename(sysParamState == null ? "" : sysParamState.getColumnDesc());
				//更新买家退换货信息
				StringBuffer sbf = new StringBuffer();
				sbf.append(afterLogistics.getProvinceCode() == null ? ""
						: iCacheSV.getAreaName(afterLogistics.getProvinceCode()));
				sbf.append(afterLogistics.getCityCode() == null ? ""
						: iCacheSV.getAreaName(afterLogistics.getCityCode()));
				sbf.append(afterLogistics.getCountyCode() == null ? ""
						: iCacheSV.getAreaName(afterLogistics.getCountyCode()));
				sbf.append(afterLogistics.getAddress());
				ordProdExtend.setAftercontactTel(afterLogistics.getContactTel());
				ordProdExtend.setAftercontactinfo(sbf.toString());
			}
		}
		ESClientManager.getSesClient(SearchConstants.SearchNameSpace).bulkInsert(ordList);
	}
}
