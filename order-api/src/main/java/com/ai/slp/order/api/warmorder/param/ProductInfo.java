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
	private long bugSum;

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public long getBugSum() {
		return bugSum;
	}

	public void setBugSum(long bugSum) {
		this.bugSum = bugSum;
	}

}
