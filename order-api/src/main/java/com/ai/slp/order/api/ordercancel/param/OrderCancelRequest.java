package com.ai.slp.order.api.ordercancel.param;

import javax.validation.constraints.NotNull;

import com.ai.opt.base.vo.BaseInfo;

/**
 * 未支付订单处理的请求参数（手动关闭）
 * @author caofz
 *
 */
public class OrderCancelRequest extends BaseInfo{

	private static final long serialVersionUID = 1L;
	
	@NotNull(message = "订单Id不能为空")
	private Long orderId;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	 
	 
}
