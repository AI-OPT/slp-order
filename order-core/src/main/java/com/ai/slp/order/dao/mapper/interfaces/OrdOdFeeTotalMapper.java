package com.ai.slp.order.dao.mapper.interfaces;

import com.ai.slp.order.dao.mapper.bo.OrdOdFeeTotal;
import com.ai.slp.order.dao.mapper.bo.OrdOdFeeTotalCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrdOdFeeTotalMapper {
	//按照条件查询
    int countByExample(OrdOdFeeTotalCriteria example);
    //按照条件删除
    int deleteByExample(OrdOdFeeTotalCriteria example);
    //按照主键删除
    int deleteByPrimaryKey(long orderId);
    //插入
    int insert(OrdOdFeeTotal record);
    //选择性插入
    int insertSelective(OrdOdFeeTotal record);
    //按照条件查询
    List<OrdOdFeeTotal> selectByExample(OrdOdFeeTotalCriteria example);
    //按照主键查询
    OrdOdFeeTotal selectByPrimaryKey(long orderId);
    //按条件选择性修改
    int updateByExampleSelective(@Param("record") OrdOdFeeTotal record, @Param("example") OrdOdFeeTotalCriteria example);
    //按条件修改
    int updateByExample(@Param("record") OrdOdFeeTotal record, @Param("example") OrdOdFeeTotalCriteria example);
    //修改
    int updateByPrimaryKeySelective(OrdOdFeeTotal record);
    //修改
    int updateByPrimaryKey(OrdOdFeeTotal record);
}