package com.ai.slp.order.service.atom.interfaces;

import java.util.List;

import com.ai.slp.order.api.stasticsorder.param.StasticsOrderRequest;
import com.ai.slp.order.dao.mapper.attach.StasticOrdOrderAttach;

public interface IStasticsOrderAtomSV {
	
	public List<StasticOrdOrderAttach> getStasticOrd(StasticsOrderRequest request);
	
    public int queryCount(StasticsOrderRequest request);

}
