package com.ai.slp.order.service.atom.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.slp.order.dao.mapper.attach.InvoicePrintAttachMapper;
import com.ai.slp.order.dao.mapper.attach.OrdOrderProdAttach;
import com.ai.slp.order.service.atom.interfaces.IInvoicePrintAtomSV;
@Component
public class InvoicePrintAtomSVImpl implements IInvoicePrintAtomSV{
	
	@Autowired
	InvoicePrintAttachMapper invoicePrintAttachMapper;

	@Override
	public List<OrdOrderProdAttach> query(String userId, String tenantId, String skuId, String routeId, String state,
			Timestamp timeBefore, Timestamp timeAfter) {
		return invoicePrintAttachMapper.query(userId, tenantId,skuId, routeId, state,timeBefore,timeAfter);
	}

}
