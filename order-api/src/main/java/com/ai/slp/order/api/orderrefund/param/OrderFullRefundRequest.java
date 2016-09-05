package com.ai.slp.order.api.orderrefund.param;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.ai.opt.base.vo.BaseInfo;

/**
 * 订单退款(全额)
 * @date 2016年8月30日 
 * @author caofz
 */
public class OrderFullRefundRequest extends BaseInfo{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 售后订单
	 */
	@NotNull(message="订单id不能为空")
	private long orderId;
	
	/**
	 * 受理工号
	 */
	@NotBlank(message="受理工号不能为空")
	private String operId;

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public String getOperId() {
		return operId;
	}

	public void setOperId(String operId) {
		this.operId = operId;
	}
}
