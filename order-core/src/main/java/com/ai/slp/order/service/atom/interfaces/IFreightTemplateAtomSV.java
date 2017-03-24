package com.ai.slp.order.service.atom.interfaces;

import java.util.List;

import com.ai.slp.order.dao.mapper.bo.FreightTemplate;
import com.ai.slp.order.dao.mapper.bo.FreightTemplateCriteria;

public interface IFreightTemplateAtomSV {

    int insert(FreightTemplate record);

    int insertSelective(FreightTemplate record);
    
    List<FreightTemplate> selectByExample(FreightTemplateCriteria example);
    
    List<FreightTemplate> selectFreightTemplate(String supplierId,Integer pageInt,Integer pageSize);
    
    int countFreightTemplate(String supplierId);

    FreightTemplate selectByPrimaryKey(String templateId);
    
    int updateByPrimaryKeySelective(FreightTemplate record);
    
    int deleteByPrimaryKey(String templateId);
    
    int countByExample(FreightTemplateCriteria example);
}
