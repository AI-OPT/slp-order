package com.ai.slp.order.api.orderPayimpl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.sdk.dubbo.util.HttpClientUtil;
import com.ai.opt.sdk.util.DateUtil;
import com.ai.slp.order.api.orderpay.interfaces.IOrderPaySV;
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
        arrayList.add(2000001075298641l);
        arrayList.add(2000001075178392l);
        arrayList.add(2000001075056067l);
        request.setOrderIds(arrayList);
        request.setExternalId("123456");
        request.setPayFee(463500l);
        request.setPayType("21");
        request.setTenantId("changhong");
        System.out.println(JSON.toJSONString(request));
        System.out.println("==============");

        BaseResponse pay = iOrderPaySV.pay(request);
    }
}
