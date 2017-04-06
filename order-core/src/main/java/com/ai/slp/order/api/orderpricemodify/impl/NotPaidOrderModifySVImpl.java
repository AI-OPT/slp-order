package com.ai.slp.order.api.orderpricemodify.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.slp.order.api.orderpricemodify.interfaces.INotPaidOrderModifySV;
import com.ai.slp.order.api.orderpricemodify.param.OrderModifyRequest;
import com.ai.slp.order.service.business.interfaces.INotPaidOrderModifyBusiSV;
import com.ai.slp.order.util.ValidateUtils;
import com.alibaba.dubbo.config.annotation.Service;

@Service
@Component
public class NotPaidOrderModifySVImpl implements INotPaidOrderModifySV{
	@Autowired
	private INotPaidOrderModifyBusiSV notPaidOrderModifyBusiSV;
	@Override
	public BaseResponse modify(OrderModifyRequest request) throws BusinessException, SystemException {
		BaseResponse response=new BaseResponse();
		/* 1.检验参数*/
		ValidateUtils.validateNotPaidModifyRequest(request);
		/* 2.修改金额*/
		notPaidOrderModifyBusiSV.modify(request);
		ResponseHeader responseHeader = new ResponseHeader(true,
                ExceptCodeConstants.Special.SUCCESS, "成功");
		response.setResponseHeader(responseHeader);
		return response;
	}

}
