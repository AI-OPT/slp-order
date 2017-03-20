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
		//request.setOrderId(2000034324143088l);
		request.setOrderId(2000034324272696l);
		request.setUserId("112233");
		request.setTenantId("changhong");
		DeliveryOrderQueryResponse response = deliveryOrderPrintSV.query(request);
		System.out.println(response);
	}
	
	
	@Test
	public void testPrint() {
		
		List<DeliveryProdPrintVo> deliveryProdPrintVos=new ArrayList<DeliveryProdPrintVo>();
		DeliveryOrderPrintInfosRequest request=new DeliveryOrderPrintInfosRequest();
		DeliveryProdPrintVo dp=new DeliveryProdPrintVo();
		DeliveryProdPrintVo dp1=new DeliveryProdPrintVo();
		request.setOrderId(	2000034324817339l);
		request.setTenantId("changhong");
		request.setContactName("小志918");
		dp.setBuySum(1);
		dp.setExtendInfo("件");
		dp.setProdName("test1018");
		dp.setSalePrice(50000);
		dp.setSkuId("0000000000000287");
		dp1.setBuySum(2);
		dp1.setExtendInfo("件");
		dp1.setProdName("test1017");
		dp1.setSalePrice(5000);
		dp1.setSkuId("0000000000000286");
		List<Long> list1=new ArrayList<Long>();
	//	List<Long> list2=new ArrayList<Long>();
		list1.add(2000034323751477l);
		dp1.setHorOrderId(list1);
	//	dp1.setHorOrderId(list2);
	//	deliveryProdPrintVos.add(dp);
		deliveryProdPrintVos.add(dp1);
		request.setDeliveryProdPrintVos(deliveryProdPrintVos);
		BaseResponse response = deliveryOrderPrintSV.print(request);
		System.out.println(response);
	}
	
	@Test
	public void testDisplay() {
		DeliveryOrderPrintRequest request=new DeliveryOrderPrintRequest();
		request.setOrderId(2000034324272696l);
		request.setUserId("112233");
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
