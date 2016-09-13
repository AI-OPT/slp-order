package com.ai.slp.order.api.freighttemplate.interfaces;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.slp.order.api.freighttemplate.param.FreightTemplateDeleteRequest;
import com.ai.slp.order.api.freighttemplate.param.FreightTemplateProdRequest;
import com.ai.slp.order.api.freighttemplate.param.FreightTemplateRequest;
import com.ai.slp.order.api.freighttemplate.param.FreightTemplateUpdateRequest;
import com.ai.slp.order.api.freighttemplate.param.QueryFreightTemplateRequest;
import com.ai.slp.order.api.freighttemplate.param.QueryFreightTemplateResponse;

/**
 * 运费模版服务
 * @date 2016年8月3日 
 * @author caofz
 */
@Path("/freighttemplate")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
public interface IFreightTemplateSV {
		
	   /**
	    * 运费模版添加
	    * @param request
	    * @return
	    * @throws BusinessException
	    * @throws SystemException
	    * @author caofz
	    * @ApiDocMethod
	    * @ApiCode FREIGHTTEMPLATE_001
	    * @RestRelativeURL freighttemplate/add
	    */
	   @POST
	   @Path("/add")
	   public BaseResponse add(FreightTemplateRequest request) throws BusinessException, SystemException;
	   
	   
	   /**
	    * 运费模版查询
	    * @throws BusinessException
	    * @throws SystemException
	    * @author caofz
	    * @ApiDocMethod
	    * @ApiCode FREIGHTTEMPLATE_002
	    * @RestRelativeURL freighttemplate/query
	    */
	   @POST
	   @Path("/query")
	   public QueryFreightTemplateResponse query(QueryFreightTemplateRequest request) throws BusinessException, SystemException;
	   
	   /**
	    * 运费模版的修改
	    * @return
	    * @throws BusinessException
	    * @throws SystemException
	    * @author caofz
	    * @ApiDocMethod
	    * @ApiCode FREIGHTTEMPLATE_003
	    * @RestRelativeURL freighttemplate/update
	    */
	   @POST
	   @Path("/update")
	   public BaseResponse update(FreightTemplateUpdateRequest request) throws BusinessException, SystemException;
	   
	   
	   /**
	    * 运费模版的删除
	    * @return
	    * @throws BusinessException
	    * @throws SystemException
	    * @author caofz
	    * @ApiDocMethod
	    * @ApiCode FREIGHTTEMPLATE_004
	    * @RestRelativeURL freighttemplate/delete
	    */
	   @POST
	   @Path("/delete")
	   public BaseResponse delete(FreightTemplateDeleteRequest request) throws BusinessException,SystemException;
	   
	   /**
	    * 运费模版明细的删除
	    * @return
	    * @throws BusinessException
	    * @throws SystemException
	    * @author caofz
	    * @ApiDocMethod
	    * @ApiCode FREIGHTTEMPLATE_005
	    * @RestRelativeURL freighttemplate/deleteFreightTemplateProd
	    */
	   @POST
	   @Path("/deleteFreightTemplateProd")
	   public BaseResponse deleteFreightTemplateProd(FreightTemplateProdRequest request) throws BusinessException,SystemException;
	   
}
