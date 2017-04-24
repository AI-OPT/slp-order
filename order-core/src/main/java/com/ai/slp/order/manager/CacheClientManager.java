package com.ai.slp.order.manager;

import java.util.HashMap;
import java.util.Map;

import com.ai.opt.sdk.components.mcs.MCSClientFactory;
import com.ai.paas.ipaas.mcs.interfaces.ICacheClient;
import com.ai.paas.ipaas.util.StringUtil;

public class CacheClientManager {
	private static Map<String, ICacheClient> map = new HashMap<String, ICacheClient>();
	
	/**
	 * 获取缓存client
	 * @param cachens
	 * @return
	 * @author caofz
	 * @ApiDocMethod
	 * @ApiCode 
	 * @RestRelativeURL
	 */
	public static ICacheClient getCacheClient(String cachens){
		if(StringUtil.isBlank(cachens)){
			return null;
		}
		if(map.containsKey(cachens)){
			return map.get(cachens);
		}
		ICacheClient cacheClient = MCSClientFactory.getCacheClient(cachens);
		map.put(cachens, cacheClient);
		return cacheClient;
	}
	
}
