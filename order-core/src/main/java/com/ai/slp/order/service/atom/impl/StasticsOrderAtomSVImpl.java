package com.ai.slp.order.service.atom.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.slp.order.api.stasticsorder.param.StasticsOrderRequest;
import com.ai.slp.order.dao.mapper.attach.StasticOrdOrderAttach;
import com.ai.slp.order.dao.mapper.attach.StasticOrdOrderAttachMapper;
import com.ai.slp.order.dao.mapper.interfaces.OrdOrderMapper;
import com.ai.slp.order.service.atom.interfaces.IStasticsOrderAtomSV;

/**
 * 该功能现查询elasticSearch,现方法已废弃
 * @date 2017年5月3日 
 * @author caofz
 */
@Component
public class StasticsOrderAtomSVImpl implements IStasticsOrderAtomSV {
	@Autowired
	OrdOrderMapper ordOrderMapper;
	@Autowired
    private StasticOrdOrderAttachMapper stasticOrdOrderAttachMapper;

	@Override
	public List<StasticOrdOrderAttach> getStasticOrd(StasticsOrderRequest request) {
		return stasticOrdOrderAttachMapper.getStaticOrdOrder((request.getPageNo() - 1)* request.getPageSize(), 
				request.getPageSize(),request.getState(),
				request.getOrderId(),request.getProdName(),
				request.getUserName(), 
				request.getSupplierId(),request.getTenantId(), 
				request.getOrderTimeStart(),request.getOrderTimeEnd());
	}

	@Override
	public int queryCount(StasticsOrderRequest request) {
		return stasticOrdOrderAttachMapper.getCount(request.getOrderId(), request.getUserName(), 
				request.getSupplierId(), request.getProdName(), request.getTenantId(), 
				request.getState(), request.getOrderTimeStart(), request.getOrderTimeEnd()); 
				
	}

}
