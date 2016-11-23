package com.ai.slp.order.service.atom.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ai.slp.order.dao.mapper.bo.OrdOdLogistics;
import com.ai.slp.order.dao.mapper.bo.OrdOdLogisticsCriteria;

public interface IOrdOdLogisticsAtomSV {
	
	 int countByExample(OrdOdLogisticsCriteria example);
	
	 int insertSelective(OrdOdLogistics record);
	 
	 List<OrdOdLogistics> selectByExample(OrdOdLogisticsCriteria example);
	 
	 OrdOdLogistics selectByOrd(String tenantId,long orderId);
	 
	 int updateByPrimaryKey(OrdOdLogistics record);
	 
	 int updateByExampleSelective(@Param("record") OrdOdLogistics record, @Param("example") OrdOdLogisticsCriteria example);
}
