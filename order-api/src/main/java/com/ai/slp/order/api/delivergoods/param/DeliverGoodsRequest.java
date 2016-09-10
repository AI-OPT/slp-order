package com.ai.slp.order.api.delivergoods.param;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.ai.opt.base.vo.BaseInfo;

public class DeliverGoodsRequest extends BaseInfo{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 订单id (父订单)
	 */
	@NotNull(message="订单id不能为空")
	private Long orderId;
	
	/**
	 * 物流公司id
	 */
	@NotBlank(message="物流公司id不能为空")
	private String expressId;
	
	/**
	 * 物流单号
	 */
	@NotBlank(message="物流单号不能为空")
	private String expressOddNumber;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getExpressId() {
		return expressId;
	}

	public void setExpressId(String expressId) {
		this.expressId = expressId;
	}

	public String getExpressOddNumber() {
		return expressOddNumber;
	}

	public void setExpressOddNumber(String expressOddNumber) {
		this.expressOddNumber = expressOddNumber;
	}
}
