package com.ai.slp.order.dao.mapper.interfaces;

import com.ai.slp.order.dao.mapper.bo.OrdOdProd;
import com.ai.slp.order.dao.mapper.bo.OrdOdProdCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrdOdProdMapper {
	//按照条件查询
    int countByExample(OrdOdProdCriteria example);
    //按照条件删除
    int deleteByExample(OrdOdProdCriteria example);
    //按照主键删除
    int deleteByPrimaryKey(long prodDetalId);
    //插入
    int insert(OrdOdProd record);
    //插入
    int insertSelective(OrdOdProd record);
    //查询
    List<OrdOdProd> selectByExample(OrdOdProdCriteria example);
    //按照主键查询
    OrdOdProd selectByPrimaryKey(long prodDetalId);
    //按条件选择性修改
    int updateByExampleSelective(@Param("record") OrdOdProd record, @Param("example") OrdOdProdCriteria example);
    //按条件修改
    int updateByExample(@Param("record") OrdOdProd record, @Param("example") OrdOdProdCriteria example);
    //按照主键修改
    int updateByPrimaryKeySelective(OrdOdProd record);
    //按照主键修改
    int updateByPrimaryKey(OrdOdProd record);
}