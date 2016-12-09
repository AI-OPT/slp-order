package com.ai.slp.order.dao.mapper.attach;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;

/**
 * 多表查询订单信息 Date: 2016年6月29日
 * 
 * @author caofz
 * 
 */
public interface OrdOrderAttachMapper {

    @SelectProvider(type = OrdOrderSqlProvider.class, method = "behindCount")
	public int getBehindCount(@Param("states") String states,@Param("orderId") Long orderId,
			@Param("chlId") String chlId,@Param("routeId") String routeId,
			@Param("userId") String userId, @Param("contactTel") String contactTel,
			@Param("tenantId") String tenantId,@Param("deliveryFlag") String deliveryFlag,
			@Param("orderTimeBegin") String orderTimeBegin,@Param("orderTimeEnd") String orderTimeEnd);
    
    
    @Results({ @Result(id = true, property = "orderId", column = "order_id"),
        @Result(property = "chlId", column = "chl_id"),
        @Result(property = "deliveryFlag", column = "delivery_flag"),
        @Result(property = "contactTel", column = "contact_tel"),
        @Result(property = "userId", column = "user_id"),
        @Result(property = "userName", column = "user_name"),
        @Result(property = "userTel", column = "user_tel"),
        @Result(property = "discountFee", column = "discount_fee"),
        @Result(property = "adjustFee", column = "adjust_fee"),
        @Result(property = "tenantId", column = "tenant_id")})
    @SelectProvider(type = OrdOrderSqlProvider.class, method = "behindQueryOrdOrder")
	public List<BehindOrdOrderAttach> getBehindOrdOrder(@Param("pageCount") Integer pageCount, 
			@Param("pageSize") Integer pageSize,@Param("states") String states,@Param("orderId") Long orderId,
			@Param("chlId") String chlId,@Param("routeId") String routeId,
			@Param("userId") String userId, @Param("contactTel") String contactTel,
			@Param("tenantId") String tenantId,@Param("deliveryFlag") String deliveryFlag,
			@Param("orderTimeBegin") String orderTimeBegin,@Param("orderTimeEnd") String orderTimeEnd);
    
	
}
