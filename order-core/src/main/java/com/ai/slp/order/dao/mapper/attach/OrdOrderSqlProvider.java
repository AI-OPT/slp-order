package com.ai.slp.order.dao.mapper.attach;

import java.util.Map;

import com.ai.paas.ipaas.util.StringUtil;
import com.ai.slp.order.constants.OrdersConstants;

public class OrdOrderSqlProvider {

    /**
     * 运营后台订单列表查询信息
     * 
     * @param param
     * @return
     * @author caofz
     */
    public String behindQueryOrdOrder(Map<String, Object> param) {
    	String states = param.containsKey("states") ? (String) param.get("states") : null;
        String routeId = param.containsKey("routeId") ? (String) param.get("routeId") : null;
        StringBuffer seqBuffer = new StringBuffer();
        seqBuffer.append("select oo.order_id,oo.state,oo.tenant_id,oo.chl_id,oo.delivery_flag,contact_tel,"
        		        		+ "oo.user_id,oo.user_name,oo.user_tel,discount_fee,adjust_fee from ord_order oo,ord_od_logistics ol,ord_od_fee_total of "
        		        		+ "where oo.order_id IN (select * from(select oo.order_id ");
        seqBuffer = getConnectStr(param, seqBuffer);  
        if(StringUtil.isBlank(states)) {  //空
        		if(StringUtil.isBlank(routeId)) {
        			seqBuffer.append("from ord_order oo,ord_od_logistics ol where"
    		                + " oo.tenant_id= '"+ param.get("tenantId") + "' and oo.flag "
    		                		+ "in('"+ OrdersConstants.OrdOrder.Flag.UPPLATFORM + "','"+ OrdersConstants.OrdOrder.Flag.OFC_ACTUAL_TIME+"')"
    		                );
    		         seqBuffer = getConnectStr(param, seqBuffer); 
        			seqBuffer.append(" and oo.order_id=ol.order_id and oo.PARENT_ORDER_ID=0");
        		}else {
        			seqBuffer.append("from ord_order oo,ord_order od,ord_od_logistics ol where"
    		                + " oo.tenant_id= '"+ param.get("tenantId") + "' and oo.flag "
    		                		+ "in('"+ OrdersConstants.OrdOrder.Flag.UPPLATFORM + "','"+ OrdersConstants.OrdOrder.Flag.OFC_ACTUAL_TIME+"')"
    		                );
    		         seqBuffer = getConnectStr(param, seqBuffer); 
        			seqBuffer.append(" and od.route_id = '" + routeId+"'");
                	seqBuffer.append(" and oo.order_id=od.PARENT_ORDER_ID and oo.order_id=ol.order_id");
        		}
        }else if (!StringUtil.isBlank(states) && (OrdersConstants.OrdOrder.State.WAIT_PAY.equals(states)||
        		OrdersConstants.OrdOrder.State.CANCEL.equals(states))){   //父状态
        	seqBuffer.append("from ord_order oo,ord_od_logistics ol where"
	                + " oo.tenant_id= '"+ param.get("tenantId") + "' and oo.flag "
	                		+ "in('"+ OrdersConstants.OrdOrder.Flag.UPPLATFORM + "','"+ OrdersConstants.OrdOrder.Flag.OFC_ACTUAL_TIME+"')"
	                );
	         seqBuffer = getConnectStr(param, seqBuffer);
        	seqBuffer.append(" and oo.state in(" + states + ")");
        	seqBuffer.append(" and oo.order_id=ol.order_id and oo.order_id=of.order_id");
        }else {  
        	seqBuffer.append("from ord_order oo,ord_order od,ord_od_logistics ol where"
	                + " oo.tenant_id= '"+ param.get("tenantId") + "' and oo.flag "
	                		+ "in('"+ OrdersConstants.OrdOrder.Flag.UPPLATFORM + "','"+ OrdersConstants.OrdOrder.Flag.OFC_ACTUAL_TIME+"')"
	                );
	        seqBuffer = getConnectStr(param, seqBuffer);
        	if (!StringUtil.isBlank(routeId)) {
        		seqBuffer.append(" and od.route_id = '" + routeId+"'");
        	}
        	seqBuffer.append(" and od.state in(" + states + ")");
        	seqBuffer.append(" and oo.order_id=od.PARENT_ORDER_ID and oo.order_id=ol.order_id");
        }
        seqBuffer.append(" group by oo.order_id order by oo.order_time desc limit "
        + param.get("pageCount") + "," + param.get("pageSize")+") as t) and oo.order_id=ol.order_id and oo.order_id=of.order_id");
        return seqBuffer.toString();
    }
    
    /**
     * 多表查询订单个数
     */
    public String behindCount(Map<String, Object> param) {
    	String states = param.containsKey("states") ? (String) param.get("states") : null;
        String routeId = param.containsKey("routeId") ? (String) param.get("routeId") : null;
        StringBuffer seqBuffer = new StringBuffer();
        if(StringUtil.isBlank(states)) {  //空
    		if(StringUtil.isBlank(routeId)) {
    			seqBuffer.append("select count(DISTINCT oo.ORDER_ID) from ord_order oo,ord_od_logistics ol where"
    	                + " oo.tenant_id= '"+ param.get("tenantId") + "'"
    	                +" and oo.flag in('"+ OrdersConstants.OrdOrder.Flag.UPPLATFORM + "','"+ OrdersConstants.OrdOrder.Flag.OFC_ACTUAL_TIME+"')");
    			seqBuffer = getConnectStr(param, seqBuffer);
    			seqBuffer.append(" and oo.PARENT_ORDER_ID=0 and oo.order_id=ol.order_id");
    		}else {
    			seqBuffer.append("select count(DISTINCT oo.ORDER_ID) from ord_order oo,ord_order od,ord_od_logistics ol,ord_od_fee_total of where"
    	                + " oo.tenant_id= '"+ param.get("tenantId") + "'"
    	                +" and oo.flag in('"+ OrdersConstants.OrdOrder.Flag.UPPLATFORM + "','"+ OrdersConstants.OrdOrder.Flag.OFC_ACTUAL_TIME+"')");
    			seqBuffer = getConnectStr(param, seqBuffer);
    			seqBuffer.append(" and od.route_id = '" + routeId+"'");
            	seqBuffer.append(" and oo.order_id=od.PARENT_ORDER_ID and oo.order_id=ol.order_id");
    		}
    	}else if (!StringUtil.isBlank(states) && (OrdersConstants.OrdOrder.State.WAIT_PAY.equals(states)||
            		OrdersConstants.OrdOrder.State.CANCEL.equals(states))){   //父状态
    			seqBuffer.append("select count(DISTINCT oo.ORDER_ID) from ord_order oo,ord_od_logistics ol,ord_od_fee_total of where"
	                + " oo.tenant_id= '"+ param.get("tenantId") + "'"
	               // +" and oo.flag= '"+ OrdersConstants.OrdOrder.Flag.UPPLATFORM + "'");
	                +" and oo.flag in('"+ OrdersConstants.OrdOrder.Flag.UPPLATFORM + "','"+ OrdersConstants.OrdOrder.Flag.OFC_ACTUAL_TIME+"')");
    			seqBuffer = getConnectStr(param, seqBuffer);
            	seqBuffer.append(" and oo.state in(" + states + ")");
            	seqBuffer.append(" and oo.order_id=ol.order_id and oo.order_id=of.order_id");
         }else {
        	 	seqBuffer.append("select count(DISTINCT oo.ORDER_ID) from ord_order oo,ord_order od,ord_od_logistics ol where"
 	                + " oo.tenant_id= '"+ param.get("tenantId") + "'"
 	               // +" and oo.flag= '"+ OrdersConstants.OrdOrder.Flag.UPPLATFORM + "'");
 	               +" and oo.flag in('"+ OrdersConstants.OrdOrder.Flag.UPPLATFORM + "','"+ OrdersConstants.OrdOrder.Flag.OFC_ACTUAL_TIME+"')");
        	 	seqBuffer = getConnectStr(param, seqBuffer);
            	if (!StringUtil.isBlank(routeId)) {
            		seqBuffer.append(" and od.route_id = '" + routeId+"'");
            	}
            	seqBuffer.append(" and od.state in(" + states + ")");
            	seqBuffer.append(" and oo.order_id=od.PARENT_ORDER_ID and oo.order_id=ol.order_id");
         }
         return seqBuffer.toString();
    }
    
    //组合连接信息
    private StringBuffer getConnectStr(Map<String, Object> param, StringBuffer seqBuffer) {
    	if (param.get("orderId") != null)
        	seqBuffer.append(" and oo.order_id =" + param.get("orderId"));
        String chlId = param.containsKey("chlId") ? (String) param.get("chlId") : null;
        if (!StringUtil.isBlank(chlId))
            seqBuffer.append(" and oo.chl_id = " + chlId);
        String userName = param.containsKey("userName") ? (String) param.get("userName") : null;
        if (!StringUtil.isBlank(userName))
        	seqBuffer.append(" and oo.user_name like'" + "%"+userName+"%"+"'");
        String contactTel = param.containsKey("contactTel") ? (String) param.get("contactTel") : null;
        if (!StringUtil.isBlank(contactTel))
        	seqBuffer.append(" and ol.contact_tel like '" + "%"+contactTel+"%"+"'");
        String deliveryFlag = param.containsKey("deliveryFlag") ? (String) param.get("deliveryFlag") : null;
        if (!StringUtil.isBlank(deliveryFlag))
            seqBuffer.append(" and oo.delivery_flag='" + deliveryFlag+"'");
        if (param.get("orderTimeBegin") != null && param.get("orderTimeEnd") == null) {
            seqBuffer.append(" and oo.order_time >= '" + param.get("orderTimeBegin")+ "'");
        }
        if (param.get("orderTimeBegin") == null && param.get("orderTimeEnd") != null) {
            seqBuffer.append(" and oo.order_time <= '" + param.get("orderTimeEnd") + "'");
        }
        if (param.get("orderTimeBegin") != null && param.get("orderTimeEnd") != null) {
            seqBuffer.append(" and oo.order_time between '" + param.get("orderTimeBegin")
                    + "' and '" + param.get("orderTimeEnd") + "'");
        }
    	return seqBuffer;
    }
    
}
