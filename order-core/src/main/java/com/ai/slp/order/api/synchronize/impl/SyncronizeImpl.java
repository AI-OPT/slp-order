package com.ai.slp.order.api.synchronize.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.slp.order.api.synchronize.interfaces.ISynchronizeSV;
import com.ai.slp.order.api.synchronize.params.OrderSynchronizeVo;
import com.ai.slp.order.constants.ResultCodeConstants;
import com.ai.slp.order.service.business.interfaces.ISyncronizeBusiSV;
import com.alibaba.dubbo.config.annotation.Service;

@Service
@Component
public class SyncronizeImpl implements ISynchronizeSV {

	private static final Logger LOG = LoggerFactory.getLogger(SyncronizeImpl.class);

	@Autowired
	private ISyncronizeBusiSV syncronizeBusiSV;

	@Override
	public BaseResponse orderSynchronize(OrderSynchronizeVo request) throws BusinessException, SystemException {
		ResponseHeader responseHeader = null;
		BaseResponse response = new BaseResponse();
		try {
			syncronizeBusiSV.orderSynchronize(request);
			responseHeader = new ResponseHeader(true, ResultCodeConstants.SUCCESS_CODE, "同步成功");
		} catch (BusinessException e) {
			LOG.error("同步订单失败");
			responseHeader = new ResponseHeader(false, ResultCodeConstants.FAILT_CODE, "同步失败");
		}
		response.setResponseHeader(responseHeader);
		return response;
	}

}
