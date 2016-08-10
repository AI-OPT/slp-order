package com.ai.slp.order.vo;

/**
 * 调用充值路由返回参数 Date: 2016年6月13日 <br>
 * Copyright (c) 2016 asiainfo.com <br>
 * 
 * @author zhangxw
 */

public class RouteServResVo {

    /**
     * 订单Id
     */
    private String orderId;

    /**
     * 业务类型
     */
    private String bizType;

    /**
     * 订单状态
     */
    private String coopOrderStatus;

    /**
     * 供应商平台的订单号
     */
    private String coopOrderId;
    
    /**
     * 外部供货商ID
     */
    private String coSysId;

    /**
     * 错误码
     */
    private String errCode;

    /**
     * 错误码描述
     */
    private String errDesc;

    public String getOrderId() {
        return orderId;
    }

    public String getBizType() {
        return bizType;
    }

    public String getCoopOrderStatus() {
        return coopOrderStatus;
    }

    public String getCoopOrderId() {
        return coopOrderId;
    }

    public String getErrCode() {
        return errCode;
    }

    public String getErrDesc() {
        return errDesc;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public void setCoopOrderStatus(String coopOrderStatus) {
        this.coopOrderStatus = coopOrderStatus;
    }

    public void setCoopOrderId(String coopOrderId) {
        this.coopOrderId = coopOrderId;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public void setErrDesc(String errDesc) {
        this.errDesc = errDesc;
    }

	public String getCoSysId() {
		return coSysId;
	}

	public void setCoSysId(String coSysId) {
		this.coSysId = coSysId;
	}
    
}
