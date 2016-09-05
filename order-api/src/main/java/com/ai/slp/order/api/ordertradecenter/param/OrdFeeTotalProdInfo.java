package com.ai.slp.order.api.ordertradecenter.param;

import com.ai.opt.base.vo.BaseInfo;

public class OrdFeeTotalProdInfo extends BaseInfo{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 支付方式
	 */
	private String payStyle;
	
	/**
	 * 实收费用
	 */
	private long paidFee;

	public String getPayStyle() {
		return payStyle;
	}

	public void setPayStyle(String payStyle) {
		this.payStyle = payStyle;
	}

	public long getPaidFee() {
		return paidFee;
	}

	public void setPaidFee(long paidFee) {
		this.paidFee = paidFee;
	}
	
}
