package com.ai.slp.order.api.invoiceprint.param;

import com.ai.opt.base.vo.BaseInfo;

public class InvoicePrintRequest extends BaseInfo{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 订单号
	 */
	private Long orderId;
	
	/**
	 * 发票抬头
	 */
	private String invoiceTitle;
	
	/**
	 * 发票状态
	 */
	private String invoiceStatus;
	
	/**
     * pageNo
     */
    private Integer pageNo;

    /**
     * pageSize
     */
    private Integer pageSize;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getInvoiceTitle() {
		return invoiceTitle;
	}

	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}

	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}
