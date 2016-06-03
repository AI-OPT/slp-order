package com.ai.slp.order.api.orderPayimpl;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ai.opt.base.vo.BaseResponse;
import com.ai.slp.order.api.orderpay.interfaces.IOrderPaySV;
import com.ai.slp.order.api.orderpay.param.OrderPayRequest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/context/core-context.xml" })
public class OrderTradeCenterSVImplTest {

    @Autowired
    private IOrderPaySV iOrderPaySV;

    @Test
    public void orderTradeTest() {
        OrderPayRequest request = new OrderPayRequest();
        ArrayList<Long> arrayList = new ArrayList<Long>();
        arrayList.add(2522477l);
        request.setOrderIds(arrayList);
        request.setExternalId("123456");
        request.setPayFee(39920l);
        request.setPayType("21");
        request.setTenantId("SLP");

        BaseResponse pay = iOrderPaySV.pay(request);
    }
}
