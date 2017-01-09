package com.ai.slp.order.api.ofcactual.param;

import java.util.List;

import com.ai.opt.base.vo.BaseInfo;

public class OfcOrderCreateRequest extends BaseInfo{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 订单号
	 */
	 private List<Long> orderIds;

	public List<Long> getOrderIds() {
		return orderIds;
	}

	public void setOrderIds(List<Long> orderIds) {
		this.orderIds = orderIds;
	}
}
