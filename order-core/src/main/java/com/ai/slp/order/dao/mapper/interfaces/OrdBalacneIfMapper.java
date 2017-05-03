package com.ai.slp.order.dao.mapper.interfaces;

import com.ai.slp.order.dao.mapper.bo.OrdBalacneIf;
import com.ai.slp.order.dao.mapper.bo.OrdBalacneIfCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrdBalacneIfMapper {
	//按照条件查询
    int countByExample(OrdBalacneIfCriteria example);
    //按照条件删除
    int deleteByExample(OrdBalacneIfCriteria example);
    //按照主键删除
    int deleteByPrimaryKey(long balacneIfId);
    //插入
    int insert(OrdBalacneIf record);
    //选择性插入
    int insertSelective(OrdBalacneIf record);
    //按照条件查询
    List<OrdBalacneIf> selectByExample(OrdBalacneIfCriteria example);
    //按照主键查询
    OrdBalacneIf selectByPrimaryKey(long balacneIfId);
    //按条件选择性修改
    int updateByExampleSelective(@Param("record") OrdBalacneIf record, @Param("example") OrdBalacneIfCriteria example);
    //修改
    int updateByExample(@Param("record") OrdBalacneIf record, @Param("example") OrdBalacneIfCriteria example);
    //按照主键修改
    int updateByPrimaryKeySelective(OrdBalacneIf record);
    //按照主键修改
    int updateByPrimaryKey(OrdBalacneIf record);
}