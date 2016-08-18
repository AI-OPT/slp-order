package com.ai.slp.order.api.invoiceprint.param;

import javax.validation.constraints.NotNull;


import com.ai.opt.base.vo.BaseInfo;

/**
 * 发货单打印参数
 * @author caofz
 *
 */
public class InvoicePrintRequest extends BaseInfo{

	private static final long serialVersionUID = 1L;
	
	/**
     * 订单号
     */
	@NotNull(message="订单id不能为空")
    private Long orderId;
    
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
}
