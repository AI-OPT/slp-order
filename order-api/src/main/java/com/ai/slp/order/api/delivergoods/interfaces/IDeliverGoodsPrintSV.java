package com.ai.slp.order.api.delivergoods.interfaces;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.slp.order.api.delivergoods.param.DeliverGoodsPrintInfosRequest;
import com.ai.slp.order.api.delivergoods.param.DeliverGoodsPrintRequest;
import com.ai.slp.order.api.delivergoods.param.DeliverGoodsPrintResponse;

/**
 * 发货单打印服务
 * @author caofz
 *
 */
@Path("/deliverGoods")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
public interface IDeliverGoodsPrintSV {
	

	/**
	 * 发货打印查看
	 * @param request
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 * @author caofz
	 * @ApiDocMethod
	 * @ApiCode ORDER_DELIVERGOODS_001
	 * @RestRelativeURL deliverGoods/query
	 */
	@POST
	@Path("/query")
	public DeliverGoodsPrintResponse query(DeliverGoodsPrintRequest request) throws BusinessException,SystemException;
	
	
	/**
	 * 发货打印
	 * @param request
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 * @author caofz
	 * @ApiDocMethod
	 * @ApiCode ORDER_DELIVERGOODS_002
	 * @RestRelativeURL deliverGoods/print
	 */
	@POST
	@Path("/print")
	public BaseResponse print(DeliverGoodsPrintInfosRequest request) throws BusinessException,SystemException;
	
}
