package com.ai.slp.order.api.letvstatusnotify;

import java.io.IOException;
import java.sql.Timestamp;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.sdk.dubbo.util.HttpClientUtil;
import com.ai.opt.sdk.util.DateUtil;
import com.ai.slp.order.api.letvstatusnotify.interfaces.ILetvStatusNotifySV;
import com.ai.slp.order.api.letvstatusnotify.param.LetvStatusNotifyRequest;
import com.ai.slp.order.api.o2pcallback.param.O2pCallBackRequest;
import com.ai.slp.order.service.business.interfaces.IO2pCallBackBusiSV;
import com.alibaba.fastjson.JSON;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/context/core-context.xml" })
public class LetvCallBackImplTest {
	
	@Autowired
	ILetvStatusNotifySV letvStatusNotifySV;
	
	@Autowired
	IO2pCallBackBusiSV 	o2pCallBackBusiSV;
	
	
	@Test
	public void callBackTest() throws ClientProtocolException, IOException {
		LetvStatusNotifyRequest request=new LetvStatusNotifyRequest();
		request.setDownstreamOrderId("7211803");
		request.setOrderId("2000000348887209");
		request.setTenantId("SLP");
		request.setUserId("000000000000001203");
		request.setState("121");
		String tsStr = "20160721191916";
		Timestamp timestamp = DateUtil.getTimestamp(tsStr, "yyyyMMddHHmmss");
		
		//Timestamp ts = new Timestamp(System.currentTimeMillis());
		// ts = Timestamp.valueOf(tsStr);
		request.setFinishTime(timestamp);
		request.setOrderType("100010");
		
		BaseResponse baseResponse = letvStatusNotifySV.statusnotify(request);
	}
	
}
