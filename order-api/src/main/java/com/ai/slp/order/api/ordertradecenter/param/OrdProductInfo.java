package com.ai.slp.order.api.ordertradecenter.param;

import java.io.Serializable;

public class OrdProductInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 销售品Id
     */
    private String prodId;

    /**
     * 销售品名称
     */
    private String prodName;

    /**
     * 单品Id
     */
    private String skuId;

    /**
     * 标准品Id
     */
    private String standardProdId;

    /**
     * 库存Id
     */
    private String storageId;

    /**
     * sku库存Id
     */
    private String skuStorageId;

    /**
     * 销售单价
     */
    private long salePrice;

    /**
     * 购买数量
     */
    private long buySum;

    /**
     * 优惠费用
     */
    private long discountFee;

    /**
     * 减免金额
     */
    private long operDiscountFee;

    /**
     * 减免原因
     */
    private String operDiscountDesc;

    /**
     * 应收费用
     */
    private long adjustFee;

    /**
     * 已收费用
     */
    private long paidFee;

    public String getProdId() {
        return prodId;
    }

    public String getProdName() {
        return prodName;
    }

    public long getBuySum() {
        return buySum;
    }

    public long getDiscountFee() {
        return discountFee;
    }

    public long getOperDiscountFee() {
        return operDiscountFee;
    }

    public String getOperDiscountDesc() {
        return operDiscountDesc;
    }

    public long getAdjustFee() {
        return adjustFee;
    }

    public long getPaidFee() {
        return paidFee;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public void setBuySum(long buySum) {
        this.buySum = buySum;
    }

    public void setDiscountFee(long discountFee) {
        this.discountFee = discountFee;
    }

    public void setOperDiscountFee(long operDiscountFee) {
        this.operDiscountFee = operDiscountFee;
    }

    public void setOperDiscountDesc(String operDiscountDesc) {
        this.operDiscountDesc = operDiscountDesc;
    }

    public void setAdjustFee(long adjustFee) {
        this.adjustFee = adjustFee;
    }

    public void setPaidFee(long paidFee) {
        this.paidFee = paidFee;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getStandardProdId() {
        return standardProdId;
    }

    public void setStandardProdId(String standardProdId) {
        this.standardProdId = standardProdId;
    }

    public String getStorageId() {
        return storageId;
    }

    public String getSkuStorageId() {
        return skuStorageId;
    }

    public void setStorageId(String storageId) {
        this.storageId = storageId;
    }

    public void setSkuStorageId(String skuStorageId) {
        this.skuStorageId = skuStorageId;
    }

    public long getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(long salePrice) {
        this.salePrice = salePrice;
    }

}
