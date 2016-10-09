package com.ai.slp.order.api.invoiceprint.param;

import java.io.Serializable;

public class InvoiceSumbitVo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 公司代码
	 */
	private String corporationCode;
	
	/**
	 * 发票类型
	 */
	private String invoiceClass;
	
	/**
	 * 发票种类
	 */
	private String invoiceKind;
	
	/**
	 * 购货方纳税人识别号
	 */
	private String buyerTaxpayerNumber;
	
	/**
	 * 购货方代码
	 */
	private String buyerCode;
	
	/**
	 * 购货方名称
	 */
	private String buyerName;
	
	/**
	 * 购货方地址
	 */
	private String buyerAddress;
	
	/**
	 * 购货方固定电话
	 */
	private String buyerTelephone;
	
	/**
	 * 购货方手机
	 */
	private String buyerMobile;
	
	/**
	 * 购货方邮箱
	 */
	private String buyerEmail;
	
	/**
	 * 购货方类型
	 */
	private String buyerCompanyClass;
	
	/**
	 * 开户行代码
	 */
	private String buyerBankCode;
	
	/**
	 * 开户行名称
	 */
	private String buyerBankName;
	/**
	 * 开户行账号
	 */
	private String buyerBankAccount;
	/**
	 * 销售订单号
	 */
	private String salesOrderNo;
	/**
	 * 订单创建日期
	 */
    private String orderCreateTime;
    /**
     * 项目号
     */
    private String orderItem;
    /**
     * 商品编码
     */
    private String materialCode;
    /**
     * 规格型号
     */
    private String specification;
    private String materialName;//商品名称
    /**
     * 商品单价
     */
    private String price;
    /**
     * 商品数量
     */
    private String quantity;
    /**
     * 商品单位
     */
    private String unit;
    /**
     * 商品折扣金额
     */
    private String discountAmount;
    /**
     * 税率
     */
    private String rate;
    /**
     * 税金
     */
    private String tax;
    /**
     * 不含税金额
     */
    private String amount;
    /**
     * 含税金额
     */
    private String taxAmount;
    /**
     * 备注
     */
    private String remark;
	public String getCorporationCode() {
		return corporationCode;
	}
	public void setCorporationCode(String corporationCode) {
		this.corporationCode = corporationCode;
	}
	public String getInvoiceClass() {
		return invoiceClass;
	}
	public void setInvoiceClass(String invoiceClass) {
		this.invoiceClass = invoiceClass;
	}
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
	public String getBuyerCode() {
		return buyerCode;
	}
	public void setBuyerCode(String buyerCode) {
		this.buyerCode = buyerCode;
	}
	public String getBuyerName() {
		return buyerName;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	public String getBuyerAddress() {
		return buyerAddress;
	}
	public void setBuyerAddress(String buyerAddress) {
		this.buyerAddress = buyerAddress;
	}
	public String getBuyerTelephone() {
		return buyerTelephone;
	}
	public void setBuyerTelephone(String buyerTelephone) {
		this.buyerTelephone = buyerTelephone;
	}
	public String getBuyerMobile() {
		return buyerMobile;
	}
	public void setBuyerMobile(String buyerMobile) {
		this.buyerMobile = buyerMobile;
	}
	public String getBuyerEmail() {
		return buyerEmail;
	}
	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}
	public String getBuyerCompanyClass() {
		return buyerCompanyClass;
	}
	public void setBuyerCompanyClass(String buyerCompanyClass) {
		this.buyerCompanyClass = buyerCompanyClass;
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
	public String getSalesOrderNo() {
		return salesOrderNo;
	}
	public void setSalesOrderNo(String salesOrderNo) {
		this.salesOrderNo = salesOrderNo;
	}
	public String getOrderCreateTime() {
		return orderCreateTime;
	}
	public void setOrderCreateTime(String orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}
	public String getOrderItem() {
		return orderItem;
	}
	public void setOrderItem(String orderItem) {
		this.orderItem = orderItem;
	}
	public String getMaterialCode() {
		return materialCode;
	}
	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
	public String getSpecification() {
		return specification;
	}
	public void setSpecification(String specification) {
		this.specification = specification;
	}
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(String discountAmount) {
		this.discountAmount = discountAmount;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getTax() {
		return tax;
	}
	public void setTax(String tax) {
		this.tax = tax;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getTaxAmount() {
		return taxAmount;
	}
	public void setTaxAmount(String taxAmount) {
		this.taxAmount = taxAmount;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
