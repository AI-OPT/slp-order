package com.ai.slp.order.api.ordertradecenter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.slp.order.api.ordertradecenter.interfaces.IOrderTradeCenterSV;
import com.ai.slp.order.api.ordertradecenter.param.OrderTradeCenterRequest;
import com.ai.slp.order.api.ordertradecenter.param.OrderTradeCenterResponse;
import com.ai.slp.order.service.business.interfaces.IOrdOrderTradeBusiSV;
import com.alibaba.dubbo.config.annotation.Service;

@Service(validation = "true")
@Component
public class OrderTradeCenterSVImpl implements IOrderTradeCenterSV {

    @Autowired
    private IOrdOrderTradeBusiSV ordOrderTradeBusiSV;

   /* @Autowired
    private IOrdOrderApiTradeBusiSV ordOrderApiTradeBusiSV;*/

    @Override
    public OrderTradeCenterResponse apply(OrderTradeCenterRequest request)
            throws BusinessException, SystemException {
        OrderTradeCenterResponse response = ordOrderTradeBusiSV.apply(request);
        ResponseHeader responseHeader = new ResponseHeader(true,
                ExceptCodeConstants.Special.SUCCESS, "成功");
        response.setResponseHeader(responseHeader);
        return response;
    }

  /*  @Override
    public OrderApiTradeCenterResponse apiApply(OrderApiTradeCenterRequest request) throws BusinessException,
            SystemException {
    	OrderApiTradeCenterResponse response;
		try {
			response = ordOrderApiTradeBusiSV.apiApply(request);
			ResponseHeader responseHeader = new ResponseHeader(true,
					ExceptCodeConstants.Special.SUCCESS, "成功");
			response.setResponseHeader(responseHeader);
		} catch (SystemException e) {
			throw new SystemException(ResultCodeConstants.ApiOrder.SYSTEM_ERROR, "系统异常");
		}
        return response;
    }*/
}
