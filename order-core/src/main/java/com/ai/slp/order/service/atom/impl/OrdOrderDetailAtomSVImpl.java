package com.ai.slp.order.service.atom.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ai.slp.order.dao.mapper.bo.OrdOdFeeTotal;
import com.ai.slp.order.dao.mapper.bo.OrdOdFeeTotalCriteria;
import com.ai.slp.order.dao.mapper.bo.OrdOdProd;
import com.ai.slp.order.dao.mapper.bo.OrdOdProdCriteria;
import com.ai.slp.order.dao.mapper.bo.OrdOdProdExtend;
import com.ai.slp.order.dao.mapper.bo.OrdOdProdExtendCriteria;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.dao.mapper.bo.OrdOrderCriteria;
import com.ai.slp.order.dao.mapper.factory.MapperFactory;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderDetailAtomSV;

@Component
public class OrdOrderDetailAtomSVImpl implements IOrdOrderDetailAtomSV {

    @Override
    public List<OrdOrder> selectByExample(OrdOrderCriteria example) {
        return  MapperFactory.getOrdOrderMapper().selectByExample(example);
    }

    @Override
    public List<OrdOdProd> getOrdOdProdInfo(OrdOdProdCriteria example) {
        return MapperFactory.getOrdOdProdMapper().selectByExample(example);
    }

    @Override
    public List<OrdOdProdExtend> getOrdOdProdExtend(OrdOdProdExtendCriteria example) {
       
        return null;
    }

    @Override
    public List<OrdOdFeeTotal> getOrdOdFeeTotalInfo(OrdOdFeeTotalCriteria example) {
        
        return MapperFactory.getOrdOdFeeTotalMapper().selectByExample(example);
    }

}
