package com.ai.slp.order.api.aftersaleorder.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.slp.order.api.aftersaleorder.interfaces.IOrderAfterSaleSV;
import com.ai.slp.order.api.aftersaleorder.param.OrderOFCBackRequest;
import com.ai.slp.order.api.aftersaleorder.param.OrderReturnRequest;
import com.ai.slp.order.dao.mapper.bo.OrdOdProd;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.service.atom.interfaces.IOrdOdProdAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.ai.slp.order.service.business.interfaces.IOrderAfterSaleBusiSV;
import com.ai.slp.order.util.ValidateUtils;
import com.alibaba.dubbo.config.annotation.Service;

@Service(validation = "true")
@Component
public class OrderAfterSaleSVImpl implements IOrderAfterSaleSV {
	
	@Autowired
	private IOrderAfterSaleBusiSV orderAfterSaleBusiSV;
	@Autowired
	private IOrdOdProdAtomSV ordOdProdAtomSV;
	@Autowired
	private IOrdOrderAtomSV ordOrderAtomSV;
	
	@Override
	public BaseResponse back(OrderReturnRequest request) throws BusinessException, SystemException {
		/* 1. 参数校验*/
		ValidateUtils.validateOrderReturnRequest(request);
		/* 2. 订单商品数量校验*/
		OrdOdProd ordOdProd = this.prodNumCheck(request);
		//
		OrdOrder order =this.queryOrderInfo(request);
		BaseResponse response =new BaseResponse();
		/* 3.售后处理*/
		orderAfterSaleBusiSV.back(request,order,ordOdProd);
		ResponseHeader responseHeader = new ResponseHeader(true,
				ExceptCodeConstants.Special.SUCCESS, "成功");
		response.setResponseHeader(responseHeader);
		return response;
	}

	@Override
	public BaseResponse exchange(OrderReturnRequest request) throws BusinessException, SystemException {
		/* 1.参数校验*/
		ValidateUtils.validateOrderReturnRequest(request);
		/* 2. 订单商品数量校验*/
		OrdOdProd ordOdProd = this.prodNumCheck(request);
		//
		OrdOrder order =this.queryOrderInfo(request);
		BaseResponse response =new BaseResponse();
		/* 3.售后处理*/
		orderAfterSaleBusiSV.exchange(request,order,ordOdProd);
		ResponseHeader responseHeader = new ResponseHeader(true,
				ExceptCodeConstants.Special.SUCCESS, "成功");
		response.setResponseHeader(responseHeader);
		return response;
	}

	@Override
	public BaseResponse refund(OrderReturnRequest request) throws BusinessException, SystemException {
		/* 1.参数校验*/
		ValidateUtils.validateOrderReturnRequest(request);
		/* 2. 订单商品数量校验*/
		OrdOdProd ordOdProd = this.prodNumCheck(request);
		//
		OrdOrder order =this.queryOrderInfo(request);
		BaseResponse response =new BaseResponse();
		/* 3.售后处理*/
		orderAfterSaleBusiSV.refund(request,order,ordOdProd);
        ResponseHeader responseHeader = new ResponseHeader(true,
                ExceptCodeConstants.Special.SUCCESS, "成功");
        response.setResponseHeader(responseHeader);
        return response;
	}

	@Override
	public BaseResponse backStateOFC(OrderOFCBackRequest request) throws BusinessException, SystemException {
		//参数校验
		ValidateUtils.validateOFCBackRequest(request);
		BaseResponse response =new BaseResponse();
		//
		OrdOrder ordOrder =new OrdOrder();
		ordOrder.setOrderId(Long.parseLong(request.getExternalOrderId()));
		ordOrder.setState(request.getState());
  		ordOrder.setRemark(request.getReasonDesc());
  		//状态修改
		orderAfterSaleBusiSV.backStateOFC(ordOrder);
		ResponseHeader responseHeader = new ResponseHeader(true,
				ExceptCodeConstants.Special.SUCCESS, "成功");
		response.setResponseHeader(responseHeader);
		return response;
	}
	
	
	/**
	 * 查询订单主表信息
	 */
	private OrdOrder queryOrderInfo(OrderReturnRequest request) {
		OrdOrder order = ordOrderAtomSV.selectByPrimaryKey(request.getOrderId());
		if(order==null) {
			throw new BusinessException(ExceptCodeConstants.Special.NO_RESULT, 
					"订单信息不存在[订单id:"+request.getOrderId()+"]");
		}
		return order;
	}
	
	/**
	 * 订单商品数量校验
	 */
	private OrdOdProd prodNumCheck(OrderReturnRequest request) {
		OrdOdProd ordOdProd = ordOdProdAtomSV.selectByPrimaryKey(request.getProdDetalId());
		if(ordOdProd==null) {
			throw new BusinessException(ExceptCodeConstants.Special.NO_RESULT, 
					"订单商品明细不存在[商品明细id:"+request.getProdDetalId()+"]");
		}
		long prodSum = request.getProdSum();
		if(prodSum>ordOdProd.getBuySum()) {
			throw new BusinessException("","退货数量不能大于实际商品数量");
		}
		return ordOdProd;
	}
}
