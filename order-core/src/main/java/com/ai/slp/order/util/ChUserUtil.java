package com.ai.slp.order.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.opt.sdk.dubbo.util.HttpClientUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public  class ChUserUtil {
	private static final Logger LOG = LoggerFactory.getLogger(ChUserUtil.class);
	//获取绑定手机号
	public static String getPhone(String id){
		 Map<String,String> params=new HashMap<String,String>();
		   params.put("uid", id);
	       String url="http://10.19.13.16:28151/opaas/http/srv_up_user_getuserdetialbyuid_reg";
	       String param=JSON.toJSONString(params);
	       Map<String,String> mapHeader = new HashMap<String,String>();
	       mapHeader.put("appkey", "3a83ed361ebce978731b736328a97ea8");
	       String result ="";
			try {
				result = HttpClientUtil.sendPost(url, param, mapHeader);
			} catch (Exception e) {
				e.printStackTrace();
			}
	     //将返回结果，转换为JSON对象 
	     JSONObject json=JSON.parseObject(result);
	     String reqResultCode=json.getString("resultCode");
	     if("000000".equals(reqResultCode)){
	         String dataStr=(String)json.get("data");
	         JSONObject dataJson=JSON.parseObject(dataStr);
	         Object phone =dataJson.get("phone");
	         if(phone!=null){
	        	 return phone.toString();
	         }
	     }else{
	     	//请求过程失败
	    	 LOG.info("请求失败,请求错误码:"+reqResultCode);
	     }
	     return null;
	}
}
