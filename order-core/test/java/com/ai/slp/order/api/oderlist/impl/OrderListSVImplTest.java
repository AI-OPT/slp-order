package com.ai.slp.order.api.oderlist.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ai.slp.order.api.orderlist.interfaces.IOrderListSV;
import com.ai.slp.order.api.orderlist.param.QueryOrderListRequest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/context/core-context.xml" })
public class OrderListSVImplTest {

    @Autowired
    private IOrderListSV orderListSV;

    @Test
    public void orderListTest() {
        QueryOrderListRequest request = new QueryOrderListRequest();
        request.setTenantId("test111");
        request.setOrderId(111L);
        request.setPageNo(111);
        request.setPageSize(111);
        request.setPayStyle("11");
        System.out.println(orderListSV.queryOrderList(request).getResponseHeader());
        System.out.println(orderListSV.queryOrderList(request).getPageInfo().getResult().size());
    }
}
