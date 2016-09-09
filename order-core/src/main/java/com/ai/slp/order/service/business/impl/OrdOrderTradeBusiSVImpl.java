package com.ai.slp.order.service.business.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import com.ai.slp.order.api.ordertradecenter.param.OrdBaseInfo;
import com.ai.slp.order.api.ordertradecenter.param.OrdExtendInfo;
import com.ai.slp.order.api.ordertradecenter.param.OrdFeeInfo;
import com.ai.slp.order.api.ordertradecenter.param.OrdFeeTotalProdInfo;
import com.ai.slp.order.api.ordertradecenter.param.OrdInvoiceInfo;
import com.ai.slp.order.api.ordertradecenter.param.OrdLogisticsInfo;
import com.ai.slp.order.api.ordertradecenter.param.OrdProductInfo;
import com.ai.slp.order.api.ordertradecenter.param.OrdProductResInfo;
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
import com.ai.slp.order.util.SequenceUtil;
import com.ai.slp.order.vo.ProdAttrInfoVo;
import com.ai.slp.product.api.storageserver.interfaces.IStorageNumSV;
import com.ai.slp.product.api.storageserver.param.StorageNumRes;
import com.ai.slp.product.api.storageserver.param.StorageNumUserReq;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Service
@Transactional
public class OrdOrderTradeBusiSVImpl implements IOrdOrderTradeBusiSV {

    private static final Log LOG = LogFactory.getLog(OrdOrderTradeBusiSVImpl.class);

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

    @Override
    public OrderTradeCenterResponse apply(OrderTradeCenterRequest request)
            throws BusinessException, SystemException {
    	LOG.info("商品下单处理......");
        OrderTradeCenterResponse response = new OrderTradeCenterResponse();
        IDSSClient client = DSSClientFactory.getDSSClient(OrdersConstants.ORDER_PHONENUM_DSS);
        long orderId =0;
        List<OrdProductResInfo> ordProductResList =null;
        OrdFeeInfo ordFeeInfo =null;
        Timestamp sysDate = DateUtil.getSysDate();
    	List<OrdProductInfo> productInfoList = request.getOrdProductInfoList();
    	/* 商品个数为1的时候,即销售商Id为一个的情况下*/
    	if(productInfoList.size()==1) {  
    		/* 创建业务订单,并返回订单Id*/
    		orderId = this.createOrder(request, sysDate);
    		/* 创建商品明细信息 */
            ordProductResList = this.createOrderProd(request, sysDate, orderId,client);
            /* 创建其它信息*/
            ordFeeInfo = this.createOrderRelatedInfo(request, sysDate, orderId);
    	}else { 
    		/* 销售商Id为多个的情况下*/
    		Map<Long, Long> orderMap=new HashMap<Long,Long>();
    		for (OrdProductInfo ordProductInfo : productInfoList) {
    			if(!orderMap.containsKey(ordProductInfo.getSupplierId())) {
    				/* 创建业务订单,并返回订单Id*/
    				orderId = this.createOrder(request, sysDate);
    				/* 不包含的话,销售商Id和订单Id存入orderMap集合中*/
    				orderMap.put(ordProductInfo.getSupplierId(), orderId);
    				/* 创建商品明细信息 */
    		        ordProductResList = this.createOrderProd(request, sysDate, orderId,client);
    		        /* 创建其它信息*/
    		        ordFeeInfo=this.createOrderRelatedInfo(request, sysDate, orderId);
    			}else if(ordProductInfo.getSupplierId()!=null && orderMap.containsKey(ordProductInfo.getSupplierId())){
    				/* orderMap集合中包含的话,取出订单Id*/
    				orderId = orderMap.get(ordProductInfo.getSupplierId());
    		        ordProductResList = this.createOrderProd(request, sysDate, orderId,client);
    		        ordFeeInfo=this.createOrderRelatedInfo(request, sysDate, orderId);
    			}
			}
    	}
        /* 封装返回参数 */
        response.setOrderId(orderId);
        response.setOrdFeeInfo(ordFeeInfo);
        response.setOrdProductResList(ordProductResList);
        return response;
    }
    
    
    private OrdFeeInfo createOrderRelatedInfo(OrderTradeCenterRequest request, Timestamp sysDate,long orderId) {
    	/* 1.费用信息处理 */
    	OrdFeeInfo ordFeeInfo = this.createFeeInfo(request, sysDate, orderId);
    	/* 2.创建发票信息 */
    	this.createOrderFeeInvoice(request, sysDate, orderId);
    	/* 3. 处理配送信息，存在则写入 */
    	this.createOrderLogistics(request, sysDate, orderId);
    	/* 4. 记录一条订单创建轨迹记录 */
    	this.writeOrderCreateStateChg(request, sysDate, orderId);
    	/* 5. 更新订单状态 */
    	this.updateOrderState(request.getTenantId(), sysDate, orderId);
        return ordFeeInfo;
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
    private long createOrder(OrderTradeCenterRequest request, Timestamp sysDate) {
        //LOG.debug("开始处理订单主表[" + orderId + "]资料信息..");
        OrdBaseInfo ordBaseInfo = request.getOrdBaseInfo();
        if (StringUtil.isBlank(ordBaseInfo.getOrderType())) {
            throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "订单类型为空");
        }
        if (StringUtil.isBlank(ordBaseInfo.getUserId())) {
            throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "用户Id为空");
        }
        if(StringUtil.isBlank(ordBaseInfo.getUserType())) {
        	throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "用户类型为空");
        }
        OrdOrder ordOrder = new OrdOrder();
        long orderId = SequenceUtil.createOrderId();
        ordOrder.setOrderId(orderId);
        ordOrder.setTenantId(request.getTenantId());
        ordOrder.setBusiCode(OrdersConstants.OrdOrder.BusiCode.NORMAL_ORDER);
        ordOrder.setOrderType(ordBaseInfo.getOrderType());
        ordOrder.setSubFlag(OrdersConstants.OrdOrder.SubFlag.NO);
        ordOrder.setUserId(ordBaseInfo.getUserId());
        ordOrder.setUserType(ordBaseInfo.getUserType());
        ordOrder.setIpAddress(ordBaseInfo.getIpAddress());
        ordOrder.setProvinceCode(ordBaseInfo.getProvinceCode());
        ordOrder.setCityCode(ordBaseInfo.getCityCode());
        if(StringUtil.isBlank(ordBaseInfo.getChlId())) {
        	ordOrder.setChlId(OrdersConstants.OrdOrder.ChlId.SELFOPERATION); //自运营
        }
        ordOrder.setState(OrdersConstants.OrdOrder.State.NEW);
        ordOrder.setStateChgTime(sysDate);
        ordOrder.setDisplayFlag(OrdersConstants.OrdOrder.DisplayFlag.USER_NORMAL_VISIABLE);
        ordOrder.setDisplayFlagChgTime(sysDate);
        ordOrder.setDeliveryFlag(OrdersConstants.OrdOrder.DeliveryFlag.NONE);
        ordOrder.setOrderTime(sysDate);
        ordOrder.setOrderDesc(ordBaseInfo.getOrderDesc());
        ordOrder.setKeywords(ordBaseInfo.getKeywords());
        ordOrder.setRemark(ordBaseInfo.getRemark());
        ordOrder.setCusServiceFlag(OrdersConstants.OrdOrder.cusServiceFlag.NO);
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
    private List<OrdProductResInfo> createOrderProd(OrderTradeCenterRequest request,
            Timestamp sysDate, long orderId, IDSSClient client) {
        LOG.debug("开始处理订单商品明细[" + orderId + "]资料信息..");
        /* 1.创建费用明细信息*/
        List<OrdFeeTotalProdInfo> totalProdInfos = request.getOrdFeeTotalProdInfo();
        long totalCouponFee=0;
		long totalJfFee=0;
        for (OrdFeeTotalProdInfo ordFeeTotalProdInfo : totalProdInfos) {
			OrdOdFeeProd feeProd=new OrdOdFeeProd();
			feeProd.setOrderId(orderId);
			String payStyle = ordFeeTotalProdInfo.getPayStyle();
			if(OrdersConstants.OrdOdFeeProd.PayStyle.COUPON.equals(payStyle)) {
				totalCouponFee=ordFeeTotalProdInfo.getPaidFee();
			}
			if(OrdersConstants.OrdOdFeeProd.PayStyle.JF.equals(payStyle)) {
				totalJfFee=ordFeeTotalProdInfo.getPaidFee();
			}
			feeProd.setPayStyle(ordFeeTotalProdInfo.getPayStyle());
			feeProd.setPaidFee(ordFeeTotalProdInfo.getPaidFee());
        	ordOdFeeProdAtomSV.insertSelective(feeProd);
		}
        /* 2. 创建商品明细 */
        OrdBaseInfo ordBaseInfo = request.getOrdBaseInfo();
        String orderType = ordBaseInfo.getOrderType();
        List<OrdProductResInfo> ordProductResList = new ArrayList<OrdProductResInfo>();
        List<OrdProductInfo> ordProductInfoList = request.getOrdProductInfoList();
        long jfFee=totalJfFee/ordProductInfoList.size();
        long couponFee=totalCouponFee/ordProductInfoList.size();
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
            ordOdProd.setState(OrdersConstants.OrdOdProd.State.SELL);
            ordOdProd.setBuySum(ordProductInfo.getBuySum());
            ordOdProd.setSalePrice(storageNumRes.getSalePrice());
            ordOdProd.setTotalFee(storageNumRes.getSalePrice() * ordProductInfo.getBuySum());
            ordOdProd.setDiscountFee(0);
            ordOdProd.setOperDiscountFee(0);
            ordOdProd.setOperDiscountDesc("");
            ordOdProd.setCusServiceFlag(OrdersConstants.OrdOrder.cusServiceFlag.NO);;
            ordOdProd.setAdjustFee(0);
            ProdAttrInfoVo vo = new ProdAttrInfoVo();
            vo.setBasicOrgId(ordProductInfo.getBasicOrgId());
            vo.setProvinceCode(ordProductInfo.getProvinceCode());
            vo.setChargeFee(ordProductInfo.getChargeFee());
            ordOdProd.setProdDesc("");
            ordOdProd.setExtendInfo(JSON.toJSONString(vo));
            ordOdProd.setUpdateTime(sysDate);
            ordOdProd.setJfFee(jfFee);
            ordOdProd.setCouponFee(couponFee);
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
        return ordProductResList;
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
    private OrdFeeInfo createFeeInfo(OrderTradeCenterRequest request, Timestamp sysDate,
            long orderId) {
        /* 1. 费用入总表 */
        List<OrdOdProd> ordOdProds = this.getOrdOdProds(request.getTenantId(), orderId);
        if (CollectionUtil.isEmpty(ordOdProds)) {
            throw new BusinessException("", "订单商品明细不存在[订单ID:" + orderId + "]");
        }
        OrdExtendInfo ordExtendInfo = request.getOrdExtendInfo();
        String infoJson = ordExtendInfo.getInfoJson();
        JSONObject object = JSON.parseObject(infoJson);
        long totalFee = (long) object.get("totalFee"); //传过来的总费用
        long adjustFee = (long) object.get("adjustFee"); //应收费用
        long operDiscountFee = (long) object.get("operDiscountFee"); //减免费用
        long freightFee = (long) object.get("freight"); //减免费用
        String operDiscountDesc = (String) object.get("operDiscountDesc"); //减免费用原因
        OrdOdFeeTotal ordOdFeeTotal = new OrdOdFeeTotal();
        ordOdFeeTotal.setOrderId(orderId);
        ordOdFeeTotal.setTenantId(request.getTenantId());
        ordOdFeeTotal.setPayFlag(OrdersConstants.OrdOdFeeTotal.payFlag.IN);
        ordOdFeeTotal.setTotalFee(totalFee);
        ordOdFeeTotal.setDiscountFee(totalFee-adjustFee);
        ordOdFeeTotal.setOperDiscountFee(operDiscountFee);
        ordOdFeeTotal.setOperDiscountDesc(operDiscountDesc);
        ordOdFeeTotal.setAdjustFee(adjustFee);
        ordOdFeeTotal.setPaidFee(0);
        ordOdFeeTotal.setPayFee(adjustFee);
        ordOdFeeTotal.setUpdateTime(sysDate);
        ordOdFeeTotal.setUpdateChlId("");
        ordOdFeeTotal.setUpdateOperId("");
        ordOdFeeTotal.setTotalJf(0l);
        ordOdFeeTotal.setFreight(freightFee);
        ordOdFeeTotalAtomSV.insertSelective(ordOdFeeTotal);
        /* 2. 封装返回参数 */
        OrdFeeInfo ordFeeInfo = new OrdFeeInfo();
        ordFeeInfo.setTotalFee(totalFee);
        ordFeeInfo.setDiscountFee(operDiscountFee);
        ordFeeInfo.setOperDiscountFee(operDiscountFee);
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
    private void createOrderFeeInvoice(OrderTradeCenterRequest request, Timestamp sysDate,
            long orderId) {
    	LOG.debug("开始处理订单发票[" + orderId + "]信息..");
		/* 1.判断商品是否允许发票*/
		OrdInvoiceInfo ordInvoiceInfo = request.getOrdInvoiceInfo();
		/* 2.判断是否选择打印发票*/
		if(ordInvoiceInfo==null) {
			return;
		}else {
			/* 3.参数校验*/
			if(StringUtil.isBlank(ordInvoiceInfo.getInvoiceType())) {
				throw new BusinessException("", "在打印发票的情况下,发票类型不能为空");
			}
			if(StringUtil.isBlank(ordInvoiceInfo.getInvoiceTitle())) {
				throw new BusinessException("", "在打印发票的情况下,发票抬头不能为空");
			}
			if(StringUtil.isBlank(ordInvoiceInfo.getInvoiceContent())) {
				throw new BusinessException("", "在打印发票的情况下,发票内容不能为空");
			}
		}
		OrdOdInvoice ordInvoice=new OrdOdInvoice();
		ordInvoice.setOrderId(orderId);
		ordInvoice.setTenantId(request.getTenantId());
		ordInvoice.setInvoiceTitle(ordInvoiceInfo.getInvoiceTitle());
		ordInvoice.setInvoiceType(ordInvoiceInfo.getInvoiceType());
		ordInvoice.setInvoiceContent(ordInvoiceInfo.getInvoiceContent());
		ordOdInvoiceAtomSV.insertSelective(ordInvoice);
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
    	/* 1.参数检验*/
    	this.checkLogisticsInfo(ordLogisticsInfo);
    	/* 2.创建配送信息*/
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
    	//TODO
    	//直接存入编码 ?
    	logistics.setProvinceCode(ordLogisticsInfo.getProvinceCode());
    	logistics.setCityCode(ordLogisticsInfo.getCityCode());
    	logistics.setCountyCode(ordLogisticsInfo.getCountyCode());
    	logistics.setPostcode(ordLogisticsInfo.getPostCode());
    	logistics.setAreaCode(ordLogisticsInfo.getAreaCode());
    	logistics.setAddress(ordLogisticsInfo.getAddress());
    	logistics.setExpressId(ordLogisticsInfo.getExpressId());
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
        if (orderId.intValue() != 0 && orderId != null) {
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
    
    /**
     * 配送信息校验
     */
    private void checkLogisticsInfo(OrdLogisticsInfo ordLogisticsInfo) {
    	if(ordLogisticsInfo==null) {
    		throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "参数对象不能为空");
    	}
    	if(StringUtil.isBlank(ordLogisticsInfo.getLogisticsType())) {
    		throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "配送方式不能为空");
    	}
    	if(StringUtil.isBlank(ordLogisticsInfo.getContactName())) {
    		throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "收件人姓名不能为空");
    	}
    	if(StringUtil.isBlank(ordLogisticsInfo.getContactTel())) {
    		throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "收件人电话不能为空");
    	}
    	if(StringUtil.isBlank(ordLogisticsInfo.getProvinceCode())) {
    		throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "收件人省份不能为空");
    	}
    	if(StringUtil.isBlank(ordLogisticsInfo.getCityCode())) {
    		throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "收件人地市不能为空");
    	}
    	if(StringUtil.isBlank(ordLogisticsInfo.getCountyCode())) {
    		throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "收件人区县不能为空");
    	}
    	if(StringUtil.isBlank(ordLogisticsInfo.getPostCode())) {
    		throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "收件人邮编不能为空");
    	}
    	if(StringUtil.isBlank(ordLogisticsInfo.getAddress())) {
    		throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "详细地址不能为空");
    	}
    	if(StringUtil.isBlank(ordLogisticsInfo.getExpressId())) {
    		throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "物流公司ID不能为空");
    	}
    }
}
