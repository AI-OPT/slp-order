package com.ai.slp.order.api.synchronize.params;

import java.io.Serializable;

public class OrdOdFeeTotalVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 订单id
	 */
	private long orderId;

	/**
	 * 租户id
	 */
	//private String tenantId;

	// private String payFlag;

	/**
	 * 总费用
	 */
	private long totalFee;

	/**
	 * 总优惠金额
	 */
	private long discountFee;

	/**
	 * 总减免费用(营业员)
	 */
	private long operDiscountFee;

	/**
	 * 减免原因
	 */
	private String operDiscountDesc;

	/**
	 * 总应收费用
	 */
	private long adjustFee;

	/**
	 * 实收费用
	 */
	private long paidFee;

	/**
	 * 总待收费用
	 */
	private long payFee;

	/**
	 * 支付方式
	 */
	private String payStyle;

	// private Timestamp updateTime;

	// private String updateChlId;

	// private String updateOperId;

	// private long totalJf;

	/**
	 * 运费
	 */
	private long freight;

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public long getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(long totalFee) {
		this.totalFee = totalFee;
	}

	public long getDiscountFee() {
		return discountFee;
	}

	public void setDiscountFee(long discountFee) {
		this.discountFee = discountFee;
	}

	public long getOperDiscountFee() {
		return operDiscountFee;
	}

	public void setOperDiscountFee(long operDiscountFee) {
		this.operDiscountFee = operDiscountFee;
	}

	public String getOperDiscountDesc() {
		return operDiscountDesc;
	}

	public void setOperDiscountDesc(String operDiscountDesc) {
		this.operDiscountDesc = operDiscountDesc;
	}

	public long getAdjustFee() {
		return adjustFee;
	}

	public void setAdjustFee(long adjustFee) {
		this.adjustFee = adjustFee;
	}

	public long getPaidFee() {
		return paidFee;
	}

	public void setPaidFee(long paidFee) {
		this.paidFee = paidFee;
	}

	public long getPayFee() {
		return payFee;
	}

	public void setPayFee(long payFee) {
		this.payFee = payFee;
	}

	public String getPayStyle() {
		return payStyle;
	}

	public void setPayStyle(String payStyle) {
		this.payStyle = payStyle;
	}

	public long getFreight() {
		return freight;
	}

	public void setFreight(long freight) {
		this.freight = freight;
	}

}
