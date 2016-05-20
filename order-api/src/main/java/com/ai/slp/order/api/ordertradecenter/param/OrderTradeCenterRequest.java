package com.ai.slp.order.api.ordertradecenter.param;

import com.ai.opt.base.vo.BaseInfo;

/**
 * 订单提交请求参数 Date: 2016年5月13日 <br>
 * Copyright (c) 2016 asiainfo.com <br>
 * 
 * @author zhangxw
 */
public class OrderTradeCenterRequest extends BaseInfo {

    private static final long serialVersionUID = 1L;

    /**
     * 订单信息
     */
    private OrdOrderInfo ordOrderInfo;

    public OrdOrderInfo getOrdOrderInfo() {
        return ordOrderInfo;
    }

    public void setOrdOrderInfo(OrdOrderInfo ordOrderInfo) {
        this.ordOrderInfo = ordOrderInfo;
    }

}
