package com.ai.slp.order.service.business.interfaces;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.slp.order.api.orderconfirm.param.OrderConfirmRequest;

public interface IOrderConfirmBusiSV {
	
	public void confirm(OrderConfirmRequest request) throws BusinessException, SystemException;
}
