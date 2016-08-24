package com.ai.slp.order.api.aftersaleorder.param;

import javax.validation.constraints.NotNull;

import com.ai.opt.base.vo.BaseInfo;

public class OrderReturnRequest extends BaseInfo{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 订单id
	 */
	@NotNull(message = "订单ID不能为空")
	private long orderId;
	
	/**
	 * 商品明细id
	 */
	@NotNull(message = "商品明细ID不能为空")
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
