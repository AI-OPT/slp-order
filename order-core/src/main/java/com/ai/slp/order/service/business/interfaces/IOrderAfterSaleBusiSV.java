package com.ai.slp.order.service.business.interfaces;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.slp.order.api.aftersaleorder.param.OrderReturnRequest;
import com.ai.slp.order.dao.mapper.bo.OrdOdProd;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;

public interface IOrderAfterSaleBusiSV {
	
	public void back(OrderReturnRequest request,OrdOrder order,
			OrdOdProd ordOdProd) throws BusinessException, SystemException;
	
	public void exchange(OrderReturnRequest request,OrdOrder order,
			OrdOdProd ordOdProd) throws BusinessException, SystemException; 

	public void refund(OrderReturnRequest request,OrdOrder order,
			OrdOdProd ordOdProd) throws BusinessException, SystemException;

	public void backStateOFC(OrdOrder ordOrder)
			throws BusinessException, SystemException;
}
