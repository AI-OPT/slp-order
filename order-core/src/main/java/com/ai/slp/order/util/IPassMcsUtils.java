package com.ai.slp.order.util;

import com.ai.slp.order.constants.ShopCartConstants;

/**
 * ipass中mcs工具
 * Created by jackieliu on 16/5/17.
 */
public final class IPassMcsUtils {
    public static String genShopCartUserId(String tenantId,String userId){
        return ShopCartConstants.CacheParams.CART_POINTS+":"+tenantId+":"+userId;
    }
}
