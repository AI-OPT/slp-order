package com.ai.slp.order.api.ordertradecenter.param;


import com.ai.opt.base.vo.BaseInfo;

/**
 * api订单提交请求参数 Date: 2016年6月29日 <br>
 * Copyright (c) 2016 asiainfo.com <br>
 * 
 * @author zhangxw
 */
public class OrderApiTradeCenterRequest extends BaseInfo {

    private static final long serialVersionUID = 1L;

    /**
     * 外部订单ID（下游）
     */
    private String downstreamOrderId;

    /**
     * 用户Id
     */
    private String userId;

    /**
     * 用户类型
     */
    private String userType;

    /**
     * 账户Id
     */
    private long acctId;

    /**
     * 单品Id
     */
    private String skuId;

    /**
     * 购买数量
     */
    private int buySum;

    /**
     * 销售单价
     */
    private long salePrice;

    /**
     * 充值面额
     */
    private String chargeFee;

    /**
     * 订单类型
     */
    private String orderType;

    /**
     * 支付方式
     */
    private String payStyle;

    /**
     * 时间戳
     */
    private String lockTime;
    
    /**
     * 手机号
     */
    private String infoJson;
    
    public String getUserId() {
        return userId;
    }

    public String getUserType() {
        return userType;
    }

    public long getAcctId() {
        return acctId;
    }

    public String getSkuId() {
        return skuId;
    }

    public int getBuySum() {
        return buySum;
    }

    public long getSalePrice() {
        return salePrice;
    }

    public String getChargeFee() {
        return chargeFee;
    }

    public String getOrderType() {
        return orderType;
    }

    public String getPayStyle() {
        return payStyle;
    }

    public String getLockTime() {
        return lockTime;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setAcctId(long acctId) {
        this.acctId = acctId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public void setBuySum(int buySum) {
        this.buySum = buySum;
    }

    public void setSalePrice(long salePrice) {
        this.salePrice = salePrice;
    }

    public void setChargeFee(String chargeFee) {
        this.chargeFee = chargeFee;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public void setPayStyle(String payStyle) {
        this.payStyle = payStyle;
    }

    public void setLockTime(String lockTime) {
        this.lockTime = lockTime;
    }

    public String getDownstreamOrderId() {
        return downstreamOrderId;
    }

    public void setDownstreamOrderId(String downstreamOrderId) {
        this.downstreamOrderId = downstreamOrderId;
    }

    public String getInfoJson() {
        return infoJson;
    }

    public void setInfoJson(String infoJson) {
        this.infoJson = infoJson;
    }
}
