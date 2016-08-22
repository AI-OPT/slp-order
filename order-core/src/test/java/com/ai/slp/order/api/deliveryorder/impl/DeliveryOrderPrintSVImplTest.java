package com.ai.slp.order.api.deliveryorder.impl;

import com.ai.slp.order.api.deliveryorderprint.interfaces.IDeliveryOrderPrintSV;
import com.ai.slp.order.api.deliveryorderprint.param.DeliveryOrderPrintRequest;
import com.ai.slp.order.api.deliveryorderprint.param.DeliveryOrderQueryResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/context/core-context.xml" })
public class DeliveryOrderPrintSVImplTest {
	
	@Autowired
	private IDeliveryOrderPrintSV deliveryOrderPrintSV;
	
	@Test
	public void testQuery() {
		DeliveryOrderPrintRequest request=new DeliveryOrderPrintRequest();
		request.setOrderId(35913355l);
		request.setUserId("000000000000000945");
		request.setTenantId("SLP");
		DeliveryOrderQueryResponse response = deliveryOrderPrintSV.query(request);
		System.out.println(response);
	}
	
	
	@Test
	public void testPrint() {
		
		DeliveryOrderPrintRequest request=new DeliveryOrderPrintRequest();
		request.setOrderId(35913355l);
		request.setUserId("000000000000000945");
		request.setTenantId("SLP");
		
		//BaseResponse response = deliveryOrderPrintSV.print(request);
		//System.out.println(response);
	}
	
	@Test
	public void testDisplay() {
		DeliveryOrderPrintRequest request=new DeliveryOrderPrintRequest();
		request.setOrderId(35913355l);
		request.setUserId("000000000000000945");
		request.setTenantId("SLP");
		deliveryOrderPrintSV.display(request);
		//System.out.println(response);
	}

}
