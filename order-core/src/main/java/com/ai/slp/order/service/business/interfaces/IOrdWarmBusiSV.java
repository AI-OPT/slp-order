package com.ai.slp.order.service.business.interfaces;

import com.ai.opt.base.vo.PageInfo;
import com.ai.slp.order.api.warmorder.param.OrderWarmListVo;
import com.ai.slp.order.api.warmorder.param.OrderWarmRequest;
import com.ai.slp.order.api.warmorder.param.OrderWarmVo;

public interface IOrdWarmBusiSV {
	
	public PageInfo<OrderWarmListVo> selectWarmOrdPage(OrderWarmRequest request);
	
	public OrderWarmVo selectWarmOrdDetail(String tenantId,long orderId);

}
