package com.ai.slp.order.api.orderconfirm.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.slp.order.api.orderconfirm.interfaces.IOrderConfirmSV;
import com.ai.slp.order.api.orderconfirm.param.OrderConfirmRequest;
import com.ai.slp.order.service.business.interfaces.IOrderConfirmBusiSV;
import com.alibaba.dubbo.config.annotation.Service;

@Service(validation = "true")
@Component
public class OrderConfirmSVImpl implements IOrderConfirmSV {
	
	@Autowired
	private IOrderConfirmBusiSV orderConfirmBusiSV;

	@Override
	public BaseResponse confirm(OrderConfirmRequest request) throws BusinessException, SystemException {
		BaseResponse response =new BaseResponse();
		orderConfirmBusiSV.confirm(request);
        ResponseHeader responseHeader = new ResponseHeader(true,
                ExceptCodeConstants.Special.SUCCESS, "成功");
        response.setResponseHeader(responseHeader);
        return response;
	}
}
