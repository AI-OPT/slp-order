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
     * 商品名称关键字
     */
    // private String keyWords;

    /**
     * 父订单号
     */
    private Long parentOrderId;

    /**
     * 子订单号标识
     */
    private String subFlag;

    /**
     * 订单类型
     */
    private String orderType;

    /**
     * 订单生成时间开始
     */
    private String orderTimeBegin;

    /**
     * 订单生成时间结束
     */
    private String orderTimeEnd;

    /**
     * 订单状态变化时间开始
     */
    private String orderChgBegin;

    /**
     * 订单状态变化时间结束
     */
    private String orderChgEnd;

    /**
     * pageNo
     */
    private Integer pageNo;

    /**
     * pageSize
     */
    private Integer pageSize;

    public Long getParentOrderId() {
        return parentOrderId;
    }

    public void setParentOrderId(Long parentOrderId) {
        this.parentOrderId = parentOrderId;
    }

    public String getSubFlag() {
        return subFlag;
    }

    public void setSubFlag(String subFlag) {
        this.subFlag = subFlag;
    }

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

    public String getOrderChgBegin() {
        return orderChgBegin;
    }

    public void setOrderChgBegin(String orderChgBegin) {
        this.orderChgBegin = orderChgBegin;
    }

    public String getOrderChgEnd() {
        return orderChgEnd;
    }

    public void setOrderChgEnd(String orderChgEnd) {
        this.orderChgEnd = orderChgEnd;
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

}
