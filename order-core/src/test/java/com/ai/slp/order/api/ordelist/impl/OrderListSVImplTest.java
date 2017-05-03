package com.ai.slp.order.api.ordelist.impl;



import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
    	request.setOrderId(2000034384443504l);
    	request.setTenantId("changhong");
    	QueryOrderResponse response = orderListSV.queryOrder(request);
    	String str = JSON.toJSONString(response);
    	System.out.println(str);
    }
    
    @Test
    public void behindOrderListTest() {
    	BehindQueryOrderListRequest request = new BehindQueryOrderListRequest();
         request.setTenantId("changhong");
      //  request.setUserId("7048d255c62e4511");
     //   List<Object> stateList=new ArrayList<Object>();
    List<Object> flagList=new ArrayList<Object>();
      flagList.add("2");
     //    request.setContactTel("18210680992");
     //  stateList.add("21");
    //  request.setOrderId(2000034314177605l);
     //   request.setProdName("test22");
         request.setPageNo(1);
         request.setPageSize(5);
      //   request.setStateList(stateList);
         request.setFlagList(flagList);
     //    request.setDeliveryFlag("Y");
         
     //    request.setProdName("说的发挥3");
       //  request.setRouteId("0000000000000511");
    //    request.setOrderTimeBegin("2017-04-29 00:00:00");
        // request.setOrderTimeEnd("2016-08-15 16:16:29");
      //   request.setChlId("9001");
      //   request.setContactTel("");
       //  request.setDeliveryFlag("Y");
      //   request.setUserId("7048d255c62e4511");
      	
         
         System.out.println(JSON.toJSON(request));
         BehindQueryOrderListResponse response = orderListSV.behindQueryOrderList(request);
         String str = JSON.toJSONString(response);
         System.out.println(str);
         System.out.println(orderListSV.behindQueryOrderList(request).getPageInfo().getResult().size());
    }
}
