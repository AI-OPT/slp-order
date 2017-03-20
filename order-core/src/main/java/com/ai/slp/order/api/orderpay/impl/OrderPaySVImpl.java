package com.ai.slp.order.api.orderpay.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.components.mds.MDSClientFactory;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.slp.order.api.orderpay.interfaces.IOrderPaySV;
import com.ai.slp.order.api.orderpay.param.OrderOidRequest;
import com.ai.slp.order.api.orderpay.param.OrderPayRequest;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.service.business.interfaces.IOrderPayBusiSV;
import com.ai.slp.order.service.business.interfaces.search.IOrderIndexBusiSV;
import com.ai.slp.order.util.MQConfigUtil;
import com.ai.slp.order.util.ValidateUtils;
import com.alibaba.fastjson.JSON;

/**
 * 订单收费服务 Date: 2016年5月24日 <br>
 * Copyright (c) 2016 asiainfo.com <br>
 * 
 * @author zhangxw
 */
@Component
public class OrderPaySVImpl implements IOrderPaySV {
    private static Logger logger = LoggerFactory.getLogger(OrderPaySVImpl.class);
    @Autowired
    private IOrderPayBusiSV orderPayBusiSV;
    @Autowired
    private IOrderIndexBusiSV orderIndexBusiSV;

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
		boolean ccsMqFlag=false;
		//ccsMqFlag = MQConfigUtil.getCCSMqFlag();
		//非消息模式 同步调用服务
		if(!ccsMqFlag) {
			 BaseResponse response = new BaseResponse();
	         orderPayBusiSV.returnOid(request);
	         ResponseHeader responseHeader = new ResponseHeader(true,
	                ExceptCodeConstants.Special.SUCCESS, "成功");
	         response.setResponseHeader(responseHeader);
		     return response;  
		}else {
			//消息模式下 异步调用服务
			 BaseResponse response = new BaseResponse();
			 MDSClientFactory.getSenderClient(OrdersConstants.MDSNS.MDS_NS_ORDER_RETURNOID_TOPIC).send(JSON.toJSONString(request), 0);
	         ResponseHeader responseHeader = new ResponseHeader(true,
	                ExceptCodeConstants.Special.SUCCESS, "成功");
	         response.setResponseHeader(responseHeader);
		     return response;  
		}
		
	}
}
