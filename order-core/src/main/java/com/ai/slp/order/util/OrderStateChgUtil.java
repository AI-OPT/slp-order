package com.ai.slp.order.util;

import java.sql.Timestamp;

import com.ai.slp.order.aync.AyncExector;
import com.ai.slp.order.aync.AyncTask;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.manager.MDSClientManager;
import com.ai.slp.order.vo.OrderStateChgVo;
import com.alibaba.fastjson.JSON;

/**
 * 订单轨迹处理
 * @date 2017年3月22日 
 * @author caofz
 */
public class OrderStateChgUtil {
	
	 public static void trailProcess(Long orderId, String tenantId, 
			 String orgState, String newState,String chgDesc, String orgId, 
			 String operId, String operName, Timestamp timestamp){
		   final OrderStateChgVo stateChgVo=new OrderStateChgVo();
           stateChgVo.setOrderId(orderId);
           stateChgVo.setTenantId(tenantId);
           stateChgVo.setOrgState(orgState);
           stateChgVo.setNewState(newState);
           stateChgVo.setChgDesc(chgDesc);
           stateChgVo.setOrgId(orgId);
           stateChgVo.setOperId(operId);
           stateChgVo.setOperName(operName);
           stateChgVo.setTimestamp(timestamp);
           
           AyncExector.submit(new AyncTask(){
				@Override
				public void run() {
					MDSClientManager.getSenderClient(OrdersConstants.MDSNS.MDS_NS_ORDER_STATE_TOPIC).
					send(JSON.toJSONString(stateChgVo), 0);
				}
				
			});
	    }
	 
	
	 
	 

}
