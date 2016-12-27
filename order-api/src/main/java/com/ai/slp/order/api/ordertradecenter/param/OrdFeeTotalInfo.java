package com.ai.slp.order.api.ordertradecenter.param;

import java.io.Serializable;

public class OrdFeeTotalInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private long totalFee; //总费用
	private long adjustFee;//实际支付费用
	public long getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(long totalFee) {
		this.totalFee = totalFee;
	}
	public long getAdjustFee() {
		return adjustFee;
	}
	public void setAdjustFee(long adjustFee) {
		this.adjustFee = adjustFee;
	}
}
