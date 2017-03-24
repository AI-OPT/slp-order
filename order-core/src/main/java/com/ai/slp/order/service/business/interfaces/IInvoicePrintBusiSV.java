package com.ai.slp.order.service.business.interfaces;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.slp.order.api.invoiceprint.param.InvoiceModifyRequest;
import com.ai.slp.order.api.invoiceprint.param.InvoiceNoticeRequest;
import com.ai.slp.order.api.invoiceprint.param.InvoicePrintRequest;
import com.ai.slp.order.api.invoiceprint.param.InvoicePrintResponse;
import com.ai.slp.order.api.invoiceprint.param.InvoiceSubmitRequest;
import com.ai.slp.order.api.invoiceprint.param.InvoiceSumbitResponse;
import com.ai.slp.order.dao.mapper.bo.OrdOdInvoice;

public interface IInvoicePrintBusiSV {
	
	//发票打印列表查看
	public InvoicePrintResponse queryList(InvoicePrintRequest request) throws BusinessException, SystemException;
	//发票回调,状态修改
	public void updateInvoiceStatus(InvoiceNoticeRequest request,OrdOdInvoice ordOdInvoice) throws BusinessException, SystemException;
	//发票报送(打印)
	public InvoiceSumbitResponse invoiceSubmit(InvoiceSubmitRequest request) throws BusinessException, SystemException;
	//状态修改
	public void modifyState(InvoiceModifyRequest request,OrdOdInvoice ordOdInvoice) throws BusinessException, SystemException;;
}
