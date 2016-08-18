package com.ai.slp.order.dao.mapper.bo;

import java.sql.Timestamp;

public class OrdOrder {
    private long orderId;

    private String tenantId;

    private String busiCode;

    private String orderType;

    private String subFlag;

    private long parentOrderId;

    private long batchNo;

    private String userId;

    private long acctId;

    private long subsId;

    private long supplierId;

    private String storageId;

    private String routeId;

    private String provinceCode;

    private String cityCode;

    private String state;

    private Timestamp stateChgTime;

    private String displayFlag;

    private Timestamp displayFlagChgTime;

    private String deliveryFlag;

    private String lockFlag;

    private Timestamp lockTime;

    private Timestamp orderTime;

    private long sellerId;

    private String chlId;

    private String operId;

    private String workflowId;

    private String reasonType;

    private String reasonDesc;

    private Timestamp finishTime;

    private long origOrderId;

    private String orderDesc;

    private String keywords;

    private String remark;

    private String externalOrderId;

    private String externalSupplyId;

    private String downstreamOrderId;

    private String userType;

    private String ipAddress;

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

    public String getBusiCode() {
        return busiCode;
    }

    public void setBusiCode(String busiCode) {
        this.busiCode = busiCode == null ? null : busiCode.trim();
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType == null ? null : orderType.trim();
    }

    public String getSubFlag() {
        return subFlag;
    }

    public void setSubFlag(String subFlag) {
        this.subFlag = subFlag == null ? null : subFlag.trim();
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
        this.userId = userId == null ? null : userId.trim();
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

    public long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(long supplierId) {
        this.supplierId = supplierId;
    }

    public String getStorageId() {
        return storageId;
    }

    public void setStorageId(String storageId) {
        this.storageId = storageId == null ? null : storageId.trim();
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId == null ? null : routeId.trim();
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode == null ? null : provinceCode.trim();
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
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
        this.displayFlag = displayFlag == null ? null : displayFlag.trim();
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
        this.deliveryFlag = deliveryFlag == null ? null : deliveryFlag.trim();
    }

    public String getLockFlag() {
        return lockFlag;
    }

    public void setLockFlag(String lockFlag) {
        this.lockFlag = lockFlag == null ? null : lockFlag.trim();
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
        this.chlId = chlId == null ? null : chlId.trim();
    }

    public String getOperId() {
        return operId;
    }

    public void setOperId(String operId) {
        this.operId = operId == null ? null : operId.trim();
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId == null ? null : workflowId.trim();
    }

    public String getReasonType() {
        return reasonType;
    }

    public void setReasonType(String reasonType) {
        this.reasonType = reasonType == null ? null : reasonType.trim();
    }

    public String getReasonDesc() {
        return reasonDesc;
    }

    public void setReasonDesc(String reasonDesc) {
        this.reasonDesc = reasonDesc == null ? null : reasonDesc.trim();
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
        this.orderDesc = orderDesc == null ? null : orderDesc.trim();
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords == null ? null : keywords.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getExternalOrderId() {
        return externalOrderId;
    }

    public void setExternalOrderId(String externalOrderId) {
        this.externalOrderId = externalOrderId == null ? null : externalOrderId.trim();
    }

    public String getExternalSupplyId() {
        return externalSupplyId;
    }

    public void setExternalSupplyId(String externalSupplyId) {
        this.externalSupplyId = externalSupplyId == null ? null : externalSupplyId.trim();
    }

    public String getDownstreamOrderId() {
        return downstreamOrderId;
    }

    public void setDownstreamOrderId(String downstreamOrderId) {
        this.downstreamOrderId = downstreamOrderId == null ? null : downstreamOrderId.trim();
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType == null ? null : userType.trim();
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress == null ? null : ipAddress.trim();
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (acctId ^ (acctId >>> 32));
		result = prime * result + (int) (batchNo ^ (batchNo >>> 32));
		result = prime * result + ((busiCode == null) ? 0 : busiCode.hashCode());
		result = prime * result + ((chlId == null) ? 0 : chlId.hashCode());
		result = prime * result + ((cityCode == null) ? 0 : cityCode.hashCode());
		result = prime * result + ((deliveryFlag == null) ? 0 : deliveryFlag.hashCode());
		result = prime * result + ((displayFlag == null) ? 0 : displayFlag.hashCode());
		result = prime * result + ((displayFlagChgTime == null) ? 0 : displayFlagChgTime.hashCode());
		result = prime * result + ((downstreamOrderId == null) ? 0 : downstreamOrderId.hashCode());
		result = prime * result + ((externalOrderId == null) ? 0 : externalOrderId.hashCode());
		result = prime * result + ((externalSupplyId == null) ? 0 : externalSupplyId.hashCode());
		result = prime * result + ((finishTime == null) ? 0 : finishTime.hashCode());
		result = prime * result + ((ipAddress == null) ? 0 : ipAddress.hashCode());
		result = prime * result + ((keywords == null) ? 0 : keywords.hashCode());
		result = prime * result + ((lockFlag == null) ? 0 : lockFlag.hashCode());
		result = prime * result + ((lockTime == null) ? 0 : lockTime.hashCode());
		result = prime * result + ((operId == null) ? 0 : operId.hashCode());
		result = prime * result + ((orderDesc == null) ? 0 : orderDesc.hashCode());
		result = prime * result + (int) (orderId ^ (orderId >>> 32));
		result = prime * result + ((orderTime == null) ? 0 : orderTime.hashCode());
		result = prime * result + ((orderType == null) ? 0 : orderType.hashCode());
		result = prime * result + (int) (origOrderId ^ (origOrderId >>> 32));
		result = prime * result + (int) (parentOrderId ^ (parentOrderId >>> 32));
		result = prime * result + ((provinceCode == null) ? 0 : provinceCode.hashCode());
		result = prime * result + ((reasonDesc == null) ? 0 : reasonDesc.hashCode());
		result = prime * result + ((reasonType == null) ? 0 : reasonType.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((routeId == null) ? 0 : routeId.hashCode());
		result = prime * result + (int) (sellerId ^ (sellerId >>> 32));
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((stateChgTime == null) ? 0 : stateChgTime.hashCode());
		result = prime * result + ((storageId == null) ? 0 : storageId.hashCode());
		result = prime * result + ((subFlag == null) ? 0 : subFlag.hashCode());
		result = prime * result + (int) (subsId ^ (subsId >>> 32));
		result = prime * result + (int) (supplierId ^ (supplierId >>> 32));
		result = prime * result + ((tenantId == null) ? 0 : tenantId.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		result = prime * result + ((userType == null) ? 0 : userType.hashCode());
		result = prime * result + ((workflowId == null) ? 0 : workflowId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrdOrder other = (OrdOrder) obj;
		if (acctId != other.acctId)
			return false;
		if (batchNo != other.batchNo)
			return false;
		if (busiCode == null) {
			if (other.busiCode != null)
				return false;
		} else if (!busiCode.equals(other.busiCode))
			return false;
		if (chlId == null) {
			if (other.chlId != null)
				return false;
		} else if (!chlId.equals(other.chlId))
			return false;
		if (cityCode == null) {
			if (other.cityCode != null)
				return false;
		} else if (!cityCode.equals(other.cityCode))
			return false;
		if (deliveryFlag == null) {
			if (other.deliveryFlag != null)
				return false;
		} else if (!deliveryFlag.equals(other.deliveryFlag))
			return false;
		if (displayFlag == null) {
			if (other.displayFlag != null)
				return false;
		} else if (!displayFlag.equals(other.displayFlag))
			return false;
		if (displayFlagChgTime == null) {
			if (other.displayFlagChgTime != null)
				return false;
		} else if (!displayFlagChgTime.equals(other.displayFlagChgTime))
			return false;
		if (downstreamOrderId == null) {
			if (other.downstreamOrderId != null)
				return false;
		} else if (!downstreamOrderId.equals(other.downstreamOrderId))
			return false;
		if (externalOrderId == null) {
			if (other.externalOrderId != null)
				return false;
		} else if (!externalOrderId.equals(other.externalOrderId))
			return false;
		if (externalSupplyId == null) {
			if (other.externalSupplyId != null)
				return false;
		} else if (!externalSupplyId.equals(other.externalSupplyId))
			return false;
		if (finishTime == null) {
			if (other.finishTime != null)
				return false;
		} else if (!finishTime.equals(other.finishTime))
			return false;
		if (ipAddress == null) {
			if (other.ipAddress != null)
				return false;
		} else if (!ipAddress.equals(other.ipAddress))
			return false;
		if (keywords == null) {
			if (other.keywords != null)
				return false;
		} else if (!keywords.equals(other.keywords))
			return false;
		if (lockFlag == null) {
			if (other.lockFlag != null)
				return false;
		} else if (!lockFlag.equals(other.lockFlag))
			return false;
		if (lockTime == null) {
			if (other.lockTime != null)
				return false;
		} else if (!lockTime.equals(other.lockTime))
			return false;
		if (operId == null) {
			if (other.operId != null)
				return false;
		} else if (!operId.equals(other.operId))
			return false;
		if (orderDesc == null) {
			if (other.orderDesc != null)
				return false;
		} else if (!orderDesc.equals(other.orderDesc))
			return false;
		if (orderId != other.orderId)
			return false;
		if (orderTime == null) {
			if (other.orderTime != null)
				return false;
		} else if (!orderTime.equals(other.orderTime))
			return false;
		if (orderType == null) {
			if (other.orderType != null)
				return false;
		} else if (!orderType.equals(other.orderType))
			return false;
		if (origOrderId != other.origOrderId)
			return false;
		if (parentOrderId != other.parentOrderId)
			return false;
		if (provinceCode == null) {
			if (other.provinceCode != null)
				return false;
		} else if (!provinceCode.equals(other.provinceCode))
			return false;
		if (reasonDesc == null) {
			if (other.reasonDesc != null)
				return false;
		} else if (!reasonDesc.equals(other.reasonDesc))
			return false;
		if (reasonType == null) {
			if (other.reasonType != null)
				return false;
		} else if (!reasonType.equals(other.reasonType))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (routeId == null) {
			if (other.routeId != null)
				return false;
		} else if (!routeId.equals(other.routeId))
			return false;
		if (sellerId != other.sellerId)
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (stateChgTime == null) {
			if (other.stateChgTime != null)
				return false;
		} else if (!stateChgTime.equals(other.stateChgTime))
			return false;
		if (storageId == null) {
			if (other.storageId != null)
				return false;
		} else if (!storageId.equals(other.storageId))
			return false;
		if (subFlag == null) {
			if (other.subFlag != null)
				return false;
		} else if (!subFlag.equals(other.subFlag))
			return false;
		if (subsId != other.subsId)
			return false;
		if (supplierId != other.supplierId)
			return false;
		if (tenantId == null) {
			if (other.tenantId != null)
				return false;
		} else if (!tenantId.equals(other.tenantId))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		if (userType == null) {
			if (other.userType != null)
				return false;
		} else if (!userType.equals(other.userType))
			return false;
		if (workflowId == null) {
			if (other.workflowId != null)
				return false;
		} else if (!workflowId.equals(other.workflowId))
			return false;
		return true;
	}
    
    
}