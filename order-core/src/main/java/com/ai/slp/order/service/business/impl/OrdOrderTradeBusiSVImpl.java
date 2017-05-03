package com.ai.slp.order.service.business.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.DateUtil;
import com.ai.platform.common.api.cache.interfaces.ICacheSV;
import com.ai.platform.common.api.cache.param.SysParam;
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
import com.ai.slp.order.constants.SearchConstants;
import com.ai.slp.order.dao.mapper.bo.OrdOdFeeProd;
import com.ai.slp.order.dao.mapper.bo.OrdOdFeeTotal;
import com.ai.slp.order.dao.mapper.bo.OrdOdInvoice;
import com.ai.slp.order.dao.mapper.bo.OrdOdLogistics;
import com.ai.slp.order.dao.mapper.bo.OrdOdProd;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.manager.ESClientManager;
import com.ai.slp.order.search.bo.OrdProdExtend;
import com.ai.slp.order.search.bo.OrderInfo;
import com.ai.slp.order.search.bo.ProdInfo;
import com.ai.slp.order.service.atom.interfaces.IOrdOdFeeProdAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdFeeTotalAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdInvoiceAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdLogisticsAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdProdAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.ai.slp.order.service.business.interfaces.IOrdOrderTradeBusiSV;
import com.ai.slp.order.util.DbUtils;
import com.ai.slp.order.util.InfoTranslateUtil;
import com.ai.slp.order.util.SequenceUtil;
import com.ai.slp.product.api.storageserver.interfaces.IStorageNumSV;
import com.ai.slp.product.api.storageserver.param.StorageNumRes;
import com.ai.slp.product.api.storageserver.param.StorageNumUserReq;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Service
@Transactional
public class OrdOrderTradeBusiSVImpl implements IOrdOrderTradeBusiSV {

    @Autowired
    private IOrdOrderAtomSV ordOrderAtomSV;
    @Autowired
    private IOrdOdProdAtomSV ordOdProdAtomSV;
    @Autowired
    private IOrdOdFeeTotalAtomSV ordOdFeeTotalAtomSV;
    @Autowired
    private IOrdOdLogisticsAtomSV ordOdLogisticsAtomSV;
    @Autowired
    private IOrdOdInvoiceAtomSV ordOdInvoiceAtomSV;
    @Autowired
    private IOrdOdFeeProdAtomSV ordOdFeeProdAtomSV;
    @Autowired
    private IOrderMonitorSV orderMonitorSV;
    
    //订单提交
    @Override
    public OrderTradeCenterResponse apply(OrderTradeCenterRequest request,	
    		OrderMonitorBeforResponse beforSubmitOrder,
    		OrderMonitorRequest monitorRequest) throws BusinessException, SystemException {
    	OrderTradeCenterResponse response = new OrderTradeCenterResponse();
        List<OrderResInfo> orderResInfos=new ArrayList<OrderResInfo>();
        Timestamp sysDate = DateUtil.getSysDate();
        List<OrdProductDetailInfo> ordProductDetailInfos = request.getOrdProductDetailInfos();
        for (OrdProductDetailInfo ordProductDetailInfo : ordProductDetailInfos) {
        	OrderResInfo orderResInfo=new OrderResInfo();
        	/* 1.创建业务订单,并返回订单Id*/
        	OrdOrder ordOrder = this.createOrder(request,beforSubmitOrder,sysDate,ordProductDetailInfo);
        	long orderId=ordOrder.getOrderId();
        	/* 2.创建商品明细,费用明细信息 */
        	Map<String, Object> mapProduct = this.createProdInfo(request,ordProductDetailInfo, sysDate, 
        			orderId);
        	/* 3.费用信息 */
        	OrdOdFeeTotal feeInfo = this.createFeeInfo(request,ordProductDetailInfo, sysDate, orderId,mapProduct);
        	/* 4.创建发票信息 */
        	OrdInvoiceInfo invoiceInfo = this.createOrderFeeInvoice(request,ordProductDetailInfo, sysDate, orderId);
        	/* 5. 处理配送信息，存在则写入 */
        	OrdOdLogistics logistics = this.createOrderLogistics(request, sysDate, orderId);
        	/* 6. 记录一条订单创建轨迹记录,并处理订单信息 */
        	this.writeOrderCreateStateChg(sysDate, ordOrder);
        	/* 7.刷新elasticsearch数据 */
        	this.insertSesData(ordOrder, feeInfo, logistics,mapProduct,invoiceInfo);
        	/* 8.订单提交成功后监控服务  */
        	orderMonitorSV.afterSubmitOrder(monitorRequest);
        	
        	/*异步处理 发送消息
    		MDSClientManager.getSenderClient(OrdersConstants.MDSNS.MDS_NS_ORDER_TOPIC).
    				send(JSON.toJSONString(monitorRequest), 0);*/
        	
        	/* 9.封装返回参数*/
        	orderResInfo.setOrderId(orderId);
        	orderResInfo.setOrdProductResList((List<OrdProductResInfo>) mapProduct.get("ordProductResList"));
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
    private OrdOrder createOrder(OrderTradeCenterRequest request,OrderMonitorBeforResponse beforSubmitOrder,
    		Timestamp sysDate,OrdProductDetailInfo ordProductDetailInfo ) {
    	OrdBaseInfo ordBaseInfo = request.getOrdBaseInfo();
    	String tenantId = request.getTenantId();
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
        //积分账户id
        ordOrder.setAccountId(DbUtils.getField(ordProductDetailInfo.getAccountId()));
        //积分令牌id
        ordOrder.setTokenId(DbUtils.getField(ordProductDetailInfo.getTokenId()));
        ordOrder.setChlId(ordBaseInfo.getChlId());
        ordOrder.setState(OrdersConstants.OrdOrder.State.NEW);
        ordOrder.setStateChgTime(sysDate);
        ordOrder.setDeliveryFlag(ordBaseInfo.getDeliveryFlag());
        ordOrder.setOrderTime(sysDate);
        ordOrder.setOrderDesc(DbUtils.getField(ordBaseInfo.getOrderDesc()));
        ordOrder.setKeywords(DbUtils.getField(ordBaseInfo.getKeywords()));
        ordOrder.setRemark(DbUtils.getField(ordProductDetailInfo.getRemark()));
        ordOrder.setSupplierId(ordProductDetailInfo.getSupplierId());
        ordOrder.setIfWarning(DbUtils.getField(beforSubmitOrder.getIfWarning()));
        ordOrder.setWarningType(DbUtils.getField(beforSubmitOrder.getWarningType()));
        ordOrder.setCusServiceFlag(OrdersConstants.OrdOrder.cusServiceFlag.NO);
        ordOrder.setFlag(DbUtils.getField(ordBaseInfo.getFlag()));
        //积分比率
        ordOrder.setPointRate(DbUtils.getField(ordProductDetailInfo.getPointRate()));
        return ordOrder;
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
    		Timestamp sysDate, long orderId) {
       // LOG.debug("开始处理订单商品明细[" + orderId + "]和订单费用明细资料信息..");
        List<OrdProductResInfo> ordProductResList = new ArrayList<OrdProductResInfo>();
        List<OrdOdProd> ordOdProds = new ArrayList<OrdOdProd>();
        Map<String,Object> mapProduct = new HashMap<String,Object>();
        List<OrdProductInfo> ordProductInfoList = ordProductDetailInfo.getOrdProductInfoList();
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
        long discountFee = 0;
        long totalJf=0;
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
            ordOdProd.setProdType(DbUtils.getField(storageNumRes.getProductCatId()));
            ordOdProd.setProdId(DbUtils.getField(storageNumRes.getProdId()));
            ordOdProd.setProdName(DbUtils.getField(storageNumRes.getSkuName()));
            ordOdProd.setSkuId(ordProductInfo.getSkuId());
            ordOdProd.setStandardProdId(storageNumRes.getStandedProdId());
            ordOdProd.setSkuStorageId(JSON.toJSONString(storageNum));
            ordOdProd.setSupplierId(ordProductDetailInfo.getSupplierId());
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
       //   ordOdProd.setProdCode(""); //商品编码
            ordOdProds.add(ordOdProd); //加入list集合中
            /* 2. 封装订单提交商品返回参数 */
            OrdProductResInfo ordProductResInfo = new OrdProductResInfo();
            ordProductResInfo.setSkuId(ordOdProd.getSkuId());
            ordProductResInfo.setSkuName(ordOdProd.getProdName());
            ordProductResInfo.setSalePrice(ordOdProd.getSalePrice());
            ordProductResInfo.setBuySum((int) ordOdProd.getBuySum());
            ordProductResInfo.setSkuTotalFee(ordOdProd.getTotalFee());
            ordProductResList.add(ordProductResInfo);
        }
        /* 更新商品明细信息*/
        for (OrdOdProd ordOdProd : ordOdProds) {
        	//积分 积分金额 优惠券 优惠金额,按比例划分
        	BigDecimal rate = BigDecimal.valueOf(ordOdProd.getTotalFee()).divide(new BigDecimal(prodTotalFee),5,BigDecimal.ROUND_HALF_UP);
        	long prodJfFee=(rate.multiply(new BigDecimal(totalJfFee))).longValue();
        	long prodCouponFee=(rate.multiply(new BigDecimal(totalCouponFee))).longValue();
        	long prodJfAmount=(rate.multiply(new BigDecimal(totallJfAmount))).longValue();
        	long prodDiscountFee=(rate.multiply(new BigDecimal(shopDiscountFee))).longValue();
        	long prodAdjustFee= ordOdProd.getTotalFee()-(prodCouponFee+prodJfAmount+prodDiscountFee);
        	ordOdProd.setJfFee(prodJfFee);
        	ordOdProd.setCouponFee(prodCouponFee);
        	ordOdProd.setDiscountFee(prodCouponFee+prodJfAmount+prodDiscountFee);
        	ordOdProd.setAdjustFee(prodAdjustFee<0?0:prodAdjustFee);
        	ordOdProdAtomSV.insertSelective(ordOdProd);
        	//计算费用总表信息
            discountFee = ordOdProd.getDiscountFee() + discountFee;
            totalJf=ordOdProd.getJf() + totalJf;
        }
        mapProduct.put("discountFee", discountFee);
        mapProduct.put("totalFee", prodTotalFee);
        mapProduct.put("totalJf", totalJf);
        mapProduct.put("ordOdProds", ordOdProds);
        mapProduct.put("totalJfFee", totalJfFee);
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
    private OrdOdFeeTotal createFeeInfo(OrderTradeCenterRequest request,OrdProductDetailInfo ordProductDetailInfo,
    		Timestamp sysDate,long orderId,Map<String, Object> mapProduct) {
    	OrdOdFeeTotal ordOdFeeTotal = new OrdOdFeeTotal();
    	long totalFee = (long) mapProduct.get("totalFee");
    	long totalJf = (long) mapProduct.get("totalJf");
    	long discountFee = (long) mapProduct.get("discountFee");
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
    //  ordOdFeeTotal.setUpdateChlId("");
    //  ordOdFeeTotal.setUpdateOperId("");
        ordOdFeeTotal.setTotalJf(totalJf);
        ordOdFeeTotal.setFreight(freight);
        ordOdFeeTotalAtomSV.insertSelective(ordOdFeeTotal);
        return ordOdFeeTotal;
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
    private OrdInvoiceInfo createOrderFeeInvoice(OrderTradeCenterRequest request,OrdProductDetailInfo ordProductDetailInfo,
    		Timestamp sysDate,long orderId) {
    	//LOG.debug("开始处理订单发票[" + orderId + "]信息..");
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
		return ordInvoiceInfo;
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
    private OrdOdLogistics createOrderLogistics(OrderTradeCenterRequest request, Timestamp sysDate,
            long orderId) {
    	//LOG.debug("开始处理订单配送[" + orderId + "]信息..");
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
    	return logistics;
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
    private void writeOrderCreateStateChg(Timestamp sysDate,OrdOrder ordOrder) {
     //   String orgState = ordOrder.getState();
        String newState = OrdersConstants.OrdOrder.State.WAIT_PAY;
        ordOrder.setState(newState);
    //  ordOrder.setStateChgTime(sysDate);
        ordOrderAtomSV.insertSelective(ordOrder);
        //异步  写入订单状态变化轨迹表
      /*  OrderStateChgUtil.trailProcess(ordOrder.getOrderId(),
        		ordOrder.getTenantId(), orgState, newState,
                OrdOdStateChg.ChgDesc.ORDER_TO_PAY, null, null, null, sysDate);*/
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
    
    /**
     * 刷新elasticsearch数据
     * 
     * @author caofz
     * @ApiDocMethod
     * @ApiCode 
     * @RestRelativeURL
     */
    private void insertSesData(OrdOrder ordOrder,OrdOdFeeTotal feeInfo,
    		OrdOdLogistics logistics,Map<String, Object> mapProduct,OrdInvoiceInfo invoiceInfo) {
    	ICacheSV iCacheSV = DubboConsumerFactory.getService(ICacheSV.class);
    	List<OrderInfo> orderList = new ArrayList<OrderInfo>();
    	List<OrdProdExtend> prodExtends=new ArrayList<OrdProdExtend>();
    	String tenantId = ordOrder.getTenantId();
    	List<OrdOdProd> ordOdProds =  (List<OrdOdProd>) mapProduct.get("ordOdProds");
    	long totalJfFee = (long) mapProduct.get("totalJfFee");
    	//组装共有数据
    	OrderInfo ordInfo = packageCommonData(ordOrder, feeInfo, logistics, 
    			invoiceInfo, totalJfFee, iCacheSV);
    	//
		List<ProdInfo> prodInfos=new ArrayList<ProdInfo>();
		OrdProdExtend prodExtend=new OrdProdExtend();
		prodExtend.setState(ordOrder.getState());
		//订单状态翻译
		SysParam sysParamState = InfoTranslateUtil.translateInfo(tenantId,
				"ORD_ORDER", "STATE",ordOrder.getState(), iCacheSV);
		prodExtend.setStatename(sysParamState == null ? "" : sysParamState.getColumnDesc());
		prodExtend.setBusicode(ordOrder.getBusiCode());
		prodExtend.setOrderid(ordOrder.getOrderId());
		//
		prodExtend.setRemark(ordOrder.getRemark());
		//订单费用信息
		prodExtend.setTotalfee(feeInfo.getTotalFee());
		prodExtend.setDiscountfee(feeInfo.getDiscountFee());
		prodExtend.setAdjustfee(feeInfo.getAdjustFee());
		prodExtend.setFreight(feeInfo.getFreight());
		// 查询商品信息
		for (OrdOdProd ordOdProd : ordOdProds) {
			ProdInfo prodInfo=new ProdInfo();
			prodInfo.setBuysum(ordOdProd.getBuySum());
			prodInfo.setProdname(ordOdProd.getProdName());
			prodInfo.setSaleprice(ordOdProd.getSalePrice());
			prodInfo.setCouponfee(ordOdProd.getCouponFee());
			prodInfo.setJffee(ordOdProd.getJfFee());
			prodInfo.setGivejf(ordOdProd.getJf());
			prodInfo.setCusserviceflag(ordOdProd.getCusServiceFlag());
			prodInfo.setState(ordOdProd.getState());
			prodInfo.setProdcode(ordOdProd.getProdCode());
			prodInfo.setSkuid(ordOdProd.getSkuId());
			prodInfo.setProddetalid(ordOdProd.getProdDetalId());
			prodInfo.setSkustorageid(ordOdProd.getSkuStorageId());
			prodInfo.setProdcode(ordOdProd.getProdCode());
			prodInfos.add(prodInfo);
		}
		prodExtend.setProdsize(prodInfos.size());
		int totalprodsize=prodInfos.size();
		prodExtend.setProdinfos(prodInfos);
		prodExtends.add(prodExtend);
		ordInfo.setTotalprodsize(totalprodsize);
		ordInfo.setOrdextendes(prodExtends);
		orderList.add(ordInfo);
		try {
			ESClientManager.getSesClient(SearchConstants.SearchNameSpace).bulkInsert(orderList);
		} catch (Exception e) {
			 throw new SystemException("","订单信息加入搜索引擎失败,订单ID:"+ordOrder.getOrderId());
		}
    }
    
    
    /**
     * 组装es中共有数据
     */
    private OrderInfo packageCommonData(OrdOrder ordOrder,
    		OrdOdFeeTotal feeInfo,OrdOdLogistics logistics,
    		OrdInvoiceInfo invoiceInfo,long totalJfFee,ICacheSV iCacheSV) {
    	OrderInfo ordInfo = new OrderInfo();
    	ordInfo.setChlid(ordOrder.getChlId());
		ordInfo.setPorderid( ordOrder.getOrderId());
		ordInfo.setUsername( ordOrder.getUserName());
		ordInfo.setUsertel( ordOrder.getUserTel());
		ordInfo.setDeliveryflag( ordOrder.getDeliveryFlag());
		ordInfo.setFlag( ordOrder.getFlag());
		//翻译是否需要物流
		SysParam sysParamDf = InfoTranslateUtil.translateInfo(ordOrder.getTenantId(), "ORD_ORDER",
				"ORD_DELIVERY_FLAG",  ordOrder.getDeliveryFlag(), iCacheSV);
		ordInfo.setDeliveryflagname(sysParamDf == null ? "" : sysParamDf.getColumnDesc());
		ordInfo.setOrdertime( ordOrder.getOrderTime());
		ordInfo.setParentorderstate( ordOrder.getState());
		ordInfo.setSupplierid( ordOrder.getSupplierId());
		ordInfo.setContacttel(logistics.getContactTel());
		ordInfo.setIfwarning(ordOrder.getIfWarning());
		ordInfo.setWarningtype(ordOrder.getWarningType());
		ordInfo.setPoints(totalJfFee);
		ordInfo.setAdjustfee(feeInfo.getAdjustFee());
		ordInfo.setDiscountfee(feeInfo.getDiscountFee());
		//订单详情
		ordInfo.setAccountid(ordOrder.getAccountId());
		ordInfo.setUserid(ordOrder.getUserId());
		ordInfo.setOrdertype(ordOrder.getOrderType());
		ordInfo.setToken(ordOrder.getTokenId());
		ordInfo.setPaystyle(feeInfo.getPayStyle());
		ordInfo.setInvoicetype(invoiceInfo.getInvoiceType());
		ordInfo.setInvoicetitle(invoiceInfo.getInvoiceTitle());
		ordInfo.setInvoicecontent(invoiceInfo.getInvoiceContent());
		ordInfo.setBuyertaxpayernumber(invoiceInfo.getBuyerTaxpayerNumber());
		ordInfo.setBuyerbankname(invoiceInfo.getBuyerBankName());
		ordInfo.setBuyerbankaccount(invoiceInfo.getBuyerBankAccount());
		ordInfo.setExpressoddnumber(logistics.getExpressOddNumber());
		ordInfo.setContactcompany(logistics.getContactCompany());
		ordInfo.setContactname(logistics.getContactName());
		ordInfo.setLogisticstype(logistics.getLogisticsType());
		ordInfo.setProvincecode(logistics.getProvinceCode() == null ? ""
				: iCacheSV.getAreaName(logistics.getProvinceCode()));
		ordInfo.setCitycode(logistics.getCityCode() == null ? ""
				: iCacheSV.getAreaName(logistics.getCityCode()));
		ordInfo.setCountycode(logistics.getCountyCode() == null ? ""
				: iCacheSV.getAreaName(logistics.getCountyCode()));
		ordInfo.setPostcode(logistics.getPostcode());
		ordInfo.setAreacode(logistics.getAreaCode() == null ? ""
				: iCacheSV.getAreaName(logistics.getAreaCode()));
		ordInfo.setAddress(logistics.getAddress());
		return ordInfo;
    }
}
