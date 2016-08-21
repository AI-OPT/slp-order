package com.ai.slp.order.api.deliveryorder.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.slp.order.api.deliveryorderprint.interfaces.IDeliveryOrderPrintSV;
import com.ai.slp.order.api.deliveryorderprint.param.DeliveryOrderPrintRequest;
import com.ai.slp.order.api.deliveryorderprint.param.DeliveryOrderPrintResponse;
import com.ai.slp.order.api.deliveryorderprint.param.DeliveryOrderQueryResponse;
import com.ai.slp.order.service.business.interfaces.IDeliveryOrderNoMergePrintBusiSV;
import com.ai.slp.order.service.business.interfaces.IDeliveryOrderPrintBusiSV;
import com.alibaba.dubbo.config.annotation.Service;

@Service(validation="true")
@Component
public class DeliveryOrderPrintSVImpl implements IDeliveryOrderPrintSV {
	
	@Autowired
	private IDeliveryOrderPrintBusiSV deliveryOrderPrintBusiSV;
	
	@Autowired
	private IDeliveryOrderNoMergePrintBusiSV deliveryOrderNoMergePrintBusiSV;
	
	
	@Override
	public DeliveryOrderQueryResponse query(DeliveryOrderPrintRequest request) throws BusinessException, SystemException {
		DeliveryOrderQueryResponse response = deliveryOrderPrintBusiSV.query(request);
		ResponseHeader responseHeader = new ResponseHeader(true,
	                ExceptCodeConstants.Special.SUCCESS, "成功");
	    response.setResponseHeader(responseHeader);
		return response;
	}
	
	@Override
	public DeliveryOrderPrintResponse display(DeliveryOrderPrintRequest request) throws BusinessException, SystemException {
		DeliveryOrderPrintResponse response = deliveryOrderPrintBusiSV.display(request);
		ResponseHeader responseHeader = new ResponseHeader(true,
	                ExceptCodeConstants.Special.SUCCESS, "成功");
	    response.setResponseHeader(responseHeader);
		return response;
	}

	@Override
	public DeliveryOrderPrintResponse noMergePrint(DeliveryOrderPrintRequest request) throws BusinessException, SystemException {
		DeliveryOrderPrintResponse response = deliveryOrderNoMergePrintBusiSV.noMergePrint(request);
		ResponseHeader responseHeader = new ResponseHeader(true,
	                ExceptCodeConstants.Special.SUCCESS, "成功");
	    response.setResponseHeader(responseHeader);
		return response;
	}

	@Override
	public BaseResponse print(DeliveryOrderPrintRequest request)throws BusinessException, SystemException {
		BaseResponse response=new BaseResponse();
		deliveryOrderPrintBusiSV.print(request);
		ResponseHeader responseHeader = new ResponseHeader(true,
	                ExceptCodeConstants.Special.SUCCESS, "成功");
	    response.setResponseHeader(responseHeader);
		return response;
	}
}
