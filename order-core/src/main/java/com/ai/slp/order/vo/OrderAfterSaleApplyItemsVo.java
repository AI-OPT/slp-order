package com.ai.slp.order.vo;

import java.io.Serializable;

public class OrderAfterSaleApplyItemsVo implements Serializable{

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
	 * 申请数量
	 */
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
