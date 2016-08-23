package com.ai.slp.order.api.orderlist.param;

import javax.validation.constraints.NotNull;


import com.ai.opt.base.vo.BaseInfo;

public class QueryOrderRequest extends BaseInfo {

    private static final long serialVersionUID = 1L;

    /**
     * 订单id
     */
	@NotNull(message="订单id不能为空")
    private Long orderId;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

}
