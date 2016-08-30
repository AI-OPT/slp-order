package com.ai.slp.order.api.orderconfirm.interfaces;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.slp.order.api.orderconfirm.param.OrderConfirmRequest;

/**
 * 订单确认
 * @date 2016年8月10日 
 * @author caofz
 */
@Path("/order")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
public interface IOrderConfirmSV {
	
	/**
	 * 确认收货服务
	 * @param request
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 * @author caofz
	 * @ApiDocMethod
	 * @ApiCode 
	 * @RestRelativeURL /order/confirm
	 */
	@POST
	@Path("/confirm")
	public BaseResponse confirm(OrderConfirmRequest request) throws BusinessException,SystemException;

}
