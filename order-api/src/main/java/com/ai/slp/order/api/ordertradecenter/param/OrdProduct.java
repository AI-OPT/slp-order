package com.ai.slp.order.api.ordertradecenter.param;

import java.io.Serializable;

public class OrdProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 销售品Id
     */
    private String prodId;

    /**
     * 销售品名称
     */
    private String prodName;

    /**
     * 购买数量
     */
    private long buySum;

    /**
     * 总费用
     */
    private long totalFee;

    /**
     * 优惠费用
     */
    private long discountFee;

    /**
     * 减免金额
     */
    private long operDiscountFee;

    /**
     * 减免原因
     */
    private long operDiscountDesc;

    /**
     * 应收费用
     */
    private long adjustFee;

    /**
     * 已收费用
     */
    private long paidFee;

    public String getProdId() {
        return prodId;
    }

    public String getProdName() {
        return prodName;
    }

    public long getBuySum() {
        return buySum;
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

    public long getOperDiscountDesc() {
        return operDiscountDesc;
    }

    public long getAdjustFee() {
        return adjustFee;
    }

    public long getPaidFee() {
        return paidFee;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public void setBuySum(long buySum) {
        this.buySum = buySum;
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

    public void setOperDiscountDesc(long operDiscountDesc) {
        this.operDiscountDesc = operDiscountDesc;
    }

    public void setAdjustFee(long adjustFee) {
        this.adjustFee = adjustFee;
    }

    public void setPaidFee(long paidFee) {
        this.paidFee = paidFee;
    }

}
