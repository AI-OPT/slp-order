package com.ai.slp.order.constants;

/**
 * 购物车
 * Created by jackieliu on 16/5/17.
 */
public final class ShopCartConstants {

    public final class McsParams {
        /**
         * 购物车所用MCS的标识
         */
        public static final String SHOP_CART_MCS = "com.ai.opt.slp.order.shopcart";
        /**
         * 缓存中购物车用户前缀
         */
        public static final String USER_PREFIX = "SHOP_CART";
        /**
         * 购物车概览Hash的KEY
         */
        public static final String CART_POINTS = "CART_POINTS";
    }

    public final class MdsParams{
        /**
         * 购物车消息中心topic
         */
        public static final String SHOP_CART_TOPIC = "slpShopCartTopic";
    }

    public final class CcsParams{
        public final class ShopCart{
            /**
             * 单个商品数量限制
             */
            public static final String SKU_NUM_LIMIT = "/shop_cart_sku_num_limit";

            /**
             * 商品类型数量限制
             */
            public static final String PROD_NUM_LIMIT = "/shop_cart_prod_num_limit";
        }
    }
}
