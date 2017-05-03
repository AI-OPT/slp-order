package com.ai.slp.order.dao.mapper.interfaces;

import com.ai.slp.order.dao.mapper.bo.OrdOdLogistics;
import com.ai.slp.order.dao.mapper.bo.OrdOdLogisticsCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrdOdLogisticsMapper {
	//按照条件查询
    int countByExample(OrdOdLogisticsCriteria example);
    //按照条件删除
    int deleteByExample(OrdOdLogisticsCriteria example);
    //按照主键删除
    int deleteByPrimaryKey(long logisticsId);
    //插入
    int insert(OrdOdLogistics record);
    //插入
    int insertSelective(OrdOdLogistics record);
    //按照条件查询
    List<OrdOdLogistics> selectByExample(OrdOdLogisticsCriteria example);
    //按照条件查询
    OrdOdLogistics selectByPrimaryKey(long logisticsId);
    //按条件选择性修改
    int updateByExampleSelective(@Param("record") OrdOdLogistics record, @Param("example") OrdOdLogisticsCriteria example);
    //按条件修改
    int updateByExample(@Param("record") OrdOdLogistics record, @Param("example") OrdOdLogisticsCriteria example);
    //按主键选择性修改
    int updateByPrimaryKeySelective(OrdOdLogistics record);
    //按主键修改
    int updateByPrimaryKey(OrdOdLogistics record);
}