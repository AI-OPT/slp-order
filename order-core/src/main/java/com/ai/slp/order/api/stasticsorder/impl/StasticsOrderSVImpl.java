package com.ai.slp.order.api.stasticsorder.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.slp.order.api.stasticsorder.interfaces.IStasticsOrderSV;
import com.ai.slp.order.api.stasticsorder.param.StasticOrderResponse;
import com.ai.slp.order.api.stasticsorder.param.StasticParentOrderVo;
import com.ai.slp.order.api.stasticsorder.param.StasticsOrderRequest;
import com.ai.slp.order.service.business.interfaces.IStasticsOrderBusiSV;
import com.ai.slp.order.util.ValidateUtils;
import com.alibaba.dubbo.config.annotation.Service;
@Service(validation = "true")
@Component
public class StasticsOrderSVImpl implements IStasticsOrderSV{
	@Autowired
	IStasticsOrderBusiSV iStasticsOrderBusiSV;
	
	/**
	 * 该功能查询elasticSearch,现方法已废弃
	 */
	@Override
	public StasticOrderResponse queryStasticOrdPage(StasticsOrderRequest request)
			throws BusinessException, SystemException {
		//有效性校验
		ValidateUtils.validateStasticOrdQuery(request);
		StasticOrderResponse response = new StasticOrderResponse();
		PageInfo<StasticParentOrderVo> pageInfo = iStasticsOrderBusiSV.getStasticOrdPage(request);
		ResponseHeader responseHeader = new ResponseHeader(true, ExceptCodeConstants.Special.SUCCESS, "成功");
		response.setResponseHeader(responseHeader);
		response.setPageInfo(pageInfo);
		return response;
	}

}
