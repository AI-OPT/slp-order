package com.ai.slp.order.dao.mapper.interfaces;

import com.ai.slp.order.dao.mapper.bo.OrdOdDeliverInfo;
import com.ai.slp.order.dao.mapper.bo.OrdOdDeliverInfoCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrdOdDeliverInfoMapper {
    int countByExample(OrdOdDeliverInfoCriteria example);

    int deleteByExample(OrdOdDeliverInfoCriteria example);

    int deleteByPrimaryKey(long deliverInfoId);

    int insert(OrdOdDeliverInfo record);

    int insertSelective(OrdOdDeliverInfo record);

    List<OrdOdDeliverInfo> selectByExample(OrdOdDeliverInfoCriteria example);

    OrdOdDeliverInfo selectByPrimaryKey(long deliverInfoId);

    int updateByExampleSelective(@Param("record") OrdOdDeliverInfo record, @Param("example") OrdOdDeliverInfoCriteria example);

    int updateByExample(@Param("record") OrdOdDeliverInfo record, @Param("example") OrdOdDeliverInfoCriteria example);

    int updateByPrimaryKeySelective(OrdOdDeliverInfo record);

    int updateByPrimaryKey(OrdOdDeliverInfo record);
}