package com.ai.slp.order.dao.mapper.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ai.slp.order.dao.mapper.bo.OrdParam;
import com.ai.slp.order.dao.mapper.bo.OrdParamCriteria;

public interface OrdParamMapper {
    int countByExample(OrdParamCriteria example);

    int deleteByExample(OrdParamCriteria example);

    int insert(OrdParam record);

    int insertSelective(OrdParam record);

    List<OrdParam> selectByExample(OrdParamCriteria example);

    int updateByExampleSelective(@Param("record") OrdParam record, @Param("example") OrdParamCriteria example);

    int updateByExample(@Param("record") OrdParam record, @Param("example") OrdParamCriteria example);
}