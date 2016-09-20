package com.ai.slp.order.api.ordertradecenter.param;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

public class OrdBaseInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户Id
     */
    @NotBlank(message = "用户Id不能为空")
    private String userId;
    
    /**
     * 用户类型
     */
    private String userType;
    
    /**
     * 订单类型
     */
    @NotBlank(message = "订单类型不能为空")
    private String orderType;

    /**
     * 渠道Id
     */
    @NotBlank(message = "渠道Id不能为空")
    private String chlId;
    
    /**
     * 账户id
     */
    private Long acctId;

    /**
     * 操作员Id
     */
    private String operId;
    
    /**
     * 是否需要物流
     */
    @NotBlank(message = "是否需要物流信息不能为空")
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
}
