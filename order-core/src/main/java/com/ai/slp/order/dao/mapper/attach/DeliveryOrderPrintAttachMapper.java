package com.ai.slp.order.dao.mapper.attach;


import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;

/**
 * 多表多条件查询订单信息
 * @date 2016年8月10日 
 * @author caofz
 */
public interface DeliveryOrderPrintAttachMapper {
	
    @SelectProvider(type = DeliveryOrderPrintSqlProvider.class, method = "queryOrderProd")
    @Results({ @Result(id = true, property = "orderId", column = "order_id"),
            @Result(property = "tenantId", column = "tenant_id"),
            @Result(property = "prodName", column = "prod_name"),
            @Result(property = "buySum", column = "buy_sum"),
            @Result(property = "skuId", column = "sku_id"),
            @Result(property = "extendInfo", column = "extend_info"),
            })
    public List<OrdOrderProdAttach> query(@Param("userId") String userId, @Param("tenantId") String tenantId,@Param("skuId") String skuId, 
    		@Param("routeId") String routeId,@Param("orderId") long orderId, @Param("state") String state,
    		@Param("timeBefore") Timestamp timeBefore, @Param("timeAfter") Timestamp timeAfter,@Param("cusServiceFlag") String cusServiceFlag);
}
