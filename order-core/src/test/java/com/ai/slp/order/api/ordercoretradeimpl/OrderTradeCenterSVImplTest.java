package com.ai.slp.order.api.ordercoretradeimpl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ai.opt.base.vo.BaseResponse;
import com.ai.slp.order.api.ordertradecenter.interfaces.IOrderTradeCenterSV;
import com.ai.slp.order.api.ordertradecenter.param.OrdBaseInfo;
import com.ai.slp.order.api.ordertradecenter.param.OrdExtendInfo;
import com.ai.slp.order.api.ordertradecenter.param.OrdProductInfo;
import com.ai.slp.order.api.ordertradecenter.param.OrderApiTradeCenterRequest;
import com.ai.slp.order.api.ordertradecenter.param.OrderTradeCenterRequest;
import com.ai.slp.order.api.ordertradecenter.param.OrderTradeCenterResponse;
import com.ai.slp.order.vo.InfoJsonVo;
import com.ai.slp.order.vo.ProdExtendInfoVo;
import com.alibaba.fastjson.JSON;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/context/core-context.xml" })
public class OrderTradeCenterSVImplTest {

    @Autowired
    private IOrderTradeCenterSV orderTradeCenterSV;

    @Test
    public void orderTradeTest() {
        OrderTradeCenterRequest request = new OrderTradeCenterRequest();
        OrdBaseInfo ordBaseInfo = new OrdBaseInfo();
        ordBaseInfo.setUserId("123");
        ordBaseInfo.setOrderType("100010");

        List<OrdProductInfo> ordProductInfoList = new ArrayList<OrdProductInfo>();
        OrdProductInfo ordProductInfo = new OrdProductInfo();
        ordProductInfo.setBasicOrgId("10");
        ordProductInfo.setBuySum(4);
        ordProductInfo.setChargeFee("40");
        ordProductInfo.setSkuId("1000000000002401");
        ordProductInfoList.add(ordProductInfo);
        List<ProdExtendInfoVo> prodExtendInfoVoList = new ArrayList<ProdExtendInfoVo>();

        ProdExtendInfoVo prodExtendInfoVo = new ProdExtendInfoVo();
        prodExtendInfoVo.setProdExtendInfoValue("13069859685");
        ProdExtendInfoVo prodExtendInfoVo1 = new ProdExtendInfoVo();
        prodExtendInfoVo1.setProdExtendInfoValue("13969859856");
        prodExtendInfoVoList.add(prodExtendInfoVo);
        prodExtendInfoVoList.add(prodExtendInfoVo1);
        InfoJsonVo infoJsonVo = new InfoJsonVo();
        infoJsonVo.setProdExtendInfoVoList(prodExtendInfoVoList);
        OrdExtendInfo ordExtendInfo = new OrdExtendInfo();
        ordExtendInfo.setBatchFlag("1");
        ordExtendInfo.setInfoJson(JSON.toJSONString(infoJsonVo));

        request.setOrdBaseInfo(ordBaseInfo);
        request.setOrdExtendInfo(ordExtendInfo);
        request.setOrdProductInfoList(ordProductInfoList);
        request.setTenantId("SLP");
        OrderTradeCenterResponse apply = orderTradeCenterSV.apply(request);
    }

    @Test
    public void orderApiTradeTest() {
        OrderApiTradeCenterRequest request = new OrderApiTradeCenterRequest();
        request.setTenantId("SLP");
        request.setUserId("000000000000001457");
        request.setOrderType("100010");
        request.setBasicOrgId("10");
        request.setBuySum(4);
        request.setChargeFee("40");
        request.setSkuId("1000000000002516");
        request.setAcctId(11235);
        request.setUserType("12");
        request.setOrderTime("2016-06-03");
        request.setPayStyle("21");
        request.setSalePrice(2400);
        request.setDownstreamOrderId("sdfsfsd32432");
        List<ProdExtendInfoVo> prodExtendInfoVoList = new ArrayList<ProdExtendInfoVo>();
        ProdExtendInfoVo prodExtendInfoVo = new ProdExtendInfoVo();
        prodExtendInfoVo.setProdExtendInfoValue("13969859856");
        prodExtendInfoVoList.add(prodExtendInfoVo);
        InfoJsonVo infoJsonVo = new InfoJsonVo();
        infoJsonVo.setProdExtendInfoVoList(prodExtendInfoVoList);
        OrdExtendInfo ordExtendInfo = new OrdExtendInfo();
        ordExtendInfo.setInfoJson(JSON.toJSONString(infoJsonVo));

        request.setOrdExtendInfo(ordExtendInfo);
        request.setTenantId("SLP");
        BaseResponse apply = orderTradeCenterSV.apiApply(request);
    }
}
