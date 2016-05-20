package com.ai.slp.order.constants;

public final class OrdersConstants {

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
             * 购买话费卡包
             */
            public static final String BUG_PHONE_BILL_CARD = "100000";

            /**
             * 购买流量卡包
             */
            public static final String BUG_FLOWRATE_CARD = "100001";

            /**
             * 购买话费直充
             */
            public static final String BUG_PHONE_BILL_RECHARGE = "100010";

            /**
             * 购买流量直充
             */
            public static final String BUG_FLOWRATE_RECHARGE = "100011";

            /**
             * 购买实物类商品
             */
            public static final String BUG_MATERIAL_PROD = "110000";

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
            public static final String WAIT_AUDIT = "12";

            /**
             * 121 已充值
             */
            public static final String FINISH_AUDITED = "121";

            /**
             * 122 审核不通过
             */
            public static final String AUDIT_FAILED = "122";

            /**
             * 13 待配货 　 　
             */
            public static final String WAIT_DISTRIBUTION = "13";

            /**
             * 131 已配货
             */
            public static final String FINISH_DISTRIBUTION = "131";

            /**
             * 14 待出库
             */
            public static final String WAIT_DELIVERY = "14";

            /**
             * 141 已出库
             */
            public static final String FINISH_DELIVERY = "141";

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
             * 92撤销
             */
            public static final String REVOKE = "92";

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
             * 22待商家确认
             */
            public static final String REVOKE_WAIT_CONFIRM = "22";

            /**
             * 221已商家确认
             */
            public static final String REVOKE_FINISH_CONFIRMED = "221";

            /**
             * 23待退款
             */
            public static final String WAIT_REPAY = "23";

            /**
             * 231已退款
             */
            public static final String FINISH_REPAY = "231";

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

            /**
             * 已退货
             */
            public static final String FINISH_REFUND = "95";

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

        }

        public static class ProdType {
            /**
             * 1 商品
             */
            public static final String PROD = "1";

        }

    }

}
