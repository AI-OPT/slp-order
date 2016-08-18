package com.ai.slp.order.service.atom.impl;


import org.springframework.stereotype.Component;

import com.ai.slp.order.dao.mapper.bo.OrdOdInvoice;
import com.ai.slp.order.dao.mapper.factory.MapperFactory;
import com.ai.slp.order.service.atom.interfaces.IInvoicePrintAtomSV;
@Component
public class InvoicePrintAtomSVImpl implements IInvoicePrintAtomSV{

	@Override
	public int insertSelective(OrdOdInvoice record) {
		return MapperFactory.getOrdOdInvoiceMapper().insertSelective(record);
	}
	

}
