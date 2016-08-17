package com.ai.slp.order.service.atom.interfaces;

import java.util.List;

import com.ai.slp.order.dao.mapper.bo.OrdOdLogistics;
import com.ai.slp.order.dao.mapper.bo.OrdOdLogisticsCriteria;

public interface IOrdOdLogisticsAtomSV {
	
	 int insertSelective(OrdOdLogistics record);
	 
	 List<OrdOdLogistics> selectByExample(OrdOdLogisticsCriteria example);
}
