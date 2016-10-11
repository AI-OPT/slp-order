package com.ai.slp.order.api.aftersaleorder.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.slp.order.api.aftersaleorder.interfaces.IOrderAfterSaleSV;
import com.ai.slp.order.api.aftersaleorder.param.OrderOFCBackRequest;
import com.ai.slp.order.api.aftersaleorder.param.OrderReturnRequest;
import com.ai.slp.order.service.business.interfaces.IOrderAfterSaleBusiSV;
import com.alibaba.dubbo.config.annotation.Service;

@Service(validation = "true")
@Component
public class OrderAfterSaleSVImpl implements IOrderAfterSaleSV {
	
	@Autowired
	private IOrderAfterSaleBusiSV orderAfterSaleBusiSV;
	
	@Override
	public BaseResponse back(OrderReturnRequest request) throws BusinessException, SystemException {
		BaseResponse response =new BaseResponse();
		orderAfterSaleBusiSV.back(request);
        ResponseHeader responseHeader = new ResponseHeader(true,
                ExceptCodeConstants.Special.SUCCESS, "成功");
        response.setResponseHeader(responseHeader);
        return response;
	}

	@Override
	public BaseResponse exchange(OrderReturnRequest request) throws BusinessException, SystemException {
		BaseResponse response =new BaseResponse();
		orderAfterSaleBusiSV.exchange(request);
        ResponseHeader responseHeader = new ResponseHeader(true,
                ExceptCodeConstants.Special.SUCCESS, "成功");
        response.setResponseHeader(responseHeader);
        return response;
	}

	@Override
	public BaseResponse refund(OrderReturnRequest request) throws BusinessException, SystemException {
		BaseResponse response =new BaseResponse();
		orderAfterSaleBusiSV.refund(request);
        ResponseHeader responseHeader = new ResponseHeader(true,
                ExceptCodeConstants.Special.SUCCESS, "成功");
        response.setResponseHeader(responseHeader);
        return response;
	}

	@Override
	public BaseResponse backStateOFC(OrderOFCBackRequest request) throws BusinessException, SystemException {
		BaseResponse response =new BaseResponse();
		orderAfterSaleBusiSV.backStateOFC(request);
        ResponseHeader responseHeader = new ResponseHeader(true,
                ExceptCodeConstants.Special.SUCCESS, "成功");
        response.setResponseHeader(responseHeader);
        return response;
	}
	
}
