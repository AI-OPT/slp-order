package com.ai.slp.order.constants;

public final class OrdersConstants {

    public static final String SLP_CHARGE_TOPIC = "slpChargeTopic";

    public static final String ORDER_PHONENUM_DSS = "Order_PhoneNum";
    
    public static final String O2P_NOTIFYURL = "o2p_nofityurl";
    
    public static final String DTS_SCHEDULE_NAME = "slp-order-dts-sched";

    public static final class OrdOrder {


        public static final class DeliveryFlag {
            /**
             * 0:无
             */
            public static final String NONE = "0";

            /**
             * 1：快递
             */
            public static final String EXPRESS = "1";

            /**
             * 2：自提
             */
            public static final String SELF_PICKUP = "2";
        }

        public static class OrderType {

            /**
             * 话费流量卡包
             */
            public static final String BUG_PHONE_FLOWRATE_CARD = "100000";

            /**
             * 话费流量直充
             */
            public static final String BUG_PHONE_FLOWRATE_RECHARGE = "100010";

            /**
             * 购买实物类商品
             */
            public static final String BUG_MATERIAL_PROD = "110000";

            /**
             * 话费充值
             */
            public static final String PHONE_BILL_RECHARGE = "200000";

            /**
             * 流量充值
             */
            public static final String FLOWRATE_RECHARGE = "210000";

        }

        public static class State {
            /**
             * 101 提交
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
            public static final String WAIT_DISTRIBUTION="13";
            
            /**
             * 130 提货单已打印
             */
            public static final String LADING_BILL_FINISH_PRINT="130";
            
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
            public static final String INVOICE_FINISH_PRINT="140";

            /**
             * 141 已出库
             */
            public static final String FINISH_DELIVERY = "141";
            
            /**
             * 15 待发货
             */
            public static final String WAIT_SEND="15";
            
            /**
             * 151 已发货
             */
            public static final String FINISH_SEND="151";

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
             * 已退货
             */
            public static final String FINISH_REFUND = "92";

            /**
             * 93退货审核不通过
             */
            public static final String REFUND_AUDIT_NO_PASS = "93";

            /**
             * 94换货审核不通过
             */
            public static final String EXCHANGE_AUDIT_NO_PASS = "94";

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
            public static final String  AUDIT_FAILURE  = "212";

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
            public static final String WAIT_RECEIPT_CONFIRMATION ="23";

            /**
             * 231 卖家已收货确认
             */
            public static final String RECEIPT_CONFIRMATION ="231";

            /**
             * 31待退款
             */
            public static final String WAIT_REPAY = "31";

            /**
             * 311已退款
             */
            public static final String FINISH_REPAY = "311";

            /**
             * 撤销完成
             */
            public static final String FINSH_REVOKE = "25";

            /**
             * 80 订单等待自动执行
             */
            public static final String WAIT_AUTO_EXCUTE = "80";

            /**
             * 802 订单自动执行失败
             */
            public static final String AUTO_EXCUTED_FAILURE = "802";

            /**
             * 110 支付失败
             */
            public static final String PAY_FAILURE = "110";

            /**
             * 待激活
             */
            public static final String WAIT_ACTIVATION = "18";

            /**
             * 已激活
             */
            public static final String FINISH_ACTIVATION = "181";

            /**
             * 预开户
             */
            public static final String PRE_OPEN_STATE = "40";

            /**
             * 待生成制卡文件
             */
            public static final String WAIT_CARD_FILE = "36";

            /**
             * 待卡商制卡
             */
            public static final String WAIT_BUSINESS_CARD_FILE = "37";


        }

        public static final class SubFlag {

            // 1 是
            public static final String YES = "1";

            // 0 否
            public static final String NO = "0";

        }

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

        public static class DisplayFlag {

            // 10 用户正常可见
            public static final String USER_NORMAL_VISIABLE = "10";

            // 11 用户临时删除
            public static final String USER_TEMP_DELETE = "11";

            // 12 用户永久删除
            public static final String USER_FOREVER_DELETE = "12";
        }

    }

    public static final class OrdOdProd {

        public static class State {
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
            public static final String  PRESALE="4";

        }

        public static class ProdType {
            /**
             * 1 商品
             */
            public static final String PROD = "1";

        }

    }

    public static final class OrdOdStateChg {

        /**
         * 处理信息 Date: 2016年5月20日 <br>
         * Copyright (c) 2016 asiainfo.com <br>
         * 
         * @author zhangxw
         */
        public static class ChgDesc {
            /**
             * 订单提交
             */
            public static final String ORDER_CREATE = "您提交的订单已经受理,请耐心等候处理";

            /**
             * 订单进入支付环节
             */
            public static final String ORDER_TO_PAY = "您提交的订单进入待支付处理";

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
             * 订单待用户网厅收货确认
             */
            public static final String ORDER_TO_WAIT_CUST_NETHALL_CONFIRM = "您提交的订单需要您在网厅进行收货确认";

            /**
             * 订单待用户网厅激活
             */
            public static final String ORDER_TO_WAIT_WAIT_ACTIVATE = "等待激活";

            /**
             * 订单 用户网厅激活操作
             */
            public static final String ORDER_TO_AUTO_START_SV = "您已经完成激活";

            /**
             * 订单 用户确认收货
             */
            public static final String ORDER_TO_FINISH_CUST_NETHALL_CONFIRM = "您已经在网厅完成收货";

            /**
             * 订单待用户营业厅自提确认
             */
            public static final String ORDER_TO_WAIT_CUST_OFFICEHALL_CONFIRM = "您提交的订单需要您在营业厅进行自提确认";

            /**
             * 订单 客户营业厅自提确认完成
             */
            public static final String ORDER_TO_FINISH_CUST_OFFICEHALL_CONFIRM = "您已经在营业厅完成自提收货";

            /**
             * 订单 物流送达确认
             */
            public static final String ORDER_TO_FINISH_EXPRESS_CONFIRM = "您的订单已经由物流公司送达";

            /**
             * 订单-完成
             */
            public static final String ORDER_TO_COMPLETED = "订单处理完成";

            /**
             * 订单－取消
             */
            public static final String ORDER_TO_CANCEL = "您的订单已取消";

            /**
             * 订单-撤销
             */
            public static final String ORDER_TO_REVOKE = "您的撤销订单申请已经受理，请耐心等候处理";

            /**
             * 订单-撤销审核
             */
            public static final String ORDER_TO_REVOKE_AUDIT = "您的撤销申请进入待审核处理";

            /**
             * 订单-审核通过
             */
            public static final String ORDER_REVOKE_AUDIT = "您的撤销单申请已审核通过";

            /**
             * 订单-审核未通过
             */
            public static final String ORDER_REVOKE_AUDIT_NOT_PASS = "您的撤销单申请审核未通过";

            /**
             * 订单-待退费
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
             * 订单 - 待写卡
             */
            public static final String WAIT_WRITE_CARD = "您的订单待写卡处理";

            /**
             * 订单 - 退货完成
             */
            public static final String FINISH_RETURN_GOODS = "您的订单退货完成";

            /**
             * 订单不需要收费，后台自动执行订单
             */
            public static final String NO_PAY_AUTO_EXCUTING = "您的订单不需要收费，正在自动执行订单，请稍候";

            /**
             * 订单完成收费，后台自动执行订单
             */
            public static final String PAID_AUTO_EXCUTING = "您的订单完成收费，正在自动执行订单，请稍候";

            /**
             * 预开户订单提交完成,待客户资料返档
             */
            public static final String ORDER_TO_WAIT_ARCHIVE = "您的订单完成，正在等待客户资料返档";

            /**
             * 订单－预开户号码批量回收
             */
            public static final String PRE_ORDER_BATCH_CANCEL = "您的订单已经通过预开户号码批量回收取消";

            /**
             * 订单－用户逾期未激活强制销户
             */
            public static final String INACTIVE_BATCH_CANCEL = "用户逾期未激活强制销户";

            /**
             * 订单－用户欠费停机销户
             */
            public static final String OWE_FEE_BATCH_CANCEL = "用户欠费停机销户";

            /**
             * 订单-制卡文件生成
             */
            public static final String WAIT_CREATE_CARD_FILE = "您的订单已审核通过，进入制卡文件生成环节";

            /**
             * 订单-卡商制卡
             */
            public static final String WAIT_BUSINESS_CARD_FILE = "您的订单制卡文件已生成，进入卡商制卡环节";

        }

    }

    public static final class OrdOdFeeTotal {

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

        public static class PayStyle {
            
            /**
             * 余额支付
             */
            public static final String YE = "1";


            /**
             * 支付宝
             */
            public static final String ZFB = "21";

            /**
             * 银联
             */
            public static final String YL = "22";

            /**
             * 微信支付
             */
            public static final String WEIXIN = "23";

        }

    }

    public static final class OrdBalacneIf {

        public static class paySystemId {
            /**
             * 1 支付中心
             */
            public static final String PAY_CENTER = "1";

        }

    }

    public static final class OrdOdProdExtend {

        public static class BatchFlag {
            /**
             * 1 是
             */
            public static final String YES = "1";

            /**
             * 0 否
             */
            public static final String NO = "0";

        }

    }
    
    
    public static final class OrdOdDeliverInfo {

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
    
    public static final class OrderCheck {

        public static class result {
        	
            /**
             * 审核同意
             */
            public static final String OK = "1";

            /**
             * 审核拒绝
             */
            public static final String NO = "2";

        }

    }

}
