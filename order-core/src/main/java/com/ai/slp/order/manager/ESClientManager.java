package com.ai.slp.order.manager;

import java.util.HashMap;
import java.util.Map;

import com.ai.opt.sdk.components.ses.SESClientFactory;
import com.ai.paas.ipaas.search.ISearchClient;
import com.ai.paas.ipaas.util.StringUtil;

public class ESClientManager {
	
private static Map<String, ISearchClient> map = new HashMap<String, ISearchClient>();
	
	/**
	 * 获取ESclient
	 * @param sesns
	 * @return
	 * @author caofz
	 * @ApiDocMethod
	 * @ApiCode 
	 * @RestRelativeURL
	 */
	public static ISearchClient getSesClient(String sesns){
		if(StringUtil.isBlank(sesns)){
			return null;
		}
		if(map.containsKey(sesns)){
			return map.get(sesns);
		}
		ISearchClient sesClient = SESClientFactory.getSearchClient(sesns);
		map.put(sesns, sesClient);
		return sesClient;
	}

}
