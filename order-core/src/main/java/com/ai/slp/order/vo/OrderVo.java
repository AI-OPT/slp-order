package com.ai.slp.order.vo;

import java.io.Serializable;

public class OrderVo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 订单号
	 */
	private String orderNo;
	
	/**
	 * 店铺名称
	 */
	private String shopName;
	
	/**
	 * 买家姓名
	 */
	private String receiverContact;
	
	/**
	 * 买家电话
	 */
	private String receiverPhone;
	
	/**
	 * 省
	 */
	private String province;
	
	/**
	 * 市
	 */
	private String city;
	
	/**
	 * 区县
	 */
	private String region;
	
	/**
	 * 详细地址
	 */
	private String receiverAddress;
	
	/**
	 * 邮编
	 */
	private String postCode;
	
	/**
	 * 支付时间
	 */
	private String payTime;
	
	/**
	 * 支付帐号
	 */
	private String payNo;
	/**
	 * 支付方式
	 */
	private long payType;
	/**
	 * 订单金额
	 */
	private long orderAmout;
	
	/**
	 * 支付金额
	 */
	private long payAmount;
	/**
	 * 优惠金额
	 */
	private long coupAmount;
	
	/**
	 * 应收金额
	 */
	private long receiveAmount;
	
	/**
	 * 是否开票
	 */
	private boolean needInvoice; 
	
	/**
	 * 发票抬头
	 */
	private String invoiceTitle;
	/**
	 * 发票类型
	 */
	private long invoiceType;
	
	/**
	 * 单位名称
	 */
	private String companyName;
	/**
	 * 纳税人识别号
	 */
	private String taxNo;
	/**
	 * 注册地址
	 */
	private String registerAddress;
	/**
	 * 注册电话
	 */
	private String registerTel;
	
	/**
	 * 开户银行
	 */
	private String bank;
	/**
	 * 银行账户
	 */
	private String bankNo;
	/**
	 * 买家备注
	 */
	private String buyerRemark;
	/**
	 * 商家备注
	 */
	private String sellerRemark;
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getReceiverContact() {
		return receiverContact;
	}
	public void setReceiverContact(String receiverContact) {
		this.receiverContact = receiverContact;
	}
	public String getReceiverPhone() {
		return receiverPhone;
	}
	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getReceiverAddress() {
		return receiverAddress;
	}
	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public String getPayTime() {
		return payTime;
	}
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	public String getPayNo() {
		return payNo;
	}
	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}
	public long getPayType() {
		return payType;
	}
	public void setPayType(long payType) {
		this.payType = payType;
	}
	public long getOrderAmout() {
		return orderAmout;
	}
	public void setOrderAmout(long orderAmout) {
		this.orderAmout = orderAmout;
	}
	public long getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(long payAmount) {
		this.payAmount = payAmount;
	}
	public long getCoupAmount() {
		return coupAmount;
	}
	public void setCoupAmount(long coupAmount) {
		this.coupAmount = coupAmount;
	}
	public long getReceiveAmount() {
		return receiveAmount;
	}
	public void setReceiveAmount(long receiveAmount) {
		this.receiveAmount = receiveAmount;
	}
	public boolean isNeedInvoice() {
		return needInvoice;
	}
	public void setNeedInvoice(boolean needInvoice) {
		this.needInvoice = needInvoice;
	}
	public String getInvoiceTitle() {
		return invoiceTitle;
	}
	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}
	public long getInvoiceType() {
		return invoiceType;
	}
	public void setInvoiceType(long invoiceType) {
		this.invoiceType = invoiceType;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getTaxNo() {
		return taxNo;
	}
	public void setTaxNo(String taxNo) {
		this.taxNo = taxNo;
	}
	public String getRegisterAddress() {
		return registerAddress;
	}
	public void setRegisterAddress(String registerAddress) {
		this.registerAddress = registerAddress;
	}
	public String getRegisterTel() {
		return registerTel;
	}
	public void setRegisterTel(String registerTel) {
		this.registerTel = registerTel;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getBankNo() {
		return bankNo;
	}
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	public String getBuyerRemark() {
		return buyerRemark;
	}
	public void setBuyerRemark(String buyerRemark) {
		this.buyerRemark = buyerRemark;
	}
	public String getSellerRemark() {
		return sellerRemark;
	}
	public void setSellerRemark(String sellerRemark) {
		this.sellerRemark = sellerRemark;
	}
}
