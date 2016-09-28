package com.ai.slp.order.dao.mapper.attach;

import com.ai.opt.base.vo.BaseInfo;

public class StasticOrdOrderAttach extends BaseInfo{
	private static final long serialVersionUID = 1L;

	/**
	 * 业务订单ID
	 */
	private Long orderId;

	/**
	 * 订单来源
	 */
	private String chlId;

	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * 是否需要物流
	 */
	private String deliveryFlag;
	/**
	 * 商户ID
	 */
	private String supplierId;

	/**
	 * 订单状态
	 */
	private String state;

	/**
	 * 收货人手机号
	 */
	private String contactTel;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getChlId() {
		return chlId;
	}

	public void setChlId(String chlId) {
		this.chlId = chlId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDeliveryFlag() {
		return deliveryFlag;
	}

	public void setDeliveryFlag(String deliveryFlag) {
		this.deliveryFlag = deliveryFlag;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getContactTel() {
		return contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

}
