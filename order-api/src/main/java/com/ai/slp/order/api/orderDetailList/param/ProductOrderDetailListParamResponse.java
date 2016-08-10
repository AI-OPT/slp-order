package com.ai.slp.order.api.orderDetailList.param;


import com.ai.opt.base.vo.PageInfo;

public class ProductOrderDetailListParamResponse {
    public PageInfo<ProductOrderDetailListParam> list;

    public PageInfo<ProductOrderDetailListParam> getList() {
        return list;
    }

    public void setList(PageInfo<ProductOrderDetailListParam> list) {
        this.list = list;
    }
    
}
