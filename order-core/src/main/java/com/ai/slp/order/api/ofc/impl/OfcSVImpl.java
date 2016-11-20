package com.ai.slp.order.api.ofc.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.slp.order.api.ofc.interfaces.IOfcSV;
import com.ai.slp.order.api.ofc.params.OfcCodeRequst;
import com.ai.slp.order.api.ofc.params.OrdOdProdVo;
import com.ai.slp.order.api.ofc.params.OrderOfcVo;
import com.ai.slp.order.service.business.interfaces.IOfcBusiSV;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;

@Service
@Component
public class OfcSVImpl implements IOfcSV {

	private static final Logger log = LoggerFactory.getLogger(OfcSVImpl.class);
	
	@Autowired
	private IOfcBusiSV ofcBusiSV;
	
	@Override
	public void insertOrdOrder(OrderOfcVo request) throws BusinessException, SystemException {
		BaseResponse response = new BaseResponse();
		ResponseHeader responseHeader =null;
		try{
			Long beginTime = System.currentTimeMillis();
			log.info("保存订单信息服务开始"+beginTime);
			ofcBusiSV.insertOrdOrder(request);
			log.info("保存订单息服务结束"+System.currentTimeMillis()+"耗时:"+String.valueOf(System.currentTimeMillis()-beginTime)+"毫秒");
		responseHeader = new ResponseHeader(true, ExceptCodeConstants.Special.SUCCESS, "操作成功");
		}catch(BusinessException e){
			log.info("++++++++++++++++++异常信息+++++++++++++++"+JSON.toJSONString(e));
			responseHeader = new ResponseHeader(false, e.getErrorCode(), e.getErrorMessage());
		}
		response.setResponseHeader(responseHeader);
		return ;
	}


	@Override
	public void insertOrdOdProd(OrdOdProdVo request) throws BusinessException, SystemException {
		BaseResponse response = new BaseResponse();
		ResponseHeader responseHeader =null;
		try{
			Long beginTime = System.currentTimeMillis();
			log.info("保存订单商品信息服务开始"+beginTime);
			ofcBusiSV.insertOrdOdProdOfc(request);
			log.info("保存订单商品信息服务结束"+System.currentTimeMillis()+"耗时:"+String.valueOf(System.currentTimeMillis()-beginTime)+"毫秒");
		responseHeader = new ResponseHeader(true, ExceptCodeConstants.Special.SUCCESS, "操作成功");
		}catch(BusinessException e){
			log.info("++++++++++++++++++异常信息+++++++++++++++"+JSON.toJSONString(e));
			responseHeader = new ResponseHeader(false, e.getErrorCode(), e.getErrorMessage());
		}
		response.setResponseHeader(responseHeader);
		return ;
	}


	@Override
	public String parseOfcCode(OfcCodeRequst request) throws BusinessException, SystemException {
		return ofcBusiSV.parseOfcCode(request);
	}
	
	

}

