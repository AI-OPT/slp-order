package com.ai.slp.order.api.deliveryorder.impl;

import com.ai.opt.base.vo.BaseResponse;
import com.ai.slp.order.api.deliveryorderprint.interfaces.IDeliveryOrderPrintSV;
import com.ai.slp.order.api.deliveryorderprint.param.DeliveryOrderPrintInfosRequest;
import com.ai.slp.order.api.deliveryorderprint.param.DeliveryOrderPrintRequest;
import com.ai.slp.order.api.deliveryorderprint.param.DeliveryOrderPrintResponse;
import com.ai.slp.order.api.deliveryorderprint.param.DeliveryOrderQueryResponse;
import com.ai.slp.order.api.deliveryorderprint.param.DeliveryProdPrintVo;
import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/context/core-context.xml" })
public class DeliveryOrderPrintSVImplTest {
	
	@Autowired
	private IDeliveryOrderPrintSV deliveryOrderPrintSV;
	
	@Test
	public void testQuery() {
		DeliveryOrderPrintRequest request=new DeliveryOrderPrintRequest();
		request.setOrderId(2000001256210756l);
		request.setUserId("2ecee85451c3460a");
		request.setTenantId("changhong");
		DeliveryOrderQueryResponse response = deliveryOrderPrintSV.query(request);
		System.out.println(response);
	}
	
	
	@Test
	public void testPrint() {
		
		List<DeliveryProdPrintVo> deliveryProdPrintVos=new ArrayList<DeliveryProdPrintVo>();
		DeliveryOrderPrintInfosRequest request=new DeliveryOrderPrintInfosRequest();
		DeliveryProdPrintVo dp=new DeliveryProdPrintVo();
		request.setOrderId(2000001070521943l);
		request.setTenantId("changhong");
		request.setContactName("小志918");
		dp.setBuySum(4);
		dp.setExtendInfo("件");
		dp.setProdName("test1017");
		dp.setSalePrice(5000);
		dp.setSkuId("0000000000000286");
		List<Long> list=new ArrayList<Long>();
		list.add(2000001070626360l);
		dp.setHorOrderId(list);
		deliveryProdPrintVos.add(dp);
		request.setDeliveryProdPrintVos(deliveryProdPrintVos);
		BaseResponse response = deliveryOrderPrintSV.print(request);
		System.out.println(response);
	}
	
	@Test
	public void testDisplay() {
		DeliveryOrderPrintRequest request=new DeliveryOrderPrintRequest();
		request.setOrderId(2000001070521943l);
		request.setUserId("2ecee85451c3460a");
		request.setTenantId("changhong");
		DeliveryOrderPrintResponse response = deliveryOrderPrintSV.display(request);
		System.out.println(JSON.toJSON(response));
	}
	
	@Test
	public void testNoMergePrint() {
		DeliveryOrderPrintRequest request=new DeliveryOrderPrintRequest();
		request.setOrderId(2000001057276084l);
		request.setUserId("2ecee85451c3460a");
		request.setTenantId("changhong");
		System.out.println(JSON.toJSONString(request));
		DeliveryOrderPrintResponse response = deliveryOrderPrintSV.noMergePrint(request);
		System.out.println(JSON.toJSON(response));
	}

}
