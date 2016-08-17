package com.ai.slp.order.dao.mapper.interfaces;

import com.ai.slp.order.dao.mapper.bo.FreightTemplate;
import com.ai.slp.order.dao.mapper.bo.FreightTemplateCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FreightTemplateMapper {
    int countByExample(FreightTemplateCriteria example);

    int deleteByExample(FreightTemplateCriteria example);

    int deleteByPrimaryKey(String templateId);

    int insert(FreightTemplate record);

    int insertSelective(FreightTemplate record);

    List<FreightTemplate> selectByExample(FreightTemplateCriteria example);

    FreightTemplate selectByPrimaryKey(String templateId);

    int updateByExampleSelective(@Param("record") FreightTemplate record, @Param("example") FreightTemplateCriteria example);

    int updateByExample(@Param("record") FreightTemplate record, @Param("example") FreightTemplateCriteria example);

    int updateByPrimaryKeySelective(FreightTemplate record);

    int updateByPrimaryKey(FreightTemplate record);
}