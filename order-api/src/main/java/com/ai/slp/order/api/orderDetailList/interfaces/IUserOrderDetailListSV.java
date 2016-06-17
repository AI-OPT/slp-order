package com.ai.slp.order.api.orderDetailList.interfaces;

import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
@Path("/userorder")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
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
