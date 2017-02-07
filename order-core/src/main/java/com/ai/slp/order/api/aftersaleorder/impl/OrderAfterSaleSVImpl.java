package com.ai.slp.order.api.aftersaleorder.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.components.mds.MDSClientFactory;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.slp.order.api.aftersaleorder.interfaces.IOrderAfterSaleSV;
import com.ai.slp.order.api.aftersaleorder.param.OrderOFCBackRequest;
import com.ai.slp.order.api.aftersaleorder.param.OrderReturnRequest;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.service.business.interfaces.IOrderAfterSaleBusiSV;
import com.ai.slp.order.util.MQConfigUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;

@Service(validation = "true")
@Component
public class OrderAfterSaleSVImpl implements IOrderAfterSaleSV {
	
	@Autowired
	private IOrderAfterSaleBusiSV orderAfterSaleBusiSV;
	
	@Override
	public BaseResponse back(OrderReturnRequest request) throws BusinessException, SystemException {
		boolean ccsMqFlag=false;
    	//从配置中心获取ccsMqFlag
    	ccsMqFlag=MQConfigUtil.getCCSMqFlag();
    	//非消息模式下，同步调用服务
		if(!ccsMqFlag) {
			BaseResponse response =new BaseResponse();
			orderAfterSaleBusiSV.back(request);
			ResponseHeader responseHeader = new ResponseHeader(true,
					ExceptCodeConstants.Special.SUCCESS, "成功");
			response.setResponseHeader(responseHeader);
			return response;
		}else {
			//消息模式下，异步调用服务
			BaseResponse response =new BaseResponse();
			//发送消息
			MDSClientFactory.getSenderClient(OrdersConstants.MDSNS.MDS_NS_ORDER_TOPIC).send(JSON.toJSONString(request), 0);
			ResponseHeader responseHeader = new ResponseHeader(true,
					ExceptCodeConstants.Special.SUCCESS, "成功");
			response.setResponseHeader(responseHeader);
			return response;
		}
	}

	@Override
	public BaseResponse exchange(OrderReturnRequest request) throws BusinessException, SystemException {
		boolean ccsMqFlag=false;
		ccsMqFlag = MQConfigUtil.getCCSMqFlag();
		if(!ccsMqFlag) {
			//非消息模式下,同步调用服务
			BaseResponse response =new BaseResponse();
			orderAfterSaleBusiSV.exchange(request);
			ResponseHeader responseHeader = new ResponseHeader(true,
					ExceptCodeConstants.Special.SUCCESS, "成功");
			response.setResponseHeader(responseHeader);
			return response;
		}else {
			//消息模式下,异步调用服务
			BaseResponse response =new BaseResponse();
			//发送消息
			MDSClientFactory.getSenderClient(OrdersConstants.MDSNS.MDS_NS_ORDER_TOPIC).send(JSON.toJSONString(request), 0);
			ResponseHeader responseHeader = new ResponseHeader(true,
					ExceptCodeConstants.Special.SUCCESS, "成功");
			response.setResponseHeader(responseHeader);
			return response;
		}
	}

	@Override
	public BaseResponse refund(OrderReturnRequest request) throws BusinessException, SystemException {
		boolean ccsMqFlag=false;
		ccsMqFlag = MQConfigUtil.getCCSMqFlag();
		//非消息模式
		if(!ccsMqFlag) {
			BaseResponse response =new BaseResponse();
			orderAfterSaleBusiSV.refund(request);
	        ResponseHeader responseHeader = new ResponseHeader(true,
	                ExceptCodeConstants.Special.SUCCESS, "成功");
	        response.setResponseHeader(responseHeader);
	        return response;
		}else {
			//消息模式下
			BaseResponse response =new BaseResponse();
			//发送消息
			MDSClientFactory.getSenderClient(OrdersConstants.MDSNS.MDS_NS_ORDER_TOPIC).send(JSON.toJSONString(request), 0);
	        ResponseHeader responseHeader = new ResponseHeader(true,
	                ExceptCodeConstants.Special.SUCCESS, "成功");
	        response.setResponseHeader(responseHeader);
	        return response;
		}
	}

	@Override
	public BaseResponse backStateOFC(OrderOFCBackRequest request) throws BusinessException, SystemException {
		boolean ccsMqFlag=false;
		ccsMqFlag = MQConfigUtil.getCCSMqFlag();
		//非消息模式
		if(!ccsMqFlag) {
			BaseResponse response =new BaseResponse();
			orderAfterSaleBusiSV.backStateOFC(request);
			ResponseHeader responseHeader = new ResponseHeader(true,
					ExceptCodeConstants.Special.SUCCESS, "成功");
			response.setResponseHeader(responseHeader);
			return response;
		}else {
			//消息模式
			BaseResponse response =new BaseResponse();
			MDSClientFactory.getSenderClient(OrdersConstants.MDSNS.MDS_NS_ORDER_TOPIC).send(JSON.toJSONString(request), 0);
			ResponseHeader responseHeader = new ResponseHeader(true,
					ExceptCodeConstants.Special.SUCCESS, "成功");
			response.setResponseHeader(responseHeader);
			return response;
		}
	}
	
}
