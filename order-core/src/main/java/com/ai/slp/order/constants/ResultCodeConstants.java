package com.ai.slp.order.constants;

public class ResultCodeConstants {
	
	public static final String SUCCESS_CODE = "000000";
	
	public static final String FAIL_CODE = "000001";
	
	public final class ApiOrder {
		
		 /**
	     * 传入的参数为空
	     */
	    public static final String REQUIRED_IS_EMPTY="110000";
	    
	    /**
	     * 系统异常
	     */
	    public static final String SYSTEM_ERROR="110011";
	    
	    /**
	     * 订单重复
	     */
	    public static final String ORDER_REPEAT="110012";
	    
	    /**
	     * sku不存在
	     */
	    public static final String SKU_NO_EXIST="211001";
	    
	    /**
	     * 商品不存在或已下架
	     */
	    public static final String PROD_NO_EXIST="211002";
	    
	    /**
	     * 库存不足
	     */
	    public static final String SKU_NOT_ENOUGH="211003";
	    
	    /**
	     * 商品价格不符
	     */
	    public static final String PROD_PRICE_WRONG="211004";
	    
	    /**
	     * 用户与商品不符
	     */
	    public static final String USERS_GOODS_NOT_MATCH="211005";
	
	    /**
	     * 余额不足
	     */
	    public static final String MONEY_NOT_ENOUGH="110099";
	}

}
