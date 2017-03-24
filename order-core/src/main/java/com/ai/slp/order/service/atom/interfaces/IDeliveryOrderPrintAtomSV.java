package com.ai.slp.order.service.atom.interfaces;

import java.sql.Timestamp;
import java.util.List;

import com.ai.slp.order.dao.mapper.attach.OrdOrderProdAttach;
import com.ai.slp.order.dao.mapper.bo.DeliverInfoProd;
import com.ai.slp.order.dao.mapper.bo.DeliverInfoProdCriteria;
import com.ai.slp.order.dao.mapper.bo.OrdOdDeliverInfo;
import com.ai.slp.order.dao.mapper.bo.OrdOdDeliverInfoCriteria;

public interface IDeliveryOrderPrintAtomSV {
	
	/**
	 * 多个条件下多表查询商品信息
	 * @param userId
	 * @param skuId
	 * @param routeId
	 * @param state
	 * @return
	 * @author caofz
	 */
	public List<OrdOrderProdAttach> query(String userId,String tenantId, String skuId, String routeId, 
			long orderId,String state,Timestamp timeBefore,Timestamp timeAfter,String cusServiceFlag);
	  
	int insertSelective(OrdOdDeliverInfo record);

	List<OrdOdDeliverInfo> selectByExample(OrdOdDeliverInfoCriteria example);
	
	List<OrdOdDeliverInfo> selectDeliverByPrintInfo(long orderId,String pringInfo);

	int insert(DeliverInfoProd record);

	int insertSelective(DeliverInfoProd record);
	
	List<DeliverInfoProd> selectByExample(DeliverInfoProdCriteria example);
	
	List<DeliverInfoProd> selectDeliverInfoProd(long deliverInfoId);
}
