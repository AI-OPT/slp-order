package com.ai.slp.order.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.opt.sdk.util.DateUtil;
import com.alibaba.fastjson.JSON;

public final class DateCycleUtil {

	private static final Logger LOG = LoggerFactory.getLogger(DateCycleUtil.class);

	public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static final String DATETIME_FORMAT2 = "yyyyMMdd HH:mm:ss";

	public static final String DATETIME_FORMAT3 = "yyyy-MM-dd HH:mm:ss.SS";

	public static final String DATE_FORMAT = "yyyy-MM-dd";

	public static final String YYYYMMDD = "yyyyMMdd";

	public static final String YYYYMM = "yyyyMM";
	public static final String YYYY_MM = "yyyy-MM";

	public static final String YYYY = "yyyy";

	public static final String YYYYMMDDHHMM = "yyyyMMddHHmm";

	public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

	public static final String yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";

	public static final String BILL_CYCLE_ID = "BILL_CYCLE_ID";

	public static final String PAY_DATE = "PAY_DATE";

	public static final String PAY_DATE_NEW = "PAY_DATE_NEW";

	public static final String DATE_TYPE_MINUTE = "MIN";// 分钟

	public static final String DATE_TYPE_HOURS = "H";// 小时

	public static final String DATE_TYPE_DAY = "D";// 天

	public static final String DATE_TYPE_WEEK = "W";

	public static final String DATE_TYPE_MONTH = "M";
	
	public static final Map<String,String> dateTypeMap = new HashMap<String,String>();
	static{
		dateTypeMap.put(DATE_TYPE_DAY, "天");
		dateTypeMap.put(DATE_TYPE_HOURS, "小时");
		dateTypeMap.put(DATE_TYPE_MINUTE, "分钟");
	}
	private DateCycleUtil() {
		// 禁止实例化
	}

	/**
	 * 获取时间周期
	 * 
	 * @param dateType
	 * @param amount
	 * @return
	 * @author zhangzd
	 * @ApiDocMethod
	 * @ApiCode
	 */
	public static Map<String, Object> getCycleDate(String dateType, int amount) {
		LOG.info("当前周期类型：" + dateType);
		LOG.info("当前周期数：" + amount);
		Map<String, Object> timeMap = new HashMap<String, Object>();
		String dateFormatStr = "";
		int DateType = Calendar.DAY_OF_MONTH;
		//
		if (dateType.equals(DATE_TYPE_DAY)) {
			// 天
			dateFormatStr = YYYYMMDDHHMMSS;
			DateType = Calendar.DAY_OF_MONTH;
		} else if (dateType.equals(DATE_TYPE_HOURS)) {
			// 小时
			dateFormatStr = YYYYMMDDHHMMSS;
			DateType = Calendar.HOUR;
		} else if (dateType.equals(DATE_TYPE_MINUTE)) {
			// 分钟
			dateFormatStr = YYYYMMDDHHMMSS;
			DateType = Calendar.MINUTE;
		}

		//
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormatStr);

		Calendar cal = Calendar.getInstance();

		//
		String startTime = sdf.format(cal.getTime());
		//
		LOG.info("*****StartTime*****：" + startTime);
		long startTimeLong = cal.getTime().getTime() / 1000;
		LOG.info("*****StartTime long*****：" + startTimeLong);
		//
		cal.add(DateType, amount);

		String endTime = sdf.format(cal.getTime());

		LOG.info("*****EndTime*****：" + endTime);
		long endTimeLong = cal.getTime().getTime() / 1000;
		LOG.info("*****EndTime long*****：" + endTimeLong);

		timeMap.put("startTime", startTimeLong);
		timeMap.put("endTime", endTimeLong);
		LOG.info("json:" + JSON.toJSONString(timeMap));
		//
		return timeMap;

	}
	
	public static Date strToDate(String timeStr) {
		Date date = new Date();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDDHHMMSS);
			date = sdf.parse(timeStr);
		} catch (Exception e) {
			
		}
		LOG.info("SECOUND:"+date.getTime()/1000);
		return date;

	}

	public static void main(String[] args) {

		LOG.info("================五分钟前===================");

		DateCycleUtil.getCycleDate("MIN", -5);

		LOG.info("================五小时前===================");

		DateCycleUtil.getCycleDate("H", -5);

		LOG.info("================五天前===================");

		DateCycleUtil.getCycleDate("D", -5);
		
		DateCycleUtil.strToDate("20000101000000");
		
		LOG.info("秒："+DateUtil.getSysDate().getTime()/1000);
		LOG.info("毫秒："+DateUtil.getSysDate().getTime());
		
		String dateTypeValue = DateCycleUtil.dateTypeMap.get(DATE_TYPE_DAY);
		LOG.info("日期类型："+dateTypeValue);
	}
}
