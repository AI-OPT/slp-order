package com.ai.slp.order.service.atom.interfaces;

import com.ai.slp.order.dao.mapper.bo.OrdOdFeeOffset;

public interface IOrdOdFeeOffsetAtomSV {
    int insertSelective(OrdOdFeeOffset record);
}
