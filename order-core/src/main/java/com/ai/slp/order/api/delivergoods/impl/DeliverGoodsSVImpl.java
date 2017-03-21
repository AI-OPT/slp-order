package com.ai.slp.order.api.delivergoods.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.slp.order.api.delivergoods.interfaces.IDeliverGoodsSV;
import com.ai.slp.order.api.delivergoods.param.DeliverGoodsRequest;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.ai.slp.order.service.business.interfaces.IDeliverGoodsBusiSV;
import com.ai.slp.order.util.ValidateUtils;
import com.alibaba.dubbo.config.annotation.Service;

@Service(validation="true")
@Component
public class DeliverGoodsSVImpl implements IDeliverGoodsSV {
	
	private static final Logger logger=LoggerFactory.getLogger(DeliverGoodsSVImpl.class);
	@Autowired
	private IDeliverGoodsBusiSV deliverGoodsBusiSV;
	@Autowired
	private IOrdOrderAtomSV ordOrderAtomSV;
	
	@Override
	public BaseResponse deliverGoods(DeliverGoodsRequest request) throws BusinessException, SystemException {
		/* 参数校验*/
		ValidateUtils.validateDeliverGoodsRequest(request);
		OrdOrder ordOrder = ordOrderAtomSV.selectByOrderId(request.getTenantId(), request.getOrderId());
		if(ordOrder==null) {
			logger.error("未能查询到指定的订单主表信息[订单id:"+request.getOrderId()+" ,租户id:"+request.getTenantId()+"]");
			throw new BusinessException(ExceptCodeConstants.Special.NO_RESULT, 
					"未能查询到指定的订单主表信息[订单id:"+request.getOrderId()+" ,租户id:"+request.getTenantId()+"]");
		}
		if(!OrdersConstants.OrdOrder.State.WAIT_SEND.equals(ordOrder.getState())) {
			logger.error("请确认该订单是否已经打印发货单[订单状态:"+ordOrder.getState()+"]");
			throw new BusinessException(ExceptCodeConstants.Special.NO_RESULT, 
					"请确认该订单是否已经打印发货单!");
		}
		BaseResponse response=new BaseResponse();
		deliverGoodsBusiSV.deliverGoods(request,ordOrder);
		ResponseHeader responseHeader = new ResponseHeader(true,
	                ExceptCodeConstants.Special.SUCCESS, "成功");
	    response.setResponseHeader(responseHeader);
		return response;
	}
}
