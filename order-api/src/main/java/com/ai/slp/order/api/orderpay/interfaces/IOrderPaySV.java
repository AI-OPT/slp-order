package com.ai.slp.order.api.orderpay.interfaces;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.slp.order.api.orderpay.param.OrderPayRequest;
/**
 * 订单收费服务
 * Date: 2016年5月24日 <br>
 * Copyright (c) 2016 asiainfo.com <br>
 * 
 * @author zhangxw
 */
public interface IOrderPaySV {

    /**
     * 订单收费
     * @param request
     * @return
     * @throws BusinessException
     * @throws SystemException
     * @author zhangxw
     * @ApiDocMethod
     */
    public BaseResponse pay(OrderPayRequest request)throws BusinessException,SystemException;
    @interface pay{}

}
