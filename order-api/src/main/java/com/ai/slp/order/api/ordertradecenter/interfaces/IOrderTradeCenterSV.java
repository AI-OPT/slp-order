package com.ai.slp.order.api.ordertradecenter.interfaces;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.slp.order.api.ordertradecenter.param.OrderTradeCenterRequest;
import com.ai.slp.order.api.ordertradecenter.param.OrderTradeCenterResponse;

/**
 * 订单提交核心服务 Date: 2016年5月13日 <br>
 * Copyright (c) 2016 asiainfo.com <br>
 * 
 * @author zhangxw
 */
@Path("/ordertrade")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
public interface IOrderTradeCenterSV {

    /**
     * 订单提交
     * 
     * @param request
     * @return
     * @throws BusinessException
     * @throws SystemException
     * @author zhangxw
     * @ApiDocMethod
     * @ApiCode ORDER_APPLY_001
     * @RestRelativeURL ordertrade/apply
     */
    @POST
    @Path("/apply")
    public OrderTradeCenterResponse apply(OrderTradeCenterRequest request)
            throws BusinessException, SystemException;
}
