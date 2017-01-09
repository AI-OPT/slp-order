package com.ai.slp.order.api.synchronize.interfaces;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.slp.order.api.synchronize.params.OrderSynchronizeVo;

/**
 * 订单同步服务
 */
@Path("/ordersynchronizeservice")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
public interface ISynchronizeSV {

	/**
	 * 订单同步服务
	 * 
	 * @param request
	 * @throws SystemException
	 * @author zhangqiang7
	 * @ApiCode 	
     * @RestRelativeURL ordersynchronizeservice/orderSynchronize
	 * 
	 */
	@POST
	@Path("/orderSynchronize")
	public BaseResponse orderSynchronize(OrderSynchronizeVo request) throws BusinessException, SystemException;

}
