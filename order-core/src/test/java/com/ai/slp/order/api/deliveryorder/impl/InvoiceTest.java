package com.ai.slp.order.api.deliveryorder.impl;


import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ai.slp.order.api.delivergoods.interfaces.IDeliverGoodsPrintSV;
import com.ai.slp.order.api.delivergoods.param.DeliverGoodsPrintInfosRequest;
import com.ai.slp.order.api.delivergoods.param.DeliverGoodsPrintRequest;
import com.ai.slp.order.api.delivergoods.param.DeliverGoodsPrintResponse;
import com.ai.slp.order.api.delivergoods.param.DeliverGoodsPrintVo;
import com.alibaba.fastjson.JSON;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/context/core-context.xml" })
public class InvoiceTest {
	
	@Autowired
	private IDeliverGoodsPrintSV deliverGoodsPrintSV;

	@Test
	public void testQuery() {
		DeliverGoodsPrintRequest request=new DeliverGoodsPrintRequest();
		request.setOrderId(0l);
		request.setTenantId("changhong");
		System.out.println(JSON.toJSONString(request));
		DeliverGoodsPrintResponse response = deliverGoodsPrintSV.query(request);
		System.out.println(JSON.toJSONString(response));
	}
	
	
	@Test
	public void testPrint() {
		DeliverGoodsPrintInfosRequest request=new DeliverGoodsPrintInfosRequest();
		List<DeliverGoodsPrintVo> invoicePrintVos=new ArrayList<DeliverGoodsPrintVo>();
		DeliverGoodsPrintVo invoicePrintVo=new DeliverGoodsPrintVo();
		request.setOrderId(59612493l);
		request.setTenantId("changhong");
		System.out.println(JSON.toJSONString(request));
		deliverGoodsPrintSV.print(request);
	}

}
