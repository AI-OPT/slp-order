package com.ai.slp.order.api.orderlist.param;

import java.sql.Timestamp;
import java.util.List;

import com.ai.opt.base.vo.BaseInfo;

/**
 * 订单表 Date: 2016年5月3日 <br>
 * Copyright (c) 2016 asiainfo.com <br>
 * 
 * @author zhangqiang7
 */
public class OrdOrderVo extends BaseInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 订单来源 受理渠道
	 */
	private String chlId;

	/**
	 * 仓库ID
	 */
	private String routeId;

	/**
	 * 仓库信息
	 */
	private String routeName;

	/**
	 * 父订单id
	 */
	private Long parentOrderId;

	/**
	 * 支付流水号
	 */
	private Long balacneIfId;
	/**
	 * 外部流水号
	 */
	private String externalId;

	/**
	 * 买家帐号 userid
	 */
	private String userId;

	/**
	 * 用户姓名
	 */
	private String userName;

	/**
	 * 支付帐号
	 */
	private Long acctId;

	/**
	 * 买家留言 订单备注
	 */
	private String remark;

	/**
	 * 业务订单ID
	 */
	private Long orderId;

	/**
	 * 原始订单号
	 */
	private Long origOrderId;

	/**
	 * 积分账户id
	 */
	private String accountId;

	/**
	 * 积分中心返回的id
	 */
	private String downstreamOrderId;

	/**
	 * 业务类型
	 */
	private String busiCode;

	/**
	 * 业务类型展示名称
	 */
	private String busiCodeName;

	/**
	 * 订单类型
	 */
	private String orderType;

	/**
	 * 订单类型展示名称
	 */
	private String orderTypeName;

	/**
	 * 订单状态(后厂)
	 */
	private String state;

	/**
	 * 订单状态展示
	 */
	private String stateName;

	/**
	 * 支付方式
	 */
	private String payStyle;

	/**
	 * 支付方式显示值
	 */
	private String payStyleName;

	/**
	 * 支付时间
	 */
	private Timestamp payTime;

	/**
	 * 下单时间
	 */
	private Timestamp orderTime;

	/**
	 * 手机个数
	 */
	private Integer phoneCount;

	/**
	 * 总费用
	 */
	private Long totalFee;

	/**
	 * 总优惠金额
	 */
	private Long discountFee;

	/**
	 * 减免金额
	 */
	private long operDiscountFee;

	/**
	 * 减免原因
	 */
	private String operDiscountDesc;

	/**
	 * 总应收费用
	 */
	private Long adjustFee;

	/**
	 * 总实收费用
	 */
	private Long paidFee;

	/**
	 * 总待收费用
	 */
	private Long payFee;

	/**
	 * 运费
	 */
	private Long freight;

	/**
	 * 发票类型
	 */
	private String invoiceType;

	/**
	 * 发票类型展示名称
	 */
	private String InvoiceTypeName;

	/**
	 * 发票抬头
	 */
	private String invoiceTitle;

	/**
	 * 登记打印内容
	 */
	private String invoiceContent;

	/**
	 * 发票打印状态
	 */
	private String invoiceStatus;

	/**
	 * 物流单号
	 */
	private String expressOddNumber;

	/**
	 * 到件方公司
	 */
	private String contactCompany;

	/**
	 * 收件人姓名
	 */
	private String contactName;
	
	/**
	 * 售后页面客户手机
	 */
	private String aftercontactTel;
	
	/**
	 * 售后页面收货信息
	 */
	private String aftercontactInfo;

	/**
	 * 收件人电话
	 */
	private String contactTel;

	/**
	 * 配送方式
	 */
	private String logisticsType;

	/**
	 * 收件人省份
	 */
	private String provinceCode;

	/**
	 * 收件人地市
	 */
	private String cityCode;

	/**
	 * 收件人区县
	 */
	private String countyCode;

	/**
	 * 收件人邮编
	 */
	private String postCode;

	/**
	 * 收件人末级区域
	 */
	private String areaCode;

	/**
	 * 详细地址(自提地址)
	 */
	private String address;

	/**
	 * 物流公司ID
	 */
	private String expressId;

	/**
	 * 受理工号
	 */
	private String operId;

	/**
	 * 纳税人识别号
	 */
	private String buyerTaxpayerNumber;

	/**
	 * 购货方开户行名称
	 */
	private String buyerBankName;

	/**
	 * 购货方开户行帐号
	 */
	private String buyerBankAccount;

	/**
	 * 支付信息(订单费用总表信息)
	 */
	private List<OrderPayVo> payDataList;

	/**
	 * 商品集合
	 */
	private List<OrdProductVo> productList;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getBusiCode() {
		return busiCode;
	}

	public void setBusiCode(String busiCode) {
		this.busiCode = busiCode;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Timestamp getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Timestamp orderTime) {
		this.orderTime = orderTime;
	}

	public Long getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Long totalFee) {
		this.totalFee = totalFee;
	}

	public Long getDiscountFee() {
		return discountFee;
	}

	public void setDiscountFee(Long discountFee) {
		this.discountFee = discountFee;
	}

	public Long getAdjustFee() {
		return adjustFee;
	}

	public void setAdjustFee(Long adjustFee) {
		this.adjustFee = adjustFee;
	}

	public List<OrdProductVo> getProductList() {
		return productList;
	}

	public void setProductList(List<OrdProductVo> productList) {
		this.productList = productList;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getPayStyle() {
		return payStyle;
	}

	public void setPayStyle(String payStyle) {
		this.payStyle = payStyle;
	}

	public String getPayStyleName() {
		return payStyleName;
	}

	public void setPayStyleName(String payStyleName) {
		this.payStyleName = payStyleName;
	}

	public Timestamp getPayTime() {
		return payTime;
	}

	public void setPayTime(Timestamp payTime) {
		this.payTime = payTime;
	}

	public Long getPaidFee() {
		return paidFee;
	}

	public void setPaidFee(Long paidFee) {
		this.paidFee = paidFee;
	}

	public Long getPayFee() {
		return payFee;
	}

	public void setPayFee(Long payFee) {
		this.payFee = payFee;
	}

	public List<OrderPayVo> getPayDataList() {
		return payDataList;
	}

	public void setPayDataList(List<OrderPayVo> payDataList) {
		this.payDataList = payDataList;
	}

	public Integer getPhoneCount() {
		return phoneCount;
	}

	public void setPhoneCount(Integer phoneCount) {
		this.phoneCount = phoneCount;
	}

	public long getOperDiscountFee() {
		return operDiscountFee;
	}

	public void setOperDiscountFee(long operDiscountFee) {
		this.operDiscountFee = operDiscountFee;
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

	public String getExpressOddNumber() {
		return expressOddNumber;
	}

	public void setExpressOddNumber(String expressOddNumber) {
		this.expressOddNumber = expressOddNumber;
	}

	public String getContactCompany() {
		return contactCompany;
	}

	public void setContactCompany(String contactCompany) {
		this.contactCompany = contactCompany;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactTel() {
		return contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCountyCode() {
		return countyCode;
	}

	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getExpressId() {
		return expressId;
	}

	public void setExpressId(String expressId) {
		this.expressId = expressId;
	}

	public String getChlId() {
		return chlId;
	}

	public void setChlId(String chlId) {
		this.chlId = chlId;
	}

	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}

	public Long getParentOrderId() {
		return parentOrderId;
	}

	public void setParentOrderId(Long parentOrderId) {
		this.parentOrderId = parentOrderId;
	}

	public Long getBalacneIfId() {
		return balacneIfId;
	}

	public void setBalacneIfId(Long balacneIfId) {
		this.balacneIfId = balacneIfId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	public Long getOrigOrderId() {
		return origOrderId;
	}

	public void setOrigOrderId(Long origOrderId) {
		this.origOrderId = origOrderId;
	}

	public Long getAcctId() {
		return acctId;
	}

	public void setAcctId(Long acctId) {
		this.acctId = acctId;
	}

	public String getBusiCodeName() {
		return busiCodeName;
	}

	public void setBusiCodeName(String busiCodeName) {
		this.busiCodeName = busiCodeName;
	}

	public String getInvoiceTypeName() {
		return InvoiceTypeName;
	}

	public void setInvoiceTypeName(String invoiceTypeName) {
		InvoiceTypeName = invoiceTypeName;
	}

	public String getOrderTypeName() {
		return orderTypeName;
	}

	public void setOrderTypeName(String orderTypeName) {
		this.orderTypeName = orderTypeName;
	}

	public Long getFreight() {
		return freight;
	}

	public void setFreight(Long freight) {
		this.freight = freight;
	}

	public String getLogisticsType() {
		return logisticsType;
	}

	public void setLogisticsType(String logisticsType) {
		this.logisticsType = logisticsType;
	}

	public String getOperId() {
		return operId;
	}

	public void setOperId(String operId) {
		this.operId = operId;
	}

	public String getOperDiscountDesc() {
		return operDiscountDesc;
	}

	public void setOperDiscountDesc(String operDiscountDesc) {
		this.operDiscountDesc = operDiscountDesc;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getDownstreamOrderId() {
		return downstreamOrderId;
	}

	public void setDownstreamOrderId(String downstreamOrderId) {
		this.downstreamOrderId = downstreamOrderId;
	}

	public String getBuyerTaxpayerNumber() {
		return buyerTaxpayerNumber;
	}

	public void setBuyerTaxpayerNumber(String buyerTaxpayerNumber) {
		this.buyerTaxpayerNumber = buyerTaxpayerNumber;
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

	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getAftercontactTel() {
		return aftercontactTel;
	}

	public void setAftercontactTel(String aftercontactTel) {
		this.aftercontactTel = aftercontactTel;
	}

	public String getAftercontactInfo() {
		return aftercontactInfo;
	}

	public void setAftercontactInfo(String aftercontactInfo) {
		this.aftercontactInfo = aftercontactInfo;
	}
}
