package com.ai.slp.order.service.business.interfaces;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.slp.order.api.delivergoods.param.DeliverGoodsPrintInfosRequest;
import com.ai.slp.order.api.delivergoods.param.DeliverGoodsPrintRequest;
import com.ai.slp.order.api.delivergoods.param.DeliverGoodsPrintResponse;

public interface IDeliverGoodsPrintBusiSV {
	
	public DeliverGoodsPrintResponse deliverGoodsQuery(DeliverGoodsPrintRequest request) throws BusinessException, SystemException;
	
	public void deliverGoodsPrint(DeliverGoodsPrintInfosRequest request) throws BusinessException, SystemException;

}
