package com.ai.slp.order.dao.mapper.interfaces;

import com.ai.slp.order.dao.mapper.bo.OrdOdFeeProd;
import com.ai.slp.order.dao.mapper.bo.OrdOdFeeProdCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrdOdFeeProdMapper {
	//按照条件查询
    int countByExample(OrdOdFeeProdCriteria example);
    //按照条件删除
    int deleteByExample(OrdOdFeeProdCriteria example);
    //插入
    int insert(OrdOdFeeProd record);
    //插入
    int insertSelective(OrdOdFeeProd record);
    //按照条件查询
    List<OrdOdFeeProd> selectByExample(OrdOdFeeProdCriteria example);
    //按条件选择性修改
    int updateByExampleSelective(@Param("record") OrdOdFeeProd record, @Param("example") OrdOdFeeProdCriteria example);
    //按条件修改
    int updateByExample(@Param("record") OrdOdFeeProd record, @Param("example") OrdOdFeeProdCriteria example);
}