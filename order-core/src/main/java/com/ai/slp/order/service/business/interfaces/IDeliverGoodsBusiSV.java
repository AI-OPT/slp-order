package com.ai.slp.order.service.business.interfaces;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.slp.order.api.delivergoods.param.DeliverGoodsRequest;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;

public interface IDeliverGoodsBusiSV {
	//订单发货
	public void deliverGoods(DeliverGoodsRequest request,OrdOrder ordOrder) throws BusinessException, SystemException;

}
