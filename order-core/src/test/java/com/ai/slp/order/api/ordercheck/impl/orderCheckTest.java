package com.ai.slp.order.api.ordercheck.impl;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ai.slp.order.api.ordercheck.interfaces.IOrderCheckSV;
import com.ai.slp.order.api.ordercheck.param.OrderCheckRequest;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/context/core-context.xml"})
public class orderCheckTest {
	
	@Autowired
	IOrderCheckSV orderCheckSV;
	@Test
	public void test() {
		
		OrderCheckRequest request=new OrderCheckRequest();
		request.setOrderId(35913355l);
		request.setCheckResult("1");
		request.setTenantId("SLP");
		request.setOperId("12312");
		orderCheckSV.check(request);
		
	}

}
