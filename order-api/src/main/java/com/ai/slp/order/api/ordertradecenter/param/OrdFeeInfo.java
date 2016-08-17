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

    public long getTotalFee() {
        return totalFee;
    }

    public long getDiscountFee() {
        return discountFee;
    }

    public long getOperDiscountFee() {
        return operDiscountFee;
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

}
