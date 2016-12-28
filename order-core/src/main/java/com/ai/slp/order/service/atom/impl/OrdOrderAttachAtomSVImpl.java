package com.ai.slp.order.service.atom.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.slp.order.api.orderlist.param.BehindQueryOrderListRequest;
import com.ai.slp.order.dao.mapper.attach.BehindOrdOrderAttach;
import com.ai.slp.order.dao.mapper.attach.OrdOrderAttachMapper;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAttachAtomSV;

@Component
public class OrdOrderAttachAtomSVImpl implements IOrdOrderAttachAtomSV {

    @Autowired
    private OrdOrderAttachMapper ordOrderAttachMapper;

	@Override
	public int behindQueryCount(BehindQueryOrderListRequest orderListRequest, String states) {
		 return ordOrderAttachMapper.getBehindCount(states, 
				 	orderListRequest.getOrderId(),orderListRequest.getChlId(),
				 	orderListRequest.getRouteId(), orderListRequest.getUserName(),
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
				orderListRequest.getRouteId(),orderListRequest.getUserName(), 
				orderListRequest.getContactTel(),orderListRequest.getTenantId(), 
				orderListRequest.getDeliveryFlag(),
                orderListRequest.getOrderTimeBegin(),orderListRequest.getOrderTimeEnd());
	}

}
