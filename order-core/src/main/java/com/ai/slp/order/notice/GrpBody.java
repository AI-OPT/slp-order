package com.ai.slp.order.notice;

public class GrpBody {
	/**
	 * 商户主订单ID号
	 */
	private String MerOrderId;
	/**
	 * 支付流水号
	 */
	private String PayTranSn;
	/**
	 * 订单金额
	 */
	private Long OrderAmt;
	/**
	 * 日期
	 */
	private String OrderDate;
	/**
	 * 支付状态
	 */
	private String PayStatus;
	/**
	 * 备注
	 */
	private String Remark;
	/**
	 * 保留域
	 */
	private String Resv;
	/**
	 * 支付渠道
	 */
	private String PaymentChannel;
	/**
	 * 支付方法
	 */
	private String PaymentMethod;
	
	public String getMerOrderId() {
		return MerOrderId;
	}
	public void setMerOrderId(String merOrderId) {
		MerOrderId = merOrderId;
	}
	public String getPayTranSn() {
		return PayTranSn;
	}
	public void setPayTranSn(String payTranSn) {
		PayTranSn = payTranSn;
	}
	public Long getOrderAmt() {
		return OrderAmt;
	}
	public void setOrderAmt(Long orderAmt) {
		OrderAmt = orderAmt;
	}
	public String getOrderDate() {
		return OrderDate;
	}
	public void setOrderDate(String orderDate) {
		OrderDate = orderDate;
	}
	public String getPayStatus() {
		return PayStatus;
	}
	public void setPayStatus(String payStatus) {
		PayStatus = payStatus;
	}
	public String getRemark() {
		return Remark;
	}
	public void setRemark(String remark) {
		Remark = remark;
	}
	public String getResv() {
		return Resv;
	}
	public void setResv(String resv) {
		Resv = resv;
	}
	public String getPaymentChannel() {
		return PaymentChannel;
	}
	public void setPaymentChannel(String paymentChannel) {
		PaymentChannel = paymentChannel;
	}
	public String getPaymentMethod() {
		return PaymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		PaymentMethod = paymentMethod;
	}

}
