package com.ai.slp.order.api.ses;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ai.slp.order.api.orderlist.param.BehindQueryOrderListRequest;
import com.ai.slp.order.api.sesdata.interfaces.ISesDataRefreshSV;
import com.ai.slp.order.api.sesdata.param.SesDataByPageRequest;
import com.ai.slp.order.api.sesdata.param.SesDataRequest;
import com.ai.slp.order.api.sesdata.param.SesDataResponse;
import com.ai.slp.order.service.business.interfaces.search.IOrderIndexBusiSV;
import com.alibaba.fastjson.JSON;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/context/core-context.xml" })
public class SesDataRefreshSVImplTest {
	
	@Autowired
	private ISesDataRefreshSV sesDataRefreshSV;
	@Autowired
	private IOrderIndexBusiSV orderIndexBusiSV;
	
	@Test
	public void refreshSesData(){
		SesDataByPageRequest request=new SesDataByPageRequest();
		request.setPageNo(1);
		request.setPageSize(500);
		request.setTenantId("changhong");
		SesDataResponse response = sesDataRefreshSV.refreshSesData(request);
		System.out.println("同步完成");
		System.out.println(JSON.toJSON(response));
		
	}

	@Test
	public void orderSyncDataToSes() {
		SesDataRequest request=new SesDataRequest();
		
		request.setOrderId(409396l);
		orderIndexBusiSV.orderSynchDataToSes(request);
	}
	
	@Test
	public void deleteSesData() {
		BehindQueryOrderListRequest orderListRequest=new BehindQueryOrderListRequest();
		orderListRequest.setOrderTimeBegin("2017-04-27 18:15:29");
		orderIndexBusiSV.deleteSesData(orderListRequest);
	}
}
