package com.ai.slp.order.api.orderPayimpl;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ai.opt.base.vo.BaseResponse;
import com.ai.slp.order.api.orderpay.interfaces.IOrderPaySV;
import com.ai.slp.order.api.orderpay.param.OrderOidRequest;
import com.ai.slp.order.api.orderpay.param.OrderPayRequest;
import com.alibaba.fastjson.JSON;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/context/core-context.xml" })
public class OrderPayImplTest {

    @Autowired
    private IOrderPaySV iOrderPaySV;

    @Test
    public void orderPayTest() {
        OrderPayRequest request = new OrderPayRequest();
        ArrayList<Long> arrayList = new ArrayList<Long>();
        arrayList.add(2000034412999827l);
        arrayList.add(2000034413394047l);
        arrayList.add(2000034413497848l);
        request.setOrderIds(arrayList);
        request.setExternalId("123456");
        request.setPayFee(30000l);
        request.setPayType("21");
        request.setTenantId("changhong");
        System.out.println(JSON.toJSONString(request));
        System.out.println("==============");

        BaseResponse pay = iOrderPaySV.pay(request);
        System.out.println(JSON.toJSON(pay));
    }
    
    @Test
    public void returnOid() {
    	OrderOidRequest request=new OrderOidRequest();
    	request.setOid("1313");
    	request.setOrderId(2000034374195624l);
    	request.setTenantId("changhong");
    	BaseResponse response = iOrderPaySV.returnOid(request);
    	System.out.println(JSON.toJSON(response));
    }
}