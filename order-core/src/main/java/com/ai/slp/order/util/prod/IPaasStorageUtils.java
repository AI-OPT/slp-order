package com.ai.slp.order.util.prod;

import com.ai.opt.sdk.components.mcs.MCSClientFactory;
import com.ai.paas.ipaas.mcs.interfaces.ICacheClient;
import com.ai.slp.order.constants.prod.IPaasConstants;
import com.ai.slp.order.constants.prod.StorageConstants;

/**
 * ipass的工具类
 * Created by jackieliu on 16/5/25.
 */
public final class IPaasStorageUtils {
    private IPaasStorageUtils() {
    }

    /**
     * 缓存key分割符
     */
    private static final String CACHE_KEY_SEPARATE = ":";

    /**
     * 获取库存缓存客户端
     * @return
     */
    public static ICacheClient getClient(){
        return MCSClientFactory.getCacheClient(IPaasConstants.McsParams.STORAGE_MCS);
    }

    /**
     * 获取缓存中group的key(A)
     * @param tenantId
     * @param groupId
     * @return
     */
    public static String genMcsStorageGroupKey(String tenantId,String groupId){
        return StorageConstants.IPass.McsParams.GROUP_TAG+CACHE_KEY_SEPARATE
                +tenantId+CACHE_KEY_SEPARATE
                +groupId;
    }

    /**
     * 库存组优先级的价格KEY(B)
     * @param tenantId
     * @param groupId
     * @param priority
     * @return
     */
    public static String genMcsGroupSerialPriceKey(String tenantId,String groupId,String priority){
        return StorageConstants.IPass.McsParams.SALE_PRICE_TAG+CACHE_KEY_SEPARATE
                +tenantId+CACHE_KEY_SEPARATE
                +groupId+CACHE_KEY_SEPARATE
                +priority;
    }

    /**
     * 库存组优先级下SKU的可用量KEY(C)
     * @param tenantId
     * @param groupId
     * @param priority
     * @return
     */
    public static String genMcsSerialSkuUsableKey(String tenantId, String groupId, String priority){
        return StorageConstants.IPass.McsParams.SKU_USABLE_TAG +CACHE_KEY_SEPARATE
                +tenantId+CACHE_KEY_SEPARATE
                +groupId+CACHE_KEY_SEPARATE
                + priority;
    }

    /**
     * 促销优先级开始时间KEY(D)
     * @param tenantId
     * @param groupId
     * @return
     */
    public static String genMcsGroupSerialStartTimeKey(String tenantId,String groupId){
        return StorageConstants.IPass.McsParams.PROMOTION_STORAGE_TAG+CACHE_KEY_SEPARATE
                +tenantId+CACHE_KEY_SEPARATE
                +groupId;
    }

    /**
     * 库存组内优先级对应SKU库存的KEY(E)
     * @param tenantId
     * @param groupId
     * @param serial
     * @param skuId
     * @return
     */
    public static String genMcsSkuStorageUsableKey(
            String tenantId,String groupId,String serial,String skuId){
        return StorageConstants.IPass.McsParams.SKU_STORAGE_TAG+CACHE_KEY_SEPARATE
                +tenantId+CACHE_KEY_SEPARATE
                +groupId+CACHE_KEY_SEPARATE
                +serial+CACHE_KEY_SEPARATE
                +skuId;
    }

    /**
     * 库存组内优先级内库存可用量的KEY(F)
     * @param tenantId
     * @param groupId
     * @param priority
     * @return
     */
    public static String genMcsPriorityUsableKey(String tenantId,String groupId,String priority){
        return StorageConstants.IPass.McsParams.PRIORITY_USABLE_TAG+CACHE_KEY_SEPARATE
                +tenantId+CACHE_KEY_SEPARATE
                +groupId+CACHE_KEY_SEPARATE
                + priority;
    }
}
