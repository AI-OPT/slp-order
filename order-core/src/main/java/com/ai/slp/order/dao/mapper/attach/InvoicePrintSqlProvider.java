package com.ai.slp.order.dao.mapper.attach;

import java.util.Map;

public class InvoicePrintSqlProvider {
	
	public String queryOrderProd(Map<String, Object> param) {
		StringBuffer seqBuffer = new StringBuffer();
        seqBuffer.append("select od.order_id,od.tenant_id,op.prod_name,op.buy_sum from ord_order od,ord_od_prod op "
        		+ "where od.order_id=op.order_id and od.user_id="+param.get("userId")
        		+ " and op.sku_id="+param.get("skuId") +" and op.route_id="+param.get("routeId")
        		+ " and od.state="+param.get("state") + " and od.tenant_id= '"
                + param.get("tenantId") + "'"+" and od.order_time between '"+param.get("timeBefore")+"' and '"+param.get("timeAfter")+"'");
        return seqBuffer.toString();
	}
}
