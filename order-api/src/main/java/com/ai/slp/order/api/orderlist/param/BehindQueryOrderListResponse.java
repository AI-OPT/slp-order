package com.ai.slp.order.api.orderlist.param;

import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.base.vo.PageInfo;

public class BehindQueryOrderListResponse extends BaseResponse{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 运营后台列表信息
	 */
	private PageInfo<BehindOrdOrderVo> pageInfo;

	public PageInfo<BehindOrdOrderVo> getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfo<BehindOrdOrderVo> pageInfo) {
		this.pageInfo = pageInfo;
	}

}
