package com.ai.slp.order.api.sesdata.param;

import com.ai.opt.base.vo.BaseInfo;

public class SesDataRequest extends BaseInfo{

	private static final long serialVersionUID = 1L;
	//父订单id
	private long orderId;
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
}
