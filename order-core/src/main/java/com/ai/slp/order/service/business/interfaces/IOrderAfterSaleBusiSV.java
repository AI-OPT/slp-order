package com.ai.slp.order.service.business.interfaces;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.slp.order.api.aftersaleorder.param.OrderReturnRequest;
import com.ai.slp.order.dao.mapper.bo.OrdOdProd;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;

public interface IOrderAfterSaleBusiSV {
	
	//订单退货申请处理
	public void back(OrderReturnRequest request,OrdOrder order,
			OrdOdProd ordOdProd) throws BusinessException, SystemException;
	//订单换货申请处理
	public void exchange(OrderReturnRequest request,OrdOrder order,
			OrdOdProd ordOdProd) throws BusinessException, SystemException; 
	//订单退款申请处理
	public void refund(OrderReturnRequest request,OrdOrder order,
			OrdOdProd ordOdProd) throws BusinessException, SystemException;
	//ofc售后订单状态回传
	public void backStateOFC(OrdOrder ordOrder)
			throws BusinessException, SystemException;
}
