package com.ai.slp.order.api.orderlist.param;

import java.util.List;

import com.ai.opt.base.vo.BaseResponse;

public class QueryApiOrderResponse extends BaseResponse{

	private static final long serialVersionUID = 1L;
	
	private List<OrdOrderApiVo> orderApiVo;

	public List<OrdOrderApiVo> getOrderApiVo() {
		return orderApiVo;
	}
	public void setOrderApiVo(List<OrdOrderApiVo> orderApiVo) {
		this.orderApiVo = orderApiVo;
	}
	
}	
