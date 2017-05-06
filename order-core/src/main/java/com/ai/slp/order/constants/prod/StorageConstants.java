package com.ai.slp.order.constants.prod;

/**
 * 库存和库存组的常量
 *
 * Created by jackieliu on 16/4/29.
 */
public class StorageConstants {

    public static final class IPass{
        public static final class McsParams {
            /**
             * 库存所用mds
             */
            public static final String STORAGE_MCS = "com.ai.opt.slp.product.storage";
            /**
             * 库存组前缀
             */
            public static final String GROUP_TAG = "GROUP_TAG";
            /**
             * 库存优先级价格前缀
             */
            public static final String SALE_PRICE_TAG = "SALE_PRICE";
            /**
             * SKU优先级下总库存可用量
             */
            public static final String SKU_USABLE_TAG = "SKU_USABLE";
            /**
             * 促销库存
             */
            public static final String PROMOTION_STORAGE_TAG = "PROMOT_STO";
            /**
             * sku单品库存
             */
            public static final String SKU_STORAGE_TAG = "SKU_STO";
            /**
             * 优先级总库存可用量
             */
            public static final String PRIORITY_USABLE_TAG = "PRIORITY_USABLE";

            /**
             * HASH中库存组优先级的hashKey
             */
            public static final String GROUP_SERIAL_HTAGE = "GROUP_SERIAL";
            /**
             * HASH中库存组状态的hashKey
             */
            public static final String GROUP_STATE_HTAGE = "GROUP_STATE";
            /**
             * 缓存有效期延长时间,10分钟 单位:秒
             */
            public static final long CACHE_EXT_TIME = 10*60;

        }
    }

    public static final class StorageGroup{

        public static final String DEFAULT_NAME = "默认库存组";
        public static final class State{
            /**
             * 启用
             */
            public static final String ACTIVE = "1";
            /**
             * 自动启用
             */
            public static final String AUTO_ACTIVE = "11";
            /**
             * 停用
             */
            public static final String STOP = "2";
            /**
             * 自动停用
             */
            public static final String AUTO_STOP = "21";
            /**
             * 废弃
             */
            public static final String DISCARD = "3";
            /**
             * 自动废弃
             */
            public static final String AUTO_DISCARD = "31";
        }
        public static final class isSaleAttr{
            /**
             * 有销售属性
             */
            public static final String HAS_SALE_ATTR = "Y";
            /**
             * 无销售属性
             */
            public static final String NO_SALE_ATTR = "N";
        }
    }

    public static final class Storage{
        public static final class State{
            /**
             * 启用
             */
            public static final String ACTIVE = "1";
            /**
             * 自动启用
             */
            public static final String AUTO_ACTIVE = "11";
            /**
             * 停用
             */
            public static final String STOP = "2";
            /**
             * 自动停用
             */
            public static final String AUTO_STOP = "21";
            /**
             * 废弃
             */
            public static final String DISCARD = "3";
            /**
             * 自动废弃
             */
            public static final String AUTO_DISCARD = "31";
        }
    }

    public static final class SkuStorage{
        public static final class State{
            /**
             * 启用
             */
            public static final String ACTIVE = "1";
            /**
             * 自动停用
             */
            public static final String AUTO_STOP = "21";
            /**
             * 自动废弃
             */
            public static final String AUTO_DISCARD = "31";
        }
    }
}
