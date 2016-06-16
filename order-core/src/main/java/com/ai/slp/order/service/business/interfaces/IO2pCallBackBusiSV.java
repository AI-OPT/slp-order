package com.ai.slp.order.service.business.interfaces;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.slp.order.api.o2pcallback.param.O2pCallBackRequest;

/**
 * O2p回调业务逻辑处理 Date: 2016年6月16日 <br>
 * Copyright (c) 2016 asiainfo.com <br>
 * 
 * @author zhangxw
 */
public interface IO2pCallBackBusiSV {

    public void callBack(O2pCallBackRequest o2pCallBackRequest) throws BusinessException,
            SystemException;
}
