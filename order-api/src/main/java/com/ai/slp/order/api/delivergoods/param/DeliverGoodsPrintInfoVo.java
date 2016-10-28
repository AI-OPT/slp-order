package com.ai.slp.order.api.delivergoods.param;

import java.io.Serializable;
import java.util.List;

public class DeliverGoodsPrintInfoVo implements Serializable{
	
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
    * 价格
    */
   private long salePrice;
   
   /**
    * 数量
    */
   private long buySum;
   
   /**
    * 合并id
    */
   private List<Long> horOrderId;

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
	
	public long getSalePrice() {
		return salePrice;
	}
	
	public void setSalePrice(long salePrice) {
		this.salePrice = salePrice;
	}
	
	public long getBuySum() {
		return buySum;
	}
	
	public void setBuySum(long buySum) {
		this.buySum = buySum;
	}

	public List<Long> getHorOrderId() {
		return horOrderId;
	}

	public void setHorOrderId(List<Long> horOrderId) {
		this.horOrderId = horOrderId;
	}
}
