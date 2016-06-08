package com.ai.slp.order.api.oderlist.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ai.slp.order.api.orderlist.interfaces.IOrderListSV;
import com.ai.slp.order.api.orderlist.param.OrdOrderVo;
import com.ai.slp.order.api.orderlist.param.QueryOrderListRequest;
import com.ai.slp.order.api.orderlist.param.QueryOrderRequest;
import com.ai.slp.order.api.orderlist.param.QueryOrderResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/context/core-context.xml"})
public class OrderListSVImplTest {

    @Autowired
    private IOrderListSV orderListSV;

    @Test
    public void orderListTest() {
        QueryOrderListRequest request = new QueryOrderListRequest();
        request.setTenantId("SLP");
        request.setOrderId(2456229l);
        request.setUserId("234");
        request.setPageNo(1);
        request.setPageSize(3);
        System.out.println(orderListSV.queryOrderList(request).getResponseHeader());
        System.out.println(orderListSV.queryOrderList(request).getPageInfo().getResult().size());
        
//        QueryOrderRequest request = new QueryOrderRequest();
//        request.setOrderId(2456229);
//        request.setTenantId("SLP");
//        QueryOrderResponse queryOrder = orderListSV.queryOrder(request);
//        OrdOrderVo ordOrderVo = queryOrder.getOrdOrderVo();
//        System.out.println(ordOrderVo.getBusiCode());
    }
}
