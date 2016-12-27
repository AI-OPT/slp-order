package com.ai.slp.order.api.orderstate.param;

import java.io.Serializable;

import com.ai.opt.base.vo.BaseInfo;

/**
 * 发货请求参数
 */
public class WaitSellReceiveSureRequest extends BaseInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long orderId; //订单id
	private String expressId;//物流公司id
	private String expressOddNumber;//物流单号
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getExpressId() {
		return expressId;
	}
	public void setExpressId(String expressId) {
		this.expressId = expressId;
	}
	public String getExpressOddNumber() {
		return expressOddNumber;
	}
	public void setExpressOddNumber(String expressOddNumber) {
		this.expressOddNumber = expressOddNumber;
	}
	
}
