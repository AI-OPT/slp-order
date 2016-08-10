package com.ai.slp.order.service.atom.interfaces;

import java.util.List;

import com.ai.slp.order.dao.mapper.bo.OrdOdFeeTotal;
import com.ai.slp.order.dao.mapper.bo.OrdOdFeeTotalCriteria;

public interface IOrdOdFeeTotalAtomSV {
    List<OrdOdFeeTotal> selectByExample(OrdOdFeeTotalCriteria example);

    public OrdOdFeeTotal selectByOrderId(String tenantId, long orderId);

    int insertSelective(OrdOdFeeTotal record);

    int updateByOrderId(OrdOdFeeTotal ordOdFeeTotal);
}
