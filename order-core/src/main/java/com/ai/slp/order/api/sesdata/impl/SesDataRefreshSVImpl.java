package com.ai.slp.order.api.sesdata.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.slp.order.api.orderlist.param.BehindQueryOrderListRequest;
import com.ai.slp.order.api.sesdata.interfaces.ISesDataRefreshSV;
import com.ai.slp.order.api.sesdata.param.SesDataByPageRequest;
import com.ai.slp.order.api.sesdata.param.SesDataResponse;
import com.ai.slp.order.service.business.interfaces.search.IOrderIndexBusiSV;
import com.ai.slp.order.util.ValidateUtils;
import com.alibaba.dubbo.config.annotation.Service;
@Service
@Component
public class SesDataRefreshSVImpl implements ISesDataRefreshSV {
	@Autowired
	IOrderIndexBusiSV orderIndexBusiSV;
	@Override
	public SesDataResponse refreshSesData(SesDataByPageRequest request) throws BusinessException, SystemException {
		//参数校验
		ValidateUtils.validateSesDataRequest(request);
		SesDataResponse response = new SesDataResponse();
		//刷新数据
		response = orderIndexBusiSV.insertSesDataByPage(request);
		response.setResponseHeader(new ResponseHeader(true, ExceptCodeConstants.Special.SUCCESS, "数据刷新成功"));
		return response;
	}
	
	
	
	@Override
	public SesDataResponse deleteSesData(BehindQueryOrderListRequest request)
			throws BusinessException, SystemException {
		SesDataResponse response = new SesDataResponse();
		// TODO Auto-generated method stub
		orderIndexBusiSV.deleteSesData(request);
		response.setResponseHeader(new ResponseHeader(true, ExceptCodeConstants.Special.SUCCESS, "数据刷新成功"));
		return response;
	}
}
