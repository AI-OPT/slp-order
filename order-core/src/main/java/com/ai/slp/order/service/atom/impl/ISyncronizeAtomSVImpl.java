package com.ai.slp.order.service.atom.impl;

import org.springframework.stereotype.Component;

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

	@Override
	public int countByExample(OrdOrderCriteria example) {
		return MapperFactory.getOrdOrderMapper().countByExample(example);
	}

	@Override
	public int countByExample(OrdOdProdCriteria example) {
		return MapperFactory.getOrdOdProdMapper().countByExample(example);
	}

	@Override
	public int countByExample(OrdOdLogisticsCriteria example) {
		return MapperFactory.getOrdOdLogisticsMapper().countByExample(example);
	}

	@Override
	public int countByExample(OrdOdInvoiceCriteria example) {
		return MapperFactory.getOrdOdInvoiceMapper().countByExample(example);
	}

	@Override
	public int countByExample(OrdOdFeeTotalCriteria example) {
		return MapperFactory.getOrdOdFeeTotalMapper().countByExample(example);
	}

	@Override
	public int countByExample(OrdBalacneIfCriteria example) {
		return MapperFactory.getOrdBalacneIfMapper().countByExample(example);
	}

	@Override
	public int updateByExampleSelective(OrdOrder  record, OrdOrderCriteria example) {
		return MapperFactory.getOrdOrderMapper().updateByExampleSelective(record, example);
	}

	@Override
	public int updateByExampleSelective(OrdOdLogistics record, OrdOdLogisticsCriteria example) {
		return MapperFactory.getOrdOdLogisticsMapper().updateByExampleSelective(record, example);
	}

	@Override
	public int updateByExampleSelective(OrdOdProd record, OrdOdProdCriteria example) {
		return MapperFactory.getOrdOdProdMapper().updateByExampleSelective(record, example);
	}

	@Override
	public int updateByExampleSelective(OrdOdInvoice record, OrdOdInvoiceCriteria example) {
		return MapperFactory.getOrdOdInvoiceMapper().updateByExampleSelective(record, example);
	}

	@Override
	public int updateByExampleSelective(OrdOdFeeTotal record, OrdOdFeeTotalCriteria example) {
		return MapperFactory.getOrdOdFeeTotalMapper().updateByExampleSelective(record, example);
	}

	@Override
	public int updateByExampleSelective(OrdBalacneIf record, OrdBalacneIfCriteria example) {
		return MapperFactory.getOrdBalacneIfMapper().updateByExampleSelective(record, example);
	}

}
