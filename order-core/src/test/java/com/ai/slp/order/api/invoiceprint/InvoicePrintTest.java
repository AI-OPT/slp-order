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
		//request.setOrderId(334570392323l);
		//request.setInvoiceTitle("在线");
		//request.setInvoiceStatus("1");
		InvoicePrintResponse response = invoicePrintSV.queryList(request);
		System.out.println(JSON.toJSONString(response));
	}
	
	@Test
	public void testUpdate() {
		InvoiceNoticeRequest request=new InvoiceNoticeRequest();
		request.setInvoiceId("1212");
		request.setCompanyId(-1l);
		request.setInvoiceNum("1212");
		request.setInvoiceStatus("1");
		request.setInvoiceTime("2016-10-090");
		request.setInvoiceTotalFee(0l);
		request.setOrderId(334570392323l);
		request.setProofItemNum("222");
		request.setInvoiceStatus("3");
		System.out.println(JSON.toJSON(request));
		invoicePrintSV.updateInvoiceStatus(request);
	}
	
	@Test
	public void testInvoiceSubmit() {
		InvoiceSubmitRequest request=new InvoiceSubmitRequest();
		request.setOrderId(2000001003796151l);
		request.setTenantId("changhong");
		InvoiceSumbitResponse response = invoicePrintSV.invoiceSubmit(request);
		System.out.println(JSON.toJSON(response));
	}
	

}
