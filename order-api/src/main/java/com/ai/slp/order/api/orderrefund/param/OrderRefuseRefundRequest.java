package com.ai.slp.order.api.orderrefund.param;
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
	private Long orderId;
	
	/**
	 * 拒绝理由
	 */
	private String refuseReason;
	
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
