package com.ai.slp.order.dao.mapper.interfaces;

import com.ai.slp.order.dao.mapper.bo.OrdOdInvoice;
import com.ai.slp.order.dao.mapper.bo.OrdOdInvoiceCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrdOdInvoiceMapper {
	//按照条件查询
    int countByExample(OrdOdInvoiceCriteria example);
    //按照条件删除
    int deleteByExample(OrdOdInvoiceCriteria example);
    //按照主键删除
    int deleteByPrimaryKey(long orderId);
    //插入
    int insert(OrdOdInvoice record);
    //选择性插入
    int insertSelective(OrdOdInvoice record);
    //按照条件查询
    List<OrdOdInvoice> selectByExample(OrdOdInvoiceCriteria example);
    //按照主键查询
    OrdOdInvoice selectByPrimaryKey(long orderId);
    //按条件选择性修改
    int updateByExampleSelective(@Param("record") OrdOdInvoice record, @Param("example") OrdOdInvoiceCriteria example);
    //按条件修改
    int updateByExample(@Param("record") OrdOdInvoice record, @Param("example") OrdOdInvoiceCriteria example);
    //按照主键修改
    int updateByPrimaryKeySelective(OrdOdInvoice record);
    //按照主键修改
    int updateByPrimaryKey(OrdOdInvoice record);
}