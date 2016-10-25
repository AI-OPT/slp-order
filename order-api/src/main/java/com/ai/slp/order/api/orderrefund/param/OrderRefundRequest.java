package com.ai.slp.order.api.orderrefund.param;

import com.ai.opt.base.vo.BaseInfo;

/**
 * 订单部分退款(修改金额)
 * @date 2016年8月30日 
 * @author caofz
 */
public class OrderRefundRequest extends BaseInfo{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 售后订单
	 */
	private Long orderId;
	
	/**
	 * 修改金额
	 */
	private Long updateMoney;
	
	/**
	 * 修改理由
	 */
	private String updateReason;
	
	/**
	 * 受理工号
	 */
	private String operId;
	

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getUpdateMoney() {
		return updateMoney;
	}

	public void setUpdateMoney(Long updateMoney) {
		this.updateMoney = updateMoney;
	}

	public String getUpdateReason() {
		return updateReason;
	}

	public void setUpdateReason(String updateReason) {
		this.updateReason = updateReason;
	}

	public String getOperId() {
		return operId;
	}

	public void setOperId(String operId) {
		this.operId = operId;
	}
}
