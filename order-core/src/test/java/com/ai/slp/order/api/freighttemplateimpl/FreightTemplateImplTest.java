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
import com.ai.slp.order.api.freighttemplate.param.FreightTemplateRequest;
import com.ai.slp.order.api.freighttemplate.param.FreightTemplateUpdateRequest;
import com.ai.slp.order.api.freighttemplate.param.FreightTemplateVo;
import com.ai.slp.order.api.freighttemplate.param.QueryFreightTemplateRequest;
import com.ai.slp.order.api.freighttemplate.param.QueryFreightTemplateResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/context/core-context.xml" })
public class FreightTemplateImplTest {
	
	@Autowired
	IFreightTemplateSV freightTemplateSV;

	@Test
	public void addTest() {
		FreightTemplateRequest request=new FreightTemplateRequest();
		FreightTemplateInfo info=new FreightTemplateInfo();
		info.setSupplierId("1111");
		info.setIsFree("1");
		info.setLogisticsCompanyId("333");
		info.setTemplateName("长虹亚信运费模版14");
		info.setValuationType("10");
		
		List<FreightTemplateProdInfo> freightTemplateProdInfos=new ArrayList<FreightTemplateProdInfo>();
		FreightTemplateProdInfo prodInfo=new FreightTemplateProdInfo();
		prodInfo.setTransportAddress("北京,天津,河北");
		prodInfo.setFirstNum(50l);
		prodInfo.setFirstNumber(10l);
		prodInfo.setPieceNumber(20l);
		prodInfo.setPieceNum(10l);
		
		FreightTemplateProdInfo prodInfo1=new FreightTemplateProdInfo();
		prodInfo1.setTransportAddress("江浙沪,广州");
		prodInfo1.setFirstNum(60l);
		prodInfo1.setFirstNumber(10l);
		prodInfo1.setPieceNumber(50l);
		prodInfo1.setPieceNum(20l);
		freightTemplateProdInfos.add(prodInfo);
		freightTemplateProdInfos.add(prodInfo1);
		request.setFreightTemplateInfo(info);
		request.setFreightTemplateProdInfos(freightTemplateProdInfos);
		freightTemplateSV.add(request);
	}
	
	@Test
	public void query() {
		QueryFreightTemplateRequest request=new QueryFreightTemplateRequest();
		request.setSupplierId("1111");
		request.setPageNo(1);
		request.setPageSize(3);
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
		info.setLogisticsCompanyId("33333311");
		info.setTemplateName("长虹亚信运费模版1111111");
		info.setValuationType("11");
		
		List<FreightTemplateProdInfo> freightTemplateProdInfos=new ArrayList<FreightTemplateProdInfo>();
		FreightTemplateProdInfo prodInfo=new FreightTemplateProdInfo();
		prodInfo.setTransportAddress("北京12222,河南,天津,哈尔滨");
		prodInfo.setFirstNum(51l);
		prodInfo.setFirstNumber(11l);
		prodInfo.setPieceNumber(21l);
		prodInfo.setPieceNum(11l);
		prodInfo.setRegionId("1002");
		
		FreightTemplateProdInfo prodInfo1=new FreightTemplateProdInfo();
		prodInfo1.setTransportAddress("江浙沪2,广州22222");
		prodInfo1.setFirstNum(66l);
		prodInfo1.setFirstNumber(16l);
		prodInfo1.setPieceNumber(56l);
		prodInfo1.setPieceNum(26l);
		prodInfo1.setRegionId("1003");
		freightTemplateProdInfos.add(prodInfo);
		freightTemplateProdInfos.add(prodInfo1);
		request.setFreightTemplateInfo(info);
		request.setFreightTemplateProdInfos(freightTemplateProdInfos);
		request.setTemplateId("2000000001625392");
		freightTemplateSV.update(request);
	}
	
	@Test
	public void delete() {
		FreightTemplateDeleteRequest request=new FreightTemplateDeleteRequest();
		//request.setTemplateId("");
		freightTemplateSV.delete(request);
	}
	
}
