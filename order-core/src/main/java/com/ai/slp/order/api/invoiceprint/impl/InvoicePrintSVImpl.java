package com.ai.slp.order.api.invoiceprint.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.slp.order.api.invoiceprint.interfaces.IInvoicePrintSV;
import com.ai.slp.order.api.invoiceprint.param.InvoicePrintInfosRequest;
import com.ai.slp.order.api.invoiceprint.param.InvoicePrintRequest;
import com.ai.slp.order.api.invoiceprint.param.InvoicePrintResponse;
import com.ai.slp.order.service.business.interfaces.IInvoicePrintBusiSV;
import com.alibaba.dubbo.config.annotation.Service;

@Service(validation="true")
@Component
public class InvoicePrintSVImpl implements IInvoicePrintSV{
	
	@Autowired
	private IInvoicePrintBusiSV invoicePrintBusiSV;

	@Override
	public InvoicePrintResponse query(InvoicePrintRequest request) throws BusinessException, SystemException {
		InvoicePrintResponse response = invoicePrintBusiSV.invoiceQuery(request);
		ResponseHeader header=new ResponseHeader(true, ExceptCodeConstants.Special.SUCCESS, "成功");
		response.setResponseHeader(header);
		return response;
	}

	@Override
	public BaseResponse print(InvoicePrintInfosRequest request) throws BusinessException, SystemException {
		BaseResponse response = new BaseResponse();
		invoicePrintBusiSV.invoicePrint(request);
		ResponseHeader header=new ResponseHeader(true, ExceptCodeConstants.Special.SUCCESS, "成功");
		response.setResponseHeader(header);
		return response;
	}

}
