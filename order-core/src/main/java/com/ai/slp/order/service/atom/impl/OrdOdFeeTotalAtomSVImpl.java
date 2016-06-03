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

    @Override
    public OrdOdFeeTotal selectByOrderId(String tenantId, long orderId) {
        OrdOdFeeTotalCriteria example = new OrdOdFeeTotalCriteria();
        example.createCriteria().andTenantIdEqualTo(tenantId).andOrderIdEqualTo(orderId);
        List<OrdOdFeeTotal> ordOdFeeTotals = MapperFactory.getOrdOdFeeTotalMapper()
                .selectByExample(example);
        return ordOdFeeTotals == null || ordOdFeeTotals.isEmpty() ? null : ordOdFeeTotals.get(0);
    }

    @Override
    public int insertSelective(OrdOdFeeTotal record) {
        return MapperFactory.getOrdOdFeeTotalMapper().insertSelective(record);
    }

    @Override
    public int updateByOrderId(OrdOdFeeTotal ordOdFeeTotal) {
        return MapperFactory.getOrdOdFeeTotalMapper().updateByPrimaryKey(ordOdFeeTotal);
    }

}
