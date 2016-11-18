package com.ai.slp.order.api.aftersaleorder.impl;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ai.slp.order.api.aftersaleorder.interfaces.IOrderAfterSaleJudgeSV;
import com.ai.slp.order.api.aftersaleorder.param.OrderJuageRequest;
import com.ai.slp.order.api.aftersaleorder.param.OrderJuageResponse;
import com.alibaba.fastjson.JSON;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/context/core-context.xml" })
public class OrderAfterSaleJudgeSVImplTest {
	
	@Autowired
	private IOrderAfterSaleJudgeSV orderAfterSaleJudgeSV;
	@Test
	public void testJudge() {
		OrderJuageRequest request=new OrderJuageRequest();
		request.setOrderId(2000001158766307l);
		request.setSkuId("0000000000000287");
		request.setTenantId("changhong");
		OrderJuageResponse response = orderAfterSaleJudgeSV.judge(request);
		System.out.println(JSON.toJSON(response));
	}
	

}
