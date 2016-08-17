package com.ai.slp.order.service.atom.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ai.slp.order.dao.mapper.bo.FreightTemplate;
import com.ai.slp.order.dao.mapper.bo.FreightTemplateCriteria;
import com.ai.slp.order.dao.mapper.factory.MapperFactory;
import com.ai.slp.order.service.atom.interfaces.IFreightTemplateAtomSV;

@Component
public class FreightTemplateAtomSVImpl implements IFreightTemplateAtomSV {

	@Override
	public int insert(FreightTemplate record) {
		return MapperFactory.getFreightTemplateMapper().insert(record);
	}

	@Override
	public int insertSelective(FreightTemplate record) {
		return MapperFactory.getFreightTemplateMapper().insertSelective(record);
	}

	@Override
	public List<FreightTemplate> selectByExample(FreightTemplateCriteria example) {
		return MapperFactory.getFreightTemplateMapper().selectByExample(example);
	}

	@Override
	public FreightTemplate selectByPrimaryKey(String templateId) {
		return MapperFactory.getFreightTemplateMapper().selectByPrimaryKey(templateId);
	}

	@Override
	public int updateByPrimaryKeySelective(FreightTemplate record) {
		return MapperFactory.getFreightTemplateMapper().updateByPrimaryKeySelective(record);
	}

	@Override
	public int deleteByPrimaryKey(String templateId) {
		return MapperFactory.getFreightTemplateMapper().deleteByPrimaryKey(templateId);
	}

	@Override
	public int countByExample(FreightTemplateCriteria example) {
		return MapperFactory.getFreightTemplateMapper().countByExample(example);
	}

}
