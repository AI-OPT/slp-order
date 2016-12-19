package com.ai.slp.order.service.atom.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ai.slp.order.dao.mapper.bo.OrdParam;
import com.ai.slp.order.dao.mapper.bo.OrdParamCriteria;
import com.ai.slp.order.dao.mapper.factory.MapperFactory;
import com.ai.slp.order.service.atom.interfaces.IOrdParamAtomSV;

@Component
public class OrdParamAtomSVImpl implements IOrdParamAtomSV {

	@Override
	public List<OrdParam> selectByExample(OrdParamCriteria example) {
		return MapperFactory.getOrdParamMapper().selectByExample(example);
	}

}
