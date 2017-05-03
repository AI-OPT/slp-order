package com.ai.slp.order.dao.mapper.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ai.slp.order.dao.mapper.bo.OrdParam;
import com.ai.slp.order.dao.mapper.bo.OrdParamCriteria;

public interface OrdParamMapper {
	//按照条件查询
    int countByExample(OrdParamCriteria example);
    //按照条件删除
    int deleteByExample(OrdParamCriteria example);
    //插入
    int insert(OrdParam record);
    //选择性插入
    int insertSelective(OrdParam record);
    //按照条件查询
    List<OrdParam> selectByExample(OrdParamCriteria example);
    //按条件选择性修改
    int updateByExampleSelective(@Param("record") OrdParam record, @Param("example") OrdParamCriteria example);
    //修改
    int updateByExample(@Param("record") OrdParam record, @Param("example") OrdParamCriteria example);
}