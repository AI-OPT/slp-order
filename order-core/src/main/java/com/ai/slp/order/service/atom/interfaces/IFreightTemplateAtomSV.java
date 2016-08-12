package com.ai.slp.order.service.atom.interfaces;

import java.util.List;

import com.ai.slp.order.dao.mapper.bo.FreightTemplate;
import com.ai.slp.order.dao.mapper.bo.FreightTemplateCriteria;

public interface IFreightTemplateAtomSV {

    int insert(FreightTemplate record);

    int insertSelective(FreightTemplate record);
    
    List<FreightTemplate> selectByExample(FreightTemplateCriteria example);

    FreightTemplate selectByPrimaryKey(String templateId);
    
    int updateByPrimaryKeySelective(FreightTemplate record);
    
    int deleteByPrimaryKey(String templateId);
    
    int countByExample(FreightTemplateCriteria example);
}
