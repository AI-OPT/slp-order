package com.ai.slp.order.dao.mapper.interfaces;

import com.ai.slp.order.dao.mapper.bo.OrdOdLogistics;
import com.ai.slp.order.dao.mapper.bo.OrdOdLogisticsCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrdOdLogisticsMapper {
    int countByExample(OrdOdLogisticsCriteria example);

    int deleteByExample(OrdOdLogisticsCriteria example);

    int insert(OrdOdLogistics record);

    int insertSelective(OrdOdLogistics record);

    List<OrdOdLogistics> selectByExample(OrdOdLogisticsCriteria example);

    int updateByExampleSelective(@Param("record") OrdOdLogistics record, @Param("example") OrdOdLogisticsCriteria example);

    int updateByExample(@Param("record") OrdOdLogistics record, @Param("example") OrdOdLogisticsCriteria example);
}