package com.ai.slp.order.api.invoiceprint.interfaces;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.slp.order.api.invoiceprint.param.InvoicePrintInfosRequest;
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
	 * 发货打印查看
	 * @param request
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 * @author caofz
	 * @ApiDocMethod
	 * @ApiCode ORDER_INVOICE_001
	 * @RestRelativeURL invoice/query
	 */
	@POST
	@Path("/query")
	public InvoicePrintResponse query(InvoicePrintRequest request) throws BusinessException,SystemException;
	
	
	/**
	 * 发货打印
	 * @param request
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 * @author caofz
	 * @ApiDocMethod
	 * @ApiCode ORDER_INVOICE_002
	 * @RestRelativeURL invoice/print
	 */
	@POST
	@Path("/print")
	public BaseResponse print(InvoicePrintInfosRequest request) throws BusinessException,SystemException;
	
}
