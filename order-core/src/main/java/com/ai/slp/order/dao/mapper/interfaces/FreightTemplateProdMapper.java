package com.ai.slp.order.dao.mapper.interfaces;

import com.ai.slp.order.dao.mapper.bo.FreightTemplateProd;
import com.ai.slp.order.dao.mapper.bo.FreightTemplateProdCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FreightTemplateProdMapper {
    int countByExample(FreightTemplateProdCriteria example);

    int deleteByExample(FreightTemplateProdCriteria example);

    int deleteByPrimaryKey(String regionId);

    int insert(FreightTemplateProd record);

    int insertSelective(FreightTemplateProd record);

    List<FreightTemplateProd> selectByExample(FreightTemplateProdCriteria example);

    FreightTemplateProd selectByPrimaryKey(String regionId);

    int updateByExampleSelective(@Param("record") FreightTemplateProd record, @Param("example") FreightTemplateProdCriteria example);

    int updateByExample(@Param("record") FreightTemplateProd record, @Param("example") FreightTemplateProdCriteria example);

    int updateByPrimaryKeySelective(FreightTemplateProd record);

    int updateByPrimaryKey(FreightTemplateProd record);
}