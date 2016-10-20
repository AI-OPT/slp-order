package com.ai.slp.order.service.business.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.slp.order.api.orderstate.param.WaitRebateRequest;
import com.ai.slp.order.api.orderstate.param.WaitRebateResponse;
import com.ai.slp.order.api.orderstate.param.WaitSellReceiveSureRequest;
import com.ai.slp.order.api.orderstate.param.WaitSellReceiveSureResponse;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.dao.mapper.bo.OrdOdLogistics;
import com.ai.slp.order.service.atom.interfaces.IOrdOdLogisticsAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.ai.slp.order.service.business.interfaces.IOrderStateBusiSV;
import com.ai.slp.order.util.SequenceUtil;
@Service
public class OrderStateBusiSVImpl implements IOrderStateBusiSV {

	@Autowired
	private IOrdOrderAtomSV ordOrderAtomSV;
	@Autowired
	private IOrdOdLogisticsAtomSV ordOdLogisticsAtomSV;
	
	@Override
	@Transactional
	public WaitSellReceiveSureResponse updateWaitSellRecieveSureState(WaitSellReceiveSureRequest request) {
		WaitSellReceiveSureResponse response = new WaitSellReceiveSureResponse();
		//
		String tenantId = request.getTenantId();
		Long orderId = request.getOrderId();
		String expressId = request.getExpressId();
		String expressOddNumber = request.getExpressOddNumber();
		//
		this.ordOrderAtomSV.updateStateByOrderId(tenantId, orderId, OrdersConstants.OrdOrder.State.WAIT_RECEIPT_CONFIRMATION);
		//
		OrdOdLogistics ordOdLogistics = new OrdOdLogistics();
		ordOdLogistics.setLogisticsId(SequenceUtil.genLogisticsId());
		ordOdLogistics.setTenantId(tenantId);
		ordOdLogistics.setOrderId(orderId);
		ordOdLogistics.setExpressId(expressId);
		ordOdLogistics.setExpressOddNumber(expressOddNumber);
		ordOdLogistics.setLogisticsType("0");
		//
		this.ordOdLogisticsAtomSV.insertSelective(ordOdLogistics);
		//
		return response;
	}

	@Override
	@Transactional
	public WaitRebateResponse updateWaitRebateState(WaitRebateRequest request) {
		WaitRebateResponse response = new WaitRebateResponse();
		//
		String tenantId = request.getTenantId();
		Long orderId = request.getOrderId();
		//
		this.ordOrderAtomSV.updateStateByOrderId(tenantId, orderId, OrdersConstants.OrdOrder.State.WAIT_REPAY);
		//
		return response;
	}

}
