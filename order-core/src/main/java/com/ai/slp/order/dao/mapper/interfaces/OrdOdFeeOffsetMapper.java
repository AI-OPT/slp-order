package com.ai.slp.order.dao.mapper.interfaces;

import com.ai.slp.order.dao.mapper.bo.OrdOdFeeOffset;
import com.ai.slp.order.dao.mapper.bo.OrdOdFeeOffsetCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrdOdFeeOffsetMapper {
	//按照条件查询
    int countByExample(OrdOdFeeOffsetCriteria example);
    //按照条件删除
    int deleteByExample(OrdOdFeeOffsetCriteria example);
    //按照主键删除
    int deleteByPrimaryKey(long feeOffsetId);
    //插入
    int insert(OrdOdFeeOffset record);
    //选择性插入
    int insertSelective(OrdOdFeeOffset record);
    //按照条件查询
    List<OrdOdFeeOffset> selectByExample(OrdOdFeeOffsetCriteria example);
    //按照主键查询
    OrdOdFeeOffset selectByPrimaryKey(long feeOffsetId);
    //按条件选择性修改
    int updateByExampleSelective(@Param("record") OrdOdFeeOffset record, @Param("example") OrdOdFeeOffsetCriteria example);
    //按条件修改
    int updateByExample(@Param("record") OrdOdFeeOffset record, @Param("example") OrdOdFeeOffsetCriteria example);
    //按照主键修改
    int updateByPrimaryKeySelective(OrdOdFeeOffset record);
    //按照主键修改
    int updateByPrimaryKey(OrdOdFeeOffset record);
}