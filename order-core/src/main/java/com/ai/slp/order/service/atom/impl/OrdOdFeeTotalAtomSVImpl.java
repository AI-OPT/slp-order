package com.ai.slp.order.service.atom.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ai.slp.order.dao.mapper.bo.OrdOdFeeTotal;
import com.ai.slp.order.dao.mapper.bo.OrdOdFeeTotalCriteria;
import com.ai.slp.order.dao.mapper.factory.MapperFactory;
import com.ai.slp.order.service.atom.interfaces.IOrdOdFeeTotalAtomSV;

@Component
public class OrdOdFeeTotalAtomSVImpl implements IOrdOdFeeTotalAtomSV {

    @Override
    public List<OrdOdFeeTotal> selectByExample(OrdOdFeeTotalCriteria example) {
        return MapperFactory.getOrdOdFeeTotalMapper().selectByExample(example);
    }


}
