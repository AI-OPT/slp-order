package com.ai.slp.order.service.atom.impl;

import org.springframework.stereotype.Component;

import com.ai.slp.order.dao.mapper.bo.OrdBalacneIf;
import com.ai.slp.order.dao.mapper.bo.OrdOdFeeTotal;
import com.ai.slp.order.dao.mapper.bo.OrdOdInvoice;
import com.ai.slp.order.dao.mapper.bo.OrdOdLogistics;
import com.ai.slp.order.dao.mapper.bo.OrdOdProd;
import com.ai.slp.order.dao.mapper.bo.OrdOdProdExtend;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.dao.mapper.factory.MapperFactory;
import com.ai.slp.order.service.atom.interfaces.ISyncronizeAtomSV;

@Component
public class ISyncronizeAtomSVImpl implements ISyncronizeAtomSV {

	@Override
	public int insertSelective(OrdBalacneIf record) {
		return MapperFactory.getOrdBalacneIfMapper().insertSelective(record);
	}

	@Override
	public int insertSelective(OrdOdFeeTotal record) {
		return MapperFactory.getOrdOdFeeTotalMapper().insertSelective(record);
	}

	@Override
	public int insertSelective(OrdOdInvoice record) {
		return MapperFactory.getOrdOdInvoiceMapper().insertSelective(record);
	}

	@Override
	public int insertSelective(OrdOdLogistics record) {
		return MapperFactory.getOrdOdLogisticsMapper().insertSelective(record);
	}

	@Override
	public int insertSelective(OrdOdProdExtend record) {
		return MapperFactory.getOrdOdProdExtendMapper().insertSelective(record);
	}

	@Override
	public int insertSelective(OrdOdProd record) {
		return MapperFactory.getOrdOdProdMapper().insertSelective(record);
	}

	@Override
	public int insertSelective(OrdOrder record) {
		return MapperFactory.getOrdOrderMapper().insertSelective(record);
	}

}
