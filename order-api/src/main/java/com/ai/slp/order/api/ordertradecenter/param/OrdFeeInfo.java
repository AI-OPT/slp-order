package com.ai.slp.order.api.ordertradecenter.param;

import java.io.Serializable;

public class OrdFeeInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 总费用
     */
    private long totalFee;

    /**
     * 总优惠费用
     */
    private long discountFee;

    /**
     * 减免金额
     */
    private long operDiscountFee;

    /**
     * 减免原因
     */
    private String operDiscountDesc;

    /**
     * 应收费用
     */
    private long adjustFee;

    /**
     * 已收费用
     */
    private long paidFee;

    /**
     * 默认支付方式
     */
    private String payStyle;

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

    public long getTotalFee() {
        return totalFee;
    }

    public long getDiscountFee() {
        return discountFee;
    }

    public long getOperDiscountFee() {
        return operDiscountFee;
    }

    public String getOperDiscountDesc() {
        return operDiscountDesc;
    }

    public long getPaidFee() {
        return paidFee;
    }

    public void setTotalFee(long totalFee) {
        this.totalFee = totalFee;
    }

    public void setDiscountFee(long discountFee) {
        this.discountFee = discountFee;
    }

    public void setOperDiscountFee(long operDiscountFee) {
        this.operDiscountFee = operDiscountFee;
    }

    public void setOperDiscountDesc(String operDiscountDesc) {
        this.operDiscountDesc = operDiscountDesc;
    }

    public void setPaidFee(long paidFee) {
        this.paidFee = paidFee;
    }

}
