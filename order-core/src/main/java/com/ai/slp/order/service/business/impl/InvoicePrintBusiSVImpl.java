package com.ai.slp.order.service.business.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
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
import com.ai.opt.sdk.util.StringUtil;
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
import com.ai.slp.order.dao.mapper.bo.OrdOdInvoiceCriteria;
import com.ai.slp.order.dao.mapper.bo.OrdOdInvoiceCriteria.Criteria;
import com.ai.slp.order.dao.mapper.bo.OrdOdLogistics;
import com.ai.slp.order.dao.mapper.bo.OrdOdProd;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.service.atom.interfaces.IOrdOdInvoiceAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdLogisticsAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdProdAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.ai.slp.order.service.business.interfaces.IInvoicePrintBusiSV;
import com.ai.slp.order.util.CommonCheckUtils;
import com.ai.slp.order.util.ValidateUtils;

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
		CommonCheckUtils.checkTenantId(request.getTenantId(), ExceptCodeConstants.Special.PARAM_IS_NULL);
		InvoicePrintResponse response = new InvoicePrintResponse();
		/* 发票列表信息*/
		PageInfo<InvoicePrintVo> pageInfo = queryForPage(pageNo, pageSize, request.getOrderId(), 
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
		if(null==request.getCompanyId()) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "公司代码(销售方)不能为空");
		}
		if(StringUtil.isBlank(request.getInvoiceStatus())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "发票状态不能为空");
		}else {
			String status = request.getInvoiceStatus();
			if(OrdersConstants.ordOdInvoice.invoiceStatus.THREE.equals(status)) {
				if(StringUtil.isBlank(request.getInvoiceId())) {
					throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "发票代码不能为空");
				}
				if(StringUtil.isBlank(request.getInvoiceNum())) {
					throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "发票号码不能为空");
				}
				if(request.getInvoiceTime()==null) {
					throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "发票打印日期不能为空");
				}else {
					boolean valiFlag = this.isValidDate(request.getInvoiceTime(), "yyyy-MM-dd");
					if(!valiFlag) {
						throw new BusinessException("", "打印日期格式有误,请根据yyyy-MM-dd格式");
					}
				}
			}
			if(!(OrdersConstants.ordOdInvoice.invoiceStatus.THREE.equals(status)||
					OrdersConstants.ordOdInvoice.invoiceStatus.FOUR.equals(status))) {
				throw new BusinessException("", "发票状态不符合要求");
			}
		}
		long orderId = request.getOrderId();
		List<OrdOdProd> prods = ordOdProdAtomSV.selectByOrd(null, orderId);
		if(CollectionUtil.isEmpty(prods)) {
			logger.error("商品信息不存在[订单id:"+orderId+"]");
			throw new BusinessException(ExceptCodeConstants.Special.NO_RESULT, 
					"商品信息不存在[订单id:"+orderId+"]");
		}
		long invoiceAmount=0;
		long invoiceTotal=0;
		String supplierId = null;
		//计算发票金额
		for (OrdOdProd ordOdProd : prods) {
			invoiceTotal=ordOdProd.getTotalFee()+invoiceTotal;
			invoiceAmount=ordOdProd.getAdjustFee()+invoiceAmount;
			supplierId = ordOdProd.getSupplierId();
		}
		if((request.getCompanyId().equals(supplierId))) {
			throw new BusinessException("", "公司代码(销售方id)和商品中的销售方id不一致");
		}
		String taxValue = String.valueOf((invoiceTotal/1000)*0.17);
		String amoutValue = String.valueOf(invoiceAmount/1000);
		BigDecimal b1 = new BigDecimal(taxValue);
		BigDecimal b2 = new BigDecimal(amoutValue);
		double totalMoney=b1.add(b2).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue(); 
		if(request.getInvoiceTotalFee()!=0 && totalMoney!=request.getInvoiceTotalFee()) {
			throw new BusinessException("", "发票总额和商品获取的额度不一致");
		}
		OrdOdInvoice ordOdInvoice = ordOdInvoiceAtomSV.selectByPrimaryKey(orderId);
		if(ordOdInvoice==null) {
			logger.error("发票信息不存在[订单id:"+orderId+"]");
			throw new BusinessException(ExceptCodeConstants.Special.NO_RESULT, "发票信息不存在[订单id:"+orderId+"]");
		}
		ordOdInvoice.setInvoiceId(request.getInvoiceId());
		ordOdInvoice.setInvoiceNum(request.getInvoiceNum());
		String invoiceTime = request.getInvoiceTime();
		ordOdInvoice.setInvoiceTime(DateUtil.getTimestamp(DateUtil.str2Date(invoiceTime).getTime()));
		ordOdInvoice.setInvoiceStatus(request.getInvoiceStatus());
		ordOdInvoiceAtomSV.updateByPrimaryKey(ordOdInvoice);
	}
	
	
	@Override
	public InvoiceSumbitResponse invoiceSubmit(InvoiceSubmitRequest request) throws BusinessException, SystemException {
		ICacheSV iCacheSV = DubboConsumerFactory.getService(ICacheSV.class);
		InvoiceSumbitResponse response=new InvoiceSumbitResponse();
		List<InvoiceSumbitVo> invoiceList=new ArrayList<InvoiceSumbitVo>();
		if (request == null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "参数对象不能为空");
		}
		if (request.getOrderId() == null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "订单id不能为空");
		}
		CommonCheckUtils.checkTenantId(request.getTenantId(), ExceptCodeConstants.Special.PARAM_IS_NULL);
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
			respVo.setBuyerName(odLogistics.getContactName());
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
			String salePrice = String.valueOf(ordOdProd.getSalePrice()/1000);
			respVo.setPrice(new BigDecimal(salePrice).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
			respVo.setQuantity(String.valueOf(ordOdProd.getBuySum()));
			respVo.setUnit("");
			respVo.setDiscountAmount("0.00");
			respVo.setRate("0.17");
			String taxValue = String.valueOf((ordOdProd.getTotalFee()/1000)*0.17);
			String amoutValue = String.valueOf(ordOdProd.getAdjustFee()/1000);
			BigDecimal b1 = new BigDecimal(taxValue);
			BigDecimal b2 = new BigDecimal(amoutValue);
			respVo.setTax(b1.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
			respVo.setAmount(b2.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
			String s=b1.add(b2).setScale(2,BigDecimal.ROUND_HALF_UP).toString(); 
			respVo.setTaxAmount(s);
			respVo.setRemark(order.getRemark()==null?"":order.getRemark());
			invoiceList.add(respVo);
		}
		response.setInvoiceSumbitVo(invoiceList);
		return response;
	}
	
	
	 private PageInfo<InvoicePrintVo> queryForPage(Integer pageNo,Integer pageSize,Long orderId,
	            String tenantId, String invoiceTitle, String invoiceStatus) {
		 	OrdOdInvoiceCriteria example = new OrdOdInvoiceCriteria();
		 	List<InvoicePrintVo> invoicePrintVos=new ArrayList<InvoicePrintVo>();
	        example.setOrderByClause("INVOICE_TIME DESC");//顺序号正序
	        OrdOdInvoiceCriteria.Criteria criteria = example.createCriteria();
	        criteria.andTenantIdEqualTo(tenantId);
	        if (null!=orderId && 0!=orderId)
	            criteria.andOrderIdEqualTo(orderId);
	        if (StringUtils.isNotBlank(invoiceTitle))
	            criteria.andInvoiceTitleLike("%"+invoiceTitle+"%");
	        if (StringUtils.isNotBlank(invoiceStatus))
	        	criteria.andInvoiceStatusEqualTo(invoiceStatus);
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
					printVo.setTaxRate(17l);//17  查看该订单下的商品税率
					printVo.setTaxAmount((invoiceAmount/100)*17);//税率和金额
					printVo.setInvoiceAmount(invoiceAmount);
					invoicePrintVos.add(printVo);
				}
			}
	        pageInfo.setPageNo(pageNo);
	        pageInfo.setPageSize(pageSize);
	        pageInfo.setResult(invoicePrintVos);
	        return pageInfo;
	    }
	 
	 /**
	  * 判断时间是否符合格式要求
	  */
	  private boolean isValidDate(String str, String fomat) throws SystemException {
	        if (StringUtil.isBlank(str)) {
	            throw new SystemException("请指定时间字符");
	        }
	        if (StringUtil.isBlank(fomat)) {
	            throw new SystemException("请指定格式");
	        }
	        boolean flag = true;
	        try {
	            SimpleDateFormat sdf = new SimpleDateFormat(fomat);
	            sdf.setLenient(false);
	            sdf.parse(str);
	            flag = true;
	        } catch (ParseException e) {
	        	logger.error(e.getMessage(), e);
	            flag = false;
	        }
	        return flag;
	    }



	@Override
	public void modifyState(InvoiceModifyRequest request) throws BusinessException, SystemException {
		/* 参数校验*/
		ValidateUtils.validateModifyRequest(request);
		/* 查询订单信息*/
		OrdOdInvoiceCriteria example=new OrdOdInvoiceCriteria();
		Criteria criteria = example.createCriteria();
		criteria.andOrderIdEqualTo(request.getOrderId());
		criteria.andTenantIdEqualTo(request.getTenantId());
		List<OrdOdInvoice> invoiceList = ordOdInvoiceAtomSV.selectByExample(example);
		if(CollectionUtil.isEmpty(invoiceList)) {
			throw new BusinessException(ExceptCodeConstants.Special.NO_RESULT, 
					"发票信息不存在[订单id: "+request.getOrderId()+",租户id: "+request.getTenantId()+"]");
		}
		OrdOdInvoice ordOdInvoice = invoiceList.get(0);
		ordOdInvoice.setInvoiceStatus(OrdersConstants.ordOdInvoice.invoiceStatus.TWO);
		ordOdInvoiceAtomSV.updateByPrimaryKey(ordOdInvoice);
	}
}
