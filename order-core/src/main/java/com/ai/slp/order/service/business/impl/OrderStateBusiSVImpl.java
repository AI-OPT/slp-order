package com.ai.slp.order.service.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.slp.order.api.orderstate.param.WaitRebateRequest;
import com.ai.slp.order.api.orderstate.param.WaitRebateResponse;
import com.ai.slp.order.api.orderstate.param.WaitSellReceiveSureRequest;
import com.ai.slp.order.api.orderstate.param.WaitSellReceiveSureResponse;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.dao.mapper.bo.OrdOdLogistics;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.dao.mapper.bo.OrdOrderCriteria;
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
		OrdOrder ordOrder = ordOrderAtomSV.selectByOrderId(tenantId, orderId);
		ordOrder.setState(OrdersConstants.OrdOrder.State.WAIT_RECEIPT_CONFIRMATION);
		this.ordOrderAtomSV.updateById(ordOrder);
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
			OrdOrderCriteria example=new OrdOrderCriteria();
			OrdOrderCriteria.Criteria criteria = example.createCriteria();
			criteria.andOrigOrderIdEqualTo(ordOrder.getOrigOrderId());
			criteria.andOrderIdNotEqualTo(request.getOrderId());
			List<OrdOrder> orderList = ordOrderAtomSV.selectByExample(example);
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
				OrdOrder parentOrder = ordOrderAtomSV.selectByOrderId(request.getTenantId(), 
						order.getParentOrderId());
				parentOrder.setState(OrdersConstants.OrdOrder.State.COMPLETED);
				ordOrderAtomSV.updateById(parentOrder);
			}
		}else {
			ordOrder.setState(OrdersConstants.OrdOrder.State.WAIT_REPAY);
			ordOrderAtomSV.updateById(ordOrder);
			//this.ordOrderAtomSV.updateStateByOrderId(tenantId, orderId, OrdersConstants.OrdOrder.State.WAIT_REPAY);
		}
		return response;
	}

}
