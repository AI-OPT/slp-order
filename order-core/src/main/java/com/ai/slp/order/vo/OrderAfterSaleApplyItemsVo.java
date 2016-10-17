package com.ai.slp.order.vo;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class OrderAfterSaleApplyItemsVo implements Serializable{

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
	private String productCode;
	
	/**
	 * 申请数量
	 */
	@JSONField(name="ApplyQuanlity")
	private long applyQuanlity;

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

	public long getApplyQuanlity() {
		return applyQuanlity;
	}

	public void setApplyQuanlity(long applyQuanlity) {
		this.applyQuanlity = applyQuanlity;
	}
}
