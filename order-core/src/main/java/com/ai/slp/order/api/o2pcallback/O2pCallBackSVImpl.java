package com.ai.slp.order.api.o2pcallback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.slp.order.api.o2pcallback.interfaces.IO2pCallBackSV;
import com.ai.slp.order.api.o2pcallback.param.O2pCallBackRequest;
import com.ai.slp.order.service.business.interfaces.IO2pCallBackBusiSV;
import com.alibaba.dubbo.config.annotation.Service;

@Service
@Component
public class O2pCallBackSVImpl implements IO2pCallBackSV {

    @Autowired
    private IO2pCallBackBusiSV o2pCallBackBusiSV;

    @Override
    public BaseResponse callBack(O2pCallBackRequest o2pCallBackRequest) throws BusinessException,
            SystemException {
        BaseResponse response = new BaseResponse();
        o2pCallBackBusiSV.callBack(o2pCallBackRequest);
        ResponseHeader responseHeader = new ResponseHeader(true,
                ExceptCodeConstants.Special.SUCCESS, "成功");
        response.setResponseHeader(responseHeader);
        return response;
    }

}
