package com.ai.slp.order.api.ofcactual.interfaces;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.slp.order.api.ofcactual.param.OfcOrderCreateRequest;


/**
 * ofc实时相关服务
 * @date 2017年1月9日 
 * @author caofz
 */
@Path("/ofc")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
public interface IOfcOrderActualSV {
	
	/**
	 * ofc销售订单创建
	 * @param request
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 * @author caofz
	 * @ApiDocMethod
	 * @ApiCode OFC_ORDERCREATE_001
	 * @RestRelativeURL ofc/orderCreate
	 */
	@POST
	@Path("/orderCreate")
	BaseResponse orderCreate(OfcOrderCreateRequest request) throws BusinessException,SystemException;
}
