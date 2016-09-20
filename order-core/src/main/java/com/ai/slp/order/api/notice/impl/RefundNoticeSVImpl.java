package com.ai.slp.order.api.notice.impl;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.slp.order.api.notice.interfaces.IRefundNoticeSV;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.alibaba.dubbo.config.annotation.Service;
import com.ylink.itfin.certificate.SecurityUtil;
import com.ylink.upp.base.oxm.util.OxmHandler;
import com.ylink.upp.oxm.entity.upp_803_001_01.GrpBody;
@Service(validation="true")
@Component
public class RefundNoticeSVImpl implements IRefundNoticeSV {
	@Autowired
    private IOrdOrderAtomSV ordOrderAtomSV;
	@Override
	public BaseResponse getRefundNotice(String xmlbody, String signMsg, String header)
			throws BusinessException, SystemException {
		BaseResponse response = new BaseResponse();
		OxmHandler oxmHandler = DubboConsumerFactory.getService(OxmHandler.class);
		 try {  
			 	GrpBody body = (GrpBody)oxmHandler.unmarshaller(xmlbody);
			 	//验签
			 	boolean flag = verify(xmlbody,signMsg);
			 	if (!flag) {
			 		ResponseHeader responseHeader = new ResponseHeader(false,
			 				OrdersConstants.ORDER_FAILD, "验签失败");
		             response.setResponseHeader(responseHeader);
		             return response;
				}
	             //获取退款状态
			 	String state = body.getRefundStatus();
			 	String orderId = body.getResv();
			 	if("00".equals(state)){
			 		//退款中
			 	}else if("01".equals(state)){
			 		//成功,更新订单状态
	           		 OrdOrder order = new OrdOrder();
	           		 order.setOrderId(Long.valueOf(orderId));
	           		 order.setState("311");
	           		 ordOrderAtomSV.updateById(order);
			 	}else if("02".equals(state)){
			 		//失败
			 		 ResponseHeader responseHeader = new ResponseHeader(false,
			 				OrdersConstants.ORDER_FAILD, "退款失败");
		             response.setResponseHeader(responseHeader);
		             return response;
			 	}
	             ResponseHeader responseHeader = new ResponseHeader(true,
	            		 OrdersConstants.ORDER_SUCCESS, "退款成功");
	             response.setResponseHeader(responseHeader);
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
		return response;
	}
	
	/**
	 * 验签
	 * 
	 * @param xmlMsg
	 * @param sign
	 * @return
	 * @throws Exception
	 */
	private boolean verify(String xmlMsg, String sign) throws Exception {
		ResourceLoader resourceLoader = new DefaultResourceLoader();
		Resource pfxResource = resourceLoader.getResource("classpath:mobile.cer"); // 支付公钥解签
		InputStream in = new FileInputStream(pfxResource.getFile());
		byte[] cerByte = IOUtils.toByteArray(in);
		return SecurityUtil.verify(cerByte, xmlMsg, sign);
	}
}
