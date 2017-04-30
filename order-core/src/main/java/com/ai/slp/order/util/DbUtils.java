package com.ai.slp.order.util;

import org.apache.commons.lang.StringUtils;

public class DbUtils {

	/**
	 * 空处理,返回处理后的text,避免数据库做无效字段插入
	 * @param text
	 * @return
	 * @author caofz
	 * @ApiDocMethod
	 * @ApiCode 
	 * @RestRelativeURL
	 */
	public static String getField(String text){
		if(StringUtils.isNotBlank(text)){
			return text;
		}
		return null;
	}
	

}
