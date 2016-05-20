package com.ai.slp.order.service.atom.impl;

import org.springframework.stereotype.Component;

import com.ai.slp.order.dao.mapper.bo.OrdOdStateChg;
import com.ai.slp.order.dao.mapper.factory.MapperFactory;
import com.ai.slp.order.service.atom.interfaces.IOrdOdStateChgAtomSV;

@Component
public class OrdOdStateChgAtomSVImpl implements IOrdOdStateChgAtomSV {

    @Override
    public int insertSelective(OrdOdStateChg record) {
        return MapperFactory.getOrdOdStateChgMapper().insertSelective(record);
    }

}
