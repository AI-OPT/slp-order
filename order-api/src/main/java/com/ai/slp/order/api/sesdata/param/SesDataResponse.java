package com.ai.slp.order.api.sesdata.param;

import java.util.List;

import com.ai.opt.base.vo.BaseResponse;

public class SesDataResponse extends BaseResponse{

	private static final long serialVersionUID = 1L;
	
	//查询的数量
	private int queryCount;
	//失败的数量
	private int failCount;
	//共用父订单数量
	private int shareParentCount;
	//失败订单集合
	private List<Long> failOrders;
	
	public int getQueryCount() {
		return queryCount;
	}
	public void setQueryCount(int queryCount) {
		this.queryCount = queryCount;
	}
	public int getFailCount() {
		return failCount;
	}
	public void setFailCount(int failCount) {
		this.failCount = failCount;
	}
	public int getShareParentCount() {
		return shareParentCount;
	}
	public void setShareParentCount(int shareParentCount) {
		this.shareParentCount = shareParentCount;
	}
	public List<Long> getFailOrders() {
		return failOrders;
	}
	public void setFailOrders(List<Long> failOrders) {
		this.failOrders = failOrders;
	}
}
