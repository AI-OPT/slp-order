package com.ai.slp.order.api.orderrule.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.paas.ipaas.util.StringUtil;
import com.ai.slp.order.api.orderrule.interfaces.IOrderMonitorSV;
import com.ai.slp.order.api.orderrule.param.OrderMonitorBeforResponse;
import com.ai.slp.order.api.orderrule.param.OrderMonitorRequest;
import com.ai.slp.order.api.orderrule.param.OrderMonitorResponse;
import com.ai.slp.order.monitor.MonitorService;
import com.alibaba.dubbo.config.annotation.Service;
@Service(validation="true")
@Component
public class OrderMonitorSVImpl implements IOrderMonitorSV {
	
	private static final Logger LOG = LoggerFactory.getLogger(OrderMonitorSVImpl.class);

	@Autowired
	private MonitorService orderMonitorService;
	
	@Override
	public OrderMonitorBeforResponse beforSubmitOrder(OrderMonitorRequest request)
			throws BusinessException, SystemException {
		OrderMonitorBeforResponse response = new OrderMonitorBeforResponse();
		//
		ResponseHeader responseHeader = new ResponseHeader();
		if(null == request){
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL,"请求参数不能为空");
		}
		if(StringUtil.isBlank(request.getUserId())){
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL,"用户id不能为空");
		}
		if(StringUtil.isBlank(request.getIpAddress())){
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL,"ip地址不能为空");
		}
		//
		try{
			response = this.orderMonitorService.beforSubmitOrder(request.getIpAddress(), request.getUserId());
			responseHeader.setIsSuccess(true);
			responseHeader.setResultCode("000000");
			responseHeader.setResultMessage("成功");
			//
			response.setResponseHeader(responseHeader);
		}catch(BusinessException e){
			responseHeader.setResultCode(e.getErrorCode());
			responseHeader.setResultMessage(e.getErrorMessage());
			//
			response.setResponseHeader(responseHeader);
			LOG.error(e.getMessage(),e);
		}catch(Exception e){
			responseHeader.setResultCode("999999");
			responseHeader.setResultMessage("订单监控异常");
			//
			response.setResponseHeader(responseHeader);
			LOG.error("订单监控异常:"+e.getMessage(),e);
		}
		//
		return response;
	}

	@Override
	public OrderMonitorResponse afterSubmitOrder(OrderMonitorRequest request)
			throws BusinessException, SystemException {
		OrderMonitorResponse response = new OrderMonitorResponse();
		//
		ResponseHeader responseHeader = new ResponseHeader();
		if(null == request){
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL,"请求参数不能为空");
		}
		if(StringUtil.isBlank(request.getUserId())){
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL,"用户id不能为空");
		}
		if(StringUtil.isBlank(request.getIpAddress())){
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL,"ip地址不能为空");
		}
		//
		try{

			this.orderMonitorService.afterSubmitOrder(request.getIpAddress(), request.getUserId());
			responseHeader.setIsSuccess(true);
			responseHeader.setResultCode("000000");
			responseHeader.setResultMessage("成功");
			//
			response.setResponseHeader(responseHeader);
		}catch(BusinessException e){
			responseHeader.setResultCode(e.getErrorCode());
			responseHeader.setResultMessage(e.getErrorMessage());
			//
			response.setResponseHeader(responseHeader);
			LOG.error(e.getMessage(),e);
		}catch(Exception e){
			responseHeader.setResultCode("999999");
			responseHeader.setResultMessage("订单监控异常");
			//
			response.setResponseHeader(responseHeader);
			
			LOG.error("订单监控异常:"+e.getMessage(),e);
		}
		//
		return response;
	}

}
