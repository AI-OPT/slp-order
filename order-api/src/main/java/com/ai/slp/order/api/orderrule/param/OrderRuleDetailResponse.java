package com.ai.slp.order.api.orderrule.param;

import java.io.Serializable;

import com.ai.opt.base.vo.BaseResponse;

public class OrderRuleDetailResponse extends BaseResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private OrderRuleDetailVo orderRuleDetailVo;
	public OrderRuleDetailVo getOrderRuleDetailVo() {
		return orderRuleDetailVo;
	}
	public void setOrderRuleDetailVo(OrderRuleDetailVo orderRuleDetailVo) {
		this.orderRuleDetailVo = orderRuleDetailVo;
	}

}
