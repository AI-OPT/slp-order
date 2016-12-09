package com.ai.slp.order.api.ordelist.impl;


import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.slp.order.api.orderlist.interfaces.IOrderListSV;
import com.ai.slp.order.api.orderlist.param.BehindQueryOrderListRequest;
import com.ai.slp.order.api.orderlist.param.BehindQueryOrderListResponse;
import com.ai.slp.order.api.orderlist.param.QueryOrderRequest;
import com.ai.slp.order.api.orderlist.param.QueryOrderResponse;
import com.alibaba.fastjson.JSON;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/context/core-context.xml" })
public class OrderListSVImplTest {

    @Autowired
    private IOrderListSV orderListSV;

    @Test
    public void testQueryOrder() {
    	QueryOrderRequest request=new QueryOrderRequest();
    	request.setOrderId(277497l);
    	request.setTenantId("changhong");
    	QueryOrderResponse response = orderListSV.queryOrder(request);
    	String str = JSON.toJSONString(response);
    	System.out.println(str);
    }
    
    @Test
    public void behindOrderListTest() {
    	BehindQueryOrderListRequest request = new BehindQueryOrderListRequest();
         request.setTenantId("changhong");
      //   request.setUserId("7048d255c62e4511");
        List<String> stateList=new ArrayList<String>();
     //    request.setContactTel("18210680992");
     //  stateList.add("21,212,213,312,22,23,31,92,93,94,95");
     //    stateList.add("21");
         stateList.add("13");
        stateList.add("14");
     //    stateList.add("15");
       //  request.setOrderId(333295l);
         request.setPageNo(1);
         request.setPageSize(5);
      //   request.setStateList(stateList);
        // request.setRouteId("0000000000000394");
        // request.setOrderTimeBegin("2016-07-14 16:15:29");
        // request.setOrderTimeEnd("2016-08-15 16:16:29");
      //   request.setChlId("9001");
      //   request.setContactTel("");
      //   request.setDeliveryFlag("Y");
      //   request.setUserId("7048d255c62e4511");
      	
         
         System.out.println(JSON.toJSON(request));
         BehindQueryOrderListResponse response = orderListSV.behindQueryOrderList(request);
         String str = JSON.toJSONString(response);
         System.out.println(str);
         System.out.println(orderListSV.behindQueryOrderList(request).getPageInfo().getResult().size());
    }
}
