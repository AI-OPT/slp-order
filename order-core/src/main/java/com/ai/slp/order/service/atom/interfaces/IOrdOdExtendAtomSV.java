package com.ai.slp.order.service.atom.interfaces;

import java.util.List;

import com.ai.slp.order.dao.mapper.bo.OrdOdExtend;
import com.ai.slp.order.dao.mapper.bo.OrdOdExtendCriteria;

public interface IOrdOdExtendAtomSV {

    int insertSelective(OrdOdExtend record);
    
    List<OrdOdExtend> selectByExample(OrdOdExtendCriteria example);
}
