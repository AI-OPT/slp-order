package com.ai.slp.order.dao.mapper.bo;

import java.sql.Timestamp;

public class OrdOdFeeTotal {
    private Long orderId;

    private String tenantId;

    private String payFlag;

    private Long totalFee;

    private Long discountFee;

    private Long operDiscountFee;

    private String operDiscountDesc;

    private Long adjustFee;

    private Long paidFee;

    private Long payFee;

    private String payStyle;

    private Timestamp updateTime;

    private String updateChlId;

    private String updateOperId;

    private Long totalJf;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
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

    public Long getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Long totalFee) {
        this.totalFee = totalFee;
    }

    public Long getDiscountFee() {
        return discountFee;
    }

    public void setDiscountFee(Long discountFee) {
        this.discountFee = discountFee;
    }

    public Long getOperDiscountFee() {
        return operDiscountFee;
    }

    public void setOperDiscountFee(Long operDiscountFee) {
        this.operDiscountFee = operDiscountFee;
    }

    public String getOperDiscountDesc() {
        return operDiscountDesc;
    }

    public void setOperDiscountDesc(String operDiscountDesc) {
        this.operDiscountDesc = operDiscountDesc == null ? null : operDiscountDesc.trim();
    }

    public Long getAdjustFee() {
        return adjustFee;
    }

    public void setAdjustFee(Long adjustFee) {
        this.adjustFee = adjustFee;
    }

    public Long getPaidFee() {
        return paidFee;
    }

    public void setPaidFee(Long paidFee) {
        this.paidFee = paidFee;
    }

    public Long getPayFee() {
        return payFee;
    }

    public void setPayFee(Long payFee) {
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

    public Long getTotalJf() {
        return totalJf;
    }

    public void setTotalJf(Long totalJf) {
        this.totalJf = totalJf;
    }
}