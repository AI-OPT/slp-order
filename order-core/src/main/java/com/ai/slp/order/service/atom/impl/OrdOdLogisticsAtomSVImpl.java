package com.ai.slp.order.service.atom.impl;

import java.util.List;

import org.springframework.stereotype.Component;
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

}
