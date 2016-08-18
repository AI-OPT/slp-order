package com.ai.slp.order.dao.mapper.bo;

public class DeliverInfoProd {
    private long deliverInfoId;

    private String skuId;

    private String prodName;

    private String extendInfo;

    private long buySum;

    private long salePrice;

    public long getDeliverInfoId() {
        return deliverInfoId;
    }

    public void setDeliverInfoId(long deliverInfoId) {
        this.deliverInfoId = deliverInfoId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId == null ? null : skuId.trim();
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName == null ? null : prodName.trim();
    }

    public String getExtendInfo() {
        return extendInfo;
    }

    public void setExtendInfo(String extendInfo) {
        this.extendInfo = extendInfo == null ? null : extendInfo.trim();
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
}