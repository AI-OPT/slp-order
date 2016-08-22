package com.ai.slp.order.api.deliveryorderprint.param;

import java.io.Serializable;

public class DeliveryProdPrintVo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	 /**
     * 商户编号
     */
	private String skuId;

	/**
     * 商品名称
     */
    private String prodName;
    
    /**
     * 规格
     */
    private String extendInfo;
    
    /**
     * 数量
     */
    private long buySum;
    
    /**
     * 合并id
     */
    private long horOrderId;
    
    /**
     * 销售单价
     */
    private long salePrice;
    
	public long getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(long salePrice) {
		this.salePrice = salePrice;
	}

	public long getHorOrderId() {
		return horOrderId;
	}

	public void setHorOrderId(long horOrderId) {
		this.horOrderId = horOrderId;
	}

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public String getExtendInfo() {
		return extendInfo;
	}

	public void setExtendInfo(String extendInfo) {
		this.extendInfo = extendInfo;
	}

	public long getBuySum() {
		return buySum;
	}

	public void setBuySum(long buySum) {
		this.buySum = buySum;
	}
    
    
}
