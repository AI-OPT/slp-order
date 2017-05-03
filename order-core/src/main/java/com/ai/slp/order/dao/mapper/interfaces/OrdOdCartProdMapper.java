package com.ai.slp.order.dao.mapper.interfaces;

import com.ai.slp.order.dao.mapper.bo.OrdOdCartProd;
import com.ai.slp.order.dao.mapper.bo.OrdOdCartProdCriteria;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrdOdCartProdMapper {
	//按照条件查询
    int countByExample(OrdOdCartProdCriteria example);
    //按照条件删除
    int deleteByExample(OrdOdCartProdCriteria example);
    //按照主键删除
    int deleteByPrimaryKey(Long prodDetalId);
    //插入
    int insert(OrdOdCartProd record);
    //选择性插入
    int insertSelective(OrdOdCartProd record);
    //按照条件查询
    List<OrdOdCartProd> selectByExample(OrdOdCartProdCriteria example);
    //按照主键查询
    OrdOdCartProd selectByPrimaryKey(Long prodDetalId);
    //按条件选择性修改
    int updateByExampleSelective(@Param("record") OrdOdCartProd record, @Param("example") OrdOdCartProdCriteria example);
    //按条件修改
    int updateByExample(@Param("record") OrdOdCartProd record, @Param("example") OrdOdCartProdCriteria example);
    //按照主键修改
    int updateByPrimaryKeySelective(OrdOdCartProd record);
    //按照主键修改
    int updateByPrimaryKey(OrdOdCartProd record);
}