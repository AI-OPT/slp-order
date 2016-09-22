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
import com.ai.opt.sdk.util.StringUtil;
import com.ai.slp.order.api.notice.interfaces.IPayNoticeSV;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.alibaba.dubbo.config.annotation.Service;
import com.ylink.itfin.certificate.SecurityUtil;
import com.ylink.upp.base.oxm.XmlBodyEntity;
import com.ylink.upp.base.oxm.util.Dom4jHelper;
import com.ylink.upp.base.oxm.util.HandlerMsgUtil;
import com.ylink.upp.base.oxm.util.HeaderBean;
import com.ylink.upp.base.oxm.util.OxmHandler;
@Service(validation="true")
@Component
public class PayNoticeSVImpl implements IPayNoticeSV {
	@Autowired
    private IOrdOrderAtomSV ordOrderAtomSV;
	@Autowired
	private OxmHandler oxmHandler;
	@Override
	public BaseResponse getPayNotice(String xmlbody, String signMsg, String header)
			throws BusinessException, SystemException {
		BaseResponse response = new BaseResponse();
		ResponseHeader responseHeader = null;
		 try {  
			 	//验签
			 	com.ylink.upp.oxm.entity.upp_103_001_01.RespInfo receive = (com.ylink.upp.oxm.entity.upp_103_001_01.RespInfo) 
		        		receiveMsg(header, xmlbody, signMsg);
			 	if (receive==null) {
			 		com.ylink.upp.oxm.entity.upp_599_001_01.RespInfo messageReseive = (com.ylink.upp.oxm.entity.upp_599_001_01.RespInfo) 
			        		receiveMsg(header, xmlbody, signMsg);
			 		if (!"90000".equals(messageReseive.getGrpBody().getStsRsn().getRespCode())) {
						throw new RuntimeException("系统异常.");
					}
				}
	             //获取支付状态
	             String state =  receive.getGrpBody().getPayStatus();
	             String orderId = receive.getGrpBody().getMerOrderId();
	             if(!StringUtil.isBlank(state)){
	            	 if(state.equals("00")){
	            		 //更新订单状态
	            		 OrdOrder order = new OrdOrder();
	            		 order.setOrderId(Long.valueOf(orderId));
	            		 order.setState("");
	            		 ordOrderAtomSV.updateById(order);
	            	 }
	             }
	              responseHeader = new ResponseHeader(true,
	            		  OrdersConstants.ORDER_SUCCESS, "支付成功");
	            
	        } catch (Exception e) {  
	        	 responseHeader = new ResponseHeader(false,
	        			 OrdersConstants.ORDER_FAILD, "支付失败");
	            e.printStackTrace();  
	        }  
		 response.setResponseHeader(responseHeader);
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
	private XmlBodyEntity receiveMsg(String msgHeader, String xmlMsg, String sign) {
		try {
			boolean verify = this.verify(xmlMsg, sign);
			if (!verify) {
				throw new RuntimeException("验签失败");
			}
			HeaderBean headerBean = new HeaderBean();
			HandlerMsgUtil.conversion(msgHeader, headerBean);
			xmlMsg = Dom4jHelper.addNamespace(xmlMsg, headerBean.getMesgType(), "UTF-8");
			return (XmlBodyEntity) oxmHandler.unmarshaller(xmlMsg);
		} catch (Exception e) {
			System.out.println("接收数据时发生异常，错误信息为:" + e.getMessage());
			throw new RuntimeException(e);
		}

	}
}
