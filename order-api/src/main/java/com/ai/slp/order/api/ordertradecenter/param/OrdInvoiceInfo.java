package com.ai.slp.order.api.ordertradecenter.param;

import java.io.Serializable;

public class OrdInvoiceInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /**
     * 发票类型
     */
    private String invoiceType; 
    
    /**
     * 发票种类
     */
    private String invoiceKind;
    
    /**
     * 购货方纳税人识别号
     */
    private String buyerTaxpayerNumber;
    
    /**
     * 购货方开户行代码
     */
    private String buyerBankCode;
    
    /**
     * 购货方开户行名称
     */
    private String buyerBankName;
    
    /**
     * 购货方开户行账号
     */
    private String buyerBankAccount;

    /**
     * 发票抬头
     */
    private String invoiceTitle;         

    /**
     * 登记打印内容
     */
    private String invoiceContent;
    
	public String getInvoiceKind() {
		return invoiceKind;
	}

	public void setInvoiceKind(String invoiceKind) {
		this.invoiceKind = invoiceKind;
	}

	public String getBuyerTaxpayerNumber() {
		return buyerTaxpayerNumber;
	}

	public void setBuyerTaxpayerNumber(String buyerTaxpayerNumber) {
		this.buyerTaxpayerNumber = buyerTaxpayerNumber;
	}

	public String getBuyerBankCode() {
		return buyerBankCode;
	}

	public void setBuyerBankCode(String buyerBankCode) {
		this.buyerBankCode = buyerBankCode;
	}

	public String getBuyerBankName() {
		return buyerBankName;
	}

	public void setBuyerBankName(String buyerBankName) {
		this.buyerBankName = buyerBankName;
	}

	public String getBuyerBankAccount() {
		return buyerBankAccount;
	}

	public void setBuyerBankAccount(String buyerBankAccount) {
		this.buyerBankAccount = buyerBankAccount;
	}

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
