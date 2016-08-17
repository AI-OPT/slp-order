package com.ai.slp.order.service.atom.interfaces;

import java.util.List;

import com.ai.slp.order.dao.mapper.bo.OrdRule;

public interface IOrdRuleAtomSV {
	public void saveOrderRule(OrdRule ordRule);
	public void updateOrderRuleSel(OrdRule ordRule);
	public OrdRule getOrdRule(String orderRuleId);
	public List<OrdRule> queryOrdRule(List<String> orderRuleIds);
}
