package com.ai.slp.order.api.invoiceprint.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.slp.order.api.invoiceprint.interfaces.IInvoicePrintSV;
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
	public InvoicePrintResponse print(InvoicePrintRequest request) throws BusinessException, SystemException {
		InvoicePrintResponse response = invoicePrintBusiSV.print(request);
		ResponseHeader header=new ResponseHeader(true, ExceptCodeConstants.Special.SUCCESS, "成功");
		response.setResponseHeader(header);
		return response;
	}

}
