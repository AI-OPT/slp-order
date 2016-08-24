package com.ai.slp.order.api.ordercheck.interfaces;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.slp.order.api.ordercheck.param.OrderCheckRequest;

/**
 * 售后订单审核
 * @date 2016年8月10日 
 * @author caofz
 */
@Path("/aftersaleorder")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
public interface IOrderCheckSV {
	
	
	/**
	 * 退货审核
	 * @param request
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 * @author caofz
	 * @ApiDocMethod
	 * @ApiCode 
	 * @RestRelativeURL /aftersaleorder/check
	 */
	@POST
	@Path("/check")
	public BaseResponse check(OrderCheckRequest request) throws BusinessException,SystemException;
	
}
