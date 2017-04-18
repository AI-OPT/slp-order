package com.ai.slp.order.api.warmorder.param;

import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.base.vo.PageInfo;

public class OrderWarmResponse extends BaseResponse {

	private static final long serialVersionUID = 1L;
	private PageInfo<OrderWarmListVo> pageInfo;

	public PageInfo<OrderWarmListVo> getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfo<OrderWarmListVo> pageInfo) {
		this.pageInfo = pageInfo;
	}

}
