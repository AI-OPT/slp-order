package com.ai.slp.order.service.business.interfaces;

import java.util.List;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.slp.order.api.ordertradecenter.param.OrderTradeCenterRequest;

public interface IOrdOrderTradeBusiSV {

    public List<Long> apply(OrderTradeCenterRequest request) throws BusinessException,
            SystemException;
}
