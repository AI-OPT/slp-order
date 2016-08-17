package com.ai.slp.order.dao.mapper.bo;

public class OrdOdFeeOffset {
    private long feeOffsetId;

    private String tenantId;

    private long balacneIfId;

    private long orderId;

    private long prodDetalId;

    private String prodId;

    private long offsetFee;

    private String remark;

    public long getFeeOffsetId() {
        return feeOffsetId;
    }

    public void setFeeOffsetId(long feeOffsetId) {
        this.feeOffsetId = feeOffsetId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId == null ? null : tenantId.trim();
    }

    public long getBalacneIfId() {
        return balacneIfId;
    }

    public void setBalacneIfId(long balacneIfId) {
        this.balacneIfId = balacneIfId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getProdDetalId() {
        return prodDetalId;
    }

    public void setProdDetalId(long prodDetalId) {
        this.prodDetalId = prodDetalId;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId == null ? null : prodId.trim();
    }

    public long getOffsetFee() {
        return offsetFee;
    }

    public void setOffsetFee(long offsetFee) {
        this.offsetFee = offsetFee;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}