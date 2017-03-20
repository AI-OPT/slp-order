package com.ai.slp.order.api.aftersaleorder.impl;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.slp.order.api.aftersaleorder.interfaces.IOrderAfterSaleSV;
import com.ai.slp.order.api.aftersaleorder.param.OrderOFCBackRequest;
import com.ai.slp.order.api.aftersaleorder.param.OrderReturnRequest;
import com.alibaba.fastjson.JSON;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/context/core-context.xml" })
public class OrderAfterSaleSVImplTest {
	
	@Autowired
	private IOrderAfterSaleSV orderAfterSaleSV;
	@Test
	public void testBack() {
		try {
			OrderReturnRequest req=new OrderReturnRequest();
			req.setOrderId(2000034322571899l);
			req.setProdDetalId(626927l);
			req.setProdSum(1l);
			req.setTenantId("changhong");
			req.setOperId("11111");
			req.setAfterSaleReason("testssssss111");
			req.setImageType(".jpg");
			req.setImageId("5804376946e0fb000610ef3b");
			System.out.println(JSON.toJSONString(req));
			orderAfterSaleSV.back(req);
			System.out.println(11);
		} catch (BusinessException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testExchange() {
		try {
			OrderReturnRequest req=new OrderReturnRequest();
			req.setOrderId(2000001059646714l);
			req.setProdDetalId(6023l);
			req.setTenantId("changhong");
			req.setOperId("1111");
			req.setProdSum(1);
			System.out.println(JSON.toJSONString(req));
			orderAfterSaleSV.exchange(req);
			System.out.println(11);
		} catch (BusinessException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testRefund() {
		try {
			OrderReturnRequest req=new OrderReturnRequest();
			req.setOrderId(2000034322571899l);
			req.setProdDetalId(626927l);
			req.setProdSum(1l);
			req.setTenantId("changhong");
			req.setOperId("11111");
			req.setAfterSaleReason("testssssss111");
			req.setImageType(".jpg");
			req.setImageId("5804376946e0fb000610ef3b");
			System.out.println(JSON.toJSONString(req));
			orderAfterSaleSV.refund(req);
		} catch (BusinessException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testOfcStateBack(){
		//2000034307433482
		OrderOFCBackRequest request=new OrderOFCBackRequest();
		request.setExternalOrderId("2000034307569588");
		request.setOrderId("2000034307433482");
		request.setState("21");
		BaseResponse response = orderAfterSaleSV.backStateOFC(request);
		System.out.println(JSON.toJSON(response));
	}

}
