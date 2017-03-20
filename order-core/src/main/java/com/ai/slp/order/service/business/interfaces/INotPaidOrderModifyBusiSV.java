package com.ai.slp.order.service.business.interfaces;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.slp.order.api.orderpricemodify.param.OrderModifyRequest;

public interface INotPaidOrderModifyBusiSV {
	 //未支付订单修改
	 public void modify(OrderModifyRequest request)throws BusinessException,SystemException; 

}
