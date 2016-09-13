package com.ai.slp.order.service.atom.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.dao.mapper.attach.DeliveryOrderPrintAttachMapper;
import com.ai.slp.order.dao.mapper.attach.OrdOrderProdAttach;
import com.ai.slp.order.dao.mapper.bo.DeliverInfoProd;
import com.ai.slp.order.dao.mapper.bo.DeliverInfoProdCriteria;
import com.ai.slp.order.dao.mapper.bo.OrdOdDeliverInfo;
import com.ai.slp.order.dao.mapper.bo.OrdOdDeliverInfoCriteria;
import com.ai.slp.order.dao.mapper.factory.MapperFactory;
import com.ai.slp.order.service.atom.interfaces.IDeliveryOrderPrintAtomSV;

@Component
public class DeliveryOrderPrintAtomSVImpl implements IDeliveryOrderPrintAtomSV {
	
	@Autowired
	DeliveryOrderPrintAttachMapper deliveryOrderPrintAttachMapper;

	@Override
	public List<OrdOrderProdAttach> query(String userId,String tenantId, String skuId, String routeId, 
			long orderId,String state,Timestamp timeBefore,Timestamp timeAfter,String cusServiceFlag) {
		return deliveryOrderPrintAttachMapper.query(userId, tenantId,skuId,routeId,orderId,
				state,timeBefore,timeAfter,OrdersConstants.OrdOrder.cusServiceFlag.NO);
	}

	@Override
	public int insertSelective(OrdOdDeliverInfo record) {
		return MapperFactory.getOrdOdDeliverInfoMapper().insertSelective(record);
	}

	@Override
	public List<OrdOdDeliverInfo> selectByExample(OrdOdDeliverInfoCriteria example) {
		return MapperFactory.getOrdOdDeliverInfoMapper().selectByExample(example);
	}

	@Override
	public int insert(DeliverInfoProd record) {
		return MapperFactory.getDeliverInfoProdMapper().insert(record);
	}

	@Override
	public int insertSelective(DeliverInfoProd record) {
		return MapperFactory.getDeliverInfoProdMapper().insertSelective(record);
	}

	@Override
	public List<DeliverInfoProd> selectByExample(DeliverInfoProdCriteria example) {
		return MapperFactory.getDeliverInfoProdMapper().selectByExample(example);
	}
}
