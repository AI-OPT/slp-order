package com.ai.slp.order.service.atom.impl;

import org.springframework.stereotype.Component;

import com.ai.slp.order.dao.mapper.bo.OrdOdFeeOffset;
import com.ai.slp.order.dao.mapper.factory.MapperFactory;
import com.ai.slp.order.service.atom.interfaces.IOrdOdFeeOffsetAtomSV;

@Component
public class OrdOdFeeOffsetAtomSVImpl implements IOrdOdFeeOffsetAtomSV {

    @Override
    public int insertSelective(OrdOdFeeOffset record) {
        return MapperFactory.getOrdOdFeeOffsetMapper().insertSelective(record);
    }

}
