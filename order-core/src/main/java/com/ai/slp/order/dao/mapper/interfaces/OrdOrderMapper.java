package com.ai.slp.order.dao.mapper.interfaces;

import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.dao.mapper.bo.OrdOrderCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrdOrderMapper {
	//按照条件查询
    int countByExample(OrdOrderCriteria example);
    //按照条件删除
    int deleteByExample(OrdOrderCriteria example);
    //按照主键删除
    int deleteByPrimaryKey(long orderId);
    //插入
    int insert(OrdOrder record);
    //插入
    int insertSelective(OrdOrder record);
    //按照条件查询
    List<OrdOrder> selectByExample(OrdOrderCriteria example);
    //按照主键查询
    OrdOrder selectByPrimaryKey(long orderId);
    //按条件选择性修改
    int updateByExampleSelective(@Param("record") OrdOrder record, @Param("example") OrdOrderCriteria example);
    //按条件修改
    int updateByExample(@Param("record") OrdOrder record, @Param("example") OrdOrderCriteria example);
    //按照主键修改
    int updateByPrimaryKeySelective(OrdOrder record);
    //按照主键修改
    int updateByPrimaryKey(OrdOrder record);
}