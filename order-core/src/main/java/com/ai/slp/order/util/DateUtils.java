package com.ai.slp.order.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间格式化工具
 */
public class DateUtils {
	private  static final Logger logger = LoggerFactory.getLogger(DateUtils.class);
	private DateUtils(){}

	/**
	 * 返回时间的Timestamp实例
	 * @param date
	 * @return
     */
	public static Timestamp toTimeStamp(Date date){
		return date==null?null:new Timestamp(date.getTime());
	}

	public static Timestamp getTimestamp(String str,String pattern) {
		Timestamp result = null;
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		try {
			return new Timestamp(formatter.parse(str).getTime());
		} catch (ParseException e) {
			logger.error("" ,e);
		}
		return result;
	}

	/**
	 * 对时间进行格式化
	 * @param date
	 * @param pattern
     * @return
     */
	public static String format(Date date, String pattern){
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		return formatter.format(date);
	}
	
	public static String getCurrMonth(){
		return format(new Date(),"yyyyMM");
	}
	
	public static Timestamp currTimeStamp(){
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * 获取每月的开始,第一天零点零分零秒
	 *
	 * @param month yyyyMM
	 * @return
     */
	public static Timestamp getSartOfMonth(String month){
		String startStr = month+"01000000";
		Timestamp result = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			result = new Timestamp(formatter.parse(startStr).getTime());
		} catch (ParseException e) {
			logger.error("" ,e);
		}
		return result;
	}

	/**
	 * 
	* @Title: monthsAdd 
	* @Description:
	* @param @param montstr
	* @param @param interval
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String monthsAdd(String montstr,int interval){
		String yyyy = montstr.substring(0,4);
		String mm = montstr.substring(4,6);
		int diffyear ;
		int desMonth ;
		int norimonth = Integer.parseInt(mm);

		if ((norimonth + interval) > 0 && (norimonth + interval)%12 != 0){
			diffyear = (norimonth + interval)/12;
			desMonth = (norimonth + interval)%12;
		}else{
			diffyear = (norimonth + interval)/12 - 1;
			desMonth = (norimonth + interval)%12 + 12;
		}

		int ndesYear = Integer.parseInt(yyyy) + diffyear;
		String sdesmonth = Integer.toString(desMonth);
        if (desMonth<10){
        	sdesmonth = "0" + sdesmonth;
        }
        return Integer.toString(ndesYear) + sdesmonth;
	}
	
	/**
	 * 计算连个时间之间月份差值
	* @Title: monthDiffs
	* @param @param fisMonth
	* @param @param secMonth
	* @param @return    设定文件 
	* @return Integer    返回类型 
	* @throws
	 */

	public static Integer monthDiffs(String fisMonth,String secMonth){
		int nfstyyyy = Integer.parseInt(fisMonth.substring(0,4));
		int nfstMonth =  Integer.parseInt(fisMonth.substring(4,6));
		
		int nsecyyyy = Integer.parseInt(secMonth.substring(0,4));
		int nsecMonth = Integer.parseInt(secMonth.substring(4,6));
	
		int nMonth = nsecMonth -  nfstMonth;
		
		return (nsecyyyy - nfstyyyy)*12 + nMonth;
	}

	public static java.sql.Date getNowDate() {
		return new java.sql.Date(new Date().getTime());
	}
	
	
}
