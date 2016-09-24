package com.ai.slp.order.api.delivergoods.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.slp.order.api.delivergoods.interfaces.IDeliverGoodsPrintSV;
import com.ai.slp.order.api.delivergoods.param.DeliverGoodsPrintInfosRequest;
import com.ai.slp.order.api.delivergoods.param.DeliverGoodsPrintRequest;
import com.ai.slp.order.api.delivergoods.param.DeliverGoodsPrintResponse;
import com.ai.slp.order.service.business.interfaces.IDeliverGoodsPrintBusiSV;
import com.alibaba.dubbo.config.annotation.Service;

@Service(validation="true")
@Component
public class DeliverGoodsPrintSVImpl implements IDeliverGoodsPrintSV{
	
	@Autowired
	private IDeliverGoodsPrintBusiSV deliverGoodsPrintBusiSV;

	@Override
	public DeliverGoodsPrintResponse query(DeliverGoodsPrintRequest request) throws BusinessException, SystemException {
		DeliverGoodsPrintResponse response = deliverGoodsPrintBusiSV.deliverGoodsQuery(request);
		ResponseHeader header=new ResponseHeader(true, ExceptCodeConstants.Special.SUCCESS, "成功");
		response.setResponseHeader(header);
		return response;
	}

	@Override
	public BaseResponse print(DeliverGoodsPrintInfosRequest request) throws BusinessException, SystemException {
		BaseResponse response = new BaseResponse();
		deliverGoodsPrintBusiSV.deliverGoodsPrint(request);
		ResponseHeader header=new ResponseHeader(true, ExceptCodeConstants.Special.SUCCESS, "成功");
		response.setResponseHeader(header);
		return response;
	}

}
