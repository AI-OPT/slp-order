package com.ai.slp.order.service.atom.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ai.slp.order.dao.mapper.bo.OrdOdFeeTotal;
import com.ai.slp.order.dao.mapper.bo.OrdOdFeeTotalCriteria;

public interface IOrdOdFeeTotalAtomSV {
	List<OrdOdFeeTotal> selectByExample(OrdOdFeeTotalCriteria example);

	public OrdOdFeeTotal selectByOrderId(String tenantId, long orderId);

	int countByExample(OrdOdFeeTotalCriteria example);

	int insertSelective(OrdOdFeeTotal record);

	int updateByOrderId(OrdOdFeeTotal ordOdFeeTotal);

	int updateByExampleSelective(@Param("record") OrdOdFeeTotal record,
			@Param("example") OrdOdFeeTotalCriteria example);
}
