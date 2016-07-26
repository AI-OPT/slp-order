package com.ai.slp.order.service.business.interfaces;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.slp.order.api.ordertradecenter.param.OrderApiTradeCenterRequest;
import com.ai.slp.order.api.ordertradecenter.param.OrderApiTradeCenterResponse;

public interface IOrdOrderApiTradeBusiSV {

    public OrderApiTradeCenterResponse apiApply(OrderApiTradeCenterRequest request) throws BusinessException,
            SystemException;
}
