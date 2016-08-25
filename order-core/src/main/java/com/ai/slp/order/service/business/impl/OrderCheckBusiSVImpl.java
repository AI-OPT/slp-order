package com.ai.slp.order.service.business.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.opt.sdk.util.DateUtil;
import com.ai.slp.order.api.aftersaleorder.impl.OrderAfterSaleSVImpl;
import com.ai.slp.order.api.ordercheck.param.OrderCheckRequest;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.constants.OrdersConstants.OrdOdStateChg;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.ai.slp.order.service.business.interfaces.IOrderCheckBusiSV;
import com.ai.slp.order.service.business.interfaces.IOrderFrameCoreSV;
import com.ai.slp.order.util.CommonCheckUtils;

@Service
@Transactional
public class OrderCheckBusiSVImpl implements IOrderCheckBusiSV {
	
	private static final Logger logger=LoggerFactory.getLogger(OrderAfterSaleSVImpl.class);
	
	@Autowired
	private IOrdOrderAtomSV ordOrderAtomSV;
	
	@Autowired
	private IOrderFrameCoreSV orderFrameCoreSV;

	@Override
	public void check(OrderCheckRequest request) throws BusinessException, SystemException {
		/* 租户非空校验*/
		CommonCheckUtils.checkTenantId(request.getTenantId(), ExceptCodeConstants.Special.PARAM_IS_NULL);
		/* 订单id非空检验*/
		if(request.getOrderId()==0) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "订单id不能为空");
		}
		OrdOrder ordOrder = ordOrderAtomSV.selectByOrderId(request.getTenantId(), request.getOrderId());
		if(ordOrder==null) {
			logger.error("未能查询到相对应的订单信息[订单id:"+request.getOrderId()+"租户id:"+request.getTenantId()+"]");
			throw new BusinessException(ExceptCodeConstants.Special.NO_RESULT, 
					"未能查询到相对应的订单信息[订单id:"+request.getOrderId()+"租户id:"+request.getTenantId()+"]");
		}
		if(OrdersConstants.OrderCheck.result.OK.equals(request.getCheckResult())) {//表示审核通过
			String orgState = ordOrder.getState();
			String newState=OrdersConstants.OrdOrder.State.REVOKE_FINISH_AUDITED;
			this.updateOrderState(ordOrder, newState,request);
			// 写入订单状态变化轨迹表
	        orderFrameCoreSV.ordOdStateChg(ordOrder.getOrderId(), ordOrder.getTenantId(), orgState, newState,
	                OrdOdStateChg.ChgDesc.ORDER_TO_AUDIT, null, request.getOperId(), null, DateUtil.getSysDate());
		}else {
			//审核拒绝
			String orgState = ordOrder.getState();
			String newState=OrdersConstants.OrdOrder.State.AUDIT_FAILURE;
			this.updateOrderState(ordOrder, newState,request);
			// 写入订单状态变化轨迹表
	        orderFrameCoreSV.ordOdStateChg(ordOrder.getOrderId(), ordOrder.getTenantId(), orgState, newState,
	                OrdOdStateChg.ChgDesc.ORDER_AUDIT_NOT_PASS, null, request.getOperId(), null, DateUtil.getSysDate());
		}
	}
	
	
	/**
     * 更新订单状态
     */
    private void updateOrderState(OrdOrder ordOrder,String newState,OrderCheckRequest request) {
        ordOrder.setState(newState);
        ordOrder.setStateChgTime(DateUtil.getSysDate());
        ordOrder.setRemark(request.getRemark());
        ordOrderAtomSV.updateById(ordOrder);
    }
	
}
