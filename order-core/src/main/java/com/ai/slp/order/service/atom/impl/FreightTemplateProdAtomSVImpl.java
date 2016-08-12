package com.ai.slp.order.service.atom.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ai.slp.order.dao.mapper.bo.FreightTemplateProd;
import com.ai.slp.order.dao.mapper.bo.FreightTemplateProdCriteria;
import com.ai.slp.order.dao.mapper.factory.MapperFactory;
import com.ai.slp.order.service.atom.interfaces.IFreightTemplateProdAtomSV;

@Component
public class FreightTemplateProdAtomSVImpl implements IFreightTemplateProdAtomSV {

	@Override
	public int insert(FreightTemplateProd record) {
		return MapperFactory.getFreightTemplateProdMapper().insert(record);
	}

	@Override
	public int insertSelective(FreightTemplateProd record) {
		return MapperFactory.getFreightTemplateProdMapper().insertSelective(record);
	}

	@Override
	public List<FreightTemplateProd> selectByExample(FreightTemplateProdCriteria example) {
		return MapperFactory.getFreightTemplateProdMapper().selectByExample(example);
	}

	@Override
	public int updateByExampleSelective(FreightTemplateProd record, FreightTemplateProdCriteria example) {
		return MapperFactory.getFreightTemplateProdMapper().updateByExampleSelective(record, example);
	}
	
	 public FreightTemplateProd selectByPrimaryKey(String regionId) {
		 return MapperFactory.getFreightTemplateProdMapper().selectByPrimaryKey(regionId);
	 }
	 
	 public int updateByPrimaryKeySelective(FreightTemplateProd record) {
		 return MapperFactory.getFreightTemplateProdMapper().updateByPrimaryKeySelective(record);
	 }

	@Override
	public int deleteByExample(FreightTemplateProdCriteria example) {
		return MapperFactory.getFreightTemplateProdMapper().deleteByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(String regionId) {
		return MapperFactory.getFreightTemplateProdMapper().deleteByPrimaryKey(regionId);
	}

}
