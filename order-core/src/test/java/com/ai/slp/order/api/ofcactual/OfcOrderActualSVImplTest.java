package com.ai.slp.order.api.ofcactual;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ai.opt.base.vo.BaseResponse;
import com.ai.slp.order.api.ofcactual.interfaces.IOfcOrderActualSV;
import com.ai.slp.order.api.ofcactual.param.OfcOrderCreateRequest;
import com.alibaba.fastjson.JSON;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/context/core-context.xml" })
public class OfcOrderActualSVImplTest {
	
	@Autowired
    private IOfcOrderActualSV ofcOrderActualSV;

    @Test
    public void testQueryOrder() {
    	 
    	OfcOrderCreateRequest request=new OfcOrderCreateRequest();
    	List<Long> orderIds=new ArrayList<Long>();
    	orderIds.add(2000001278567640l);
    	request.setOrderIds(orderIds);
    	request.setTenantId("changhong");
    	BaseResponse response = ofcOrderActualSV.orderCreate(request);
    	String str = JSON.toJSONString(response);
    	System.out.println(str);
    }

}
