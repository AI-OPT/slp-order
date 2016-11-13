package com.ai.slp.order.api.ofc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.slp.order.api.ofc.interfaces.IOfcSV;
import com.ai.slp.order.api.ofc.params.OrdOdFeeTotalVo;
import com.ai.slp.order.api.ofc.params.OrdOdLogisticsVo;
import com.ai.slp.order.api.ofc.params.OrdOdProdVo;
import com.ai.slp.order.api.ofc.params.OrdOrderOfcVo;
import com.ai.slp.order.service.business.interfaces.IOrdOrderBusiSV;
import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.config.annotation.Service;

@Service(validation = "true")
@Component
public class OfcSVImpl implements IOfcSV {

	private static final Logger log = LoggerFactory.getLogger(OfcSVImpl.class);
	
	@Autowired
	private IOrdOrderBusiSV orderBusiSV;
	
	@Override
	public BaseResponse insertOrdOrder(OrdOrderOfcVo request) throws BusinessException, SystemException {
		BaseResponse response = new BaseResponse();
		ResponseHeader responseHeader =null;
		try{
			Long beginTime = System.currentTimeMillis();
			log.info("保存订单信息服务开始"+beginTime);
			orderBusiSV.insertOrdOrderOfc(request);
			log.info("保存订单息服务结束"+System.currentTimeMillis()+"耗时:"+String.valueOf(System.currentTimeMillis()-beginTime)+"毫秒");
		responseHeader = new ResponseHeader(true, ExceptCodeConstants.Special.SUCCESS, "操作成功");
		}catch(BusinessException e){
			responseHeader = new ResponseHeader(false, e.getErrorCode(), e.getErrorMessage());
		}
		response.setResponseHeader(responseHeader);
		return response;
	}

	@Override
	public BaseResponse insertOrdOdLogistics(OrdOdLogisticsVo request) throws BusinessException, SystemException {
		BaseResponse response = new BaseResponse();
		ResponseHeader responseHeader =null;
		try{
			Long beginTime = System.currentTimeMillis();
			log.info("保存订单出货信息服务开始"+beginTime);
			orderBusiSV.insertOrdOdLogisticsOfc(request);
			log.info("保存订单出货信息服务结束"+System.currentTimeMillis()+"耗时:"+String.valueOf(System.currentTimeMillis()-beginTime)+"毫秒");
		responseHeader = new ResponseHeader(true, ExceptCodeConstants.Special.SUCCESS, "操作成功");
		}catch(BusinessException e){
			responseHeader = new ResponseHeader(false, e.getErrorCode(), e.getErrorMessage());
		}
		response.setResponseHeader(responseHeader);
		return response;
	}

	@Override
	public BaseResponse insertOrdOdFeeTotal(OrdOdFeeTotalVo request) throws BusinessException, SystemException {
		BaseResponse response = new BaseResponse();
		ResponseHeader responseHeader =null;
		try{
			Long beginTime = System.currentTimeMillis();
			log.info("保存订单费用信息服务开始"+beginTime);
			orderBusiSV.insertOrdOdFeeTotalOfc(request);
			log.info("保存订单费用信息服务结束"+System.currentTimeMillis()+"耗时:"+String.valueOf(System.currentTimeMillis()-beginTime)+"毫秒");
		responseHeader = new ResponseHeader(true, ExceptCodeConstants.Special.SUCCESS, "操作成功");
		}catch(BusinessException e){
			responseHeader = new ResponseHeader(false, e.getErrorCode(), e.getErrorMessage());
		}
		response.setResponseHeader(responseHeader);
		return response;
	}

	@Override
	public BaseResponse insertOrdOdProd(OrdOdProdVo request) throws BusinessException, SystemException {
		BaseResponse response = new BaseResponse();
		ResponseHeader responseHeader =null;
		try{
			Long beginTime = System.currentTimeMillis();
			log.info("保存订单商品信息服务开始"+beginTime);
			orderBusiSV.insertOrdOdProdOfc(request);
			log.info("保存订单商品信息服务结束"+System.currentTimeMillis()+"耗时:"+String.valueOf(System.currentTimeMillis()-beginTime)+"毫秒");
		responseHeader = new ResponseHeader(true, ExceptCodeConstants.Special.SUCCESS, "操作成功");
		}catch(BusinessException e){
			responseHeader = new ResponseHeader(false, e.getErrorCode(), e.getErrorMessage());
		}
		response.setResponseHeader(responseHeader);
		return response;
	}

}
