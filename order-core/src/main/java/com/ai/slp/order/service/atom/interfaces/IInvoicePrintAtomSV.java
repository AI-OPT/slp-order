package com.ai.slp.order.service.atom.interfaces;

import com.ai.slp.order.dao.mapper.bo.OrdOdInvoice;

public interface IInvoicePrintAtomSV {
	
	 int insertSelective(OrdOdInvoice record);
}
