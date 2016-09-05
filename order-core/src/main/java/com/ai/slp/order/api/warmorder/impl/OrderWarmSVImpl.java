package com.ai.slp.order.api.warmorder.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.slp.order.api.warmorder.interfaces.IOrderWarmSV;
import com.ai.slp.order.api.warmorder.param.OrderWarmDetailRequest;
import com.ai.slp.order.api.warmorder.param.OrderWarmDetailResponse;
import com.ai.slp.order.api.warmorder.param.OrderWarmRequest;
import com.ai.slp.order.api.warmorder.param.OrderWarmResponse;
import com.ai.slp.order.api.warmorder.param.OrderWarmVo;
import com.ai.slp.order.service.business.interfaces.IOrdWarmBusiSV;
import com.ai.slp.order.util.ValidateUtils;
import com.alibaba.dubbo.config.annotation.Service;

@Service(validation = "true")
@Component
public class OrderWarmSVImpl implements IOrderWarmSV {
	@Autowired
	IOrdWarmBusiSV iOrdWarmBusiSV;

	@Override
	public OrderWarmResponse serchWarmOrder(OrderWarmRequest request) throws BusinessException, SystemException {
		// 有效性校验
		ValidateUtils.validateWarmOrdQuery(request);
		OrderWarmResponse response = new OrderWarmResponse();
		PageInfo<OrderWarmVo> pageInfo = iOrdWarmBusiSV.selectWarmOrdPage(request);
		ResponseHeader responseHeader = new ResponseHeader(true, ExceptCodeConstants.Special.SUCCESS, "成功");
		response.setResponseHeader(responseHeader);
		response.setPageInfo(pageInfo);
		return response;
	}

	@Override
	public OrderWarmDetailResponse searchWarmorderDetail(OrderWarmDetailRequest request)
			throws BusinessException, SystemException {
		// 参数校验
		ValidateUtils.validateWarmOrdDetail(request);
		OrderWarmDetailResponse response = new OrderWarmDetailResponse();
		OrderWarmVo orderWarmVo = iOrdWarmBusiSV.selectWarmOrdDetail(request.getTenantId(), request.getOrderId());
		ResponseHeader responseHeader = new ResponseHeader(true, ExceptCodeConstants.Special.SUCCESS, "成功");
		response.setResponseHeader(responseHeader);
		response.setOrderWarmVo(orderWarmVo);
		return response;
	}

}
