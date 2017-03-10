package com.ai.slp.order.api.orderlist.param;

import java.io.Serializable;

public class BehindOrdProductVo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	// 商品名称
	private String prodname;
	// 商品购买数量
	private long buysum;

	public String getProdname() {
		return prodname;
	}

	public void setProdname(String prodname) {
		this.prodname = prodname;
	}

	public long getBuysum() {
		return buysum;
	}

	public void setBuysum(long buysum) {
		this.buysum = buysum;
	}
}
