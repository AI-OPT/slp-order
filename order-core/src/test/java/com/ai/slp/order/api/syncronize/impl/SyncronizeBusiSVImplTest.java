package com.ai.slp.order.api.syncronize.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ai.opt.sdk.util.DateUtil;
import com.ai.slp.order.api.synchronize.params.OrdBalanceIfVo;
import com.ai.slp.order.api.synchronize.params.OrdOdFeeTotalVo;
import com.ai.slp.order.api.synchronize.params.OrdOdInvoiceVo;
import com.ai.slp.order.api.synchronize.params.OrdOdLogisticVo;
import com.ai.slp.order.api.synchronize.params.OrdOdProdVo;
import com.ai.slp.order.api.synchronize.params.OrdOrderVo;
import com.ai.slp.order.api.synchronize.params.OrderSynchronizeVo;
import com.ai.slp.order.service.business.interfaces.ISyncronizeBusiSV;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/context/core-context.xml" })
public class SyncronizeBusiSVImplTest {

	@Autowired
	private ISyncronizeBusiSV sv;

	@Test
	public void syncronizeSV() {
		OrdOrderVo ordOrder = new OrdOrderVo();
		OrdOdProdVo ordOdProd = new OrdOdProdVo();
		OrdOdLogisticVo ordOdLogistics = new OrdOdLogisticVo();
		OrdOdInvoiceVo ordOdInvoice = new OrdOdInvoiceVo();
		OrdOdFeeTotalVo ordOdFeeTotal = new OrdOdFeeTotalVo();
		OrdBalanceIfVo ordBalacneIf = new OrdBalanceIfVo();

		// 订单表
		ordOrder.setOrderId(45657569);
		ordOrder.setChlId("jingdong");
		ordOrder.setIfWarning("Y");
		ordOrder.setBusiCode("1");
		ordOrder.setFlag("0");
		ordOrder.setOrderTime(DateUtil.getSysDate());
		ordOrder.setState("1");
		ordOrder.setUserId("2342443");
		ordOrder.setDeliveryFlag("Y");
		ordOrder.setOrderType("1");
		ordOrder.setAcctId(2343534);
		ordOrder.setSubsId(3455564);
		ordOrder.setUserType("1");
		ordOrder.setSupplierId("56546345");

		// 商品表
		ordOdProd.setOrderId(45657569);
		ordOdProd.setProdId("45635345");
		ordOdProd.setProdName("长虹电视");
		ordOdProd.setSkuId("3546457");
		ordOdProd.setProdCode("464565");
		ordOdProd.setBuySum(100);
		ordOdProd.setTotalFee(1000);
		ordOdProd.setDiscountFee(0);
		ordOdProd.setOperDiscountFee(0);
		ordOdProd.setAdjustFee(100);
		ordOdProd.setCusServiceFlag("N");

		// 物流表
		ordOdLogistics.setOrderId(45657569);
		// 配送类型,必传
		ordOdLogistics.setLogisticsType("0");
		// 买家名称
		ordOdLogistics.setContactName("ww");
		// 买家电话
		ordOdLogistics.setContactTel("1313124234");
		// 详细地址
		ordOdLogistics.setAddress("舒服的暖色调");
		// 邮编,6位
		ordOdLogistics.setPostcode("453454");
		
		//发票信息
		ordOdInvoice.setOrderId(45657569);
		ordOdInvoice.setInvoiceType("0");
		ordOdInvoice.setInvoiceKind("001");
		ordOdInvoice.setInvoiceStatus("1");
		ordOdInvoice.setInvoiceContent("nothings");
		ordOdInvoice.setInvoiceTitle("title");
		
		//费用
		ordOdFeeTotal.setOrderId(45657569);
		ordOdFeeTotal.setTotalFee(100);
		ordOdFeeTotal.setDiscountFee(100);
		ordOdFeeTotal.setPayStyle("1");
		ordOdFeeTotal.setOperDiscountFee(0);
		ordOdFeeTotal.setAdjustFee(1000);
		ordOdFeeTotal.setPaidFee(100);
		ordOdFeeTotal.setPayFee(100);
		
		//支付接口
		ordBalacneIf.setOrderId(45657569);
		ordBalacneIf.setPayStyle("0");
		ordBalacneIf.setPayFee(100);
		ordBalacneIf.setExternalId("65464");
		
		OrderSynchronizeVo vo = new OrderSynchronizeVo();
		vo.setOrdBalanceIfVo(ordBalacneIf);
		vo.setOrdOdFeeTotalVo(ordOdFeeTotal);
		vo.setOrdOdInvoiceVo(ordOdInvoice);
		vo.setOrdOdLogisticVo(ordOdLogistics);
		vo.setOrdOdProdVo(ordOdProd);
		vo.setOrdOrderVo(ordOrder);
		vo.setTenantId("changhong");
		sv.orderSynchronize(vo);
	}
}
