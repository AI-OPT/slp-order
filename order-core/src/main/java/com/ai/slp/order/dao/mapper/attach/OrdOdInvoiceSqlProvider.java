package com.ai.slp.order.dao.mapper.attach;

import java.util.Map;

import com.ai.paas.ipaas.util.StringUtil;

public class OrdOdInvoiceSqlProvider {
	
	  /**
     * 查询配送信息
     * 
     * @param param
     * @return
     * @author caofz
     */
    public String queryInvoice(Map<String, Object> param) {
        StringBuffer seqBuffer = new StringBuffer();
        seqBuffer.append("select oi.* from ord_order oo,ord_od_invoice oi where oo.SUB_FLAG= '" + param.get("subFlag")
        + "' and oo.tenant_id= '"+ param.get("tenantId") + "'");
        Long orderId = (Long) param.get("orderId");
        if (orderId!= null && orderId !=0)
            seqBuffer.append(" and oo.order_id = " + param.get("orderId"));
        String invoiceTitle = param.containsKey("invoiceTitle") ? (String) param.get("invoiceTitle") : null;
        if (!StringUtil.isBlank(invoiceTitle))
            seqBuffer.append(" and oi.invoice_title like '" + "%"+invoiceTitle+"%"+"'");
        String invoiceStatus = param.containsKey("invoiceStatus") ? (String) param.get("invoiceStatus") : null;
        if (!StringUtil.isBlank(invoiceStatus))
            seqBuffer.append(" and oi.invoice_status = '" + invoiceStatus + "'");
        seqBuffer.append(" and oo.ORDER_ID=oi.ORDER_ID order by oo.order_time desc limit "
                + param.get("pageCount") + "," + param.get("pageSize"));
        return seqBuffer.toString();
    }

    /**
     * 多表查询订单个数
     */
    public String count(Map<String, Object> param) {
    	 StringBuffer seqBuffer = new StringBuffer();
         seqBuffer.append("select count(*) from ord_order oo,ord_od_invoice oi where oo.SUB_FLAG='" + param.get("subFlag")
         + "' and oo.TENANT_ID= '"+ param.get("tenantId") + "'");
         Long orderId = (Long) param.get("orderId");
         if (orderId!= null && orderId !=0)
             seqBuffer.append(" and oo.order_id = " + param.get("orderId"));
         String invoiceTitle = param.containsKey("invoiceTitle") ? (String) param.get("invoiceTitle") : null;
         if (!StringUtil.isBlank(invoiceTitle))
             seqBuffer.append(" and oi.invoice_title like '" + "%"+invoiceTitle+"%"+"'");
         String invoiceStatus = param.containsKey("invoiceStatus") ? (String) param.get("invoiceStatus") : null;
         if (!StringUtil.isBlank(invoiceStatus))
             seqBuffer.append(" and oi.invoice_status ='" + invoiceStatus + "'");
         seqBuffer.append(" and  oo.ORDER_ID=oi.ORDER_ID order by oo.order_time desc");
         return seqBuffer.toString();
    }

}
