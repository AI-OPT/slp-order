package com.ai.slp.order.dao.mapper.bo;

import java.sql.Timestamp;

public class OrdOdInvoice {
    private long orderId;

    private String tenantId;

    private String invoiceType;

    private String invoiceTitle;

    private String invoiceContent;

    private String invoiceStatus;

    private String invoiceId;

    private String invoiceNum;

    private String invoiceKind;

    private Timestamp invoiceTime;

    private String buyerTaxpayerNumber;

    private String buyerBankCode;

    private String buyerBankName;

    private String buyerBankAccount;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId == null ? null : tenantId.trim();
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType == null ? null : invoiceType.trim();
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle == null ? null : invoiceTitle.trim();
    }

    public String getInvoiceContent() {
        return invoiceContent;
    }

    public void setInvoiceContent(String invoiceContent) {
        this.invoiceContent = invoiceContent == null ? null : invoiceContent.trim();
    }

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus == null ? null : invoiceStatus.trim();
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId == null ? null : invoiceId.trim();
    }

    public String getInvoiceNum() {
        return invoiceNum;
    }

    public void setInvoiceNum(String invoiceNum) {
        this.invoiceNum = invoiceNum == null ? null : invoiceNum.trim();
    }

    public String getInvoiceKind() {
        return invoiceKind;
    }

    public void setInvoiceKind(String invoiceKind) {
        this.invoiceKind = invoiceKind == null ? null : invoiceKind.trim();
    }

    public Timestamp getInvoiceTime() {
        return invoiceTime;
    }

    public void setInvoiceTime(Timestamp invoiceTime) {
        this.invoiceTime = invoiceTime;
    }

    public String getBuyerTaxpayerNumber() {
        return buyerTaxpayerNumber;
    }

    public void setBuyerTaxpayerNumber(String buyerTaxpayerNumber) {
        this.buyerTaxpayerNumber = buyerTaxpayerNumber == null ? null : buyerTaxpayerNumber.trim();
    }

    public String getBuyerBankCode() {
        return buyerBankCode;
    }

    public void setBuyerBankCode(String buyerBankCode) {
        this.buyerBankCode = buyerBankCode == null ? null : buyerBankCode.trim();
    }

    public String getBuyerBankName() {
        return buyerBankName;
    }

    public void setBuyerBankName(String buyerBankName) {
        this.buyerBankName = buyerBankName == null ? null : buyerBankName.trim();
    }

    public String getBuyerBankAccount() {
        return buyerBankAccount;
    }

    public void setBuyerBankAccount(String buyerBankAccount) {
        this.buyerBankAccount = buyerBankAccount == null ? null : buyerBankAccount.trim();
    }
}