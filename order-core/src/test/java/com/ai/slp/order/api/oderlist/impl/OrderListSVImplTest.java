package com.ai.slp.order.api.oderlist.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ai.opt.sdk.util.DateUtil;
import com.ai.slp.order.api.orderlist.interfaces.IOrderListSV;
import com.ai.slp.order.api.orderlist.param.QueryOrderListRequest;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.dao.mapper.bo.OrdOrderCriteria;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.ai.slp.order.service.business.interfaces.IOrderCancelBusiSV;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/context/core-context.xml" })
public class OrderListSVImplTest {

    @Autowired
    private IOrderListSV orderListSV;

    @Autowired
    private IOrdOrderAtomSV ordOrderAtomSV;

    @Autowired
    private IOrderCancelBusiSV orderCancelBusiSV;

    @Test
    public void orderListTest() {
         QueryOrderListRequest request = new QueryOrderListRequest();
         request.setTenantId("SLP");
         request.setUserId("000000000000000949");
         request.setPayStyle("21");
         request.setPageNo(1);
         request.setPageSize(5);
         System.out.println(orderListSV.queryOrderList(request).getResponseHeader());
         System.out.println(orderListSV.queryOrderList(request).getPageInfo().getResult().size());

//        QueryOrderRequest request = new QueryOrderRequest();
//        request.setOrderId(78436478);
//        request.setTenantId("SLP");
//        QueryOrderResponse queryOrder = orderListSV.queryOrder(request);
//        OrdOrderVo ordOrderVo = queryOrder.getOrdOrderVo();
//        System.out.println("param=" + JSON.toJSONString(request));
//        System.out.println("result=" + JSON.toJSONString(ordOrderVo));
    }

    @Test
    public void getNoPayOrderList() {
        Timestamp sysDate = DateUtil.getSysDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sysDate);
        calendar.add(Calendar.MINUTE, -30);
        OrdOrderCriteria example = new OrdOrderCriteria();
        OrdOrderCriteria.Criteria criteria = example.createCriteria();
        criteria.andOrderIdEqualTo(83540589l);
        criteria.andOrderTimeLessThan(new Timestamp(calendar.getTimeInMillis()));
        criteria.andStateEqualTo(OrdersConstants.OrdOrder.State.WAIT_PAY);
        criteria.andBusiCodeEqualTo(OrdersConstants.OrdOrder.BusiCode.NORMAL_ORDER);
        List<OrdOrder> list = ordOrderAtomSV.selectByExample(example);
        for (OrdOrder ordOrder : list) {
            try {
                orderCancelBusiSV.orderCancel(ordOrder);
            } catch (Exception e) {
                continue;
            }
        }
    }
}
