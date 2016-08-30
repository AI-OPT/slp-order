package com.ai.slp.order.api.orderlist.param;

import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.base.vo.PageInfo;

public class BehindQueryOrderListResponse extends BaseResponse{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 运营后台列表信息
	 */
	private PageInfo<BehindParentOrdOrderVo> pageInfo;

	public PageInfo<BehindParentOrdOrderVo> getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfo<BehindParentOrdOrderVo> pageInfo) {
		this.pageInfo = pageInfo;
	}

}
