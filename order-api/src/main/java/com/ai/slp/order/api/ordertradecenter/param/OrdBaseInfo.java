package com.ai.slp.order.api.ordertradecenter.param;

import java.io.Serializable;


public class OrdBaseInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户Id
     */
    private String userId;
    
    /**
     * 用户ip地址
     */
    private String userIp;
    
    /**
     * 用户类型
     */
    private String userType;
    
    /**
     * 订单类型
     */
    private String orderType;

    /**
     * 渠道Id
     */
    private String chlId;
    
    /**
     * 账户id
     */
    private long acctId;

    /**
     * 操作员Id
     */
    private String operId;
    
    /**
     * 是否需要物流
     */
    private String deliveryFlag;

    /**
     * 省份
     */
    private String provinceCode;

    /**
     * 地市
     */
    private String cityCode;
    
    /**
     * ip地址
     */
    private String ipAddress;

    /**
     * 订单摘要信息
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

    public String getUserId() {
        return userId;
    }
    
    public String getUserType() {
    	return userType;
    }

    public String getOrderType() {
        return orderType;
    }

    public String getChlId() {
        return chlId;
    }

    public String getOperId() {
        return operId;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public String getOrderDesc() {
        return orderDesc;
    }

    public String getKeywords() {
        return keywords;
    }

    public String getRemark() {
        return remark;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public void setUserType(String userType) {
    	this.userType = userType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public void setChlId(String chlId) {
        this.chlId = chlId;
    }

    public void setOperId(String operId) {
        this.operId = operId;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
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

	public Long getAcctId() {
		return acctId;
	}

	public void setAcctId(Long acctId) {
		this.acctId = acctId;
	}

	public String getDeliveryFlag() {
		return deliveryFlag;
	}

	public void setDeliveryFlag(String deliveryFlag) {
		this.deliveryFlag = deliveryFlag;
	}

	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public void setAcctId(long acctId) {
		this.acctId = acctId;
	}
}
