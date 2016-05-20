package com.ai.slp.order.service.atom.impl;

import org.springframework.stereotype.Component;

import com.ai.slp.order.dao.mapper.bo.OrdOdExtend;
import com.ai.slp.order.dao.mapper.factory.MapperFactory;
import com.ai.slp.order.service.atom.interfaces.IOrdOdExtendAtomSV;

@Component
public class OrdOdExtendAtomSVImpl implements IOrdOdExtendAtomSV {

    @Override
    public int insertSelective(OrdOdExtend record) {
        return MapperFactory.getOrdOdExtendMapper().insertSelective(record);
    }

}
