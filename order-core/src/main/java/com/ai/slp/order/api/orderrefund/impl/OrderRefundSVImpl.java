package com.ai.slp.order.api.orderrefund.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.slp.order.api.orderrefund.interfaces.IOrderRefundSV;
import com.ai.slp.order.api.orderrefund.param.OrderRefundRequest;
import com.ai.slp.order.api.orderrefund.param.OrderRefuseRefundRequest;
import com.ai.slp.order.service.business.interfaces.IOrderRefundBusiSV;
import com.alibaba.dubbo.config.annotation.Service;

@Service(validation="true")
@Component
public class OrderRefundSVImpl implements IOrderRefundSV {
	
	@Autowired
	private IOrderRefundBusiSV orderRefundBusiSV;

	@Override
	public BaseResponse partRefund(OrderRefundRequest request) throws BusinessException, SystemException {
		BaseResponse response=new BaseResponse();
		orderRefundBusiSV.partRefund(request);
	    ResponseHeader responseHeader = new ResponseHeader(true,
                ExceptCodeConstants.Special.SUCCESS, "成功");
        response.setResponseHeader(responseHeader);
        return response;
	}

	@Override
	public BaseResponse refuseRefund(OrderRefuseRefundRequest request) throws BusinessException, SystemException {
		BaseResponse response=new BaseResponse();
		orderRefundBusiSV.refuseRefund(request);
	    ResponseHeader responseHeader = new ResponseHeader(true,
                ExceptCodeConstants.Special.SUCCESS, "成功");
        response.setResponseHeader(responseHeader);
        return response;
	}

}
