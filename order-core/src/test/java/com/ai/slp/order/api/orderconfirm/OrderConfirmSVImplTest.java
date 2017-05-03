package com.ai.slp.order.api.orderconfirm;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ai.slp.order.api.orderconfirm.interfaces.IOrderConfirmSV;
import com.ai.slp.order.api.orderconfirm.param.OrderConfirmRequest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/context/core-context.xml" })
public class OrderConfirmSVImplTest {
	
	@Autowired
	private IOrderConfirmSV orderConfirmSV;
	@Test
	public void test() {
		OrderConfirmRequest request=new OrderConfirmRequest();
		request.setOrderId(2000034409242775l);
		request.setTenantId("changhong");
		orderConfirmSV.confirm(request);
	}

}
