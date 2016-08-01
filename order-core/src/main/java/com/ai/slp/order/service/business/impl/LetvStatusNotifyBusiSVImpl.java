package com.ai.slp.order.service.business.impl;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.slp.order.api.letvstatusnotify.param.LetvStatusNotifyRequest;
import com.ai.slp.order.service.business.interfaces.ILetvStatusNotifyBusiSV;
import com.alibaba.fastjson.JSON;

@Service
@Transactional
public class LetvStatusNotifyBusiSVImpl implements ILetvStatusNotifyBusiSV{
	
	private static final Logger logger=LoggerFactory.getLogger(LetvStatusNotifyBusiSVImpl.class);
	
	@Override
	public BaseResponse statusnotify(LetvStatusNotifyRequest request) throws BusinessException, SystemException {
		logger.info("调用服务,查看订单状态通知...");
		BaseResponse baseResponse = null;
		try {
			HttpClient client=HttpClients.createDefault();
			String url="http://10.1.235.246:8081/serviceAgent/http/srv_slpo_charge_order_callback";
			HttpPost post=new HttpPost(url);
			//设置头部分
			post.setHeader("Content-Type", "application/json");	
			String str = JSON.toJSONString(request);
			StringEntity entity=new StringEntity(str, "utf-8");
			post.setEntity(entity);
			//执行post请求
			HttpResponse response = client.execute(post);
			HttpEntity httpEntity = response.getEntity();
			//得到内容
			String content = EntityUtils.toString(httpEntity, "utf-8");
		//	baseResponse = JSON.parseObject(content,BaseResponse.class);
		} catch (IOException e) {
			logger.error("调用服务失败...");
			e.printStackTrace();
			throw new SystemException("", "调用服务失败,系统出现异常.");
		}
		return baseResponse;
	}
}
