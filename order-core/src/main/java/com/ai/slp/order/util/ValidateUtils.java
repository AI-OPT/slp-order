package com.ai.slp.order.util;

import java.util.List;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.util.StringUtil;
import com.ai.slp.order.api.aftersaleorder.param.OrderOFCBackRequest;
import com.ai.slp.order.api.aftersaleorder.param.OrderReturnRequest;
import com.ai.slp.order.api.delivergoods.param.DeliverGoodsRequest;
import com.ai.slp.order.api.deliveryorderprint.param.DeliveryOrderPrintRequest;
import com.ai.slp.order.api.invoiceprint.param.InvoiceModifyRequest;
import com.ai.slp.order.api.ofcactual.param.OfcOrderCreateRequest;
import com.ai.slp.order.api.ordercheck.param.OrderCheckRequest;
import com.ai.slp.order.api.orderlist.param.QueryOrderRequest;
import com.ai.slp.order.api.ordermodify.param.OrdRequest;
import com.ai.slp.order.api.orderpay.param.OrderOidRequest;
import com.ai.slp.order.api.orderpay.param.OrderPayRequest;
import com.ai.slp.order.api.orderpricemodify.param.OrderModifyRequest;
import com.ai.slp.order.api.orderrefund.param.OrderRefundRequest;
import com.ai.slp.order.api.orderrefund.param.OrderRefuseRefundRequest;
import com.ai.slp.order.api.orderstate.param.WaitSellReceiveSureRequest;
import com.ai.slp.order.api.ordertradecenter.param.OrdBaseInfo;
import com.ai.slp.order.api.ordertradecenter.param.OrdFeeTotalProdInfo;
import com.ai.slp.order.api.ordertradecenter.param.OrdInvoiceInfo;
import com.ai.slp.order.api.ordertradecenter.param.OrdLogisticsInfo;
import com.ai.slp.order.api.ordertradecenter.param.OrdProductDetailInfo;
import com.ai.slp.order.api.ordertradecenter.param.OrdProductInfo;
import com.ai.slp.order.api.ordertradecenter.param.OrderTradeCenterRequest;
import com.ai.slp.order.api.stasticsorder.param.StasticsOrderRequest;
import com.ai.slp.order.api.synchronize.params.OrdOdProdVo;
import com.ai.slp.order.api.synchronize.params.OrderSynchronizeVo;
import com.ai.slp.order.api.warmorder.param.OrderWarmDetailRequest;
import com.ai.slp.order.api.warmorder.param.OrderWarmRequest;
import com.ai.slp.order.constants.OrdersConstants;

public class ValidateUtils {
	private ValidateUtils() {
	}

	/**
	 * 预警订单查询参数校验
	 */
	public static void validateWarmOrdQuery(OrderWarmRequest condition) {
		if (condition == null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "参数对象不能为空");
		}
		if (StringUtil.isBlank(condition.getTenantId())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "租户ID不能为空");
		}
		if (condition.getPageNo() == null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "页码不能为空");
		}
		if (condition.getPageSize() == null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "页码大小不能为空");
		}
	}

	/**
	 * 订单状态修改参数校验
	 */
	public static void validateOrdUpdate(OrdRequest condition) {
		if (condition == null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "参数对象不能为空");
		}
		if (StringUtil.isBlank(condition.getTenantId())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "租户ID不能为空");
		}
		if (condition.getOrderId() == null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "订单ID不能为空");
		}
		if (StringUtil.isBlank(condition.getState())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "订单状态不能为空");
		}
	}

	/**
	 * 预警订单详情参数校验
	 */
	public static void validateWarmOrdDetail(OrderWarmDetailRequest condition) {
		if (condition == null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "参数对象不能为空");
		}
		if (StringUtil.isBlank(condition.getTenantId())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "租户ID不能为空");
		}
		if (condition.getOrderId() == null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "订单号不能为空");
		}
	}

	/**
	 * 统计查询参数校验
	 */
	public static void validateStasticOrdQuery(StasticsOrderRequest condition) {
		if (condition == null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "参数对象不能为空");
		}
		if (StringUtil.isBlank(condition.getTenantId())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "租户ID不能为空");
		}
		if (condition.getPageNo() == null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "页码不能为空");
		}
		if (condition.getPageSize() == null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "页码大小不能为空");
		}
	}

	/**
	 * 订单提交参数校验
	 */
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
		if (ordBaseInfo == null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "订单信息不能为空");
		}
		if (StringUtil.isBlank(ordBaseInfo.getUserId())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "用户id不能为空");
		}
		if (StringUtil.isBlank(ordBaseInfo.getUserName())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "用户名称不能为空");
		}
		if (StringUtil.isBlank(ordBaseInfo.getUserTel())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "用户手机号不能为空");
		}
		if (StringUtil.isBlank(ordBaseInfo.getUserType())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "用户类型不能为空");
		}
		if (StringUtil.isBlank(ordBaseInfo.getFlag())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "业务标识不能为空");
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
			if (!StringUtil.isBlank(ordProductDetailInfo.getAccountId())) {
				if (StringUtil.isBlank(ordProductDetailInfo.getTokenId())) {
					throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "积分账户id存在的情况下,积分令牌不能为空");
				}
				if (StringUtil.isBlank(ordProductDetailInfo.getPointRate())) {
					throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "积分账户id存在的情况下,积分比率不能为空");
				}

			}
			if (StringUtil.isBlank(ordProductDetailInfo.getSupplierId())) {
				throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "销售商id不能为空");
			}
			List<OrdProductInfo> ordProductInfoList = ordProductDetailInfo.getOrdProductInfoList();
			// 费用明细不为空时,校验支付方式是否存在
			List<OrdFeeTotalProdInfo> totalProdInfos = ordProductDetailInfo.getOrdFeeTotalProdInfo();
			if (!CollectionUtil.isEmpty(totalProdInfos)) {
				for (OrdFeeTotalProdInfo ordFeeTotalProdInfo : totalProdInfos) {
					if (StringUtil.isBlank(ordFeeTotalProdInfo.getPayStyle())) {
						throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "支付方式不能为空");
					}
				}
			}
			/** 判断商品是否允许发票 */
			OrdInvoiceInfo ordInvoiceInfo = ordProductDetailInfo.getOrdInvoiceInfo();
			/** 判断是否选择打印发票 */
			if (ordInvoiceInfo != null) {
				/** 发票参数校验 */
				ValidateUtils.validateOrdInvoice(ordInvoiceInfo,ordBaseInfo.getFlag());
			}
			if (CollectionUtil.isEmpty(ordProductInfoList)) {
				throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "商品信息列表不能为空");
			}
			for (OrdProductInfo ordProductInfo : ordProductInfoList) {
				if (StringUtil.isBlank(ordProductInfo.getSkuId())) {
					throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "单品id不能为空");
				}
				if (ordProductInfo.getBuySum() == 0) {
					throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "购买数量不能为空");
				}
				if (StringUtil.isBlank(ordProductInfo.getStandard())) {
					throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "商品规格不能为空");
				}
			}
		}
		if (logisticsInfo == null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "配送信息不能为空");
		}
		if (StringUtil.isBlank(logisticsInfo.getLogisticsType())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "配送方式不能为空");
		}
		if (StringUtil.isBlank(logisticsInfo.getContactName())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "收件人姓名不能为空");
		}
		if (StringUtil.isBlank(logisticsInfo.getContactTel())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "收件人电话不能为空");
		}
		if (StringUtil.isBlank(logisticsInfo.getProvinceCode())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "收件人省份不能为空");
		}
		if (StringUtil.isBlank(logisticsInfo.getCityCode())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "收件人地市不能为空");
		}
		if (StringUtil.isBlank(logisticsInfo.getCountyCode())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "收件人区县不能为空");
		}
		if (StringUtil.isBlank(logisticsInfo.getAddress())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "收件人详细地址不能为空");
		}
		if (StringUtil.isBlank(logisticsInfo.getPostCode())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "收件人邮编不能为空");
		}
	}

	/**
	 * 订单下单时,发票参数检验
	 */
	public static void validateOrdInvoice(OrdInvoiceInfo condition,String flag) {
		if (StringUtil.isBlank(condition.getInvoiceType())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "在打印发票的情况下,发票类型不能为空");
		}
		if (StringUtil.isBlank(condition.getInvoiceTitle())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "在打印发票的情况下,发票抬头不能为空");
		}
		if (StringUtil.isBlank(condition.getInvoiceContent())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "在打印发票的情况下,发票内容不能为空");
		}
		if (StringUtil.isBlank(condition.getInvoiceKind())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "在打印发票的情况下,发票种类不能为空");
		} else {
			if (OrdersConstants.ordOdInvoice.invoiceKind.VAT_SPECIAL_INVOICE.equals(condition.getInvoiceKind())
					|| OrdersConstants.ordOdInvoice.invoiceKind.VAT_ELECTRONIC_SPECIAL_INVOICE
							.equals(condition.getInvoiceKind())
					||OrdersConstants.ordOdInvoice.invoiceType.FOUR.equals(condition.getInvoiceType())) { //ofc增票存在的情况
				if (StringUtil.isBlank(condition.getBuyerTaxpayerNumber())) {
					throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "专用发票时,购货方纳税人识别号不能为空");
				}
				if (StringUtil.isBlank(condition.getBuyerBankName())) {
					throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "专用发票时,购货方开户行名称不能为空");
				}
				if (StringUtil.isBlank(condition.getBuyerBankAccount())) {
					throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "专用发票时,购货方开户行账号不能为空");
				}
			}
		}

		if (OrdersConstants.ordOdInvoice.invoiceType.ZERO.equals(condition.getInvoiceType())) {
			if (!(OrdersConstants.ordOdInvoice.invoiceKind.VAT_ELECTRONIC_ORDINARY_INVOICE
					.equals(condition.getInvoiceKind())
					|| OrdersConstants.ordOdInvoice.invoiceKind.VAT_ELECTRONIC_SPECIAL_INVOICE
							.equals(condition.getInvoiceKind()))) {
				throw new BusinessException("", "电子发票必须对应相应的发票种类");
			}
		} else if (OrdersConstants.ordOdInvoice.invoiceType.ONE.equals(condition.getInvoiceType())) {
			if (!(OrdersConstants.ordOdInvoice.invoiceKind.VAT_SPECIAL_INVOICE.equals(condition.getInvoiceKind())
					|| OrdersConstants.ordOdInvoice.invoiceKind.VAT_ORDINARY_INVOICE.equals(condition.getInvoiceKind())
					|| OrdersConstants.ordOdInvoice.invoiceKind.WASTE_INVOICE.equals(condition.getInvoiceKind()))) {
				throw new BusinessException("", "纸质发票必须对应相应的发票种类");
			}
		} else {
			if(OrdersConstants.OrdOrder.Flag.OFC_ACTUAL_TIME.equals(flag)) {
				if (!(OrdersConstants.ordOdInvoice.invoiceType.THREE.equals(condition.getInvoiceType())||
						OrdersConstants.ordOdInvoice.invoiceType.FOUR.equals(condition.getInvoiceType()))){
					throw new BusinessException("", "发票类型不符合要求");
				}
			}else {
				throw new BusinessException("", "发票类型不符合要求");
			}
		}
	}

	/**
	 * 积分中心回调参数校验
	 */
	public static void validateReturnOid(OrderOidRequest condition) {
		if (condition == null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "参数对象不能为空");
		}
		if (StringUtil.isBlank(condition.getTenantId())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "租户ID不能为空");
		}
		if (condition.getOrderId() == null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "订单号不能为空");
		}
		if (StringUtil.isBlank(condition.getOid())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "积分中心返回的oid不能为空");
		}
	}

	/**
	 * OFC售后订单状态通知参数校验
	 */
	public static void validateOFCBackRequest(OrderOFCBackRequest condition) {
		if (condition == null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "参数对象不能为空");
		}
		if (condition.getOrderId() == null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "订单号不能为空");
		}
		if (StringUtil.isBlank(condition.getExternalOrderId())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "外部申请单号不能为空");
		}
		if (StringUtil.isBlank(condition.getState())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "审核状态不能为空");
		}
	}

	/**
	 * 订单发票修改状态参数校验
	 */
	public static void validateModifyRequest(InvoiceModifyRequest condition) {
		if (condition == null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "参数对象不能为空");
		}
		if (StringUtil.isBlank(condition.getTenantId())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "租户ID不能为空");
		}
		if (condition.getOrderId() == null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "订单号不能为空");
		}
		String status = condition.getInvoiceStatus();
		if (StringUtil.isBlank(status)) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "发票打印状态不能为空");
		} else if (!OrdersConstants.ordOdInvoice.invoiceStatus.ONE.equals(status)) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "发票打印状态不属于未打印");
		}
	}

	/**
	 * 未支付订单金额修改参数校验
	 */
	public static void validateNotPaidModifyRequest(OrderModifyRequest condition) {
		if (condition == null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "参数不能为空");
		}
		if (StringUtil.isBlank(condition.getTenantId())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "租户ID不能为空");
		}
		if (condition.getOrderId() == null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "订单id不能为空");
		}
		if (StringUtil.isBlank(condition.getOperId())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "受理工号operId不能为空");
		}
		if (condition.getUpdateAmount() <= 0) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "修改金额必须大于0");
		}
	}

	/**
	 * 订单发货参数校验
	 */
	public static void validateDeliverGoodsRequest(DeliverGoodsRequest condition) {
		if (condition == null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "参数不能为空");
		}
		if (StringUtil.isBlank(condition.getTenantId())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "租户ID不能为空");
		}
		if (condition.getOrderId() == null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "订单id不能为空");
		}
		if (StringUtil.isBlank(condition.getOperId())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "受理工号operId不能为空");
		}
		if (StringUtil.isBlank(condition.getExpressId())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "物流公司id不能为空");
		}
		if (StringUtil.isBlank(condition.getExpressOddNumber())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "物流单号不能为空");
		}
	}

	/**
	 * 售后订单审核参数校验
	 */
	public static void validateOrderCheckRequest(OrderCheckRequest condition) {
		if (condition == null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "参数不能为空");
		}
		if (StringUtil.isBlank(condition.getTenantId())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "租户ID不能为空");
		}
		if (condition.getOrderId() == null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "订单id不能为空");
		}
		if (StringUtil.isBlank(condition.getOperId())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "审核工号operId不能为空");
		}
		if (StringUtil.isBlank(condition.getState())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "审核结果不能为空");
		}
	}

	/**
	 * 售后申请参数校验
	 */
	public static void validateOrderReturnRequest(OrderReturnRequest condition) {
		if (condition == null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "参数不能为空");
		}
		if (StringUtil.isBlank(condition.getTenantId())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "租户ID不能为空");
		}
		if (condition.getOrderId() == null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "订单id不能为空");
		}
		if (StringUtil.isBlank(condition.getOperId())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "审核工号operId不能为空");
		}
		if (condition.getProdDetalId() == null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "商品明细Id不能为空");
		}
		if (condition.getProdSum() <= 0) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "售后数量不能小于等于0");
		}
		String imageId = condition.getImageId();
		String afterSaleReason = condition.getAfterSaleReason();
		if (!StringUtil.isBlank(imageId)) {
			if (imageId.length() > 64) {
				throw new BusinessException("", "图片id位数不能超过64位");
			}
			if (StringUtil.isBlank(condition.getImageType())) {
				throw new BusinessException("", "图片id存在的情况下图片类型不能为空");
			}
		}
		if (!StringUtil.isBlank(afterSaleReason)) {
			if (afterSaleReason.length() > 200) {
				throw new BusinessException("", "退款理由不能超过200字");
			}
		}
	}

	/**
	 * 提货单打印参数校验
	 */
	public static void validateDeliveryOrderPrintRequest(DeliveryOrderPrintRequest condition) {
		if (condition == null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "参数不能为空");
		}
		if (StringUtil.isBlank(condition.getTenantId())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "租户ID不能为空");
		}
		if (condition.getOrderId() == null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "订单id不能为空");
		}
		if (StringUtil.isBlank(condition.getUserId())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "用户Id不能为空");
		}
	}

	/**
	 * 同意退款参数校验
	 */
	public static void validateOrderRefundRequest(OrderRefundRequest condition) {
		if (condition == null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "参数不能为空");
		}
		if (StringUtil.isBlank(condition.getTenantId())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "租户ID不能为空");
		}
		if (condition.getOrderId() == null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "订单id不能为空");
		}
		if (StringUtil.isBlank(condition.getOperId())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "受理工号不能为空");
		}
		if (condition.getUpdateMoney() == null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "修改金额不能为空");
		} else {
			if (condition.getUpdateMoney() < 0) {
				throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "修改金额不能小于0");
			}
		}
	}

	/**
	 * 拒绝退款参数校验
	 */
	public static void validateOrderRefuseRefundRequest(OrderRefuseRefundRequest condition) {
		if (condition == null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "参数不能为空");
		}
		if (StringUtil.isBlank(condition.getTenantId())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "租户ID不能为空");
		}
		if (condition.getOrderId() == null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "订单id不能为空");
		}
		if (StringUtil.isBlank(condition.getOperId())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "受理工号不能为空");
		}
		if (StringUtil.isBlank(condition.getRefuseReason())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "拒绝理由不能为空");
		}
	}

	/**
	 * 订单详情查询参数校验
	 */
	public static void validateQueryOrder(QueryOrderRequest condition) {
		if (condition == null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "参数不能为空");
		}
		if (StringUtil.isBlank(condition.getTenantId())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "租户ID不能为空");
		}
		if (condition.getOrderId() == null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "订单id不能为空");
		}
	}

	/**
	 * 订单同步查询参数校验
	 */
	public static void validateSynchronize(OrderSynchronizeVo vo) {
		if (StringUtil.isBlank(vo.getTenantId())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "租户Id不能为空");
		}
		if (vo.getOrderId() == 0) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "订单Id不能为空");
		}
		if (vo.getOrdOrderVo() != null) {
			if (StringUtil.isBlank(vo.getOrdOrderVo().getFlag())) {
				throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "参数不能为空");
			}
			if (StringUtil.isBlank(vo.getOrdOrderVo().getCusServiceFlag())) {
				throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "参数不能为空");
			}
			if (StringUtil.isBlank(vo.getOrdOrderVo().getSupplierId())) {
				throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "参数不能为空");
			}
			if (StringUtil.isBlank(vo.getOrdOrderVo().getChlId())) {
				throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "参数不能为空");
			}
			if (StringUtil.isBlank(vo.getOrdOrderVo().getDeliveryFlag())) {
				throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "参数不能为空");
			}
			if (StringUtil.isBlank(vo.getOrdOrderVo().getUserType())) {
				throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "参数不能为空");
			}
			if (StringUtil.isBlank(vo.getOrdOrderVo().getBusiCode())) {
				throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "参数不能为空");
			}
			if (StringUtil.isBlank(vo.getOrdOrderVo().getOrderType())) {
				throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "参数不能为空");
			}
			if (StringUtil.isBlank(vo.getOrdOrderVo().getUserId())) {
				throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "参数不能为空");
			}
			if (StringUtil.isBlank(vo.getOrdOrderVo().getState())) {
				throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "参数不能为空");
			}
			if (vo.getOrdOrderVo().getOrderTime() == null) {
				throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "参数不能为空");
			}
		} else {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "订单信息不能为空");
		}

		if (vo.getOrdOdProdList() != null && (!vo.getOrdOdProdList().isEmpty())) {
			for (OrdOdProdVo ordOdProdVo : vo.getOrdOdProdList()) {
				if (StringUtil.isBlank(ordOdProdVo.getProdCode())) {
					throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "参数不能为空");
				}
				if (StringUtil.isBlank(ordOdProdVo.getCusServiceFlag())) {
					throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "参数不能为空");
				}
				if (StringUtil.isBlank(ordOdProdVo.getProdName())) {
					throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "参数不能为空");
				}
				if (StringUtil.isBlank(ordOdProdVo.getSkuId())) {
					throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "参数不能为空");
				}
			}

		} else {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "订单商品信息不能为空");
		}

		if (vo.getOrdOdFeeTotalVo() != null) {
			if (StringUtil.isBlank(vo.getOrdOdFeeTotalVo().getPayStyle())) {
				throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "参数不能为空");
			}
		} else {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "订单费用信息不能为空");
		}

		if (vo.getOrdOdInvoiceVo() != null) {
			if (StringUtil.isBlank(vo.getOrdOdInvoiceVo().getInvoiceType())) {
				throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "参数不能为空");
			}
			if (StringUtil.isBlank(vo.getOrdOdInvoiceVo().getInvoiceKind())) {
				throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "参数不能为空");
			}
			if (StringUtil.isBlank(vo.getOrdOdInvoiceVo().getInvoiceContent())) {
				throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "参数不能为空");
			}
			if (StringUtil.isBlank(vo.getOrdOdInvoiceVo().getInvoiceTitle())) {
				throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "参数不能为空");
			}
			if (StringUtil.isBlank(vo.getOrdOdInvoiceVo().getInvoiceStatus())) {
				throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "参数不能为空");
			}
		} 

		if (vo.getOrdBalanceIfVo() != null) {
			if (StringUtil.isBlank(vo.getOrdBalanceIfVo().getPayStyle())) {
				throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "参数不能为空");
			}
			if (StringUtil.isBlank(vo.getOrdBalanceIfVo().getExternalId())) {
				throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "参数不能为空");
			}
		}

		if (vo.getOrdOdLogisticVo() != null) {
			if (StringUtil.isBlank(vo.getOrdOdLogisticVo().getLogisticsType())) {
				throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "参数不能为空");
			}
			if (StringUtil.isBlank(vo.getOrdOdLogisticVo().getContactName())) {
				throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "参数不能为空");
			}
			if (StringUtil.isBlank(vo.getOrdOdLogisticVo().getContactTel())) {
				throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "参数不能为空");
			}
			if (StringUtil.isBlank(vo.getOrdOdLogisticVo().getAddress())) {
				throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "参数不能为空");
			}
		} 
	}
	
	/**
	 * Ofc销售订单创建参数校验
	 */
	public static void validateOfcOrderCreateRequest(OfcOrderCreateRequest condition) {
		if (condition == null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "参数不能为空");
		}
		if (StringUtil.isBlank(condition.getTenantId())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "租户ID不能为空");
		}
		if (CollectionUtil.isEmpty(condition.getOrderIds())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "订单id不能为空");
		}
	}
	
	/**
	 * 买家填写物流参数校验
	 */
	public static void validateUpdateWaitSellState(WaitSellReceiveSureRequest condition) {
		if(null == condition){
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL,"请求参数不能为空");
		}
		if(StringUtil.isBlank(condition.getTenantId())){
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL,"租户id不能为空");
		}
		if(null == condition.getOrderId()){
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL,"租户id不能为空");
		}
		if(StringUtil.isBlank(condition.getExpressId())){
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL,"快递Id不能为空");
		}
		if(StringUtil.isBlank(condition.getExpressOddNumber())){
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL,"快递Number不能为空");
		}
	}
	
	/**
	 * 订单支付参数校验
	 */
	public static void validateOrderPay(OrderPayRequest condition) {
		if (condition == null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "参数对象不能为空");
		}
		if(CollectionUtil.isEmpty(condition.getOrderIds())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "待收费订单号为空");
		}
		if(condition.getPayFee()==null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "收费金额不能为空");
		}
		if(StringUtil.isBlank(condition.getExternalId())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "外部流水号不能为空");
		}
		if(StringUtil.isBlank(condition.getPayType())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "支付类型不能为空");
		}
		if (StringUtil.isBlank(condition.getTenantId())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "租户ID不能为空");
		}
	}
}
