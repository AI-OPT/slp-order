package com.ai.slp.order.api.orderstate.param;

import java.io.Serializable;

import com.ai.opt.base.vo.BaseInfo;

public class WaitRebateRequest extends BaseInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long orderId;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

}
