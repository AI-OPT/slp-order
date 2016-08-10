package com.ai.slp.order.service.atom.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ai.slp.order.dao.mapper.bo.OrdRule;
import com.ai.slp.order.dao.mapper.bo.OrdRuleCriteria;
import com.ai.slp.order.dao.mapper.factory.MapperFactory;
import com.ai.slp.order.service.atom.interfaces.IOrdRuleAtomSV;
@Component
public class OrdRuleAtomSVImpl implements IOrdRuleAtomSV {
	
	@Override
	public void saveOrderRule(OrdRule ordRule){
		MapperFactory.getOrdRuleMapper().insert(ordRule);
	}

	@Override
	public void updateOrderRuleSel(OrdRule ordRule) {
		MapperFactory.getOrdRuleMapper().updateByPrimaryKeySelective(ordRule);
		
	}

	@Override
	public OrdRule getOrdRule(String orderRuleId) {
		return MapperFactory.getOrdRuleMapper().selectByPrimaryKey(orderRuleId);
	}

	@Override
	public List<OrdRule> queryOrdRule(List<String> orderRuleIds) {
		OrdRuleCriteria example = new OrdRuleCriteria();
		//
		OrdRuleCriteria.Criteria criteria = example.createCriteria();
		//
		criteria.andOrderRuleIdIn(orderRuleIds);
		//
		return MapperFactory.getOrdRuleMapper().selectByExample(example);
	}

}
