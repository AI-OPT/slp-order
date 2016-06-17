package com.ai.slp.order.api.o2pcallback.interfaces;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
@Path("/o2pservice")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
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
     * @ApiCode O2PCALLBACK_001
     * @RestRelativeURL o2pservice/callback
     */
	@POST
	@Path("/callback")
    BaseResponse callBack(O2pCallBackRequest o2pCallBackRequest) throws BusinessException,
            SystemException;

}
