package com.ai.slp.order.dao.mapper.attach;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;

public interface StasticOrdOrderAttachMapper {
	
	//查询订单统计信息
	@Results({ @Result(id = true, property = "orderId", column = "order_id"),
        @Result(property = "chlId", column = "chl_id"),
        @Result(property = "deliveryFlag", column = "delivery_flag"),
        @Result(property = "userName", column = "user_name"),
        @Result(property = "userTel", column = "user_tel"),
        @Result(property = "supplierId", column = "supplier_id"),
        @Result(property = "tenantId", column = "tenant_id")})
    @SelectProvider(type = StasticOrdOrderSqlProvider.class, method = "staticQueryOrdOrder")
	public List<StasticOrdOrderAttach> getStaticOrdOrder(@Param("pageCount") Integer pageCount, 
			@Param("pageSize") Integer pageSize,@Param("states") String states,@Param("orderId") Long orderId,
			@Param("prodName") String prodName,@Param("userName") String userName, 
			@Param("supplierId") String supplierId,@Param("tenantId") String tenantId,
			@Param("orderTimeBegin") String orderTimeBegin,@Param("orderTimeEnd") String orderTimeEnd);
	
	//订单统计个数
	@SelectProvider(type = StasticOrdOrderSqlProvider.class, method = "stasticCount")
	    public int getCount( @Param("orderId") Long orderId, @Param("userName") String userName,
	    		@Param("supplierId") String supplierId, 
	    		@Param("prodName") String prodName, @Param("tenantId") String tenantId, 
	    		@Param("states") String states, @Param("orderTimeBegin") String orderTimeBegin, 
	    		@Param("orderTimeEnd") String orderTimeEnd);
}
