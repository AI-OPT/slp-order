package com.ai.slp.order.api.deliveryorder.impl;

import com.ai.opt.base.vo.BaseResponse;
import com.ai.slp.order.api.deliveryorderprint.interfaces.IDeliveryOrderPrintSV;
import com.ai.slp.order.api.deliveryorderprint.param.DeliveryOrderPrintRequest;
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
	public void test() {
		DeliveryOrderPrintRequest request=new DeliveryOrderPrintRequest();
		request.setOrderId(35913355l);
		request.setUserId("000000000000000945");
		request.setTenantId("SLP");
		BaseResponse response = deliveryOrderPrintSV.print(request);
		System.out.println(response);
	}

}
