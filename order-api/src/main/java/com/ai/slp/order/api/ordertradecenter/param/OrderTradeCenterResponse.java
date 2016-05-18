package com.ai.slp.order.api.ordertradecenter.param;

import java.util.List;

import com.ai.opt.base.vo.BaseResponse;

/**
 * 订单提交返回参数 Date: 2016年5月13日 <br>
 * Copyright (c) 2016 asiainfo.com <br>
 * 
 * @author zhangxw
 */
public class OrderTradeCenterResponse extends BaseResponse {

    private static final long serialVersionUID = 1L;

    private List<Long> orderList;

    public List<Long> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Long> orderList) {
        this.orderList = orderList;
    }

}
