package com.ai.slp.order.service.business.interfaces;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.slp.order.api.orderpay.param.OrderPayRequest;

/**
 * 订单收费 Date: 2016年5月24日 <br>
 * Copyright (c) 2016 asiainfo.com <br>
 * 
 * @author zhangxw
 */
public interface IOrderPayBusiSV {

    public void orderPay(OrderPayRequest request) throws BusinessException, SystemException;
}
