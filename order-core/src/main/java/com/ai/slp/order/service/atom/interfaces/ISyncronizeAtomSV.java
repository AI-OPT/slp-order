package com.ai.slp.order.service.atom.interfaces;

import com.ai.slp.order.dao.mapper.bo.OrdBalacneIf;
import com.ai.slp.order.dao.mapper.bo.OrdOdFeeTotal;
import com.ai.slp.order.dao.mapper.bo.OrdOdInvoice;
import com.ai.slp.order.dao.mapper.bo.OrdOdLogistics;
import com.ai.slp.order.dao.mapper.bo.OrdOdProd;
import com.ai.slp.order.dao.mapper.bo.OrdOdProdExtend;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;

public interface ISyncronizeAtomSV {

	int insertSelective(OrdBalacneIf record);

	int insertSelective(OrdOdFeeTotal record);

	int insertSelective(OrdOdInvoice record);

	int insertSelective(OrdOdLogistics record);

	int insertSelective(OrdOdProdExtend record);

	int insertSelective(OrdOdProd record);

	int insertSelective(OrdOrder record);
}
