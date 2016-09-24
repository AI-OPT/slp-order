package com.ai.slp.order.api.invoiceprint.param;

import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.base.vo.PageInfo;

public class InvoicePrintResponse extends BaseResponse{

	private static final long serialVersionUID = 1L;
	
	private PageInfo<InvoicePrintVo> pageInfo;

	public PageInfo<InvoicePrintVo> getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfo<InvoicePrintVo> pageInfo) {
		this.pageInfo = pageInfo;
	}
}
