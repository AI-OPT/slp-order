package com.ai.slp.order.api.ordercheck.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.slp.order.api.ordercheck.interfaces.IOrderCheckSV;
import com.ai.slp.order.api.ordercheck.param.OrderCheckRequest;
import com.ai.slp.order.service.business.interfaces.IOrderCheckBusiSV;
import com.ai.slp.order.util.ValidateUtils;
import com.alibaba.dubbo.config.annotation.Service;

@Service(validation = "true")
@Component
public class OrderCheckSVImpl implements IOrderCheckSV {
	
	@Autowired
	private IOrderCheckBusiSV orderCheckBusiSV;
	@Override
	public BaseResponse check(OrderCheckRequest request) throws BusinessException, SystemException {
		/* 参数校验*/
		ValidateUtils.validateOrderCheckRequest(request);
		BaseResponse response =new BaseResponse();
		orderCheckBusiSV.check(request);
        ResponseHeader responseHeader = new ResponseHeader(true,
                ExceptCodeConstants.Special.SUCCESS, "成功");
        response.setResponseHeader(responseHeader);
        return response;
	}

}
