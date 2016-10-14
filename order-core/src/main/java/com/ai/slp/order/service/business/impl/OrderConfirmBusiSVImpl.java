package com.ai.slp.order.service.business.impl;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.opt.sdk.util.DateUtil;
import com.ai.slp.order.api.orderconfirm.param.OrderConfirmRequest;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.constants.OrdersConstants.OrdOdStateChg;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.ai.slp.order.service.business.interfaces.IOrderConfirmBusiSV;
import com.ai.slp.order.service.business.interfaces.IOrderFrameCoreSV;
import com.ai.slp.order.util.CommonCheckUtils;

@Service
@Transactional
public class OrderConfirmBusiSVImpl implements IOrderConfirmBusiSV {
	
	private static final Logger logger=LoggerFactory.getLogger(OrderConfirmBusiSVImpl.class);
	
	@Autowired
	private IOrdOrderAtomSV ordOrderAtomSV;
	
	@Autowired
	private IOrderFrameCoreSV orderFrameCoreSV;
	
	@Override
	public void confirm(OrderConfirmRequest request) throws BusinessException, SystemException {
		/* 参数校验*/
		CommonCheckUtils.checkTenantId(request.getTenantId(), ExceptCodeConstants.Special.PARAM_IS_NULL);
		/* 订单非空检验*/
		if(request.getOrderId()==0) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "订单id不能为空");
		}
		OrdOrder ordOrder = ordOrderAtomSV.selectByOrderId(request.getTenantId(), request.getOrderId());
		if(ordOrder==null) {
			logger.error("订单信息不存在[订单id:"+request.getOrderId()+"租户id:"+request.getTenantId()+"]");
			throw new BusinessException(ExceptCodeConstants.Special.NO_RESULT, 
					"订单信息不存在[订单id:"+request.getOrderId()+"租户id:"+request.getTenantId()+"]");
		}
		this.updateOrderState(request.getTenantId(), DateUtil.getSysDate(), request.getOrderId());
	}
	
	
	private void updateOrderState(String tenantId, Timestamp sysDate, long orderId) {
        OrdOrder ordOrder = ordOrderAtomSV.selectByOrderId(tenantId, orderId);
        String orgState = ordOrder.getState();
        if(!OrdersConstants.OrdOrder.State.WAIT_CONFIRM.equals(orgState)) {
        	throw new BusinessException("", "订单的状态不是待确认,无法修改");
        }
        String newState1 = OrdersConstants.OrdOrder.State.FINISH_CONFIRMED;
        String newState2 = OrdersConstants.OrdOrder.State.COMPLETED;
        ordOrder.setState(newState2);
        ordOrder.setStateChgTime(sysDate);
        //子订单写入订单状态变化轨迹表 (待确认-已确认)
        orderFrameCoreSV.ordOdStateChg(orderId, tenantId, orgState, newState1,
        		OrdOdStateChg.ChgDesc.ORDER_TO_FINISH_CONFIRM, null, null, null, sysDate);
        ordOrderAtomSV.updateById(ordOrder);
        //子订单写入订单状态变化轨迹表 (已确认-完成)
        orderFrameCoreSV.ordOdStateChg(orderId, tenantId, newState1, newState2,
                OrdOdStateChg.ChgDesc.ORDER_TO_COMPLETED, null, null, null, sysDate);
        //父订单
        OrdOrder pOrder = ordOrderAtomSV.selectByOrderId(tenantId, ordOrder.getParentOrderId());
        String pOldstate = pOrder.getState();
        pOrder.setState(newState2);
        pOrder.setStateChgTime(sysDate);
        ordOrderAtomSV.updateById(pOrder);
        //父订单写入订单状态变化轨迹表 (已支付-完成)
        orderFrameCoreSV.ordOdStateChg(pOrder.getOrderId(), tenantId, pOldstate, newState2,
                OrdOdStateChg.ChgDesc.ORDER_TO_COMPLETED, null, null, null, sysDate);
    }

}
