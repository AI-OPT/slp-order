package com.ai.slp.order.api.aftersaleorder.impl;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ai.slp.order.api.aftersaleorder.interfaces.IOrderAfterSaleSV;
import com.ai.slp.order.api.aftersaleorder.param.OrderReturnRequest;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/context/core-context.xml" })
public class OrderAfterSaleSVImplTest {
	
	@Autowired
	private IOrderAfterSaleSV orderAfterSaleSV;
	@Test
	public void test() {
		OrderReturnRequest req=new OrderReturnRequest();
		req.setOrderId(35913355l);
		req.setProdDetalId(297);
		req.setTenantId("SLP");
		req.setOperId("");
		orderAfterSaleSV.back(req);
	}

}
