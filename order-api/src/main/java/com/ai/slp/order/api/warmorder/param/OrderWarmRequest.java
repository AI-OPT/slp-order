package com.ai.slp.order.api.warmorder.param;

import java.sql.Timestamp;

import com.ai.opt.base.vo.BaseInfo;

public class OrderWarmRequest extends BaseInfo {
	private static final long serialVersionUID = 1L;
	/**
	 * 下单开始时间
	 */
	private Timestamp orderTimeStart;
	/**
	 * 下单结束时间
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
