package com.ai.slp.order.api.ordermodify.interfaces;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.slp.order.api.ordermodify.param.OrdRequest;
@Path("/order")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
public interface IOrderModifySV {
	/**
	 * 订单状态修改
	 * @param request
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 * @author zhanglh
	 * @ApiCode ORDER_STATEMODIFY_001
	 * @RestRelativeURL order/stateModify
	 */
	 @POST
	 @Path("/stateModify")
	 public BaseResponse modify(OrdRequest request)throws BusinessException,SystemException; 
}
