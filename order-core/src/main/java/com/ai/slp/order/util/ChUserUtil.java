package com.ai.slp.order.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.opt.sdk.dubbo.util.HttpClientUtil;
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
			try {
				result = HttpClientUtil.sendPost(OrdersConstants.USER_URL, param,mapHeader);
			} catch (Exception e) {
				e.printStackTrace();
			}
	     //将返回结果，转换为JSON对象 
		 JSONObject dataJson=null;
		 JSONObject data=null;
	     JSONObject json=JSON.parseObject(result);
	     String reqResultCode=json.getString("resultCode");
	     if("000000".equals(reqResultCode)){
	         String dataStr=(String)json.get("data");
	         data=JSON.parseObject(dataStr);
	         dataJson = (JSONObject) data.get("data");
	         if(dataJson==null) {
	        	 return dataJson; 
	         }
	     }else{
	     	//请求过程失败
	    	 LOG.info("请求失败,请求错误码:"+reqResultCode);
	     }
	     return dataJson;
	}
}
