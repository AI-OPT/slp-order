package com.ai.slp.order.dao.mapper.attach;

import java.util.Map;

import com.ai.paas.ipaas.util.StringUtil;

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
}
