package com.ai.slp.order.service.business.interfaces;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.slp.order.api.ofc.params.OrdOdFeeTotalVo;
import com.ai.slp.order.api.ofc.params.OrdOdLogisticsVo;
import com.ai.slp.order.api.ofc.params.OrdOdProdVo;
import com.ai.slp.order.api.ofc.params.OrdOrderOfcVo;
import com.ai.slp.order.api.orderlist.param.BehindQueryOrderListRequest;
import com.ai.slp.order.api.orderlist.param.BehindQueryOrderListResponse;
import com.ai.slp.order.api.orderlist.param.OrdOrderVo;
import com.ai.slp.order.api.orderlist.param.QueryApiOrderRequest;
import com.ai.slp.order.api.orderlist.param.QueryApiOrderResponse;
import com.ai.slp.order.api.orderlist.param.QueryOrderListRequest;
import com.ai.slp.order.api.orderlist.param.QueryOrderListResponse;
import com.ai.slp.order.api.orderlist.param.QueryOrderRequest;
import com.ai.slp.order.api.orderlist.param.QueryOrderResponse;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;

public interface IOrdOrderBusiSV {

    public QueryOrderListResponse queryOrderList(QueryOrderListRequest orderListRequest)
            throws BusinessException, SystemException;

    public QueryOrderResponse queryOrder(QueryOrderRequest orderRequest) throws BusinessException,
            SystemException;

    public QueryApiOrderResponse queryApiOrder(QueryApiOrderRequest orderRequest)
            throws BusinessException, SystemException;
    
    public BehindQueryOrderListResponse behindQueryOrderList(BehindQueryOrderListRequest orderListRequest)
            throws BusinessException, SystemException;
    
    public int updateOrder(OrdOrder request)
            throws BusinessException, SystemException;
    
    public int insertOrdOrderOfc(OrdOrderOfcVo request)
            throws BusinessException, SystemException;
    
    public int insertOrdOdFeeTotalOfc(OrdOdFeeTotalVo request)
    		throws BusinessException, SystemException;
    
    public int insertOrdOdProdOfc(OrdOdProdVo request)
    		throws BusinessException, SystemException;
    
    public int insertOrdOdLogisticsOfc(OrdOdLogisticsVo request)
    		throws BusinessException, SystemException;
    
}
