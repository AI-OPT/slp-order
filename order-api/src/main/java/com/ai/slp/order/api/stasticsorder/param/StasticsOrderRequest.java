package com.ai.slp.order.api.stasticsorder.param;

import java.sql.Timestamp;
import java.util.List;

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
	private List<String> userIdList;
	/**
	 * 销售商ID
	 */
	private List<Long> supplierIdList;
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

	public List<String> getUserIdList() {
		return userIdList;
	}

	public void setUserIdList(List<String> userIdList) {
		this.userIdList = userIdList;
	}

	public List<Long> getSupplierIdList() {
		return supplierIdList;
	}

	public void setSupplierIdList(List<Long> supplierIdList) {
		this.supplierIdList = supplierIdList;
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
