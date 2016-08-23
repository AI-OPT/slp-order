package com.ai.slp.order.api.aftersaleorder.param;

import com.ai.opt.base.vo.BaseInfo;

public class OrderReturnRequest extends BaseInfo{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 订单id
	 */
	private long orderId;
	
	/**
	 * 商品明细id
	 */
	private long prodDetalId;

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public long getProdDetalId() {
		return prodDetalId;
	}

	public void setProdDetalId(long prodDetalId) {
		this.prodDetalId = prodDetalId;
	}
	
}
