package com.ai.slp.order.api.sesdata.interfaces;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.slp.order.api.sesdata.param.SesDataRequest;

@Path("/sesData")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
public interface ISesDataUpdateSV {
	/**
	 * 
	 * @param request
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 * @author caofz
	 * @ApiCode SES_001
	 * @ApiDocMethod
	 * @RestRelativeURL sesData/updateSesData
	 */
	@POST
	@Path("/updateSesData")
	BaseResponse updateSesData(SesDataRequest request) throws BusinessException, SystemException;
}
