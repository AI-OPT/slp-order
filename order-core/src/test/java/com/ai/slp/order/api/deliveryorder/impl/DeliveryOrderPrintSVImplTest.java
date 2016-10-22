package com.ai.slp.order.api.deliveryorder.impl;

import com.ai.slp.order.api.deliveryorderprint.interfaces.IDeliveryOrderPrintSV;
import com.ai.slp.order.api.deliveryorderprint.param.DeliveryOrderPrintRequest;
import com.ai.slp.order.api.deliveryorderprint.param.DeliveryOrderQueryResponse;
import com.alibaba.fastjson.JSON;

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
		request.setOrderId(334570391232l);
		request.setUserId("000000000000000945");
		request.setTenantId("changhong");
		DeliveryOrderQueryResponse response = deliveryOrderPrintSV.query(request);
		System.out.println(response);
	}
	
	
	@Test
	public void testPrint() {
		
		DeliveryOrderPrintRequest request=new DeliveryOrderPrintRequest();
		request.setOrderId(59612493l);
		request.setUserId("000000000000000945");
		request.setTenantId("changhong");
		
		//BaseResponse response = deliveryOrderPrintSV.print(request);
		//System.out.println(response);
	}
	
	@Test
	public void testDisplay() {
		DeliveryOrderPrintRequest request=new DeliveryOrderPrintRequest();
		request.setOrderId(334570391232l);
		request.setUserId("000000000000000945");
		request.setTenantId("changhong");
		System.out.println(JSON.toJSONString(request));
		deliveryOrderPrintSV.display(request);
		//System.out.println(response);
	}
	
	@Test
	public void testNoMergePrint() {
		DeliveryOrderPrintRequest request=new DeliveryOrderPrintRequest();
		request.setOrderId(2000001011472557l);
		request.setUserId("1");
		request.setTenantId("changhong");
		System.out.println(JSON.toJSONString(request));
		deliveryOrderPrintSV.noMergePrint(request);
		//System.out.println(response);
	}

}
