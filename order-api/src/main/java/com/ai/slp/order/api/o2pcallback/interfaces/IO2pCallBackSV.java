package com.ai.slp.order.api.o2pcallback.interfaces;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.slp.order.api.o2pcallback.param.O2pCallBackRequest;

/**
 * O2P充值成功后回调 Date: 2016年6月16日 <br>
 * Copyright (c) 2016 asiainfo.com <br>
 * 
 * @author zhangxw
 */
public interface IO2pCallBackSV {

    /**
     * O2P充值成功后回调方法
     * 
     * @param o2pCallBackRequest
     * @return
     * @throws BusinessException
     * @throws SystemException
     * @author zhangxw
     * @ApiDocMethod
     */
    BaseResponse callBack(O2pCallBackRequest o2pCallBackRequest) throws BusinessException,
            SystemException;

}
