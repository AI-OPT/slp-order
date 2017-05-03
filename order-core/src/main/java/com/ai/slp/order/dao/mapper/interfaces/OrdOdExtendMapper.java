package com.ai.slp.order.dao.mapper.interfaces;

import com.ai.slp.order.dao.mapper.bo.OrdOdExtend;
import com.ai.slp.order.dao.mapper.bo.OrdOdExtendCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrdOdExtendMapper {
	//按照条件查询
    int countByExample(OrdOdExtendCriteria example);
    //按照条件删除
    int deleteByExample(OrdOdExtendCriteria example);
    //插入
    int insert(OrdOdExtend record);
    //选择性插入
    int insertSelective(OrdOdExtend record);
    //按照条件查询
    List<OrdOdExtend> selectByExample(OrdOdExtendCriteria example);
    //按条件选择性修改
    int updateByExampleSelective(@Param("record") OrdOdExtend record, @Param("example") OrdOdExtendCriteria example);
    //按条件修改
    int updateByExample(@Param("record") OrdOdExtend record, @Param("example") OrdOdExtendCriteria example);
}