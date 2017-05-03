package com.ai.slp.order.api.invoiceprint;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ai.slp.order.api.invoiceprint.interfaces.IInvoicePrintSV;
import com.ai.slp.order.api.invoiceprint.param.InvoiceNoticeRequest;
import com.ai.slp.order.api.invoiceprint.param.InvoicePrintRequest;
import com.ai.slp.order.api.invoiceprint.param.InvoicePrintResponse;
import com.ai.slp.order.api.invoiceprint.param.InvoiceSubmitRequest;
import com.ai.slp.order.api.invoiceprint.param.InvoiceSumbitResponse;
import com.alibaba.fastjson.JSON;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/context/core-context.xml" })
public class InvoicePrintTest {
	
	@Autowired
	private IInvoicePrintSV invoicePrintSV;
	@Test
	public void testQuery() {
		InvoicePrintRequest request=new InvoicePrintRequest();
		request.setPageNo(1);
		request.setPageSize(5);
		request.setTenantId("changhong");
	//request.setOrderId(null);
	//	request.setInvoiceTitle("长虹");
	//	request.setInvoiceStatus("2");
		InvoicePrintResponse response = invoicePrintSV.queryList(request);
		System.out.println(JSON.toJSONString(response));
	}
	
	@Test
	public void testUpdate() {
		
		InvoiceNoticeRequest request=new InvoiceNoticeRequest();
		request.setInvoiceId("1212");
		request.setCompanyId(1300l);
		request.setInvoiceNum("1313");
		request.setInvoiceTime("2017-04-17");
		request.setInvoiceTotalFee(48.5);
		request.setOrderId(20000343286707821l);
		request.setProofItemNum("1414");
		request.setInvoiceStatus("3");
		System.out.println(JSON.toJSON(request));
		invoicePrintSV.updateInvoiceStatus(request);
	}
	
	@Test
	public void testInvoiceSubmit() {
		InvoiceSubmitRequest request=new InvoiceSubmitRequest();
		request.setOrderId(2000001159582808l);
		request.setTenantId("changhong");
		InvoiceSumbitResponse response = invoicePrintSV.invoiceSubmit(request);
		System.out.println(JSON.toJSON(response));
	}
	

}
