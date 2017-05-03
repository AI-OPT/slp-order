package com.ai.slp.order.api.orderstate.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ai.slp.order.api.orderstate.interfaces.IOrderStateServiceSV;
import com.ai.slp.order.api.orderstate.param.WaitRebateRequest;
import com.ai.slp.order.api.orderstate.param.WaitRebateResponse;
import com.ai.slp.order.api.orderstate.param.WaitSellReceiveSureRequest;
import com.ai.slp.order.api.orderstate.param.WaitSellReceiveSureResponse;
import com.alibaba.fastjson.JSON;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/context/core-context.xml" })
public class OrderStateServiceSVImplTest {
	private static final Logger log = LogManager.getLogger(OrderStateServiceSVImplTest.class);
	@Autowired
	private IOrderStateServiceSV orderStateServiceSV;
	@Test
	public void updateWaitSellRecieveSureState(){
		WaitSellReceiveSureRequest request = new WaitSellReceiveSureRequest();
		//
		request.setExpressId("yunda");
		request.setExpressOddNumber("1111");
		request.setOrderId(2000034414396270l);
		request.setTenantId("changhong");
		log.info("request:"+JSON.toJSONString(request));
		WaitSellReceiveSureResponse response = this.orderStateServiceSV.updateWaitSellRecieveSureState(request);
		log.info("response:"+JSON.toJSONString(response));
	}
	@Test
	public void updateWaitRebateState(){
		WaitRebateRequest request = new WaitRebateRequest();
		//
		request.setOrderId(2000034390021452l);
		request.setTenantId("changhong");
		log.info("request:"+JSON.toJSONString(request));
		WaitRebateResponse response = this.orderStateServiceSV.updateWaitRebateState(request);
		log.info("response:"+JSON.toJSONString(response));
		
	}
}
