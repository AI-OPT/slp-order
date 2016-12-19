package com.ai.slp.order.service.atom.interfaces;

import java.util.List;

import com.ai.slp.order.dao.mapper.bo.OrdParam;
import com.ai.slp.order.dao.mapper.bo.OrdParamCriteria;

public interface IOrdParamAtomSV {

	 List<OrdParam> selectByExample(OrdParamCriteria example);
}
