package com.ai.slp.order.service.business.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.sdk.util.DateUtil;
import com.ai.slp.order.api.ordertradecenter.param.OrdBaseInfo;
import com.ai.slp.order.api.ordertradecenter.param.OrdExtendInfo;
import com.ai.slp.order.api.ordertradecenter.param.OrdFeeInfo;
import com.ai.slp.order.api.ordertradecenter.param.OrdOrderInfo;
import com.ai.slp.order.api.ordertradecenter.param.OrdProductInfo;
import com.ai.slp.order.api.ordertradecenter.param.OrderTradeCenterRequest;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.constants.OrdersConstants.OrdOdStateChg;
import com.ai.slp.order.dao.mapper.bo.OrdOdFeeTotal;
import com.ai.slp.order.dao.mapper.bo.OrdOdProd;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.service.atom.interfaces.IOrdOdFeeTotalAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdProdAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.ai.slp.order.service.business.interfaces.IOrdOrderTradeBusiSV;
import com.ai.slp.order.service.business.interfaces.IOrderFrameCoreSV;
import com.ai.slp.order.util.SequenceUtil;

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
    public List<Long> apply(OrderTradeCenterRequest request) throws BusinessException,
            SystemException {
        /* 1.生成订单号 */
        long orderId = SequenceUtil.createOrderId();
        LOG.debug("开始处理-订单号[" + orderId + "]订单提交..");
        Timestamp sysDate = DateUtil.getSysDate();
        /* 2.创建业务订单信息 */
        this.createOrder(request, sysDate, orderId);
        /* 3.创建商品明细信息 */
        this.createOrderProd(request, sysDate, orderId);
        /* 4.费用信息处理 */
        this.createFeeInfo(request, sysDate, orderId);
        /* 5.创建发票信息 */
        this.createOrderFeeInvoice(request, sysDate, orderId);
        /* 6. 处理配送信息，存在则写入 */
        this.createOrderLogistics(request, sysDate, orderId);
        /* 7. 记录一条订单创建轨迹记录 */
        this.writeOrderCreateStateChg(request, sysDate, orderId);
        /* 8. 更新订单状态 */
        this.updateOrderState(request.getTenantId(), sysDate, orderId);
        /* 9. 返回结果订单列表 */
        List<Long> orderList = new ArrayList<Long>();
        orderList.add(orderId);
        return orderList;
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
        OrdOrderInfo ordOrderInfo = request.getOrdOrderInfo();
        OrdBaseInfo ordBaseInfo = ordOrderInfo.getOrdBaseInfo();
        OrdOrder ordOrder = new OrdOrder();
        ordOrder.setOrderId(orderId);
        ordOrder.setTenantId(request.getTenantId());
        ordOrder.setBusiCode(OrdersConstants.OrdOrder.BusiCode.NORMAL_ORDER);
        ordOrder.setOrderType(ordBaseInfo.getOrderType());
        ordOrder.setSubFlag(OrdersConstants.OrdOrder.SubFlag.NO);
        // ordOrder.setParentOrderId(0l);
        ordOrder.setUserId(ordBaseInfo.getUserId());
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
     * @ApiDocMethod
     */
    private void createOrderProd(OrderTradeCenterRequest request, Timestamp sysDate, long orderId) {
        LOG.debug("开始处理订单商品明细[" + orderId + "]资料信息..");
        OrdOrderInfo ordOrderInfo = request.getOrdOrderInfo();
        /* 1. 创建商品明细 */
        List<OrdProductInfo> ordProductInfoList = ordOrderInfo.getOrdProductInfoList();
        for (OrdProductInfo ordProductInfo : ordProductInfoList) {
            long prodDetailId = SequenceUtil.createProdDetailId();
            OrdOdProd ordOdProd = new OrdOdProd();
            ordOdProd.setProdDetalId(prodDetailId);
            ordOdProd.setTenantId(request.getTenantId());
            ordOdProd.setOrderId(orderId);
            ordOdProd.setProdType(OrdersConstants.OrdOdProd.ProdType.PROD);
            ordOdProd.setProdId(ordProductInfo.getProdId());
            ordOdProd.setProdName(ordProductInfo.getProdName());
            ordOdProd.setSkuId(ordProductInfo.getSkuId());
            ordOdProd.setStandardProdId(ordProductInfo.getStandardProdId());
            ordOdProd.setStorageId(ordProductInfo.getStorageId());
            ordOdProd.setValidTime(sysDate);
            ordOdProd.setInvalidTime(DateUtil.getFutureTime());
            ordOdProd.setState(OrdersConstants.OrdOdProd.State.SELL);
            ordOdProd.setBuySum(ordProductInfo.getBuySum());
            ordOdProd.setSalePrice(ordProductInfo.getSalePrice());
            ordOdProd.setTotalFee(ordProductInfo.getSalePrice() * ordProductInfo.getBuySum());
            ordOdProd.setDiscountFee(ordProductInfo.getDiscountFee());
            ordOdProd.setOperDiscountFee(ordProductInfo.getOperDiscountFee());
            ordOdProd.setOperDiscountDesc(ordProductInfo.getOperDiscountDesc());
            ordOdProd.setAdjustFee(ordProductInfo.getAdjustFee());
            ordOdProd.setProdDesc("");
            ordOdProd.setExtendInfo("");
            ordOdProdAtomSV.insertSelective(ordOdProd);
            /* 2. 创建商品明细扩展表 */
            this.createOrdOdProdExtend(prodDetailId, request, sysDate, orderId);
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
    private void createFeeInfo(OrderTradeCenterRequest request, Timestamp sysDate, long orderId) {
        OrdOrderInfo ordOrderInfo = request.getOrdOrderInfo();
        OrdFeeInfo ordFeeInfo = ordOrderInfo.getOrdFeeInfo();
        OrdOdFeeTotal ordOdFeeTotal = new OrdOdFeeTotal();
        ordOdFeeTotal.setOrderId(orderId);
        ordOdFeeTotal.setTenantId(request.getTenantId());
        ordOdFeeTotal.setPayFlag(OrdersConstants.OrdOdFeeTotal.payFlag.IN);
        ordOdFeeTotal.setTotalFee(ordFeeInfo.getTotalFee());
        ordOdFeeTotal.setDiscountFee(ordFeeInfo.getDiscountFee());
        ordOdFeeTotal.setOperDiscountFee(ordFeeInfo.getOperDiscountFee());
        ordOdFeeTotal.setOperDiscountDesc(ordFeeInfo.getOperDiscountDesc());
        ordOdFeeTotal.setAdjustFee(ordFeeInfo.getAdjustFee());
        ordOdFeeTotal.setPaidFee(ordFeeInfo.getPaidFee());
        ordOdFeeTotal.setPayFee(ordFeeInfo.getTotalFee());
        ordOdFeeTotal.setPayStyle(ordFeeInfo.getPayStyle());
        ordOdFeeTotal.setUpdateTime(sysDate);
        ordOdFeeTotal.setUpdateChlId("");
        ordOdFeeTotal.setUpdateOperId("");
        ordOdFeeTotal.setTotalJf(0l);

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
            Timestamp sysDate, long orderId) {
        OrdOrderInfo ordOrderInfo = request.getOrdOrderInfo();
        OrdExtendInfo ordExtendInfo = ordOrderInfo.getOrdExtendInfo();
        orderFrameCoreSV.createOrdProdExtend(prodDetailId, orderId, request.getTenantId(),
                ordExtendInfo.getInfoJson());
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
        ordOrder.setState(OrdersConstants.OrdOrder.State.WAIT_PAY);
        ordOrder.setStateChgTime(sysDate);
        ordOrderAtomSV.updateById(ordOrder);
        // 写入订单状态变化轨迹表
        orderFrameCoreSV.ordOdStateChg(orderId, tenantId, ordOrder.getState(),
                OrdersConstants.OrdOrder.State.WAIT_PAY, OrdOdStateChg.ChgDesc.ORDER_TO_PAY, null,
                null, null, sysDate);
    }

}
