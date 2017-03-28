package com.ai.slp.order.service.atom.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.slp.order.dao.mapper.bo.OrdRule;
import com.ai.slp.order.dao.mapper.bo.OrdRuleCriteria;
import com.ai.slp.order.dao.mapper.interfaces.OrdRuleMapper;
import com.ai.slp.order.service.atom.interfaces.IOrdRuleAtomSV;
@Component
public class OrdRuleAtomSVImpl implements IOrdRuleAtomSV {
	
	@Autowired
	private OrdRuleMapper ordRuleMapper;
	
	@Override
	public void saveOrderRule(OrdRule ordRule){
		ordRuleMapper.insert(ordRule);
	}

	@Override
	public void updateOrderRuleSel(OrdRule ordRule) {
		ordRuleMapper.updateByPrimaryKeySelective(ordRule);
		
	}

	@Override
	public OrdRule getOrdRule(String orderRuleId) {
		return ordRuleMapper.selectByPrimaryKey(orderRuleId);
	}

	@Override
	public List<OrdRule> queryOrdRule(List<String> orderRuleIds) {
		OrdRuleCriteria example = new OrdRuleCriteria();
		//
		OrdRuleCriteria.Criteria criteria = example.createCriteria();
		//
		criteria.andOrderRuleIdIn(orderRuleIds);
		//
		return ordRuleMapper.selectByExample(example);
	}

}
