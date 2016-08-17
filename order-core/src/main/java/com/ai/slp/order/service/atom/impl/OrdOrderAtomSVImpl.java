package com.ai.slp.order.service.atom.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.dao.mapper.bo.OrdOrderCriteria;
import com.ai.slp.order.dao.mapper.factory.MapperFactory;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;

@Component
public class OrdOrderAtomSVImpl implements IOrdOrderAtomSV {

    @Override
    public List<OrdOrder> selectByExample(OrdOrderCriteria example) {
        return MapperFactory.getOrdOrderMapper().selectByExample(example);
    }

    @Override
    public int countByExample(OrdOrderCriteria example) {
        return MapperFactory.getOrdOrderMapper().countByExample(example);
    }

    @Override
    public int insertSelective(OrdOrder record) {
        return MapperFactory.getOrdOrderMapper().insertSelective(record);
    }

    @Override
    public OrdOrder selectByOrderId(String tenantId, long orderId) {
        OrdOrderCriteria example = new OrdOrderCriteria();
        example.createCriteria().andTenantIdEqualTo(tenantId).andOrderIdEqualTo(orderId);
        List<OrdOrder> ordOrders = MapperFactory.getOrdOrderMapper().selectByExample(example);
        return ordOrders == null || ordOrders.isEmpty() ? null : ordOrders.get(0);
    }

    @Override
    public int updateById(OrdOrder ordOrder) {
        return MapperFactory.getOrdOrderMapper().updateByPrimaryKey(ordOrder);
    }
}
