package com.ai.slp.order.service.atom.impl;

import org.springframework.stereotype.Component;

import com.ai.slp.order.dao.mapper.bo.OrdBalacneIf;
import com.ai.slp.order.dao.mapper.factory.MapperFactory;
import com.ai.slp.order.service.atom.interfaces.IOrdBalacneIfAtomSV;

@Component
public class OrdBalacneIfAtomSVImpl implements IOrdBalacneIfAtomSV {

    @Override
    public int insertSelective(OrdBalacneIf record) {
        return MapperFactory.getOrdBalacneIfMapper().insertSelective(record);
    }

}
