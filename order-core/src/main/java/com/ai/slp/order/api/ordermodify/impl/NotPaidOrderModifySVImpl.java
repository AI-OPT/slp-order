package com.ai.slp.order.api.ordermodify.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.slp.order.api.ordermodify.interfaces.INotPaidOrderModifySV;
import com.ai.slp.order.api.ordermodify.param.OrderModifyRequest;
import com.ai.slp.order.api.sesdata.param.SesDataRequest;
import com.ai.slp.order.service.business.interfaces.INotPaidOrderModifyBusiSV;
import com.ai.slp.order.service.business.interfaces.search.IOrderIndexBusiSV;

@Component
public class NotPaidOrderModifySVImpl implements INotPaidOrderModifySV{
	@Autowired
	private INotPaidOrderModifyBusiSV notPaidOrderModifyBusiSV;
	@Autowired
	private IOrderIndexBusiSV orderIndexBusiSV;
	
	@Override
	public BaseResponse modify(OrderModifyRequest request) throws BusinessException, SystemException {
		BaseResponse response=new BaseResponse();
		//1.修改金额
		notPaidOrderModifyBusiSV.modify(request);
		//2.导入数据到搜索引擎
		SesDataRequest sesReq=new SesDataRequest();
		sesReq.setTenantId(request.getTenantId());
		sesReq.setParentOrderId(request.getOrderId());
		this.orderIndexBusiSV.insertSesData(sesReq);
		ResponseHeader responseHeader = new ResponseHeader(true,
                ExceptCodeConstants.Special.SUCCESS, "成功");
		response.setResponseHeader(responseHeader);
		return response;
	}

}
