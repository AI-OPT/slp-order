package com.ai.slp.order.service.atom.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.slp.order.dao.mapper.bo.FreightTemplateProd;
import com.ai.slp.order.dao.mapper.bo.FreightTemplateProdCriteria;
import com.ai.slp.order.dao.mapper.interfaces.FreightTemplateProdMapper;
import com.ai.slp.order.service.atom.interfaces.IFreightTemplateProdAtomSV;

@Component
public class FreightTemplateProdAtomSVImpl implements IFreightTemplateProdAtomSV {
	
	@Autowired
	private FreightTemplateProdMapper freightTemplateProdMapper;

	@Override
	public int insert(FreightTemplateProd record) {
		return freightTemplateProdMapper.insert(record);
	}

	@Override
	public int insertSelective(FreightTemplateProd record) {
		return freightTemplateProdMapper.insertSelective(record);
	}

	@Override
	public List<FreightTemplateProd> selectByExample(FreightTemplateProdCriteria example) {
		return freightTemplateProdMapper.selectByExample(example);
	}

	@Override
	public int updateByExampleSelective(FreightTemplateProd record, FreightTemplateProdCriteria example) {
		return freightTemplateProdMapper.updateByExampleSelective(record, example);
	}
	
	 public FreightTemplateProd selectByPrimaryKey(String regionId) {
		 return freightTemplateProdMapper.selectByPrimaryKey(regionId);
	 }
	 
	 public int updateByPrimaryKeySelective(FreightTemplateProd record) {
		 return freightTemplateProdMapper.updateByPrimaryKeySelective(record);
	 }

	@Override
	public int deleteByExample(FreightTemplateProdCriteria example) {
		return freightTemplateProdMapper.deleteByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(String regionId) {
		return freightTemplateProdMapper.deleteByPrimaryKey(regionId);
	}

	@Override
	public List<FreightTemplateProd> selectTemplatesByTemplateId(String templateId) {
		// TODO Auto-generated method stub
		FreightTemplateProdCriteria exampleProd=new FreightTemplateProdCriteria();
		FreightTemplateProdCriteria.Criteria criteriaProd = exampleProd.createCriteria();
		criteriaProd.andTemplateIdEqualTo(templateId);
		return freightTemplateProdMapper.selectByExample(exampleProd);
	}

}
