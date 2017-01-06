package com.ai.slp.order.service.business.interfaces;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.slp.order.api.synchronize.params.OrderSynchronizeVo;

public interface ISyncronizeBusiSV {

	public int orderSynchronize(OrderSynchronizeVo request) throws BusinessException, SystemException;

}
