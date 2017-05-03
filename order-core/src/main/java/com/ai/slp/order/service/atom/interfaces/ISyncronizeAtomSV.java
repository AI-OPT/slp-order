package com.ai.slp.order.service.atom.interfaces;

import org.apache.ibatis.annotations.Param;

import com.ai.slp.order.dao.mapper.bo.OrdBalacneIf;
import com.ai.slp.order.dao.mapper.bo.OrdBalacneIfCriteria;
import com.ai.slp.order.dao.mapper.bo.OrdOdFeeTotal;
import com.ai.slp.order.dao.mapper.bo.OrdOdFeeTotalCriteria;
import com.ai.slp.order.dao.mapper.bo.OrdOdInvoice;
import com.ai.slp.order.dao.mapper.bo.OrdOdInvoiceCriteria;
import com.ai.slp.order.dao.mapper.bo.OrdOdLogistics;
import com.ai.slp.order.dao.mapper.bo.OrdOdLogisticsCriteria;
import com.ai.slp.order.dao.mapper.bo.OrdOdProd;
import com.ai.slp.order.dao.mapper.bo.OrdOdProdCriteria;
import com.ai.slp.order.dao.mapper.bo.OrdOdProdExtend;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.dao.mapper.bo.OrdOrderCriteria;
/**
 * 订单信息同步
 */
public interface ISyncronizeAtomSV {
	
	int insertSelective(OrdBalacneIf record);

	int insertSelective(OrdOdFeeTotal record);

	int insertSelective(OrdOdInvoice record);

	int insertSelective(OrdOdLogistics record);

	int insertSelective(OrdOdProdExtend record);

	int insertSelective(OrdOdProd record);

	int insertSelective(OrdOrder record);

	int countByExample(OrdOrderCriteria example);

	int countByExample(OrdOdProdCriteria example);

	int countByExample(OrdOdLogisticsCriteria example);

	int countByExample(OrdOdInvoiceCriteria example);

	int countByExample(OrdOdFeeTotalCriteria example);

	int countByExample(OrdBalacneIfCriteria example);

	int updateByExampleSelective(@Param("record")  OrdOrder record, @Param("example") OrdOrderCriteria example);

	int updateByExampleSelective(@Param("record") OrdOdLogistics record, @Param("example") OrdOdLogisticsCriteria example);

	int updateByExampleSelective(@Param("record") OrdOdProd record, @Param("example") OrdOdProdCriteria example);

	int updateByExampleSelective(@Param("record") OrdOdInvoice record, @Param("example") OrdOdInvoiceCriteria example);

	int updateByExampleSelective(@Param("record") OrdOdFeeTotal record,
			@Param("example") OrdOdFeeTotalCriteria example);

	int updateByExampleSelective(@Param("record") OrdBalacneIf record, @Param("example") OrdBalacneIfCriteria example);
}
