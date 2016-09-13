package com.ai.slp.order.dao.mapper.attach;

import java.util.Map;

public class DeliveryOrderPrintSqlProvider {
	
	public String queryOrderProd(Map<String, Object> param) {
		StringBuffer seqBuffer = new StringBuffer();
        seqBuffer.append("select od.order_id,od.tenant_id,op.prod_name,op.buy_sum,op.sku_id,op.extend_info from ord_order od,ord_od_prod op "
        		+ "where od.order_id=op.order_id and od.user_id="+param.get("userId")
        		+ " and op.sku_id="+param.get("skuId") +" and op.route_id="+param.get("routeId")
        		+" and op.order_id!="+param.get("orderId")
        		+" and op.cus_service_flag='"+param.get("cusServiceFlag")+"'"
        		+ " and od.state="+param.get("state") + " and od.tenant_id= '"
                + param.get("tenantId") + "'"+" and od.order_time between '"+param.get("timeBefore")+"' and '"+param.get("timeAfter")+"'");
        return seqBuffer.toString();
	}
}
