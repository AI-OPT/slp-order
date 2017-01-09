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
	//运费模版添加
	public void add(FreightTemplateRequest request) throws BusinessException, SystemException;
	//运费模版查询
	public QueryFreightTemplateResponse query(QueryFreightTemplateRequest request) throws BusinessException, SystemException;
	//运费模版更新
	public void update(FreightTemplateUpdateRequest request) throws BusinessException, SystemException;
	//运费模版服务删除
	public void delete(FreightTemplateDeleteRequest request); 
	//运费模版明细删除
	public void deleteFreightTemplateProd(FreightTemplateProdRequest request);
}
