package com.ai.slp.order.dao.mapper.interfaces;

import com.ai.slp.order.dao.mapper.bo.DeliverInfoProd;
import com.ai.slp.order.dao.mapper.bo.DeliverInfoProdCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DeliverInfoProdMapper {
	//按照条件查询数量
    int countByExample(DeliverInfoProdCriteria example);
    //按照条件删除
    int deleteByExample(DeliverInfoProdCriteria example);
    //插入
    int insert(DeliverInfoProd record);
    //根据参数选择插入
    int insertSelective(DeliverInfoProd record);
    //按照条件查询
    List<DeliverInfoProd> selectByExample(DeliverInfoProdCriteria example);
    //修改
    int updateByExampleSelective(@Param("record") DeliverInfoProd record, @Param("example") DeliverInfoProdCriteria example);
    //修改
    int updateByExample(@Param("record") DeliverInfoProd record, @Param("example") DeliverInfoProdCriteria example);
}