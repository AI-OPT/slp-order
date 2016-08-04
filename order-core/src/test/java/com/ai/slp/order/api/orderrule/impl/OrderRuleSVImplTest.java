package com.ai.slp.order.api.orderrule.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ai.slp.order.api.orderrule.interfaces.IOrderRuleSV;
import com.ai.slp.order.api.orderrule.param.OrderRuleDetailResponse;
import com.ai.slp.order.api.orderrule.param.OrderRuleRequest;
import com.ai.slp.order.api.orderrule.param.OrderRuleResponse;
import com.ai.slp.order.constants.OrdRuleConstants;
import com.alibaba.fastjson.JSON;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/context/core-context.xml" })
public class OrderRuleSVImplTest {
	private static final Logger log = LogManager.getLogger(OrderRuleSVImplTest.class);
	@Autowired
	private IOrderRuleSV orderRuleSV;
	/**
	 * 规则设置
	 * 
	 * @author zhangzd
	 * @ApiDocMethod
	 * @ApiCode
	 */
	@Test
	public void orderRuleSetting(){
		OrderRuleRequest request = new OrderRuleRequest();
//		110000010000100010：合并
//		110000010000100011：时间
//		110000010000100012：数量
//		110000010000100013：IP监控'
		//
		request.setTimeMonitorId(OrdRuleConstants.TIME_MONITOR_ID);
		request.setTimeMonitorTime(4);
		request.setTimeMonitorTimeType("D");
		request.setTimeMonitorOrderSum(8888877);
		//
		request.setBuyEmployeeMonitorId(OrdRuleConstants.BUY_EMPLOYEE_MONITOR_ID);
		request.setBuyEmployeeMonitorTime(3);
		request.setBuyEmployeeMonitorTimeType("H");
		request.setBuyEmployeeMonitorOrderSum(1022028989);
		//
		request.setBuyIpMonitorId(OrdRuleConstants.BUY_IP_MONITOR_ID);
		request.setBuyIpMonitorTime(3);
		request.setBuyIpMonitorTimeType("H");
		request.setBuyIpMonitorOrderSum(1002230);
		//
		request.setMergeOrderSettingId(OrdRuleConstants.MERGE_ORDER_SETTING_ID);
		request.setMergeOrderSettingTime(3);
		request.setMergeOrderSettingTimeType("D");
		request.setMergeOrderSettingOrderSum(1022400);
		//
		log.info("request:"+JSON.toJSONString(request));
		//
		OrderRuleResponse response = this.orderRuleSV.orderRuleSetting(request);
		//
		log.info("response:"+JSON.toJSONString(response));
	}
	/**
	 * 订单规则详情
	 * 
	 * @author zhangzd
	 * @ApiDocMethod
	 * @ApiCode
	 */
	@Test
	public void findOrderRuleDetail(){
		OrderRuleDetailResponse response = this.orderRuleSV.findOrderRuleDetail();
		//
		log.info("response:"+JSON.toJSONString(response));
		
	}

}
