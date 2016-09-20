package com.ai.slp.order.api.ofcorderquery.param;

import java.util.List;

import com.ai.opt.base.vo.BaseInfo;

public class OFCOrderQueryRequest extends BaseInfo{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 订单号列表
	 */
	private List<OFCOrderVo> orderNoList;
	
	/**
	 * 店铺名称 (渠道id对应的名称)
	 */
	private String shopName;
	
	/**
	 * 当前页数
	 */
	private int pageIndex;
	
	/**
	 * 每页数量
	 */
	private int pageSize;

	public List<OFCOrderVo> getOrderNoList() {
		return orderNoList;
	}

	public void setOrderNoList(List<OFCOrderVo> orderNoList) {
		this.orderNoList = orderNoList;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
