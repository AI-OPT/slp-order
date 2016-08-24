package com.ai.slp.order.api.ordercheck.impl;

import org.springframework.stereotype.Component;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.slp.order.api.ordercheck.interfaces.IOrderCheckSV;
import com.ai.slp.order.api.ordercheck.param.OrderCheckRequest;
import com.alibaba.dubbo.config.annotation.Service;
@Service(validation = "true")
@Component
public class OrderCheckSVImpl implements IOrderCheckSV {

	@Override
	public BaseResponse check(OrderCheckRequest request) throws BusinessException, SystemException {
		return null;
	}

}
