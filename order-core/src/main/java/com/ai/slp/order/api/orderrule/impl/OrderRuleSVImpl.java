package com.ai.slp.order.api.orderrule.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.opt.sdk.util.StringUtil;
import com.ai.slp.order.api.orderrule.interfaces.IOrderRuleSV;
import com.ai.slp.order.api.orderrule.param.OrderRuleDetailResponse;
import com.ai.slp.order.api.orderrule.param.OrderRuleRequest;
import com.ai.slp.order.api.orderrule.param.OrderRuleResponse;
import com.ai.slp.order.service.business.interfaces.IOrdRuleBusiSV;
import com.alibaba.dubbo.config.annotation.Service;
/**
 * 规则设置
 *
 * Date: 2016年8月3日 <br>
 * Copyright (c) 2016 asiainfo.com <br>
 * @author zhangzd
 */
@Service
@Component
public class OrderRuleSVImpl implements IOrderRuleSV {
	@Autowired
	private IOrdRuleBusiSV ordRuleBusiSV;
	/**
	 * 规则设置
	 */
	@Override
	public OrderRuleResponse orderRuleSetting(OrderRuleRequest request) throws BusinessException, SystemException {
		//
		OrderRuleResponse response = new OrderRuleResponse();
		ResponseHeader responseHeader = new ResponseHeader();
		//
		if(null == request){
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL,"请求参数不能为空");
			
		}
		if(StringUtil.isBlank(request.getTimeMonitorId())){
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL,"时间监控规则编号不能为空");
		}
		if(StringUtil.isBlank(request.getBuyEmployeeMonitorId())){
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL,"购买人员监控规则编号不能为空");
		}
		if(StringUtil.isBlank(request.getBuyIpMonitorId())){
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL,"购买ip监控规则编号不能为空");
		}
		if(StringUtil.isBlank(request.getMergeOrderSettingId())){
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL,"合并订单设置规则编号不能为空");
		}
		//
		try{
			
			this.ordRuleBusiSV.saveOrderRuleSetting(request);
			//
			responseHeader.setIsSuccess(true);
			responseHeader.setResultCode("000000");
			responseHeader.setResultMessage("设置成功");
			//
			response.setResponseHeader(responseHeader);
		}catch(BusinessException e){
			//
			responseHeader.setResultCode(e.getErrorCode());
			responseHeader.setResultMessage(e.getErrorMessage());
			//
			response.setResponseHeader(responseHeader);
		}catch(Exception e){
			//
			responseHeader.setResultCode("999999");
			responseHeader.setResultMessage("设置失败");
			//
			response.setResponseHeader(responseHeader);
		}
		return response;
	}
	/**
	 * 查询订单规则详情
	 */
	@Override
	public OrderRuleDetailResponse findOrderRuleDetail() throws BusinessException, SystemException {
		OrderRuleDetailResponse response = new OrderRuleDetailResponse();
		//
		ResponseHeader responseHeader = new ResponseHeader();
		//
		try{
			response = this.ordRuleBusiSV.findOrderRuleDetail();
		}catch(Exception e){
			//
			responseHeader.setResultCode("999999");
			responseHeader.setResultMessage("失败");
			//
			response.setResponseHeader(responseHeader);
		}
		//
		return response;
	}

}
