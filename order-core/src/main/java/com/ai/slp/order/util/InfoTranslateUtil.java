package com.ai.slp.order.util;

import com.ai.platform.common.api.cache.interfaces.ICacheSV;
import com.ai.platform.common.api.cache.param.SysParam;
import com.ai.platform.common.api.cache.param.SysParamSingleCond;

public class InfoTranslateUtil {
	
	/**
     * 信息翻译
     */
    public static SysParam translateInfo(String tenantId, String typeCode, 
    		String paramCode, String columnValue,ICacheSV iCacheSV) {
    	SysParamSingleCond sysParamSingleCond = new SysParamSingleCond(
    			tenantId, typeCode,paramCode, columnValue);
    	SysParam sysParamInfo = iCacheSV.getSysParamSingle(sysParamSingleCond);
    	return sysParamInfo;
    }

}
