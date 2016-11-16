package com.ai.slp.order.api.ofc.params;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 订单商品表
 * Date: 2016年11月12日 <br>
 * Copyright (c) 2016 asiainfo.com <br>
 * 
 * @author zhangqiang7
 */
public class OrdOdProdVo implements Serializable{

	private static final long serialVersionUID = 1L;

	private long prodDetalId;

	private String tenantId;

	private long orderId;

	private String prodType;

	private String supplierId;

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

	private long couponFee;

	private long jfFee;

	private String cusServiceFlag;

	private String prodCode;

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
		this.tenantId = tenantId;
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
		this.prodType = prodType;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
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

	public String getProdSn() {
		return prodSn;
	}

	public void setProdSn(String prodSn) {
		this.prodSn = prodSn;
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

	public String getSupplyId() {
		return supplyId;
	}

	public void setSupplyId(String supplyId) {
		this.supplyId = supplyId;
	}

	public String getStorageId() {
		return storageId;
	}

	public void setStorageId(String storageId) {
		this.storageId = storageId;
	}

	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
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
		this.state = state;
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
		this.operDiscountDesc = operDiscountDesc;
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
		this.prodDesc = prodDesc;
	}

	public String getExtendInfo() {
		return extendInfo;
	}

	public void setExtendInfo(String extendInfo) {
		this.extendInfo = extendInfo;
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
		this.updateChlId = updateChlId;
	}

	public String getUpdateOperId() {
		return updateOperId;
	}

	public void setUpdateOperId(String updateOperId) {
		this.updateOperId = updateOperId;
	}

	public String getSkuStorageId() {
		return skuStorageId;
	}

	public void setSkuStorageId(String skuStorageId) {
		this.skuStorageId = skuStorageId;
	}

	public String getIsInvoice() {
		return isInvoice;
	}

	public void setIsInvoice(String isInvoice) {
		this.isInvoice = isInvoice;
	}

	public long getCouponFee() {
		return couponFee;
	}

	public void setCouponFee(long couponFee) {
		this.couponFee = couponFee;
	}

	public long getJfFee() {
		return jfFee;
	}

	public void setJfFee(long jfFee) {
		this.jfFee = jfFee;
	}

	public String getCusServiceFlag() {
		return cusServiceFlag;
	}

	public void setCusServiceFlag(String cusServiceFlag) {
		this.cusServiceFlag = cusServiceFlag;
	}

	public String getProdCode() {
		return prodCode;
	}

	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}

}
