package com.ai.slp.order.service.business.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.util.DateUtil;
import com.ai.slp.order.api.orderconfirm.param.OrderConfirmRequest;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.constants.OrdersConstants.OrdOdStateChg;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.ai.slp.order.service.business.interfaces.IOrderConfirmBusiSV;
import com.ai.slp.order.service.business.interfaces.search.IOrderIndexBusiSV;
import com.ai.slp.order.util.OrderStateChgUtil;

@Service
@Transactional
public class OrderConfirmBusiSVImpl implements IOrderConfirmBusiSV {
	
	@Autowired
	private IOrdOrderAtomSV ordOrderAtomSV;
	@Autowired
	IOrderIndexBusiSV orderIndexBusiSV;
	
	/**
	 * 订单确认
	 */
	@Override
	public void confirm(OrderConfirmRequest request,OrdOrder ordOrder)
			throws BusinessException, SystemException {
		/* 1.更新订单状态*/
		this.updateOrderState(ordOrder);
	}
	
	/**
	 * 状态修改
	 */
	private void updateOrderState(OrdOrder ordOrder) {
		OrdOrder pOrder=null;
        String orgState = ordOrder.getState();
        Timestamp sysDate = DateUtil.getSysDate();
        if(!OrdersConstants.OrdOrder.State.WAIT_CONFIRM.equals(orgState)) {
        	throw new BusinessException("", "订单的状态不是待确认,无法修改");
        }
        String newState1 = OrdersConstants.OrdOrder.State.FINISH_CONFIRMED;
        String newState2 = OrdersConstants.OrdOrder.State.COMPLETED;
        ordOrder.setState(newState2);
        ordOrder.setStateChgTime(sysDate);
        
        //异步  子订单写入订单状态变化轨迹表 (待确认-已确认)
        OrderStateChgUtil.trailProcess(ordOrder.getOrderId(), ordOrder.getTenantId(), orgState, newState1,
        		OrdOdStateChg.ChgDesc.ORDER_TO_FINISH_CONFIRM, null, null, null, sysDate);
        ordOrderAtomSV.updateOrderState(ordOrder);
        //异步 子订单写入订单状态变化轨迹表 (已确认-完成)
        OrderStateChgUtil.trailProcess(ordOrder.getOrderId(), ordOrder.getTenantId(), newState1, newState2,
                OrdOdStateChg.ChgDesc.ORDER_TO_COMPLETED, null, null, null, sysDate);
        //父订单下的其它子订单
        List<OrdOrder> childOrders = ordOrderAtomSV.selectOtherOrders(ordOrder);
        boolean flag=true;
	    if(!CollectionUtil.isEmpty(childOrders)) {
	    	for (OrdOrder order : childOrders) {
	    		//其它子订单状态不是'完成'
				if(!newState2.equals(order.getState())) {
					flag=false;
					break;
				}
			}
	    }
	    if(flag) {
	    	//父订单
	    	pOrder = ordOrderAtomSV.selectByOrderId(ordOrder.getTenantId(), ordOrder.getParentOrderId());
	    	String pOldstate = pOrder.getState();
	    	pOrder.setState(newState2);
	    	pOrder.setStateChgTime(sysDate);
	    	ordOrderAtomSV.updateOrderState(pOrder);
	    	//父订单写入订单状态变化轨迹表 (已支付-完成)
	        OrderStateChgUtil.trailProcess(pOrder.getOrderId(), ordOrder.getTenantId(), pOldstate, newState2,
	    			OrdOdStateChg.ChgDesc.ORDER_TO_COMPLETED, null, null, null, sysDate);
	    } 
	  //写入搜索引擎
	  orderIndexBusiSV.refreshStateData(ordOrder,pOrder);
    }

}
