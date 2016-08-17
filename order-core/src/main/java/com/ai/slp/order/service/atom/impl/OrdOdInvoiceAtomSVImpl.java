package com.ai.slp.order.service.atom.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ai.slp.order.dao.mapper.bo.OrdOdInvoice;
import com.ai.slp.order.dao.mapper.bo.OrdOdInvoiceCriteria;
import com.ai.slp.order.dao.mapper.factory.MapperFactory;
import com.ai.slp.order.service.atom.interfaces.IOrdOdInvoiceAtomSV;

@Component
public class OrdOdInvoiceAtomSVImpl implements IOrdOdInvoiceAtomSV{

	@Override
	public int insertSelective(OrdOdInvoice record) {
		return MapperFactory.getOrdOdInvoiceMapper().insertSelective(record);
	}

	@Override
	public List<OrdOdInvoice> selectByExample(OrdOdInvoiceCriteria example) {
		return MapperFactory.getOrdOdInvoiceMapper().selectByExample(example);
	}

	@Override
	public OrdOdInvoice selectByPrimaryKey(long orderId) {
		return MapperFactory.getOrdOdInvoiceMapper().selectByPrimaryKey(orderId);
	}
}
