package com.ai.slp.order.constants;

import com.ai.slp.order.util.PropertiesLoader;

public final class OrdersConstants {
	
	/** 消息队列配置信息(已废弃)*/
	public static final String SLP_CHARGE_TOPIC = "slpChargeTopic";
	/** 订单租户id */
	public static final String TENANT_ID = "changhong";
	/** 定时任务配置信息*/
	public static final String DTS_SCHEDULE_NAME = "slp-order-dts-sched";
	/** 发票开具公司代码*/
	public static final String INVOICE_SUPPLIERID = "1300";
	/** 发票开具税率*/
	public static final String INVOICE_RATE = "0.06";
	/** 商品销售商id*/
	public static final String PROD_SUPPLIERID = "-1";

	/**
	 * 属性文件加载对象
	 */
	private static PropertiesLoader loader = new PropertiesLoader("remote_url.properties");
	/** 订单常量相关信息*/
	public static final class OrdOrder {
		/** 订单业务标识 */
		public static final class Flag {
			/**
			 * 0:OFC(定时)
			 */
			public static final String OFC_DTIME = "0";

			/**
			 * 1：up平台
			 */
			public static final String UPPLATFORM = "1";
			
			/**
			 * 1：积分平台(同步)
			 */
			public static final String JFSYNCH = "2";
			
			/**
			 * 3:OFC(实时)
			 */
			public static final String OFC_ACTUAL_TIME  = "3";

		}
		/** 订单用户类型 */
		public static final class UserType {
			/**
			 * 10:个人
			 */
			public static final String PERSONAL = "10";

			/**
			 * 11:企业
			 */
			public static final String ENTERPRISE = "11";
			/**
			 * 11:代理人
			 */
			public static final String PROXY = "12";

		}
		/** 订单物流信息*/
		public static final class DeliveryFlag {
			/**
			 * N:不需要物流
			 */
			public static final String NONE = "N";

			/**
			 * Y：需要物流
			 */
			public static final String EXPRESS = "Y";

		}
		/** 订单标识*/
		public static final class cusServiceFlag {
			/**
			 * N:否 (售后订单标识)
			 */
			public static final String NO = "N";

			/**
			 * Y：是
			 */
			public static final String YES = "Y";

		}
		/** 订单来源*/
		public static final class ChlId {

			/**
			 * 9001:天猫
			 */
			public static final String TMALL = "9001";

			/**
			 * 9002：京东
			 */
			public static final String JINGDONG = "9002";

			/**
			 * 9003：国美
			 */
			public static final String GOME = "9003";

			/**
			 * 9004：苏宁
			 */
			public static final String SUNING = "9004";

			/**
			 * 9005：一号店
			 */
			public static final String ONESTORE = "9005";

			/**
			 * 9006：自运营
			 */
			public static final String SELFOPERATION = "9006";
		}
		/** 订单类型*/
		public static class OrderType {

			/**
			 * 虚拟类商品
			 */
			public static final String VIRTUAL_PROD = "100000";
			
			/**
			 * 实物类商品
			 */
			public static final String BUG_MATERIAL_PROD = "110000";

		}
		/** 订单状态*/
		public static class State {
			/**
			 * 10 提交
			 */
			public static final String NEW = "10";

			/**
			 * 11 待支付
			 */
			public static final String WAIT_PAY = "11";

			/**
			 * 111 已支付
			 */
			public static final String FINISH_PAID = "111";

			/**
			 * 12 待充值
			 */
			public static final String WAIT_CHARGE = "12";

			/**
			 * 121 已充值
			 */
			public static final String FINISH_CHARGE = "121";

			/**
			 * 122 充值失败
			 */
			public static final String CHARGE_FAILED = "122";

			/**
			 * 123 充值未知
			 */
			public static final String CHARGE_UNKNOWN = "123";

			/**
			 * 13 待配货
			 */
			public static final String WAIT_DISTRIBUTION = "13";

			/**
			 * 130 提货单已打印
			 */
			public static final String LADING_BILL_FINISH_PRINT = "130";

			/**
			 * 131 已配货
			 */
			public static final String FINISH_DISTRIBUTION = "131";

			/**
			 * 14 待出库
			 */
			public static final String WAIT_DELIVERY = "14";

			/**
			 * 140 发货单已打印
			 */
			public static final String INVOICE_FINISH_PRINT = "140";

			/**
			 * 141 已出库
			 */
			public static final String FINISH_DELIVERY = "141";

			/**
			 * 15 待发货
			 */
			public static final String WAIT_SEND = "15";

			/**
			 * 151 已发货
			 */
			public static final String FINISH_SEND = "151";

			/**
			 * 16 待确认
			 */
			public static final String WAIT_CONFIRM = "16";

			/**
			 * 161 已确认
			 */
			public static final String FINISH_CONFIRMED = "161";

			/**
			 * 17 待写卡
			 */
			public static final String WAIT_WRITE_CARD = "17";

			/**
			 * 171 已写卡
			 */
			public static final String FINISH_WRITE_CARD = "171";

			/**
			 * 18 待激活
			 */
			public static final String WAIT_ACTIVATE = "18";

			/**
			 * 181 已激活
			 */
			public static final String ACTIVATED = "已激活";

			/**
			 * 182 未激活销户
			 */
			public static final String INACTIVED_CANCEL = "182";

			/**
			 * 90 完成
			 */
			public static final String COMPLETED = "90";

			/**
			 * 91取消
			 */
			public static final String CANCEL = "91";

			/**
			 * 92退货完成
			 */
			public static final String FINISH_REFUND = "92";

			/**
			 * 93换货完成
			 */
			public static final String REFUND_AUDIT = "93";

			/**
			 * 94退费完成
			 */
			public static final String EXCHANGE_AUDIT = "94";

			/**
			 * 退款失败
			 */
			public static final String REFUND_FAILD = "95";

			/**
			 * 20申请撤单
			 */
			public static final String APPLY_REVOKE = "20";

			/**
			 * 21待审核（撤单）
			 */
			public static final String REVOKE_WAIT_AUDIT = "21";

			/**
			 * 211已审核（撤单）
			 */
			public static final String REVOKE_FINISH_AUDITED = "211";

			/**
			 * 212 审核失败
			 */
			public static final String AUDIT_FAILURE = "212";

			/**
			 * 213 审核失败(第二次审核失败)
			 */
			public static final String AUDIT_AGAIN_FAILURE = "213";

			/**
			 * 22待商家确认/待买家退货
			 */
			public static final String REVOKE_WAIT_CONFIRM = "22";

			/**
			 * 221已商家确认/买家已退货
			 */
			public static final String REVOKE_FINISH_CONFIRMED = "221";

			/**
			 * 23 待卖家收货确认
			 */
			public static final String WAIT_RECEIPT_CONFIRMATION = "23";

			/**
			 * 231 卖家已收货确认
			 */
			public static final String RECEIPT_CONFIRMATION = "231";

			/**
			 * 31待退款
			 */
			public static final String WAIT_REPAY = "31";

			/**
			 * 311已退款
			 */
			public static final String FINISH_REPAY = "311";

			/**
			 * 312处理中
			 */
			public static final String IN_PROCESS = "312";

			/**
			 * 撤销完成
			 */
			public static final String FINSH_REVOKE = "25";

			/**
			 * 110 支付失败
			 */
			public static final String PAY_FAILURE = "110";

		}
		/** 子订单标识*/
		public static final class SubFlag {

			// 1 是
			public static final String YES = "Y";

			// 0 否
			public static final String NO = "N";

		}
		/** 订单业务类型*/
		public static class BusiCode {

			// 1：正常单
			public static final String NORMAL_ORDER = "1";

			// 2.换货单
			public static final String EXCHANGE_ORDER = "2";

			// 3.退货单
			public static final String UNSUBSCRIBE_ORDER = "3";

			// 4：退费单
			public static final String CANCEL_ORDER = "4";

		}
		/** 客户端显示状态*/
		public static class DisplayFlag {

			// 10 用户正常可见
			public static final String USER_NORMAL_VISIABLE = "10";

			// 11 用户临时删除
			public static final String USER_TEMP_DELETE = "11";

			// 12 用户永久删除
			public static final String USER_FOREVER_DELETE = "12";
		}

	}
	/** 订单商品常量相关信息*/
	public static final class OrdOdProd {
		/** 订单商品状态*/
		public static class State {
			
			/**
			 * prodId
			 */
			public static final String PRODID = "1";
			
			/**
			 * 1 销售
			 */
			public static final String SELL = "1";

			/**
			 * 2 退货
			 */
			public static final String RETURN = "2";

			/**
			 * 3 换货
			 */
			public static final String EXCHANGE = "3";

			/**
			 * 4 预售
			 */
			public static final String PRESALE = "4";

		}
		/** 销售品类型*/
		public static class ProdType {
			/**
			 * 1 商品
			 */
			public static final String PROD = "1";

		}
		public static class StandProdId {
			/**
			 *  标准品id
			 */
			public static final String STAND_PROD_ID = "1";
			
		}

	}
	/** 订单轨迹相关信息*/
	public static final class OrdOdStateChg {

		/**
		 * 处理信息 Date: 2016年5月20日 <br>
		 * Copyright (c) 2016 asiainfo.com <br>
		 * 
		 * @author zhangxw
		 */
		public static class ChgDesc {

			/**
			 * 订单提交之后进入支付环节
			 */
			public static final String ORDER_TO_PAY = "您提交的订单已经受理,现进入待支付处理";

			/**
			 * 订单支付完成
			 */
			public static final String ORDER_PAID = "您提交的订单已经支付完成";

			/**
			 * 订单待充值
			 */
			public static final String ORDER_TO_CHARGE = "您提交的订单进入待充值状态";

			/**
			 * 订单充值完成
			 */
			public static final String ORDER_CHARGED = "您提交的订单已经充值完成";

			/**
			 * 订单充值失败
			 */
			public static final String ORDER_CHARGE_FAILED = "您提交的订单充值失败";

			/**
			 * 订单充值未知
			 */
			public static final String ORDER_CHARGE_UNKNOWN = "您提交的订单充值未知";

			/**
			 * 订单进入待审核环节
			 */
			public static final String ORDER_TO_AUDIT = "您提交的订单进入待审核处理";

			/**
			 * 订单已审核
			 */
			public static final String ORDER_AUDITED = "您提交的订单已审核通过";

			/**
			 * 订单审核不通过
			 */
			public static final String ORDER_AUDIT_NOT_PASS = "您提交的订单审核未通过";

			/**
			 * 订单进入待配货
			 */
			public static final String ORDER_TO_WAIT_DISTRIBUTION = "您提交的订单进入待配货，请耐心等待";

			/**
			 * 提货单已经打印
			 */
			public static final String ORDER_TO_PRINT = "您提交的订单提货单已经打印";

			/**
			 * 订单已经配货
			 */
			public static final String ORDER_TO_FINISH_DISTRIBUTION = "您提交的订单配货完成，请耐心等待";

			/**
			 * 订单资料已经同步计费
			 */
			public static final String ORDER_TO_BILLING = "您提交的订单资料已经同步计费";

			/**
			 * 订单资料已经实例化
			 */
			public static final String ORDER_TO_SUBS = "您提交的订单资料已经处理完成";

			/**
			 * 订单业务已经开通
			 */
			public static final String ORDER_SERVICE_START = "您提交的订单已经开通所有服务";

			/**
			 * 发货单已经打印
			 */
			public static final String INVOICE_ORDER_TO_PRINT = "您提交的订单发货单已经打印";

			/**
			 * 订单进入待发货状态
			 */
			public static final String ORDER_TO_WAIT_SEND = "您提交的订单等待发货，请耐心等待";

			/**
			 * 订单进入待出库状态
			 */
			public static final String ORDER_TO_WAIT_DELIVERY = "您提交的订单等待出库，请耐心等待";

			/**
			 * 订单用户自提出库完成
			 */
			public static final String ORDER_TO_FINISH_SELF_DELIVERY = "您提交的订单已经完成自提出库，可以去营业厅自提";

			/**
			 * 订单物流出库完成
			 */
			public static final String ORDER_TO_FINISH_LOGISTICS_DELIVERY = "您提交的订单已经完成出库，物流派发中，请耐心等待收货";

			/**
			 * 订单 用户确认收货
			 */
			public static final String ORDER_TO_FINISH_CONFIRM = "您已经完成确认收货";

			/**
			 * 订单-完成
			 */
			public static final String ORDER_TO_COMPLETED = "订单处理完成";

			/**
			 * 订单－取消
			 */
			public static final String ORDER_TO_CANCEL = "您的订单已取消";

			/**
			 * 订单-审核通过
			 */
			public static final String ORDER_REVOKE_AUDIT = "您的撤销单申请已审核通过";

			/**
			 * 订单-审核未通过
			 */
			public static final String ORDER_REVOKE_AUDIT_NOT_PASS = "您的撤销单申请审核未通过";

			/**
			 * 订单--待买家退货
			 */
			public static final String ORDER_BUYERS_TO_RETURN = "您的订单商品进入待买家退货处理";

			/**
			 * 订单-待退费
			 */
			public static final String ORDER_SELLER_CONFIRMED_WAIT_PAY = "您的订单进入待退费处理";

			/**
			 * 订单-卖家已确认收货进入待退费
			 */
			public static final String ORDER_REVOKE_WAIT_PAY = "您的订单进入待退费处理";

			/**
			 * 订单已审核待退费
			 */
			public static final String ORDER_AUDITED_WAIT_REPAY = "您提交的订单已审核通过,进入待退费处理";

			/**
			 * 订单 - 退费
			 */
			public static final String ORDER_REVOKE_FINISH_PAY = "您的订单退费完成";

			/**
			 * 订单-撤销完成
			 */
			public static final String ORDER_REVOKE_FINISH = "您的订单撤销完成";

			/**
			 * 订单 - 退货完成
			 */
			public static final String FINISH_RETURN_GOODS = "您的订单退货完成";
		}

	}
	/** 订单费用总表常量相关信息*/
	public static final class OrdOdFeeTotal {
		/** 收退费表示*/
		public static class payFlag {
			/**
			 * 收入
			 */
			public static final String IN = "in";

			/**
			 * 支出
			 */
			public static final String OUT = "out";

		}
		/** 默认支付方式*/
		public static class PayStyle {

			/**
			 * 余额支付
			 */
			public static final String YE = "1";

			/**
			 * 支付宝
			 */
			public static final String ZFB = "20";

			/**
			 * 银联
			 */
			public static final String YL = "23";

			/**
			 * 微信支付
			 */
			public static final String WEIXIN = "28";

		}

	}
	/** 订单费用明细常量相关信息*/
	public static final class OrdOdFeeProd {
		/** 支付方式*/
		public static class PayStyle {

			/**
			 * 积分
			 */
			public static final String JF = "5";

			/**
			 * 优惠券
			 */
			public static final String COUPON = "8";
		}
	}
	/** 订单支付机构接口常量相关信息*/
	public static final class OrdBalacneIf {
		/** 支付系统id*/
		public static class paySystemId {
			/**
			 * 1 支付中心
			 */
			public static final String PAY_CENTER = "1";

		}

	}
	/** 订单商品明细扩展相关信息*/
	public static final class OrdOdProdExtend {
		/**批量标识*/
		public static class BatchFlag {
			/**
			 * 1 是
			 */
			public static final String YES = "Y";

			/**
			 * 0 否
			 */
			public static final String NO = "N";

		}

	}
	
	/** 订单提发货相关信息*/
	public static final class OrdOdDeliverInfo {
		/** 打印信息*/
		public static class printInfo {

			/**
			 * 1：提货单打印
			 */
			public static final String ONE = "1";

			/**
			 * 2：发货单打印
			 */
			public static final String TWO = "2";

		}

	}
	/** 订单发票相关信息*/
	public static final class ordOdInvoice {
		/** 发票状态*/
		public static class invoiceStatus {
			/** 1：发票未打印 */
			public static final String ONE = "1";
			/** 2：已报送 */
			public static final String TWO = "2";
			/** 已打印 */
			public static final String THREE = "3";
			/** 打印失败 */
			public static final String FOUR = "4";
		}
		/** 发票类型*/
		public static class invoiceType {
			/** 0：电子发票 */
			public static final String ZERO = "0";
			/** 1：纸质发票 */
			public static final String ONE = "1";
			/** 3：普票 (OFC)*/
			public static final String THREE = "3";
			/** 4：增票 (OFC)*/
			public static final String FOUR = "4";
		}
		/** 发票种类*/
		public static class invoiceKind {
			/** 001：增值税专用发票 */
			public static final String VAT_SPECIAL_INVOICE = "001";
			/** 002：增值税电子普通发票 */
			public static final String VAT_ELECTRONIC_ORDINARY_INVOICE = "002";
			/** 003: 增值税普通发票 */
			public static final String VAT_ORDINARY_INVOICE = "003";
			/** 004: 废旧物资发票 */
			public static final String WASTE_INVOICE = "004";
			/** 005:增值税电子专用发票 */
			public static final String VAT_ELECTRONIC_SPECIAL_INVOICE = "005";
		}
	}
	/** 订单预警信息*/
	public static final class IfWarning {

		public static class result {

			/**
			 * 预警标志
			 */
			public static final String WARING = "N";

			/**
			 * 预警标志
			 */
			public static final String NO_WARING = "Y";

		}

	}
	/** 订单打印标记信息*/
	public static final class printMark {

		/**
		 * 可合并
		 */
		public static final String CAN_MERGE = "1";

		/**
		 * 不可合并
		 */
		public static final String NOT_MERGE = "2";

		/**
		 * 不可打印
		 */
		public static final String NOT_PRINT = "3";
	}
	
	public static final class Sate {
		public static final String TENANT_ID = "changhong";
		/** 订单typeCode */
		public static final String TYPE_CODE = "ORD_ORDER";
		/** 订单状态paramCode */
		public static final String ORD_STATE = "STATE";
	}

	/** OFCAPPKEY **/
	private static final String OFC_APPKEY_KEY = "ofc.appkey";
	public static final String OFC_APPKEY = loader.getProperty(OFC_APPKEY_KEY);
	/** OFCSIGN **/
	private static final String OFC_SIGN_KEY = "ofc.sign";
	public static final String OFC_SIGN = loader.getProperty(OFC_SIGN_KEY);
	/** OFC查询URL **/
	private static final String OFC_QUERY_URL_KEY = "ofc.query.url";
	public static final String OFC_QUERY_URL = loader.getProperty(OFC_QUERY_URL_KEY);
	/** OFC销售订单创建URL **/
	private static final String OFC_ORDER_CREATE_URL_KEY = "ofc.order.create.url";
	public static final String OFC_ORDER_CREATE_URL = loader.getProperty(OFC_ORDER_CREATE_URL_KEY);
	/** OFC退换单创建URL **/
	private static final String OFC_RETURN_CREATE_URL_KEY = "ofc.return.create.url";
	public static final String OFC_RETURN_CREATE_URL = loader.getProperty(OFC_RETURN_CREATE_URL_KEY);
	/** 用户信息url */
	private static final String USER_URL_KEY = "user.query.url";
	public static final String USER_URL = loader.getProperty(USER_URL_KEY);
	/** OFC积分与人民币的兑换比例 */
	private static final String INTEGRAL_RATE_URL_KEY = "integral.rate.url";
	public static final String INTEGRAL_RATE_URL = loader.getProperty(INTEGRAL_RATE_URL_KEY);

	/**
	 * 退换货申请同步OFC 申请类型
	 */
	public static final class OFCApplyType {
		/** 退款类型 */
		public static final int REFUND = 1;
		/** 退货类型 */
		public static final int BACK = 3;
	}

	public static final class OFCDeliveryState {
		/** 已发货 */
		public static final String ALREADY_DELIVER_GOODS = "12";
		/** 已收货 */
		public static final String ALREADY_RECEIVE_GOODS = "13";
		/** 部分发货 */
		public static final String PART_DELIVER_GOODS = "18";
	}
	public static final class MDSNS {
		/** 订单系统的消息队列命名空间 */
		public static final String MDS_NS_ORDER_TOPIC = "MDS_NS_ORDER_TOPIC";
		/** 订单系统的使用消息队列的标识配置项名称 */
		public static final String CCS_MQ_FLAG = "/CCS_MQ_FLAG";
	}
}
