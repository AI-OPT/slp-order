package com.ai.slp.order.service.business.interfaces;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.slp.order.api.ofcactual.param.OfcOrderCreateRequest;

public interface IOfcOrderActualBusiSV {
	//ofc销售订单创建
	public void orderCreate(OfcOrderCreateRequest request) throws BusinessException, SystemException;

}
