package com.ai.slp.order.vo;

import java.io.Serializable;

public class OrderAfterSaleApplyVo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 订单号
	 */
	private String orderNo;
	
	/**
	 * 外部申请单号
	 */
	private String externalApplyNo;
	
	/**
	 * 申请类型
	 */
	private int applyType;
	
	/**
	 * 申请原因
	 */
	private int reasonType;
	
	/**
	 * 原因描述
	 */
	private String description;
	
	/**
	 * 退款金额
	 */
	private long refundAmount;
	
	/**
	 * 申请时间
	 */
	private String applyTime;
	
	/**
	 * 备注
	 */
	private String remark;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getExternalApplyNo() {
		return externalApplyNo;
	}

	public void setExternalApplyNo(String externalApplyNo) {
		this.externalApplyNo = externalApplyNo;
	}

	public int getApplyType() {
		return applyType;
	}

	public void setApplyType(int applyType) {
		this.applyType = applyType;
	}

	public int getReasonType() {
		return reasonType;
	}

	public void setReasonType(int reasonType) {
		this.reasonType = reasonType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(long refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
