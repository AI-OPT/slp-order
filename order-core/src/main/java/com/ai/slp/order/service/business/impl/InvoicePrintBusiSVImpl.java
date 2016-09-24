package com.ai.slp.order.service.business.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.util.DateUtil;
import com.ai.opt.sdk.util.StringUtil;
import com.ai.slp.order.api.invoiceprint.param.InvoiceNoticeRequest;
import com.ai.slp.order.api.invoiceprint.param.InvoicePrintRequest;
import com.ai.slp.order.api.invoiceprint.param.InvoicePrintResponse;
import com.ai.slp.order.api.invoiceprint.param.InvoicePrintVo;
import com.ai.slp.order.dao.mapper.bo.OrdOdFeeTotal;
import com.ai.slp.order.dao.mapper.bo.OrdOdInvoice;
import com.ai.slp.order.dao.mapper.bo.OrdOdInvoiceCriteria;
import com.ai.slp.order.dao.mapper.bo.OrdOdProd;
import com.ai.slp.order.service.atom.interfaces.IOrdOdFeeTotalAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdInvoiceAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdProdAtomSV;
import com.ai.slp.order.service.business.interfaces.IInvoicePrintBusiSV;
import com.ai.slp.order.util.CommonCheckUtils;

public class InvoicePrintBusiSVImpl implements IInvoicePrintBusiSV{
	
	private static final Logger logger=LoggerFactory.getLogger(InvoicePrintBusiSVImpl.class);
	
	@Autowired
	private IOrdOdInvoiceAtomSV ordOdInvoiceAtomSV;
	
	@Autowired
	private IOrdOdFeeTotalAtomSV ordOdFeeTotalAtomSV;
	
	@Autowired
	private IOrdOdProdAtomSV ordOdProdAtomSV;
	
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
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "页码不能为空或者小于1");
		}
		if (pageSize==null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "页码大小不能为空");
		}
		CommonCheckUtils.checkTenantId(request.getTenantId(), ExceptCodeConstants.Special.PARAM_IS_NULL);
		InvoicePrintResponse response = new InvoicePrintResponse();
		/* 发票列表信息*/
		PageInfo<InvoicePrintVo> pageInfo = this.queryForPage(pageNo, pageSize, request.getOrderId(), 
				request.getTenantId(), request.getInvoiceTitle(), request.getInvoiceStatus());
		response.setPageInfo(pageInfo);
		return response;
	}
	
	

	@Override
	public void updateInvoiceStatus(InvoiceNoticeRequest request) throws BusinessException, SystemException {	
		/* 参数检验*/
		if (request == null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "参数对象不能为空");
		}
		if(StringUtil.isBlank(request.getCompanyId())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "公司代码(销售方)不能为空");
		}
		if(StringUtil.isBlank(request.getInvoiceId())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "发票代码不能为空");
		}
		if(StringUtil.isBlank(request.getInvoiceNum())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "发票号码不能为空");
		}
		boolean validDate = DateUtil.isValidDate(request.getInvoiceTime(), "yyyy-MM-dd");
		if(validDate) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "发票打印日期不能为空或者格式不正确");
		}
		if(StringUtil.isBlank(request.getInvoiceStatus())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "发票状态不能为空");
		}
		Long orderId = request.getOrderId();
		OrdOdInvoice ordOdInvoice = ordOdInvoiceAtomSV.selectByPrimaryKey(orderId);
		ordOdInvoice.setInvoiceId(request.getInvoiceId());
		ordOdInvoice.setInvoiceNum(request.getInvoiceNum());
		ordOdInvoice.setInvoiceTime(DateUtil.getTimestamp(request.getInvoiceTime()));
		ordOdInvoice.setInvoiceStatus(request.getInvoiceStatus());
		ordOdInvoiceAtomSV.updateByPrimaryKey(ordOdInvoice);
	}
	
	
	 private PageInfo<InvoicePrintVo> queryForPage(Integer pageNo,Integer pageSize,long orderId,
	            String tenantId, String invoiceTitle, String invoiceStatus) {
		 	OrdOdInvoiceCriteria example = new OrdOdInvoiceCriteria();
		 	List<InvoicePrintVo> invoicePrintVos=new ArrayList<InvoicePrintVo>();
	        example.setOrderByClause("INVOICE_TIME DESC");//顺序号正序
	        OrdOdInvoiceCriteria.Criteria criteria = example.createCriteria();
	        criteria.andTenantIdEqualTo(tenantId);
	        if (StringUtils.isNotBlank(invoiceTitle))
	            criteria.andInvoiceTitleLike("%"+invoiceStatus+"%");
	        if (StringUtils.isNotBlank(invoiceStatus))
	        	criteria.andInvoiceTitleEqualTo(invoiceStatus);
	        PageInfo<InvoicePrintVo> pageInfo = new PageInfo<InvoicePrintVo>();
	        //设置总数
	        pageInfo.setCount(ordOdInvoiceAtomSV.countByExample(example));
	        example.setLimitStart((pageNo - 1) * pageSize);
	        example.setLimitEnd(pageSize);
	        List<OrdOdInvoice> invoiceList = ordOdInvoiceAtomSV.selectByExample(example);
			if(!CollectionUtil.isEmpty(invoiceList)) {
				for (OrdOdInvoice ordOdInvoice : invoiceList) {
					InvoicePrintVo printVo=new InvoicePrintVo();
					List<OrdOdProd> prods = ordOdProdAtomSV.selectByOrd(ordOdInvoice.getTenantId(), ordOdInvoice.getOrderId());
					if(CollectionUtil.isEmpty(prods)) {
						logger.error("商品信息不存在[订单id:"+ordOdInvoice.getOrderId()+"]");
						throw new BusinessException(ExceptCodeConstants.Special.NO_RESULT, 
								"商品信息不存在[订单id:"+ordOdInvoice.getOrderId()+"]");
					}
					long taxAmount=0;
					//计算发票金额
					for (OrdOdProd ordOdProd : prods) {
						//TODO 通过商品信息计算税率
						taxAmount=ordOdProd.getAdjustFee()+taxAmount;
					}
					OrdOdFeeTotal odFeeTotal = ordOdFeeTotalAtomSV.selectByOrderId(ordOdInvoice.getTenantId(), 
							ordOdInvoice.getOrderId());
					
					printVo.setOrderId(ordOdInvoice.getOrderId());
					printVo.setInvoiceContent(ordOdInvoice.getInvoiceContent());
					printVo.setInvoiceStatus(ordOdInvoice.getInvoiceStatus());
					printVo.setInvoiceTitle(ordOdInvoice.getInvoiceTitle());
					printVo.setInvoiceType(ordOdInvoice.getInvoiceType());
					printVo.setTaxRate("");//17%  查看该订单下的商品税率
					printVo.setTaxAmount(taxAmount);//税率和金额
					invoicePrintVos.add(printVo);
				}
			}
	        pageInfo.setPageNo(pageNo);
	        pageInfo.setPageSize(pageSize);
	        pageInfo.setResult(invoicePrintVos);
	        return pageInfo;
	    }
}
