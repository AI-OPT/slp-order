package com.ai.slp.order.api.orderlist.param;

import java.io.Serializable;

public class BehindOrdProductVo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 商品名称
	 */
	private String prodName;
	
	/**
	 * 数量
	 */
	private long buySum;

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
