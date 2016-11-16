package com.ai.slp.order.api.ofc.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ai.opt.sdk.util.DateUtil;
import com.ai.opt.sdk.util.UUIDUtil;
import com.ai.slp.order.api.ofc.interfaces.IOfcSV;
import com.ai.slp.order.api.ofc.params.OrdOdFeeTotalVo;
import com.ai.slp.order.api.ofc.params.OrdOdLogisticsVo;
import com.ai.slp.order.api.ofc.params.OrdOrderOfcVo;
import com.ai.slp.order.api.ofc.params.OrderOfcVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/context/core-context.xml" })
public class OfcSVImplTest {

	@Autowired
	private IOfcSV ofcSV;

	@Test
	public void insertOrder() {
		OrdOrderOfcVo vo = new OrdOrderOfcVo();
		vo.setOrderId(277362);
		vo.setOrderTime(DateUtil.getSysDate());
		vo.setChlId("jingdong");
		vo.setExternalOrderId("36863153724");
		vo.setSubFlag("Y");
		vo.setIfWarning("Y");
		vo.setTenantId("changhong");
		vo.setFlag("0");
		vo.setBusiCode("1");
		// 订单类型,必传
		vo.setOrderType("110000");
		// 用户类型,必传
		vo.setUserType("11");
		// 用户Id,必传
		vo.setUserId("1");
		// 订单状态,必传
		vo.setState("1");
		// 订单变化时间
		vo.setStateChgTime(DateUtil.getSysDate());
		// 显示状态
		vo.setDisplayFlag("10");
		// 状态变更时间
		vo.setDisplayFlagChgTime(DateUtil.getSysDate());

		OrdOdFeeTotalVo ordOdFeeTotal = new OrdOdFeeTotalVo();
		// 订单Id
		ordOdFeeTotal.setOrderId(277362);
		// 租户Id
		ordOdFeeTotal.setTenantId("changhong");
		// 抵扣价格
		ordOdFeeTotal.setDiscountFee(0);
		// 订单支付金额
		ordOdFeeTotal.setPayFee(0);
		// 支付类型,需要解码
		ordOdFeeTotal.setPayStyle("11");
		// 收退费标识,必传
		ordOdFeeTotal.setPayFlag("in");
		// 总费用,抵扣金额+支付金额,必传
		ordOdFeeTotal.setTotalFee(100);
		// 总应收费用,总费用?,必传
		ordOdFeeTotal.setAdjustFee(50);
		// 总实收费用,支付金额?,必传
		ordOdFeeTotal.setPaidFee(100);
		// 待收金额,已完成订单,0,必传
		ordOdFeeTotal.setPayFee(0);
		// 变更时间,必传
		ordOdFeeTotal.setUpdateTime(DateUtil.getSysDate());

		OrdOdLogisticsVo ordOdLogistics = new OrdOdLogisticsVo();
		// 配送Id,必传
		ordOdLogistics.setLogisticsId(UUIDUtil.genShortId());
		// 配送类型,必传
		ordOdLogistics.setLogisticsType("0");
		// 租户Id
		ordOdLogistics.setTenantId("changhong");
		// 订单Id
		ordOdLogistics.setOrderId(277362);
		// 买家名称
		ordOdLogistics.setContactName("ww");
		// 买家电话
		ordOdLogistics.setContactTel("1313124234");
		// 详细地址
		ordOdLogistics.setAddress("舒服的暖色调");
		// 邮编
		ordOdLogistics.setPostcode("146");
		
		OrderOfcVo order = new OrderOfcVo();
		order.setOrOrderOfcVo(vo);
		order.setOrdOdFeeTotalVo(ordOdFeeTotal);
		order.setOrdOdLogisticsVo(ordOdLogistics);

		ofcSV.insertOrdOrder(order);
	}
}
