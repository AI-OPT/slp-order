package com.ai.slp.order.api.freighttemplate.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.slp.order.api.freighttemplate.interfaces.IFreightTemplateSV;
import com.ai.slp.order.api.freighttemplate.param.FreightTemplateDeleteRequest;
import com.ai.slp.order.api.freighttemplate.param.FreightTemplateRequest;
import com.ai.slp.order.api.freighttemplate.param.FreightTemplateUpdateRequest;
import com.ai.slp.order.api.freighttemplate.param.QueryFreightTemplateRequest;
import com.ai.slp.order.api.freighttemplate.param.QueryFreightTemplateResponse;
import com.ai.slp.order.service.business.interfaces.IFreightTemplateBusiSV;
import com.alibaba.dubbo.config.annotation.Service;

@Service(validation = "true")
@Component
public class FreightTemplateSVImpl implements IFreightTemplateSV{
	
	@Autowired
	private IFreightTemplateBusiSV freightTemplateBusiSV;
	
	@Override
	public BaseResponse add(FreightTemplateRequest request) throws BusinessException, SystemException {
		BaseResponse response=new BaseResponse();
		freightTemplateBusiSV.add(request);
		ResponseHeader responseHeader = new ResponseHeader(true,
	                ExceptCodeConstants.Special.SUCCESS, "成功");
		response.setResponseHeader(responseHeader);
		return response;
	}

	@Override
	public QueryFreightTemplateResponse query(QueryFreightTemplateRequest request) throws BusinessException, SystemException {
		QueryFreightTemplateResponse response = freightTemplateBusiSV.query(request);
		ResponseHeader responseHeader = new ResponseHeader(true,
                ExceptCodeConstants.Special.SUCCESS, "成功");
		response.setResponseHeader(responseHeader);
		return response;
	}

	@Override
	public BaseResponse update(FreightTemplateUpdateRequest request) throws BusinessException, SystemException {
		BaseResponse response=new BaseResponse();
		freightTemplateBusiSV.update(request);
		ResponseHeader responseHeader = new ResponseHeader(true,
	                ExceptCodeConstants.Special.SUCCESS, "成功");
		response.setResponseHeader(responseHeader);
		return response;
	}

	@Override
	public BaseResponse delete(FreightTemplateDeleteRequest request) throws BusinessException, SystemException {
		BaseResponse response=new BaseResponse();
		freightTemplateBusiSV.delete(request);
		ResponseHeader responseHeader = new ResponseHeader(true,
	                ExceptCodeConstants.Special.SUCCESS, "成功");
		response.setResponseHeader(responseHeader);
		return response;
	}
}
