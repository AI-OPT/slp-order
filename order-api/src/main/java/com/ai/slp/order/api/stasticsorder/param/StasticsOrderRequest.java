package com.ai.slp.order.api.stasticsorder.param;

import java.sql.Timestamp;

import com.ai.opt.base.vo.BaseInfo;

public class StasticsOrderRequest extends BaseInfo {
	private static final long serialVersionUID = 1L;
	/**
	 * 父订单号
	 */
	private Long parentOrderId;
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
	private Long supplierId;
	/**
	 * 状态
	 */
	private String state;
	/**
	 * 开始时间
	 */
	private Timestamp orderTimeStart;
	/**
	 * 结束时间
	 */
	private Timestamp orderTimeEnd;
	/**
	 * 页码
	 */
	private Integer pageNo;
	/**
	 * 页数大小
	 */
	private Integer pageSize;

	public Long getParentOrderId() {
		return parentOrderId;
	}

	public void setParentOrderId(Long parentOrderId) {
		this.parentOrderId = parentOrderId;
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

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Timestamp getOrderTimeStart() {
		return orderTimeStart;
	}

	public void setOrderTimeStart(Timestamp orderTimeStart) {
		this.orderTimeStart = orderTimeStart;
	}

	public Timestamp getOrderTimeEnd() {
		return orderTimeEnd;
	}

	public void setOrderTimeEnd(Timestamp orderTimeEnd) {
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
