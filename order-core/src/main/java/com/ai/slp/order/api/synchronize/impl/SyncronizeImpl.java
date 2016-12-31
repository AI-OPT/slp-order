package com.ai.slp.order.api.synchronize.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.slp.order.api.synchronize.interfaces.ISynchronizeSV;
import com.ai.slp.order.api.synchronize.params.OrderSynchronizeVo;
import com.alibaba.dubbo.config.annotation.Service;

@Service
@Component
public class SyncronizeImpl implements ISynchronizeSV{

	private static final Logger LOG = LoggerFactory.getLogger(SyncronizeImpl.class);

	@Override
	public BaseResponse orderSynchronize(OrderSynchronizeVo request) throws BusinessException, SystemException {
		return null;
	}


}
