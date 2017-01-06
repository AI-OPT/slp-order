package com.ai.slp.order.api.synchronize.params;

import java.io.Serializable;

public class OrdBalanceIfVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 支付流水
	 */
	private long balacneIfId;

	/**
	 * 订单id
	 */
	private long orderId;
	/**
	 * 支付类型
	 */
	private String payStyle;

	/**
	 * 已支付金额
	 */
	private long payFee;
	/**
	 * 外部流水号
	 */
	private String externalId;

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getPayStyle() {
		return payStyle;
	}

	public void setPayStyle(String payStyle) {
		this.payStyle = payStyle;
	}

	public long getBalacneIfId() {
		return balacneIfId;
	}

	public void setBalacneIfId(long balacneIfId) {
		this.balacneIfId = balacneIfId;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public long getPayFee() {
		return payFee;
	}

	public void setPayFee(long payFee) {
		this.payFee = payFee;
	}

	// private String paySystemId;

	// private String externalId;

	// private Timestamp createTime;

	// private String remark;

}
