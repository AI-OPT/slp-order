package com.ai.slp.order.api.shopcart.param;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 购物车中商品信息
 *
 * Date: 2016年5月16日 <br>
 * Copyright (c) 2016 asiainfo.com <br>
 *
 * @author liutong5
 */
public class CartProdInfo implements Serializable{
    /**
     * 销售商(租户)标识
     */
    private String supplierId;
    /**
     * SKU单品标识
     */
    private String skuId;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 销售商品标识
     */
    private String productId;

    /**
     * 属性串
     */
    private String saleAttrs;
    /**
     * 数量
     */
    private long buyNum;
    /**
     * 商品状态
     */
    private String state;
    /**
     * 库存可用量
     */
    private long usableNum;
    /**
     * 商品图片标识
     */
    private String vfsId;
    /**
     * 图片类型
     */
    private String picType;
    /**
     * 图片地址,为前端预留字段
     */
    private String picUrl;
    /**
     * 添加时间
     */
    private Timestamp insertTime;
    /**
     * 销售价,单位:厘
     */
    private Long salePrice;

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSaleAttrs() {
        return saleAttrs;
    }

    public void setSaleAttrs(String saleAttrs) {
        this.saleAttrs = saleAttrs;
    }

    public void setUsableNum(long usableNum) {
        this.usableNum = usableNum;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public long getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(long buyNum) {
        this.buyNum = buyNum;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public long getUsableNum() {
        return usableNum;
    }

    public String getVfsId() {
        return vfsId;
    }

    public void setVfsId(String vfsId) {
        this.vfsId = vfsId;
    }

    public Timestamp getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Timestamp insertTime) {
        this.insertTime = insertTime;
    }

    public Long getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Long salePrice) {
        this.salePrice = salePrice;
    }

    public String getPicType() {
        return picType;
    }

    public void setPicType(String picType) {
        this.picType = picType;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
