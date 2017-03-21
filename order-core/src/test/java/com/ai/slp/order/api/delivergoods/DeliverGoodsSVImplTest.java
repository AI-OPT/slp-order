package com.ai.slp.order.api.delivergoods;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ai.slp.order.api.delivergoods.param.DeliverGoodsRequest;
import com.ai.slp.order.service.business.interfaces.IDeliverGoodsBusiSV;
import com.alibaba.fastjson.JSON;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/context/core-context.xml" })
public class DeliverGoodsSVImplTest {

	@Autowired
	private IDeliverGoodsBusiSV deliverGoodsBusiSV;
	@Test
	public void testDeliverGoods() {
		DeliverGoodsRequest request=new DeliverGoodsRequest();
		request.setExpressId("1100011");
		request.setExpressOddNumber("3333");
		request.setOrderId(2000001226131125l);
		request.setTenantId("changhong");
		request.setOperId("11111");
		System.out.println(JSON.toJSONString(request));
	//	deliverGoodsBusiSV.deliverGoods(request);
	}

}
