package com.ai.slp.order.api.ordercancelimpl;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ai.slp.order.api.ordercancel.impl.OrderCancelSVImpl;
import com.ai.slp.order.api.ordercancel.param.OrderCancelRequest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/context/core-context.xml"})
public class OrderCancelSVImplTest {
	
	@Autowired
	OrderCancelSVImpl orderCancelSVImpl;

	@Test
	public void test() {
		OrderCancelRequest cancelRequest=new OrderCancelRequest();
		cancelRequest.setTenantId("changhong");
		cancelRequest.setOrderId(2000001208051917l);
		orderCancelSVImpl.handCancelNoPayOrder(cancelRequest);
	}
	
	@Test
	public void test1() {
		orderCancelSVImpl.noPayOrderCancel();
	}

}
