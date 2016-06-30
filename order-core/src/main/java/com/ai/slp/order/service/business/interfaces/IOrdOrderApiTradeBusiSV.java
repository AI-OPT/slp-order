package com.ai.slp.order.service.business.interfaces;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.slp.order.api.ordertradecenter.param.OrderApiTradeCenterRequest;

public interface IOrdOrderApiTradeBusiSV {

    public BaseResponse apiApply(OrderApiTradeCenterRequest request) throws BusinessException,
            SystemException;
}
