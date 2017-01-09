package com.ai.slp.order.service.business.interfaces;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.slp.order.api.delivergoods.param.DeliverGoodsPrintInfosRequest;
import com.ai.slp.order.api.delivergoods.param.DeliverGoodsPrintRequest;
import com.ai.slp.order.api.delivergoods.param.DeliverGoodsPrintResponse;

public interface IDeliverGoodsPrintBusiSV {
	
	//发货单打印查看
	public DeliverGoodsPrintResponse deliverGoodsQuery(DeliverGoodsPrintRequest request) throws BusinessException, SystemException;
	
	//发货单打印
	public void deliverGoodsPrint(DeliverGoodsPrintInfosRequest request) throws BusinessException, SystemException;

}
