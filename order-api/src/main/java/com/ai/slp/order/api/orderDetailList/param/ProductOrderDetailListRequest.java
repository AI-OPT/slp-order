package com.ai.slp.order.api.orderDetailList.param;

import java.util.Date;

public class ProductOrderDetailListRequest {
    
    /**
     * 订单来源
     */
    private String orderSource;
    
    /**
     * 订单类型
     */
    private String orderType;
    
    /**
     * 订单归属地
     */
    private String cityCode;
    
    /**
     * 是否需要物流
     */
    private String deliveryFlag; 
    
    /**
     * 父订单号
     */
    private String parentOrderId;
    
    /**
     * 订单状态
     */
    private String state;
    
    /**
     * 下单时间
     */
    private Date orderTime;

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getDeliveryFlag() {
        return deliveryFlag;
    }

    public void setDeliveryFlag(String deliveryFlag) {
        this.deliveryFlag = deliveryFlag;
    }

    public String getParentOrderId() {
        return parentOrderId;
    }

    public void setParentOrderId(String parentOrderId) {
        this.parentOrderId = parentOrderId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }
}
