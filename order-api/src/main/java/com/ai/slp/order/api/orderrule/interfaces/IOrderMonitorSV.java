package com.ai.slp.order.api.orderrule.interfaces;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.slp.order.api.orderrule.param.OrderMonitorBeforResponse;
import com.ai.slp.order.api.orderrule.param.OrderMonitorRequest;
import com.ai.slp.order.api.orderrule.param.OrderMonitorResponse;

/**
 * 订单规则监控服务，提供提交订单前服务调用和提交成功后订单服务调用
 *
 * Date: 2016年9月20日 <br>
 * Copyright (c) 2016 asiainfo.com <br>
 * @author zhangzd
 */
@Path("/orderMonitorService")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
public interface IOrderMonitorSV {
	/**
	 * 订单提交前监控服务
	 * @param request
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 * @author zhangzd
	 * @ApiDocMethod
	 * @ApiCode orderMonitorService-0001
     * @RestRelativeURL orderMonitorService/beforSubmitOrder
     */
	@POST
	@Path("/beforSubmitOrder")
	public OrderMonitorBeforResponse beforSubmitOrder(OrderMonitorRequest request) throws BusinessException, SystemException;
	/**
	 * 订单提交成功后监控服务
	 * @param request
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 * @author zhangzd
	 * @ApiDocMethod
	 * @ApiCode orderMonitorService-0002
     * @RestRelativeURL orderMonitorService/afterSubmitOrder
     */
	@POST
	@Path("/afterSubmitOrder")
	public OrderMonitorResponse afterSubmitOrder(OrderMonitorRequest request) throws BusinessException, SystemException;
}
