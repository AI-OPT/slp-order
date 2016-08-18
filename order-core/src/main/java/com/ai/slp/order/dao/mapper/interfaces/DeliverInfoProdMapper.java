package com.ai.slp.order.dao.mapper.interfaces;

import com.ai.slp.order.dao.mapper.bo.DeliverInfoProd;
import com.ai.slp.order.dao.mapper.bo.DeliverInfoProdCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DeliverInfoProdMapper {
    int countByExample(DeliverInfoProdCriteria example);

    int deleteByExample(DeliverInfoProdCriteria example);

    int insert(DeliverInfoProd record);

    int insertSelective(DeliverInfoProd record);

    List<DeliverInfoProd> selectByExample(DeliverInfoProdCriteria example);

    int updateByExampleSelective(@Param("record") DeliverInfoProd record, @Param("example") DeliverInfoProdCriteria example);

    int updateByExample(@Param("record") DeliverInfoProd record, @Param("example") DeliverInfoProdCriteria example);
}