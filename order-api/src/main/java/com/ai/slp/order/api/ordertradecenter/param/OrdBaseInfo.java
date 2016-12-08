package com.ai.slp.order.api.ordertradecenter.param;

import java.io.Serializable;


public class OrdBaseInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户Id
     */
    private String userId;
    
    /**
     * 用户名称
     */
    private String userName;
    
    /**
     * 用户手机号
     */
    private String userTel;
    
    /**
     * 用户类型
     */
    private String userType;
    
    /**
     * 业务标识
     */
    private String flag;
    
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

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getDeliveryFlag() {
		return deliveryFlag;
	}

	public void setDeliveryFlag(String deliveryFlag) {
		this.deliveryFlag = deliveryFlag;
	}

	public long getAcctId() {
		return acctId;
	}

	public void setAcctId(long acctId) {
		this.acctId = acctId;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserTel() {
		return userTel;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}
}
