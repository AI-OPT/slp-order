package com.ai.slp.order.api.orderpay.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.slp.order.api.orderpay.interfaces.IOrderPaySV;
import com.ai.slp.order.api.orderpay.param.OrderOidRequest;
import com.ai.slp.order.api.orderpay.param.OrderPayRequest;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.service.business.interfaces.IOrderPayBusiSV;
import com.ai.slp.order.util.ValidateUtils;
import com.alibaba.dubbo.config.annotation.Service;

/**
 * 订单收费服务 Date: 2016年5月24日 <br>
 * Copyright (c) 2016 asiainfo.com <br>
 * 
 * @author zhangxw
 */
@Service
@Component
public class OrderPaySVImpl implements IOrderPaySV {
	
    private static Logger logger = LoggerFactory.getLogger(OrderPaySVImpl.class);
    
    @Autowired
    private IOrderPayBusiSV orderPayBusiSV;
    /**
     * 订单收费
     */
    @Override
    public BaseResponse pay(OrderPayRequest request) throws BusinessException, SystemException {
        logger.debug("开始接收订单收费服务调用......");
        //参数校验
        ValidateUtils.validateOrderPay(request);
        BaseResponse response = new BaseResponse();
        orderPayBusiSV.orderPay(request);
        ResponseHeader responseHeader = new ResponseHeader(true,
                ExceptCodeConstants.Special.SUCCESS, "成功");
        response.setResponseHeader(responseHeader);
        return response;
    }

	@Override
	public BaseResponse returnOid(OrderOidRequest request) throws BusinessException, SystemException {
		/* 参数校验*/
		ValidateUtils.validateReturnOid(request);
		BaseResponse response = new BaseResponse();
		OrdOrder order =new OrdOrder();
		order.setOrderId(request.getOrderId());
		order.setDownstreamOrderId(request.getOid());
        orderPayBusiSV.returnOid(order);
        ResponseHeader responseHeader = new ResponseHeader(true,
                ExceptCodeConstants.Special.SUCCESS, "成功");
        response.setResponseHeader(responseHeader);
	    return response;  
	}
}
