package com.ai.slp.order.service.business.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.util.DateUtil;
import com.ai.paas.ipaas.util.StringUtil;
import com.ai.slp.balance.api.deduct.interfaces.IDeductSV;
import com.ai.slp.balance.api.deduct.param.DeductParam;
import com.ai.slp.balance.api.deduct.param.DeductResponse;
import com.ai.slp.balance.api.deduct.param.TransSummary;
import com.ai.slp.common.api.servicenum.interfaces.IServiceNumSV;
import com.ai.slp.common.api.servicenum.param.ServiceNum;
import com.ai.slp.order.api.orderpay.interfaces.IOrderPaySV;
import com.ai.slp.order.api.orderpay.param.OrderPayRequest;
import com.ai.slp.order.api.ordertradecenter.param.OrderApiTradeCenterRequest;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.constants.OrdersConstants.OrdOdStateChg;
import com.ai.slp.order.constants.ResultCodeConstants;
import com.ai.slp.order.dao.mapper.bo.OrdOdFeeTotal;
import com.ai.slp.order.dao.mapper.bo.OrdOdProd;
import com.ai.slp.order.dao.mapper.bo.OrdOdProdCriteria;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.dao.mapper.bo.OrdOrderCriteria;
import com.ai.slp.order.service.atom.interfaces.IOrdOdFeeTotalAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdProdAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.ai.slp.order.service.business.interfaces.IOrdOrderApiTradeBusiSV;
import com.ai.slp.order.service.business.interfaces.IOrderFrameCoreSV;
import com.ai.slp.order.util.SequenceUtil;
import com.ai.slp.order.vo.InfoJsonVo;
import com.ai.slp.order.vo.ProdAttrInfoVo;
import com.ai.slp.order.vo.ProdExtendInfoVo;
import com.ai.slp.product.api.storageserver.interfaces.IStorageNumSV;
import com.ai.slp.product.api.storageserver.param.StorageNumRes;
import com.ai.slp.product.api.storageserver.param.StorageNumUseReq;
import com.alibaba.fastjson.JSON;

import sun.util.logging.resources.logging;

@Service
@Transactional
public class OrdOrderApiTradeBusiSVImpl implements IOrdOrderApiTradeBusiSV {

    private static final Logger logger =LoggerFactory.getLogger(OrdOrderApiTradeBusiSVImpl.class);

    @Autowired
    private IOrdOrderAtomSV ordOrderAtomSV;

    @Autowired
    private IOrdOdProdAtomSV ordOdProdAtomSV;

    @Autowired
    private IOrdOdFeeTotalAtomSV ordOdFeeTotalAtomSV;

    @Autowired
    private IOrderFrameCoreSV orderFrameCoreSV;

    @Autowired
    private IOrderPaySV iOrderPaySV;

    @Override
    public BaseResponse apiApply(OrderApiTradeCenterRequest request) throws BusinessException,
            SystemException {
        BaseResponse response = new BaseResponse();
        /* 1.校验必填项*/
        this.checkParamRequired(request);
        /* 2.校验订单 */
        this.checkOrder(request.getTenantId(), request.getDownstreamOrderId());
        /* 3.调用商品,使用库存 */
        StorageNumRes querySkuInfo = this.querySkuInfo(request);
        /* 4.创建业务订单信息 */
        long orderId = SequenceUtil.createOrderId();
        logger.debug("开始处理-订单号[" + orderId + "]订单提交..");
        Timestamp sysDate = DateUtil.getSysDate();
        this.createOrder(request, sysDate, orderId);
        /* 5.创建商品明细信息 */
        this.createOrderProd(request, sysDate, orderId, querySkuInfo);
        /* 6.费用信息处理 */
        long payFee = this.createFeeInfo(request, sysDate, orderId);
        /* 7. 记录一条订单创建轨迹记录 */
        this.writeOrderCreateStateChg(request, sysDate, orderId);
        /* 8. 更新订单状态 */
        this.updateOrderState(request.getTenantId(), sysDate, orderId);
        /* 9. 订单支付 */
        this.deductFund(request, payFee, orderId);
        return response;
    }
    
    
    /**
     * 参数必填项校验
     */
    private void checkParamRequired(OrderApiTradeCenterRequest request) {
    	logger.info("参数校验...");
    	if (StringUtil.isBlank(request.getUserId())) {
    		throw new BusinessException(ResultCodeConstants.ApiOrder.REQUIRED_IS_EMPTY, "用户Id为空");
    	}
    	if (StringUtil.isBlank(request.getSkuId())) {
    		throw new BusinessException(ResultCodeConstants.ApiOrder.REQUIRED_IS_EMPTY, "skuId为空");
    	}
        if(StringUtil.isBlank(request.getBuySum()+"")) {
        	throw new BusinessException(ResultCodeConstants.ApiOrder.REQUIRED_IS_EMPTY,"购买数量不能为空"); 
        }
        if(StringUtil.isBlank(request.getInfoJson())) {
        	throw new BusinessException(ResultCodeConstants.ApiOrder.REQUIRED_IS_EMPTY,"手机号信息为空"); 
        }
        if(StringUtil.isBlank(request.getChargeFee())) {
        	throw new BusinessException(ResultCodeConstants.ApiOrder.REQUIRED_IS_EMPTY,"充值面额为空"); 
        }
        if(StringUtil.isBlank(request.getOrderType())) {
        	throw new BusinessException(ResultCodeConstants.ApiOrder.REQUIRED_IS_EMPTY,"订单类型为空"); 
        }
    }
    
    /**
     * 对订单进行校验
     * 
     * @param tenantId
     * @param downstreamOrderId
     * @author zhangxw
     * @ApiDocMethod
     */
    private void checkOrder(String tenantId, String downstreamOrderId) {
        if(StringUtil.isBlank(tenantId)) {
        	throw new BusinessException(ResultCodeConstants.ApiOrder.REQUIRED_IS_EMPTY, "租户Id为空");
        }
        if(StringUtil.isBlank(downstreamOrderId)) {
        	throw new BusinessException(ResultCodeConstants.ApiOrder.REQUIRED_IS_EMPTY, "下游订单为空");
        }
        OrdOrderCriteria example = new OrdOrderCriteria();
        OrdOrderCriteria.Criteria criteria = example.createCriteria();
        criteria.andTenantIdEqualTo(tenantId);
        criteria.andDownstreamOrderIdEqualTo(downstreamOrderId);
        List<OrdOrder> list = ordOrderAtomSV.selectByExample(example);
        if (!CollectionUtil.isEmpty(list)) {
            throw new BusinessException(ResultCodeConstants.ApiOrder.ORDER_REPEAT, "订单已经存在[downstreamOrderId:" + downstreamOrderId
                    + "]");
        }
    }

    /**
     * 查询SKU单品信息
     * 
     * @param tenantId
     * @param skuId
     * @return
     */
    private StorageNumRes querySkuInfo(OrderApiTradeCenterRequest request) {
        StorageNumUseReq storageNumUseReq = new StorageNumUseReq();
        storageNumUseReq.setTenantId(request.getTenantId());
        storageNumUseReq.setSkuId(request.getSkuId());
        storageNumUseReq.setSkuNum(request.getBuySum());
        storageNumUseReq.setUserId(request.getUserId());
        storageNumUseReq.setUserType(request.getUserType());
        storageNumUseReq.setSalePrice(request.getSalePrice());
        IStorageNumSV iStorageNumSV = DubboConsumerFactory.getService(IStorageNumSV.class);
        StorageNumRes useStorageNum = iStorageNumSV.useStorageNum(storageNumUseReq);
        if (useStorageNum == null)
            throw new BusinessException(ResultCodeConstants.ApiOrder.PROD_NO_EXIST, 
            		"商品信息不存在[skuId:" + request.getSkuId() + "]");
        ResponseHeader responseHeader = useStorageNum.getResponseHeader();
        boolean success = responseHeader.isSuccess();
        if (!success) {
            String resultCode = "21" + responseHeader.getResultCode();
            throw new BusinessException(resultCode, responseHeader.getResultMessage());
        }
        return useStorageNum;

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
    private void createOrder(OrderApiTradeCenterRequest request, Timestamp sysDate, long orderId) {
    	logger.debug("开始处理订单主表[" + orderId + "]资料信息..");
        OrdOrder ordOrder = new OrdOrder();
        ordOrder.setOrderId(orderId);
        ordOrder.setTenantId(request.getTenantId());
        ordOrder.setBusiCode(OrdersConstants.OrdOrder.BusiCode.NORMAL_ORDER);
        ordOrder.setOrderType(request.getOrderType());
        ordOrder.setSubFlag(OrdersConstants.OrdOrder.SubFlag.NO);
        ordOrder.setUserId(request.getUserId());
        ordOrder.setUserType(request.getUserType());
        ordOrder.setProvinceCode("");
        ordOrder.setCityCode("");
        ordOrder.setState(OrdersConstants.OrdOrder.State.NEW);
        ordOrder.setStateChgTime(sysDate);
        ordOrder.setDownstreamOrderId(request.getDownstreamOrderId());
        ordOrder.setDisplayFlag(OrdersConstants.OrdOrder.DisplayFlag.USER_NORMAL_VISIABLE);
        ordOrder.setDisplayFlagChgTime(sysDate);
        ordOrder.setDeliveryFlag(OrdersConstants.OrdOrder.DeliveryFlag.NONE);
        ordOrder.setLockTime(DateUtil.getTimestamp(request.getOrderTime()));
        ordOrder.setOrderTime(sysDate);
        ordOrder.setOrderDesc("");
        ordOrder.setKeywords("");
        ordOrder.setRemark("");
        ordOrderAtomSV.insertSelective(ordOrder);
    }

    /**
     * 创建商品明细信息
     * 
     * @param request
     * @param sysDate
     * @param orderId
     * @author zhangxw
     * @param querySkuInfo
     * @ApiDocMethod
     */
    private void createOrderProd(OrderApiTradeCenterRequest request, Timestamp sysDate,
            long orderId, StorageNumRes storageNumRes) {
    	logger.debug("开始处理订单商品明细[" + orderId + "]资料信息..");
        /* 1. 创建商品明细 */
        String infoJson = request.getInfoJson();
        IServiceNumSV serviceNumSV = DubboConsumerFactory.getService(IServiceNumSV.class);
        ServiceNum serviceNumByPhone = serviceNumSV.getServiceNumByPhone(infoJson);
        if(serviceNumByPhone==null){
            throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "手机号"+infoJson+"没有找到相应的省份信息");
        }
        String orderType = request.getOrderType();
        Map<String, Integer> storageNum = storageNumRes.getStorageNum();
        if (storageNum == null) {
            throw new BusinessException(ResultCodeConstants.ApiOrder.SKU_NO_EXIST, "商品库存为空");
        }
        long prodDetailId = SequenceUtil.createProdDetailId();
        OrdOdProd ordOdProd = new OrdOdProd();
        ordOdProd.setProdDetalId(prodDetailId);
        ordOdProd.setTenantId(request.getTenantId());
        ordOdProd.setOrderId(orderId);
        ordOdProd.setProdType(storageNumRes.getProductCatId());
        ordOdProd.setProdId(storageNumRes.getProdId());
        ordOdProd.setProdName(storageNumRes.getSkuName());
        ordOdProd.setSkuId(request.getSkuId());
        ordOdProd.setStandardProdId(storageNumRes.getStandedProdId());
        ordOdProd.setSkuStorageId(JSON.toJSONString(storageNum));
        ordOdProd.setValidTime(sysDate);
        ordOdProd.setInvalidTime(DateUtil.getFutureTime());
        ordOdProd.setState(OrdersConstants.OrdOdProd.State.SELL);
        ordOdProd.setBuySum(request.getBuySum());
        ordOdProd.setSalePrice(storageNumRes.getSalePrice());
        ordOdProd.setTotalFee(storageNumRes.getSalePrice() * request.getBuySum());
        ordOdProd.setDiscountFee(0);
        ordOdProd.setOperDiscountFee(0);
        ordOdProd.setOperDiscountDesc("");
        ordOdProd.setAdjustFee(ordOdProd.getTotalFee() - ordOdProd.getDiscountFee()
                - ordOdProd.getOperDiscountFee());
        ProdAttrInfoVo vo = new ProdAttrInfoVo();
        vo.setBasicOrgId(serviceNumByPhone.getBasicOrgCode());
        vo.setProvinceCode(serviceNumByPhone.getProvinceCode());
        vo.setChargeFee(request.getChargeFee());
        ordOdProd.setProdDesc("");
        ordOdProd.setExtendInfo(JSON.toJSONString(vo));
        ordOdProd.setUpdateTime(sysDate);
        ordOdProdAtomSV.insertSelective(ordOdProd);
        /* 3. 创建商品明细扩展表 */
        this.createOrdOdProdExtend(prodDetailId, request, orderId, orderType);

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
    private void createOrdOdProdExtend(long prodDetailId, OrderApiTradeCenterRequest request,
            long orderId, String orderType) {
        if (OrdersConstants.OrdOrder.OrderType.BUG_PHONE_FLOWRATE_RECHARGE.equals(orderType)) {
            String infoJson = request.getInfoJson();
            if (infoJson == null)
                throw new BusinessException(ResultCodeConstants.ApiOrder.REQUIRED_IS_EMPTY,
                        "请求参数手机号信息为空");
            InfoJsonVo infoJsonVo = new InfoJsonVo();
            ProdExtendInfoVo prodExtendInfoVo = new ProdExtendInfoVo();
            prodExtendInfoVo.setProdExtendInfoValue(infoJson);
            List<ProdExtendInfoVo> prodExtendInfoVoList = new ArrayList<ProdExtendInfoVo>();
            prodExtendInfoVoList.add(prodExtendInfoVo);
            infoJsonVo.setProdExtendInfoVoList(prodExtendInfoVoList);
            String jsonString = JSON.toJSONString(infoJsonVo);
            orderFrameCoreSV.createOrdProdExtend(prodDetailId, orderId, request.getTenantId(),
            		jsonString, OrdersConstants.OrdOdProdExtend.BatchFlag.NO);
        }
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
    private long createFeeInfo(OrderApiTradeCenterRequest request, Timestamp sysDate, long orderId) {
        /* 1. 费用入总表 */
        List<OrdOdProd> ordOdProds = this.getOrdOdProds(request.getTenantId(), orderId);
        if (CollectionUtil.isEmpty(ordOdProds)) {
            throw new BusinessException("", "订单商品明细不存在[订单ID:" + orderId + "]");
        }
        long totalFee = 0;
        long discountFee = 0;
        long operDiscountFee = 0;
        for (OrdOdProd ordOdProd : ordOdProds) {
            totalFee = ordOdProd.getTotalFee() + totalFee;
            discountFee = ordOdProd.getDiscountFee() + discountFee;
            operDiscountFee = ordOdProd.getOperDiscountFee() + operDiscountFee;
        }
        OrdOdFeeTotal ordOdFeeTotal = new OrdOdFeeTotal();
        ordOdFeeTotal.setOrderId(orderId);
        ordOdFeeTotal.setTenantId(request.getTenantId());
        ordOdFeeTotal.setPayFlag(OrdersConstants.OrdOdFeeTotal.payFlag.IN);
        ordOdFeeTotal.setTotalFee(totalFee);
        ordOdFeeTotal.setDiscountFee(discountFee);
        ordOdFeeTotal.setOperDiscountFee(operDiscountFee);
        ordOdFeeTotal.setOperDiscountDesc("");
        ordOdFeeTotal.setAdjustFee(totalFee - discountFee - operDiscountFee);
        ordOdFeeTotal.setPaidFee(0);
        ordOdFeeTotal.setPayFee(totalFee - discountFee - operDiscountFee);
        ordOdFeeTotal.setUpdateTime(sysDate);
        ordOdFeeTotal.setUpdateChlId("");
        ordOdFeeTotal.setUpdateOperId("");
        ordOdFeeTotal.setTotalJf(0l);
        ordOdFeeTotalAtomSV.insertSelective(ordOdFeeTotal);
        return ordOdFeeTotal.getPayFee();
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
     * 写入订单状态变化轨迹
     * 
     * @param request
     * @param sysDate
     * @param orderId
     * @author zhangxw
     * @ApiDocMethod
     */
    private void writeOrderCreateStateChg(OrderApiTradeCenterRequest request, Timestamp sysDate,
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
     * 余额扣款
     * 
     * @param tenantId
     * @param sysDate
     * @param orderId
     * @author zhangxw
     * @param payFee
     * @param orderId
     * @ApiDocMethod
     */
    private void deductFund(OrderApiTradeCenterRequest request, long payFee, long orderId) {
        /* 1. 余额扣款 */
        DeductParam deductParam = new DeductParam();
        deductParam.setTenantId(request.getTenantId());
        deductParam.setSystemId("slp-order");
        deductParam.setExternalId(SequenceUtil.getExternalId());
        deductParam.setBusinessCode(OrdersConstants.OrdOrder.BusiCode.NORMAL_ORDER);
        deductParam.setAccountId(request.getAcctId());
        deductParam.setSubsId(0);
        deductParam.setCheckPwd(1);
        List<TransSummary> transSummaryList = new ArrayList<TransSummary>();
        TransSummary transSummary = new TransSummary();
        transSummary.setAmount(payFee);
        transSummary.setSubjectId(100000);
        transSummaryList.add(transSummary);
        deductParam.setTransSummary(transSummaryList);
        deductParam.setTotalAmount(payFee);
        logger.info("订单支付：请求参数:" + JSON.toJSONString(deductParam));
        IDeductSV iDeductSV = DubboConsumerFactory.getService(IDeductSV.class);
        DeductResponse response = iDeductSV.deductFund(deductParam);
        if (response == null)
            throw new BusinessException("", "余额扣款返回值为空[账户ID:" + request.getAcctId() + "]");
        ResponseHeader responseHeader = response.getResponseHeader();
        if (!responseHeader.isSuccess()) {
            throw new BusinessException(ResultCodeConstants.ApiOrder.MONEY_NOT_ENOUGH, responseHeader.getResultMessage());
        }
        logger.info("订单支付：扣款流水:" + response.getSerialNo());
        /* 2. 调用后台订单支付 */
        try {
            OrderPayRequest payRequest = new OrderPayRequest();
            List<Long> orderIds = new ArrayList<Long>();
            orderIds.add(orderId);
            payRequest.setPayFee(parseLong(Double.valueOf(payFee)));
            payRequest.setOrderIds(orderIds);
            payRequest.setExternalId(response.getSerialNo());
            payRequest.setPayType(OrdersConstants.OrdOdFeeTotal.PayStyle.YE);
            payRequest.setTenantId(request.getTenantId());
            BaseResponse payResponse = iOrderPaySV.pay(payRequest);
            if (payResponse == null) {
                throw new BusinessException("", "订单支付返回值为空[订单ID:" + orderId + "]");
            }
            logger.info("订单支付成功：orderId=" + orderId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("调用订单支付服务失败：orderId=" + orderId);
        }
    }

    /**
     * 转化订单金额为long型
     * 
     * @Description
     * @author Administrator
     * @param num
     * @return
     */
    private Long parseLong(Double num) {
        if (null == num) {
            return null;
        }
        try {
            BigDecimal bnum = new BigDecimal(num);
            return bnum.longValue();
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
