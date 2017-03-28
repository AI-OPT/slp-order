package com.ai.slp.order.service.business.interfaces;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.slp.order.api.orderrule.param.OrderMonitorBeforResponse;
import com.ai.slp.order.api.orderrule.param.OrderMonitorRequest;
import com.ai.slp.order.api.ordertradecenter.param.OrderTradeCenterRequest;
import com.ai.slp.order.api.ordertradecenter.param.OrderTradeCenterResponse;

public interface IOrdOrderTradeBusiSV {

    public OrderTradeCenterResponse apply(OrderTradeCenterRequest request,	
    		OrderMonitorBeforResponse beforSubmitOrder,
    		OrderMonitorRequest monitorRequest ) throws BusinessException,SystemException;
}
