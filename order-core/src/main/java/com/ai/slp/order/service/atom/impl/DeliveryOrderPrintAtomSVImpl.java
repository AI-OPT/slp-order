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
import com.ai.slp.order.dao.mapper.interfaces.DeliverInfoProdMapper;
import com.ai.slp.order.dao.mapper.interfaces.OrdOdDeliverInfoMapper;
import com.ai.slp.order.service.atom.interfaces.IDeliveryOrderPrintAtomSV;

@Component
public class DeliveryOrderPrintAtomSVImpl implements IDeliveryOrderPrintAtomSV {
	
	@Autowired
	DeliveryOrderPrintAttachMapper deliveryOrderPrintAttachMapper;
	@Autowired
	OrdOdDeliverInfoMapper ordOdDeliverInfoMapper;
	@Autowired
	DeliverInfoProdMapper deliverInfoProdMapper;

	@Override
	public List<OrdOrderProdAttach> query(String userId,String tenantId, String skuId, String routeId, 
			long orderId,String state,Timestamp timeBefore,Timestamp timeAfter,String cusServiceFlag) {
		return deliveryOrderPrintAttachMapper.query(userId, tenantId,skuId,routeId,orderId,
				state,timeBefore,timeAfter,OrdersConstants.OrdOrder.cusServiceFlag.NO);
	}

	@Override
	public int insertSelective(OrdOdDeliverInfo record) {
		return ordOdDeliverInfoMapper.insertSelective(record);
	}

	@Override
	public List<OrdOdDeliverInfo> selectByExample(OrdOdDeliverInfoCriteria example) {
		return ordOdDeliverInfoMapper.selectByExample(example);
	}

	@Override
	public int insert(DeliverInfoProd record) {
		return deliverInfoProdMapper.insert(record);
	}

	@Override
	public int insertSelective(DeliverInfoProd record) {
		return deliverInfoProdMapper.insertSelective(record);
	}

	@Override
	public List<DeliverInfoProd> selectByExample(DeliverInfoProdCriteria example) {
		return deliverInfoProdMapper.selectByExample(example);
	}

	@Override
	public List<OrdOdDeliverInfo> selectDeliverByPrintInfo(long orderId, String pringInfo) {
		// TODO Auto-generated method stub
		OrdOdDeliverInfoCriteria example=new OrdOdDeliverInfoCriteria();
		OrdOdDeliverInfoCriteria.Criteria criteriaDeliver = example.createCriteria();
		criteriaDeliver.andOrderIdEqualTo(orderId);
		criteriaDeliver.andPrintInfoEqualTo(pringInfo);
		return ordOdDeliverInfoMapper.selectByExample(example);
	}

	@Override
	public List<DeliverInfoProd> selectDeliverInfoProd(long deliverInfoId) {
		// TODO Auto-generated method stub
		DeliverInfoProdCriteria exampleInfo=new DeliverInfoProdCriteria();
		DeliverInfoProdCriteria.Criteria criteriaInfo = exampleInfo.createCriteria();
		criteriaInfo.andDeliverInfoIdEqualTo(deliverInfoId);
		return deliverInfoProdMapper.selectByExample(exampleInfo);
	}
}
