package com.ai.slp.order.api.o2pcallback.param;

import com.ai.opt.base.vo.BaseInfo;

public class O2pCallBackRequest extends BaseInfo {

    private static final long serialVersionUID = 1L;

    /**
     * 外部订单Id
     */
    private String externalOrderId;

    /**
     * 外部供货商Id
     */
    private String externalSupplyId;

    /**
     * 订单状态
     */
    private String state;

    public String getExternalOrderId() {
        return externalOrderId;
    }

    public String getExternalSupplyId() {
        return externalSupplyId;
    }

    public String getState() {
        return state;
    }

    public void setExternalOrderId(String externalOrderId) {
        this.externalOrderId = externalOrderId;
    }

    public void setExternalSupplyId(String externalSupplyId) {
        this.externalSupplyId = externalSupplyId;
    }

    public void setState(String state) {
        this.state = state;
    }

}
