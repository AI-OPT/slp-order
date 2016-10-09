package com.ai.slp.order.api.invoiceprint.interfaces;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.slp.order.api.invoiceprint.param.InvoiceNoticeRequest;
import com.ai.slp.order.api.invoiceprint.param.InvoicePrintRequest;
import com.ai.slp.order.api.invoiceprint.param.InvoicePrintResponse;
import com.ai.slp.order.api.invoiceprint.param.InvoiceSubmitRequest;
import com.ai.slp.order.api.invoiceprint.param.InvoiceSumbitResponse;

/**
 * 发票打印服务
 * @author caofz
 *
 */
@Path("/invoice")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
public interface IInvoicePrintSV {


	/**
	 * 发票打印列表查看
	 * @param request
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 * @author caofz
	 * @ApiDocMethod
	 * @ApiCode INVOICE_QUERY_001
	 * @RestRelativeURL invoice/queryList
	 */
	@POST
	@Path("/queryList")
	public InvoicePrintResponse queryList(InvoicePrintRequest request) throws BusinessException,SystemException;
	
	/**
	 * 发票回调,状态修改
	 * @param request
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 * @author caofz
	 * @ApiDocMethod
	 * @ApiCode INVOICE_UPDATE_001
	 * @RestRelativeURL invoice/updateInvoiceStatus
	 */
	@POST
	@Path("/updateInvoiceStatus")
	public BaseResponse updateInvoiceStatus(InvoiceNoticeRequest request) throws BusinessException,SystemException;
	
	/**
	 * 发票报送(打印)
	 * @param request
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 * @author caofz
	 * @ApiDocMethod
	 * @ApiCode INVOICE_SUBMIT_001
	 * @RestRelativeURL invoice/invoiceSubmit
	 */
	@POST
	@Path("/invoiceSubmit")
	public InvoiceSumbitResponse invoiceSubmit(InvoiceSubmitRequest request) throws BusinessException,SystemException;
	
}
