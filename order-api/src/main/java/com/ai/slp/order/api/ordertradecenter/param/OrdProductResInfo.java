package com.ai.slp.order.api.ordertradecenter.param;

import java.io.Serializable;

public class OrdProductResInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 单品Id
     */
    private String skuId;

    /**
     * 单品名称
     */
    private String skuName;

    /**
     * 销售单价
     */
    private long salePrice;

    /**
     * 数量
     */
    private long buySum;

    /**
     * 总计
     */
    private long skuTotalFee;
    
    private String imageUrl;

    public String getSkuId() {
        return skuId;
    }

    public String getSkuName() {
        return skuName;
    }

    public long getSalePrice() {
        return salePrice;
    }

    public long getBuySum() {
        return buySum;
    }

    public void setBuySum(long buySum) {
        this.buySum = buySum;
    }

    public long getSkuTotalFee() {
        return skuTotalFee;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public void setSalePrice(long salePrice) {
        this.salePrice = salePrice;
    }

    public void setSkuTotalFee(long skuTotalFee) {
        this.skuTotalFee = skuTotalFee;
    }

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

}
