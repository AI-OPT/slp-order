package com.ai.slp.order.util;

import java.util.List;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.util.StringUtil;
import com.ai.slp.order.api.ordermodify.param.OrdRequest;
import com.ai.slp.order.api.ordertradecenter.param.OrdBaseInfo;
import com.ai.slp.order.api.ordertradecenter.param.OrdInvoiceInfo;
import com.ai.slp.order.api.ordertradecenter.param.OrdLogisticsInfo;
import com.ai.slp.order.api.ordertradecenter.param.OrdProductDetailInfo;
import com.ai.slp.order.api.ordertradecenter.param.OrdProductInfo;
import com.ai.slp.order.api.ordertradecenter.param.OrderTradeCenterRequest;
import com.ai.slp.order.api.stasticsorder.param.StasticsOrderRequest;
import com.ai.slp.order.api.warmorder.param.OrderWarmDetailRequest;
import com.ai.slp.order.api.warmorder.param.OrderWarmRequest;
import com.ai.slp.order.constants.OrdersConstants;

public class ValidateUtils {
	private ValidateUtils() {
	}

	public static void validateWarmOrdQuery(OrderWarmRequest condition) {
		if (condition == null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "参数对象不能为空");
		}
		if (StringUtil.isBlank(condition.getTenantId())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "租户ID不能为空");
		}
		if (condition.getPageNo()==null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "页码不能为空");
		}
		if (condition.getPageSize()==null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "页码大小不能为空");
		}
	}
	public static void validateOrdUpdate(OrdRequest condition) {
		if (condition == null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "参数对象不能为空");
		}
		if (StringUtil.isBlank(condition.getTenantId())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "租户ID不能为空");
		}
		if (condition.getOrderId()==null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "订单ID不能为空");
		}
		if (StringUtil.isBlank(condition.getState())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "订单状态不能为空");
		}
	}
	public static void validateWarmOrdDetail(OrderWarmDetailRequest condition) {
		if (condition == null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "参数对象不能为空");
		}
		if (StringUtil.isBlank(condition.getTenantId())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "租户ID不能为空");
		}
		if (condition.getOrderId()==null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "订单号不能为空");
		}
	}
	public static void validateStasticOrdQuery(StasticsOrderRequest condition) {
		if (condition == null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "参数对象不能为空");
		}
		if (StringUtil.isBlank(condition.getTenantId())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "租户ID不能为空");
		}
		if (condition.getPageNo()==null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "页码不能为空");
		}
		if (condition.getPageSize()==null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "页码大小不能为空");
		}
	}
	
	public static void validateOrderTradeCenter(OrderTradeCenterRequest condition) {
		if (condition == null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "参数对象不能为空");
		}
		if (StringUtil.isBlank(condition.getTenantId())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "租户ID不能为空");
		}
		OrdBaseInfo ordBaseInfo = condition.getOrdBaseInfo();
		List<OrdProductDetailInfo> ordProductDetailInfos = condition.getOrdProductDetailInfos();
		OrdLogisticsInfo logisticsInfo = condition.getOrdLogisticsInfo();
		if (ordBaseInfo==null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "订单信息不能为空");
		}
		if (StringUtil.isBlank(ordBaseInfo.getUserId())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "用户id不能为空");
		}
		if (StringUtil.isBlank(ordBaseInfo.getIpAddress())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "用户ip不能为空");
		}
		if (StringUtil.isBlank(ordBaseInfo.getOrderType())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "订单类型不能为空");
		}
		if (StringUtil.isBlank(ordBaseInfo.getChlId())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "渠道id不能为空");
		}
		if (StringUtil.isBlank(ordBaseInfo.getDeliveryFlag())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "是否需要物流信息不能为空");
		}
		
		if (CollectionUtil.isEmpty(ordProductDetailInfos)) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "信息列表不能为空");
		}
		for (OrdProductDetailInfo ordProductDetailInfo : ordProductDetailInfos) {
			if (ordProductDetailInfo.getSupplierId()==null) {
				throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "销售商id不能为空");
			}
			List<OrdProductInfo> ordProductInfoList = ordProductDetailInfo.getOrdProductInfoList();
			if (CollectionUtil.isEmpty(ordProductInfoList)) {
				throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "商品信息列表不能为空");
			}
			for (OrdProductInfo ordProductInfo : ordProductInfoList) {
				if(StringUtil.isBlank(ordProductInfo.getSkuId())) {
					throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "单品id不能为空");
				}
				if(ordProductInfo.getSupplierId()==null) {
					throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "销售商id不能为空");
				}
				if(ordProductInfo.getBuySum()==0) {
					throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "购买数量不能为空");
				}
			}
		}
		if(logisticsInfo==null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "配送信息不能为空");
		}
		if(StringUtil.isBlank(logisticsInfo.getLogisticsType())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "配送方式不能为空");
		}
		if(StringUtil.isBlank(logisticsInfo.getContactName())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "收件人姓名不能为空");
		}
		if(StringUtil.isBlank(logisticsInfo.getContactTel())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "收件人电话不能为空");
		}
		if(StringUtil.isBlank(logisticsInfo.getProvinceCode())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "收件人省份不能为空");
		}
		if(StringUtil.isBlank(logisticsInfo.getCityCode())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "收件人地市不能为空");
		}
		if(StringUtil.isBlank(logisticsInfo.getCountyCode())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "收件人区县不能为空");
		}
		if(StringUtil.isBlank(logisticsInfo.getAddress())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "收件人详细地址不能为空");
		}
		if(StringUtil.isBlank(logisticsInfo.getPostCode())) {
    		throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "收件人邮编不能为空");
    	}
    	if(StringUtil.isBlank(logisticsInfo.getExpressId())) {
    		throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "物流公司ID不能为空");
    	}
	}
	
	/**
	 * 订单下单时,发票参数检验
	 */
	public static void validateOrdInvoice(OrdInvoiceInfo condition) {
		if(StringUtil.isBlank(condition.getInvoiceType())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "在打印发票的情况下,发票类型不能为空");
		}
		if(StringUtil.isBlank(condition.getInvoiceTitle())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "在打印发票的情况下,发票抬头不能为空");
		}
		if(StringUtil.isBlank(condition.getInvoiceContent())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "在打印发票的情况下,发票内容不能为空");
		}
		if(StringUtil.isBlank(condition.getInvoiceKind())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "在打印发票的情况下,发票种类不能为空");
		}else {
			if(OrdersConstants.ordOdInvoice.invoiceKind.VAT_SPECIAL_INVOICE.equals(condition.getInvoiceKind())||
					OrdersConstants.ordOdInvoice.invoiceKind.VAT_ELECTRONIC_SPECIAL_INVOICE.equals(condition.getInvoiceKind())) {
				if(StringUtil.isBlank(condition.getBuyerTaxpayerNumber())) {
					throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "专用发票时,购货方纳税人识别号不能为空");
				}
				if(StringUtil.isBlank(condition.getBuyerBankName())) {
					throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "专用发票时,购货方开户行名称不能为空");
				}
				if(StringUtil.isBlank(condition.getBuyerBankAccount())) {
					throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "专用发票时,购货方开户行账号不能为空");
				}
			}
		}
	}
}
