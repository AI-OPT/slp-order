package com.ai.slp.order.service.business.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.slp.order.api.ordermodify.param.OrderModifyRequest;
import com.ai.slp.order.dao.mapper.bo.OrdOdFeeTotal;
import com.ai.slp.order.service.atom.interfaces.IOrdOdFeeTotalAtomSV;
import com.ai.slp.order.service.business.interfaces.INotPaidOrderModifyBusiSV;
import com.ai.slp.order.util.CommonCheckUtils;

@Service
@Transactional
public class NotPaidOrderModifyBusiSVImpl implements INotPaidOrderModifyBusiSV {
	
	private static final Logger logger=LoggerFactory.getLogger(NotPaidOrderModifyBusiSVImpl.class);
	
	@Autowired
	private IOrdOdFeeTotalAtomSV ordOdFeeTotalAtomSV;
	
	@Override
	public void modify(OrderModifyRequest request) throws BusinessException, SystemException {
		/* 1.检验参数*/
		if(request==null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "参数不能为空");
		}
		if(request.getOrderId()==0) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "订单id不能为空");
		}
		if(request.getUpdateAmount()<=0) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "未支付订单修改金额必须大于0");
		}
		CommonCheckUtils.checkTenantId(request.getTenantId(), ExceptCodeConstants.Special.PARAM_IS_NULL);
		/* 2.修改金额和备注*/
		OrdOdFeeTotal odFeeTotal = ordOdFeeTotalAtomSV.selectByOrderId(request.getTenantId(), request.getOrderId());
		if(odFeeTotal==null) {
			logger.warn("未能查询到指定的订单费用总表信息[订单id:"+request.getOrderId()+"]");
			throw new BusinessException(ExceptCodeConstants.Special.NO_RESULT,"未能查询到指定的订单费用总表信息[订单id:"+request.getOrderId()+"]");
		}
		long updateAmount = request.getUpdateAmount();
		if(updateAmount>odFeeTotal.getAdjustFee()) {
			throw new BusinessException("", "修改金额不能大于之前的金额");
		}
		//总减免费用 
		long operDiscountFee=odFeeTotal.getAdjustFee()-updateAmount;
		odFeeTotal.setAdjustFee(updateAmount);
		odFeeTotal.setOperDiscountFee(odFeeTotal.getOperDiscountFee()+operDiscountFee);
		odFeeTotal.setOperDiscountDesc(request.getUpdateRemark());
		odFeeTotal.setDiscountFee(odFeeTotal.getDiscountFee()+operDiscountFee); //总优惠金额
		odFeeTotal.setUpdateOperId(request.getOperId());
		odFeeTotal.setPayFee(updateAmount);
		/* 3.修改金额和备注信息*/
		ordOdFeeTotalAtomSV.updateByOrderId(odFeeTotal);
	}
}
