package com.ai.slp.order.service.business.interfaces;


import java.util.Map;

import com.ai.opt.base.vo.PageInfo;
import com.ai.slp.order.api.orderDetailList.param.OrdOrderDetailParams;
import com.ai.slp.order.api.orderDetailList.param.OrdOrderDetailParamsResponse;
import com.ai.slp.order.api.orderDetailList.param.OrderDetailListRequest;


public interface IordOrderDetailBusiSV {
    
    public Map<String,Object> getFastChargeBillDetailInfo(OrderDetailListRequest orderDetailInfo);
    
    public PageInfo<OrdOrderDetailParams> getFastChargeFlowDetailInfo(OrderDetailListRequest orderDetailInfo);
    
    public PageInfo<OrdOrderDetailParams> getRechargeCardsDetailInfo(OrderDetailListRequest orderDetailInfo);
    
    public OrdOrderDetailParamsResponse getDetailsInfo(OrdOrderDetailParams ordOrder);
}
