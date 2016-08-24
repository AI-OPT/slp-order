package com.ai.slp.order.api.ordercheck.param;

import javax.validation.constraints.NotNull;

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
	private long orderId;
	
	/**
	 * 审核结果
	 */
	private int checkResult; //1 同意,0 拒绝
	
	/**
	 * 审核结果描述
	 */
	private String remark;
	
	/**
	 * 审核人
	 */
	private String operId;

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public int getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(int checkResult) {
		this.checkResult = checkResult;
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
