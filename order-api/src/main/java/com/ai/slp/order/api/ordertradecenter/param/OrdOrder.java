package com.ai.slp.order.api.ordertradecenter.param;

import java.io.Serializable;

public class OrdOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户Id
     */
    private String userId;

    /**
     * 账户Id
     */
    private long acctId;

    /**
     * 订购Id
     */
    private long subsId;

    /**
     * 业务类型
     */
    private String orderType;

    /**
     * 是否需要快递
     */
    private String deliveryFlag;

    /**
     * 供应商Id
     */
    private String supplierId;

    /**
     * 销售商Id
     */
    private long sellerId;

    /**
     * 渠道Id
     */
    private String chlId;

    /**
     * 操作员Id
     */
    private String operId;

    /**
     * 省份
     */
    private String provinceCode;

    /**
     * 地市
     */
    private String cityCode;

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

    public long getAcctId() {
        return acctId;
    }

    public long getSubsId() {
        return subsId;
    }

    public String getOrderType() {
        return orderType;
    }

    public String getDeliveryFlag() {
        return deliveryFlag;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public long getSellerId() {
        return sellerId;
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

    public void setAcctId(long acctId) {
        this.acctId = acctId;
    }

    public void setSubsId(long subsId) {
        this.subsId = subsId;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public void setDeliveryFlag(String deliveryFlag) {
        this.deliveryFlag = deliveryFlag;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public void setSellerId(long sellerId) {
        this.sellerId = sellerId;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
