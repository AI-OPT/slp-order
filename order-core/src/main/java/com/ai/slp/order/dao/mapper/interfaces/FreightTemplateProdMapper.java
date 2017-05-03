package com.ai.slp.order.dao.mapper.interfaces;

import com.ai.slp.order.dao.mapper.bo.FreightTemplateProd;
import com.ai.slp.order.dao.mapper.bo.FreightTemplateProdCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FreightTemplateProdMapper {
	//按照条件查询
	int countByExample(FreightTemplateProdCriteria example);
	 //按照条件删除
    int deleteByExample(FreightTemplateProdCriteria example);
    //按照主键删除
    int deleteByPrimaryKey(String regionId);
    //插入
    int insert(FreightTemplateProd record);
    //选择性插入
    int insertSelective(FreightTemplateProd record);
    //按照条件查询
    List<FreightTemplateProd> selectByExample(FreightTemplateProdCriteria example);
    //按照主键查询
    FreightTemplateProd selectByPrimaryKey(String regionId);
    //按条件选择性修改
    int updateByExampleSelective(@Param("record") FreightTemplateProd record, @Param("example") FreightTemplateProdCriteria example);
    //按条件修改
    int updateByExample(@Param("record") FreightTemplateProd record, @Param("example") FreightTemplateProdCriteria example);
    //按照主键修改
    int updateByPrimaryKeySelective(FreightTemplateProd record);
    //按照主键修改
    int updateByPrimaryKey(FreightTemplateProd record);
}