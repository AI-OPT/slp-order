package com.ai.slp.order.dao.mapper.attach;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;

public interface StasticOrdOrderAttachMapper {
	@Results({ @Result(id = true, property = "orderId", column = "order_id"),
        @Result(property = "chlId", column = "chl_id"),
        @Result(property = "deliveryFlag", column = "delivery_flag"),
        @Result(property = "userId", column = "user_id"),
        @Result(property = "supplierId", column = "supplier_id"),
        @Result(property = "tenantId", column = "tenant_id")})
    @SelectProvider(type = StasticOrdOrderSqlProvider.class, method = "staticQueryOrdOrder")
	public List<StasticOrdOrderAttach> getStaticOrdOrder(@Param("pageCount") Integer pageCount, 
			@Param("pageSize") Integer pageSize,@Param("states") String states,@Param("orderId") Long orderId,
			@Param("prodName") String prodName,@Param("userId") String userId, 
			@Param("supplierId") String supplierId,@Param("tenantId") String tenantId,
			@Param("orderTimeBegin") String orderTimeBegin,@Param("orderTimeEnd") String orderTimeEnd);
	
	
	@SelectProvider(type = StasticOrdOrderSqlProvider.class, method = "stasticCount")
	    public int getCount( @Param("orderId") Long orderId, @Param("userId") String userId,
	    		@Param("supplierId") String supplierId, 
	    		@Param("prodName") String prodName, @Param("tenantId") String tenantId, 
	    		@Param("states") String states, @Param("orderTimeBegin") String orderTimeBegin, 
	    		@Param("orderTimeEnd") String orderTimeEnd);
}
