package com.ai.slp.order.api.aftersaleorder.param;

import java.io.Serializable;

import com.ai.opt.base.vo.BaseInfo;

public class OrderAfterVo extends BaseInfo{

	private static final long serialVersionUID = 1L;
	
	private long orderId; //售后订单id

	private String busiCode; //业务类型

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public String getBusiCode() {
		return busiCode;
	}

	public void setBusiCode(String busiCode) {
		this.busiCode = busiCode;
	}
}
