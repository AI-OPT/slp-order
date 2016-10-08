package com.ai.slp.order.api.ordermodify.param;

import com.ai.opt.base.vo.BaseInfo;

public class OrdRequest extends BaseInfo{
	private static final long serialVersionUID = 1L;
	/**
	 * 订单号
	 */
	private Long orderId;
	/**
	 * 订单状态
	 */
	private String state;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	

}
