package com.ai.slp.order.elasticjob;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.opt.sdk.util.DateUtil;
import com.ai.opt.sdk.util.StringUtil;
import com.ai.slp.order.service.business.interfaces.IOfcBusiSV;
import com.ai.slp.order.util.PropertiesUtil;
import com.ai.slp.order.vo.OfcCodeRequst;
import com.ai.slp.order.vo.OrdOdFeeTotalVo;
import com.ai.slp.order.vo.OrdOdLogisticsVo;
import com.ai.slp.order.vo.OrdOrderOfcVo;
import com.ai.slp.order.vo.OrderOfcVo;
import com.alibaba.fastjson.JSON;

public class OrderThread extends Thread {

	private static final Log LOG = LogFactory.getLog(OrderThread.class);

	private IOfcBusiSV ofcSV;

	private BlockingQueue<String[]> ordOrderQueue;

	public OrderThread(BlockingQueue<String[]> ordOrderQueue, IOfcBusiSV ofcSV) {
		this.ordOrderQueue = ordOrderQueue;
		this.ofcSV = ofcSV;
	}

	public void run() {
		while (true) {
			try {
				String[] queue = ordOrderQueue.poll(120, TimeUnit.SECONDS);
				if (null == queue) {
					LOG.error("+++++++++订单信息+++++++++线程OrderThread中断了");
					break;
				}
				synchronized (queue) {
					LOG.error("开始执行保存订单信息,时间" + DateUtil.getSysDate());
					OrderOfcVo order = setOrdererror(queue);
					ofcSV.insertOrdOrder(order);
					LOG.error("结束执行保存订单信息,时间" + DateUtil.getSysDate());
				}
			} catch (Exception e) {
				LOG.error("+++++++++++订单信息报错++++++++++++"+e.getMessage());
			}
		}

	}

	/**
	 * 封装导入订单信息
	 * 
	 * @param officeerror
	 * @return Office
	 */
	private OrderOfcVo setOrdererror(String[] orderData) {
		/**
		 * 订单主表
		 */
		LOG.error("++++++++++ofc开始设置订单信息数据,时间" + DateUtil.getSysDate());
		OrdOrderOfcVo record = new OrdOrderOfcVo();
		OfcCodeRequst requst = new OfcCodeRequst();
		requst.setTenantId(PropertiesUtil.getStringByKey("ofc.ordOrder.tenantId"));
		requst.setSystemId(PropertiesUtil.getStringByKey("ofc.ordOrder.systemId"));
		// 订单Id
		record.setOrderId(Long.valueOf(orderData[0]));
		// 下单时间
		record.setOrderTime(Timestamp.valueOf(orderData[1]));
		// 订单来源
		String chlId = "";
		if (!StringUtil.isBlank(orderData[3])) {
			requst.setOutCode(orderData[3]);
			requst.setParamCode(PropertiesUtil.getStringByKey("ofc.order.CHL_ID"));
			chlId = ofcSV.parseOfcCode(requst);
			if (chlId == null) {
				requst.setOutCode("其他平台");
				chlId = ofcSV.parseOfcCode(requst);
			}
		} else {
			requst.setOutCode(orderData[3]);
			requst.setParamCode(PropertiesUtil.getStringByKey("ofc.order.CHL_ID"));
			chlId = ofcSV.parseOfcCode(requst);
		}
		record.setChlId(chlId);
		// 外部订单编号->上游订单Id
		record.setExternalOrderId(orderData[4]);

		// 子订单标识/Y/N
		if ("是".equals(orderData[12])) {
			record.setSubFlag("N");
		} else if ("否".equals(orderData[12])) {
			record.setSubFlag("Y");
		}
		// 是否异常单->预警订单标识
		if ("是".equals(orderData[13])) {
			record.setIfWarning("Y");
		} else if ("否".equals(orderData[13])) {
			record.setIfWarning("N");
		}

		// 租户Id,必传
		record.setTenantId(PropertiesUtil.getStringByKey("ofc.ordOrder.tenantId"));
		// 业务标识,ofc是0
		record.setFlag(PropertiesUtil.getStringByKey("ofc.ordOrder.flag"));
		// 订单类型,必传
		String orderType="";
		if(!StringUtil.isBlank(orderData[27])){
			requst.setOutCode(orderData[27]);
			requst.setParamCode(PropertiesUtil.getStringByKey("ofc.order.ORDER_TYPE"));
			orderType = ofcSV.parseOfcCode(requst);
			if(StringUtil.isBlank(orderType)){
				requst.setOutCode("其它");
				orderType = ofcSV.parseOfcCode(requst);
			}
		}else{
			requst.setOutCode("其它");
			requst.setParamCode(PropertiesUtil.getStringByKey("ofc.order.ORDER_TYPE"));
			orderType = ofcSV.parseOfcCode(requst);
		}
		record.setOrderType(orderType);
		// 订单类型,必传
		record.setBusiCode(PropertiesUtil.getStringByKey("ofc.ordOrder.busiCode"));
		// 用户类型,必传
		record.setUserType(PropertiesUtil.getStringByKey("ofc.ordOrder.userType"));
		// 用户Id,必传
		record.setUserId(PropertiesUtil.getStringByKey("ofc.ordOrder.userId"));
		// 订单状态,必传
		record.setState(PropertiesUtil.getStringByKey("ofc.ordOrder.state"));
		// 订单变化时间
		record.setStateChgTime(DateUtil.getSysDate());
		// 显示状态
		record.setDisplayFlag(PropertiesUtil.getStringByKey("ofc.ordOrder.displayFlag"));
		// 状态变更时间
		if(!StringUtil.isBlank(orderData[29])){
		record.setDisplayFlagChgTime(DateUtil.getTimestamp(orderData[29]));
		}else{
			record.setDisplayFlagChgTime(DateUtil.getSysDate());
		}
		/**
		 * 订单-费用表
		 */
		OrdOdFeeTotalVo ordOdFeeTotal = new OrdOdFeeTotalVo();
		// 订单Id
		ordOdFeeTotal.setOrderId(Long.valueOf(orderData[0]));
		// 租户Id
		ordOdFeeTotal.setTenantId(PropertiesUtil.getStringByKey("ofc.ordOrder.tenantId"));
		// 支付类型,需要解码
		String payStyle;
		if (!StringUtil.isBlank(orderData[16])) {
			requst.setOutCode(orderData[16]);
			requst.setParamCode(PropertiesUtil.getStringByKey("ofc.order.PAY_STYLE"));
			payStyle = ofcSV.parseOfcCode(requst);
			if (payStyle == null) {
				requst.setOutCode("其它");
				payStyle = ofcSV.parseOfcCode(requst);
			}
		} else {
			requst.setOutCode("其它");
			requst.setParamCode(PropertiesUtil.getStringByKey("ofc.order.PAY_STYLE"));
			payStyle = ofcSV.parseOfcCode(requst);
		}
		ordOdFeeTotal.setPayStyle(payStyle);
		// 收退费标识,必传
		ordOdFeeTotal.setPayFlag(PropertiesUtil.getStringByKey("ofc.ordOdFeeTotal.payFlag"));
		// 总费用,必传
		if ((!StringUtil.isBlank(orderData[15]))) {
			ordOdFeeTotal.setAdjustFee(new BigDecimal(orderData[15]).longValue()*1000);
		}
		// 总实收费用,支付金额?,必传
		if (!StringUtil.isBlank(orderData[15])) {
			ordOdFeeTotal.setPaidFee(new BigDecimal(orderData[15]).longValue()*1000);
		}
		if (!StringUtil.isBlank(orderData[6])) {
			ordOdFeeTotal.setDiscountFee(new BigDecimal(orderData[6]).longValue()*1000);
		}
		// 待收金额,已完成订单,0,必传
		ordOdFeeTotal.setPayFee(0);
		
		ordOdFeeTotal.setOperDiscountFee(0);
		ordOdFeeTotal.setTotalFee(0);
		// 变更时间,必传
		if (!StringUtil.isBlank(orderData[29])) {
			ordOdFeeTotal.setUpdateTime(DateUtil.getTimestamp(orderData[29]));
		}else{
			ordOdFeeTotal.setUpdateTime(DateUtil.getSysDate());
		}

		/**
		 * 订单-出货表
		 */
		OrdOdLogisticsVo ordOdLogistics = new OrdOdLogisticsVo();
		// 配送类型,必传
		String logisticsType = "";
		if (!StringUtil.isBlank(orderData[17])) {
			requst.setOutCode(orderData[17]);
			requst.setParamCode(PropertiesUtil.getStringByKey("ofc.order.LOGISTICS_TYPE"));
			logisticsType = ofcSV.parseOfcCode(requst);
			if (logisticsType == null) {
				requst.setOutCode("其它");
				logisticsType = ofcSV.parseOfcCode(requst);
			}
		} else {
			requst.setOutCode("其它");
			requst.setParamCode(PropertiesUtil.getStringByKey("ofc.order.LOGISTICS_TYPE"));
			logisticsType = ofcSV.parseOfcCode(requst);
		}
		ordOdLogistics.setLogisticsType(logisticsType);

		// 租户Id
		ordOdLogistics.setTenantId(PropertiesUtil.getStringByKey("ofc.ordOrder.tenantId"));
		// 订单Id
		ordOdLogistics.setOrderId(Long.valueOf(orderData[0]));
		// 买家名称,长度最大16位
		if (!StringUtil.isBlank(orderData[20])) {
			if (orderData[20].length() <= 16) {
				ordOdLogistics.setContactName(orderData[20]);
			}
		}
		// 买家电话
		ordOdLogistics.setContactTel(orderData[21]);
		// 详细地址
		ordOdLogistics.setAddress(orderData[22]);
		// 邮编,6位哦
		if (orderData[25].length() > 0 && orderData[25].length() < 7) {
			ordOdLogistics.setPostcode(orderData[25]);
		}
		OrderOfcVo orderVo = new OrderOfcVo();
		orderVo.setOrOrderOfcVo(record);
		orderVo.setOrdOdFeeTotalVo(ordOdFeeTotal);
		orderVo.setOrdOdLogisticsVo(ordOdLogistics);
		LOG.error("结束设置订单信息,时间" + DateUtil.getSysDate());
		LOG.error(JSON.toJSONString(orderVo));
		return orderVo;
	}
}
