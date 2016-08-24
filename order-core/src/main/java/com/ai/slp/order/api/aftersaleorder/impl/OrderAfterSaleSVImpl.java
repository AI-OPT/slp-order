package com.ai.slp.order.api.aftersaleorder.impl;

import org.springframework.stereotype.Component;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.slp.order.api.aftersaleorder.interfaces.IOrderAfterSaleSV;
import com.ai.slp.order.api.aftersaleorder.param.OrderReturnRequest;
import com.alibaba.dubbo.config.annotation.Service;
@Service(validation = "true")
@Component
public class OrderAfterSaleSVImpl implements IOrderAfterSaleSV {

	@Override
	public BaseResponse back(OrderReturnRequest request) throws BusinessException, SystemException {
		return null;
	}

	@Override
	public BaseResponse exchange(OrderReturnRequest request) throws BusinessException, SystemException {
		return null;
	}

	@Override
	public BaseResponse refund(OrderReturnRequest request) throws BusinessException, SystemException {
		return null;
	}

}
