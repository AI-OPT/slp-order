package com.ai.slp.order.api.deliveryorder.impl;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ai.slp.order.api.invoiceprint.interfaces.IInvoicePrintSV;
import com.ai.slp.order.api.invoiceprint.param.InvoicePrintRequest;
import com.ai.slp.order.api.invoiceprint.param.InvoicePrintResponse;
import com.alibaba.fastjson.JSON;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/context/core-context.xml" })
public class InvoiceTest {
	
	@Autowired
	private IInvoicePrintSV invoicePrintSV;

	@Test
	public void test() {
		InvoicePrintRequest request=new InvoicePrintRequest();
		request.setOrderId(35913355l);
		request.setTenantId("SLP");
		InvoicePrintResponse response = invoicePrintSV.query(request);
		System.out.println(JSON.toJSONString(response));
	}

}
