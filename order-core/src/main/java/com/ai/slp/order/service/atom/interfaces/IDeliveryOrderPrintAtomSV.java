package com.ai.slp.order.service.atom.interfaces;

import java.sql.Timestamp;
import java.util.List;

import com.ai.slp.order.dao.mapper.attach.OrdOrderProdAttach;

public interface IDeliveryOrderPrintAtomSV {
	
	/**
	 * 多个条件下多表查询商品信息
	 * @param userId
	 * @param skuId
	 * @param routeId
	 * @param state
	 * @return
	 * @author caofz
	 * @ApiDocMethod
	 * @ApiCode 
	 * @RestRelativeURL
	 */
	public List<OrdOrderProdAttach> query(String userId,String tenantId, String skuId, String routeId, 
			String state,Timestamp timeBefore,Timestamp timeAfter);
	

}
