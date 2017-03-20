package com.ai.slp.order.service.atom.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.util.StringUtil;
import com.ai.slp.order.dao.mapper.bo.OrdOdLogistics;
import com.ai.slp.order.dao.mapper.bo.OrdOdLogisticsCriteria;
import com.ai.slp.order.dao.mapper.interfaces.OrdOdLogisticsMapper;
import com.ai.slp.order.service.atom.interfaces.IOrdOdLogisticsAtomSV;

@Component
public class OrdOdLogisticsAtomSVImpl implements IOrdOdLogisticsAtomSV {
	
	@Autowired
	OrdOdLogisticsMapper ordOdLogisticsMapper;

	@Override
	public int insertSelective(OrdOdLogistics record) {
		return ordOdLogisticsMapper.insertSelective(record);
	}

	@Override
	public List<OrdOdLogistics> selectByExample(OrdOdLogisticsCriteria example) {
		return ordOdLogisticsMapper.selectByExample(example);
	}

	@Override
	public OrdOdLogistics selectByOrd(String tenantId, long orderId) {
		OrdOdLogisticsCriteria example = new OrdOdLogisticsCriteria();
		OrdOdLogisticsCriteria.Criteria param = example.createCriteria();
        if(!StringUtil.isBlank(tenantId)){
        	param.andTenantIdEqualTo(tenantId);
        }
        param.andOrderIdEqualTo(orderId);
        List<OrdOdLogistics> list = ordOdLogisticsMapper.selectByExample(example);
        if(!CollectionUtil.isEmpty(list)){
        	return list.get(0);
        }
        return null;
	}

	@Override
	public int updateByPrimaryKey(OrdOdLogistics record) {
		return ordOdLogisticsMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByExampleSelective(OrdOdLogistics record, OrdOdLogisticsCriteria example) {
		return ordOdLogisticsMapper.updateByExampleSelective(record, example);
	}

	@Override
	public int countByExample(OrdOdLogisticsCriteria example) {
		return ordOdLogisticsMapper.countByExample(example);
	}

}
