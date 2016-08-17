package com.ai.slp.order.dao.mapper.interfaces;

import com.ai.slp.order.dao.mapper.bo.OrdOdProd;
import com.ai.slp.order.dao.mapper.bo.OrdOdProdCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrdOdProdMapper {
    int countByExample(OrdOdProdCriteria example);

    int deleteByExample(OrdOdProdCriteria example);

    int deleteByPrimaryKey(long prodDetalId);

    int insert(OrdOdProd record);

    int insertSelective(OrdOdProd record);

    List<OrdOdProd> selectByExample(OrdOdProdCriteria example);

    OrdOdProd selectByPrimaryKey(long prodDetalId);

    int updateByExampleSelective(@Param("record") OrdOdProd record, @Param("example") OrdOdProdCriteria example);

    int updateByExample(@Param("record") OrdOdProd record, @Param("example") OrdOdProdCriteria example);

    int updateByPrimaryKeySelective(OrdOdProd record);

    int updateByPrimaryKey(OrdOdProd record);
}