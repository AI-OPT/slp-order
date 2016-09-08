package com.ai.slp.order.api.aftersaleorder.param;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

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
	
	/**
	 * 商品数量
	 */
	private long prodSum;
	
	/**
	 * 受理工号
	 */
	@NotBlank(message="受理工号不能为空")
	private String operId;

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

	public String getOperId() {
		return operId;
	}

	public void setOperId(String operId) {
		this.operId = operId;
	}

	public long getProdSum() {
		return prodSum;
	}

	public void setProdSum(long prodSum) {
		this.prodSum = prodSum;
	}
}
