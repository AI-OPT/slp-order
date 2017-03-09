package com.ai.slp.order.service.business.interfaces.search;

import java.util.List;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.slp.order.api.sesdata.param.SesDataRequest;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.search.bo.OrderInfo;

public interface IOrderIndexBusiSV {
	 public List<OrderInfo> orderFillQuery(List<OrdOrder> ordList) throws BusinessException, SystemException;
	 //新增订单信息
	 public boolean insertSesData(SesDataRequest request) throws BusinessException, SystemException; 
	 

}
