package com.ai.slp.order.service.atom.interfaces;

import com.ai.opt.base.vo.PageInfo;
import com.ai.slp.order.api.stasticsorder.param.StasticsOrderRequest;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;

public interface IStasticsOrderAtomSV {
	
	public PageInfo<OrdOrder> getStasticOrdPage(StasticsOrderRequest request);

}
