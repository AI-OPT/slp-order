package com.ai.slp.order.api.ordertradecenter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.components.mds.MDSClientFactory;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.slp.order.api.orderrule.interfaces.IOrderMonitorSV;
import com.ai.slp.order.api.orderrule.param.OrderMonitorBeforResponse;
import com.ai.slp.order.api.orderrule.param.OrderMonitorRequest;
import com.ai.slp.order.api.ordertradecenter.interfaces.IOrderTradeCenterSV;
import com.ai.slp.order.api.ordertradecenter.param.OrdBaseInfo;
import com.ai.slp.order.api.ordertradecenter.param.OrderTradeCenterRequest;
import com.ai.slp.order.api.ordertradecenter.param.OrderTradeCenterResponse;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.service.business.interfaces.IOrdOrderTradeBusiSV;
import com.ai.slp.order.util.MQConfigUtil;
import com.ai.slp.order.util.ValidateUtils;
import com.alibaba.fastjson.JSON;

@Component
public class OrderTradeCenterSVImpl implements IOrderTradeCenterSV {

    @Autowired
    private IOrdOrderTradeBusiSV ordOrderTradeBusiSV;
    @Autowired
    private IOrderMonitorSV orderMonitorSV;
    
    @Override
    public OrderTradeCenterResponse apply(OrderTradeCenterRequest request)
            throws BusinessException, SystemException {
    	//参数校验
    	ValidateUtils.validateOrderTradeCenter(request); 
    	boolean ccsMqFlag=false;
    	//从配置中心获取ccsMqFlag
    	//ccsMqFlag=MQConfigUtil.getCCSMqFlag();
    	//非消息模式下，同步调用服务
    	if(!ccsMqFlag){
    	 	//订单下单前异常监控
        	OrderMonitorRequest monitorRequest=new OrderMonitorRequest();
        	OrdBaseInfo ordBaseInfo = request.getOrdBaseInfo();
        	monitorRequest.setUserId(ordBaseInfo.getUserId());
        	monitorRequest.setIpAddress(ordBaseInfo.getIpAddress());
        	OrderMonitorBeforResponse beforSubmitOrder = orderMonitorSV.beforSubmitOrder(monitorRequest);
    		OrderTradeCenterResponse response = ordOrderTradeBusiSV.apply(request,
    				beforSubmitOrder,monitorRequest);
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
