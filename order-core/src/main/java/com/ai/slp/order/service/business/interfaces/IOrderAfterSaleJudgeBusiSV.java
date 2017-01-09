package com.ai.slp.order.service.business.interfaces;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.slp.order.api.aftersaleorder.param.OrderJuageRequest;
import com.ai.slp.order.api.aftersaleorder.param.OrderJuageResponse;

public interface IOrderAfterSaleJudgeBusiSV {
	
	//判断该商品对应的订单所属业务类型
	public OrderJuageResponse judge(OrderJuageRequest request) throws BusinessException, SystemException;

}
