package com.ai.slp.order.dao.mapper.bo;

import java.sql.Timestamp;

public class OrdOdProd {
    private long prodDetalId;

    private String tenantId;

    private long orderId;

    private String prodType;

    private long supplierId;

    private String sellerId;

    private String prodId;

    private String prodName;

    private String prodSn;

    private String skuId;

    private String standardProdId;

    private String supplyId;

    private String storageId;

    private String routeId;

    private Timestamp validTime;

    private Timestamp invalidTime;

    private String state;

    private long buySum;

    private long salePrice;

    private long costPrice;

    private long totalFee;

    private long discountFee;

    private long operDiscountFee;

    private String operDiscountDesc;

    private long adjustFee;

    private long jf;

    private String prodDesc;

    private String extendInfo;

    private Timestamp updateTime;

    private String updateChlId;

    private String updateOperId;

    private String skuStorageId;

    private String isInvoice;

    public long getProdDetalId() {
        return prodDetalId;
    }

    public void setProdDetalId(long prodDetalId) {
        this.prodDetalId = prodDetalId;
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

    public String getProdType() {
        return prodType;
    }

    public void setProdType(String prodType) {
        this.prodType = prodType == null ? null : prodType.trim();
    }

    public long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(long supplierId) {
        this.supplierId = supplierId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId == null ? null : sellerId.trim();
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId == null ? null : prodId.trim();
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName == null ? null : prodName.trim();
    }

    public String getProdSn() {
        return prodSn;
    }

    public void setProdSn(String prodSn) {
        this.prodSn = prodSn == null ? null : prodSn.trim();
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId == null ? null : skuId.trim();
    }

    public String getStandardProdId() {
        return standardProdId;
    }

    public void setStandardProdId(String standardProdId) {
        this.standardProdId = standardProdId == null ? null : standardProdId.trim();
    }

    public String getSupplyId() {
        return supplyId;
    }

    public void setSupplyId(String supplyId) {
        this.supplyId = supplyId == null ? null : supplyId.trim();
    }

    public String getStorageId() {
        return storageId;
    }

    public void setStorageId(String storageId) {
        this.storageId = storageId == null ? null : storageId.trim();
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId == null ? null : routeId.trim();
    }

    public Timestamp getValidTime() {
        return validTime;
    }

    public void setValidTime(Timestamp validTime) {
        this.validTime = validTime;
    }

    public Timestamp getInvalidTime() {
        return invalidTime;
    }

    public void setInvalidTime(Timestamp invalidTime) {
        this.invalidTime = invalidTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public long getBuySum() {
        return buySum;
    }

    public void setBuySum(long buySum) {
        this.buySum = buySum;
    }

    public long getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(long salePrice) {
        this.salePrice = salePrice;
    }

    public long getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(long costPrice) {
        this.costPrice = costPrice;
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

    public long getJf() {
        return jf;
    }

    public void setJf(long jf) {
        this.jf = jf;
    }

    public String getProdDesc() {
        return prodDesc;
    }

    public void setProdDesc(String prodDesc) {
        this.prodDesc = prodDesc == null ? null : prodDesc.trim();
    }

    public String getExtendInfo() {
        return extendInfo;
    }

    public void setExtendInfo(String extendInfo) {
        this.extendInfo = extendInfo == null ? null : extendInfo.trim();
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

    public String getSkuStorageId() {
        return skuStorageId;
    }

    public void setSkuStorageId(String skuStorageId) {
        this.skuStorageId = skuStorageId == null ? null : skuStorageId.trim();
    }

    public String getIsInvoice() {
        return isInvoice;
    }

    public void setIsInvoice(String isInvoice) {
        this.isInvoice = isInvoice == null ? null : isInvoice.trim();
    }
}