package com.ai.slp.order.dao.mapper.interfaces;

import com.ai.slp.order.dao.mapper.bo.OrdOdFeeOffset;
import com.ai.slp.order.dao.mapper.bo.OrdOdFeeOffsetCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrdOdFeeOffsetMapper {
    int countByExample(OrdOdFeeOffsetCriteria example);

    int deleteByExample(OrdOdFeeOffsetCriteria example);

    int insert(OrdOdFeeOffset record);

    int insertSelective(OrdOdFeeOffset record);

    List<OrdOdFeeOffset> selectByExample(OrdOdFeeOffsetCriteria example);

    int updateByExampleSelective(@Param("record") OrdOdFeeOffset record, @Param("example") OrdOdFeeOffsetCriteria example);

    int updateByExample(@Param("record") OrdOdFeeOffset record, @Param("example") OrdOdFeeOffsetCriteria example);
}