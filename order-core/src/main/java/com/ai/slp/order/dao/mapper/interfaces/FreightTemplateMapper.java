package com.ai.slp.order.dao.mapper.interfaces;

import com.ai.slp.order.dao.mapper.bo.FreightTemplate;
import com.ai.slp.order.dao.mapper.bo.FreightTemplateCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FreightTemplateMapper {
	//按照条件查询
    int countByExample(FreightTemplateCriteria example);
    //按照条件删除
    int deleteByExample(FreightTemplateCriteria example);
    //按照主键删除
    int deleteByPrimaryKey(String templateId);
    //插入
    int insert(FreightTemplate record);
    //选择性插入
    int insertSelective(FreightTemplate record);
    //按照条件查询
    List<FreightTemplate> selectByExample(FreightTemplateCriteria example);
    //按照主键查询
    FreightTemplate selectByPrimaryKey(String templateId);
    //按条件选择性修改
    int updateByExampleSelective(@Param("record") FreightTemplate record, @Param("example") FreightTemplateCriteria example);
    //按条件修改
    int updateByExample(@Param("record") FreightTemplate record, @Param("example") FreightTemplateCriteria example);
    //按照主键修改
    int updateByPrimaryKeySelective(FreightTemplate record);
    //按照主键修改
    int updateByPrimaryKey(FreightTemplate record);
}