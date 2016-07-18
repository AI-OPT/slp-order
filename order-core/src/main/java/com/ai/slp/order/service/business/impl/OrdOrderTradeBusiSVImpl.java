package com.ai.slp.order.service.business.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
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
import com.ai.slp.order.api.ordertradecenter.param.OrdProductInfo;
import com.ai.slp.order.api.ordertradecenter.param.OrdProductResInfo;
import com.ai.slp.order.api.ordertradecenter.param.OrderTradeCenterRequest;
import com.ai.slp.order.api.ordertradecenter.param.OrderTradeCenterResponse;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.constants.OrdersConstants.OrdOdStateChg;
import com.ai.slp.order.dao.mapper.bo.OrdOdFeeTotal;
import com.ai.slp.order.dao.mapper.bo.OrdOdProd;
import com.ai.slp.order.dao.mapper.bo.OrdOdProdCriteria;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.service.atom.interfaces.IOrdOdFeeTotalAtomSV;
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

    @Override
    public OrderTradeCenterResponse apply(OrderTradeCenterRequest request)
            throws BusinessException, SystemException {
        OrderTradeCenterResponse response = new OrderTradeCenterResponse();
        IDSSClient client = DSSClientFactory.getDSSClient(OrdersConstants.ORDER_PHONENUM_DSS);
        /* 1.生成订单号 */
        long orderId = SequenceUtil.createOrderId();
        LOG.debug("开始处理-订单号[" + orderId + "]订单提交..");
        Timestamp sysDate = DateUtil.getSysDate();
        /* 2.创建业务订单信息 */
        this.createOrder(request, sysDate, orderId);
        /* 3.创建商品明细信息 */
        List<OrdProductResInfo> ordProductResList = this.createOrderProd(request, sysDate, orderId,client);
        /* 4.费用信息处理 */
        OrdFeeInfo ordFeeInfo = this.createFeeInfo(request, sysDate, orderId);
        /* 5.创建发票信息 */
        this.createOrderFeeInvoice(request, sysDate, orderId);
        /* 6. 处理配送信息，存在则写入 */
        this.createOrderLogistics(request, sysDate, orderId);
        /* 7. 记录一条订单创建轨迹记录 */
        this.writeOrderCreateStateChg(request, sysDate, orderId);
        /* 8. 更新订单状态 */
        this.updateOrderState(request.getTenantId(), sysDate, orderId);
        /* 9. 封装返回参数 */
        response.setOrderId(orderId);
        response.setOrdFeeInfo(ordFeeInfo);
        response.setOrdProductResList(ordProductResList);
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
    private void createOrder(OrderTradeCenterRequest request, Timestamp sysDate, long orderId) {
        LOG.debug("开始处理订单主表[" + orderId + "]资料信息..");
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
        ordOrder.setOrderId(orderId);
        ordOrder.setTenantId(request.getTenantId());
        ordOrder.setBusiCode(OrdersConstants.OrdOrder.BusiCode.NORMAL_ORDER);
        ordOrder.setOrderType(ordBaseInfo.getOrderType());
        ordOrder.setSubFlag(OrdersConstants.OrdOrder.SubFlag.NO);
        ordOrder.setUserId(ordBaseInfo.getUserId());
        ordOrder.setUserType(ordBaseInfo.getUserType());
        ordOrder.setProvinceCode(ordBaseInfo.getProvinceCode());
        ordOrder.setCityCode(ordBaseInfo.getCityCode());
        ordOrder.setState(OrdersConstants.OrdOrder.State.NEW);
        ordOrder.setStateChgTime(sysDate);
        ordOrder.setDisplayFlag(OrdersConstants.OrdOrder.DisplayFlag.USER_NORMAL_VISIABLE);
        ordOrder.setDisplayFlagChgTime(sysDate);
        ordOrder.setDeliveryFlag(OrdersConstants.OrdOrder.DeliveryFlag.NONE);
        ordOrder.setOrderTime(sysDate);
        ordOrder.setOrderDesc(ordBaseInfo.getOrderDesc());
        ordOrder.setKeywords(ordBaseInfo.getKeywords());
        ordOrder.setRemark(ordBaseInfo.getRemark());
        ordOrderAtomSV.insertSelective(ordOrder);
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
        /* 1. 创建商品明细 */
        OrdBaseInfo ordBaseInfo = request.getOrdBaseInfo();
        String orderType = ordBaseInfo.getOrderType();
        List<OrdProductResInfo> ordProductResList = new ArrayList<OrdProductResInfo>();
        List<OrdProductInfo> ordProductInfoList = request.getOrdProductInfoList();
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
            ordOdProd.setDiscountFee(ordProductInfo.getDiscountFee());
            ordOdProd.setOperDiscountFee(ordProductInfo.getOperDiscountFee());
            ordOdProd.setOperDiscountDesc(ordProductInfo.getOperDiscountDesc());
            ordOdProd.setAdjustFee(ordOdProd.getTotalFee() - ordOdProd.getDiscountFee()
                    - ordOdProd.getOperDiscountFee());
            ProdAttrInfoVo vo = new ProdAttrInfoVo();
            vo.setBasicOrgId(ordProductInfo.getBasicOrgId());
            vo.setProvinceCode(ordProductInfo.getProvinceCode());
            vo.setChargeFee(ordProductInfo.getChargeFee());
            ordOdProd.setProdDesc("");
            ordOdProd.setExtendInfo(JSON.toJSONString(vo));
            ordOdProd.setUpdateTime(sysDate);
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
}
