package com.ai.slp.order.api.invoiceprint.param;

import com.ai.opt.base.vo.BaseInfo;

public class InvoiceModifyRequest extends BaseInfo{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 订单号
	 */
	private Long orderId;
	
	/**
	 * 发票状态
	 */
	private String invoiceStatus;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}
}
