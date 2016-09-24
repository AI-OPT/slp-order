package com.ai.slp.order.dao.mapper.bo;

public class OrdOdFeeProd {
    private long orderId;

    private String payStyle;

    private long paidFee;

    private long jfAmount;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getPayStyle() {
        return payStyle;
    }

    public void setPayStyle(String payStyle) {
        this.payStyle = payStyle == null ? null : payStyle.trim();
    }

    public long getPaidFee() {
        return paidFee;
    }

    public void setPaidFee(long paidFee) {
        this.paidFee = paidFee;
    }

    public long getJfAmount() {
        return jfAmount;
    }

    public void setJfAmount(long jfAmount) {
        this.jfAmount = jfAmount;
    }
}