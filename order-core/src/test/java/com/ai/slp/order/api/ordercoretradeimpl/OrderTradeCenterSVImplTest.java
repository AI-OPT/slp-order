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
        ordBaseInfo.setUserId("112233");
        ordBaseInfo.setOrderType("110000");
        ordBaseInfo.setUserType("10");
        ordBaseInfo.setChlId("9006");
        ordBaseInfo.setDeliveryFlag("1");
        ordBaseInfo.setIpAddress("10.1.65.98");
        ordBaseInfo.setAcctId(0l);
        ordBaseInfo.setFlag("1");//业务标识 up平台
        List<OrdProductDetailInfo> ordProductDetailInfos=new ArrayList<OrdProductDetailInfo>();
        //详情1
        OrdProductDetailInfo detailInfo=new OrdProductDetailInfo();
        
        //商品信息1
        List<OrdProductInfo> ordProductInfoList = new ArrayList<OrdProductInfo>();
        OrdProductInfo ordProductInfo = new OrdProductInfo();
        ordProductInfo.setBuySum(1);
        ordProductInfo.setSkuId("0000000000000194");
        //ordProductInfo.setSupplierId(-1l);
        ordProductInfo.setGiveJF(100);
        ordProductInfoList.add(ordProductInfo);
        
  /*    OrdProductInfo ordProductInfo1 = new OrdProductInfo();
        ordProductInfo1.setBuySum(5);
        //ordProductInfo1.setSupplierId(-1l);
        ordProductInfo1.setSkuId("0000000000000194");  //两个商品同一销售商 不同仓库
        ordProductInfo.setGiveJF(10);
        ordProductInfoList.add(ordProductInfo1);*/
        
  /*     OrdProductInfo ordProductInfo2 = new OrdProductInfo();
        ordProductInfo2.setBuySum(2);
       // ordProductInfo2.setSupplierId(-1l);
        ordProductInfo2.setSkuId("0000000000000194");  //两个商品同一销售商 不同仓库
        ordProductInfo2.setGiveJF(3000);
        ordProductInfoList.add(ordProductInfo2);*/
        
        //发票信息1
        OrdInvoiceInfo ordInvoiceInfo=new OrdInvoiceInfo();
        ordInvoiceInfo.setInvoiceType("0");//0 电子发票;1.纸质发票
        ordInvoiceInfo.setInvoiceTitle("亚信科技(中国)公司");
        ordInvoiceInfo.setInvoiceContent("交通费923");
        ordInvoiceInfo.setBuyerBankAccount("622191231232321");
        ordInvoiceInfo.setBuyerBankCode("");
        ordInvoiceInfo.setBuyerBankName("中国工商银行");
        ordInvoiceInfo.setBuyerTaxpayerNumber("11111112222333");
        ordInvoiceInfo.setInvoiceKind("002");//001  增值税专用发票  002  增值税电子普通发票   003  增值税普通发票   004  废旧物资发票   005  增值税电子专用发票
        
        //订单费用明细信息1
        List<OrdFeeTotalProdInfo> ordFeeTotalProdInfo=new ArrayList<OrdFeeTotalProdInfo>();
        OrdFeeTotalProdInfo ordFeeTotalProdInfo1=new OrdFeeTotalProdInfo();
        ordFeeTotalProdInfo1.setPayStyle("23");
        ordFeeTotalProdInfo1.setPaidFee(23000);
        ordFeeTotalProdInfo1.setJfAmount(0l);
        OrdFeeTotalProdInfo ordFeeTotalProdInfo2=new OrdFeeTotalProdInfo();
        ordFeeTotalProdInfo2.setPayStyle("5");
        ordFeeTotalProdInfo2.setPaidFee(5000);
        ordFeeTotalProdInfo2.setJfAmount(5000);
        OrdFeeTotalProdInfo ordFeeTotalProdInfo3=new OrdFeeTotalProdInfo();
        ordFeeTotalProdInfo3.setPayStyle("8");
        ordFeeTotalProdInfo3.setPaidFee(1000);
        ordFeeTotalProdInfo.add(ordFeeTotalProdInfo1);
        ordFeeTotalProdInfo.add(ordFeeTotalProdInfo2);
        ordFeeTotalProdInfo.add(ordFeeTotalProdInfo3);
        
        detailInfo.setFreight(10000);
        detailInfo.setDiscountFee(500);
        detailInfo.setSupplierId("-1");
        detailInfo.setRemark("A买家留言信息信息信息信息信息信息");
        detailInfo.setOrdInvoiceInfo(ordInvoiceInfo);
        detailInfo.setOrdProductInfoList(ordProductInfoList);
        detailInfo.setOrdFeeTotalProdInfo(ordFeeTotalProdInfo);
        detailInfo.setAccountId("jfat1.201609256101549914_0001");
        ordProductDetailInfos.add(detailInfo);
        
        
        //商品信息2
       List<OrdProductInfo> ordProductInfoList1 = new ArrayList<OrdProductInfo>();
        OrdProductInfo ordProductInfo3 = new OrdProductInfo();
        ordProductInfo3.setBuySum(1);
        ordProductInfo3.setSkuId("0000000000000194");
        //ordProductInfo3.setSupplierId(-1l);
        ordProductInfo3.setGiveJF(20);
        ordProductInfoList1.add(ordProductInfo3);
        //发票信息2
        OrdInvoiceInfo ordInvoiceInfo1=new OrdInvoiceInfo();
        ordInvoiceInfo1.setInvoiceType("0");//0 电子发票;1.纸质发票
        ordInvoiceInfo1.setInvoiceTitle("亚信科技(中国)公司");
        ordInvoiceInfo1.setInvoiceContent("交通费919");
        ordInvoiceInfo1.setBuyerBankAccount("622191231232321");
        ordInvoiceInfo1.setBuyerBankCode("");
        ordInvoiceInfo1.setBuyerBankName("中国建设银行");
        ordInvoiceInfo1.setBuyerTaxpayerNumber("11111112222333");
        ordInvoiceInfo1.setInvoiceKind("002");//001  增值税专用发票  002  增值税电子普通发票   003  增值税普通发票   004  废旧物资发票   005  增值税电子专用发票
        
        //订单费用明细信息2
        List<OrdFeeTotalProdInfo> ordFeeTotalProdInfo5=new ArrayList<OrdFeeTotalProdInfo>();
        OrdFeeTotalProdInfo ordFeeTotalProdInfo4=new OrdFeeTotalProdInfo();
        ordFeeTotalProdInfo4.setPayStyle("23");
        ordFeeTotalProdInfo4.setPaidFee(23000);
        ordFeeTotalProdInfo4.setJfAmount(0);
        OrdFeeTotalProdInfo ordFeeTotalProdInfo6=new OrdFeeTotalProdInfo();
        ordFeeTotalProdInfo6.setPayStyle("5");
        ordFeeTotalProdInfo6.setPaidFee(3000);
        ordFeeTotalProdInfo6.setJfAmount(200);
        OrdFeeTotalProdInfo ordFeeTotalProdInfo7=new OrdFeeTotalProdInfo();
        ordFeeTotalProdInfo7.setPayStyle("8");
        ordFeeTotalProdInfo7.setPaidFee(1000);
        ordFeeTotalProdInfo5.add(ordFeeTotalProdInfo4);
        ordFeeTotalProdInfo5.add(ordFeeTotalProdInfo6);
        ordFeeTotalProdInfo5.add(ordFeeTotalProdInfo7);
        
        //详情2
        OrdProductDetailInfo detailInfo1=new OrdProductDetailInfo();
        detailInfo1.setFreight(60000);
        detailInfo1.setDiscountFee(300);
        detailInfo1.setSupplierId("-1");
        detailInfo1.setRemark("买家留言信息信息信息信息信息信息");
        detailInfo1.setOrdProductInfoList(ordProductInfoList1);
   //     detailInfo1.setOrdInvoiceInfo(ordInvoiceInfo1);
        detailInfo1.setOrdFeeTotalProdInfo(ordFeeTotalProdInfo5);
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
       // ordLogisticsInfo.setExpressId("1100011");
        
        request.setOrdBaseInfo(ordBaseInfo);
        request.setOrdExtendInfo(ordExtendInfo);
        request.setOrdProductDetailInfos(ordProductDetailInfos);
        request.setOrdLogisticsInfo(ordLogisticsInfo);
        
        request.setTenantId("changhong");
        System.out.println(JSON.toJSON(request));
        OrderTradeCenterResponse apply = orderTradeCenterSV.apply(request);
    }
}
