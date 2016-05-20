package com.ai.slp.order.dao.mapper.bo;

public class OrdOdProdExtend {
    private Long prodDetalExtendId;

    private Long prodDetalId;

    private Long orderId;

    private String tenantId;

    private String infoJson;

    public Long getProdDetalExtendId() {
        return prodDetalExtendId;
    }

    public void setProdDetalExtendId(Long prodDetalExtendId) {
        this.prodDetalExtendId = prodDetalExtendId;
    }

    public Long getProdDetalId() {
        return prodDetalId;
    }

    public void setProdDetalId(Long prodDetalId) {
        this.prodDetalId = prodDetalId;
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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

}