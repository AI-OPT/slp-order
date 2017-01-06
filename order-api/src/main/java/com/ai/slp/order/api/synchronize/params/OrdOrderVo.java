package com.ai.slp.order.api.synchronize.params;

import java.io.Serializable;
import java.sql.Timestamp;

public class OrdOrderVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 订单id
	 */
	private long orderId;

	/**
	 * 租户id
	 */
	// private String tenantId;

	/**
	 * 业务类型
	 */
	private String busiCode;

	/**
	 * 订单类型
	 */
	private String orderType;

	// private String subFlag;

	// private long parentOrderId;

	// private long batchNo;

	/**
	 * 用户ID
	 */
	private String userId;

	/**
	 * 帐号ID
	 */
	private long acctId;

	/**
	 * 订购ID
	 */
	private long subsId;

	/**
	 * 供应商ID
	 */
	private String supplierId;

	// private String storageId;

	// private String routeId;

	/**
	 * 省分
	 */
	private String provinceCode;

	/**
	 * 地市
	 */
	private String cityCode;

	/**
	 * 订单状态
	 */
	private String state;

	// private Timestamp stateChgTime;

	// private String displayFlag;

	// private Timestamp displayFlagChgTime;

	/**
	 * 是否需要物流
	 */
	private String deliveryFlag;

	// private String lockFlag;

	// private Timestamp lockTime;
	/**
	 * 下单时间
	 */
	private Timestamp orderTime;

	/**
	 * 销售商ID
	 */
	private long sellerId;

	/**
	 * 订单来源
	 */
	private String chlId;

	/**
	 * 操作员ID
	 */
	private String operId;

	// private String workflowId;

	// private String reasonType;

	// private String reasonDesc;

	// private Timestamp finishTime;

	// private long origOrderId;

	/**
	 * 订单简要信息
	 */
	private String orderDesc;

	/**
	 * 订单关键词
	 */
	private String keywords;

	/**
	 * 订单备注
	 */
	private String remark;

	// private String externalOrderId;

	// private String externalSupplyId;

	// private String downstreamOrderId;

	private String userType;

	/**
	 * IP地址
	 */
	private String ipAddress;

	/**
	 * 是否预警
	 */
	private String ifWarning;

	/**
	 * 预警类型
	 */
	private String warningType;

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public long getAcctId() {
		return acctId;
	}

	public void setAcctId(long acctId) {
		this.acctId = acctId;
	}

	public long getSubsId() {
		return subsId;
	}

	public void setSubsId(long subsId) {
		this.subsId = subsId;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDeliveryFlag() {
		return deliveryFlag;
	}

	public void setDeliveryFlag(String deliveryFlag) {
		this.deliveryFlag = deliveryFlag;
	}

	public long getSellerId() {
		return sellerId;
	}

	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}

	public String getChlId() {
		return chlId;
	}

	public void setChlId(String chlId) {
		this.chlId = chlId;
	}

	public String getOperId() {
		return operId;
	}

	public void setOperId(String operId) {
		this.operId = operId;
	}

	public String getOrderDesc() {
		return orderDesc;
	}

	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getIfWarning() {
		return ifWarning;
	}

	public void setIfWarning(String ifWarning) {
		this.ifWarning = ifWarning;
	}

	public String getWarningType() {
		return warningType;
	}

	public void setWarningType(String warningType) {
		this.warningType = warningType;
	}

	public Timestamp getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Timestamp orderTime) {
		this.orderTime = orderTime;
	}

	// private String cusServiceFlag;

	// private String accountId;

	private String flag;

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	// private String tokenId;

	// private String userName;

	// private String userTel;

	// private String pointRate;

}
