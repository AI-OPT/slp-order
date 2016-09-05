package com.ai.slp.order.api.warmorder.param;

import com.ai.opt.base.vo.BaseResponse;

public class OrderWarmDetailResponse extends BaseResponse{
	private static final long serialVersionUID = 1L;
	/**
	 * 预警订单信息
	 */
	private OrderWarmVo orderWarmVo;

	public OrderWarmVo getOrderWarmVo() {
		return orderWarmVo;
	}

	public void setOrderWarmVo(OrderWarmVo orderWarmVo) {
		this.orderWarmVo = orderWarmVo;
	}
	

}
