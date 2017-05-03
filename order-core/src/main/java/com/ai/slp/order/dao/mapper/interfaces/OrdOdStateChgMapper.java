package com.ai.slp.order.dao.mapper.interfaces;

import com.ai.slp.order.dao.mapper.bo.OrdOdStateChg;
import com.ai.slp.order.dao.mapper.bo.OrdOdStateChgCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrdOdStateChgMapper {
	//按照条件查询
    int countByExample(OrdOdStateChgCriteria example);
    //按照条件删除
    int deleteByExample(OrdOdStateChgCriteria example);
    //按照主键删除
    int deleteByPrimaryKey(long stateChgId);
    //插入
    int insert(OrdOdStateChg record);
    //选择性插入
    int insertSelective(OrdOdStateChg record);
    //按照条件查询
    List<OrdOdStateChg> selectByExample(OrdOdStateChgCriteria example);
    //按照主键查询
    OrdOdStateChg selectByPrimaryKey(long stateChgId);
    //按条件选择性修改
    int updateByExampleSelective(@Param("record") OrdOdStateChg record, @Param("example") OrdOdStateChgCriteria example);
    //按条件修改
    int updateByExample(@Param("record") OrdOdStateChg record, @Param("example") OrdOdStateChgCriteria example);
    //按照主键修改
    int updateByPrimaryKeySelective(OrdOdStateChg record);
    //按照主键修改
    int updateByPrimaryKey(OrdOdStateChg record);
}