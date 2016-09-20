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
import com.ai.slp.order.api.ordertradecenter.param.OrdFeeTotalInfo;
import com.ai.slp.order.api.ordertradecenter.param.OrdFeeTotalProdInfo;
import com.ai.slp.order.api.ordertradecenter.param.OrdInvoiceInfo;
import com.ai.slp.order.api.ordertradecenter.param.OrdLogisticsInfo;
import com.ai.slp.order.api.ordertradecenter.param.OrdProductDetailInfo;
import com.ai.slp.order.api.ordertradecenter.param.OrdProductInfo;
import com.ai.slp.order.api.ordertradecenter.param.OrderApiTradeCenterRequest;
import com.ai.slp.order.api.ordertradecenter.param.OrderApiTradeCenterResponse;
import com.ai.slp.order.api.ordertradecenter.param.OrderTradeCenterRequest;
import com.ai.slp.order.api.ordertradecenter.param.OrderTradeCenterResponse;
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
        ordBaseInfo.setChlId("9006");
        ordBaseInfo.setDeliveryFlag("Y");
        
        List<OrdProductDetailInfo> ordProductDetailInfos=new ArrayList<OrdProductDetailInfo>();
        //详情1
        OrdProductDetailInfo detailInfo=new OrdProductDetailInfo();
        
        //商品信息1
        List<OrdProductInfo> ordProductInfoList = new ArrayList<OrdProductInfo>();
        OrdProductInfo ordProductInfo = new OrdProductInfo();
        ordProductInfo.setBasicOrgId("10");
        ordProductInfo.setBuySum(4);
        ordProductInfo.setChargeFee("40");
        ordProductInfo.setSkuId("0000000000000194");
        ordProductInfo.setSupplierId(12l);
        ordProductInfoList.add(ordProductInfo);
        
        OrdProductInfo ordProductInfo1 = new OrdProductInfo();
        ordProductInfo1.setBasicOrgId("10");
        ordProductInfo1.setBuySum(5);
        ordProductInfo1.setChargeFee("50");
        ordProductInfo1.setSkuId("1000000000002459");
        ordProductInfo1.setSupplierId(12l);
        ordProductInfoList.add(ordProductInfo1);
        
        //发票信息1
        OrdInvoiceInfo ordInvoiceInfo=new OrdInvoiceInfo();
        ordInvoiceInfo.setInvoiceType("1");
        ordInvoiceInfo.setInvoiceTitle("亚信科技(中国)公司");
        ordInvoiceInfo.setInvoiceContent("交通费918");
        
        //订单费用明细信息1
        List<OrdFeeTotalProdInfo> ordFeeTotalProdInfo=new ArrayList<OrdFeeTotalProdInfo>();
        OrdFeeTotalProdInfo ordFeeTotalProdInfo1=new OrdFeeTotalProdInfo();
        ordFeeTotalProdInfo1.setPayStyle("23");
        ordFeeTotalProdInfo1.setPaidFee(23000);
        OrdFeeTotalProdInfo ordFeeTotalProdInfo2=new OrdFeeTotalProdInfo();
        ordFeeTotalProdInfo2.setPayStyle("5");
        ordFeeTotalProdInfo2.setPaidFee(5000);
        ordFeeTotalProdInfo.add(ordFeeTotalProdInfo1);
        ordFeeTotalProdInfo.add(ordFeeTotalProdInfo2);
        
        detailInfo.setSupplierId(12l);
        detailInfo.setFreight(10000);
        detailInfo.setOrdInvoiceInfo(ordInvoiceInfo);
        detailInfo.setOrdProductInfoList(ordProductInfoList);
        detailInfo.setOrdFeeTotalProdInfo(ordFeeTotalProdInfo);
        ordProductDetailInfos.add(detailInfo);
        
        
        
        //商品信息2
        List<OrdProductInfo> ordProductInfoList1 = new ArrayList<OrdProductInfo>();
        OrdProductInfo ordProductInfo2 = new OrdProductInfo();
        ordProductInfo2.setBasicOrgId("10");
        ordProductInfo2.setBuySum(4);
        ordProductInfo2.setChargeFee("40");
        ordProductInfo2.setSkuId("0000000000000194");
        ordProductInfo2.setSupplierId(13l);
        ordProductInfoList1.add(ordProductInfo2);
        //发票信息2
        OrdInvoiceInfo ordInvoiceInfo1=new OrdInvoiceInfo();
        ordInvoiceInfo1.setInvoiceType("1");
        ordInvoiceInfo1.setInvoiceTitle("亚信科技(中国)公司");
        ordInvoiceInfo1.setInvoiceContent("交通费919");
        
        //订单费用明细信息2
        List<OrdFeeTotalProdInfo> ordFeeTotalProdInfo3=new ArrayList<OrdFeeTotalProdInfo>();
        OrdFeeTotalProdInfo ordFeeTotalProdInfo4=new OrdFeeTotalProdInfo();
        ordFeeTotalProdInfo4.setPayStyle("23");
        ordFeeTotalProdInfo4.setPaidFee(23000);
        ordFeeTotalProdInfo3.add(ordFeeTotalProdInfo4);
        
        //详情2
        OrdProductDetailInfo detailInfo1=new OrdProductDetailInfo();
        detailInfo1.setSupplierId(13l);
        detailInfo1.setFreight(60000);
        detailInfo1.setOrdProductInfoList(ordProductInfoList1);
        detailInfo1.setOrdInvoiceInfo(ordInvoiceInfo1);
        detailInfo1.setOrdFeeTotalProdInfo(ordFeeTotalProdInfo3);
        ordProductDetailInfos.add(detailInfo1);
        
        OrdExtendInfo ordExtendInfo = new OrdExtendInfo();
        ordExtendInfo.setBatchFlag("1");
        OrdFeeTotalInfo feeTotalInfo=new OrdFeeTotalInfo();
        feeTotalInfo.setTotalFee(230000l);
        feeTotalInfo.setAdjustFee(18000l);
        ordExtendInfo.setInfoJson(JSON.toJSONString(feeTotalInfo));
        
      
        //配送信息
        OrdLogisticsInfo ordLogisticsInfo=new OrdLogisticsInfo();
        ordLogisticsInfo.setLogisticsType("0");
        ordLogisticsInfo.setContactCompany("亚信918");
        ordLogisticsInfo.setContactName("小志918");
        ordLogisticsInfo.setContactTel("918");
        ordLogisticsInfo.setContactEmail("918@163.com");
        ordLogisticsInfo.setProvinceCode("75");
        ordLogisticsInfo.setCityCode("750");
        ordLogisticsInfo.setCountyCode("100839");
        ordLogisticsInfo.setPostCode("1");
        ordLogisticsInfo.setAreaCode("21");
        ordLogisticsInfo.setAddress("中关村软件园二期亚信大厦");
        ordLogisticsInfo.setExpressId("1100011");
        
        request.setOrdBaseInfo(ordBaseInfo);
        request.setOrdExtendInfo(ordExtendInfo);
        request.setOrdProductDetailInfos(ordProductDetailInfos);
        request.setOrdLogisticsInfo(ordLogisticsInfo);
        
        request.setTenantId("changhong");
        System.out.println(JSON.toJSON(request));
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
