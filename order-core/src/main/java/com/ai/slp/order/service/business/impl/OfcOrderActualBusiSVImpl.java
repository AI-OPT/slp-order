package com.ai.slp.order.service.business.impl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.dubbo.util.HttpClientUtil;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.util.DateUtil;
import com.ai.opt.sdk.util.ParseO2pDataUtil;
import com.ai.platform.common.api.cache.interfaces.ICacheSV;
import com.ai.platform.common.api.cache.param.SysParam;
import com.ai.slp.order.api.ofcactual.param.OfcOrderCreateRequest;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.dao.mapper.bo.OrdOdFeeProd;
import com.ai.slp.order.dao.mapper.bo.OrdOdFeeTotal;
import com.ai.slp.order.dao.mapper.bo.OrdOdInvoice;
import com.ai.slp.order.dao.mapper.bo.OrdOdLogistics;
import com.ai.slp.order.dao.mapper.bo.OrdOdProd;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.service.atom.interfaces.IOrdBalacneIfAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdFeeOffsetAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdFeeProdAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdFeeTotalAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdInvoiceAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdLogisticsAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdProdAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.ai.slp.order.service.business.interfaces.IOfcOrderActualBusiSV;
import com.ai.slp.order.util.InfoTranslateUtil;
import com.ai.slp.order.util.ValidateUtils;
import com.ai.slp.order.vo.OFCOrderCreateRequest;
import com.ai.slp.order.vo.OrderCouponVo;
import com.ai.slp.order.vo.OrderItemsVo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Service
@Transactional
public class OfcOrderActualBusiSVImpl implements IOfcOrderActualBusiSV {
	
    private static Logger logger = LoggerFactory.getLogger(OfcOrderActualBusiSVImpl.class);

    @Autowired
    IOrdOdFeeTotalAtomSV ordOdFeeTotalAtomSV;

    @Autowired
    IOrdOdProdAtomSV ordOdProdAtomSV;

    @Autowired
    IOrdBalacneIfAtomSV ordBalacneIfAtomSV;

    @Autowired
    IOrdOdFeeOffsetAtomSV ordOdFeeOffsetAtomSV;

    @Autowired
    private IOrdOrderAtomSV ordOrderAtomSV;

    @Autowired
    private IOrdOdLogisticsAtomSV ordOdLogisticsAtomSV;
    
    @Autowired
    private IOrdOdInvoiceAtomSV ordOdInvoiceAtomSV;
    
    @Autowired
    private IOrdOdFeeProdAtomSV ordOdFeeProdAtomSV;

	@Override
	public void orderCreate(OfcOrderCreateRequest request) throws BusinessException, SystemException {
		/* 1.参数校验*/
		ValidateUtils.validateOfcOrderCreateRequest(request);
		List<Long> subOrderIds = request.getOrderIds();
		/* 2.销售订单创建同步到OFC*/
		for (Long subOrderId : subOrderIds) {
			String params = this.getOFCRequestParams(request, subOrderId);
			Map<String, String> header=new HashMap<String, String>(); 
			header.put("appkey", OrdersConstants.OFC_APPKEY);
			try {
				String strData = HttpClientUtil.sendPost(OrdersConstants.OFC_ORDER_CREATE_URL, params, header);
				JSONObject jsonObject = ParseO2pDataUtil.getData(strData);
				//JSONObject object = JSON.parseObject(strData);
				boolean val = jsonObject.getBooleanValue("IsValid");
				if(!val) {
					throw new BusinessException("", "销售订单创建同步到OFC错误");
				}
			} catch (IOException | URISyntaxException e) {
				logger.error(e.getMessage());
				throw new SystemException("", "OFC同步出现异常");
			}
		}
	}
	
	
	/**
     * 组装OFC订单创建请求参数
     */
    public String getOFCRequestParams(OfcOrderCreateRequest request,Long orderId) {
        //封装数据查询该订单下的详细数据
    	ICacheSV iCacheSV = DubboConsumerFactory.getService(ICacheSV.class);
        OFCOrderCreateRequest paramsRequest=new OFCOrderCreateRequest();
        paramsRequest.setOrderNo(String.valueOf(orderId));
        OrdOrder order = ordOrderAtomSV.selectByOrderId(request.getTenantId(),orderId);
        Timestamp sysdate=null;
		if (order!=null) {
			sysdate = order.getStateChgTime();//支付时间
			SysParam sysParamChlId = InfoTranslateUtil.translateInfo(request.getTenantId(), 
						"ORD_ORDER", "CHL_ID", order.getChlId(), iCacheSV);
	        //paramsRequest.setShopName(sysParamChlId==null?"":sysParamChlId.getColumnDesc()); 
	        paramsRequest.setShopName("长虹官方旗舰店"); 
	        String time = DateUtil.getDateString(order.getOrderTime(), DateUtil.DATETIME_FORMAT);
	        paramsRequest.setOrderTime(time);
	        paramsRequest.setPayNo(String.valueOf(order.getAcctId())); //支付帐号
	        paramsRequest.setBuyerRemark(order.getRemark()); //买家备注
		}
		OrdOdLogistics logistics = ordOdLogisticsAtomSV.selectByOrd(order.getTenantId(),
					order.getOrderId());
		if(logistics!=null) {
			paramsRequest.setReceiverContact(logistics.getContactName());
			paramsRequest.setReceiverPhone(logistics.getContactTel());
			paramsRequest.setProvince(logistics.getProvinceCode()==null?"":iCacheSV.
					getAreaName(logistics.getProvinceCode())+"省"); 
			paramsRequest.setCity(logistics.getCityCode()==null?"":iCacheSV.
					getAreaName(logistics.getCityCode())+"市");
			paramsRequest.setRegion(logistics.getCountyCode()==null?"":iCacheSV.
					getAreaName(logistics.getCountyCode()));
			paramsRequest.setReceiverAddress(logistics.getAddress());
			paramsRequest.setPostCode(logistics.getPostcode());
		}
        OrdOdFeeTotal ordOdFeeTotal = ordOdFeeTotalAtomSV.selectByOrderId(order.getTenantId(), 
				order.getOrderId());
        if(ordOdFeeTotal!=null) {
        	paramsRequest.setPayTime(sysdate.toString());
        	paramsRequest.setPayType(Long.parseLong(ordOdFeeTotal.getPayStyle())); 
        	paramsRequest.setOrderAmout(ordOdFeeTotal.getTotalFee()/10); //分为单位,订单总金额 ??
        	paramsRequest.setPayAmount(ordOdFeeTotal.getAdjustFee()/10);//支付金额
        	paramsRequest.setCoupAmount(ordOdFeeTotal.getDiscountFee()/10);//优惠金额
        	paramsRequest.setReceiveAmount(ordOdFeeTotal.getPayFee()/10);
        	paramsRequest.setSellerRemark(ordOdFeeTotal.getOperDiscountDesc()); //TODO 商家备注 减免原因 ?? 
        }
        OrdOdInvoice ordOdInvoice = ordOdInvoiceAtomSV.selectByPrimaryKey(order.getOrderId());
    	if (ordOdInvoice != null) {
        	paramsRequest.setNeedInvoice(1); 
        	String invoiceType = ordOdInvoice.getInvoiceType();
        	if(OrdersConstants.ordOdInvoice.invoiceType.THREE.equals(invoiceType)) {
        		paramsRequest.setInvoiceType(1); //普票
        	}
        	if(OrdersConstants.ordOdInvoice.invoiceType.FOUR.equals(invoiceType)) {
        		paramsRequest.setInvoiceType(2); //增票
        	}
        	paramsRequest.setInvoiceTitle(ordOdInvoice.getInvoiceTitle());
        	paramsRequest.setCompanyName(ordOdInvoice.getInvoiceTitle());
        	paramsRequest.setTaxNo(ordOdInvoice.getBuyerTaxpayerNumber());
        	paramsRequest.setBank(ordOdInvoice.getBuyerBankName());
        	paramsRequest.setBankNo(ordOdInvoice.getBuyerBankAccount());
        }else {  
        	//发票类型为空的话,表示无需发票信息
        	paramsRequest.setNeedInvoice(0);
        }
    	List<OrdOdProd> ordOdProdList=ordOdProdAtomSV.selectByOrd(order.getTenantId(), orderId);
    	List<OrderItemsVo> orderItemsVoList=new ArrayList<OrderItemsVo>();
		if (!CollectionUtil.isEmpty(ordOdProdList)) {
			for (OrdOdProd ordOdProd : ordOdProdList) {
	        	OrderItemsVo orderItemsVo=new OrderItemsVo();
	        	orderItemsVo.setProductName(ordOdProd.getProdName());
	        	orderItemsVo.setProductCode("CH5007890"); //商品编码
	        	orderItemsVo.setProductNo("");
	        	orderItemsVo.setPrice(ordOdProd.getSalePrice());
	        	orderItemsVo.setQuanlity(ordOdProd.getBuySum());
	        	orderItemsVoList.add(orderItemsVo);
			}
		}
		List<OrdOdFeeProd> ordOdFeeProds = ordOdFeeProdAtomSV.selectByOrderId(orderId);
		List<OrderCouponVo> orderCouponVoList=new ArrayList<OrderCouponVo>();
		if(!CollectionUtil.isEmpty(ordOdFeeProds)) {
			for (OrdOdFeeProd ordOdFeeProd : ordOdFeeProds) {
				if(OrdersConstants.OrdOdFeeProd.PayStyle.COUPON.equals(ordOdFeeProd.getPayStyle())) {
					OrderCouponVo couponVo=new OrderCouponVo();
					couponVo.setCouponName("优惠券");
					couponVo.setCouponCode("");
					couponVo.setProductCode(""); 
					couponVo.setAmount(ordOdFeeProd.getPaidFee()); 
					orderCouponVoList.add(couponVo);
				}
				if(OrdersConstants.OrdOdFeeProd.PayStyle.JF.equals(ordOdFeeProd.getPayStyle())) {
					OrderCouponVo couponVo=new OrderCouponVo();
					couponVo.setCouponName("积分");
					couponVo.setCouponCode("");
					couponVo.setProductCode(""); 
					couponVo.setAmount(ordOdFeeProd.getJfAmount()); 
					orderCouponVoList.add(couponVo);
				}
			}
		}
        //封装参数,转化为json形式
        paramsRequest.setItems(orderItemsVoList);
        paramsRequest.setCouponList(orderCouponVoList);
    	return JSON.toJSONString(paramsRequest);
    }
}
