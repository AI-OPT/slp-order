package com.ai.slp.order.dao.mapper.attach;

import java.util.Map;

import com.ai.paas.ipaas.util.StringUtil;

/**
 * 该功能现查询elasticSearch,现方法已废弃
 * @date 2017年5月3日 
 * @author caofz
 */
public class StasticOrdOrderSqlProvider {
	/**
	 * 订单查询
	 * @param param
	 * @return
	 * @author zhanglh
	 * @ApiCode
	 */
    public String staticQueryOrdOrder(Map<String, Object> param) {
        StringBuffer seqBuffer = new StringBuffer();
        seqBuffer.append("select DISTINCT oo.*"
        		+ " from ord_order oo, ord_od_prod prod where"
                + " oo.tenant_id= '"+ param.get("tenantId") + "'");
        if (param.get("orderId") != null){
        	seqBuffer.append(" and oo.order_id =" + param.get("orderId"));
        }
        	
        String userName = param.containsKey("userName") ? (String) param.get("userName") : null;
        if (!StringUtil.isBlank(userName)){
        	 seqBuffer.append(" and oo.user_name like'" + "%"+userName+"%"+"'"); 
        }
        seqBuffer.append(" and oo.sub_flag = '" + "N"+"'");
        String supplierId = param.containsKey("supplierId") ? (String) param.get("supplierId") : null;
        if (!StringUtil.isBlank(supplierId)){
        	seqBuffer.append(" and oo.supplier_id = '" + supplierId+"'");
        }
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
        String states = param.containsKey("states") ? (String) param.get("states") : null;
        String prodName = param.containsKey("prodName") ? (String) param.get("prodName") : null;
        if(!StringUtil.isBlank(prodName)) {
			seqBuffer.append(" and prod.prod_name like'" + "%"+prodName+"%"+"'");
		}
        if(StringUtil.isBlank(states)) {  //空
        	seqBuffer.append(" and oo.state in(" + "90,"+"91,"+"11,"+"111" + ")");
        	seqBuffer.append(" and  oo.order_id=prod.order_id"
        			+ " order by oo.order_time desc limit "
        			+ param.get("pageCount") + "," + param.get("pageSize"));
        }else {
        	//父状态
        	seqBuffer.append(" and oo.state in(" + states + ")");
        	seqBuffer.append(" and  oo.order_id=prod.order_id"
        			+ " order by oo.order_time desc limit "
        			+ param.get("pageCount") + "," + param.get("pageSize"));
        }
        return seqBuffer.toString();
    }
    
    /**
     * 多表查询订单个数
     */
    public String stasticCount(Map<String, Object> param) {
    	 StringBuffer seqBuffer = new StringBuffer();
         seqBuffer.append("select count(DISTINCT oo.ORDER_ID)"
         		+ "from ord_order oo, ord_od_prod prod where"
                 + " oo.tenant_id= '"+ param.get("tenantId") + "'");
         if (param.get("orderId") != null){
        	 seqBuffer.append(" and oo.order_id =" + param.get("orderId")); 
         }
         String userName = param.containsKey("userName") ? (String) param.get("userName") : null;
         if (!StringUtil.isBlank(userName)){
        	 seqBuffer.append(" and oo.user_name like'" + "%"+userName+"%"+"'"); 
         }
         String supplierId = param.containsKey("supplierId") ? (String) param.get("supplierId") : null;
         if (!StringUtil.isBlank(supplierId)){
        	 seqBuffer.append(" and oo.supplier_id = '" + supplierId+"'");
         }
         seqBuffer.append(" and oo.sub_flag = '" + "N"+"'");
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
         String states = param.containsKey("states") ? (String) param.get("states") : null;
         String prodName = param.containsKey("prodName") ? (String) param.get("prodName") : null;
         if(StringUtil.isBlank(prodName)) {
 			seqBuffer.append(" and oo.order_id=prod.order_id");
 		}else {
 			seqBuffer.append(" and prod.prod_name like'" + "%"+prodName+"%"+"'");
         	seqBuffer.append(" and  oo.order_id=prod.order_id");
 		}
         if(StringUtil.isBlank(states)) {  //空
         	seqBuffer.append(" and oo.state in(" + "90,"+"91,"+"11,"+"111" + ")");
         }else {
         	//父状态
         	seqBuffer.append(" and oo.state in(" + states + ")");
         	seqBuffer.append(" and  oo.order_id=prod.order_id");
         }
         return seqBuffer.toString();
    }
}
