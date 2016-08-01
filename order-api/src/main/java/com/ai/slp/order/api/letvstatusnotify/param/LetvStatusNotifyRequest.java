package com.ai.slp.order.api.letvstatusnotify.param;

import java.sql.Timestamp;

import com.ai.opt.base.vo.BaseInfo;

public class LetvStatusNotifyRequest extends BaseInfo {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 外部订单ID(下游)
	 */
	private String downstreamOrderId;
	
	/**
	 * 订单ID
	 */
	private String orderId;
	
	/**
	 * 用户ID
	 */
	private String userId;
	
	/**
	 * 手机号
	 */
	private String infoJson;
	
	/**
	 * 订单状态
	 */
	private String state;
	
	/**
	 * 时间戳
	 */
	private Timestamp finishTime;
	
	/**
	 * 订单类型
	 */
	private String orderType;

	public String getDownstreamOrderId() {
		return downstreamOrderId;
	}

	public void setDownstreamOrderId(String downstreamOrderId) {
		this.downstreamOrderId = downstreamOrderId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getInfoJson() {
		return infoJson;
	}

	public void setInfoJson(String infoJson) {
		this.infoJson = infoJson;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Timestamp getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Timestamp finishTime) {
		this.finishTime = finishTime;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
}
