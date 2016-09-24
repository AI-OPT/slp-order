package com.ai.slp.order.api.delivergoods.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.slp.order.api.delivergoods.interfaces.IDeliverGoodsSV;
import com.ai.slp.order.api.delivergoods.param.DeliverGoodsRequest;
import com.ai.slp.order.service.business.interfaces.IDeliverGoodsBusiSV;
import com.alibaba.dubbo.config.annotation.Service;

@Service(validation="true")
@Component
public class DeliverGoodsSVImpl implements IDeliverGoodsSV {
	
	@Autowired
	private IDeliverGoodsBusiSV deliverGoodsBusiSV;
	
	@Override
	public BaseResponse deliverGoods(DeliverGoodsRequest request) throws BusinessException, SystemException {
		BaseResponse response=new BaseResponse();
		deliverGoodsBusiSV.deliverGoods(request);
		ResponseHeader responseHeader = new ResponseHeader(true,
	                ExceptCodeConstants.Special.SUCCESS, "成功");
	    response.setResponseHeader(responseHeader);
		return response;
	}
}
