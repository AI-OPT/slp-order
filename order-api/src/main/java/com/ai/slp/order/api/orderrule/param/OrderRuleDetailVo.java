package com.ai.slp.order.api.orderrule.param;

import java.io.Serializable;

public class OrderRuleDetailVo implements Serializable {

	/**
	 * 时间监控编号
	 */
	private String timeMonitorId;
	private Integer timeMonitorTime;
	private String timeMonitorTimeType;
	private Integer timeMonitorOrderSum;
	/**
	 * 购买人员监控编号
	 */
	private String buyEmployeeMonitorId;
	private Integer buyEmployeeMonitorTime;
	private String buyEmployeeMonitorTimeType;
	private Integer buyEmployeeMonitorOrderSum;
	/**
	 * 购买ip监控
	 */
	private String buyIpMonitorId;
	private Integer buyIpMonitorTime;
	private String buyIpMonitorTimeType;
	private Integer buyIpMonitorOrderSum;
	/**
	 * 合并订单设置编号
	 */
	private String mergeOrderSettingId;
	private Integer mergeOrderSettingTime;
	private String mergeOrderSettingTimeType;
	private Integer mergeOrderSettingOrderSum;

	public String getTimeMonitorId() {
		return timeMonitorId;
	}

	public void setTimeMonitorId(String timeMonitorId) {
		this.timeMonitorId = timeMonitorId;
	}

	public Integer getTimeMonitorTime() {
		return timeMonitorTime;
	}

	public void setTimeMonitorTime(Integer timeMonitorTime) {
		this.timeMonitorTime = timeMonitorTime;
	}

	public String getTimeMonitorTimeType() {
		return timeMonitorTimeType;
	}

	public void setTimeMonitorTimeType(String timeMonitorTimeType) {
		this.timeMonitorTimeType = timeMonitorTimeType;
	}

	public Integer getTimeMonitorOrderSum() {
		return timeMonitorOrderSum;
	}

	public void setTimeMonitorOrderSum(Integer timeMonitorOrderSum) {
		this.timeMonitorOrderSum = timeMonitorOrderSum;
	}

	public String getBuyEmployeeMonitorId() {
		return buyEmployeeMonitorId;
	}

	public void setBuyEmployeeMonitorId(String buyEmployeeMonitorId) {
		this.buyEmployeeMonitorId = buyEmployeeMonitorId;
	}

	public Integer getBuyEmployeeMonitorTime() {
		return buyEmployeeMonitorTime;
	}

	public void setBuyEmployeeMonitorTime(Integer buyEmployeeMonitorTime) {
		this.buyEmployeeMonitorTime = buyEmployeeMonitorTime;
	}

	public String getBuyEmployeeMonitorTimeType() {
		return buyEmployeeMonitorTimeType;
	}

	public void setBuyEmployeeMonitorTimeType(String buyEmployeeMonitorTimeType) {
		this.buyEmployeeMonitorTimeType = buyEmployeeMonitorTimeType;
	}

	public Integer getBuyEmployeeMonitorOrderSum() {
		return buyEmployeeMonitorOrderSum;
	}

	public void setBuyEmployeeMonitorOrderSum(Integer buyEmployeeMonitorOrderSum) {
		this.buyEmployeeMonitorOrderSum = buyEmployeeMonitorOrderSum;
	}

	public String getBuyIpMonitorId() {
		return buyIpMonitorId;
	}

	public void setBuyIpMonitorId(String buyIpMonitorId) {
		this.buyIpMonitorId = buyIpMonitorId;
	}

	public Integer getBuyIpMonitorTime() {
		return buyIpMonitorTime;
	}

	public void setBuyIpMonitorTime(Integer buyIpMonitorTime) {
		this.buyIpMonitorTime = buyIpMonitorTime;
	}

	public String getBuyIpMonitorTimeType() {
		return buyIpMonitorTimeType;
	}

	public void setBuyIpMonitorTimeType(String buyIpMonitorTimeType) {
		this.buyIpMonitorTimeType = buyIpMonitorTimeType;
	}

	public Integer getBuyIpMonitorOrderSum() {
		return buyIpMonitorOrderSum;
	}

	public void setBuyIpMonitorOrderSum(Integer buyIpMonitorOrderSum) {
		this.buyIpMonitorOrderSum = buyIpMonitorOrderSum;
	}

	public String getMergeOrderSettingId() {
		return mergeOrderSettingId;
	}

	public void setMergeOrderSettingId(String mergeOrderSettingId) {
		this.mergeOrderSettingId = mergeOrderSettingId;
	}

	public Integer getMergeOrderSettingTime() {
		return mergeOrderSettingTime;
	}

	public void setMergeOrderSettingTime(Integer mergeOrderSettingTime) {
		this.mergeOrderSettingTime = mergeOrderSettingTime;
	}

	public String getMergeOrderSettingTimeType() {
		return mergeOrderSettingTimeType;
	}

	public void setMergeOrderSettingTimeType(String mergeOrderSettingTimeType) {
		this.mergeOrderSettingTimeType = mergeOrderSettingTimeType;
	}

	public Integer getMergeOrderSettingOrderSum() {
		return mergeOrderSettingOrderSum;
	}

	public void setMergeOrderSettingOrderSum(Integer mergeOrderSettingOrderSum) {
		this.mergeOrderSettingOrderSum = mergeOrderSettingOrderSum;
	}
}
