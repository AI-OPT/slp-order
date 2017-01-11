package com.ai.slp.order.api.synchronize.params;

import java.io.Serializable;
import java.sql.Timestamp;

public class OrdOdProdVo implements Serializable {

	// private long prodDetalId;

	private static final long serialVersionUID = 1L;

	/**
	 * 租户id
	 */
	// private String tenantId;

	/**
	 * 订单id
	 */
	// private long orderId;

	/**
	 * 销售品类型
	 */
	private String prodType;

	// private String supplierId;

	/**
	 * 售货商ID
	 */
	private String sellerId;

	/**
	 * 销售品ID
	 */
	private String prodId;

	/**
	 * 销售品名称
	 */
	private String prodName;

	// private String prodSn;

	/**
	 * 单品ID
	 */
	private String skuId;

	// private String standardProdId;

	// private String supplyId;

	/**
	 * 库存ID
	 */
	private String storageId;

	/**
	 * 仓库Id
	 */
	private String routeId;

	/**
	 * 生效时间
	 */
	private Timestamp validTime;

	/**
	 * 失效时间
	 */
	private Timestamp invalidTime;

	/**
	 * state
	 */
	private String state;

	/**
	 * 购买数量
	 */
	private long buySum;

	private long salePrice;

	// private long costPrice;

	/**
	 * 总费用
	 */
	private long totalFee;

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
	 * 赠送积分
	 */
	private long jf;

	// private String prodDesc;

	// private String extendInfo;

	// private Timestamp updateTime;

	// private String updateChlId;

	// private String updateOperId;

	/**
	 * SKU库存ID
	 */
	private String skuStorageId;

	// private String isInvoice;

	// private long couponFee;

	// private long jfFee;

	/**
	 * 售后标识
	 */
	private String cusServiceFlag;

	/**
	 * 商品编码
	 */
	private String prodCode;

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
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

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public String getStorageId() {
		return storageId;
	}

	public void setStorageId(String storageId) {
		this.storageId = storageId;
	}

	public long getBuySum() {
		return buySum;
	}

	public void setBuySum(long buySum) {
		this.buySum = buySum;
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

	public String getSkuStorageId() {
		return skuStorageId;
	}

	public void setSkuStorageId(String skuStorageId) {
		this.skuStorageId = skuStorageId;
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

	public String getProdType() {
		return prodType;
	}

	public void setProdType(String prodType) {
		this.prodType = prodType;
	}

	public long getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(long salePrice) {
		this.salePrice = salePrice;
	}

}
