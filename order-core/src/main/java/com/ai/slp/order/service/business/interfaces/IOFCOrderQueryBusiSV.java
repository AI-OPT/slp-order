package com.ai.slp.order.service.business.interfaces;

import org.springframework.stereotype.Component;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.slp.order.api.ofcorderquery.param.OFCOrderQueryRequest;
import com.ai.slp.order.api.ofcorderquery.param.OFCOrderQueryResponse;

@Component
public interface IOFCOrderQueryBusiSV {
	
	public OFCOrderQueryResponse query(OFCOrderQueryRequest request) throws BusinessException, SystemException;

}
