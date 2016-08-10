package com.ai.slp.order.api.ordercheck.param;

import com.ai.opt.base.vo.BaseInfo;

/**
 * 订单审核请求参数
 * @author caofz
 *
 */
public class OrderCheckRequest extends BaseInfo{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 系统Id
	 */
	
	/**
	 * 订单Id
	 */
	private long orderId;
	
	/**
	 * 外部流水号
	 */
	private Long balanceIfId;
	
	/**
	 * 节点编码
	 */
	
	
}
