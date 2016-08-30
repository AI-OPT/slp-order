package com.ai.slp.order.api.warmorder.param;

import java.io.Serializable;

public class ProductInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 商品名称
	 */
	private String prodName;
	/**
	 * 购买数量
	 */
	private long buySum;
	/**
	 * 销售单价
	 */
	private long salePrice;
	/**
	 * 总优惠费用
	 */
	private long discountFee;
	/**
	 * 积分
	 */
	private long jf;
	/**
	 * 应收费用
	 */
	private long adjustFee;

	public long getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(long salePrice) {
		this.salePrice = salePrice;
	}

	public long getDiscountFee() {
		return discountFee;
	}

	public void setDiscountFee(long discountFee) {
		this.discountFee = discountFee;
	}

	public long getJf() {
		return jf;
	}

	public void setJf(long jf) {
		this.jf = jf;
	}

	public long getAdjustFee() {
		return adjustFee;
	}

	public void setAdjustFee(long adjustFee) {
		this.adjustFee = adjustFee;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public long getBuySum() {
		return buySum;
	}

	public void setBuySum(long buySum) {
		this.buySum = buySum;
	}

}
