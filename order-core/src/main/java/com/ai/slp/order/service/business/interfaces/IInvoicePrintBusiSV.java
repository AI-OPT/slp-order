package com.ai.slp.order.service.business.interfaces;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.slp.order.api.invoiceprint.param.InvoicePrintRequest;
import com.ai.slp.order.api.invoiceprint.param.InvoicePrintResponse;

public interface IInvoicePrintBusiSV {
	
	public InvoicePrintResponse print(InvoicePrintRequest request) throws BusinessException, SystemException;

}
