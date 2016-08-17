package com.ai.slp.order.dao.mapper.bo;

public class OrdOdProdExtend {
    private long prodDetalExtendId;

    private long prodDetalId;

    private long orderId;

    private String tenantId;

    private String infoJson;

    private String batchFlag;

    public long getProdDetalExtendId() {
        return prodDetalExtendId;
    }

    public void setProdDetalExtendId(long prodDetalExtendId) {
        this.prodDetalExtendId = prodDetalExtendId;
    }

    public long getProdDetalId() {
        return prodDetalId;
    }

    public void setProdDetalId(long prodDetalId) {
        this.prodDetalId = prodDetalId;
    }

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

    public String getInfoJson() {
        return infoJson;
    }

    public void setInfoJson(String infoJson) {
        this.infoJson = infoJson == null ? null : infoJson.trim();
    }

    public String getBatchFlag() {
        return batchFlag;
    }

    public void setBatchFlag(String batchFlag) {
        this.batchFlag = batchFlag == null ? null : batchFlag.trim();
    }
}