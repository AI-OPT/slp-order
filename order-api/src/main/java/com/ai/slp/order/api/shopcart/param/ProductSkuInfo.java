package com.ai.slp.order.api.shopcart.param;

import com.ai.opt.base.vo.BaseResponse;

/**
 * 销售商品下SKU单品的信息
 * Created by jackieliu on 16/5/20.
 */
public class ProductSkuInfo extends BaseResponse {
    private static final long serialVersionUID = 1L;
    /**
     * 销售商(租户)标识
     */
    private String supplierId;
    /**
     * sku单品标识
     */
    private String skuId;
    /**
     * sku名称
     */
    private String skuName;
    /**
     * 销售商品标识
     */
    private String prodId;
    /**
     * 商品名称
     */
    private String prodName;
    /**
     * 属性串
     */
    private String saleAttrs;
    /**
     * SKU单品的状态
     */
    private String state;
    /**
     * 商品图片标识
     */
    private String vfsId;
    /**
     * 图片扩展名
     */
    private String picType;
    /**
     * 库存可用量
     */
    private Long usableNum;
    /**
     * 销售价
     */
    private Long salePrice;
    
    /**
     * 库存组id
     */
    private String storageGroupId;

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getSaleAttrs() {
        return saleAttrs;
    }

    public void setSaleAttrs(String saleAttrs) {
        this.saleAttrs = saleAttrs;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getVfsId() {
        return vfsId;
    }

    public void setVfsId(String vfsId) {
        this.vfsId = vfsId;
    }

    public Long getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Long salePrice) {
        this.salePrice = salePrice;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getPicType() {
        return picType;
    }

    public void setPicType(String picType) {
        this.picType = picType;
    }

    public Long getUsableNum() {
        return usableNum;
    }

    public void setUsableNum(Long usableNum) {
        this.usableNum = usableNum;
    }

	public String getStorageGroupId() {
		return storageGroupId;
	}

	public void setStorageGroupId(String storageGroupId) {
		this.storageGroupId = storageGroupId;
	}
}
