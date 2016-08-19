package com.ai.slp.order.service.business.interfaces;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.slp.order.api.deliveryorderprint.param.DeliveryOrderPrintRequest;
import com.ai.slp.order.api.deliveryorderprint.param.DeliveryOrderPrintResponse;

public interface IDeliveryOrderPrintBusiSV {
	
	public void print(DeliveryOrderPrintRequest request) throws BusinessException, SystemException;

	public DeliveryOrderPrintResponse query(DeliveryOrderPrintRequest request);

}
