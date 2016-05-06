package com.ai.slp.order.api.orderDetailList.interfaces;

import java.util.Map;

import com.ai.opt.base.vo.PageInfo;
import com.ai.slp.order.api.orderDetailList.param.OrdOrderDetailParams;
import com.ai.slp.order.api.orderDetailList.param.OrderDetailListRequest;

/**
 * 订单详情列表
 * Date: 2016-5-4 <br>
 * Copyright (c) 2016 asiainfo.com <br>
 * 
 * @author zhangyh7
 */

public interface IUserOrderDetailListSV {
    
    
    @interface GetFastChargeBillDetailInfo{}
    /**
     * 快充话费
     * @param orderDetailInfo
     * @return
     * @author zhangyh7
     * @ApiDocMethod
     */
    public Map<String,Object> getFastChargeBillDetailInfo(OrderDetailListRequest orderDetailInfo);
    
    @interface GetFastChargeFlowDetailInfo{}
    /**
     * 快充流量
     * @param orderDetailInfo
     * @return
     * @author zhangyh7
     * @ApiDocMethod
     */
    public PageInfo<OrdOrderDetailParams> getFastChargeFlowDetailInfo(OrderDetailListRequest orderDetailInfo);
   
    @interface GetRechargeCardsDetailInfo{}
    /**
     * 卡券话费流量
     * @param orderDetailInfo
     * @return
     * @author zhangyh7
     * @ApiDocMethod
     */
    public PageInfo<OrdOrderDetailParams> getRechargeCardsDetailInfo(OrderDetailListRequest orderDetailInfo);
    /**
     * 明细
     * @param ordOrder
     * @return
     * @author zhangyh7
     * @ApiDocMethod
     */
    public OrdOrderDetailParams getDetailsInfo(OrdOrderDetailParams ordOrder);
}
