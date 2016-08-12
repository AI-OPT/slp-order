package com.ai.slp.order.service.atom.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.slp.order.dao.mapper.attach.DeliveryOrderPrintAttachMapper;
import com.ai.slp.order.dao.mapper.attach.OrdOrderProdAttach;
import com.ai.slp.order.service.atom.interfaces.IDeliveryOrderPrintAtomSV;

@Component
public class DeliveryOrderPrintAtomSVImpl implements IDeliveryOrderPrintAtomSV {
	
	@Autowired
	DeliveryOrderPrintAttachMapper deliveryOrderPrintAttachMapper;

	@Override
	public List<OrdOrderProdAttach> query(String userId,String tenantId, String skuId, String routeId, 
			String state,Timestamp timeBefore,Timestamp timeAfter) {
		return deliveryOrderPrintAttachMapper.query(userId, tenantId,skuId, routeId, state,timeBefore,timeAfter);
	}

}
