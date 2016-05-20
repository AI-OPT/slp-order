package com.ai.slp.order.service.atom.impl;

import org.springframework.stereotype.Component;

import com.ai.slp.order.dao.mapper.bo.OrdOdProdExtend;
import com.ai.slp.order.dao.mapper.factory.MapperFactory;
import com.ai.slp.order.service.atom.interfaces.IOrdOdProdExtendAtomSV;

@Component
public class OrdOdProdExtendAtomSVImpl implements IOrdOdProdExtendAtomSV {

    @Override
    public int insertSelective(OrdOdProdExtend record) {
        return MapperFactory.getOrdOdProdExtendMapper().insertSelective(record);
    }

}
