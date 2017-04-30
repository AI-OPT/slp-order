package com.ai.slp.order.service.business.interfaces;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.slp.order.vo.OfcCodeRequst;
import com.ai.slp.order.vo.OrdOdProdVo;
import com.ai.slp.order.vo.OrderOfcVo;

public interface IOfcBusiSV {

	/**
	 * 保存订单数据
	 * @param request
	 * @throws BusinessException
	 * @throws SystemException
	 */
	public void insertOrdOrder(OrderOfcVo request) throws BusinessException, SystemException;

	/**
	 * 保存订单商品信息
	 * @param request
	 * @throws BusinessException
	 * @throws SystemException
	 */
	public void insertOrdOdProdOfc(OrdOdProdVo request) throws BusinessException, SystemException;
	
	/**
	 * 解析ofcCode
	 * @param request
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	public String parseOfcCode(OfcCodeRequst request) throws BusinessException, SystemException;
	
	/**
	 * 匹配订单Id
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	public long parseOrderId(String downstreamOrderId)throws BusinessException, SystemException;
	

}
