package com.ai.slp.order.api.sesdata.param;

import com.ai.opt.base.vo.BaseInfo;

public class SesDataRequest extends BaseInfo{

	private static final long serialVersionUID = 1L;
	//父订单id
	private Long parentOrderId;
	
	public Long getParentOrderId() {
		return parentOrderId;
	}

	public void setParentOrderId(Long parentOrderId) {
		this.parentOrderId = parentOrderId;
	}
}
