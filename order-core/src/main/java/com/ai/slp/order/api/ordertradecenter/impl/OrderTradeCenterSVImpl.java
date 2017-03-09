package com.ai.slp.order.api.ordertradecenter.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.components.mds.MDSClientFactory;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.slp.order.api.ordertradecenter.interfaces.IOrderTradeCenterSV;
import com.ai.slp.order.api.ordertradecenter.param.OrderResInfo;
import com.ai.slp.order.api.ordertradecenter.param.OrderTradeCenterRequest;
import com.ai.slp.order.api.ordertradecenter.param.OrderTradeCenterResponse;
import com.ai.slp.order.api.sesdata.param.SesDataRequest;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.service.business.interfaces.IOrdOrderTradeBusiSV;
import com.ai.slp.order.service.business.interfaces.search.IOrderIndexBusiSV;
import com.ai.slp.order.util.MQConfigUtil;
import com.ai.slp.order.util.ValidateUtils;
import com.alibaba.fastjson.JSON;

@Component
public class OrderTradeCenterSVImpl implements IOrderTradeCenterSV {

    @Autowired
    private IOrdOrderTradeBusiSV ordOrderTradeBusiSV;
    
    @Autowired
    private IOrderIndexBusiSV orderIndexBusiSV;

    @Override
    public OrderTradeCenterResponse apply(OrderTradeCenterRequest request)
            throws BusinessException, SystemException {
    	//参数校验
    	ValidateUtils.validateOrderTradeCenter(request); 
    	boolean ccsMqFlag=false;
    	//从配置中心获取ccsMqFlag
    	ccsMqFlag=MQConfigUtil.getCCSMqFlag();
    	
    	//非消息模式下，同步调用服务
    	if(!ccsMqFlag){
    		OrderTradeCenterResponse response = ordOrderTradeBusiSV.apply(request);
    		//刷新搜索引擎数据
    		List<OrderResInfo> orderResInfos = response.getOrderResInfos();
    		for (OrderResInfo orderResInfo : orderResInfos) {
    			SesDataRequest sesReq=new SesDataRequest();
    			sesReq.setTenantId(request.getTenantId());
    			sesReq.setParentOrderId(orderResInfo.getOrderId());
    			this.orderIndexBusiSV.insertSesData(sesReq);
			}
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
