package com.ai.slp.order.service.business.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.util.DateUtil;
import com.ai.slp.order.api.orderrule.param.OrderRuleRequest;
import com.ai.slp.order.api.orderrule.param.OrderRuleResponse;
import com.ai.slp.order.dao.mapper.bo.OrdRule;
import com.ai.slp.order.service.atom.interfaces.IOrdRuleAtomSV;
import com.ai.slp.order.service.business.interfaces.IOrdRuleBusiSV;
@Service
public class OrdRuleBusiSVImpl implements IOrdRuleBusiSV {
	@Autowired
	private IOrdRuleAtomSV ordRuleAtomSV; 
	/**
	 * 规则设置
	 */
	@Override
	@Transactional
	public OrderRuleResponse saveOrderRuleSetting(OrderRuleRequest request){
		List<OrdRule> ordRuleList = new ArrayList<OrdRule>();
		//时间监控
		OrdRule timeMonitorOrdRule = new OrdRule();
		timeMonitorOrdRule.setOrderRuleId(request.getTimeMonitorId());
		timeMonitorOrdRule.setMonitorTime(request.getTimeMonitorTime());
		timeMonitorOrdRule.setTimeType(request.getTimeMonitorTimeType());
		timeMonitorOrdRule.setOrderSum(request.getTimeMonitorOrderSum());
		
		//购买人员监控
		OrdRule buyEmployeeMonitorOrdRule = new OrdRule();
		buyEmployeeMonitorOrdRule.setOrderRuleId(request.getBuyEmployeeMonitorId());
		buyEmployeeMonitorOrdRule.setMonitorTime(request.getBuyEmployeeMonitorTime());
		buyEmployeeMonitorOrdRule.setTimeType(request.getBuyEmployeeMonitorTimeType());
		buyEmployeeMonitorOrdRule.setOrderSum(request.getBuyEmployeeMonitorOrderSum());
		
		//购买ip监控
		OrdRule buyIpMonitorOrdRule = new OrdRule();
		buyIpMonitorOrdRule.setOrderRuleId(request.getBuyIpMonitorId());
		buyIpMonitorOrdRule.setMonitorTime(request.getBuyIpMonitorTime());
		buyIpMonitorOrdRule.setTimeType(request.getBuyIpMonitorTimeType());
		buyIpMonitorOrdRule.setOrderSum(request.getBuyIpMonitorOrderSum());
		
		//合并订单设置
		OrdRule mergeOrderSettingOrdRule = new OrdRule();
		mergeOrderSettingOrdRule.setOrderRuleId(request.getMergeOrderSettingId());
		mergeOrderSettingOrdRule.setMonitorTime(request.getMergeOrderSettingTime());
		mergeOrderSettingOrdRule.setTimeType(request.getMergeOrderSettingTimeType());
		mergeOrderSettingOrdRule.setOrderSum(request.getMergeOrderSettingOrderSum());
		//
		ordRuleList.add(timeMonitorOrdRule);
		ordRuleList.add(buyEmployeeMonitorOrdRule);
		ordRuleList.add(buyIpMonitorOrdRule);
		ordRuleList.add(mergeOrderSettingOrdRule);
		//
		OrderRuleResponse response = new OrderRuleResponse();
		ResponseHeader responseHeader = new ResponseHeader();
		
		//
		for(OrdRule ordRule : ordRuleList){
			OrdRule ordRuleDb = this.ordRuleAtomSV.getOrdRule(ordRule.getOrderRuleId());
			//
			if(null == ordRuleDb){
				ordRule.setCreateTime(DateUtil.getSysDate());
				this.ordRuleAtomSV.saveOrderRule(ordRule);
			}else{
				this.ordRuleAtomSV.updateOrderRuleSel(ordRule);
			}
			
		}
		//System.out.println(1/0);
		//
		responseHeader.setIsSuccess(true);
		responseHeader.setResultCode("000000");
		responseHeader.setResultMessage("设置成功");
		//
		response.setResponseHeader(responseHeader);
	
		//
		return response;
	}
	
	
	
}
