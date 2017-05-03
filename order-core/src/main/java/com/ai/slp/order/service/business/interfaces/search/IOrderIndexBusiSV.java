package com.ai.slp.order.service.business.interfaces.search;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.slp.order.api.orderlist.param.BehindQueryOrderListRequest;
import com.ai.slp.order.api.sesdata.param.SesDataByPageRequest;
import com.ai.slp.order.api.sesdata.param.SesDataRequest;
import com.ai.slp.order.api.sesdata.param.SesDataResponse;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;

public interface IOrderIndexBusiSV {
	 //订单同步数据更新elasticSearch
	 public void orderSynchDataToSes(SesDataRequest request) throws BusinessException, SystemException; 
	 //刷新搜索引擎状态数据
	 public void refreshStateData(OrdOrder ordOrder,OrdOrder pOrder) throws BusinessException, SystemException; 
	 //分页刷新数据
	 public SesDataResponse insertSesDataByPage(SesDataByPageRequest request) throws BusinessException, SystemException; 

	 //删除es数据
	 public void deleteSesData(BehindQueryOrderListRequest request) throws BusinessException, SystemException;
}
