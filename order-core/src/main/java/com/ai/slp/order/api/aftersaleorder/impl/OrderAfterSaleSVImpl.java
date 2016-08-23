package com.ai.slp.order.api.aftersaleorder.impl;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.slp.order.api.aftersaleorder.interfaces.IOrderAfterSaleSV;
import com.ai.slp.order.api.aftersaleorder.param.OrderReturnRequest;

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
