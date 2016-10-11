package com.ai.slp.order.api.orderpay.param;

import com.ai.opt.base.vo.BaseInfo;

public class OrderOidRequest extends BaseInfo{

	private static final long serialVersionUID = 1L;
	
	private String oid;
	
	/**
	 * 订单id
	 */
	private Long orderId;

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
}
