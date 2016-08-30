package com.ai.slp.order.api.orderconfirm.param;

import javax.validation.constraints.NotNull;

import com.ai.opt.base.vo.BaseInfo;

public class OrderConfirmRequest extends BaseInfo{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 订单id
	 */
	@NotNull(message="订单ID不能为空")
	private long orderId;

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
}
