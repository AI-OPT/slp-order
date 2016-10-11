package com.ai.slp.order.vo;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class OrderCouponVo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 优惠名称
	 */
	@JSONField(name="CouponName")
	private String couponName;
	
	/**
	 * 优惠编码
	 */
	@JSONField(name="CouponCode")
	private String couponCode;
	
	/**
	 * 关联产品编码
	 */
	@JSONField(name="ProductCode")
	private String productCode;
	
	/**
	 * 优惠金额
	 */
	@JSONField(name="Amount")
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
