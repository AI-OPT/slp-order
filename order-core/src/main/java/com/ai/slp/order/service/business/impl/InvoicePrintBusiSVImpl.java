package com.ai.slp.order.service.business.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.util.DateUtil;
import com.ai.platform.common.api.cache.interfaces.ICacheSV;
import com.ai.slp.order.api.invoiceprint.param.InvoiceModifyRequest;
import com.ai.slp.order.api.invoiceprint.param.InvoiceNoticeRequest;
import com.ai.slp.order.api.invoiceprint.param.InvoicePrintRequest;
import com.ai.slp.order.api.invoiceprint.param.InvoicePrintResponse;
import com.ai.slp.order.api.invoiceprint.param.InvoicePrintVo;
import com.ai.slp.order.api.invoiceprint.param.InvoiceSubmitRequest;
import com.ai.slp.order.api.invoiceprint.param.InvoiceSumbitResponse;
import com.ai.slp.order.api.invoiceprint.param.InvoiceSumbitVo;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.dao.mapper.bo.OrdOdInvoice;
import com.ai.slp.order.dao.mapper.bo.OrdOdLogistics;
import com.ai.slp.order.dao.mapper.bo.OrdOdProd;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.service.atom.interfaces.IOrdOdInvoiceAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdLogisticsAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdProdAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.ai.slp.order.service.business.interfaces.IInvoicePrintBusiSV;
import com.ai.slp.order.util.CommonCheckUtils;

@Service
@Transactional
public class InvoicePrintBusiSVImpl implements IInvoicePrintBusiSV{
	
	private static final Logger logger=LoggerFactory.getLogger(InvoicePrintBusiSVImpl.class);
	
	@Autowired
	private IOrdOdInvoiceAtomSV ordOdInvoiceAtomSV;
	
	@Autowired
	private IOrdOdProdAtomSV ordOdProdAtomSV;
	
    @Autowired
    private IOrdOrderAtomSV ordOrderAtomSV;
    
    @Autowired
    private IOrdOdLogisticsAtomSV ordOdLogisticsAtomSV; 
	
    //发票打印列表查看
	@Override
	public InvoicePrintResponse queryList(InvoicePrintRequest request) throws BusinessException, SystemException {
		Integer pageNo = request.getPageNo();
		Integer pageSize = request.getPageSize();
		InvoicePrintResponse response = new InvoicePrintResponse();
		/* 发票列表信息*/
		PageInfo<InvoicePrintVo> pageInfo = queryForPage(pageNo, pageSize, request.getOrderId(), 
				request.getTenantId(), request.getInvoiceTitle(), request.getInvoiceStatus());
		response.setPageInfo(pageInfo);
		return response;
	}
	
	
	//发票回调,状态修改
	@Override
	public void updateInvoiceStatus(InvoiceNoticeRequest request,OrdOdInvoice ordOdInvoice) 
			throws BusinessException, SystemException {	
		ordOdInvoiceAtomSV.updateOrdOdInvoice(ordOdInvoice);
	}
	
	//发票报送(打印)
	@Override
	public InvoiceSumbitResponse invoiceSubmit(InvoiceSubmitRequest request) throws BusinessException, SystemException {
		if (request == null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "参数对象不能为空");
		}
		if (request.getOrderId() == null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "订单id不能为空");
		}
		CommonCheckUtils.checkTenantId(request.getTenantId(), ExceptCodeConstants.Special.PARAM_IS_NULL);
		ICacheSV iCacheSV = DubboConsumerFactory.getService(ICacheSV.class);
		InvoiceSumbitResponse response=new InvoiceSumbitResponse();
		List<InvoiceSumbitVo> invoiceList=new ArrayList<InvoiceSumbitVo>();
		String tenantId = request.getTenantId();
		Long orderId = request.getOrderId();
		OrdOrder order = ordOrderAtomSV.selectByOrderId(tenantId, orderId);
		if(order==null) {
			logger.error("订单信息不存在[订单id:"+orderId+",租户id:"+tenantId+"]");
			throw new BusinessException(ExceptCodeConstants.Special.NO_RESULT, "订单信息不存在[订单id:"+orderId+
					",租户id:"+tenantId+"]");
		}
		OrdOdLogistics odLogistics = ordOdLogisticsAtomSV.selectByOrd(tenantId, order.getOrderId());
		if(odLogistics==null) {
			logger.error("订单配送信息不存在[订单id:"+order.getOrderId()+",租户id:"+tenantId+"]");
			throw new BusinessException(ExceptCodeConstants.Special.NO_RESULT, "订单配送信息不存在[订单id:"+order.getOrderId()+
					",租户id:"+tenantId+"]");
		}
		OrdOdInvoice invoice = ordOdInvoiceAtomSV.selectByPrimaryKey(orderId);
		if(invoice==null) {
			logger.error("发票信息不存在[订单id:"+orderId+"]");
			throw new BusinessException(ExceptCodeConstants.Special.NO_RESULT, "发票信息不存在[订单id:"+orderId+"]");
		}
		List<OrdOdProd> prodList = ordOdProdAtomSV.selectByOrd(tenantId, orderId);
		if(CollectionUtil.isEmpty(prodList)) {
			logger.error("商品信息不存在[订单id:"+orderId+",租户id:"+tenantId+"]");
			throw new BusinessException(ExceptCodeConstants.Special.NO_RESULT, "商品信息不存在[订单id:"+orderId+""
					+ ",租户id:"+tenantId+"]");
		}
		for (OrdOdProd ordOdProd : prodList) {
			InvoiceSumbitVo respVo=new InvoiceSumbitVo();
			StringBuffer st=new StringBuffer();//拼接详细地址
			respVo.setCorporationCode(ordOdProd.getSupplierId());
			respVo.setInvoiceClass(invoice.getInvoiceType());
			respVo.setInvoiceKind(invoice.getInvoiceKind());
			respVo.setBuyerTaxpayerNumber(
					invoice.getBuyerTaxpayerNumber()==null?"":invoice.getBuyerTaxpayerNumber());
			respVo.setBuyerCode(order.getUserId());
			respVo.setBuyerName(invoice.getInvoiceTitle()); //发票抬头
			st.append(odLogistics.getProvinceCode()==null?"":iCacheSV.
        			getAreaName(odLogistics.getProvinceCode()));
			st.append(odLogistics.getCityCode()==null?"":iCacheSV.
        			getAreaName(odLogistics.getCityCode()));
			st.append(odLogistics.getCountyCode()==null?"":iCacheSV.
        			getAreaName(odLogistics.getCountyCode()));
			st.append(odLogistics.getAddress());
			respVo.setBuyerAddress(st.toString()); //详细地址
			respVo.setBuyerTelephone("");
			respVo.setBuyerMobile(odLogistics.getContactTel());
			respVo.setBuyerEmail(odLogistics.getContactEmail()==null?"":odLogistics.getContactEmail());
			respVo.setBuyerCompanyClass(order.getUserType());
			respVo.setBuyerBankCode(invoice.getBuyerBankCode()==null?"":invoice.getBuyerBankCode());
			respVo.setBuyerBankName(invoice.getBuyerBankName()==null?"":invoice.getBuyerBankName());
			respVo.setBuyerBankAccount(invoice.getBuyerBankAccount()==null
					?"":invoice.getBuyerBankAccount());
			respVo.setSalesOrderNo(String.valueOf(order.getOrderId()));
			respVo.setOrderCreateTime(DateUtil.getDateString(order.getOrderTime(), "yyyyMMddHHmmss"));
			respVo.setOrderItem(String.valueOf(ordOdProd.getProdDetalId()));
			respVo.setMaterialCode(ordOdProd.getProdCode());
			respVo.setSpecification(""); 
			respVo.setMaterialName(ordOdProd.getProdName());
			BigDecimal salePrice = BigDecimal.valueOf(ordOdProd.getSalePrice()).divide(new BigDecimal(1000));
			respVo.setPrice(salePrice.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
			respVo.setQuantity(String.valueOf(ordOdProd.getBuySum()));
			respVo.setUnit("");
			respVo.setDiscountAmount("0.00");
			respVo.setRate(OrdersConstants.INVOICE_RATE);
			BigDecimal taxValue = BigDecimal.valueOf(ordOdProd.getAdjustFee()).divide(new BigDecimal(1000));
			BigDecimal b1 =taxValue.multiply(new BigDecimal(Double.parseDouble(OrdersConstants.INVOICE_RATE)));  //税金
			BigDecimal b2 = BigDecimal.valueOf(ordOdProd.getAdjustFee()).divide(new BigDecimal(1000)); //含税金额
			String s=b2.subtract(b1).setScale(2,BigDecimal.ROUND_HALF_UP).toString(); 
			respVo.setTax(b1.setScale(2,BigDecimal.ROUND_HALF_UP).toString());//税金
			respVo.setAmount(s); //不含税金额
			respVo.setTaxAmount(b2.setScale(2,BigDecimal.ROUND_HALF_UP).toString()); //含税金额
			respVo.setRemark(order.getRemark()==null?"":order.getRemark());
			invoiceList.add(respVo);
		}
		response.setInvoiceSumbitVo(invoiceList);
		return response;
	}
	
	 //查询指定信息
	 private PageInfo<InvoicePrintVo> queryForPage(Integer pageNo,Integer pageSize,Long orderId,
	            String tenantId, String invoiceTitle, String invoiceStatus) {
		String subFlag=OrdersConstants.OrdOrder.SubFlag.YES;
		int count = ordOdInvoiceAtomSV.count(subFlag,orderId,tenantId, 
				 invoiceTitle, invoiceStatus);
		List<OrdOdInvoice> invoiceList = ordOdInvoiceAtomSV.selectByCondition(subFlag,pageNo, 
				 pageSize,orderId,tenantId, invoiceTitle, invoiceStatus);
		List<InvoicePrintVo> invoicePrintVos=new ArrayList<InvoicePrintVo>();
	    PageInfo<InvoicePrintVo> pageInfo = new PageInfo<InvoicePrintVo>();
        //设置总数
        pageInfo.setCount(count);
		if(!CollectionUtil.isEmpty(invoiceList)) {
			for (OrdOdInvoice ordOdInvoice : invoiceList) {
				InvoicePrintVo printVo=new InvoicePrintVo();
				List<OrdOdProd> prods = ordOdProdAtomSV.selectByOrd(ordOdInvoice.getTenantId(), ordOdInvoice.getOrderId());
				if(CollectionUtil.isEmpty(prods)) {
					logger.error("商品信息不存在[订单id:"+ordOdInvoice.getOrderId()+"]");
					throw new BusinessException(ExceptCodeConstants.Special.NO_RESULT, 
							"商品信息不存在[订单id:"+ordOdInvoice.getOrderId()+"]");
				}
				long invoiceAmount=0;
				//计算发票金额
				for (OrdOdProd ordOdProd : prods) {
					//TODO 通过商品信息计算税率
					invoiceAmount=ordOdProd.getAdjustFee()+invoiceAmount;
				}
				printVo.setOrderId(ordOdInvoice.getOrderId());
				printVo.setInvoiceContent(ordOdInvoice.getInvoiceContent());
				printVo.setInvoiceStatus(ordOdInvoice.getInvoiceStatus());
				printVo.setInvoiceTitle(ordOdInvoice.getInvoiceTitle());
				printVo.setInvoiceType(ordOdInvoice.getInvoiceType());
				printVo.setInvoiceId(ordOdInvoice.getInvoiceId());
				printVo.setInvoiceNum(ordOdInvoice.getInvoiceNum());
				//查看该订单下的商品税率
				printVo.setTaxRate(new BigDecimal(OrdersConstants.INVOICE_RATE).multiply(new BigDecimal(100)).longValue());
				BigDecimal rate = BigDecimal.valueOf(invoiceAmount).multiply(new BigDecimal(Double.parseDouble(OrdersConstants.INVOICE_RATE)));
				BigDecimal scale = rate.setScale(0,BigDecimal.ROUND_HALF_UP);
				printVo.setTaxAmount(scale.longValue());//税率和金额
				printVo.setInvoiceAmount(invoiceAmount);
				invoicePrintVos.add(printVo);
			}
		}
        pageInfo.setPageNo(pageNo);
        pageInfo.setPageSize(pageSize);
        pageInfo.setResult(invoicePrintVos);
        return pageInfo;
	    }
     
	//状态修改
	@Override
	public void modifyState(InvoiceModifyRequest request,OrdOdInvoice ordOdInvoice) throws BusinessException, SystemException {
		ordOdInvoiceAtomSV.updateByPrimaryKey(ordOdInvoice);
	}
}
