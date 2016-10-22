package com.ai.slp.order.api.ordermodify.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.slp.order.api.ordermodify.interfaces.IOrderModifySV;
import com.ai.slp.order.api.ordermodify.param.OrdRequest;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.service.business.interfaces.IOrdOrderBusiSV;
import com.ai.slp.order.util.ValidateUtils;
import com.alibaba.dubbo.config.annotation.Service;

@Service(validation = "true")
@Component
public class OrderModifySVImpl implements IOrderModifySV {
	@Autowired
	private IOrdOrderBusiSV iOrdOrderBusiSV;

	@Override
	public BaseResponse modify(OrdRequest request) throws BusinessException, SystemException {
		// 有效性校驗
		ValidateUtils.validateOrdUpdate(request);
		OrdOrder order = new OrdOrder();
		order.setOrderId(request.getOrderId());
		order.setTenantId(request.getTenantId());
		order.setState(request.getState());
		int updateCount = iOrdOrderBusiSV.updateOrder(order);
		// 整理返回对象
		ResponseHeader responseHeader = new ResponseHeader();
		if (updateCount > 0) {
			responseHeader.setIsSuccess(true);
			responseHeader.setResultCode("000000");
			responseHeader.setResultMessage("数据更新成功");
		} else {
			responseHeader.setIsSuccess(false);
			responseHeader.setResultCode("999999");
			responseHeader.setResultMessage("数据库更新失败");
		}
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setResponseHeader(responseHeader);
		return baseResponse;
	}

}
