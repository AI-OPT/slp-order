package com.ai.slp.order.api.ordermodify.param;


import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

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
	@NotNull(message="订单id不能为空")
    private Long orderId;
    
    /**
     * 改动金额
     */
    private Long updateAmount ;
    
    /**
     * 改动备注
     */
    private String updateRemark;
    
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

	
	public Long getUpdateAmount() {
		return updateAmount;
	}

	public void setUpdateAmount(Long updateAmount) {
		this.updateAmount = updateAmount;
	}

	public String getUpdateRemark() {
		return updateRemark;
	}

	public void setUpdateRemark(String updateRemark) {
		this.updateRemark = updateRemark;
	}

	public String getOperId() {
		return operId;
	}

	public void setOperId(String operId) {
		this.operId = operId;
	}
}
