package com.ai.slp.order.vo;

/**
 * 调用充值路由请求参数 Date: 2016年6月13日 <br>
 * Copyright (c) 2016 asiainfo.com <br>
 * 
 * @author zhangxw
 */
public class RouteServReqVo {

    /**
     * 订单Id
     */
    private String orderId;

    /**
     * 业务类型
     */
    private String bizType;

    /**
     * 供应品Id
     */
    private String proId;

    /**
     * 手机号码
     */
    private String accountVal;

    /**
     * 购买数量
     */
    private int buyNum;

    /**
     * 商品单价
     */
    private long unitPrice;

    /**
     * 回调通知地址
     */
    private String notifyUrl;
    
    /**
     * 供应商Id
     */
    private String coSysId;
    
    /**
     *运营商 
     */
    private String operatorId;
    
	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getCoSysId() {
		return coSysId;
	}

	public void setCoSysId(String coSysId) {
		this.coSysId = coSysId;
	}

	public String getOrderId() {
        return orderId;
    }

    public String getBizType() {
        return bizType;
    }

    public String getAccountVal() {
        return accountVal;
    }

    public int getBuyNum() {
        return buyNum;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public void setAccountVal(String accountVal) {
        this.accountVal = accountVal;
    }

    public void setBuyNum(int buyNum) {
        this.buyNum = buyNum;
    }

    public long getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(long unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

}
