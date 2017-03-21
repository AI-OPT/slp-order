package com.ai.slp.order.service.business.interfaces;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.slp.order.api.orderconfirm.param.OrderConfirmRequest;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;

public interface IOrderConfirmBusiSV {
	
	public void confirm(OrderConfirmRequest request,OrdOrder ordOrder) throws BusinessException, SystemException;
}
