package com.ai.slp.order.api.stasticsorder.param;

import java.util.List;

import com.ai.opt.base.vo.BaseInfo;

public class StasticParentOrderVo extends BaseInfo {
	private static final long serialVersionUID = 1L;
	/**
	 * 订单id
	 */
	private Long orderId;
	/**
	 * 订单来源
	 */
	private String chlId;
	/**
	 * 商户ID
	 */
	private Long supplierId;
	/**
	 * 商户名称
	 */
	private String supplierName;
	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 收货人手机号
	 */
	private String contactTel;
	/**
	 * 是否需要物流
	 */
	private String deliveryFlag;
	/**
	 * 状态
	 */
	private String state;
	/**
	 * 页面展示状态
	 */
	private String stateName;
	/**
	 * 子订单信息
	 */
	private List<StasticOrderVo> childOrderList;

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

	public String getContactTel() {
		return contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
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

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public List<StasticOrderVo> getChildOrderList() {
		return childOrderList;
	}

	public void setChildOrderList(List<StasticOrderVo> childOrderList) {
		this.childOrderList = childOrderList;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

}
