package com.ai.slp.order.api.ordertradecenter.param;

import java.io.Serializable;

public class OrdFeeTotalInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private long totalFee;
	private long adjustFee;
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
