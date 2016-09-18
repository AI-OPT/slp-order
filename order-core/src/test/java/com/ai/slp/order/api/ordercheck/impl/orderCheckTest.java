package com.ai.slp.order.api.ordercheck.impl;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ai.slp.order.api.ordercheck.interfaces.IOrderCheckSV;
import com.ai.slp.order.api.ordercheck.param.OrderCheckRequest;
import com.alibaba.fastjson.JSON;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/context/core-context.xml"})
public class orderCheckTest {
	
	@Autowired
	IOrderCheckSV orderCheckSV;
	@Test
	public void test() {
		OrderCheckRequest request=new OrderCheckRequest();
		request.setOrderId(2000000983277394l);
		request.setState("212");
		request.setTenantId("changhong");
		request.setOperId("12312");
		System.out.println(JSON.toJSONString(request));
		orderCheckSV.check(request);
	}

}
