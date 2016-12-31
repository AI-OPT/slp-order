package com.ai.slp.order.api.synchronize.params;

import java.io.Serializable;
import java.sql.Timestamp;

public class OrdOdProdVo implements Serializable{

	 private long prodDetalId;

	    private String tenantId;

	    private long orderId;

	    /**
	     * 销售品类型
	     */
	    private String prodType;

	    private String supplierId;

	    private String sellerId;

	    /**
	     * 销售品ID
	     */
	    private String prodId;

	    /**
	     * 销售品名称
	     */
	    private String prodName;

	    private String prodSn;

	    /**
	     * 单品ID
	     */
	    private String skuId;

	    private String standardProdId;

	    private String supplyId;

	    /**
	     * 库存ID
	     */
	    private String storageId;

	    private String routeId;

	    private Timestamp validTime;

	    private Timestamp invalidTime;

	    private String state;

	    /**
	     * 购买数量
	     */
	    private long buySum;

	    
	    private long salePrice;

	    private long costPrice;

	    /**
	     * 总费用
	     */
	    private long totalFee;

	    /**
	     * 优惠费用
	     */
	    private long discountFee;

	    /**
	     * 减免金额
	     */
	    private long operDiscountFee;

	    /**
	     * 减免原因
	     */
	    private String operDiscountDesc;

	    /**
	     * 应收费用
	     */
	    private long adjustFee;

	    /**
	     * 赠送积分
	     */
	    private long jf;

	    private String prodDesc;

	    private String extendInfo;

	    private Timestamp updateTime;

	    private String updateChlId;

	    private String updateOperId;

	    /**
	     * SKU库存ID
	     */
	    private String skuStorageId;

	    private String isInvoice;

	    private long couponFee;

	    private long jfFee;

	    /**
	     * 售后标识
	     */
	    private String cusServiceFlag;

	    /**
	     * 商品编码
	     */
	    private String prodCode;
}
