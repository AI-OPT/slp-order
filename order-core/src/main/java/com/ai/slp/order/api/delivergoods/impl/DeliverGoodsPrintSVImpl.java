package com.ai.slp.order.api.delivergoods.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.slp.order.api.delivergoods.interfaces.IDeliverGoodsPrintSV;
import com.ai.slp.order.api.delivergoods.param.DeliverGoodsPrintInfosRequest;
import com.ai.slp.order.api.delivergoods.param.DeliverGoodsPrintRequest;
import com.ai.slp.order.api.delivergoods.param.DeliverGoodsPrintResponse;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.dao.mapper.bo.OrdOdDeliverInfo;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.service.atom.interfaces.IDeliveryOrderPrintAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.ai.slp.order.service.business.interfaces.IDeliverGoodsPrintBusiSV;
import com.ai.slp.order.util.ValidateUtils;
import com.alibaba.dubbo.config.annotation.Service;

@Service(validation="true")
@Component
public class DeliverGoodsPrintSVImpl implements IDeliverGoodsPrintSV{
	
	@Autowired
	private IDeliverGoodsPrintBusiSV deliverGoodsPrintBusiSV;
	@Autowired
	private IDeliveryOrderPrintAtomSV deliveryOrderPrintAtomSV;
	@Autowired
	private IOrdOrderAtomSV ordOrderAtomSV;

	@Override
	public DeliverGoodsPrintResponse query(DeliverGoodsPrintRequest request) throws BusinessException, SystemException {
		/* 参数校验*/
		ValidateUtils.validateDeliveryGoodsPrintRequest(request);
		String tenantId = request.getTenantId();
		Long orderId = request.getOrderId();
		/* 判断是否存在提货单打印信息*/
		List<OrdOdDeliverInfo> deliverInfos =deliveryOrderPrintAtomSV.selectDeliverByPrintInfo(orderId,
				OrdersConstants.OrdOdDeliverInfo.printInfo.ONE);
		if(CollectionUtil.isEmpty(deliverInfos)) {
			throw new BusinessException(ExceptCodeConstants.Special.NO_RESULT, "未查询到相应的信息,请查看提货单信息是否已经打印.");
		}
		OrdOrder order = ordOrderAtomSV.selectByOrderId(tenantId, orderId);
		if(order==null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, 
					"未能查询到指定的订单信息[订单id:"+ orderId+"]");
		}
		DeliverGoodsPrintResponse response = deliverGoodsPrintBusiSV.deliverGoodsQuery(request,
				deliverInfos,order);
		ResponseHeader header=new ResponseHeader(true, ExceptCodeConstants.Special.SUCCESS, "成功");
		response.setResponseHeader(header);
		return response;
	}

	@Override
	public BaseResponse print(DeliverGoodsPrintInfosRequest request) throws BusinessException, SystemException {
		BaseResponse response = new BaseResponse();
		deliverGoodsPrintBusiSV.deliverGoodsPrint(request);
		ResponseHeader header=new ResponseHeader(true, ExceptCodeConstants.Special.SUCCESS, "成功");
		response.setResponseHeader(header);
		return response;
	}

}
