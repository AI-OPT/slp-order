package com.ai.slp.order.api.orderlist.interfaces;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.slp.order.api.orderlist.param.QueryApiOrderRequest;
import com.ai.slp.order.api.orderlist.param.QueryOrderListRequest;
import com.ai.slp.order.api.orderlist.param.QueryOrderListResponse;
import com.ai.slp.order.api.orderlist.param.QueryOrderRequest;
import com.ai.slp.order.api.orderlist.param.QueryOrderResponse;

/**
 * 查询订单列表 Date: 2016年5月3日 <br>
 * Copyright (c) 2016 asiainfo.com <br>
 * 
 * @author zhangqiang7
 */
@Path("/orderlist")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
public interface IOrderListSV {

	/**
	 * 查询订单列表
	 * 
	 * @param orderListRequest
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 * @author zhangqiang7
	 * @ApiDocMethod
     * @ApiCode ORDERQUERY_001
     * @RestRelativeURL orderlist/queryOrderList
	 */
	@POST
	@Path("/queryOrderList")
	QueryOrderListResponse queryOrderList(QueryOrderListRequest orderListRequest) throws BusinessException, SystemException;

	/**
	 * 订单详情查询
	 * @param orderRequest
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 * @author jiaxs
	 * @ApiDocMethod
     * @ApiCode ORDERQUERY_002
     * @RestRelativeURL orderlist/queryOrder
	 */
	@POST
	@Path("/queryOrder")
	QueryOrderResponse queryOrder(QueryOrderRequest orderRequest) throws BusinessException, SystemException;
	
	/**
     * 订单单个查询(api)
     * @param orderRequest
     * @return
     * @throws BusinessException
     * @throws SystemException
     * @author jiaxs
     * @ApiDocMethod
     * @ApiCode ORDERQUERY_003
     * @RestRelativeURL orderlist/queryApiOrder
     */
    @POST
    @Path("/queryOrder")
    QueryOrderResponse queryApiOrder(QueryApiOrderRequest orderRequest) throws BusinessException, SystemException;
	
}
