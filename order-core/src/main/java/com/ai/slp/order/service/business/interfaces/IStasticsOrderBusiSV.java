package com.ai.slp.order.service.business.interfaces;

import com.ai.opt.base.vo.PageInfo;
import com.ai.slp.order.api.stasticsorder.param.StasticParentOrderVo;
import com.ai.slp.order.api.stasticsorder.param.StasticsOrderRequest;

public interface IStasticsOrderBusiSV {
	
	public PageInfo<StasticParentOrderVo> getStasticOrdPage(StasticsOrderRequest request);

}
