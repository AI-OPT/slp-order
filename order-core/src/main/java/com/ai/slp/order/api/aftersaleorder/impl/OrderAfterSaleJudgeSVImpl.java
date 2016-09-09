package com.ai.slp.order.api.aftersaleorder.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.slp.order.api.aftersaleorder.interfaces.IOrderAfterSaleJudgeSV;
import com.ai.slp.order.api.aftersaleorder.param.OrderJuageRequest;
import com.ai.slp.order.api.aftersaleorder.param.OrderJuageResponse;
import com.ai.slp.order.service.business.interfaces.IOrderAfterSaleJudgeBusiSV;
import com.alibaba.dubbo.config.annotation.Service;

@Service(validation = "true")
@Component
public class OrderAfterSaleJudgeSVImpl implements IOrderAfterSaleJudgeSV {
	
	@Autowired
	private IOrderAfterSaleJudgeBusiSV orderAfterSaleJudgeBusiSV;
	@Override
	public OrderJuageResponse judge(OrderJuageRequest request) throws BusinessException, SystemException {
		OrderJuageResponse response = orderAfterSaleJudgeBusiSV.judge(request);
        ResponseHeader responseHeader = new ResponseHeader(true,
                ExceptCodeConstants.Special.SUCCESS, "成功");
        response.setResponseHeader(responseHeader);
        return response;
	}

}
