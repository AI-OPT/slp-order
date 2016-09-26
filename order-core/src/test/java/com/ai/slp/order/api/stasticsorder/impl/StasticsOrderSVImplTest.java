package com.ai.slp.order.api.stasticsorder.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ai.slp.order.api.stasticsorder.interfaces.IStasticsOrderSV;
import com.ai.slp.order.api.stasticsorder.param.StasticOrderResponse;
import com.ai.slp.order.api.stasticsorder.param.StasticsOrderRequest;
import com.alibaba.fastjson.JSON;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/context/core-context.xml" })
public class StasticsOrderSVImplTest {
	@Autowired
    private IStasticsOrderSV iStasticsOrderSV;
	@Test
    public void testStasticOrder(){
		StasticsOrderRequest query=new StasticsOrderRequest();
        query.setTenantId("changhong");
        query.setPageSize(20);
        query.setPageNo(1);
        //query.setState("14");
       // query.setUserId("123");
        query.setOrderId(2003749534534L);
        List<String> list = new ArrayList<String>();
        //list.add("000000000000000945");
       // list.add("123");
       // query.setUserIdList(list);
        //List<Long> suList = new ArrayList<Long>();
        //suList.add(334570392323l);
       // query.setSupplierIdList(suList);
       // query.setState("90");
       // query.setProdName("test22");
        StasticOrderResponse info=iStasticsOrderSV.queryStasticOrdPage(query);
        System.out.println("info="+JSON.toJSONString(info.getPageInfo()));
        
    }
}
