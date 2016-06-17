package com.ai.slp.order.service.business.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.sdk.components.mds.MDSClientFactory;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.BeanUtils;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.util.DateUtil;
import com.ai.paas.ipaas.mds.IMessageConsumer;
import com.ai.paas.ipaas.mds.IMessageProcessor;
import com.ai.paas.ipaas.mds.IMessageSender;
import com.ai.paas.ipaas.mds.IMsgProcessorHandler;
import com.ai.paas.ipaas.util.StringUtil;
import com.ai.slp.order.api.orderpay.param.OrderPayRequest;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.constants.OrdersConstants.OrdOdStateChg;
import com.ai.slp.order.dao.mapper.bo.OrdBalacneIf;
import com.ai.slp.order.dao.mapper.bo.OrdOdFeeOffset;
import com.ai.slp.order.dao.mapper.bo.OrdOdFeeTotal;
import com.ai.slp.order.dao.mapper.bo.OrdOdProd;
import com.ai.slp.order.dao.mapper.bo.OrdOdProdCriteria;
import com.ai.slp.order.dao.mapper.bo.OrdOdProdExtend;
import com.ai.slp.order.dao.mapper.bo.OrdOdProdExtendCriteria;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.service.atom.interfaces.IOrdBalacneIfAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdFeeOffsetAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdFeeTotalAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdProdAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdProdExtendAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.ai.slp.order.service.business.interfaces.IOrderFrameCoreSV;
import com.ai.slp.order.service.business.interfaces.IOrderPayBusiSV;
import com.ai.slp.order.util.SequenceUtil;
import com.ai.slp.order.vo.InfoJsonVo;
import com.ai.slp.order.vo.ProdExtendInfoVo;
import com.ai.slp.order.vo.RouteServReqVo;
import com.ai.slp.product.api.product.interfaces.IProductServerSV;
import com.ai.slp.product.api.product.param.ProductInfoQuery;
import com.ai.slp.product.api.product.param.ProductRoute;
import com.ai.slp.product.api.storageserver.interfaces.IStorageNumSV;
import com.ai.slp.product.api.storageserver.param.StorageNumUserReq;
import com.ai.slp.route.api.core.interfaces.IRouteCoreService;
import com.ai.slp.route.api.core.params.SaleProductInfo;
import com.ai.slp.route.api.server.params.IRouteServerRequest;
import com.ai.slp.route.api.supplyproduct.interfaces.ISupplyProductServiceSV;
import com.ai.slp.route.api.supplyproduct.param.SupplyProduct;
import com.ai.slp.route.api.supplyproduct.param.SupplyProductQueryVo;
import com.alibaba.fastjson.JSON;

/**
 * 订单收费 Date: 2016年5月24日 <br>
 * Copyright (c) 2016 asiainfo.com <br>
 * 
 * @author zhangxw
 */
@Service
@Transactional
public class OrderPayBusiSVImpl implements IOrderPayBusiSV {
    private static Logger logger = LoggerFactory.getLogger(OrderPayBusiSVImpl.class);

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
    private IOrderFrameCoreSV orderFrameCoreSV;

    @Autowired
    private IOrdOdProdExtendAtomSV ordOdProdExtendAtomSV;

    @PostConstruct
    public void RouteChargeMdsProcess() {
        IMsgProcessorHandler msgProcessorHandler = new IMsgProcessorHandler() {
            @Override
            public IMessageProcessor[] createInstances(int paramInt) {
                List<IMessageProcessor> processors = new ArrayList<>();
                IMessageProcessor processor = null;
                for (int i = 0; i < paramInt; i++) {
                    processor = new RouteChargeMessProcessorImpl(ordOrderAtomSV);
                    processors.add(processor);
                }
                return processors.toArray(new IMessageProcessor[processors.size()]);
            }
        };
        IMessageConsumer msgConsumer = MDSClientFactory.getConsumerClient(
                OrdersConstants.OrdOrder.SLP_CHARGE_TOPIC, msgProcessorHandler);
        msgConsumer.start();
    }

    /**
     * 订单收费
     * 
     * @throws Exception
     */
    @Override
    public void orderPay(OrderPayRequest request) throws BusinessException, SystemException {
        /* 1.处理费用信息 */
        Timestamp sysdate = DateUtil.getSysDate();
        this.orderCharge(request, sysdate);
        for (Long orderId : request.getOrderIds()) {
            OrdOrder ordOrder = ordOrderAtomSV.selectByOrderId(request.getTenantId(), orderId);
            if (ordOrder == null) {
                throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL,
                        "订单信息不存在[订单ID:" + orderId + "]");
            }
            /* 2.订单支付完成后，对订单进行处理 */
            this.execOrders(ordOrder, request.getTenantId(), sysdate);
            /* 3.判断订单业务类型 */
            boolean flag = this.judgeOrderType(ordOrder, request.getTenantId(), sysdate);
            if (flag) {
                continue;
            }
            /* 4.拆分子订单 */
            this.resoleOrders(ordOrder, request.getTenantId());
            /* 5.归档 */
            this.archiveOrderData(request);
        }

    }

    /**
     * 订单收费处理
     * 
     * @param request
     * @author zhangxw
     * @throws Exception
     * @ApiDocMethod
     */
    private void orderCharge(OrderPayRequest request, Timestamp sysdate) throws BusinessException,
            SystemException {
        logger.debug("开始进行订单收费处理..");
        List<Long> orderIds = request.getOrderIds();
        if (CollectionUtil.isEmpty(orderIds)) {
            throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "待收费订单号");
        }
        List<OrdOdFeeTotal> noPayList = new ArrayList<OrdOdFeeTotal>();
        /* 1.校验订单是否存在未支付的费用 */
        for (Long orderId : orderIds) {
            /* 1.1 获取费用总信息 */
            OrdOdFeeTotal ordOdFeeTotal = this.getOrdOdFeeTotal(request.getTenantId(), orderId);
            noPayList.add(ordOdFeeTotal);
        }
        /* 2.计算所有订单总费用,判断与接口传入的支付金额是否一致 */
        long actTotalFee = this.caluOrdersTotalPayFee(noPayList);
        long totalFee = request.getPayFee();
        if (totalFee != actTotalFee) {
            throw new BusinessException("", "前后台计算的待支付金额不一致[已支付金额:" + totalFee + ",实际待支付金额:"
                    + actTotalFee + "]");
        }
        /* 3.针对所有未支付订单进行冲抵处理 */
        for (OrdOdFeeTotal feeTotal : noPayList) {
            this.chargeAgainst(feeTotal, request, sysdate);
        }

    }

    /**
     * 获取费用总信息
     * 
     * @param tenantId
     * @param orderId
     * @return
     * @author zhangxw
     * @ApiDocMethod
     */
    private OrdOdFeeTotal getOrdOdFeeTotal(String tenantId, Long orderId) throws BusinessException,
            SystemException {
        OrdOdFeeTotal ordOdFeeTotal = ordOdFeeTotalAtomSV.selectByOrderId(tenantId, orderId);
        if (ordOdFeeTotal == null) {
            throw new BusinessException("", "费用总表信息不存在[订单ID:" + orderId + "]");
        }
        return ordOdFeeTotal;
    }

    /**
     * 计算订单累计待收费用
     * 
     * @param payFeeList
     * @return
     * @throws Exception
     * @author zhangxw
     * @ApiDocMethod
     */
    private Long caluOrdersTotalPayFee(List<OrdOdFeeTotal> payFeeList) throws BusinessException,
            SystemException {
        long total = 0;
        for (OrdOdFeeTotal bean : payFeeList) {
            total = total + bean.getPayFee();
        }
        return total;
    }

    /**
     * 对需要支付的订单进行冲抵处理
     * 
     * @param feeTotal
     * @param request
     * @param sysdate
     * @author zhangxw
     * @throws Exception
     * @ApiDocMethod
     */
    private void chargeAgainst(OrdOdFeeTotal feeTotal, OrderPayRequest request, Timestamp sysdate)
            throws BusinessException, SystemException {
        if(StringUtil.isBlank(request.getPayType())){
            throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "支付类型为空");
        }
        if ("WEIXIN".equals(request.getPayType())) {
            request.setPayType(OrdersConstants.OrdOdFeeTotal.PayStyle.WEIXIN);
        } else if ("ZFB".equals(request.getPayType())) {
            request.setPayType(OrdersConstants.OrdOdFeeTotal.PayStyle.ZFB);
        } else if ("YL".equals(request.getPayType())) {
            request.setPayType(OrdersConstants.OrdOdFeeTotal.PayStyle.YL);
        }
        Long orderId = feeTotal.getOrderId();
        /* 1.获取该订单的费用明细信息,如果不存在费用明细，则报错 */
        List<OrdOdProd> ordOdProds = this.getOrdOdProds(request.getTenantId(), orderId);
        if (CollectionUtil.isEmpty(ordOdProds)) {
            throw new BusinessException("", "订单商品明细不存在[订单ID:" + orderId + "]");
        }
        /* 2.为订单创建一个支付机构接口信息 */
        long balacneIfId = SequenceUtil.createBalacneIfId();
        OrdBalacneIf ordBalacneIf = new OrdBalacneIf();
        ordBalacneIf.setBalacneIfId(balacneIfId);
        ordBalacneIf.setTenantId(request.getTenantId());
        ordBalacneIf.setOrderId(orderId);
        ordBalacneIf.setPayStyle(request.getPayType());
        ordBalacneIf.setPayFee(feeTotal.getPayFee());
        ordBalacneIf.setPaySystemId(OrdersConstants.OrdBalacneIf.paySystemId.PAY_CENTER);
        ordBalacneIf.setExternalId(request.getExternalId());
        ordBalacneIf.setCreateTime(sysdate);
        ordBalacneIf.setRemark("支付成功");
        /* 2.1 保存支付机构接口信息 */
        ordBalacneIfAtomSV.insertSelective(ordBalacneIf);
        /* 3.根据费用明细生成冲抵信息 */
        for (OrdOdProd ordOdProd : ordOdProds) {
            /* 3.1 对所有存在待支付金额的明细生成冲抵记录 */
            OrdOdFeeOffset feeOffset = new OrdOdFeeOffset();
            feeOffset.setFeeOffsetId(SequenceUtil.createFeeOffsetId());
            feeOffset.setTenantId(request.getTenantId());
            feeOffset.setBalacneIfId(balacneIfId);
            feeOffset.setOrderId(orderId);
            feeOffset.setProdDetalId(ordOdProd.getProdDetalId());
            feeOffset.setProdId(ordOdProd.getProdId());
            feeOffset.setOffsetFee(ordOdProd.getAdjustFee());
            feeOffset.setRemark("");
            ordOdFeeOffsetAtomSV.insertSelective(feeOffset);
        }
        /* 4.将该订单的费用总表信息做已经收费处理 */
        // 总实收=已经实收的+原来待收的
        feeTotal.setPaidFee(feeTotal.getPaidFee() + feeTotal.getPayFee());
        // 总待售=0
        feeTotal.setPayFee(0);
        // 支付方式
        feeTotal.setPayStyle(request.getPayType());
        feeTotal.setUpdateTime(sysdate);
        /* 5.保存缴费冲抵后的费用总表信息 */
        ordOdFeeTotalAtomSV.updateByOrderId(feeTotal);

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
     * 判断订单类型,卡券类更新状态直接返回
     * 
     * @param ordOrder
     * @return
     * @author zhangxw
     * @ApiDocMethod
     */
    private boolean judgeOrderType(OrdOrder ordOrder, String tenantId, Timestamp sysdate) {
        boolean flag = false;
        if (OrdersConstants.OrdOrder.OrderType.BUG_PHONE_FLOWRATE_CARD.equals(ordOrder
                .getOrderType())) {
            flag = true;
        }

        return flag;
    }

    /**
     * 拆分子订单
     * 
     * @param request
     * @author zhangxw
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @ApiDocMethod
     */
    private void resoleOrders(OrdOrder ordOrder, String tenantId) {
        logger.debug("开始对订单[" + ordOrder.getOrderId() + "]进行拆分..");
        /* 1.查询商品明细拓展表 */
        OrdOdProdExtendCriteria example = new OrdOdProdExtendCriteria();
        OrdOdProdExtendCriteria.Criteria criteria = example.createCriteria();
        criteria.andTenantIdEqualTo(tenantId);
        if (ordOrder.getOrderId() != 0) {
            criteria.andOrderIdEqualTo(ordOrder.getOrderId());
        }
        List<OrdOdProdExtend> ordOdProdExtendList = ordOdProdExtendAtomSV.selectByExample(example);
        if (CollectionUtil.isEmpty(ordOdProdExtendList)) {
            throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "商品明细信息-扩展表信息为空");
        }
        /* 2.遍历取出值信息 */
        for (OrdOdProdExtend ordOdProdExtend : ordOdProdExtendList) {
            String infoJson = ordOdProdExtend.getInfoJson();
            InfoJsonVo infoJsonVo = JSON.parseObject(infoJson, InfoJsonVo.class);
            List<ProdExtendInfoVo> prodExtendInfoVoList = infoJsonVo.getProdExtendInfoVoList();
            for (ProdExtendInfoVo prodExtendInfoVo : prodExtendInfoVoList) {
                /* 3.生成子订单 */
                this.createSubOrder(tenantId, ordOrder, ordOdProdExtend.getProdDetalId(),
                        prodExtendInfoVo.getProdExtendInfoValue());
            }

        }
    }

    /**
     * 生成子订单
     * 
     * @param orderId
     * @param prodExtendInfoValue
     * @author zhangxw
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @ApiDocMethod
     */
    private void createSubOrder(String tenantId, OrdOrder parentOrdOrder, long parentProdDetalId,
            String prodExtendInfoValue) {
        /* 1.创建子订单表 */
        long subOrderId = SequenceUtil.createOrderId();
        OrdOrder childOrdOrder = new OrdOrder();
        BeanUtils.copyProperties(childOrdOrder, parentOrdOrder);

        childOrdOrder.setOrderId(subOrderId);
        childOrdOrder.setSubFlag(OrdersConstants.OrdOrder.SubFlag.YES);
        childOrdOrder.setParentOrderId(parentOrdOrder.getOrderId());
        ordOrderAtomSV.insertSelective(childOrdOrder);
        /* 2.创建子订单-商品明细信息 */
        long prodDetailId = SequenceUtil.createProdDetailId();
        OrdOdProdCriteria example = new OrdOdProdCriteria();
        OrdOdProdCriteria.Criteria criteria = example.createCriteria();
        criteria.andTenantIdEqualTo(tenantId);

        if (parentOrdOrder.getOrderId() != 0) {
            criteria.andOrderIdEqualTo(parentOrdOrder.getOrderId());
        }
        if (parentProdDetalId != 0) {
            criteria.andProdDetalIdEqualTo(parentProdDetalId);
        }
        List<OrdOdProd> ordOdProdList = ordOdProdAtomSV.selectByExample(example);
        if (CollectionUtil.isEmpty(ordOdProdList)) {
            throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "商品明细信息");
        }
        OrdOdProd parentOrdOdProd = ordOdProdList.get(0);
        parentOrdOdProd.getTenantId();
        OrdOdProd ordOdProd = new OrdOdProd();
        BeanUtils.copyProperties(ordOdProd, parentOrdOdProd);
        ordOdProd.setProdDetalId(prodDetailId);
        ordOdProd.setOrderId(subOrderId);
        ordOdProd.setExtendInfo(prodExtendInfoValue);
        ordOdProdAtomSV.insertSelective(ordOdProd);
        /* 3.调用路由,并更新订单明细表 */
        this.callRoute(tenantId, prodDetailId, ordOdProd);

    }

    /**
     * 订单支付确认后处理
     * 
     * @param request
     * @author zhangxw
     * @ApiDocMethod
     */
    private void execOrders(OrdOrder ordOrder, String tenantId, Timestamp sysdate) {
        logger.debug("支付完成，对订单[" + ordOrder.getOrderId() + "]状态进行处理..");
        /* 1.获取订单信息 */
        String oldState = ordOrder.getState();
        /* 2.判断订单状态是否是待支付 */
        if (OrdersConstants.OrdOrder.State.WAIT_PAY.equals(oldState)) {
            /* 2.1 如果订单之前状态为待支付,则说明执行本操作时候前，订单在进行支付操作,执行本操作后订单为已支付 */
            String newState = OrdersConstants.OrdOrder.State.FINISH_PAID;
            ordOrder.setState(newState);
            ordOrder.setStateChgTime(sysdate);
            ordOrderAtomSV.updateById(ordOrder);
            /* 2.2 记入订单轨迹表 */
            orderFrameCoreSV.ordOdStateChg(ordOrder.getOrderId(), tenantId, oldState, newState,
                    OrdOdStateChg.ChgDesc.ORDER_PAID, null, null, null, sysdate);

        }
        /* 3.增加商品销量 */
        List<OrdOdProd> ordOdProds = this.getOrdOdProds(tenantId, ordOrder.getOrderId());
        for (OrdOdProd ordOdProd : ordOdProds) {
            this.addSaleNumOfProduct(tenantId, ordOdProd.getSkuId(), (int) ordOdProd.getBuySum());
        }

    }

    /**
     * 增加商品销量
     * 
     * @param tenantId
     * @param skuId
     * @return
     */
    private void addSaleNumOfProduct(String tenantId, String skuId, int skuNum) {
        StorageNumUserReq storageNumUserReq = new StorageNumUserReq();
        storageNumUserReq.setTenantId(tenantId);
        storageNumUserReq.setSkuId(skuId);
        storageNumUserReq.setSkuNum(skuNum);
        IStorageNumSV iStorageNumSV = DubboConsumerFactory.getService("iStorageNumSV");
        BaseResponse response = iStorageNumSV.addSaleNumOfProduct(storageNumUserReq);
        if (!ExceptCodeConstants.Special.SUCCESS.equals(response.getResponseHeader()
                .getResultCode())) {
            throw new BusinessException("", "调用增加商品销量报错[skuId:" + skuId + "skuNum:" + skuNum + "]");
        }
    }

    /**
     * 调用路由
     * 
     * @param prodId
     * @param salePrice
     * @author zhangxw
     * @param prodDetailId
     * @param string
     * @ApiDocMethod
     */
    private void callRoute(String tenantId, long prodDetailId, OrdOdProd ordOdProd) {
        /* 1.获取路由组ID */
        String routeGroupId = this.getRouteGroupId(tenantId, ordOdProd.getProdId());
        /* 2.路由计算获取路由ID */
        IRouteCoreService iRouteCoreService = DubboConsumerFactory.getService("iRouteCoreService");
        SaleProductInfo saleProductInfo = new SaleProductInfo();
        saleProductInfo.setTenantId(tenantId);
        saleProductInfo.setRouteGroupId(routeGroupId);
        saleProductInfo.setTotalConsumption(ordOdProd.getSalePrice());
        String routeId = iRouteCoreService.findRoute(saleProductInfo);
        if (StringUtil.isBlank(routeId)) {
            throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "根据路由组ID["
                    + routeGroupId + "]订单金额[" + ordOdProd.getSalePrice() + "]未能找到供货路由");
        }
        /* 3.根据路由ID查询相关信息 */
        SupplyProductQueryVo supplyProductQueryVo = new SupplyProductQueryVo();
        supplyProductQueryVo.setTenantId(tenantId);
        supplyProductQueryVo.setRouteId(routeId);
        supplyProductQueryVo.setSaleCount(1);
        supplyProductQueryVo.setStandardProductId(ordOdProd.getStandardProdId());
        ISupplyProductServiceSV iSupplyProductServiceSV = DubboConsumerFactory
                .getService("iSupplyProductServiceSV");
        SupplyProduct supplyProduct = iSupplyProductServiceSV
                .updateSupplyProductSaleCount(supplyProductQueryVo);
        /* 4.更新订单商品明细表字段 */
        OrdOdProdCriteria example = new OrdOdProdCriteria();
        OrdOdProdCriteria.Criteria criteria = example.createCriteria();
        criteria.andTenantIdEqualTo(tenantId);
        if (prodDetailId != 0) {
            criteria.andProdDetalIdEqualTo(prodDetailId);
        }
        List<OrdOdProd> ordOdProdList = ordOdProdAtomSV.selectByExample(example);
        if (CollectionUtil.isEmpty(ordOdProdList)) {
            throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "商品明细信息");
        }
        OrdOdProd ordOdProdBean = ordOdProdList.get(0);
        ordOdProdBean.setRouteId(routeId);
        ordOdProdBean.setSupplyId(supplyProduct.getSupplyId());
        ordOdProdBean.setSellerId(supplyProduct.getSellerId());
        ordOdProdBean.setCostPrice(supplyProduct.getCostPrice());
        ordOdProdAtomSV.updateById(ordOdProdBean);
        /* 5.充值路由 */
        IRouteServerRequest request = new IRouteServerRequest();
        request.setTenantId(tenantId);
        request.setRouteId(routeId);
        RouteServReqVo routeServReqVo = new RouteServReqVo();
        routeServReqVo.setOrderId(String.valueOf(ordOdProd.getOrderId()));
        routeServReqVo.setBizType(ordOdProdBean.getProdType());
        routeServReqVo.setAccountVal(ordOdProdBean.getExtendInfo());
        routeServReqVo.setBuyNum(1);
        routeServReqVo.setNotifyUrl("");
        routeServReqVo.setProId(ordOdProdBean.getSupplyId());
        routeServReqVo.setUnitPrice(ordOdProdBean.getCostPrice());
        request.setRequestData(JSON.toJSONString(routeServReqVo));
        chargeMds(request);
    }

    /**
     * 根据商品ID查询路由组ID
     * 
     * @param tenantId
     * @param productId
     * @return
     * @author zhangxw
     * @ApiDocMethod
     */
    private String getRouteGroupId(String tenantId, String productId) {
        ProductInfoQuery productInfoQuery = new ProductInfoQuery();
        productInfoQuery.setTenantId(tenantId);
        productInfoQuery.setProductId(productId);
        IProductServerSV iProductServerSV = DubboConsumerFactory.getService("iProductServerSV");
        ProductRoute productRoute = iProductServerSV.queryRouteGroupOfProd(productInfoQuery);
        return productRoute.getRouteGroupId();
    }

    /**
     * 发送充值数据,调用路由o2p进行充值
     * 
     * @param request
     * @author zhangxw
     * @ApiDocMethod
     */
    private void chargeMds(IRouteServerRequest request) {
        IMessageSender msgSender = MDSClientFactory
                .getSenderClient(OrdersConstants.OrdOrder.SLP_CHARGE_TOPIC);

        msgSender.send(JSON.toJSONString(request), 0);// 第二个参数为分区键，如果不分区，传入0
    }

    /**
     * 归档
     * 
     * @param request
     * @author zhangxw
     * @ApiDocMethod
     */
    private void archiveOrderData(OrderPayRequest request) {
    }
}
