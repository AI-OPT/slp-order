package com.ai.slp.order.api.invoiceprint.impl;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.util.DateUtil;
import com.ai.opt.sdk.util.StringUtil;
import com.ai.slp.order.api.invoiceprint.interfaces.IInvoicePrintSV;
import com.ai.slp.order.api.invoiceprint.param.InvoiceModifyRequest;
import com.ai.slp.order.api.invoiceprint.param.InvoiceNoticeRequest;
import com.ai.slp.order.api.invoiceprint.param.InvoicePrintRequest;
import com.ai.slp.order.api.invoiceprint.param.InvoicePrintResponse;
import com.ai.slp.order.api.invoiceprint.param.InvoiceSubmitRequest;
import com.ai.slp.order.api.invoiceprint.param.InvoiceSumbitResponse;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.dao.mapper.bo.OrdOdInvoice;
import com.ai.slp.order.dao.mapper.bo.OrdOdProd;
import com.ai.slp.order.service.atom.interfaces.IOrdOdInvoiceAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdProdAtomSV;
import com.ai.slp.order.service.business.interfaces.IInvoicePrintBusiSV;
import com.ai.slp.order.util.ValidateUtils;
import com.alibaba.dubbo.config.annotation.Service;

@Service(validation = "true")
@Component
public class InvoicePrintSVImpl implements IInvoicePrintSV {
	
	private static final Logger logger=LoggerFactory.getLogger(InvoicePrintSVImpl.class);
	
	@Autowired
	private IInvoicePrintBusiSV invoicePrintBusiSV;
	@Autowired
	private IOrdOdProdAtomSV ordOdProdAtomSV;
	@Autowired
	private IOrdOdInvoiceAtomSV ordOdInvoiceAtomSV;

	@Override
	public InvoicePrintResponse queryList(InvoicePrintRequest request) throws BusinessException, SystemException {
		/* 参数校验*/
		if (request == null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "参数对象不能为空");
		}
		if (StringUtil.isBlank(request.getTenantId())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "租户ID不能为空");
		}
		Integer pageNo = request.getPageNo();
		Integer pageSize = request.getPageSize();
		if (pageNo==null||(pageNo!=null && pageNo<1)) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "页码不能为空或者不能小于1");
		}
		if (pageSize==null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "页码大小不能为空");
		}
		InvoicePrintResponse response = invoicePrintBusiSV.queryList(request);
		ResponseHeader header=new ResponseHeader(true, ExceptCodeConstants.Special.SUCCESS, "成功");
		response.setResponseHeader(header);
		return response;
	}

	@Override
	public BaseResponse updateInvoiceStatus(InvoiceNoticeRequest request) throws BusinessException, SystemException {
		//参数检验
		ValidateUtils.validateInvoiceNotice(request);
		BaseResponse response=new BaseResponse();
		Long orderId = request.getOrderId();
		List<OrdOdProd> prods = ordOdProdAtomSV.selectByOrd(null,orderId);
		if(CollectionUtil.isEmpty(prods)) {
			logger.error("商品信息不存在[订单id:"+orderId+"]");
			throw new BusinessException(ExceptCodeConstants.Special.NO_RESULT, 
					"商品信息不存在[订单id:"+orderId+"]");
		}
		String companyId = request.getCompanyId();
		if(!companyId.equals(prods.get(0).getSupplierId())) {
			throw new BusinessException("", "公司代码(销售方id)和商品中的销售方id不一致");
		}
		double invoiceAmount=0;
		//计算发票金额
		for (OrdOdProd ordOdProd : prods) {
		  String amount=BigDecimal.valueOf(ordOdProd.getAdjustFee()).divide(new BigDecimal(1000))
					.setScale(2,BigDecimal.ROUND_HALF_UP).toString();//含税金额
		  invoiceAmount=Double.parseDouble(amount)+invoiceAmount;
		}
		if(invoiceAmount!=request.getInvoiceTotalFee()) {
			throw new BusinessException("", "发票总额和商品获取的额度不一致,实际发票金额:"+
					invoiceAmount+",传入的金额:"+request.getInvoiceTotalFee());
		}
		OrdOdInvoice ordOdInvoice=new OrdOdInvoice();
		ordOdInvoice.setOrderId(orderId);
		ordOdInvoice.setInvoiceId(request.getInvoiceId());
		ordOdInvoice.setInvoiceNum(request.getInvoiceNum());
		String invoiceTime = request.getInvoiceTime();
		if(StringUtil.isBlank(invoiceTime)) {
			ordOdInvoice.setInvoiceTime(null);
		}else {
			ordOdInvoice.setInvoiceTime(DateUtil.getTimestamp(DateUtil.str2Date(invoiceTime).getTime()));
		}
		ordOdInvoice.setInvoiceStatus(request.getInvoiceStatus());
		invoicePrintBusiSV.updateInvoiceStatus(request,ordOdInvoice);
		ResponseHeader header=new ResponseHeader(true, ExceptCodeConstants.Special.SUCCESS, "成功");
		response.setResponseHeader(header);
		return response;
	}

	@Override
	public InvoiceSumbitResponse invoiceSubmit(InvoiceSubmitRequest request) throws BusinessException, SystemException {
		InvoiceSumbitResponse response = invoicePrintBusiSV.invoiceSubmit(request);
		ResponseHeader header=new ResponseHeader(true, ExceptCodeConstants.Special.SUCCESS, "成功");
		response.setResponseHeader(header);
		return response;
	}

	@Override
	public BaseResponse modifyState(InvoiceModifyRequest request) throws BusinessException, SystemException {
		/* 参数校验*/
		ValidateUtils.validateModifyRequest(request);
		BaseResponse response=new BaseResponse();
		/* 查询订单信息*/
		List<OrdOdInvoice> invoiceList =ordOdInvoiceAtomSV.selectOrdOdInvoice(request.getOrderId(),
				request.getTenantId());
		if(CollectionUtil.isEmpty(invoiceList)) {
			throw new BusinessException(ExceptCodeConstants.Special.NO_RESULT, 
					"发票信息不存在[订单id: "+request.getOrderId()+",租户id: "+request.getTenantId()+"]");
		}
		OrdOdInvoice ordOdInvoice = invoiceList.get(0);
		ordOdInvoice.setInvoiceStatus(OrdersConstants.ordOdInvoice.invoiceStatus.TWO);
		invoicePrintBusiSV.modifyState(request,ordOdInvoice);
		ResponseHeader header=new ResponseHeader(true, ExceptCodeConstants.Special.SUCCESS, "成功");
		response.setResponseHeader(header);
		return response;
	}
}
