package com.ai.slp.order.api.warmorder.impl;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ai.slp.order.api.warmorder.interfaces.IOrderWarmSV;
import com.ai.slp.order.api.warmorder.param.OrderWarmDetailRequest;
import com.ai.slp.order.api.warmorder.param.OrderWarmDetailResponse;
import com.ai.slp.order.api.warmorder.param.OrderWarmRequest;
import com.ai.slp.order.api.warmorder.param.OrderWarmResponse;
import com.alibaba.fastjson.JSON;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/context/core-context.xml" })
public class OrdWarmSVImplTest {
	@Autowired
    private IOrderWarmSV iOrderWarmSV;
	@Test
    public void testWarmOrder(){
		OrderWarmRequest query=new OrderWarmRequest();
        query.setTenantId("changhong");
        query.setPageSize(10);
        query.setPageNo(1);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setLenient(false);
        //要转换字符串 str_test
        String str_test1 ="2016-06-12 16:17:29"; 
        String str_test2 ="2017-06-14 16:16:29"; 
        try {
        Timestamp ts = new Timestamp(format.parse(str_test1).getTime());
        Timestamp ts2 = new Timestamp(format.parse(str_test2).getTime());
        query.setOrderTimeStart(ts);
        query.setOrderTimeEnd(ts2);
       // System.out.println(ts.toString());
        } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        }
        OrderWarmResponse info=iOrderWarmSV.serchWarmOrder(query);
        System.out.println("info="+JSON.toJSONString(info));
        
    }
	@Test
    public void testWarmOrderDetail(){
		OrderWarmDetailRequest  query=new OrderWarmDetailRequest ();
        query.setTenantId("changhong");
        query.setOrderId(2000001000218776l);
        OrderWarmDetailResponse info=iOrderWarmSV.searchWarmorderDetail(query);
        System.out.println("info="+JSON.toJSONString(info));
        
    }
}
