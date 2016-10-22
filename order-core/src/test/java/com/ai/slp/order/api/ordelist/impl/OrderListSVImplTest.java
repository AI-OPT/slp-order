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
import com.ai.slp.order.api.orderlist.param.QueryApiOrderRequest;
import com.ai.slp.order.api.orderlist.param.QueryApiOrderResponse;
import com.ai.slp.order.api.orderlist.param.QueryOrderListRequest;
import com.ai.slp.order.api.orderlist.param.QueryOrderRequest;
import com.ai.slp.order.api.orderlist.param.QueryOrderResponse;
import com.alibaba.fastjson.JSON;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/context/core-context.xml" })
public class OrderListSVImplTest {

    @Autowired
    private IOrderListSV orderListSV;

    @Test
    public void orderListTest() {
         QueryOrderListRequest request = new QueryOrderListRequest();
         request.setTenantId("SLP");
         request.setUserId("000000000000000949");
         request.setPayStyle("");
         request.setOrderType("");
         request.setPageNo(1);
         request.setPageSize(5);
         request.setOrderTimeBegin("2016-05-01 00:00:00");
         request.setOrderTimeEnd("2016-07-13 10:03:32");
         System.out.println(orderListSV.queryOrderList(request).getResponseHeader());
         System.out.println(orderListSV.queryOrderList(request).getPageInfo().getResult().size());
    }

    @Test
    public void testApiOrder(){
    	QueryApiOrderRequest orderR=new QueryApiOrderRequest();
    	orderR.setTenantId("SLP");
    	orderR.setUserId("000000000000001203");
    	orderR.setDownstreamOrderId("7201653");
    	QueryApiOrderResponse response;
		try {
			response = orderListSV.queryApiOrder(orderR);
			String jsonString = JSON.toJSONString(response);
			System.out.println(jsonString);
			System.out.println(response);
		} catch (BusinessException e) {
			System.out.println(e.getErrorCode());
			System.out.println(e.getErrorMessage());
			e.printStackTrace();
		} catch (SystemException e) {
			System.out.println(e.getErrorCode());
			System.out.println(e.getErrorMessage());
			e.printStackTrace();
		}
    }
    
    @Test
    public void testQueryOrder() {
    	QueryOrderRequest request=new QueryOrderRequest();
    	request.setOrderId(2000001031847561l);
    	request.setTenantId("changhong");
    	QueryOrderResponse response = orderListSV.queryOrder(request);
    	String str = JSON.toJSONString(response);
    	System.out.println(str);
    }
    
    @Test
    public void behindOrderListTest() {
    	BehindQueryOrderListRequest request = new BehindQueryOrderListRequest();
         request.setTenantId("changhong");
        // List<String> stateList=new ArrayList<String>();
        // request.setContactTel("18210680992");
        //stateList.add("21,212,22,23,31,92,93,94");
      //  stateList.add("91");
        // stateList.add("14");
        // stateList.add("16");
       request.setOrderId(2000000978713555l);
         request.setPageNo(1);
         request.setPageSize(5);
    //  request.setStateList(stateList);
         //request.setRouteId("仓库1");
         //request.setOrderTimeBegin("2016-07-14 16:15:29");
         //request.setOrderTimeEnd("2016-08-15 16:16:29");
         //request.setChlId("9001");
        // request.setContactTel("");
       //  request.setDeliveryFlag("Y");
         //request.setUserId("");
      	
         
         System.out.println(JSON.toJSON(request));
         BehindQueryOrderListResponse response = orderListSV.behindQueryOrderList(request);
         String str = JSON.toJSONString(response);
         System.out.println(str);
         System.out.println(orderListSV.behindQueryOrderList(request).getPageInfo().getResult().size());
    }
}
