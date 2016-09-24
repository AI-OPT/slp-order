package com.ai.slp.order.api.invoiceprint.param;

import java.io.Serializable;


public class InvoicePrintVo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 订单号
	 */
	private long orderId;

	/**
	 * 发票类目
	 */
	private String invoiceContent;
	
	/**
	 * 发票抬头
	 */
	private String invoiceTitle;
	
	/**
	 * 发票类型
	 */
	private String invoiceType;
	
	/**
	 * 发票打印状态
	 */
	private String invoiceStatus;
	
	/**
	 * 税率
	 */
	private String taxRate;
	
	/**
	 * 税额
	 */
	private long taxAmount;

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public String getInvoiceContent() {
		return invoiceContent;
	}

	public void setInvoiceContent(String invoiceContent) {
		this.invoiceContent = invoiceContent;
	}

	public String getInvoiceTitle() {
		return invoiceTitle;
	}

	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public String getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(String taxRate) {
		this.taxRate = taxRate;
	}

	public long getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(long taxAmount) {
		this.taxAmount = taxAmount;
	}
}
