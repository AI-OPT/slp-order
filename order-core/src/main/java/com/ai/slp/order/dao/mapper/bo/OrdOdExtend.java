package com.ai.slp.order.dao.mapper.bo;

public class OrdOdExtend {
    private Long orderId;

    private String tenantId;

    private String infoJson;

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

    public String getInfoJson() {
        return infoJson;
    }

    public void setInfoJson(String infoJson) {
        this.infoJson = infoJson == null ? null : infoJson.trim();
    }
}