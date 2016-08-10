package com.ai.slp.order.dao.mapper.interfaces;

import com.ai.slp.order.dao.mapper.bo.OrdOdCartProd;
import com.ai.slp.order.dao.mapper.bo.OrdOdCartProdCriteria;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrdOdCartProdMapper {
    int countByExample(OrdOdCartProdCriteria example);

    int deleteByExample(OrdOdCartProdCriteria example);

    int deleteByPrimaryKey(Long prodDetalId);

    int insert(OrdOdCartProd record);

    int insertSelective(OrdOdCartProd record);

    List<OrdOdCartProd> selectByExample(OrdOdCartProdCriteria example);

    OrdOdCartProd selectByPrimaryKey(Long prodDetalId);

    int updateByExampleSelective(@Param("record") OrdOdCartProd record, @Param("example") OrdOdCartProdCriteria example);

    int updateByExample(@Param("record") OrdOdCartProd record, @Param("example") OrdOdCartProdCriteria example);

    int updateByPrimaryKeySelective(OrdOdCartProd record);

    int updateByPrimaryKey(OrdOdCartProd record);
}