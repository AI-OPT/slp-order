package com.ai.slp.order.api.ordertradecenter.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.slp.order.api.ordertradecenter.interfaces.IOrderTradeCenterSV;
import com.ai.slp.order.api.ordertradecenter.param.OrderTradeCenterRequest;
import com.ai.slp.order.api.ordertradecenter.param.OrderTradeCenterResponse;
import com.ai.slp.order.service.business.interfaces.IOrdOrderTradeBusiSV;

@Service
@Transactional
public class OrderTradeCenterSVImpl implements IOrderTradeCenterSV {

    @Autowired
    private IOrdOrderTradeBusiSV ordOrderTradeBusiSV;

    @Override
    public OrderTradeCenterResponse apply(OrderTradeCenterRequest request)
            throws BusinessException, SystemException {
        OrderTradeCenterResponse response = new OrderTradeCenterResponse();

        List<Long> orderList = ordOrderTradeBusiSV.apply(request);
        response.setOrderList(orderList);
        ResponseHeader responseHeader = new ResponseHeader(true,
                ExceptCodeConstants.Special.SUCCESS, "成功");
        response.setResponseHeader(responseHeader);
        return response;
    }

}
