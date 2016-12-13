package com.ai.slp.order.service.business.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.sdk.components.dss.DSSClientFactory;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.util.DateUtil;
import com.ai.paas.ipaas.dss.base.interfaces.IDSSClient;
import com.ai.paas.ipaas.util.StringUtil;
import com.ai.slp.order.api.orderrule.interfaces.IOrderMonitorSV;
import com.ai.slp.order.api.orderrule.param.OrderMonitorBeforResponse;
import com.ai.slp.order.api.orderrule.param.OrderMonitorRequest;
import com.ai.slp.order.api.ordertradecenter.param.OrdBaseInfo;
import com.ai.slp.order.api.ordertradecenter.param.OrdExtendInfo;
import com.ai.slp.order.api.ordertradecenter.param.OrdFeeInfo;
import com.ai.slp.order.api.ordertradecenter.param.OrdFeeTotalProdInfo;
import com.ai.slp.order.api.ordertradecenter.param.OrdInvoiceInfo;
import com.ai.slp.order.api.ordertradecenter.param.OrdLogisticsInfo;
import com.ai.slp.order.api.ordertradecenter.param.OrdProductDetailInfo;
import com.ai.slp.order.api.ordertradecenter.param.OrdProductInfo;
import com.ai.slp.order.api.ordertradecenter.param.OrdProductResInfo;
import com.ai.slp.order.api.ordertradecenter.param.OrderResInfo;
import com.ai.slp.order.api.ordertradecenter.param.OrderTradeCenterRequest;
import com.ai.slp.order.api.ordertradecenter.param.OrderTradeCenterResponse;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.constants.OrdersConstants.OrdOdStateChg;
import com.ai.slp.order.dao.mapper.bo.OrdOdFeeProd;
import com.ai.slp.order.dao.mapper.bo.OrdOdFeeTotal;
import com.ai.slp.order.dao.mapper.bo.OrdOdInvoice;
import com.ai.slp.order.dao.mapper.bo.OrdOdLogistics;
import com.ai.slp.order.dao.mapper.bo.OrdOdProd;
import com.ai.slp.order.dao.mapper.bo.OrdOdProdCriteria;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.service.atom.interfaces.IOrdOdFeeProdAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdFeeTotalAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdInvoiceAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdLogisticsAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdProdAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.ai.slp.order.service.business.interfaces.IOrdOrderTradeBusiSV;
import com.ai.slp.order.service.business.interfaces.IOrderFrameCoreSV;
import com.ai.slp.order.util.ProdComparator;
import com.ai.slp.order.util.SequenceUtil;
import com.ai.slp.order.util.ValidateUtils;
import com.ai.slp.product.api.storageserver.interfaces.IStorageNumSV;
import com.ai.slp.product.api.storageserver.param.StorageNumRes;
import com.ai.slp.product.api.storageserver.param.StorageNumUserReq;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Service
@Transactional
public class OrdOrderTradeBusiSVImpl implements IOrdOrderTradeBusiSV {

    private static final Logger LOG = LoggerFactory.getLogger(OrdOrderTradeBusiSVImpl.class);

    @Autowired
    private IOrdOrderAtomSV ordOrderAtomSV;

    @Autowired
    private IOrdOdProdAtomSV ordOdProdAtomSV;

    @Autowired
    private IOrdOdFeeTotalAtomSV ordOdFeeTotalAtomSV;

    @Autowired
    private IOrderFrameCoreSV orderFrameCoreSV;
    
    @Autowired
    private IOrdOdLogisticsAtomSV ordOdLogisticsAtomSV;
    
    @Autowired
    private IOrdOdInvoiceAtomSV ordOdInvoiceAtomSV;
    
    @Autowired
    private IOrdOdFeeProdAtomSV ordOdFeeProdAtomSV;
    
    @Autowired
    private IOrderMonitorSV orderMonitorSV;

    @Override
    public OrderTradeCenterResponse apply(OrderTradeCenterRequest request)
            throws BusinessException, SystemException {
    	LOG.info("商品下单处理......");
    	//参数校验
    	ValidateUtils.validateOrderTradeCenter(request); 
    	//订单异常监控
    	OrderMonitorRequest monitorRequest=new OrderMonitorRequest();
    	OrdBaseInfo ordBaseInfo = request.getOrdBaseInfo();
    	monitorRequest.setUserId(ordBaseInfo.getUserId());
    	monitorRequest.setIpAddress(ordBaseInfo.getIpAddress());
    	OrderMonitorBeforResponse beforSubmitOrder = orderMonitorSV.beforSubmitOrder(monitorRequest);
        OrderTradeCenterResponse response = new OrderTradeCenterResponse();
       // IDSSClient client = DSSClientFactory.getDSSClient(OrdersConstants.ORDER_PHONENUM_DSS);
        IDSSClient client =null;
        List<OrderResInfo> orderResInfos=new ArrayList<OrderResInfo>();
        List<OrdProductResInfo> ordProductResList =null;
        Timestamp sysDate = DateUtil.getSysDate();
        List<OrdProductDetailInfo> ordProductDetailInfos = request.getOrdProductDetailInfos();
        for (OrdProductDetailInfo ordProductDetailInfo : ordProductDetailInfos) {
        	OrderResInfo orderResInfo=new OrderResInfo();
        	//积分账户id
        	String accountId = ordProductDetailInfo.getAccountId();
        	//积分令牌id
        	String tokenId=ordProductDetailInfo.getTokenId();
        	String pointRate = ordProductDetailInfo.getPointRate();
        	String remark = ordProductDetailInfo.getRemark();
        	String supplierId = ordProductDetailInfo.getSupplierId();
        	/* 1.创建业务订单,并返回订单Id*/
        	long orderId = this.createOrder(ordBaseInfo,accountId,beforSubmitOrder,
        			request.getTenantId(),sysDate,remark,supplierId,tokenId,pointRate);
        	/* 2.创建商品明细,费用明细信息 */
        	Map<String, Object> mapProduct = this.createProdInfo(request,ordProductDetailInfo, sysDate, 
        			orderId,client,supplierId);
        	ordProductResList = (List<OrdProductResInfo>) mapProduct.get("ordProductResList");
        	long totalFee = (long) mapProduct.get("totalFee");
        	/* 3.费用信息及费用明细处理 */
        	this.createFeeInfo(request,ordProductDetailInfo, sysDate, orderId,totalFee);
        	/* 4.创建发票信息 */
        	this.createOrderFeeInvoice(request,ordProductDetailInfo, sysDate, orderId);
        	/* 5. 处理配送信息，存在则写入 */
        	this.createOrderLogistics(request, sysDate, orderId);
        	/* 6. 记录一条订单创建轨迹记录 */
        	this.writeOrderCreateStateChg(request, sysDate, orderId);
        	/* 7. 更新订单状态 */
        	this.updateOrderState(request.getTenantId(), sysDate, orderId);
        	/* 8.订单提交成功后监控服务*/
        	orderMonitorSV.afterSubmitOrder(monitorRequest);
        	/* 9.封装返回参数*/
        	orderResInfo.setOrderId(orderId);
        	orderResInfo.setOrdProductResList(ordProductResList);
        	orderResInfos.add(orderResInfo);
		}
        /* 10.返回费用总金额*/
        OrdFeeInfo ordFeeInfo = this.buildFeeInfo(request);
        response.setOrdFeeInfo(ordFeeInfo);
        response.setOrderResInfos(orderResInfos);
        return response;
    }
    

    /**
     * 创建订单信息
     * 
     * @param ordOrderInfo
     * @param sysDate
     * @param orderId
     * @author zhangxw
     * @ApiDocMethod
     */
    private long createOrder(OrdBaseInfo ordBaseInfo,String accountId,OrderMonitorBeforResponse beforSubmitOrder,
    		String tenantId,Timestamp sysDate,String remark,String supplierId,String tokenId,String pointRate ) {
        OrdOrder ordOrder = new OrdOrder();
        long orderId = SequenceUtil.createOrderId();
        ordOrder.setOrderId(orderId);
        ordOrder.setTenantId(tenantId);
        ordOrder.setBusiCode(OrdersConstants.OrdOrder.BusiCode.NORMAL_ORDER);
        ordOrder.setOrderType(ordBaseInfo.getOrderType());
        ordOrder.setSubFlag(OrdersConstants.OrdOrder.SubFlag.NO);
        ordOrder.setUserId(ordBaseInfo.getUserId());
        ordOrder.setUserName(ordBaseInfo.getUserName());//用户名称
        ordOrder.setUserTel(ordBaseInfo.getUserTel()); //用户手机号
        ordOrder.setUserType(ordBaseInfo.getUserType());
        ordOrder.setIpAddress(ordBaseInfo.getIpAddress());
        ordOrder.setAcctId(ordBaseInfo.getAcctId());
        ordOrder.setAccountId(accountId);
        ordOrder.setTokenId(tokenId);// 积分令牌Id
        ordOrder.setProvinceCode(ordBaseInfo.getProvinceCode());
        ordOrder.setCityCode(ordBaseInfo.getCityCode());
        ordOrder.setChlId(ordBaseInfo.getChlId()); 
        ordOrder.setState(OrdersConstants.OrdOrder.State.NEW);
        ordOrder.setStateChgTime(sysDate);
        ordOrder.setDisplayFlag(OrdersConstants.OrdOrder.DisplayFlag.USER_NORMAL_VISIABLE);
        ordOrder.setDisplayFlagChgTime(sysDate);
        ordOrder.setDeliveryFlag(ordBaseInfo.getDeliveryFlag());
        ordOrder.setOrderTime(sysDate);
        ordOrder.setOrderDesc(ordBaseInfo.getOrderDesc());
        ordOrder.setKeywords(ordBaseInfo.getKeywords());
        ordOrder.setRemark(remark);
        ordOrder.setSupplierId(supplierId);
        ordOrder.setIfWarning(beforSubmitOrder.getIfWarning());
        ordOrder.setWarningType(beforSubmitOrder.getWarningType());
        ordOrder.setCusServiceFlag(OrdersConstants.OrdOrder.cusServiceFlag.NO);
        ordOrder.setFlag(ordBaseInfo.getFlag());
        ordOrder.setPointRate(pointRate);//积分比率
        ordOrderAtomSV.insertSelective(ordOrder);
        return orderId;
    }

    /**
     * 创建商品明细信息
     * 
     * @param request
     * @param sysDate
     * @param orderId
     * @author zhangxw
     * @param client 
     * @ApiDocMethod
     */
    private Map<String,Object> createProdInfo(OrderTradeCenterRequest request,OrdProductDetailInfo ordProductDetailInfo,
    		Timestamp sysDate, long orderId, IDSSClient client,String supplierId) {
        LOG.debug("开始处理订单商品明细[" + orderId + "]和订单费用明细资料信息..");
        OrdBaseInfo ordBaseInfo = request.getOrdBaseInfo();
        String orderType = ordBaseInfo.getOrderType();
        List<OrdProductResInfo> ordProductResList = new ArrayList<OrdProductResInfo>();
        Map<String,Object> mapProduct = new HashMap<String,Object>();
        List<OrdProductInfo> ordProductInfoList = ordProductDetailInfo.getOrdProductInfoList();
        long prodTotalFee=0;
        for (OrdProductInfo ordProductInfo : ordProductInfoList) {
            StorageNumRes storageNumRes = this.querySkuInfo(request.getTenantId(),
                    ordProductInfo.getSkuId(), ordProductInfo.getBuySum());
            boolean isSuccess = storageNumRes.getResponseHeader().getIsSuccess();
            if(!isSuccess){
            	throw new BusinessException(storageNumRes.getResponseHeader().getResultCode(), 
        			storageNumRes.getResponseHeader().getResultMessage());
        	}
            Map<String, Integer> storageNum = storageNumRes.getStorageNum();
            if (storageNum == null) {
                throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "商品库存为空");
            }
            long prodDetailId = SequenceUtil.createProdDetailId();
            OrdOdProd ordOdProd = new OrdOdProd();
            ordOdProd.setProdDetalId(prodDetailId);
            ordOdProd.setTenantId(request.getTenantId());
            ordOdProd.setOrderId(orderId);
            ordOdProd.setProdType(storageNumRes.getProductCatId());
            ordOdProd.setProdId(storageNumRes.getProdId());
            ordOdProd.setProdName(storageNumRes.getSkuName());
            ordOdProd.setSkuId(ordProductInfo.getSkuId());
            ordOdProd.setStandardProdId(storageNumRes.getStandedProdId());
            ordOdProd.setSkuStorageId(JSON.toJSONString(storageNum));
            ordOdProd.setValidTime(sysDate);
            ordOdProd.setInvalidTime(DateUtil.getFutureTime());
            ordOdProd.setSupplierId(supplierId);
            ordOdProd.setState(OrdersConstants.OrdOdProd.State.SELL);
            ordOdProd.setBuySum(ordProductInfo.getBuySum());
            ordOdProd.setSalePrice(storageNumRes.getSalePrice());
            long totalFee=storageNumRes.getSalePrice() * ordProductInfo.getBuySum();
            prodTotalFee=totalFee+prodTotalFee;
            ordOdProd.setTotalFee(totalFee);
            ordOdProd.setCusServiceFlag(OrdersConstants.OrdOrder.cusServiceFlag.NO);
            ordOdProd.setExtendInfo(ordProductInfo.getStandard());
            ordOdProd.setUpdateTime(sysDate);
            ordOdProd.setJf(ordProductInfo.getGiveJF()); //赠送积分
            ordOdProd.setProdCode(""); //商品编码 TODO 
            ordOdProdAtomSV.insertSelective(ordOdProd);
            /* 2. 封装订单提交商品返回参数 */
            OrdProductResInfo ordProductResInfo = new OrdProductResInfo();
            ordProductResInfo.setSkuId(ordOdProd.getSkuId());
            ordProductResInfo.setSkuName(ordOdProd.getProdName());
            ordProductResInfo.setSalePrice(ordOdProd.getSalePrice());
            ordProductResInfo.setBuySum((int) ordOdProd.getBuySum());
            ordProductResInfo.setSkuTotalFee(ordOdProd.getTotalFee());
            ordProductResList.add(ordProductResInfo);
            /* 3. 创建商品明细扩展表 */
            this.createOrdOdProdExtend(prodDetailId, request, sysDate, orderId, orderType,client);
        }
        mapProduct.put("totalFee", prodTotalFee);
        mapProduct.put("ordProductResList", ordProductResList);
        return mapProduct;
    }

    /**
     * 创建费用信息
     * 
     * @param request
     * @param sysDate
     * @param orderId
     * @author zhangxw
     * @ApiDocMethod
     */
    private void createFeeInfo(OrderTradeCenterRequest request,OrdProductDetailInfo ordProductDetailInfo,
    		Timestamp sysDate,long orderId,long totalFee) {
    	/* 1.费用明细表信息*/
    	List<OrdFeeTotalProdInfo> totalProdInfos = ordProductDetailInfo.getOrdFeeTotalProdInfo();
        long totalCouponFee=0;
		long totalJfFee=0; 
		long totallJfAmount=0;
        for (OrdFeeTotalProdInfo ordFeeTotalProdInfo : totalProdInfos) {
			OrdOdFeeProd feeProd=new OrdOdFeeProd();
			feeProd.setOrderId(orderId);
			String payStyle = ordFeeTotalProdInfo.getPayStyle();
			if(OrdersConstants.OrdOdFeeProd.PayStyle.COUPON.equals(payStyle)) {
				totalCouponFee=ordFeeTotalProdInfo.getPaidFee();
			}
			if(OrdersConstants.OrdOdFeeProd.PayStyle.JF.equals(payStyle)) {
				totalJfFee=ordFeeTotalProdInfo.getPaidFee();
				totallJfAmount=ordFeeTotalProdInfo.getJfAmount();
				feeProd.setJfAmount(totallJfAmount);
			}
			feeProd.setPayStyle(ordFeeTotalProdInfo.getPayStyle());
			feeProd.setPaidFee(ordFeeTotalProdInfo.getPaidFee());
        	ordOdFeeProdAtomSV.insertSelective(feeProd);
        }
        /* 2. 费用入总表 */
        long shopDiscountFee = ordProductDetailInfo.getDiscountFee(); //销售商下的优惠金额
        List<OrdOdProd> ordOdProds = this.getOrdOdProds(request.getTenantId(), orderId);
        if (CollectionUtil.isEmpty(ordOdProds)) {
            throw new BusinessException("", "订单商品明细不存在[订单ID:" + orderId + "]");
        }
        long discountFee = 0;
        long operDiscountFee = 0;
        long totalJf=0;
        
        //更新前排序
        Collections.sort(ordOdProds, new ProdComparator());

        /* 更新商品明细信息*/
        for (OrdOdProd ordOdProd : ordOdProds) {
        	//积分 积分金额 优惠券 优惠金额,按比例划分
        	BigDecimal rate = BigDecimal.valueOf(ordOdProd.getTotalFee()).divide(new BigDecimal(totalFee),5,BigDecimal.ROUND_HALF_UP);
        	long prodJfFee=(rate.multiply(new BigDecimal(totalJfFee))).longValue();
        	long prodCouponFee=(rate.multiply(new BigDecimal(totalCouponFee))).longValue();
        	long prodJfAmount=(rate.multiply(new BigDecimal(totallJfAmount))).longValue();
        	long prodDiscountFee=(rate.multiply(new BigDecimal(shopDiscountFee))).longValue();
        	long prodAdjustFee= ordOdProd.getTotalFee()-(prodCouponFee+prodJfAmount+prodDiscountFee);
        	ordOdProd.setJfFee(prodJfFee);
        	ordOdProd.setCouponFee(prodCouponFee);
        	ordOdProd.setDiscountFee(prodCouponFee+prodJfAmount+prodDiscountFee);
        	ordOdProd.setAdjustFee(prodAdjustFee<0?0:prodAdjustFee);
        	ordOdProdAtomSV.updateById(ordOdProd);
        	//计算费用总表信息
            discountFee = ordOdProd.getDiscountFee() + discountFee;
            operDiscountFee = ordOdProd.getOperDiscountFee() + operDiscountFee;
            totalJf=ordOdProd.getJf() + totalJf;
        }
        OrdOdFeeTotal ordOdFeeTotal = new OrdOdFeeTotal();
        ordOdFeeTotal.setOrderId(orderId);
        ordOdFeeTotal.setTenantId(request.getTenantId());
        ordOdFeeTotal.setPayFlag(OrdersConstants.OrdOdFeeTotal.payFlag.IN);
        long freight = ordProductDetailInfo.getFreight();
        ordOdFeeTotal.setTotalFee(totalFee+freight);
        ordOdFeeTotal.setDiscountFee(discountFee);
        long totalProdFee=totalFee-discountFee+freight;
        ordOdFeeTotal.setAdjustFee(totalProdFee<0?0:totalProdFee);
        ordOdFeeTotal.setPaidFee(0);
        ordOdFeeTotal.setPayFee(totalProdFee<0?0:totalProdFee);//加上运费
        ordOdFeeTotal.setUpdateTime(sysDate);
        ordOdFeeTotal.setUpdateChlId("");
        ordOdFeeTotal.setUpdateOperId("");
        ordOdFeeTotal.setTotalJf(totalJf);
        ordOdFeeTotal.setFreight(freight);
        ordOdFeeTotalAtomSV.insertSelective(ordOdFeeTotal);
    }
    
    /**
     * 封装参数,返回费用总金额
     */
    private OrdFeeInfo buildFeeInfo(OrderTradeCenterRequest request) {
    	/* 	1. 封装返回参数 */
        OrdExtendInfo ordExtendInfo = request.getOrdExtendInfo();
        String infoJson = ordExtendInfo.getInfoJson();
        JSONObject object = JSON.parseObject(infoJson);
        Object objTotalFee = object.get("totalFee"); 
        Object objAdjustFee = object.get("adjustFee"); 
        long returnTotalFee;
        long returnAdjustFee;
        if (objTotalFee==null || false == NumberUtils.isNumber(objTotalFee+"")){	
        	returnTotalFee=0L;
        } else {
        	returnTotalFee=Long.valueOf(objTotalFee+"");
        }
        if (objAdjustFee==null || false == NumberUtils.isNumber(objAdjustFee+"")){	
        	returnAdjustFee=0L;
        } else {
        	returnAdjustFee=Long.valueOf(objAdjustFee+"");
        }
        OrdFeeInfo ordFeeInfo = new OrdFeeInfo();
        ordFeeInfo.setTotalFee(returnTotalFee);
        ordFeeInfo.setDiscountFee(returnTotalFee-returnAdjustFee);
        ordFeeInfo.setOperDiscountFee(0);
        return ordFeeInfo;
    }

    /**
     * 创建发票信息
     * 
     * @param request
     * @param sysDate
     * @param orderId
     * @author zhangxw
     * @ApiDocMethod
     */
    private void createOrderFeeInvoice(OrderTradeCenterRequest request,OrdProductDetailInfo ordProductDetailInfo,
    		Timestamp sysDate,long orderId) {
    	LOG.debug("开始处理订单发票[" + orderId + "]信息..");
		/* 1.判断商品是否允许发票*/
		OrdInvoiceInfo ordInvoiceInfo = ordProductDetailInfo.getOrdInvoiceInfo();
		/* 2.判断是否选择打印发票*/
		if(ordInvoiceInfo!=null) {
			OrdOdInvoice ordInvoice=new OrdOdInvoice();
			ordInvoice.setOrderId(orderId);
			ordInvoice.setTenantId(request.getTenantId());
			ordInvoice.setInvoiceTitle(ordInvoiceInfo.getInvoiceTitle());
			ordInvoice.setInvoiceType(ordInvoiceInfo.getInvoiceType());
			ordInvoice.setInvoiceStatus(OrdersConstants.ordOdInvoice.invoiceStatus.ONE);//发票未打印
			ordInvoice.setInvoiceContent(ordInvoiceInfo.getInvoiceContent());
			ordInvoice.setInvoiceKind(ordInvoiceInfo.getInvoiceKind());
			ordInvoice.setBuyerTaxpayerNumber(ordInvoiceInfo.getBuyerTaxpayerNumber());
			ordInvoice.setBuyerBankCode(ordInvoiceInfo.getBuyerBankCode());
			ordInvoice.setBuyerBankName(ordInvoiceInfo.getBuyerBankName());
			ordInvoice.setBuyerBankAccount(ordInvoiceInfo.getBuyerBankAccount());
			ordOdInvoiceAtomSV.insertSelective(ordInvoice);
		}
    }

    /**
     * 创建订单配送信息
     * 
     * @param request
     * @param sysDate
     * @param orderId
     * @author zhangxw
     * @ApiDocMethod
     */
    private void createOrderLogistics(OrderTradeCenterRequest request, Timestamp sysDate,
            long orderId) {
    	LOG.debug("开始处理订单配送[" + orderId + "]信息..");
    	OrdLogisticsInfo ordLogisticsInfo = request.getOrdLogisticsInfo();
    	/* 1.创建配送信息*/
    	OrdOdLogistics logistics=new OrdOdLogistics();
    	long logisticsId=SequenceUtil.genLogisticsId();
    	logistics.setLogisticsId(logisticsId);
    	logistics.setTenantId(request.getTenantId());
    	logistics.setOrderId(orderId);
    	logistics.setLogisticsType(ordLogisticsInfo.getLogisticsType());
    	logistics.setContactCompany(ordLogisticsInfo.getContactCompany());
    	logistics.setContactName(ordLogisticsInfo.getContactName());
    	logistics.setContactTel(ordLogisticsInfo.getContactTel());
    	logistics.setContactEmail(ordLogisticsInfo.getContactEmail());
    	logistics.setProvinceCode(ordLogisticsInfo.getProvinceCode());
    	logistics.setCityCode(ordLogisticsInfo.getCityCode());
    	logistics.setCountyCode(ordLogisticsInfo.getCountyCode());
    	logistics.setPostcode(ordLogisticsInfo.getPostCode());
    	logistics.setAreaCode(ordLogisticsInfo.getAreaCode());
    	logistics.setAddress(ordLogisticsInfo.getAddress());
    	logistics.setExpressSelfId(ordLogisticsInfo.getExpressSelfId());
    	logistics.setLogisticsTimeId(ordLogisticsInfo.getLogisticsTime());
    	logistics.setRemark(ordLogisticsInfo.getRemark());
    	ordOdLogisticsAtomSV.insertSelective(logistics);
    }
    
    
    /**
     * 创建订单商品明细扩展信息
     * 
     * @param request
     * @param sysDate
     * @param orderId
     * @author zhangxw
     * @param prodDetailId
     * @ApiDocMethod
     */
    private void createOrdOdProdExtend(long prodDetailId, OrderTradeCenterRequest request,
            Timestamp sysDate, long orderId, String orderType,IDSSClient client) {
        if (OrdersConstants.OrdOrder.OrderType.BUG_PHONE_FLOWRATE_RECHARGE.equals(orderType)) {
            OrdExtendInfo ordExtendInfo = request.getOrdExtendInfo();
            if (ordExtendInfo == null)
                throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "请求参数商品扩展信息为空");
            String batchFlag = ordExtendInfo.getBatchFlag();
            if(StringUtil.isBlank(batchFlag))
                throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "请求参数批量标识为空");
            String infoJson = ordExtendInfo.getInfoJson();
            if (batchFlag != null) {
                if (OrdersConstants.OrdOdProdExtend.BatchFlag.YES.equals(batchFlag)) {
                    infoJson = client.save(infoJson.getBytes(), "phonenumbers");
                }
                orderFrameCoreSV.createOrdProdExtend(prodDetailId, orderId, request.getTenantId(),
                        infoJson,batchFlag);
            }

        }
    }

    /**
     * 写入订单状态变化轨迹
     * 
     * @param request
     * @param sysDate
     * @param orderId
     * @author zhangxw
     * @ApiDocMethod
     */
    private void writeOrderCreateStateChg(OrderTradeCenterRequest request, Timestamp sysDate,
            long orderId) {
        orderFrameCoreSV.ordOdStateChg(orderId, request.getTenantId(), null,
                OrdersConstants.OrdOrder.State.NEW, OrdOdStateChg.ChgDesc.ORDER_CREATE, null, null,
                null, sysDate);

    }

    /**
     * 更新订单状态
     * 
     * @param request
     * @param sysDate
     * @param orderId
     * @author zhangxw
     * @ApiDocMethod
     */
    private void updateOrderState(String tenantId, Timestamp sysDate, long orderId) {
        OrdOrder ordOrder = ordOrderAtomSV.selectByOrderId(tenantId, orderId);
        String orgState = ordOrder.getState();
        String newState = OrdersConstants.OrdOrder.State.WAIT_PAY;
        ordOrder.setState(newState);
        ordOrder.setStateChgTime(sysDate);
        ordOrderAtomSV.updateById(ordOrder);
        // 写入订单状态变化轨迹表
        orderFrameCoreSV.ordOdStateChg(orderId, tenantId, orgState, newState,
                OrdOdStateChg.ChgDesc.ORDER_TO_PAY, null, null, null, sysDate);
    }

    /**
     * 获取订单商品费用明细
     * 
     * @param orderId
     * @return
     * @throws Exception
     * @author zhangxw
     * @ApiDocMethod
     */
    private List<OrdOdProd> getOrdOdProds(String tenantId, Long orderId) throws BusinessException,
            SystemException {
        OrdOdProdCriteria example = new OrdOdProdCriteria();
        OrdOdProdCriteria.Criteria criteria = example.createCriteria();
        criteria.andTenantIdEqualTo(tenantId);
        // 添加搜索条件
        if (orderId != null && orderId.intValue() != 0) {
            criteria.andOrderIdEqualTo(orderId);
        }
        return ordOdProdAtomSV.selectByExample(example);
    }

    /**
     * 查询SKU单品信息
     * 
     * @param tenantId
     * @param skuId
     * @return
     */
    private StorageNumRes querySkuInfo(String tenantId, String skuId, int skuNum) {
        StorageNumUserReq storageNumUserReq = new StorageNumUserReq();
        storageNumUserReq.setTenantId(tenantId);
        storageNumUserReq.setSkuId(skuId);
        storageNumUserReq.setSkuNum(skuNum);
        IStorageNumSV iStorageNumSV = DubboConsumerFactory.getService(IStorageNumSV.class);
        return iStorageNumSV.useStorageNum(storageNumUserReq);
    }
}
