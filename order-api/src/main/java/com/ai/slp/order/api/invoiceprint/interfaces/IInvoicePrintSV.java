package com.ai.slp.order.api.invoiceprint.interfaces;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.slp.order.api.invoiceprint.param.InvoicePrintRequest;
import com.ai.slp.order.api.invoiceprint.param.InvoicePrintResponse;

/**
 * 发货单打印服务
 * @author caofz
 *
 */
@Path("/invoice")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
public interface IInvoicePrintSV {
	

	/**
	 * 打印
	 * @param request
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 * @author caofz
	 * @ApiDocMethod
	 * @ApiCode 
	 * @RestRelativeURL /invoice/print
	 */
	@POST
	@Path("/print")
	public InvoicePrintResponse print(InvoicePrintRequest request) throws BusinessException,SystemException;
}
