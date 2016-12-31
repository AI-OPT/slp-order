package com.ai.slp.order.api.synchronize.params;

import java.io.Serializable;
import java.sql.Timestamp;

public class OrdOdFeeTotalVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private long orderId;

	private String tenantId;

	private String payFlag;

	/**
	 * 总费用
	 */
	private long totalFee;

	/**
	 * 总优惠金额
	 */
	private long discountFee;

	/**
	 * 总减免费用(营业员)
	 */
	private long operDiscountFee;

	/**
	 * 减免原因
	 */
	private String operDiscountDesc;

	/**
	 * 总应收费用
	 */
	private long adjustFee;

	/**
	 * 实收费用
	 */
	private long paidFee;

	/**
	 * 总待收费用
	 */
	private long payFee;

	/**
	 * 支付方式
	 */
	private String payStyle;

	private Timestamp updateTime;

	private String updateChlId;

	private String updateOperId;

	private long totalJf;

	/**
	 * 运费	
	 */
	private long freight;
}
