package com.ai.slp.order.api.stasticsorder.impl;

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
        query.setPageSize(10);
        query.setPageNo(1);
        //query.setState("14");
        query.setUserId("123");
        StasticOrderResponse info=iStasticsOrderSV.queryStasticOrdPage(query);
        System.out.println("info="+JSON.toJSONString(info.getPageInfo()));
        
    }
}