package com.ai.slp.order.api.deliveryorder.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.slp.order.api.deliveryorderprint.interfaces.IDeliveryOrderPrintSV;
import com.ai.slp.order.api.deliveryorderprint.param.DeliveryOrderPrintInfosRequest;
import com.ai.slp.order.api.deliveryorderprint.param.DeliveryOrderPrintRequest;
import com.ai.slp.order.api.deliveryorderprint.param.DeliveryOrderPrintResponse;
import com.ai.slp.order.api.deliveryorderprint.param.DeliveryOrderQueryResponse;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.dao.mapper.bo.OrdOdDeliverInfo;
import com.ai.slp.order.dao.mapper.bo.OrdOdProd;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.service.atom.interfaces.IDeliveryOrderPrintAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdProdAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.ai.slp.order.service.business.interfaces.IDeliveryOrderNoMergePrintBusiSV;
import com.ai.slp.order.service.business.interfaces.IDeliveryOrderPrintBusiSV;
import com.ai.slp.order.util.CommonCheckUtils;
import com.ai.slp.order.util.ValidateUtils;
import com.alibaba.dubbo.config.annotation.Service;

@Service(validation="true")
@Component
public class DeliveryOrderPrintSVImpl implements IDeliveryOrderPrintSV {
	
	@Autowired
	private IDeliveryOrderPrintBusiSV deliveryOrderPrintBusiSV;
	@Autowired
	private IOrdOrderAtomSV ordOrderAtomSV;
	@Autowired
	private IOrdOdProdAtomSV ordOdProdAtomSV;
	@Autowired
	private IDeliveryOrderNoMergePrintBusiSV deliveryOrderNoMergePrintBusiSV;
	@Autowired
	private IDeliveryOrderPrintAtomSV deliveryOrderPrintAtomSV;
	
	
	@Override
	public DeliveryOrderQueryResponse query(DeliveryOrderPrintRequest request) throws BusinessException, SystemException {
		//参数校验
		ValidateUtils.validateDeliveryOrderPrintRequest(request);
		OrdOrder order = ordOrderAtomSV.selectByOrderId(request.getTenantId(), request.getOrderId());
		if(order==null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, 
					"未能查询到指定的订单信息[订单id:"+request.getTenantId()+"]");
		} 
		List<OrdOdProd> ordOdProds = ordOdProdAtomSV.selectByOrd(request.getTenantId(), request.getOrderId());
		if(CollectionUtil.isEmpty(ordOdProds)) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, 
					"未能查询到指定的订单商品明细信息[订单id:"+request.getOrderId()+"]");
		}
		DeliveryOrderQueryResponse response = deliveryOrderPrintBusiSV.query(request,order,ordOdProds);
		ResponseHeader responseHeader = new ResponseHeader(true,
	                ExceptCodeConstants.Special.SUCCESS, "成功");
	    response.setResponseHeader(responseHeader);
		return response;
	}
	
	@Override
	public DeliveryOrderPrintResponse display(DeliveryOrderPrintRequest request) throws BusinessException, SystemException {
		//参数校验
		ValidateUtils.validateDeliveryOrderPrintRequest(request);
		OrdOrder order = ordOrderAtomSV.selectByOrderId(request.getTenantId(), request.getOrderId());
		if(order==null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, 
					"未能查询到指定的订单信息[订单id:"+request.getTenantId()+"]");
		} 
		List<OrdOdProd> ordOdProds = ordOdProdAtomSV.selectOrdProd(request.getTenantId(), request.getOrderId(), 
				OrdersConstants.OrdOrder.cusServiceFlag.NO);
		if(CollectionUtil.isEmpty(ordOdProds)) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, 
					"未能查询到指定的订单商品明细信息[订单id:"+request.getOrderId()+"]");
		}
		DeliveryOrderPrintResponse response = deliveryOrderPrintBusiSV.display(request,order,ordOdProds);
		ResponseHeader responseHeader = new ResponseHeader(true,
	                ExceptCodeConstants.Special.SUCCESS, "成功");
	    response.setResponseHeader(responseHeader);
		return response;
	}

	
	
	@Override
	public DeliveryOrderPrintResponse noMergePrint(DeliveryOrderPrintRequest request) throws BusinessException, SystemException {
		/* 参数校验*/
		ValidateUtils.validateDeliveryOrderPrintRequest(request);
		DeliveryOrderPrintResponse response = deliveryOrderNoMergePrintBusiSV.noMergePrint(request);
		ResponseHeader responseHeader = new ResponseHeader(true,
	                ExceptCodeConstants.Special.SUCCESS, "成功");
	    response.setResponseHeader(responseHeader);
		return response;
	}
	
	

	@Override
	public BaseResponse print(DeliveryOrderPrintInfosRequest request)throws BusinessException, SystemException {
		//参数校验
		CommonCheckUtils.checkTenantId(request.getTenantId(), "");
		if(request.getOrderId()==null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "订单id不能为空");
		}
		/* 判断是否存在提货单打印信息*/
		List<OrdOdDeliverInfo> queryInfos =deliveryOrderPrintAtomSV.selectDeliverByPrintInfo(request.getOrderId(),
				OrdersConstants.OrdOdDeliverInfo.printInfo.ONE);
		if(!CollectionUtil.isEmpty(queryInfos)) {
			throw new BusinessException(ExceptCodeConstants.Special.NO_RESULT, 
					"提货单已经打印,不能重复打印[订单id:"+request.getOrderId()+"]");
		}
		BaseResponse response=new BaseResponse();
		deliveryOrderPrintBusiSV.print(request);
		ResponseHeader responseHeader = new ResponseHeader(true,
	                ExceptCodeConstants.Special.SUCCESS, "成功");
	    response.setResponseHeader(responseHeader);
		return response;
	}
}
