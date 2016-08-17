package com.ai.slp.order.api.ordercoretradeimpl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ai.slp.order.api.ordertradecenter.interfaces.IOrderTradeCenterSV;
import com.ai.slp.order.api.ordertradecenter.param.OrdBaseInfo;
import com.ai.slp.order.api.ordertradecenter.param.OrdExtendInfo;
import com.ai.slp.order.api.ordertradecenter.param.OrdProductInfo;
import com.ai.slp.order.api.ordertradecenter.param.OrderApiTradeCenterRequest;
import com.ai.slp.order.api.ordertradecenter.param.OrderApiTradeCenterResponse;
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
        ordBaseInfo.setUserType("10");

        List<OrdProductInfo> ordProductInfoList = new ArrayList<OrdProductInfo>();
        OrdProductInfo ordProductInfo = new OrdProductInfo();
        ordProductInfo.setBasicOrgId("10");
        ordProductInfo.setBuySum(4);
        ordProductInfo.setChargeFee("40");
        ordProductInfo.setSkuId("1000000000002519");
      //  ordProductInfo.setSupplierId(12l);
        ordProductInfoList.add(ordProductInfo);
        
        OrdProductInfo ordProductInfo1 = new OrdProductInfo();
        ordProductInfo1.setBasicOrgId("10");
        ordProductInfo1.setBuySum(5);
        ordProductInfo1.setChargeFee("50");
        ordProductInfo1.setSkuId("1000000000002459");
        ordProductInfoList.add(ordProductInfo1);
    //    ordProductInfo.setSupplierId(12l);
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
        request.setUserId("000000000000001203");
        request.setUserType("11");
        request.setOrderType("100010");
        request.setChargeFee("70MB");
        request.setSkuId("1000000000002455");
        request.setAcctId(11272);
        request.setLockTime("20160630143723");
        request.setPayStyle("1");
        request.setSalePrice(10);
        request.setDownstreamOrderId("7251537");
        request.setInfoJson("13552496249");
        request.setTenantId("SLP");
        request.setBuySum(1);
        OrderApiTradeCenterResponse apiApply = orderTradeCenterSV.apiApply(request);
        System.out.println(apiApply);
        System.out.println("11");
 
    }
}
