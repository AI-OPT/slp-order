package com.ai.slp.order.api.orderlist.param;

import java.io.Serializable;

/**
 * 订单支付对象
 * @author jiaxs
 *
 */
public class OrderPayVo implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/**
	 * 支付方式
	 */
	private String payStyle;
	
	/**
	 * 支付方式前段展示字段
	 */
	private String payStyleName;
	
	/**
	 * 支付金额
	 */
	private Long paidFee;

	public String getPayStyle() {
		return payStyle;
	}

	public void setPayStyle(String payStyle) {
		this.payStyle = payStyle;
	}

	public String getPayStyleName() {
		return payStyleName;
	}

	public void setPayStyleName(String payStyleName) {
		this.payStyleName = payStyleName;
	}

	public Long getPaidFee() {
		return paidFee;
	}

	public void setPaidFee(Long paidFee) {
		this.paidFee = paidFee;
	}
}
