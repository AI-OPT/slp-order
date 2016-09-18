package com.ai.slp.order.api.stasticsorder.param;

import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.base.vo.PageInfo;

public class StasticOrderResponse extends BaseResponse {
	private static final long serialVersionUID = 1L;
	private PageInfo<StasticParentOrderVo> pageInfo;

	public PageInfo<StasticParentOrderVo> getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfo<StasticParentOrderVo> pageInfo) {
		this.pageInfo = pageInfo;
	}

}
