package com.ai.slp.order.dubbo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ai.slp.order.api.demo.interfaces.IDemoSV;
import com.ai.slp.order.api.orderlist.interfaces.IOrderListSV;
import com.ai.slp.order.api.orderlist.param.OrdOrderVo;
import com.ai.slp.order.api.orderlist.param.QueryOrderRequest;
import com.ai.slp.order.api.orderlist.param.QueryOrderResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:dubbo/applicationContext-dubbo-service-consumer.xml" })
public class Testrest {

	@Autowired
    protected ApplicationContext ctx;

    public <T> T getBean(Class<T> type) {
        return ctx.getBean(type);
    }

    public Object getBean(String beanName) {
        return ctx.getBean(beanName);
    }
    
    /*@Autowired
    private IOrderListSV orderListSV;*/
    

    @Test
    public void orderListTest() {
    	IOrderListSV orderListSV=(IOrderListSV) ctx.getBean(IOrderListSV.class);
        QueryOrderRequest request = new QueryOrderRequest();
        request.setOrderId(78436478);
        request.setTenantId("SLP");
        QueryOrderResponse queryOrder = orderListSV.queryOrder(request);
        OrdOrderVo ordOrderVo = queryOrder.getOrdOrderVo();
        System.out.println(ordOrderVo.getBusiCode());
    }
    @Test
    public void demotest() {
    	IDemoSV orderListSV=(IDemoSV) ctx.getBean(IDemoSV.class);
    	System.out.println(orderListSV.hello("world"));
    }
    
    
}
