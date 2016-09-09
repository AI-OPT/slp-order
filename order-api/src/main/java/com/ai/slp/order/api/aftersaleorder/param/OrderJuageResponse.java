package com.ai.slp.order.api.aftersaleorder.param;

import com.ai.opt.base.vo.BaseResponse;

public class OrderJuageResponse extends BaseResponse{

	private static final long serialVersionUID = 1L;
	
	private OrderAfterVo afterVo;

	public OrderAfterVo getAfterVo() {
		return afterVo;
	}

	public void setAfterVo(OrderAfterVo afterVo) {
		this.afterVo = afterVo;
	}

}
