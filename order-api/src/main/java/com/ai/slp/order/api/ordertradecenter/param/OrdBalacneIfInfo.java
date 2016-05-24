package com.ai.slp.order.api.ordertradecenter.param;

import java.io.Serializable;

public class OrdBalacneIfInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 支付流水Id
     */
    private long balacneIfId;

    /**
     * 支付方式
     */
    private String payStyle;

    /**
     * 支付金额
     */
    private long payFee;

    public long getBalacneIfId() {
        return balacneIfId;
    }

    public String getPayStyle() {
        return payStyle;
    }

    public long getPayFee() {
        return payFee;
    }

    public void setBalacneIfId(long balacneIfId) {
        this.balacneIfId = balacneIfId;
    }

    public void setPayStyle(String payStyle) {
        this.payStyle = payStyle;
    }

    public void setPayFee(long payFee) {
        this.payFee = payFee;
    }

}
