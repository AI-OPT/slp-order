package com.ai.slp.order.api.orderlist.param;

import com.ai.opt.base.vo.BaseInfo;

/**
 * 获取订单列表入参 Date: 2016年5月3日 <br>
 * Copyright (c) 2016 asiainfo.com <br>
 * 
 * @author zhangqiang7
 */
public class QueryOrderListRequest extends BaseInfo {

    private static final long serialVersionUID = 1L;

    /**
     * 订单号
     */
    private Long orderId;

    /**
     * 订单类型
     */
    private String orderType;

    /**
     * 订单状态
     */
    private String state;

    /**
     * 支付方式
     */
    private String payStyle;

    /**
     * 订单生成时间开始
     */
    private String orderTimeBegin;

    /**
     * 订单生成时间结束
     */
    private String orderTimeEnd;

    /**
     * pageNo
     */
    private Integer pageNo;

    /**
     * pageSize
     */
    private Integer pageSize;

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderTimeBegin() {
        return orderTimeBegin;
    }

    public void setOrderTimeBegin(String orderTimeBegin) {
        this.orderTimeBegin = orderTimeBegin;
    }

    public String getOrderTimeEnd() {
        return orderTimeEnd;
    }

    public void setOrderTimeEnd(String orderTimeEnd) {
        this.orderTimeEnd = orderTimeEnd;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPayStyle() {
        return payStyle;
    }

    public void setPayStyle(String payStyle) {
        this.payStyle = payStyle;
    }

}
