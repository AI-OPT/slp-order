package com.ai.slp.order.api.stasticsorder.param;

import com.ai.opt.base.vo.BaseInfo;

public class StasticsOrderRequest extends BaseInfo {
	private static final long serialVersionUID = 1L;
	/**
	 * 订单号
	 */
	private Long orderId;
	/**
	 * 商品名称
	 */
	private String prodName;
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 销售商ID
	 */
	private String supplierId;
	/**
	 * 状态
	 */
	private String state;
	/**
	 * 开始时间
	 */
	private String orderTimeStart;
	/**
	 * 结束时间
	 */
	private String orderTimeEnd;
	/**
	 * 页码
	 */
	private Integer pageNo;
	/**
	 * 页数大小
	 */
	private Integer pageSize;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	public String getOrderTimeStart() {
		return orderTimeStart;
	}

	public void setOrderTimeStart(String orderTimeStart) {
		this.orderTimeStart = orderTimeStart;
	}

	public String getOrderTimeEnd() {
		return orderTimeEnd;
	}

	public void setOrderTimeEnd(String orderTimeEnd) {
		this.orderTimeEnd = orderTimeEnd;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

}
