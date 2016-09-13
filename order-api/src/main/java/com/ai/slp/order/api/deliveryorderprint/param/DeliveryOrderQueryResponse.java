package com.ai.slp.order.api.deliveryorderprint.param;

import com.ai.opt.base.vo.BaseResponse;

public class DeliveryOrderQueryResponse extends BaseResponse{

	private static final long serialVersionUID = 1L;
	
	private String mark; //标识  1.可合并  2.不可合并 3.不能打印

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}
}
