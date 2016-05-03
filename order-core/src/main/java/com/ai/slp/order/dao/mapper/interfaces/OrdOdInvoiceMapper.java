package com.ai.slp.order.dao.mapper.interfaces;

import com.ai.slp.order.dao.mapper.bo.OrdOdInvoice;
import com.ai.slp.order.dao.mapper.bo.OrdOdInvoiceCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrdOdInvoiceMapper {
    int countByExample(OrdOdInvoiceCriteria example);

    int deleteByExample(OrdOdInvoiceCriteria example);

    int insert(OrdOdInvoice record);

    int insertSelective(OrdOdInvoice record);

    List<OrdOdInvoice> selectByExample(OrdOdInvoiceCriteria example);

    int updateByExampleSelective(@Param("record") OrdOdInvoice record, @Param("example") OrdOdInvoiceCriteria example);

    int updateByExample(@Param("record") OrdOdInvoice record, @Param("example") OrdOdInvoiceCriteria example);
}