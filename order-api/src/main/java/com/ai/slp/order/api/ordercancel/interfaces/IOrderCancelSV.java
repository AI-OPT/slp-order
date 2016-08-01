package com.ai.slp.order.api.ordercancel.interfaces;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.slp.order.api.ordercancel.param.OrderCancelRequest;

/**
 * 订单关闭 Date: 2016年6月24日 <br>
 * Copyright (c) 2016 asiainfo.com <br>
 * 
 * @author zhangxw
 */
@Path("/ordercancel")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
public interface IOrderCancelSV {

   /**
    * 订单关闭
    * @return
    * @throws BusinessException
    * @throws SystemException
    * @author zhangxw
    * @ApiDocMethod
    * @ApiCode ORDERCANCEL_001
    * @RestRelativeURL ordercancel/noPayOrderCancel
    */
    @POST
    @Path("/noPayOrderCancel")
    BaseResponse noPayOrderCancel() throws BusinessException, SystemException;
   
    /**
     * 手动关闭订单
     * @param request
     * @return
     * @throws BusinessException
     * @throws SystemException
     */
    @POST
    @Path("/handCancelNoPayOrder")
    public BaseResponse handCancelNoPayOrder(OrderCancelRequest request) throws BusinessException, SystemException;
    

}
