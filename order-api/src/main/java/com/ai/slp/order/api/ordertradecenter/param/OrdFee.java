package com.ai.slp.order.api.ordertradecenter.param;

import java.io.Serializable;

public class OrdFee implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 默认支付方式
     */
    private String payStyle;

    /**
     * 总应收费用
     */
    private long adjustFee;

    public String getPayStyle() {
        return payStyle;
    }

    public long getAdjustFee() {
        return adjustFee;
    }

    public void setPayStyle(String payStyle) {
        this.payStyle = payStyle;
    }

    public void setAdjustFee(long adjustFee) {
        this.adjustFee = adjustFee;
    }

}
