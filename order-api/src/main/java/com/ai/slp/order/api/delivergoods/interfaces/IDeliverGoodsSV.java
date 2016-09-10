package com.ai.slp.order.api.delivergoods.interfaces;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.slp.order.api.delivergoods.param.DeliverGoodsRequest;

/**
 * 订单发货服务
 * @author caofz
 *
 */
@Path("/order")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
public interface IDeliverGoodsSV {
	
	/**
	 * 发货
	 * @param request
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 * @author caofz
	 * @ApiDocMethod
	 * @ApiCode ORDER_DELIVER_001
	 * @RestRelativeURL order/deliverGoods
	 */
	@POST
	@Path("/deliverGoods")
	public BaseResponse deliverGoods(DeliverGoodsRequest request) throws BusinessException,SystemException;

}
