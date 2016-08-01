package com.ai.slp.order.api.o2pcallback;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ai.slp.order.api.o2pcallback.param.O2pCallBackRequest;
import com.ai.slp.order.service.business.interfaces.IO2pCallBackBusiSV;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/context/core-context.xml" })
public class O2pCallBackSVImplTest {

	@Autowired
	IO2pCallBackBusiSV o2pCallBackBusiSV;
	
	@Test
	public void callback() {
		O2pCallBackRequest o2pCallBackRequest=new O2pCallBackRequest();
		o2pCallBackRequest.setTenantId("SLP");
		//{"tenantId":"SLP","externalOrderId":"201607211843396172","state":"122"}
		o2pCallBackRequest.setExternalOrderId("201607221107021212");
		o2pCallBackRequest.setState("122");
		o2pCallBackBusiSV.callBack(o2pCallBackRequest);
	}
}
