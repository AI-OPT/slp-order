package com.ai.slp.order.service.business.interfaces;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.slp.order.api.freighttemplate.param.FreightTemplateDeleteRequest;
import com.ai.slp.order.api.freighttemplate.param.FreightTemplateProdRequest;
import com.ai.slp.order.api.freighttemplate.param.FreightTemplateRequest;
import com.ai.slp.order.api.freighttemplate.param.FreightTemplateUpdateRequest;
import com.ai.slp.order.api.freighttemplate.param.QueryFreightTemplateRequest;
import com.ai.slp.order.api.freighttemplate.param.QueryFreightTemplateResponse;

public interface IFreightTemplateBusiSV {
	
	public void add(FreightTemplateRequest request) throws BusinessException, SystemException;
	
	public QueryFreightTemplateResponse query(QueryFreightTemplateRequest request) throws BusinessException, SystemException;
	
	public void update(FreightTemplateUpdateRequest request) throws BusinessException, SystemException;

	public void delete(FreightTemplateDeleteRequest request); 
	
	public void deleteFreightTemplateProd(FreightTemplateProdRequest request);
}
