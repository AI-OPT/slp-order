package com.ai.slp.order.service.business.interfaces.search;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.slp.order.api.sesdata.param.SesDataRequest;

public interface IOrderIndexBusiSV {
	 //新增订单信息
	 public boolean insertSesData(SesDataRequest request) throws BusinessException, SystemException; 
	 

}
