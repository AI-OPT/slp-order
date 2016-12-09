package com.ai.slp.order.api.orderlist.param;

import com.ai.opt.base.vo.BaseInfo;

public class QueryOrderRequest extends BaseInfo {

    private static final long serialVersionUID = 1L;

    /**
     * 订单id
     */
    private Long orderId;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

    
}
