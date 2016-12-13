package com.ai.slp.order.api.orderstate.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.opt.sdk.util.StringUtil;
import com.ai.slp.order.api.orderstate.interfaces.IOrderStateServiceSV;
import com.ai.slp.order.api.orderstate.param.WaitRebateRequest;
import com.ai.slp.order.api.orderstate.param.WaitRebateResponse;
import com.ai.slp.order.api.orderstate.param.WaitSellReceiveSureRequest;
import com.ai.slp.order.api.orderstate.param.WaitSellReceiveSureResponse;
import com.ai.slp.order.service.business.interfaces.IOrderStateBusiSV;
import com.alibaba.dubbo.config.annotation.Service;
@Service
@Component
public class OrderStateServiceSVImpl implements IOrderStateServiceSV {
	private static final Logger LOG = LoggerFactory.getLogger(OrderStateServiceSVImpl.class);

	@Autowired
	private IOrderStateBusiSV orderStateBusiSV;
	@Override
	public WaitSellReceiveSureResponse updateWaitSellRecieveSureState(WaitSellReceiveSureRequest request)
			throws BusinessException, SystemException {
		WaitSellReceiveSureResponse response = new WaitSellReceiveSureResponse();
		ResponseHeader responseHeader = new ResponseHeader();
		if(null == request){
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL,"请求参数不能为空");
		}
		if(StringUtil.isBlank(request.getTenantId())){
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL,"租户id不能为空");
		}
		if(null == request.getOrderId()){
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL,"租户id不能为空");
		}
		if(StringUtil.isBlank(request.getExpressId())){
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL,"快递Id不能为空");
		}
		if(StringUtil.isBlank(request.getExpressOddNumber())){
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL,"快递Number不能为空");
		}
		//
		try{
			response = this.orderStateBusiSV.updateWaitSellRecieveSureState(request);
			responseHeader.setIsSuccess(true);
			responseHeader.setResultCode("000000");
			responseHeader.setResultMessage("修改状态成功");
			//
			response.setResponseHeader(responseHeader);
		}catch(Exception e){
			//e.printStackTrace();
			responseHeader.setResultCode("999999");
			responseHeader.setResultMessage("修改状态失败");			
			//
			response.setResponseHeader(responseHeader);
			LOG.error("修改状态失败:"+e.getMessage(),e);
		}
		
		return response;
	}

	@Override
	public WaitRebateResponse updateWaitRebateState(WaitRebateRequest request)
			throws BusinessException, SystemException {
		WaitRebateResponse response = new WaitRebateResponse();
		ResponseHeader responseHeader = new ResponseHeader();
		if(null == request){
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL,"请求参数不能为空");
		}
		if(StringUtil.isBlank(request.getTenantId())){
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL,"租户id不能为空");
		}
		if(null == request.getOrderId()){
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL,"租户id不能为空");
		}
		try{
			response = this.orderStateBusiSV.updateWaitRebateState(request);
			responseHeader.setIsSuccess(true);
			responseHeader.setResultCode("000000");
			responseHeader.setResultMessage("修改状态成功");
			//
			response.setResponseHeader(responseHeader);
		}catch(Exception e){
			//e.printStackTrace();
			responseHeader.setResultCode("999999");
			responseHeader.setResultMessage("修改状态失败");
			//
			response.setResponseHeader(responseHeader);
		}
		//
		return response;
	}

}
