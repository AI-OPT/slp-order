package com.ai.slp.order.service.business.interfaces;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.slp.order.api.ordermodify.param.OrderModifyRequest;

public interface INotPaidOrderModifyBusiSV {
	
	 public void modify(OrderModifyRequest request)throws BusinessException,SystemException; 

}
