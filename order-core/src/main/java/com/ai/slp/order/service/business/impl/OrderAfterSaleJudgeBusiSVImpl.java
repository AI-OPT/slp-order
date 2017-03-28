package com.ai.slp.order.service.business.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.slp.order.api.aftersaleorder.param.OrderAfterVo;
import com.ai.slp.order.api.aftersaleorder.param.OrderJuageRequest;
import com.ai.slp.order.api.aftersaleorder.param.OrderJuageResponse;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.dao.mapper.bo.OrdOdProd;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.service.atom.interfaces.IOrdOdProdAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.ai.slp.order.service.business.interfaces.IOrderAfterSaleJudgeBusiSV;
import com.ai.slp.order.util.CommonCheckUtils;

@Service
@Transactional
public class OrderAfterSaleJudgeBusiSVImpl implements IOrderAfterSaleJudgeBusiSV {
	
	private static final Logger logger=LoggerFactory.getLogger(OrderAfterSaleJudgeBusiSVImpl.class);
	
	@Autowired
	private IOrdOrderAtomSV ordOrderAtomSV;
	
	@Autowired
	private IOrdOdProdAtomSV ordOdProdAtomSV;
	
	//判断该商品对应的订单所属业务类型
	@Override
	public OrderJuageResponse judge(OrderJuageRequest request) throws BusinessException, SystemException {
		OrderJuageResponse response=new OrderJuageResponse();
		List<OrdOrder> orderList=new ArrayList<>();
		List<OrdOrder> ordOrderList = ordOrderAtomSV.selectSaleOrder(request.getTenantId(), request.getOrderId());
		if(CollectionUtil.isEmpty(ordOrderList)) {
			logger.error("没有查询到相应的售后订单详情[原始订单id:"+request.getOrderId()+"]");
			throw new BusinessException(ExceptCodeConstants.Special.NO_RESULT, 
					"没有查询到相应的售后订单详情[原始订单id:"+request.getOrderId()+"]");
		}
		for (OrdOrder ordOrder : ordOrderList) {
			List<OrdOdProd> prodList = ordOdProdAtomSV.selectSaleProd(ordOrder.getTenantId(),
					ordOrder.getOrderId(), request.getSkuId());
			if(!CollectionUtil.isEmpty(prodList)) {
				OrdOdProd ordOdProd = prodList.get(0);
				OrdOrder order = ordOrderAtomSV.selectByOrderId(ordOdProd.getTenantId(),
						ordOdProd.getOrderId());
				orderList.add(order);
			}
		}
		for (OrdOrder ordOrder : orderList) {
			if(orderList.size()>1) { 
			   //多个售后订单 可能存在多个审核失败的情况
			  if(OrdersConstants.OrdOrder.State.AUDIT_FAILURE.equals(ordOrder.getState())||
					  (OrdersConstants.OrdOrder.BusiCode.CANCEL_ORDER.equals(ordOrder.getBusiCode())&&
							  OrdersConstants.OrdOrder.State.AUDIT_AGAIN_FAILURE.equals(ordOrder.getState()))) {
				  continue;
			  }
			}
			OrderAfterVo afterVo=new OrderAfterVo();
			afterVo.setOrderId(ordOrder.getOrderId());;
			afterVo.setTenantId(ordOrder.getTenantId());
			afterVo.setBusiCode(ordOrder.getBusiCode());
			afterVo.setState(ordOrder.getState());
			response.setAfterVo(afterVo);
			return response;
		}
		return null;
	}

}
