package com.ai.slp.order.service.atom.interfaces;

import com.ai.opt.base.vo.PageInfo;
import com.ai.slp.order.api.warmorder.param.OrderWarmRequest;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
public interface IOrdWarmAtomSV {
	
	public PageInfo<OrdOrder> selectWarmOrdPage(OrderWarmRequest request);
	
	public OrdOrder selectWarmOrde(String tenantId,long orderid);
	

}
