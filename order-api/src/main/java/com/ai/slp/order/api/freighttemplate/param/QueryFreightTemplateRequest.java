package com.ai.slp.order.api.freighttemplate.param;

import java.io.Serializable;

public class QueryFreightTemplateRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 销售商id
	 */
	private String supplierId;
	
	  /**
     * pageNo
     */
    private Integer pageNo;

    /**
     * pageSize
     */
    private Integer pageSize;

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
}
