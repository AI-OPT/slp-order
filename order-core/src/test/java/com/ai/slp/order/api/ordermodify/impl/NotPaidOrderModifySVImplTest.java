package com.ai.slp.order.api.ordermodify.impl;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ai.opt.base.vo.BaseResponse;
import com.ai.slp.order.api.ordermodify.interfaces.INotPaidOrderModifySV;
import com.ai.slp.order.api.ordermodify.param.OrderModifyRequest;
import com.alibaba.fastjson.JSON;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/context/core-context.xml" })
public class NotPaidOrderModifySVImplTest {
	
	@Autowired
	INotPaidOrderModifySV notPaidOrderModifySV;

	@Test
	public void test() {
        OrderModifyRequest request=new OrderModifyRequest();
        request.setOrderId(3345703912l);
		request.setUpdateAmount(6000l);
		request.setTenantId("changhong");
		request.setUpdateRemark("测试.....");
		System.out.println(JSON.toJSONString(request));
		BaseResponse response = notPaidOrderModifySV.modify(request);
		System.out.println(response);
	}
	
	  public static String trim(String str) {  
	        return ((str == null) ? "" : str.trim());  
	    } 

}
