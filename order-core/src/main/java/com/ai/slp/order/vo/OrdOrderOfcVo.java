package com.ai.slp.order.vo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 订单主表 Date: 2016年11月12日 <br>
 * Copyright (c) 2016 asiainfo.com <br>
 * 
 * @author zhangqiang7
 */
public class OrdOrderOfcVo implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 订单id
	 */
	private long orderId;

	/**
	 * 租户id
	 */
	private String tenantId;

	/**
	 * 业务类型
	 */
	private String busiCode;

	/**
	 * 订单类型
	 */
	private String orderType;

	/**
	 * 子订单标识
	 */
	private String subFlag;

	/**
	 * 父订单ID
	 */
	private long parentOrderId;

	/**
	 * 批次号
	 */
	private long batchNo;

	/**
	 * 用户ID
	 */
	private String userId;

	/**
	 * 账户ID
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

	/**
	 * 库存ID
	 */
	private String storageId;

	/**
	 * 路由ID
	 */
	private String routeId;

	/**
	 * 省分
	 */
	private String provinceCode;

	/**
	 * 地市
	 */
	private String cityCode;

	/**
	 * 订单状态(后厂)
	 */
	private String state;

	/**
	 * 状态变化时间
	 */
	private Timestamp stateChgTime;

	/**
	 * 客户端显示状态
	 */
	private String displayFlag;

	/**
	 * 客户端显示状态变更时间
	 */
	private Timestamp displayFlagChgTime;

	/**
	 * 是否需要物流
	 */
	private String deliveryFlag;

	/**
	 * 业务锁标识
	 */
	private String lockFlag;

	/**
	 * 锁定时间
	 */
	private Timestamp lockTime;

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
	 * 受理工号
	 */
	private String operId;

	/**
	 * 流程实例ID
	 */
	private String workflowId;

	/**
	 * 原因类型(撤改单)
	 */
	private String reasonType;

	/**
	 * 原因描述(撤改单)
	 */
	private String reasonDesc;

	/**
	 * 完成时间
	 */
	private Timestamp finishTime;

	/**
	 * 原始订单号
	 */
	private long origOrderId;

	/**
	 * 订单简要信息
	 */
	private String orderDesc;

	/**
	 * 关键字
	 */
	private String keywords;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 外部订单id
	 */
	private String externalOrderId;

	/**
	 * 外部供应商id
	 */
	private String externalSupplyId;

	/**
	 * 下游订单ID
	 */
	private String downstreamOrderId;

	/**
	 * 用户类型
	 */
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

	/**
	 * 售后订单标识
	 */
	private String cusServiceFlag;

	/**
	 * 积分账户ID
	 */
	private String accountId;

	/**
	 * 业务标识
	 */
	private String flag;

	/**
	 * token id
	 */
	private String tokenId;

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
		this.tenantId = tenantId;
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

	public String getSubFlag() {
		return subFlag;
	}

	public void setSubFlag(String subFlag) {
		this.subFlag = subFlag;
	}

	public long getParentOrderId() {
		return parentOrderId;
	}

	public void setParentOrderId(long parentOrderId) {
		this.parentOrderId = parentOrderId;
	}

	public long getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(long batchNo) {
		this.batchNo = batchNo;
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

	public String getStorageId() {
		return storageId;
	}

	public void setStorageId(String storageId) {
		this.storageId = storageId;
	}

	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
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

	public Timestamp getStateChgTime() {
		return stateChgTime;
	}

	public void setStateChgTime(Timestamp stateChgTime) {
		this.stateChgTime = stateChgTime;
	}

	public String getDisplayFlag() {
		return displayFlag;
	}

	public void setDisplayFlag(String displayFlag) {
		this.displayFlag = displayFlag;
	}

	public Timestamp getDisplayFlagChgTime() {
		return displayFlagChgTime;
	}

	public void setDisplayFlagChgTime(Timestamp displayFlagChgTime) {
		this.displayFlagChgTime = displayFlagChgTime;
	}

	public String getDeliveryFlag() {
		return deliveryFlag;
	}

	public void setDeliveryFlag(String deliveryFlag) {
		this.deliveryFlag = deliveryFlag;
	}

	public String getLockFlag() {
		return lockFlag;
	}

	public void setLockFlag(String lockFlag) {
		this.lockFlag = lockFlag;
	}

	public Timestamp getLockTime() {
		return lockTime;
	}

	public void setLockTime(Timestamp lockTime) {
		this.lockTime = lockTime;
	}

	public Timestamp getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Timestamp orderTime) {
		this.orderTime = orderTime;
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

	public String getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(String workflowId) {
		this.workflowId = workflowId;
	}

	public String getReasonType() {
		return reasonType;
	}

	public void setReasonType(String reasonType) {
		this.reasonType = reasonType;
	}

	public String getReasonDesc() {
		return reasonDesc;
	}

	public void setReasonDesc(String reasonDesc) {
		this.reasonDesc = reasonDesc;
	}

	public Timestamp getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Timestamp finishTime) {
		this.finishTime = finishTime;
	}

	public long getOrigOrderId() {
		return origOrderId;
	}

	public void setOrigOrderId(long origOrderId) {
		this.origOrderId = origOrderId;
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

	public String getExternalOrderId() {
		return externalOrderId;
	}

	public void setExternalOrderId(String externalOrderId) {
		this.externalOrderId = externalOrderId;
	}

	public String getExternalSupplyId() {
		return externalSupplyId;
	}

	public void setExternalSupplyId(String externalSupplyId) {
		this.externalSupplyId = externalSupplyId;
	}

	public String getDownstreamOrderId() {
		return downstreamOrderId;
	}

	public void setDownstreamOrderId(String downstreamOrderId) {
		this.downstreamOrderId = downstreamOrderId;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
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

	public String getCusServiceFlag() {
		return cusServiceFlag;
	}

	public void setCusServiceFlag(String cusServiceFlag) {
		this.cusServiceFlag = cusServiceFlag;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

}
