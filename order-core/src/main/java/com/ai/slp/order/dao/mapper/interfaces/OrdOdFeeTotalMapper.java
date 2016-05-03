package com.ai.slp.order.dao.mapper.interfaces;

import com.ai.slp.order.dao.mapper.bo.OrdOdFeeTotal;
import com.ai.slp.order.dao.mapper.bo.OrdOdFeeTotalCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrdOdFeeTotalMapper {
    int countByExample(OrdOdFeeTotalCriteria example);

    int deleteByExample(OrdOdFeeTotalCriteria example);

    int insert(OrdOdFeeTotal record);

    int insertSelective(OrdOdFeeTotal record);

    List<OrdOdFeeTotal> selectByExample(OrdOdFeeTotalCriteria example);

    int updateByExampleSelective(@Param("record") OrdOdFeeTotal record, @Param("example") OrdOdFeeTotalCriteria example);

    int updateByExample(@Param("record") OrdOdFeeTotal record, @Param("example") OrdOdFeeTotalCriteria example);
}