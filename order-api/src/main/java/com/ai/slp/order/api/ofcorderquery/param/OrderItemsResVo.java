package com.ai.slp.order.api.ofcorderquery.param;

import java.io.Serializable;

public class OrderItemsResVo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 产品名称
	 */
	private String productName;
	
	/**
	 * 产品编码
	 */
	private String productCode;
	/**
	 * 产品型号
	 */
	private String productNo;
	/**
	 * 产品单价
	 */
	private long price;
	/**
	 * 产品数量
	 */
	private long quanlity;
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductNo() {
		return productNo;
	}
	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public long getQuanlity() {
		return quanlity;
	}
	public void setQuanlity(long quanlity) {
		this.quanlity = quanlity;
	}
}
