package com.ai.slp.order.service.atom.interfaces;

import java.util.List;

import com.ai.slp.order.api.stasticsorder.param.StasticsOrderRequest;
import com.ai.slp.order.dao.mapper.attach.StasticOrdOrderAttach;

/**
 * (该功能现查询elasticSearch,现方法已废弃)
 * @date 2017年5月3日 
 * @author caofz
 */
public interface IStasticsOrderAtomSV {
	
	public List<StasticOrdOrderAttach> getStasticOrd(StasticsOrderRequest request);
	
    public int queryCount(StasticsOrderRequest request);

}
