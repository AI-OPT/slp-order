package com.ai.slp.order.service.business.interfaces;

import com.ai.slp.order.api.orderrule.param.OrderRuleDetailResponse;
import com.ai.slp.order.api.orderrule.param.OrderRuleRequest;
import com.ai.slp.order.api.orderrule.param.OrderRuleResponse;

public interface IOrdRuleBusiSV {
	public OrderRuleResponse saveOrderRuleSetting(OrderRuleRequest request);
	public OrderRuleDetailResponse findOrderRuleDetail();
}
