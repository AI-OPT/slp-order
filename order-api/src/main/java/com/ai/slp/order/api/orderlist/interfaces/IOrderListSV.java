package com.ai.slp.order.api.orderlist.interfaces;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
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
	 */
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
	 */
	QueryOrderResponse queryOrder(QueryOrderRequest orderRequest) throws BusinessException, SystemException;
}
