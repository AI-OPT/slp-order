package com.ai.slp.order.api.ordertradecenter.interfaces;

import com.ai.slp.order.api.ordertradecenter.param.OrderTradeCenterRequest;
import com.ai.slp.order.api.ordertradecenter.param.OrderTradeCenterResponse;

/**
 * 订单提交核心服务
 * Date: 2016年5月13日 <br>
 * Copyright (c) 2016 asiainfo.com <br>
 * 
 * @author zhangxw
 */
public interface IOrderTradeCenterSV {

    /**
     * 订单提交
     * @param request
     * @return
     * @author zhangxw
     * @ApiDocMethod
     */
    public OrderTradeCenterResponse apply(OrderTradeCenterRequest request);

}
