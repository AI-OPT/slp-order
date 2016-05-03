package com.ai.slp.order.api.orderlist.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.slp.order.api.orderlist.interfaces.IOrderListSV;
import com.ai.slp.order.api.orderlist.param.QueryOrderListRequest;
import com.ai.slp.order.api.orderlist.param.QueryOrderListResponse;
import com.ai.slp.order.service.business.interfaces.IOrdOrderBusiSV;

@Service
@Transactional
public class OrderListSVImpl implements IOrderListSV {

    @Autowired
    private IOrdOrderBusiSV ordOrderBusiSV;

    @Override
    public QueryOrderListResponse queryOrderList(QueryOrderListRequest orderListRequest)
            throws BusinessException, SystemException {
        return ordOrderBusiSV.queryOrderList(orderListRequest);
    }

}
