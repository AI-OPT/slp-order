package com.ai.slp.order.service.business.interfaces;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.slp.order.api.invoiceprint.param.InvoicePrintInfosRequest;
import com.ai.slp.order.api.invoiceprint.param.InvoicePrintRequest;
import com.ai.slp.order.api.invoiceprint.param.InvoicePrintResponse;

public interface IInvoicePrintBusiSV {
	
	public InvoicePrintResponse invoiceQuery(InvoicePrintRequest request) throws BusinessException, SystemException;
	
	public void invoicePrint(InvoicePrintInfosRequest request) throws BusinessException, SystemException;

}
