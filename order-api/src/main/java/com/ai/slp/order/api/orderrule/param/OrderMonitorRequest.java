package com.ai.slp.order.api.orderrule.param;

import java.io.Serializable;

import com.ai.opt.base.vo.BaseInfo;

/**
 * 订单监控参数
 */
public class OrderMonitorRequest extends BaseInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userId; //用户id
	private String ipAddress;//ip地址

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

}
