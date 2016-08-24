package com.ai.slp.order.api.ordercheck.param;

import com.ai.opt.base.vo.BaseInfo;

/**
 * 订单审核请求参数
 * @author caofz
 *
 */
public class OrderCheckRequest extends BaseInfo{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 订单Id
	 */
	private long orderId;

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	
}
