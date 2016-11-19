package com.ai.slp.order.service.business.interfaces;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.slp.order.api.ofc.params.OrdOdProdVo;
import com.ai.slp.order.api.ofc.params.OrderOfcVo;

public interface IOfcBusiSV {

	public void insertOrdOrder(OrderOfcVo request) throws BusinessException, SystemException;

	public int insertOrdOdProdOfc(OrdOdProdVo request) throws BusinessException, SystemException;
	
	public String parseOfcCode(String request) throws BusinessException, SystemException;

}
