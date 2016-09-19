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

    private OrdFeeInfo ordFeeInfo;

    private List<OrderResInfo> orderResInfos;

    public OrdFeeInfo getOrdFeeInfo() {
        return ordFeeInfo;
    }

    public void setOrdFeeInfo(OrdFeeInfo ordFeeInfo) {
        this.ordFeeInfo = ordFeeInfo;
    }

	public List<OrderResInfo> getOrderResInfos() {
		return orderResInfos;
	}

	public void setOrderResInfos(List<OrderResInfo> orderResInfos) {
		this.orderResInfos = orderResInfos;
	}
}
