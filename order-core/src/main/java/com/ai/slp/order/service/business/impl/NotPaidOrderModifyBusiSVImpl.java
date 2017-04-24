package com.ai.slp.order.service.business.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.sdk.components.ses.SESClientFactory;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.paas.ipaas.search.common.JsonBuilder;
import com.ai.slp.order.api.orderpricemodify.param.OrderModifyRequest;
import com.ai.slp.order.constants.SearchConstants;
import com.ai.slp.order.constants.SearchFieldConfConstants;
import com.ai.slp.order.dao.mapper.bo.OrdOdFeeTotal;
import com.ai.slp.order.manager.ESClientManager;
import com.ai.slp.order.service.atom.interfaces.IOrdOdFeeTotalAtomSV;
import com.ai.slp.order.service.business.interfaces.INotPaidOrderModifyBusiSV;

@Service
@Transactional
public class NotPaidOrderModifyBusiSVImpl implements INotPaidOrderModifyBusiSV {
	
	private static final Logger logger=LoggerFactory.getLogger(NotPaidOrderModifyBusiSVImpl.class);
	
	@Autowired
	private IOrdOdFeeTotalAtomSV ordOdFeeTotalAtomSV;
	//未支付订单修改
	@Override
	public void modify(OrderModifyRequest request) throws BusinessException, SystemException {
		/* 1.修改金额和备注*/
		OrdOdFeeTotal odFeeTotal = ordOdFeeTotalAtomSV.selectByOrderId(request.getTenantId(), request.getOrderId());
		if(odFeeTotal==null) {
			logger.warn("未能查询到指定的订单费用总表信息[订单id:"+request.getOrderId()+"]");
			throw new BusinessException(ExceptCodeConstants.Special.NO_RESULT,
					"未能查询到指定的订单费用总表信息[订单id:"+request.getOrderId()+"]");
		}
		long updateAmount = request.getUpdateAmount();
		if(updateAmount>odFeeTotal.getAdjustFee()) {
			throw new BusinessException("", "修改金额不能大于应付金额!");
		}
		if(updateAmount<odFeeTotal.getFreight()) {
			throw new BusinessException("", "修改金额不能小于运费!");
		}
		//总减免费用 
		long operDiscountFee=odFeeTotal.getAdjustFee()-updateAmount;
		odFeeTotal.setAdjustFee(updateAmount);
		odFeeTotal.setOperDiscountFee(odFeeTotal.getOperDiscountFee()+operDiscountFee);
		odFeeTotal.setOperDiscountDesc(request.getUpdateRemark());
		odFeeTotal.setDiscountFee(odFeeTotal.getDiscountFee()+operDiscountFee); //总优惠金额
		odFeeTotal.setUpdateOperId(request.getOperId());
		odFeeTotal.setPayFee(updateAmount);
		/* 2.修改金额和备注信息*/
		ordOdFeeTotalAtomSV.updateByOrderId(odFeeTotal);
		/* 3.导入数据到搜索引擎*/
		try {
			ESClientManager.getSesClient(SearchConstants.SearchNameSpace).
				upsert(String.valueOf(request.getOrderId()), 
						new JsonBuilder().startObject().field(SearchFieldConfConstants.DISCOUNT_FEE, 
								odFeeTotal.getDiscountFee()).
						field(SearchFieldConfConstants.ADJUST_FEE, updateAmount).endObject());
		} catch (Exception e) {
			logger.error("导入数据到搜索引擎失败.......");
			throw new SystemException("导入数据到搜索引擎失败..."+request.getOrderId());
		}
	}
}
