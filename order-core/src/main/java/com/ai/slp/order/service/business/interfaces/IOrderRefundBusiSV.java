package com.ai.slp.order.service.business.interfaces;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.slp.order.api.orderrefund.param.OrderRefundRequest;
import com.ai.slp.order.api.orderrefund.param.OrderRefuseRefundRequest;

public interface IOrderRefundBusiSV {
	
	public void fullRefund(OrderRefundRequest request) throws BusinessException, SystemException;

	public void partRefund(OrderRefundRequest request) throws BusinessException, SystemException;
	
	public void refuseRefund(OrderRefuseRefundRequest request) throws BusinessException, SystemException;
}
