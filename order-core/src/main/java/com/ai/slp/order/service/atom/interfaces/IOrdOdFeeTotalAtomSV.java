package com.ai.slp.order.service.atom.interfaces;

import java.util.List;

import com.ai.slp.order.dao.mapper.bo.OrdOdFeeTotal;
import com.ai.slp.order.dao.mapper.bo.OrdOdFeeTotalCriteria;

public interface IOrdOdFeeTotalAtomSV {
    List<OrdOdFeeTotal> selectByExample(OrdOdFeeTotalCriteria example);
}
