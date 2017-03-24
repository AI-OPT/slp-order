package com.ai.slp.order.service.atom.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ai.slp.order.dao.mapper.bo.FreightTemplateProd;
import com.ai.slp.order.dao.mapper.bo.FreightTemplateProdCriteria;

public interface IFreightTemplateProdAtomSV {
	
	  int insert(FreightTemplateProd record);

	  int insertSelective(FreightTemplateProd record);
	  
	  List<FreightTemplateProd> selectByExample(FreightTemplateProdCriteria example);
	  
	  List<FreightTemplateProd> selectTemplatesByTemplateId(String templateId);
	  
	  int updateByExampleSelective(@Param("record") FreightTemplateProd record, @Param("example") FreightTemplateProdCriteria example);

	  FreightTemplateProd selectByPrimaryKey(String regionId);
	  
	  int updateByPrimaryKeySelective(FreightTemplateProd record);
	  
	  int deleteByExample(FreightTemplateProdCriteria example);

	  int deleteByPrimaryKey(String regionId);
}
