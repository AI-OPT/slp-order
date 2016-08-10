package com.ai.slp.order.dao.mapper.interfaces;

import com.ai.slp.order.dao.mapper.bo.OrdRule;
import com.ai.slp.order.dao.mapper.bo.OrdRuleCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrdRuleMapper {
    int countByExample(OrdRuleCriteria example);

    int deleteByExample(OrdRuleCriteria example);

    int deleteByPrimaryKey(String orderRuleId);

    int insert(OrdRule record);

    int insertSelective(OrdRule record);

    List<OrdRule> selectByExample(OrdRuleCriteria example);

    OrdRule selectByPrimaryKey(String orderRuleId);

    int updateByExampleSelective(@Param("record") OrdRule record, @Param("example") OrdRuleCriteria example);

    int updateByExample(@Param("record") OrdRule record, @Param("example") OrdRuleCriteria example);

    int updateByPrimaryKeySelective(OrdRule record);

    int updateByPrimaryKey(OrdRule record);
}