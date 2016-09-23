package com.ai.slp.order.api.notice.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.slp.order.api.notice.interfaces.IRefundNoticeSV;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.ai.slp.order.util.Key;
import com.ai.slp.order.util.KeyType;
import com.alibaba.dubbo.config.annotation.Service;
import com.changhong.upp.crypto.rsa.RSACoder;
import com.changhong.upp.util.XBConvertor;
@Service(validation="true")
@Component
public class RefundNoticeSVImpl implements IRefundNoticeSV {
	@Autowired
    private IOrdOrderAtomSV ordOrderAtomSV;
	@Resource
	private Key key;
	@Override
	public BaseResponse getRefundNotice(String xmlbody, String signMsg, String header)
			throws BusinessException, SystemException {
		BaseResponse response = new BaseResponse();
		 try {  
			 boolean flag = RSACoder.verify(key.getKey(KeyType.PUBLIC_KEY), xmlbody, signMsg);
				if (!flag) {
					 ResponseHeader responseHeader = new ResponseHeader(false,
				 				OrdersConstants.Notice.SIGN_CHECK_FAILD, "验签失败");
			             response.setResponseHeader(responseHeader);
			     		return response;
				}
				com.changhong.upp.business.entity.upp_803_001_01.RepsInfo receive = (com.changhong.upp.business.entity.upp_803_001_01.RepsInfo) XBConvertor.toBean(xmlbody, com.changhong.upp.business.entity.upp_803_001_01.RepsInfo.class);
	             //获取退款状态
			 	String state = receive.getGrpBody().getRefundStatus();
			 	String orderId = receive.getGrpBody().getMerRefundSn();
			 	if(OrdersConstants.NoticeState.REFUNDING_STATE.equals(state)){
			 		//退款中
			 		 ResponseHeader responseHeader = new ResponseHeader(false,
			 				OrdersConstants.Notice.NOTICE_REFUNDING_STATE, "退款中");
			 		 response.setResponseHeader(responseHeader);
			 	}else if(OrdersConstants.NoticeState.REFUND_SUCCESS_STATE.equals(state)){
			 		//成功,更新订单状态
	           		 OrdOrder order = new OrdOrder();
	           		 order.setOrderId(Long.valueOf(orderId));
	           		 order.setState(OrdersConstants.OrdOrder.State.FINISH_REPAY);
	           		 ordOrderAtomSV.updateById(order);
	           		ResponseHeader responseHeader = new ResponseHeader(true,
	           				OrdersConstants.Notice.NOTICE_SUCCESS_STATE, "退款成功");
		             response.setResponseHeader(responseHeader);
			 	}else{
			 		 ResponseHeader responseHeader = new ResponseHeader(false,
				 				OrdersConstants.Notice.NOTICE_FAILD_STATE, "退款失败");
			             response.setResponseHeader(responseHeader);
			 	}
	             
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
		return response;
	}
}
