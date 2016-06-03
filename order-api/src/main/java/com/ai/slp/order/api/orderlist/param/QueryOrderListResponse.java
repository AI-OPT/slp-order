package com.ai.slp.order.api.orderlist.param;

import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.base.vo.PageInfo;

/**
 * 查询订单列表出参 Date: 2016年5月3日 <br>
 * Copyright (c) 2016 asiainfo.com <br>
 * 
 * @author zhangqiang7
 */
public class QueryOrderListResponse extends BaseResponse {

    private static final long serialVersionUID = 1L;

    private PageInfo<OrdOrderVo> pageInfo;

    public PageInfo<OrdOrderVo> getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo<OrdOrderVo> pageInfo) {
        this.pageInfo = pageInfo;
    }

}
