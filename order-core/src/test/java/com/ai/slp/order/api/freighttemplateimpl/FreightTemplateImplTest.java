package com.ai.slp.order.api.freighttemplateimpl;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ai.opt.sdk.util.DateUtil;
import com.ai.opt.sdk.util.StringUtil;
import com.ai.slp.order.api.freighttemplate.interfaces.IFreightTemplateSV;
import com.ai.slp.order.api.freighttemplate.param.FreightTemplateDeleteRequest;
import com.ai.slp.order.api.freighttemplate.param.FreightTemplateInfo;
import com.ai.slp.order.api.freighttemplate.param.FreightTemplateProdInfo;
import com.ai.slp.order.api.freighttemplate.param.FreightTemplateProdRequest;
import com.ai.slp.order.api.freighttemplate.param.FreightTemplateRequest;
import com.ai.slp.order.api.freighttemplate.param.FreightTemplateUpdateRequest;
import com.ai.slp.order.api.freighttemplate.param.FreightTemplateVo;
import com.ai.slp.order.api.freighttemplate.param.QueryFreightTemplateRequest;
import com.ai.slp.order.api.freighttemplate.param.QueryFreightTemplateResponse;
import com.alibaba.fastjson.JSON;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/context/core-context.xml" })
public class FreightTemplateImplTest {
	
	@Autowired
	IFreightTemplateSV freightTemplateSV;

	@Test
	public void addTest() {
		FreightTemplateRequest request=new FreightTemplateRequest();
		FreightTemplateInfo info=new FreightTemplateInfo();
		info.setSupplierId("912");
		info.setIsFree("1");
		info.setLogisticsCompanyId("912");
		info.setTemplateName("长虹亚信运费模版912");
		info.setValuationType("10");
		
		List<FreightTemplateProdInfo> freightTemplateProdInfos=new ArrayList<FreightTemplateProdInfo>();
		FreightTemplateProdInfo prodInfo=new FreightTemplateProdInfo();
		prodInfo.setTransportAddress("北京9,天津1,河北2");
		prodInfo.setFirstNum(9l);
		prodInfo.setFirstNumber(10l);
		prodInfo.setPieceNumber(20l);
		prodInfo.setPieceNum(2l);
		
		FreightTemplateProdInfo prodInfo1=new FreightTemplateProdInfo();
		prodInfo1.setTransportAddress("江浙沪912,广州912");
		prodInfo1.setFirstNum(60l);
		prodInfo1.setFirstNumber(10l);
		prodInfo1.setPieceNumber(50l);
		prodInfo1.setPieceNum(20l);
		freightTemplateProdInfos.add(prodInfo);
		freightTemplateProdInfos.add(prodInfo1);
		request.setFreightTemplateInfo(info);
		request.setFreightTemplateProdInfos(freightTemplateProdInfos);
		String jsonStr = JSON.toJSONString(request);
		System.out.println(jsonStr);
		freightTemplateSV.add(request);
	}
	
	@Test
	public void query() {
		QueryFreightTemplateRequest request=new QueryFreightTemplateRequest();
		request.setSupplierId("1111");
		request.setPageNo(0);
		//request.setPageSize();
		String jsonStr = JSON.toJSONString(request);
		System.out.println(jsonStr);
		QueryFreightTemplateResponse query = freightTemplateSV.query(request);
		List<FreightTemplateVo> result = query.getPageInfo().getResult();
		for (FreightTemplateVo vo : result) {
			 System.out.println(vo.getTime());
		}
	}
	
	@Test
	public void update() {
		FreightTemplateUpdateRequest request=new FreightTemplateUpdateRequest();
		FreightTemplateInfo info=new FreightTemplateInfo();
		info.setSupplierId("22222211");
		info.setIsFree("1");
		info.setLogisticsCompanyId("3333333333333");
		info.setTemplateName("长虹亚信运费模版2222222");
		info.setValuationType("11");
		
		List<FreightTemplateProdInfo> freightTemplateProdInfos=new ArrayList<FreightTemplateProdInfo>();
		FreightTemplateProdInfo prodInfo=new FreightTemplateProdInfo();
		//prodInfo.setTransportAddress("北京3,河南3,天津3,哈尔滨3");
		prodInfo.setFirstNum(51l);
		prodInfo.setFirstNumber(11l);
		prodInfo.setPieceNumber(21l);
		prodInfo.setPieceNum(11l);
		prodInfo.setRegionId("1024");
		
		FreightTemplateProdInfo prodInfo1=new FreightTemplateProdInfo();
		prodInfo1.setTransportAddress("江浙沪3,广州3");
		prodInfo1.setFirstNum(663l);
		//prodInfo1.setFirstNumber(163l);
		prodInfo1.setPieceNumber(563l);
		//prodInfo1.setPieceNum(263l);
		prodInfo1.setRegionId("1025");
		freightTemplateProdInfos.add(prodInfo);
		freightTemplateProdInfos.add(prodInfo1);
		request.setFreightTemplateInfo(info);
		request.setFreightTemplateProdInfos(freightTemplateProdInfos);
		request.setTemplateId("2000000002759642");
		String jsonStr = JSON.toJSONString(request);
		System.out.println(jsonStr);
		freightTemplateSV.update(request);
	}
	
	@Test
	public void delete() {
		FreightTemplateDeleteRequest request=new FreightTemplateDeleteRequest();
		request.setTemplateId("2000000001881169");
		freightTemplateSV.delete(request);
	}
	
	@Test
	public void deleteFreightTemplateProd() {
		FreightTemplateProdRequest request=new FreightTemplateProdRequest();
		request.setRegionId("");
		freightTemplateSV.deleteFreightTemplateProd(request);
	}
	
}
