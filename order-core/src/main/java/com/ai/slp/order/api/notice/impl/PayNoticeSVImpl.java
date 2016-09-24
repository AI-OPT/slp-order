package com.ai.slp.order.api.notice.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.util.StringUtil;
import com.ai.slp.order.api.notice.interfaces.IPayNoticeSV;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.ai.slp.order.util.Key;
import com.ai.slp.order.util.KeyType;
import com.alibaba.dubbo.config.annotation.Service;
import com.changhong.upp.crypto.rsa.RSACoder;
import com.changhong.upp.util.XBConvertor;
@Service
@Component
public class PayNoticeSVImpl implements IPayNoticeSV {
	@Autowired
    private IOrdOrderAtomSV ordOrderAtomSV;
	@Resource(name="key")
	private Key key;
	@Override
	public BaseResponse getPayNotice(String xmlbody, String signMsg, String header)
			throws BusinessException, SystemException {
		BaseResponse response = new BaseResponse();
		ResponseHeader responseHeader = null;
		 try {  
			//验签
				boolean flag = RSACoder.verify(key.getKey(KeyType.PUBLIC_KEY), xmlbody, signMsg);
				if (!flag) {
					 responseHeader = new ResponseHeader(false,
							 OrdersConstants.Notice.SIGN_CHECK_FAILD, "验签失败"); 
				}
				com.changhong.upp.business.entity.upp_103_001_01.RespInfo receive = (com.changhong.upp.business.entity.upp_103_001_01.RespInfo) XBConvertor.toBean(xmlbody, com.changhong.upp.business.entity.upp_103_001_01.RespInfo.class);
	             //获取支付状态
	             String state =  receive.getGrpBody().getPayStatus();
	             String orderId = receive.getGrpBody().getResv();
	             if(!StringUtil.isBlank(state)){
	            	 if(state.equals(OrdersConstants.NoticeState.PAID_SUCCESS_STATE)){
	            		 //更新订单状态
	            		 OrdOrder order = new OrdOrder();
	            		 order.setOrderId(Long.valueOf(orderId));
	            		 order.setState(OrdersConstants.OrdOrder.State.FINISH_PAID);
	            		 ordOrderAtomSV.updateById(order);
	            		  responseHeader = new ResponseHeader(true,
	    	            		  OrdersConstants.ORDER_SUCCESS, "支付成功");
	            	 }else if(state.equals(OrdersConstants.NoticeState.PAID_FAILD_STATE)){
	            		 responseHeader = new ResponseHeader(false,
	            				 OrdersConstants.Notice.NOTICE_FAILD_STATE, "支付失败");
	            	 }else if(state.equals(OrdersConstants.NoticeState.PAING_STATE)){
	            		 responseHeader = new ResponseHeader(false,
		   	            		  OrdersConstants.Notice.NOTICE_PAING_STATE, "支付中"); 
	            	 }else if(state.equals(OrdersConstants.NoticeState.UN_PAID_STATE)){
	            		 responseHeader = new ResponseHeader(false,
		   	            		  OrdersConstants.Notice.NOTICE_UNPAID_STATE, "待支付");  
	            	 }else{
	            		 responseHeader = new ResponseHeader(false,
	            				 OrdersConstants.Notice.NOTICE_FAILD_STATE, "支付失败"); 
	            	 }
	             }
	        } catch (Exception e) {  
	        	 responseHeader = new ResponseHeader(false,
	        			 OrdersConstants.Notice.NOTICE_FAILD_STATE, "支付失败");
	            e.printStackTrace();  
	        }  
		 response.setResponseHeader(responseHeader);
		return response;
	}
}
