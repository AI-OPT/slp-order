package com.ai.slp.order.api.aftersaleorder.impl;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.slp.order.api.aftersaleorder.interfaces.IOrderAfterSaleSV;
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
			req.setOrderId(3334570391232l);
			//req.setProdDetalId(535345122);
			req.setProdSum(2l);
			req.setTenantId("changhong");
			req.setOperId("");
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
			req.setOrderId(2000000978713235l);
			//req.setProdDetalId(32342614);
			req.setTenantId("changhong");
			req.setOperId("");
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
			req.setOrderId(2000000978713235l);
			//req.setProdDetalId(32342614);
			req.setTenantId("changhong");
			req.setOperId("");
			System.out.println(JSON.toJSONString(req));
			orderAfterSaleSV.refund(req);
			System.out.println(11);
		} catch (BusinessException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
