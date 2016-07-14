package com.ai.slp.order.api.orderlist.param;

import com.ai.opt.base.vo.BaseResponse;

public class QueryApiOrderResponse extends BaseResponse{

	private static final long serialVersionUID = 1L;
	
	private OrdOrderApiVo orderApiVo;

	public OrdOrderApiVo getOrderApiVo() {
		return orderApiVo;
	}

	public void setOrderApiVo(OrdOrderApiVo orderApiVo) {
		this.orderApiVo = orderApiVo;
	}
}	
