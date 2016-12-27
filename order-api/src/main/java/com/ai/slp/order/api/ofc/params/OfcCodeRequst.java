package com.ai.slp.order.api.ofc.params;

import com.ai.opt.base.vo.BaseInfo;

/**
 * ofc代码解析
 * Date: 2016年12月27日 <br>
 * Copyright (c) 2016 asiainfo.com <br>
 * 
 * @author zhangqiang7
 */
public class OfcCodeRequst extends BaseInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 租户id
	 */
	private String tenantId;

	/**
	 * 系统id
	 */
	private String SystemId;

	/**
	 * 外部编码
	 */
	private String outCode;

	/**
	 * 参数代码
	 */
	private String paramCode;

	public String getParamCode() {
		return paramCode;
	}

	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}

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
