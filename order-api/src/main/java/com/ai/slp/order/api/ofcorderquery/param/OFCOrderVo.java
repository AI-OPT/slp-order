package com.ai.slp.order.api.ofcorderquery.param;

import java.io.Serializable;

public class OFCOrderVo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String orderNo;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
}
