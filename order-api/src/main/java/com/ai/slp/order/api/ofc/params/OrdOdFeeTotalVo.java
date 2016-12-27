package com.ai.slp.order.api.ofc.params;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 订单费用表
 * Date: 2016年11月12日 <br>
 * Copyright (c) 2016 asiainfo.com <br>
 * 
 * @author zhangqiang7
 */
public class OrdOdFeeTotalVo implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 订单id
	 */
	private long orderId;

	/**
	 * 租户id
	 */
	private String tenantId;

	/**
	 * 收退费标识
	 */
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
	 * 减免费用(营业员)
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
	 * 总实收费用
	 */
	private long paidFee;

	/**
	 * 总待收费用
	 */
	private long payFee;

	/**
	 * 默认支付方式
	 */
	private String payStyle;

	/**
	 * 变更时间
	 */
	private Timestamp updateTime;

	/**
	 * 变更渠道
	 */
	private String updateChlId;

	/**
	 * 变更工号
	 */
	private String updateOperId;

	/**
	 * 赠送积分
	 */
	private long totalJf;

	/**
	 * 运费
	 */
	private long freight;

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getPayFlag() {
		return payFlag;
	}

	public void setPayFlag(String payFlag) {
		this.payFlag = payFlag;
	}

	public long getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(long totalFee) {
		this.totalFee = totalFee;
	}

	public long getDiscountFee() {
		return discountFee;
	}

	public void setDiscountFee(long discountFee) {
		this.discountFee = discountFee;
	}

	public long getOperDiscountFee() {
		return operDiscountFee;
	}

	public void setOperDiscountFee(long operDiscountFee) {
		this.operDiscountFee = operDiscountFee;
	}

	public String getOperDiscountDesc() {
		return operDiscountDesc;
	}

	public void setOperDiscountDesc(String operDiscountDesc) {
		this.operDiscountDesc = operDiscountDesc;
	}

	public long getAdjustFee() {
		return adjustFee;
	}

	public void setAdjustFee(long adjustFee) {
		this.adjustFee = adjustFee;
	}

	public long getPaidFee() {
		return paidFee;
	}

	public void setPaidFee(long paidFee) {
		this.paidFee = paidFee;
	}

	public long getPayFee() {
		return payFee;
	}

	public void setPayFee(long payFee) {
		this.payFee = payFee;
	}

	public String getPayStyle() {
		return payStyle;
	}

	public void setPayStyle(String payStyle) {
		this.payStyle = payStyle;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateChlId() {
		return updateChlId;
	}

	public void setUpdateChlId(String updateChlId) {
		this.updateChlId = updateChlId;
	}

	public String getUpdateOperId() {
		return updateOperId;
	}

	public void setUpdateOperId(String updateOperId) {
		this.updateOperId = updateOperId;
	}

	public long getTotalJf() {
		return totalJf;
	}

	public void setTotalJf(long totalJf) {
		this.totalJf = totalJf;
	}

	public long getFreight() {
		return freight;
	}

	public void setFreight(long freight) {
		this.freight = freight;
	}

}
