package com.ai.slp.order.service.atom.interfaces;

import com.ai.slp.order.dao.mapper.bo.OrdOdLogistics;

public interface IOrdOdLogisticsAtomSV {
	
	 int insertSelective(OrdOdLogistics record);
}
