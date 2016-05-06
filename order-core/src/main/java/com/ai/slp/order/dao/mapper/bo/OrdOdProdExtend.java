package com.ai.slp.order.dao.mapper.bo;

import java.math.BigDecimal;

public class OrdOdProdExtend {
    private Long prodDetalExtendId;

    private Long prodDetalId;

    private BigDecimal orderId;

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

    public BigDecimal getOrderId() {
        return orderId;
    }

    public void setOrderId(BigDecimal orderId) {
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