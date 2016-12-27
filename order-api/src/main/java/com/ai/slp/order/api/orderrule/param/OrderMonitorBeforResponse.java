package com.ai.slp.order.api.orderrule.param;

import java.io.Serializable;

import com.ai.opt.base.vo.BaseResponse;

/**
 * 订单下单前监控返回信息
 */
public class OrderMonitorBeforResponse extends BaseResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	private String ifWarning; //是否预警
	private String warningType;//预警类型
	private String warningDesc;//预警描述

	public String getWarningDesc() {
		return warningDesc;
	}

	public void setWarningDesc(String warningDesc) {
		this.warningDesc = warningDesc;
	}

	public String getIfWarning() {
		return ifWarning;
	}

	public void setIfWarning(String ifWarning) {
		this.ifWarning = ifWarning;
	}

	public String getWarningType() {
		return warningType;
	}

	public void setWarningType(String warningType) {
		this.warningType = warningType;
	}

}
