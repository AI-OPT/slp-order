package com.ai.slp.order.api.ofcorderquery.param;

import java.util.List;

import com.ai.opt.base.vo.BaseResponse;

public class OFCOrderQueryResponse extends BaseResponse {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 订单信息
	 */
	private OFCOrderResVo order;
	
	/**
	 * 订单明细
	 */
	private List<OrderItemsResVo> items;
	
	/**
	 * 优惠明细
	 */
	private List<OrderCouponResVo> couponList;
	
	/**
	 * 发货单列表
	 */
	private List<OFCLogisticsResVo> shipOrderList;

	public OFCOrderResVo getOrder() {
		return order;
	}

	public void setOrder(OFCOrderResVo order) {
		this.order = order;
	}

	public List<OrderItemsResVo> getItems() {
		return items;
	}

	public void setItems(List<OrderItemsResVo> items) {
		this.items = items;
	}

	public List<OrderCouponResVo> getCouponList() {
		return couponList;
	}

	public void setCouponList(List<OrderCouponResVo> couponList) {
		this.couponList = couponList;
	}

	public List<OFCLogisticsResVo> getShipOrderList() {
		return shipOrderList;
	}

	public void setShipOrderList(List<OFCLogisticsResVo> shipOrderList) {
		this.shipOrderList = shipOrderList;
	}
}
