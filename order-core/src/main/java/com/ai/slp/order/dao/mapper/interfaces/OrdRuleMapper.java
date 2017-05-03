package com.ai.slp.order.dao.mapper.interfaces;

import com.ai.slp.order.dao.mapper.bo.OrdRule;
import com.ai.slp.order.dao.mapper.bo.OrdRuleCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrdRuleMapper {
	//按照条件查询
    int countByExample(OrdRuleCriteria example);
    //按照条件删除
    int deleteByExample(OrdRuleCriteria example);
    //按照主键删除
    int deleteByPrimaryKey(String orderRuleId);
    //插入
    int insert(OrdRule record);
    //插入
    int insertSelective(OrdRule record);
    //按照条件查询
    List<OrdRule> selectByExample(OrdRuleCriteria example);
    //按照条件查询
    OrdRule selectByPrimaryKey(String orderRuleId);
    //按条件选择性修改
    int updateByExampleSelective(@Param("record") OrdRule record, @Param("example") OrdRuleCriteria example);
    //按条件选择性修改
    int updateByExample(@Param("record") OrdRule record, @Param("example") OrdRuleCriteria example);
    //按照主键修改
    int updateByPrimaryKeySelective(OrdRule record);
    //按照主键修改
    int updateByPrimaryKey(OrdRule record);
}