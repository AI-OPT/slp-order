package com.ai.slp.order.service.atom.interfaces;

import com.ai.slp.order.dao.mapper.bo.OrdOdStateChg;

public interface IOrdOdStateChgAtomSV {

    int insertSelective(OrdOdStateChg record);
}
