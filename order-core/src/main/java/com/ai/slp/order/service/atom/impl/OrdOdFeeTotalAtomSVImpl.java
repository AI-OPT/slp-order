package com.ai.slp.order.service.atom.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.slp.order.dao.mapper.bo.OrdOdFeeTotal;
import com.ai.slp.order.dao.mapper.bo.OrdOdFeeTotalCriteria;
import com.ai.slp.order.dao.mapper.interfaces.OrdOdFeeTotalMapper;
import com.ai.slp.order.service.atom.interfaces.IOrdOdFeeTotalAtomSV;

@Component
public class OrdOdFeeTotalAtomSVImpl implements IOrdOdFeeTotalAtomSV {
	
	@Autowired
	private OrdOdFeeTotalMapper ordOdFeeTotalMapper;

    @Override
    public List<OrdOdFeeTotal> selectByExample(OrdOdFeeTotalCriteria example) {
        return ordOdFeeTotalMapper.selectByExample(example);
    }

    @Override
    public OrdOdFeeTotal selectByOrderId(String tenantId, long orderId) {
        OrdOdFeeTotalCriteria example = new OrdOdFeeTotalCriteria();
        example.createCriteria().andTenantIdEqualTo(tenantId).andOrderIdEqualTo(orderId);
        List<OrdOdFeeTotal> ordOdFeeTotals = ordOdFeeTotalMapper
                .selectByExample(example);
        return ordOdFeeTotals == null || ordOdFeeTotals.isEmpty() ? null : ordOdFeeTotals.get(0);
    }

    @Override
    public int insertSelective(OrdOdFeeTotal record) {
        return ordOdFeeTotalMapper.insertSelective(record);
    }

    @Override
    public int updateByOrderId(OrdOdFeeTotal ordOdFeeTotal) {
        return ordOdFeeTotalMapper.updateByPrimaryKey(ordOdFeeTotal);
    }

	@Override
	public int updateByExampleSelective(OrdOdFeeTotal record, OrdOdFeeTotalCriteria example) {
		return ordOdFeeTotalMapper.updateByExampleSelective(record, example);
	}

	@Override
	public int countByExample(OrdOdFeeTotalCriteria example) {
		return ordOdFeeTotalMapper.countByExample(example);
	}

	@Override
	public OrdOdFeeTotal selectByPrimaryKey(long orderId) {
		return ordOdFeeTotalMapper.selectByPrimaryKey(orderId);
	}

}
