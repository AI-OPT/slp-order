package com.ai.slp.order.service.business.interfaces;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.slp.order.api.aftersaleorder.param.OrderJuageRequest;
import com.ai.slp.order.api.aftersaleorder.param.OrderJuageResponse;

public interface IOrderAfterSaleJudgeBusiSV {
	
	public OrderJuageResponse judge(OrderJuageRequest request) throws BusinessException, SystemException;

}
