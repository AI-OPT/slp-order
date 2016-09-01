package com.ai.slp.order.api.ordermodify.param;


import com.ai.opt.base.vo.BaseInfo;

/**
 * 未支付订单金额修改参数
 * @date 2016年8月8日 
 * @author caofz
 */
public class OrderModifyRequest extends BaseInfo{

	private static final long serialVersionUID = 1L;
	
	/**
     * 业务订单ID
     */
    private Long orderId;
    
    /**
     * 订单金额
     */
    private Long adjustFee;
    
    /**
     * 订单备注
     */
    private String remark;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getAdjustFee() {
		return adjustFee;
	}

	public void setAdjustFee(Long adjustFee) {
		this.adjustFee = adjustFee;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
    
}
