package com.ai.slp.order.api.orderstate.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.components.mds.MDSClientFactory;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.opt.sdk.util.StringUtil;
import com.ai.slp.order.api.orderstate.interfaces.IOrderStateServiceSV;
import com.ai.slp.order.api.orderstate.param.WaitRebateRequest;
import com.ai.slp.order.api.orderstate.param.WaitRebateResponse;
import com.ai.slp.order.api.orderstate.param.WaitSellReceiveSureRequest;
import com.ai.slp.order.api.orderstate.param.WaitSellReceiveSureResponse;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.dao.mapper.bo.OrdOdLogistics;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.ai.slp.order.service.business.interfaces.IOrderStateBusiSV;
import com.ai.slp.order.util.MQConfigUtil;
import com.ai.slp.order.util.SequenceUtil;
import com.ai.slp.order.util.ValidateUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
@Service
@Component
public class OrderStateServiceSVImpl implements IOrderStateServiceSV {
	private static final Logger LOG = LoggerFactory.getLogger(OrderStateServiceSVImpl.class);

	@Autowired
	private IOrdOrderAtomSV ordOrderAtomSV;
	@Autowired
	private IOrderStateBusiSV orderStateBusiSV;
	@Override
	public WaitSellReceiveSureResponse updateWaitSellRecieveSureState(WaitSellReceiveSureRequest request)
			throws BusinessException, SystemException {
		WaitSellReceiveSureResponse response = new WaitSellReceiveSureResponse();
		ResponseHeader responseHeader = new ResponseHeader();
		//参数校验
		ValidateUtils.validateUpdateWaitSellState(request);
		boolean ccsMqFlag=false;
		//从配置中心获取消息启动标识ccsMqFlag
		//ccsMqFlag = MQConfigUtil.getCCSMqFlag();
		//非消息模式 同步调用服务
		if(!ccsMqFlag) {
			try{
				String tenantId = request.getTenantId();
				Long orderId = request.getOrderId();
				String expressId = request.getExpressId();
				String expressOddNumber = request.getExpressOddNumber();
				//
				OrdOrder ordOrder = ordOrderAtomSV.selectByOrderId(tenantId, orderId);
				ordOrder.setState(OrdersConstants.OrdOrder.State.WAIT_RECEIPT_CONFIRMATION);
				//
				OrdOdLogistics ordOdLogistics = new OrdOdLogistics();
				ordOdLogistics.setLogisticsId(SequenceUtil.genLogisticsId());
				ordOdLogistics.setTenantId(tenantId);
				ordOdLogistics.setOrderId(orderId);
				ordOdLogistics.setExpressId(expressId);
				ordOdLogistics.setExpressOddNumber(expressOddNumber);
				ordOdLogistics.setLogisticsType("0");
				this.orderStateBusiSV.updateWaitSellRecieveSureState(ordOrder,ordOdLogistics);
				responseHeader.setIsSuccess(true);
				responseHeader.setResultCode("000000");
				responseHeader.setResultMessage("修改状态成功");
				//
				response.setResponseHeader(responseHeader);
			}catch(Exception e){
				responseHeader.setResultCode("999999");
				responseHeader.setResultMessage("修改状态失败");			
				response.setResponseHeader(responseHeader);
				LOG.error("修改状态失败:"+e.getMessage(),e);
			}
			return response;
		}else {
			//消息模式 异步调用服务
			MDSClientFactory.getSenderClient(OrdersConstants.MDSNS.MDS_NS_ORDER_STATE_TOPIC).send(JSON.toJSONString(request), 0);
			responseHeader.setIsSuccess(true);
			responseHeader.setResultCode("000000");
			responseHeader.setResultMessage("修改状态成功");
			response.setResponseHeader(responseHeader);
			return response;
		}
	}

	@Override
	public WaitRebateResponse updateWaitRebateState(WaitRebateRequest request)
			throws BusinessException, SystemException {
		WaitRebateResponse response = new WaitRebateResponse();
		ResponseHeader responseHeader = new ResponseHeader();
		if(null == request){
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL,"请求参数不能为空");
		}
		if(StringUtil.isBlank(request.getTenantId())){
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL,"租户id不能为空");
		}
		if(null == request.getOrderId()){
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL,"租户id不能为空");
		}
		try{
			response = this.orderStateBusiSV.updateWaitRebateState(request);
			responseHeader.setIsSuccess(true);
			responseHeader.setResultCode("000000");
			responseHeader.setResultMessage("修改状态成功");
			//
			response.setResponseHeader(responseHeader);
		}catch(Exception e){
			//e.printStackTrace();
			responseHeader.setResultCode("999999");
			responseHeader.setResultMessage("修改状态失败");
			//
			response.setResponseHeader(responseHeader);
			LOG.error("修改状态失败:"+e.getMessage(),e);
		}
		//
		return response;
	}

}
