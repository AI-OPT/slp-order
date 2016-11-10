package com.ai.slp.order.service.atom.interfaces;

import java.util.List;

import com.ai.slp.order.dao.mapper.bo.OrdOdInvoice;
import com.ai.slp.order.dao.mapper.bo.OrdOdInvoiceCriteria;

public interface IOrdOdInvoiceAtomSV {
	
	int insertSelective(OrdOdInvoice record);
	
	List<OrdOdInvoice> selectByExample(OrdOdInvoiceCriteria example);
	
	OrdOdInvoice selectByPrimaryKey(long orderId);
	
	int countByExample(OrdOdInvoiceCriteria example);
	
	int updateByPrimaryKey(OrdOdInvoice record);
	
	List<OrdOdInvoice> selectByCondition(String subFlag,Integer pageNo,Integer pageSize,Long orderId,
            String tenantId, String invoiceTitle, String invoiceStatus);
	
	int count(String subFlag,Long orderId,String tenantId, 
			String invoiceTitle, String invoiceStatus);

}
