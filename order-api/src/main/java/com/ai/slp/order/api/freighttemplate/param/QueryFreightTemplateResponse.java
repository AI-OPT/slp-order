package com.ai.slp.order.api.freighttemplate.param;


import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.base.vo.PageInfo;

/**
 * 查询运费模版所需的参数
 * @date 2016年8月4日 
 * @author caofz
 */
public class QueryFreightTemplateResponse extends BaseResponse{

	private static final long serialVersionUID = 1L;
	
	private PageInfo<FreightTemplateVo> pageInfo;

	public PageInfo<FreightTemplateVo> getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfo<FreightTemplateVo> pageInfo) {
		this.pageInfo = pageInfo;
	}

}
