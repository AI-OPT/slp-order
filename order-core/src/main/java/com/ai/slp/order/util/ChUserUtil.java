package com.ai.slp.order.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.opt.sdk.dubbo.util.HttpClientUtil;
import com.ai.opt.sdk.util.ParseO2pDataUtil;
import com.ai.slp.order.constants.OrdersConstants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class ChUserUtil {
	private static final Logger LOG = LoggerFactory.getLogger(ChUserUtil.class);
	//获取用户信息
	public static JSONObject getUserInfo(String id){
	   Map<String,String> params=new HashMap<String,String>();
	   params.put("openId", id);
       String param=JSON.toJSONString(params);
       Map<String,String> mapHeader = new HashMap<String,String>();
       mapHeader.put("appkey", OrdersConstants.OFC_APPKEY);
       String result ="";
       JSONObject object =null;
		try {
			result = HttpClientUtil.sendPost(OrdersConstants.USER_URL, param,mapHeader);
			object = ParseO2pDataUtil.getData(result);
			Object obj = object.get("resultCode");
			if(obj!=null) {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("请求出现错误!");
		}
		return object;
	}
	
	public static void main(String[] args) {
		JSONObject object = getUserInfo("3da3109cdb3f4d9e");
		System.out.println(object);
	}
}
