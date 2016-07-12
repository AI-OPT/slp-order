package com.ai.slp.order.constants;

/**
 * Created by jackieliu on 16/6/14.
 */
public final class ErrorCodeConstants {
    public final class Product{
        /**
         * sku不存在,或无效
         */
        public static final String SKU_NO_EXIST = "1001";
        /**
         * 销售商品不存在
         */
        public static final String PRODUCT_NO_EXIST = "1002";
    }
    
    public final class Order{
    	/**
    	 * 订单不存在
    	 */
    	public static final String ORDER_NO_EXIST="211002";
    }
    
    /**
     * 传入的参数为空
     */
    public static final String REQUIRED_IS_EMPTY="211000";
    
    /**
     * 系统异常
     */
    public static final String SYSTEM_ERROR="211001";
    
}
