package com.ai.slp.order.service.business.impl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.opt.sdk.dubbo.util.HttpClientUtil;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.paas.ipaas.util.StringUtil;
import com.ai.slp.order.api.ofcorderquery.param.OFCOrderQueryRequest;
import com.ai.slp.order.api.ofcorderquery.param.OFCOrderQueryResponse;
import com.ai.slp.order.api.ofcorderquery.param.OFCOrderVo;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.service.business.interfaces.IOFCOrderQueryBusiSV;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Service
@Transactional
public class OFCOrderQueryBusiSVImpl implements IOFCOrderQueryBusiSV {
	
	private static final Logger logger=LoggerFactory.getLogger(OFCOrderQueryBusiSVImpl.class);

	@Override
	public OFCOrderQueryResponse query(OFCOrderQueryRequest request) throws BusinessException, SystemException {
		OFCOrderQueryResponse response=null;
		/* 参数校验*/
		if(request==null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "参数对象不能为空");
		}
		List<OFCOrderVo> orderNoList = request.getOrderNoList();
		if(CollectionUtil.isEmpty(orderNoList)) {
			String shopName = request.getShopName();
			if(StringUtil.isBlank(shopName)) {
				throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "订单号列表和店铺名称不能都为空");
			}
		}
		String params=JSON.toJSONString(request);
		Map<String, String> header=new HashMap<String, String>(); 
		header.put("appkey", OrdersConstants.OFC_APPKEY);
		//发送Post请求,并返回信息
		try {
			String strData = HttpClientUtil.sendPost(OrdersConstants.OFC_QUERY_URL, params, header);
			JSONObject object = JSON.parseObject(strData);
			boolean val = object.getBooleanValue("IsValid");//操作是否成功
			if(!val) {
				throw new BusinessException("", "OFC订单查询错误");
			}
			//封装返回信息
			response = JSON.parseObject(strData, OFCOrderQueryResponse.class); //TODO
		} catch (IOException | URISyntaxException e) {
			logger.error(e.getMessage());
			throw new SystemException("", "OFC同步出现异常");
		}
		return response;
	}
}
