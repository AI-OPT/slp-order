package com.ai.slp.order.util;

import java.sql.Timestamp;

import com.ai.slp.order.vo.OrderStateChgVo;

/**
 * 订单轨迹参数封装
 * @date 2017年3月22日 
 * @author caofz
 */
public class OrderStateChgUtil {
	 public static OrderStateChgVo getOrderStateChg(Long orderId, String tenantId, String orgState, String newState,
	            String chgDesc, String orgId, String operId, String operName, Timestamp timestamp){
		   OrderStateChgVo stateChgVo=new OrderStateChgVo();
           stateChgVo.setOrderId(orderId);
           stateChgVo.setTenantId(tenantId);
           stateChgVo.setOrgState(orgState);
           stateChgVo.setNewState(newState);
           stateChgVo.setChgDesc(chgDesc);
           stateChgVo.setOrgId(orgId);
           stateChgVo.setOperId(operId);
           stateChgVo.setOperName(operName);
           stateChgVo.setTimestamp(timestamp);
	       return stateChgVo;
	    }

}
