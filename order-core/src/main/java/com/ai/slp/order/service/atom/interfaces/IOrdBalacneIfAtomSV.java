package com.ai.slp.order.service.atom.interfaces;

import java.util.List;

import com.ai.slp.order.dao.mapper.bo.OrdBalacneIf;
import com.ai.slp.order.dao.mapper.bo.OrdBalacneIfCriteria;

public interface IOrdBalacneIfAtomSV {
    int insertSelective(OrdBalacneIf record);
    
    List<OrdBalacneIf> selectByExample(OrdBalacneIfCriteria example);

	OrdBalacneIf selectByOrderId(String tenantId, long orderId);
	
	 int updateByPrimaryKey(OrdBalacneIf record);;
}
