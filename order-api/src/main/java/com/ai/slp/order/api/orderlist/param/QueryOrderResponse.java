package com.ai.slp.order.api.orderlist.param;

import com.ai.opt.base.vo.BaseResponse;

public class QueryOrderResponse extends BaseResponse{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 订单对象
	 */
	private OrdOrderVo ordOrderVo;

	public OrdOrderVo getOrdOrderVo() {
		return ordOrderVo;
	}

	public void setOrdOrderVo(OrdOrderVo ordOrderVo) {
		this.ordOrderVo = ordOrderVo;
	}

}

