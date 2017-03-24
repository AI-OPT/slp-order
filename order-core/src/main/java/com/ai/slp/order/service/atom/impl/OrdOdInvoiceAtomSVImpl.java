package com.ai.slp.order.service.atom.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.slp.order.dao.mapper.attach.OrdOdInvoiceAttachMapper;
import com.ai.slp.order.dao.mapper.bo.OrdOdInvoice;
import com.ai.slp.order.dao.mapper.bo.OrdOdInvoiceCriteria;
import com.ai.slp.order.dao.mapper.bo.OrdOdInvoiceCriteria.Criteria;
import com.ai.slp.order.dao.mapper.interfaces.OrdOdInvoiceMapper;
import com.ai.slp.order.service.atom.interfaces.IOrdOdInvoiceAtomSV;

@Component
public class OrdOdInvoiceAtomSVImpl implements IOrdOdInvoiceAtomSV{
	
	@Autowired
	private OrdOdInvoiceAttachMapper ordOdInvoiceAttachMapper;
	@Autowired
	private OrdOdInvoiceMapper ordOdInvoiceMapper;

	@Override
	public int insertSelective(OrdOdInvoice record) {
		return ordOdInvoiceMapper.insertSelective(record);
	}

	@Override
	public List<OrdOdInvoice> selectByExample(OrdOdInvoiceCriteria example) {
		return ordOdInvoiceMapper.selectByExample(example);
	}

	@Override
	public OrdOdInvoice selectByPrimaryKey(long orderId) {
		return ordOdInvoiceMapper.selectByPrimaryKey(orderId);
	}

	@Override
	public int countByExample(OrdOdInvoiceCriteria example) {
		return ordOdInvoiceMapper.countByExample(example);
	}

	@Override
	public int updateByPrimaryKey(OrdOdInvoice record) {
		return ordOdInvoiceMapper.updateByPrimaryKey(record);
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

	@Override
	public List<OrdOdInvoice> selectOrdOdInvoice(long orderId, String tenantId) {
		// TODO Auto-generated method stub
		OrdOdInvoiceCriteria example=new OrdOdInvoiceCriteria();
		Criteria criteria = example.createCriteria();
		criteria.andOrderIdEqualTo(orderId);
		criteria.andTenantIdEqualTo(tenantId);
		return ordOdInvoiceMapper.selectByExample(example);
	}
}
