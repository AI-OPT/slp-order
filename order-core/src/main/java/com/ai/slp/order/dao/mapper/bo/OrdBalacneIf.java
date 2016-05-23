package com.ai.slp.order.dao.mapper.bo;

import java.sql.Timestamp;

public class OrdBalacneIf {
    private long balacneIfId;

    private String tenantId;

    private long orderId;

    private String payStyle;

    private long payFee;

    private String paySystemId;

    private String externalId;

    private Timestamp createTime;

    private String remark;

    public long getBalacneIfId() {
        return balacneIfId;
    }

    public void setBalacneIfId(long balacneIfId) {
        this.balacneIfId = balacneIfId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId == null ? null : tenantId.trim();
    }

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

    public long getPayFee() {
        return payFee;
    }

    public void setPayFee(long payFee) {
        this.payFee = payFee;
    }

    public String getPaySystemId() {
        return paySystemId;
    }

    public void setPaySystemId(String paySystemId) {
        this.paySystemId = paySystemId == null ? null : paySystemId.trim();
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId == null ? null : externalId.trim();
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}