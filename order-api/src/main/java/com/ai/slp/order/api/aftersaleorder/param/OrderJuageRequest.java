package com.ai.slp.order.api.aftersaleorder.param;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.ai.opt.base.vo.BaseInfo;

public class OrderJuageRequest extends BaseInfo{
	
	private static final long serialVersionUID = 1L;

	/**
	 * 订单id
	 */
	@NotNull(message = "订单ID不能为空")
	private long orderId;
	
	/**
	 * 商品明细id
	 */
	@NotBlank(message = "商品skuId不能为空")
	private String skuId;

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
}
