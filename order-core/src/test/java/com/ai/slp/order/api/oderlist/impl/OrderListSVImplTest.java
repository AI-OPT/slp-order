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
import com.alibaba.fastjson.JSON;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/context/core-context.xml"})
public class OrderListSVImplTest {

    @Autowired
    private IOrderListSV orderListSV;

    @Test
    public void orderListTest() {
//        QueryOrderListRequest request = new QueryOrderListRequest();
//        request.setTenantId("SLP");
//        request.setUserId("000000000000000949");
//        request.setPageNo(1);
//        request.setPageSize(5);
//        System.out.println(orderListSV.queryOrderList(request).getResponseHeader());
//        System.out.println(orderListSV.queryOrderList(request).getPageInfo().getResult().size());
        
        QueryOrderRequest request = new QueryOrderRequest();
        request.setOrderId(78436478);
        request.setTenantId("SLP");
        QueryOrderResponse queryOrder = orderListSV.queryOrder(request);
        OrdOrderVo ordOrderVo = queryOrder.getOrdOrderVo();
        System.out.println("param="+JSON.toJSONString(request));
        System.out.println("result="+JSON.toJSONString(ordOrderVo));
    }
}
