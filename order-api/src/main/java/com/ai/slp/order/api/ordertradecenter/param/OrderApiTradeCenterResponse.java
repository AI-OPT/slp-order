package com.ai.slp.order.api.ordertradecenter.param;

import com.ai.opt.base.vo.BaseResponse;

/**
 * api订单提交返回参数 Date: 2016年7月22日 <br>
 * @author caofz
 *
 */
public class OrderApiTradeCenterResponse extends BaseResponse{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 外部订单ID（下游）
	 */
	private String downstreamOrderId;
	
	/**
	 * 订单状态
	 */
	private String state;

	public String getDownstreamOrderId() {
		return downstreamOrderId;
	}

	public void setDownstreamOrderId(String downstreamOrderId) {
		this.downstreamOrderId = downstreamOrderId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	

}
