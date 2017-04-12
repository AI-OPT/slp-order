package com.ai.slp.order.service.business.interfaces.search;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.slp.order.api.sesdata.param.SesDataRequest;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;

public interface IOrderIndexBusiSV {
	 //新增订单信息
	 public boolean insertSesData(SesDataRequest request) throws BusinessException, SystemException; 
	 //刷新搜索引擎状态数据
	 public void refreshStateData(OrdOrder ordOrder,OrdOrder pOrder) throws BusinessException, SystemException; 
	 

}
