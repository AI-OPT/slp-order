package com.ai.slp.order.api.ordercheck.param;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.ai.opt.base.vo.BaseInfo;

/**
 * 订单审核请求参数
 * @author caofz
 *
 */
public class OrderCheckRequest extends BaseInfo{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 订单Id
	 */
	@NotNull(message = "订单ID不能为空")
	private Long orderId;
	
	/**
	 * 审核结果
	 */
	@NotBlank(message = "审核结果不能为空")
	private String state; //211 已审核(通过),212 审核失败(不通过)
	
	/**
	 * 审核结果描述
	 */
	private String remark;
	
	/**
	 * 审核工号
	 */
	@NotBlank(message="审核工号不能为空")
	private String operId;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOperId() {
		return operId;
	}

	public void setOperId(String operId) {
		this.operId = operId;
	}
	
}
