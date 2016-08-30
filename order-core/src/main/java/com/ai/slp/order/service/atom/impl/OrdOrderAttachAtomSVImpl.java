package com.ai.slp.order.service.atom.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.slp.order.api.orderlist.param.BehindQueryOrderListRequest;
import com.ai.slp.order.api.orderlist.param.QueryOrderListRequest;
import com.ai.slp.order.dao.mapper.attach.BehindOrdOrderAttach;
import com.ai.slp.order.dao.mapper.attach.OrdOrderAttach;
import com.ai.slp.order.dao.mapper.attach.OrdOrderAttachMapper;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAttachAtomSV;

@Component
public class OrdOrderAttachAtomSVImpl implements IOrdOrderAttachAtomSV {

    @Autowired
    private OrdOrderAttachMapper ordOrderAttachMapper;

    @Override
    public List<OrdOrderAttach> queryOrderBySearch(String subFlag,
            QueryOrderListRequest orderListRequest, String states) {
        return ordOrderAttachMapper.getOrdOrder(subFlag, (orderListRequest.getPageNo() - 1)
                * orderListRequest.getPageSize(), orderListRequest.getPageSize(),
                orderListRequest.getUserId(), orderListRequest.getOrderType(),
                orderListRequest.getOrderId(), orderListRequest.getPayStyle(),
                orderListRequest.getTenantId(), states, orderListRequest.getOrderTimeBegin(),
                orderListRequest.getOrderTimeEnd());
    }

    @Override
    public int queryCount(String subFlag, QueryOrderListRequest orderListRequest, String states) {
        return ordOrderAttachMapper.getCount(subFlag, orderListRequest.getUserId(),
                orderListRequest.getOrderType(), orderListRequest.getOrderId(),
                orderListRequest.getPayStyle(), orderListRequest.getTenantId(), states,
                orderListRequest.getOrderTimeBegin(), orderListRequest.getOrderTimeEnd());
    }

	@Override
	public int behindQueryCount(BehindQueryOrderListRequest orderListRequest, String states) {
		 return ordOrderAttachMapper.getBehindCount(states, 
				 	orderListRequest.getOrderId(),orderListRequest.getChlId(),
				 	orderListRequest.getRouteId(), orderListRequest.getUserId(),
				 	orderListRequest.getContactTel(),orderListRequest.getTenantId(),
	                orderListRequest.getDeliveryFlag(),
	                orderListRequest.getOrderTimeBegin(), orderListRequest.getOrderTimeEnd());
	}

	@Override
	public List<BehindOrdOrderAttach> behindQueryOrderBySearch(BehindQueryOrderListRequest orderListRequest, 
			String states) {
		return ordOrderAttachMapper.getBehindOrdOrder((orderListRequest.getPageNo() - 1)* orderListRequest.getPageSize(), 
				orderListRequest.getPageSize(),states,
				orderListRequest.getOrderId(),orderListRequest.getChlId(),
				orderListRequest.getRouteId(),orderListRequest.getUserId(), 
				orderListRequest.getContactTel(),orderListRequest.getTenantId(), 
				orderListRequest.getDeliveryFlag(),
                orderListRequest.getOrderTimeBegin(),orderListRequest.getOrderTimeEnd());
	}

}
