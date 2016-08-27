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

    /**
     * 结算管理-商城商品列表分页查询
     */
    @SelectProvider(type = OrdOrderSqlProvider.class, method = "queryOrdOrder")
    @Results({ @Result(id = true, property = "orderId", column = "order_id"),
            @Result(property = "tenantId", column = "tenant_id"),
            @Result(property = "busiCode", column = "busi_code"),
            @Result(property = "orderType", column = "order_type"),
            @Result(property = "state", column = "state"),
            @Result(property = "payStyle", column = "pay_style"),
            @Result(property = "payTime", column = "update_time"),
            @Result(property = "orderTime", column = "order_time"),
            @Result(property = "totalFee", column = "total_fee"),
            @Result(property = "discountFee", column = "discount_fee"),
            @Result(property = "adjustFee", column = "adjust_fee"),
            @Result(property = "paidFee", column = "paid_fee"),
            @Result(property = "payFee", column = "pay_fee") })
    public List<OrdOrderAttach> getOrdOrder(@Param("subFlag") String subFlag, 
    		@Param("pageCount") Integer pageCount, @Param("pageSize") Integer pageSize,
    		@Param("userId") String userId, @Param("orderType") String orderType,
    		@Param("orderId") Long orderId, @Param("payStyle") String payStyle, 
    		@Param("tenantId") String tenantId, @Param("states") String states, 
    		@Param("orderTimeBegin") String orderTimeBegin, 
    		@Param("orderTimeEnd") String orderTimeEnd);

    @SelectProvider(type = OrdOrderSqlProvider.class, method = "count")
    public int getCount(@Param("subFlag") String subFlag, @Param("userId") String userId,
    		@Param("orderType") String orderType, @Param("orderId") Long orderId, 
    		@Param("payStyle") String payStyle, @Param("tenantId") String tenantId, 
    		@Param("states") String states, @Param("orderTimeBegin") String orderTimeBegin, 
    		@Param("orderTimeEnd") String orderTimeEnd);
    
    
    @SelectProvider(type = OrdOrderSqlProvider.class, method = "behindCount")
	public int getBehindCount(@Param("chlId") String chlId, @Param("deliveryFlag") String deliveryFlag,
			@Param("orderId") Long orderId,@Param("tenantId") String tenantId,
			@Param("contactTel") String contactTel,@Param("states") String states,@Param("userId") String userId,
			@Param("orderTimeBegin") String orderTimeBegin,@Param("orderTimeEnd") String orderTimeEnd);
    
    
    @Results({ @Result(id = true, property = "orderId", column = "order_id"),
        @Result(property = "chlId", column = "chl_id"),
        @Result(property = "deliveryFlag", column = "delivery_flag"),
        @Result(property = "contactTel", column = "contact_tel"),
        @Result(property = "userId", column = "user_id"),
        @Result(property = "discountFee", column = "discount_fee"),
        @Result(property = "adjustFee", column = "adjust_fee") })
    @SelectProvider(type = OrdOrderSqlProvider.class, method = "behindQueryOrdOrder")
	public List<BehindOrdOrderAttach> getBehindOrdOrder(@Param("pageCount") Integer pageCount, 
			@Param("pageSize") Integer pageSize, @Param("chlId") String chlId, 
			@Param("deliveryFlag") String deliveryFlag,@Param("orderId") Long orderId, 
			@Param("tenantId") String tenantId,@Param("contactTel") String contactTel, 
			@Param("states") String states,@Param("userId") String userId,
			@Param("orderTimeBegin") String orderTimeBegin, 
			@Param("orderTimeEnd") String orderTimeEnd);

	
}
