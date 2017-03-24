package com.ai.slp.order.service.atom.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.slp.order.dao.mapper.bo.FreightTemplate;
import com.ai.slp.order.dao.mapper.bo.FreightTemplateCriteria;
import com.ai.slp.order.dao.mapper.interfaces.FreightTemplateMapper;
import com.ai.slp.order.service.atom.interfaces.IFreightTemplateAtomSV;

@Component
public class FreightTemplateAtomSVImpl implements IFreightTemplateAtomSV {
	
	@Autowired
	private FreightTemplateMapper freightTemplateMapper;

	@Override
	public int insert(FreightTemplate record) {
		return freightTemplateMapper.insert(record);
	}

	@Override
	public int insertSelective(FreightTemplate record) {
		return freightTemplateMapper.insertSelective(record);
	}

	@Override
	public List<FreightTemplate> selectByExample(FreightTemplateCriteria example) {
		return freightTemplateMapper.selectByExample(example);
	}

	@Override
	public FreightTemplate selectByPrimaryKey(String templateId) {
		return freightTemplateMapper.selectByPrimaryKey(templateId);
	}

	@Override
	public int updateByPrimaryKeySelective(FreightTemplate record) {
		return freightTemplateMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int deleteByPrimaryKey(String templateId) {
		return freightTemplateMapper.deleteByPrimaryKey(templateId);
	}

	@Override
	public int countByExample(FreightTemplateCriteria example) {
		return freightTemplateMapper.countByExample(example);
	}

	@Override
	public List<FreightTemplate> selectFreightTemplate(String supplierId,Integer pageInt,Integer pageSize) {
		// TODO Auto-generated method stub
		FreightTemplateCriteria example=new FreightTemplateCriteria();
		FreightTemplateCriteria.Criteria criteria = example.createCriteria();
		criteria.andSupplierIdEqualTo(supplierId);
		example.setLimitStart(pageInt);
		example.setLimitEnd(pageSize);
		example.setOrderByClause("TIME DESC");
		return freightTemplateMapper.selectByExample(example);
	}

	@Override
	public int countFreightTemplate(String supplierId) {
		FreightTemplateCriteria example=new FreightTemplateCriteria();
		FreightTemplateCriteria.Criteria criteria = example.createCriteria();
		criteria.andSupplierIdEqualTo(supplierId);
		return freightTemplateMapper.countByExample(example);
	}

}
