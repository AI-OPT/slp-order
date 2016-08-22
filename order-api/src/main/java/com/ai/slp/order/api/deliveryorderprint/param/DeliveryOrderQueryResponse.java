package com.ai.slp.order.api.deliveryorderprint.param;

import com.ai.opt.base.vo.BaseResponse;

public class DeliveryOrderQueryResponse extends BaseResponse{

	private static final long serialVersionUID = 1L;
	
	private boolean flag;

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
}
