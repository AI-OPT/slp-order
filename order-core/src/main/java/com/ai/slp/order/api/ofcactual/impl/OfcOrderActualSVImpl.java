package com.ai.slp.order.api.ofcactual.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.slp.order.api.ofcactual.interfaces.IOfcOrderActualSV;
import com.ai.slp.order.api.ofcactual.param.OfcOrderCreateRequest;
import com.ai.slp.order.service.business.interfaces.IOfcOrderActualBusiSV;

public class OfcOrderActualSVImpl implements IOfcOrderActualSV {
	
	@Autowired
	private IOfcOrderActualBusiSV ofcOrderActualBusiSV;

	@Override
	public BaseResponse orderCreate(OfcOrderCreateRequest request) throws BusinessException, SystemException {
		BaseResponse response = new BaseResponse();
		ofcOrderActualBusiSV.orderCreate(request);
		ResponseHeader header=new ResponseHeader(true, ExceptCodeConstants.Special.SUCCESS, "成功");
		response.setResponseHeader(header);
		return response;
	}
}
