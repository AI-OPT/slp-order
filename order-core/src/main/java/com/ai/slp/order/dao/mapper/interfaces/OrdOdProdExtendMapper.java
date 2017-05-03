package com.ai.slp.order.dao.mapper.interfaces;

import com.ai.slp.order.dao.mapper.bo.OrdOdProdExtend;
import com.ai.slp.order.dao.mapper.bo.OrdOdProdExtendCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrdOdProdExtendMapper {
	//按照条件查询
    int countByExample(OrdOdProdExtendCriteria example);
    //按照条件删除
    int deleteByExample(OrdOdProdExtendCriteria example);
    //按照主键删除
    int deleteByPrimaryKey(long prodDetalExtendId);
    //插入
    int insert(OrdOdProdExtend record);
    //选择性插入
    int insertSelective(OrdOdProdExtend record);
    //按照条件查询
    List<OrdOdProdExtend> selectByExample(OrdOdProdExtendCriteria example);
    //按照主键查询
    OrdOdProdExtend selectByPrimaryKey(long prodDetalExtendId);
    //按条件选择性修改
    int updateByExampleSelective(@Param("record") OrdOdProdExtend record, @Param("example") OrdOdProdExtendCriteria example);
    //按条件修改
    int updateByExample(@Param("record") OrdOdProdExtend record, @Param("example") OrdOdProdExtendCriteria example);
    //修改
    int updateByPrimaryKeySelective(OrdOdProdExtend record);
    //修改
    int updateByPrimaryKey(OrdOdProdExtend record);
}