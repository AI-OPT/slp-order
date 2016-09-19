package com.ai.slp.order.monitor.service.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ai.slp.order.monitor.MonitorService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/context/core-context.xml" })
public class MonitorServiceTest {
	private static final Logger log = LogManager.getLogger(MonitorServiceTest.class);
	@Autowired
	private MonitorService orderMonitorService;
	@Test
	public void beforSubmitOrder(){
		String ipAddress = "10.1.147.148";
		String userId = "zhangzd";
		
		this.orderMonitorService.beforSubmitOrder(ipAddress, userId);
	}
	@Test
	public void afterSubmitOrder(){
		String ipAddress = "10.1.147.148";
		String userId = "zhangzd";
		
		this.orderMonitorService.afterSubmitOrder(ipAddress, userId);
	}

}
