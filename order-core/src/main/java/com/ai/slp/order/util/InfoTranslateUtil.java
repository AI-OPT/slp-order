package com.ai.slp.order.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.platform.common.api.cache.interfaces.ICacheSV;
import com.ai.platform.common.api.cache.param.SysParam;
import com.ai.platform.common.api.cache.param.SysParamSingleCond;

public class InfoTranslateUtil {
	
	 private static final Logger logger=LoggerFactory.getLogger(InfoTranslateUtil.class);
	
	/**
     * 信息翻译
     */
    public static SysParam translateInfo(String tenantId, String typeCode, 
    		String paramCode, String columnValue,ICacheSV iCacheSV) {
    	long caechStart=System.currentTimeMillis();
     	logger.info("开始执行dubbo订单列表查询behindQueryOrderList，操作公共中心缓存,当前时间戳："+caechStart);
    	SysParamSingleCond sysParamSingleCond = new SysParamSingleCond(
    			tenantId, typeCode,paramCode, columnValue);
    	SysParam sysParamInfo = iCacheSV.getSysParamSingle(sysParamSingleCond);
    	long caechEnd=System.currentTimeMillis();
     	logger.info("开始执行dubbo订单列表查询behindQueryOrderList，操作公共中心缓存,当前时间戳："+caechEnd+",用时:"+(caechEnd-caechStart)+"毫秒");
    	return sysParamInfo;
    }

}
