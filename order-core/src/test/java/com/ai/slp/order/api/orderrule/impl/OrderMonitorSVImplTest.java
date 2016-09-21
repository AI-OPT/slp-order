package com.ai.slp.order.api.orderrule.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ai.slp.order.api.orderrule.interfaces.IOrderMonitorSV;
import com.ai.slp.order.api.orderrule.param.OrderMonitorBeforResponse;
import com.ai.slp.order.api.orderrule.param.OrderMonitorRequest;
import com.ai.slp.order.api.orderrule.param.OrderMonitorResponse;
import com.alibaba.fastjson.JSON;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/context/core-context.xml" })
public class OrderMonitorSVImplTest {
	private static final Logger log = LogManager.getLogger(OrderMonitorSVImplTest.class);
	@Autowired
	private IOrderMonitorSV orderMonitorSV;
	@Test
	public void beforSubmitOrder(){
		OrderMonitorRequest request = new OrderMonitorRequest();
		//
		String ipAddress = "10.1.147.148";
		String userId = "zhangzd";
		request.setIpAddress(ipAddress);
		request.setUserId(userId);
		//
		OrderMonitorBeforResponse response = this.orderMonitorSV.beforSubmitOrder(request);
		log.info("response:"+JSON.toJSONString(response));
	}
	@Test
	public void afterSubmitOrder(){
		OrderMonitorRequest request = new OrderMonitorRequest();
		//
		String ipAddress = "10.1.147.148";
		String userId = "zhangzd";
		request.setIpAddress(ipAddress);
		request.setUserId(userId);
		//
		OrderMonitorResponse response = this.orderMonitorSV.afterSubmitOrder(request);
		log.info("response:"+JSON.toJSONString(response));
	}
}
