package com.ai.slp.order.service.atom.impl;

import java.util.List;

import com.ai.slp.order.dao.mapper.bo.OrdOdProd;
import com.ai.slp.order.dao.mapper.bo.OrdOdProdCriteria;
import com.ai.slp.order.dao.mapper.factory.MapperFactory;
import com.ai.slp.order.service.atom.interfaces.IOrdOdProdAtomSV;

public class OrdOdProdAtomSVImpl implements IOrdOdProdAtomSV {

    @Override
    public int insertSelective(OrdOdProd record) {
        return MapperFactory.getOrdOdProdMapper().insertSelective(record);
    }

    @Override
    public List<OrdOdProd> selectByExample(OrdOdProdCriteria example) {
        return MapperFactory.getOrdOdProdMapper().selectByExample(example);
    }

}
