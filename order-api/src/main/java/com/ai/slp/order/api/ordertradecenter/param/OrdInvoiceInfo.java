package com.ai.slp.order.api.ordertradecenter.param;

import java.io.Serializable;

public class OrdInvoiceInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /**
     * 发票类型
     */
    private String invoiceType;         

    /**
     * 发票抬头
     */
    private String invoiceTitle;         

    /**
     * 登记打印内容
     */
    private String invoiceContent;

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getInvoiceTitle() {
		return invoiceTitle;
	}

	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}

	public String getInvoiceContent() {
		return invoiceContent;
	}

	public void setInvoiceContent(String invoiceContent) {
		this.invoiceContent = invoiceContent;
	}        

}
