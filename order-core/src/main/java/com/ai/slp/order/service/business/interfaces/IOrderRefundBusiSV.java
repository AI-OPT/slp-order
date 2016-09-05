package com.ai.slp.order.service.business.interfaces;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.slp.order.api.orderrefund.param.OrderFullRefundRequest;
import com.ai.slp.order.api.orderrefund.param.OrderPartRefundRequest;
import com.ai.slp.order.api.orderrefund.param.OrderRefuseRefundRequest;

public interface IOrderRefundBusiSV {
	
	public BaseResponse fullRefund(OrderFullRefundRequest request) throws BusinessException, SystemException;

	public BaseResponse partRefund(OrderPartRefundRequest request) throws BusinessException, SystemException;
	
	public BaseResponse refuseRefund(OrderRefuseRefundRequest request) throws BusinessException, SystemException;
}
