package com.ai.slp.order.dao.mapper.attach;

import java.util.Map;

import com.ai.paas.ipaas.util.StringUtil;
import com.ai.slp.order.constants.OrdersConstants;

public class OrdOrderSqlProvider {

    /**
     * 查询我的订单信息
     * 
     * @param param
     * @return
     * @author caofz
     */
    public String queryOrdOrder(Map<String, Object> param) {
        StringBuffer seqBuffer = new StringBuffer();
        seqBuffer.append("select oo.order_id,oo.tenant_id,busi_code,order_type,state,pay_style,"
                + "update_time,order_time,total_fee,discount_fee,"
                + "adjust_fee,paid_fee,pay_fee from "
                + "ord_order oo,ord_od_fee_total of where oo.sub_flag=" + param.get("subFlag")
                + " and oo.user_id= " + param.get("userId") + " and oo.TENANT_ID= '"
                + param.get("tenantId") + "'");
        String orderType = param.containsKey("orderType") ? (String) param.get("orderType") : null;
        if (!StringUtil.isBlank(orderType))
            seqBuffer.append(" and oo.order_type = " + orderType);
        if (param.get("orderId") != null)
            seqBuffer.append(" and oo.order_id = " + param.get("orderId"));
        String payStyle = param.containsKey("payStyle") ? (String) param.get("payStyle") : null;
        if (!StringUtil.isBlank(payStyle))
            seqBuffer.append(" and of.pay_style=" + payStyle);
        String states = param.containsKey("states") ? (String) param.get("states") : null;
        if (!StringUtil.isBlank(states))
            seqBuffer.append(" and oo.state in(" + states + ")");
        if (param.get("orderTimeBegin") != null && param.get("orderTimeEnd") != null) {
            seqBuffer.append(" and oo.order_time between '" + param.get("orderTimeBegin")
                    + "' and '" + param.get("orderTimeEnd") + "'");
        }
        seqBuffer.append(" and oo.order_id=of.order_id order by order_time desc limit "
                + param.get("pageCount") + "," + param.get("pageSize"));
        return seqBuffer.toString();
    }

    /**
     * 多表查询订单个数
     */
    public String count(Map<String, Object> param) {
        StringBuffer seqBuffer = new StringBuffer();
        seqBuffer.append("select count(*) from "
                + "ord_order oo,ord_od_fee_total of where oo.sub_flag=" + param.get("subFlag")
                + " and oo.user_id= " + param.get("userId") + " and oo.TENANT_ID= '"
                + param.get("tenantId") + "'");
        String orderType = param.containsKey("orderType") ? (String) param.get("orderType") : null;
        if (!StringUtil.isBlank(orderType))
            seqBuffer.append(" and oo.order_type = " + orderType);
        if (param.get("orderId") != null)
            seqBuffer.append(" and oo.order_id = " + param.get("orderId"));
        String userId = param.containsKey("userId") ? (String) param.get("userId") : null;
        if (param.get("userId") != null)
            seqBuffer.append(" and oo.user_id = " +userId);
        String payStyle = param.containsKey("payStyle") ? (String) param.get("payStyle") : null;
        if (!StringUtil.isBlank(payStyle))
            seqBuffer.append(" and of.pay_style=" + payStyle);
        String states = param.containsKey("states") ? (String) param.get("states") : null;
        if (!StringUtil.isBlank(states))
            seqBuffer.append(" and oo.state in(" + states + ")");
        if (param.get("orderTimeBegin") != null && param.get("orderTimeEnd") != null) {
            seqBuffer.append(" and oo.order_time between '" + param.get("orderTimeBegin")
                    + "' and '" + param.get("orderTimeEnd") + "'");
        }
        seqBuffer.append(" and oo.order_id=of.order_id");
        return seqBuffer.toString();
    }
    
    
    /**
     * 运营后台订单列表查询信息
     * 
     * @param param
     * @return
     * @author caofz
     */
    public String behindQueryOrdOrder(Map<String, Object> param) {
        StringBuffer seqBuffer = new StringBuffer();
        seqBuffer.append("select DISTINCT oo.order_id,oo.state,oo.chl_id,oo.delivery_flag,contact_tel,oo.user_id,discount_fee,adjust_fee "
        		+ "from ord_order oo,ord_order od,ord_od_logistics ol,ord_od_fee_total of where"
                + " oo.tenant_id= '"+ param.get("tenantId") + "'");
        if (param.get("orderId") != null)
        	seqBuffer.append(" and oo.order_id =" + param.get("orderId"));
        String chlId = param.containsKey("chlId") ? (String) param.get("chlId") : null;
        if (!StringUtil.isBlank(chlId))
            seqBuffer.append(" and oo.chl_id = " + chlId);
      
        String userId = param.containsKey("userId") ? (String) param.get("userId") : null;
        if (!StringUtil.isBlank(userId))
            seqBuffer.append(" and oo.user_id = " + userId);
        String contactTel = param.containsKey("contactTel") ? (String) param.get("contactTel") : null;
        if (!StringUtil.isBlank(contactTel))
        	seqBuffer.append(" and ol.contact_tel like '" + "%"+contactTel+"%"+"'");
        String deliveryFlag = param.containsKey("deliveryFlag") ? (String) param.get("deliveryFlag") : null;
        if (!StringUtil.isBlank(deliveryFlag))
            seqBuffer.append(" and oo.delivery_flag=" + deliveryFlag);
        if (param.get("orderTimeBegin") != null && param.get("orderTimeEnd") != null) {
            seqBuffer.append(" and oo.order_time between '" + param.get("orderTimeBegin")
                    + "' and '" + param.get("orderTimeEnd") + "'");
        }
        String states = param.containsKey("states") ? (String) param.get("states") : null;
        String routeId = param.containsKey("routeId") ? (String) param.get("routeId") : null;
        if(StringUtil.isBlank(states)) {  //空
        		if(StringUtil.isBlank(routeId)) {
        			seqBuffer.append(" and oo.order_id=ol.order_id and oo.order_id=of.order_id"
        					+ " order by oo.order_time desc limit "
        					+ param.get("pageCount") + "," + param.get("pageSize"));
        		}else {
        			seqBuffer.append(" and od.route_id = " + routeId);
                	seqBuffer.append(" and oo.order_id=od.PARENT_ORDER_ID and oo.order_id=ol.order_id and oo.order_id=of.order_id"
                			+ " order by oo.order_time desc limit "
                			+ param.get("pageCount") + "," + param.get("pageSize"));
        		}
        }else if (!StringUtil.isBlank(states) && (OrdersConstants.OrdOrder.State.WAIT_PAY.equals(states)||
        		OrdersConstants.OrdOrder.State.COMPLETED.equals(states)||
        		OrdersConstants.OrdOrder.State.CANCEL.equals(states))){   //父状态
        	seqBuffer.append(" and oo.state in(" + states + ")");
        	seqBuffer.append(" and oo.order_id=ol.order_id and oo.order_id=of.order_id"
        			+ " order by oo.order_time desc limit "
        			+ param.get("pageCount") + "," + param.get("pageSize"));
        }else {                                                          
        	if (!StringUtil.isBlank(routeId)) {
        		seqBuffer.append(" and od.route_id = " + routeId);
        	}
        	seqBuffer.append(" and od.state in(" + states + ")");
        	seqBuffer.append(" and oo.order_id=od.PARENT_ORDER_ID and oo.order_id=ol.order_id and oo.order_id=of.order_id"
        			+ " order by oo.order_time desc limit "
        			+ param.get("pageCount") + "," + param.get("pageSize"));
        }
        return seqBuffer.toString();
    }
    
    /**
     * 多表查询订单个数
     */
    public String behindCount(Map<String, Object> param) {
        StringBuffer seqBuffer = new StringBuffer();
        seqBuffer.append("select count(DISTINCT oo.ORDER_ID) from ord_order oo,ord_order od,ord_od_logistics ol,ord_od_fee_total of where"
                + " oo.tenant_id= '"+ param.get("tenantId") + "'");
        if (param.get("orderId") != null)
        	seqBuffer.append(" and oo.order_id =" + param.get("orderId"));
        String chlId = param.containsKey("chlId") ? (String) param.get("chlId") : null;
        if (!StringUtil.isBlank(chlId))
            seqBuffer.append(" and oo.chl_id = " + chlId);
        String userId = param.containsKey("userId") ? (String) param.get("userId") : null;
        if (!StringUtil.isBlank(userId))
            seqBuffer.append(" and oo.user_id = " + userId);
        String contactTel = param.containsKey("contactTel") ? (String) param.get("contactTel") : null;
        if (!StringUtil.isBlank(contactTel))
        	seqBuffer.append(" and ol.contact_tel like '" + "%"+contactTel+"%"+"'");
        String deliveryFlag = param.containsKey("deliveryFlag") ? (String) param.get("deliveryFlag") : null;
        if (!StringUtil.isBlank(deliveryFlag))
            seqBuffer.append(" and oo.delivery_flag=" + deliveryFlag);
        if (param.get("orderTimeBegin") != null && param.get("orderTimeEnd") != null) {
            seqBuffer.append(" and oo.order_time between '" + param.get("orderTimeBegin")
                    + "' and '" + param.get("orderTimeEnd") + "'");
        }
        String states = param.containsKey("states") ? (String) param.get("states") : null;
        String routeId = param.containsKey("routeId") ? (String) param.get("routeId") : null;
        if(StringUtil.isBlank(states)) {  //空
        		if(StringUtil.isBlank(routeId)) {
        			seqBuffer.append(" and oo.order_id=ol.order_id and oo.order_id=of.order_id");
        		}else {
        			seqBuffer.append(" and od.route_id = " + routeId);
                	seqBuffer.append(" and oo.order_id=od.PARENT_ORDER_ID and oo.order_id=ol.order_id and oo.order_id=of.order_id");
        		}
        }else if (!StringUtil.isBlank(states) && (OrdersConstants.OrdOrder.State.WAIT_PAY.equals(states)||
        		OrdersConstants.OrdOrder.State.COMPLETED.equals(states)||
        		OrdersConstants.OrdOrder.State.CANCEL.equals(states))){   //父状态
        	seqBuffer.append(" and oo.state in(" + states + ")");
        	seqBuffer.append(" and oo.order_id=ol.order_id and oo.order_id=of.order_id");
        }else {                                                          
        	if (!StringUtil.isBlank(routeId)) {
        		seqBuffer.append(" and od.route_id = " + routeId);
        	}
        	seqBuffer.append(" and od.state in(" + states + ")");
        	seqBuffer.append(" and oo.order_id=od.PARENT_ORDER_ID and oo.order_id=ol.order_id and oo.order_id=of.order_id");
        }
        return seqBuffer.toString();
    }
}
