package com.ai.slp.order.api.deliveryorder.impl;


import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ai.slp.order.api.invoiceprint.interfaces.IInvoicePrintSV;
import com.ai.slp.order.api.invoiceprint.param.InvoicePrintInfosRequest;
import com.ai.slp.order.api.invoiceprint.param.InvoicePrintRequest;
import com.ai.slp.order.api.invoiceprint.param.InvoicePrintResponse;
import com.ai.slp.order.api.invoiceprint.param.InvoicePrintVo;
import com.alibaba.fastjson.JSON;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/context/core-context.xml" })
public class InvoiceTest {
	
	@Autowired
	private IInvoicePrintSV invoicePrintSV;

	@Test
	public void testQuery() {
		InvoicePrintRequest request=new InvoicePrintRequest();
		request.setOrderId(59612493l);
		request.setTenantId("changhong");
		System.out.println(JSON.toJSONString(request));
		InvoicePrintResponse response = invoicePrintSV.query(request);
		System.out.println(JSON.toJSONString(response));
	}
	
	
	@Test
	public void testPrint() {
		InvoicePrintInfosRequest request=new InvoicePrintInfosRequest();
		List<InvoicePrintVo> invoicePrintVos=new ArrayList<InvoicePrintVo>();
		InvoicePrintVo invoicePrintVo=new InvoicePrintVo();
		request.setOrderId(59612493l);
		request.setTenantId("changhong");
		System.out.println(JSON.toJSONString(request));
		invoicePrintSV.print(request);
	}

}
