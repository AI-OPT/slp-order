package com.ai.slp.order.service.atom.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.util.StringUtil;
import com.ai.slp.order.dao.mapper.bo.OrdOdLogistics;
import com.ai.slp.order.dao.mapper.bo.OrdOdLogisticsCriteria;
import com.ai.slp.order.dao.mapper.factory.MapperFactory;
import com.ai.slp.order.service.atom.interfaces.IOrdOdLogisticsAtomSV;

@Component
public class OrdOdLogisticsAtomSVImpl implements IOrdOdLogisticsAtomSV {

	@Override
	public int insertSelective(OrdOdLogistics record) {
		return MapperFactory.getOrdOdLogisticsMapper().insertSelective(record);
	}

	@Override
	public List<OrdOdLogistics> selectByExample(OrdOdLogisticsCriteria example) {
		return MapperFactory.getOrdOdLogisticsMapper().selectByExample(example);
	}

	@Override
	public OrdOdLogistics selectByOrd(String tenantId, long orderId) {
		OrdOdLogisticsCriteria example = new OrdOdLogisticsCriteria();
		OrdOdLogisticsCriteria.Criteria param = example.createCriteria();
        if(!StringUtil.isBlank(tenantId)){
        	param.andTenantIdEqualTo(tenantId);
        }
        param.andOrderIdEqualTo(orderId);
        List<OrdOdLogistics> list = MapperFactory.getOrdOdLogisticsMapper().selectByExample(example);
        if(!CollectionUtil.isEmpty(list)){
        	return list.get(0);
        }
        return null;
	}

	@Override
	public int updateByPrimaryKey(OrdOdLogistics record) {
		return MapperFactory.getOrdOdLogisticsMapper().updateByPrimaryKey(record);
	}

}
