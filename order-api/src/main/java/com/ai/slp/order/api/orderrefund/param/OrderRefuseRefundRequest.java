package com.ai.slp.order.api.orderrefund.param;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.ai.opt.base.vo.BaseInfo;

/**
 * 订单拒绝退款参数
 * @date 2016年8月30日 
 * @author caofz
 */
public class OrderRefuseRefundRequest extends BaseInfo {

	private static final long serialVersionUID = 1L;
	
	/**
	 *订单id 
	 */
	@NotNull(message="订单id不能为空")
	private Long orderId;
	
	/**
	 * 拒绝理由
	 */
	@NotBlank(message="拒绝退款理由不能为空")
	private String refuseReason;
	
	/**
	 * 受理工号
	 */
	@NotBlank(message="受理工号不能为空")
	private String operId;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getRefuseReason() {
		return refuseReason;
	}

	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}

	public String getOperId() {
		return operId;
	}

	public void setOperId(String operId) {
		this.operId = operId;
	}
}
