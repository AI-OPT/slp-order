package com.ai.slp.order.api.aftersaleorder.param;

import java.io.Serializable;

public class OrderOFCBackRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 订单id
	 */
	private String orderId;
	
	/**
	 * 外部申请单号
	 */
	private String externalOrderId;
	
	/**
	 * 审核状态
	 */
	private String state;
	
	/**
	 * 原因描述
	 */
	private String reasonDesc;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getExternalOrderId() {
		return externalOrderId;
	}

	public void setExternalOrderId(String externalOrderId) {
		this.externalOrderId = externalOrderId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getReasonDesc() {
		return reasonDesc;
	}

	public void setReasonDesc(String reasonDesc) {
		this.reasonDesc = reasonDesc;
	}
}
