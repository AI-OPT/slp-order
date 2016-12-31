package com.ai.slp.order.service.business.interfaces;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.slp.order.vo.OfcCodeRequst;
import com.ai.slp.order.vo.OrdOdProdVo;
import com.ai.slp.order.vo.OrderOfcVo;

public interface IOfcBusiSV {

	public void insertOrdOrder(OrderOfcVo request) throws BusinessException, SystemException;

	public void insertOrdOdProdOfc(OrdOdProdVo request) throws BusinessException, SystemException;
	
	public String parseOfcCode(OfcCodeRequst request) throws BusinessException, SystemException;

}
