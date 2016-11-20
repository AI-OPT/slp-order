package com.ai.slp.order.api.ofc.params;

import com.ai.opt.base.vo.BaseInfo;

public class OfcCodeRequst extends BaseInfo {

	private static final long serialVersionUID = 1L;

	private String tenantId;

	private String SystemId;

	private String outCode;

	public String getOutCode() {
		return outCode;
	}

	public void setOutCode(String outCode) {
		this.outCode = outCode;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getSystemId() {
		return SystemId;
	}

	public void setSystemId(String systemId) {
		SystemId = systemId;
	}

}
