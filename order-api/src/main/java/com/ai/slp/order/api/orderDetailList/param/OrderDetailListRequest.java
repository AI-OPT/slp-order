package com.ai.slp.order.api.orderDetailList.param;

public class OrderDetailListRequest {
    /**
     * 业务订单号
     */
    public long orderId;
    
    /**
     * 父Id
     */
    public long parentOrderId;
    
    /**
     * 订单类型
     */
    public String orderType;
    
    /**
     * 用户Id
     */
    public String userId;
    
    /**
     * 账户Id
     */
    public String acctId;

    /**
     * 状态（已支付、未支付等）
     */
    public String state;
    
    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAcctId() {
        return acctId;
    }

    public void setAcctId(String acctId) {
        this.acctId = acctId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public long getParentOrderId() {
        return parentOrderId;
    }

    public void setParentOrderId(long parentOrderId) {
        this.parentOrderId = parentOrderId;
    }
    
}
