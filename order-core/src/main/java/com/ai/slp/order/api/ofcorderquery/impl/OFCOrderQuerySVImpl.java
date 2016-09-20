package com.ai.slp.order.api.ofcorderquery.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.slp.order.api.ofcorderquery.interfaces.IOFCOrderQuerySV;
import com.ai.slp.order.api.ofcorderquery.param.OFCOrderQueryRequest;
import com.ai.slp.order.api.ofcorderquery.param.OFCOrderQueryResponse;
import com.ai.slp.order.service.business.interfaces.IOFCOrderQueryBusiSV;

public class OFCOrderQuerySVImpl implements IOFCOrderQuerySV {
	
	@Autowired
	private IOFCOrderQueryBusiSV oFCOrderQueryBusiSV;
	
	@Override
	public OFCOrderQueryResponse query(OFCOrderQueryRequest request) throws BusinessException, SystemException {
		OFCOrderQueryResponse response = oFCOrderQueryBusiSV.query(request);
		ResponseHeader header=new ResponseHeader(true, ExceptCodeConstants.Special.SUCCESS, "成功");
		response.setResponseHeader(header);
		return response;
	}
}
