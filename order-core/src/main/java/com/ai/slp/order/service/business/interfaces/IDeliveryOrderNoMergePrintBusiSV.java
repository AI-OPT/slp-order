package com.ai.slp.order.service.business.interfaces;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.slp.order.api.deliveryorderprint.param.DeliveryOrderPrintRequest;
import com.ai.slp.order.api.deliveryorderprint.param.DeliveryOrderPrintResponse;

public interface IDeliveryOrderNoMergePrintBusiSV {
	
	public DeliveryOrderPrintResponse noMergePrint(DeliveryOrderPrintRequest request) throws BusinessException, SystemException;

}
