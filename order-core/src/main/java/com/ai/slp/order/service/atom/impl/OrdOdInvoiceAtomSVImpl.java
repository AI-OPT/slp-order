package com.ai.slp.order.service.atom.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.slp.order.dao.mapper.attach.OrdOdInvoiceAttachMapper;
import com.ai.slp.order.dao.mapper.bo.OrdOdInvoice;
import com.ai.slp.order.dao.mapper.bo.OrdOdInvoiceCriteria;
import com.ai.slp.order.dao.mapper.factory.MapperFactory;
import com.ai.slp.order.service.atom.interfaces.IOrdOdInvoiceAtomSV;

@Component
public class OrdOdInvoiceAtomSVImpl implements IOrdOdInvoiceAtomSV{
	
	@Autowired
	private OrdOdInvoiceAttachMapper ordOdInvoiceAttachMapper;

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

	@Override
	public int countByExample(OrdOdInvoiceCriteria example) {
		return MapperFactory.getOrdOdInvoiceMapper().countByExample(example);
	}

	@Override
	public int updateByPrimaryKey(OrdOdInvoice record) {
		return MapperFactory.getOrdOdInvoiceMapper().updateByPrimaryKey(record);
	}

	@Override
	public List<OrdOdInvoice> selectByCondition(String subFlag,Integer pageNo, Integer pageSize, 
			Long orderId, String tenantId,String invoiceTitle, String invoiceStatus) {
		 return ordOdInvoiceAttachMapper.selectList(subFlag,(pageNo - 1)*pageSize, pageSize, 
					orderId, tenantId,invoiceTitle,invoiceStatus);
	}

	@Override
	public int count(String subFlag,Long orderId, String tenantId,
			String invoiceTitle, String invoiceStatus) {
		return ordOdInvoiceAttachMapper.count(subFlag,orderId, 
				tenantId, invoiceTitle, invoiceStatus);
	}
}
