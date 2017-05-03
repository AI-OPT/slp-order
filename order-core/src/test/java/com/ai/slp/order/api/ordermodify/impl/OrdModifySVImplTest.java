package com.ai.slp.order.api.ordermodify.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ai.opt.base.vo.BaseResponse;
import com.ai.slp.order.api.ordermodify.interfaces.IOrderModifySV;
import com.ai.slp.order.api.ordermodify.param.OrdRequest;
import com.alibaba.fastjson.JSON;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/context/core-context.xml" })
public class OrdModifySVImplTest {
	@Autowired
	IOrderModifySV iOrderModifySV;

	@Test
	public void test() {
		OrdRequest request=new OrdRequest();
        request.setOrderId(2000034411596752l);
		request.setTenantId("changhong");
		request.setState("94");
		System.out.println(JSON.toJSONString(request));
		BaseResponse response = iOrderModifySV.modify(request);
		System.out.println(response);
	}
	
}
