package com.ai.slp.order.dao.mapper.interfaces;

import com.ai.slp.order.dao.mapper.bo.OrdOdDeliverInfo;
import com.ai.slp.order.dao.mapper.bo.OrdOdDeliverInfoCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrdOdDeliverInfoMapper {
	//按照条件查询
    int countByExample(OrdOdDeliverInfoCriteria example);
    //按照条件删除
    int deleteByExample(OrdOdDeliverInfoCriteria example);
    //按照主键删除
    int deleteByPrimaryKey(long deliverInfoId);
    //插入
    int insert(OrdOdDeliverInfo record);
    //选择性插入
    int insertSelective(OrdOdDeliverInfo record);
    //按照条件查询
    List<OrdOdDeliverInfo> selectByExample(OrdOdDeliverInfoCriteria example);
    //按照主键查询
    OrdOdDeliverInfo selectByPrimaryKey(long deliverInfoId);
    //按条件选择性修改
    int updateByExampleSelective(@Param("record") OrdOdDeliverInfo record, @Param("example") OrdOdDeliverInfoCriteria example);
    //按条件修改
    int updateByExample(@Param("record") OrdOdDeliverInfo record, @Param("example") OrdOdDeliverInfoCriteria example);
    //按照主键修改
    int updateByPrimaryKeySelective(OrdOdDeliverInfo record);
    //按照主键修改
    int updateByPrimaryKey(OrdOdDeliverInfo record);
}