package com.ai.slp.order.manager;

import java.util.HashMap;
import java.util.Map;

import com.ai.opt.sdk.components.mds.MDSClientFactory;
import com.ai.paas.ipaas.mds.IMessageSender;
import com.ai.paas.ipaas.util.StringUtil;

public class MDSClientManager {
	
private static Map<String, IMessageSender> map = new HashMap<String, IMessageSender>();
	
	/**
	 * 获取消息发送client
	 * @param sesns
	 * @return
	 * @author caofz
	 * @ApiDocMethod
	 * @ApiCode 
	 * @RestRelativeURL
	 */
	public static IMessageSender getSenderClient(String mdsns){
		if(StringUtil.isBlank(mdsns)){
			return null;
		}
		if(map.containsKey(mdsns)){
			return map.get(mdsns);
		}
		IMessageSender mesClient = MDSClientFactory.getSenderClient(mdsns);
		map.put(mdsns, mesClient);
		return mesClient;
	}

}
