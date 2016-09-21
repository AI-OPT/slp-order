package com.ai.slp.order.vo;

import java.io.Serializable;

public class OrderCouponVo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 优惠名称
	 */
	private String couponName;
	
	/**
	 * 优惠编码
	 */
	private String couponCode;
	
	/**
	 * 关联产品编码
	 */
	private String productCode;
	
	/**
	 * 优惠金额
	 */
	private long amount;

	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}
}
