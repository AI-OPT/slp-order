package com.ai.slp.order.api.sesdata.param;

import com.ai.opt.base.vo.BaseInfo;

public class SesDataByPageRequest extends BaseInfo{

	private static final long serialVersionUID = 1L;
	
    /**
     * pageNo
     */
    private Integer pageNo;

    /**
     * pageSize
     */
    private Integer pageSize;
	
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
