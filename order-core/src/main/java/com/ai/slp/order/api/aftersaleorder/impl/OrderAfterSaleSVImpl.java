package com.ai.slp.order.api.aftersaleorder.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.components.mds.MDSClientFactory;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.slp.order.api.aftersaleorder.interfaces.IOrderAfterSaleSV;
import com.ai.slp.order.api.aftersaleorder.param.OrderOFCBackRequest;
import com.ai.slp.order.api.aftersaleorder.param.OrderReturnRequest;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.dao.mapper.bo.OrdOdProd;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.service.atom.interfaces.IOrdOdProdAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.ai.slp.order.service.business.interfaces.IOrderAfterSaleBusiSV;
import com.ai.slp.order.util.ValidateUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;

@Service(validation = "true")
@Component
public class OrderAfterSaleSVImpl implements IOrderAfterSaleSV {
	
	private static final Logger logger=LoggerFactory.getLogger(OrderAfterSaleSVImpl.class);
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
		BaseResponse response =new BaseResponse();
		/* 2. 订单商品数量校验*/
		OrdOdProd ordOdProd = this.prodNumCheck(request);
		//
		OrdOrder order =this.queryOrderInfo(request);
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
		BaseResponse response =new BaseResponse();
		/* 2. 订单商品数量校验*/
		OrdOdProd ordOdProd = this.prodNumCheck(request);
		//
		OrdOrder order =this.queryOrderInfo(request);
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
		BaseResponse response =new BaseResponse();
		/* 2. 订单商品数量校验*/
		OrdOdProd ordOdProd = this.prodNumCheck(request);
		//
		OrdOrder order =this.queryOrderInfo(request);
		/* 3.售后处理*/
		orderAfterSaleBusiSV.refund(request,order,ordOdProd);
        ResponseHeader responseHeader = new ResponseHeader(true,
                ExceptCodeConstants.Special.SUCCESS, "成功");
        response.setResponseHeader(responseHeader);
        return response;
	}

	@Override
	public BaseResponse backStateOFC(OrderOFCBackRequest request) throws BusinessException, SystemException {
		/*参数校验*/
		ValidateUtils.validateOFCBackRequest(request);
		boolean ccsMqFlag=false;
		//ccsMqFlag = MQConfigUtil.getCCSMqFlag();
		//非消息模式
		if(!ccsMqFlag) {
			BaseResponse response =new BaseResponse();
			orderAfterSaleBusiSV.backStateOFC(request);
			ResponseHeader responseHeader = new ResponseHeader(true,
					ExceptCodeConstants.Special.SUCCESS, "成功");
			response.setResponseHeader(responseHeader);
			return response;
		}else {
			//消息模式
			BaseResponse response =new BaseResponse();
			MDSClientFactory.getSenderClient(OrdersConstants.MDSNS.MDS_NS_OFCORDER_BACK_TOPIC).send(JSON.toJSONString(request), 0);
			ResponseHeader responseHeader = new ResponseHeader(true,
					ExceptCodeConstants.Special.SUCCESS, "成功");
			response.setResponseHeader(responseHeader);
			return response;
		}
	}
	
	
	/**
	 * 查询订单主表信息
	 */
	private OrdOrder queryOrderInfo(OrderReturnRequest request) {
		OrdOrder order = ordOrderAtomSV.selectByOrderId(request.getTenantId(), request.getOrderId());
		if(order==null) {
			logger.error("订单信息不存在[订单id:"+request.getOrderId()+"]");
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
			logger.error("订单商品明细不存在[订单id:"+request.getProdDetalId()+"]");
			throw new BusinessException(ExceptCodeConstants.Special.NO_RESULT, 
					"订单商品明细不存在[订单id:"+request.getProdDetalId()+"]");
		}
		long prodSum = request.getProdSum();
		if(prodSum>ordOdProd.getBuySum()) {
			throw new BusinessException("","退货数量不能大于实际商品数量");
		}
		return ordOdProd;
	}
}
