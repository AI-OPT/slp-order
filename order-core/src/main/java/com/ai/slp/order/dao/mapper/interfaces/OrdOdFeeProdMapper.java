package com.ai.slp.order.dao.mapper.interfaces;

import com.ai.slp.order.dao.mapper.bo.OrdOdFeeProd;
import com.ai.slp.order.dao.mapper.bo.OrdOdFeeProdCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrdOdFeeProdMapper {
    int countByExample(OrdOdFeeProdCriteria example);

    int deleteByExample(OrdOdFeeProdCriteria example);

    int insert(OrdOdFeeProd record);

    int insertSelective(OrdOdFeeProd record);

    List<OrdOdFeeProd> selectByExample(OrdOdFeeProdCriteria example);

    int updateByExampleSelective(@Param("record") OrdOdFeeProd record, @Param("example") OrdOdFeeProdCriteria example);

    int updateByExample(@Param("record") OrdOdFeeProd record, @Param("example") OrdOdFeeProdCriteria example);
}