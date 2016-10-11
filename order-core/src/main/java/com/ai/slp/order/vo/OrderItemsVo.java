package com.ai.slp.order.vo;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class OrderItemsVo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 产品名称
	 */
	@JSONField(name="ProductName")
	private String productName;
	
	/**
	 * 产品编码
	 */
	@JSONField(name="ProductCode")
	private String ProductCode;
	/**
	 * 产品型号
	 */
	@JSONField(name="ProductNo")
	private String productNo;
	/**
	 * 产品单价
	 */
	@JSONField(name="Price")
	private long price;
	/**
	 * 产品数量
	 */
	@JSONField(name="Quanlity")
	private long quanlity;
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductCode() {
		return ProductCode;
	}
	public void setProductCode(String productCode) {
		ProductCode = productCode;
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
