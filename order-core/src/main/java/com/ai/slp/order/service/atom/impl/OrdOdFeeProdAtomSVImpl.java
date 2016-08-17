package com.ai.slp.order.service.atom.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ai.slp.order.dao.mapper.bo.OrdOdFeeProd;
import com.ai.slp.order.dao.mapper.bo.OrdOdFeeProdCriteria;
import com.ai.slp.order.dao.mapper.factory.MapperFactory;
import com.ai.slp.order.service.atom.interfaces.IOrdOdFeeProdAtomSV;

@Component
public class OrdOdFeeProdAtomSVImpl implements IOrdOdFeeProdAtomSV {

    @Override
    public List<OrdOdFeeProd> selectByExample(OrdOdFeeProdCriteria example) {
        return MapperFactory.getOrdOdFeeProdMapper().selectByExample(example);
    }

    @Override
    public int insertSelective(OrdOdFeeProd record) {
        return MapperFactory.getOrdOdFeeProdMapper().insertSelective(record);
    }

}
