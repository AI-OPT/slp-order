package com.ai.slp.order.api.stasticsorder.param;

import java.io.Serializable;
import java.util.List;

public class StasticOrderVo implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 父订单id
	 */
	private Long parentOrderId;
	/**
	 * 订单id
	 */
	private Long orderId;
	/**
	 * 状态
	 */
	private String state;
	/**
	 * 页面展示状态
	 */
	private String stateName;

	/**
	 * 业务类型
	 */
	private String busiCode;
	/**
	 * 商品数量
	 */
	private int prodSize;
	/**
	 * 商品信息
	 */
	private List<StasticsProdVo> proList;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getBusiCode() {
		return busiCode;
	}

	public void setBusiCode(String busiCode) {
		this.busiCode = busiCode;
	}

	public List<StasticsProdVo> getProList() {
		return proList;
	}

	public void setProList(List<StasticsProdVo> proList) {
		this.proList = proList;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public Long getParentOrderId() {
		return parentOrderId;
	}

	public void setParentOrderId(Long parentOrderId) {
		this.parentOrderId = parentOrderId;
	}

	public int getProdSize() {
		return prodSize;
	}

	public void setProdSize(int prodSize) {
		this.prodSize = prodSize;
	}
}
