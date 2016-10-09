package com.ai.slp.order.api.invoiceprint.param;

import java.io.Serializable;

public class InvoiceNoticeRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 公司代码(销售方的公司代码)
	 */
	private Long companyId;
	
	/**
	 * 凭证号(子订单号)
	 */
	private Long orderId;
	
	/**
	 * 凭证行项目号
	 */
	private String proofItemNum;
	
	/**
	 * 发票代码
	 */
	private String invoiceId;
	
	/**
	 * 发票号码
	 */
	private String invoiceNum;
	
	/**
	 * 打印日期
	 */
	private String invoiceTime;
	
	/**
	 * 发票总额
	 */
	private long invoiceTotalFee;
	
	/**
	 * 发票状态
	 */
	private String invoiceStatus;

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getProofItemNum() {
		return proofItemNum;
	}

	public void setProofItemNum(String proofItemNum) {
		this.proofItemNum = proofItemNum;
	}

	public String getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getInvoiceNum() {
		return invoiceNum;
	}

	public void setInvoiceNum(String invoiceNum) {
		this.invoiceNum = invoiceNum;
	}

	public String getInvoiceTime() {
		return invoiceTime;
	}

	public void setInvoiceTime(String invoiceTime) {
		this.invoiceTime = invoiceTime;
	}

	public long getInvoiceTotalFee() {
		return invoiceTotalFee;
	}

	public void setInvoiceTotalFee(long invoiceTotalFee) {
		this.invoiceTotalFee = invoiceTotalFee;
	}

	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}
}
