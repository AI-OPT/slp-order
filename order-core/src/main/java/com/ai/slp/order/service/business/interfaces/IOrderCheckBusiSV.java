package com.ai.slp.order.service.business.interfaces;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.slp.order.api.ordercheck.param.OrderCheckRequest;

public interface IOrderCheckBusiSV {
	//订单审核
	public void check(OrderCheckRequest request) throws BusinessException, SystemException;
}
