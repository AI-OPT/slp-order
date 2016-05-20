package com.ai.slp.order.util;

import com.ai.opt.base.exception.BusinessException;
import org.apache.commons.lang.StringUtils;

/**
 * 通用检查类
 * Created by jackieliu on 16/5/2.
 */
public final class CommonCheckUtils {
    /**
     * 检查租户id是否为空
     */
    public static void checkTenantId(String tenantId,String errCode){
        if (StringUtils.isBlank(tenantId))
            throw new BusinessException(errCode,"租户标识不能为空");
    }
}
