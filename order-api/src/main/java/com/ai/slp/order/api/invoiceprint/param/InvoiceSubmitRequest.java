package com.ai.slp.order.api.invoiceprint.param;

import com.ai.opt.base.vo.BaseInfo;

public class InvoiceSubmitRequest extends BaseInfo{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 订单号
	 */
	private Long orderId;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
}
