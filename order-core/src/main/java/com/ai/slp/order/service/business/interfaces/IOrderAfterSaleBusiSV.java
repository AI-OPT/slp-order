package com.ai.slp.order.service.business.interfaces;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.slp.order.api.aftersaleorder.param.OrderReturnRequest;

public interface IOrderAfterSaleBusiSV {
	
	public void back(OrderReturnRequest request) throws BusinessException, SystemException;
	
	public void exchange(OrderReturnRequest request) throws BusinessException, SystemException; 

	public void refund(OrderReturnRequest request) throws BusinessException, SystemException;
}
