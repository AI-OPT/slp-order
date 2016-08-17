package com.ai.slp.order.api.orderDetailList.param;

import com.ai.opt.base.vo.PageInfo;

public class OrdOrderDetailParamsResponse {
    /**
     * 明细
     */
    public PageInfo<OrdOrderDetailParams> detailsList;

    public PageInfo<OrdOrderDetailParams> getDetailsList() {
        return detailsList;
    }

    public void setDetailsList(PageInfo<OrdOrderDetailParams> detailsList) {
        this.detailsList = detailsList;
    }
    
    
}
