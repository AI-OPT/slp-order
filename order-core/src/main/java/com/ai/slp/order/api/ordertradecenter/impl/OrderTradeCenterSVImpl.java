package com.ai.slp.order.api.ordertradecenter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.components.mds.MDSClientFactory;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.slp.order.api.ordertradecenter.interfaces.IOrderTradeCenterSV;
import com.ai.slp.order.api.ordertradecenter.param.OrderTradeCenterRequest;
import com.ai.slp.order.api.ordertradecenter.param.OrderTradeCenterResponse;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.service.business.interfaces.IOrdOrderTradeBusiSV;
import com.ai.slp.order.util.MQConfigUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;

@Service(validation = "true")
@Component
public class OrderTradeCenterSVImpl implements IOrderTradeCenterSV {

    @Autowired
    private IOrdOrderTradeBusiSV ordOrderTradeBusiSV;

    @Override
    public OrderTradeCenterResponse apply(OrderTradeCenterRequest request)
            throws BusinessException, SystemException {
    	boolean ccsMqFlag=false;
    	//从配置中心获取ccsMqFlag
    	ccsMqFlag=MQConfigUtil.getCCSMqFlag();
    	
    	//非消息模式下，同步调用服务
    	if(!ccsMqFlag){
    		OrderTradeCenterResponse response = ordOrderTradeBusiSV.apply(request);
    		ResponseHeader responseHeader = new ResponseHeader(true,
    				ExceptCodeConstants.Special.SUCCESS, "成功");
    		response.setResponseHeader(responseHeader);
    		return response;    		
    	}
    	else{
    		//消息模式下，异步调用服务
    		OrderTradeCenterResponse response=new OrderTradeCenterResponse();
    		//发送消息
    		MDSClientFactory.getSenderClient(OrdersConstants.MDSNS.MDS_NS_ORDER_TOPIC).send(JSON.toJSONString(request), 0);
    		ResponseHeader responseHeader = new ResponseHeader(true,
    				ExceptCodeConstants.Special.SUCCESS, "成功");
    		response.setResponseHeader(responseHeader);
    		return response;    
    	}
    }
}
