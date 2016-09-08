package com.ai.slp.order.dao.mapper.bo;

import java.sql.Timestamp;

public class OrdOdFeeTotal {
    private long orderId;

    private String tenantId;

    private String payFlag;

    private long totalFee;

    private long discountFee;

    private long operDiscountFee;

    private String operDiscountDesc;

    private long adjustFee;

    private long paidFee;

    private long payFee;

    private String payStyle;

    private Timestamp updateTime;

    private String updateChlId;

    private String updateOperId;

    private long totalJf;

    private long freight;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId == null ? null : tenantId.trim();
    }

    public String getPayFlag() {
        return payFlag;
    }

    public void setPayFlag(String payFlag) {
        this.payFlag = payFlag == null ? null : payFlag.trim();
    }

    public long getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(long totalFee) {
        this.totalFee = totalFee;
    }

    public long getDiscountFee() {
        return discountFee;
    }

    public void setDiscountFee(long discountFee) {
        this.discountFee = discountFee;
    }

    public long getOperDiscountFee() {
        return operDiscountFee;
    }

    public void setOperDiscountFee(long operDiscountFee) {
        this.operDiscountFee = operDiscountFee;
    }

    public String getOperDiscountDesc() {
        return operDiscountDesc;
    }

    public void setOperDiscountDesc(String operDiscountDesc) {
        this.operDiscountDesc = operDiscountDesc == null ? null : operDiscountDesc.trim();
    }

    public long getAdjustFee() {
        return adjustFee;
    }

    public void setAdjustFee(long adjustFee) {
        this.adjustFee = adjustFee;
    }

    public long getPaidFee() {
        return paidFee;
    }

    public void setPaidFee(long paidFee) {
        this.paidFee = paidFee;
    }

    public long getPayFee() {
        return payFee;
    }

    public void setPayFee(long payFee) {
        this.payFee = payFee;
    }

    public String getPayStyle() {
        return payStyle;
    }

    public void setPayStyle(String payStyle) {
        this.payStyle = payStyle == null ? null : payStyle.trim();
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateChlId() {
        return updateChlId;
    }

    public void setUpdateChlId(String updateChlId) {
        this.updateChlId = updateChlId == null ? null : updateChlId.trim();
    }

    public String getUpdateOperId() {
        return updateOperId;
    }

    public void setUpdateOperId(String updateOperId) {
        this.updateOperId = updateOperId == null ? null : updateOperId.trim();
    }

    public long getTotalJf() {
        return totalJf;
    }

    public void setTotalJf(long totalJf) {
        this.totalJf = totalJf;
    }

    public long getFreight() {
        return freight;
    }

    public void setFreight(long freight) {
        this.freight = freight;
    }
}