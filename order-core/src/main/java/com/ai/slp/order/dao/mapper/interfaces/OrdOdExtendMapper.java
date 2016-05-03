package com.ai.slp.order.dao.mapper.interfaces;

import com.ai.slp.order.dao.mapper.bo.OrdOdExtend;
import com.ai.slp.order.dao.mapper.bo.OrdOdExtendCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrdOdExtendMapper {
    int countByExample(OrdOdExtendCriteria example);

    int deleteByExample(OrdOdExtendCriteria example);

    int insert(OrdOdExtend record);

    int insertSelective(OrdOdExtend record);

    List<OrdOdExtend> selectByExample(OrdOdExtendCriteria example);

    int updateByExampleSelective(@Param("record") OrdOdExtend record, @Param("example") OrdOdExtendCriteria example);

    int updateByExample(@Param("record") OrdOdExtend record, @Param("example") OrdOdExtendCriteria example);
}