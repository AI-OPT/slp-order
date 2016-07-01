package com.ai.slp.order.api.orderlist.param;

import com.ai.opt.base.vo.BaseInfo;

public class QueryApiOrderRequest extends BaseInfo {

    private static final long serialVersionUID = 1L;

    /**
     * 下游订单id
     */
    private String downstreamOrderId;

    public String getDownstreamOrderId() {
        return downstreamOrderId;
    }

    public void setDownstreamOrderId(String downstreamOrderId) {
        this.downstreamOrderId = downstreamOrderId;
    }

}
