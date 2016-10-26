package com.ai.slp.order.service.business.impl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.ai.opt.sdk.dubbo.util.HttpClientUtil;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.util.StringUtil;
import com.ai.platform.common.api.cache.interfaces.ICacheSV;
import com.ai.platform.common.api.cache.param.SysParam;
import com.ai.slp.order.api.orderlist.param.BehindOrdOrderVo;
import com.ai.slp.order.api.orderlist.param.BehindOrdProductVo;
import com.ai.slp.order.api.orderlist.param.BehindParentOrdOrderVo;
import com.ai.slp.order.api.orderlist.param.BehindQueryOrderListRequest;
import com.ai.slp.order.api.orderlist.param.BehindQueryOrderListResponse;
import com.ai.slp.order.api.orderlist.param.OrdOrderApiVo;
import com.ai.slp.order.api.orderlist.param.OrdOrderVo;
import com.ai.slp.order.api.orderlist.param.OrdProductApiVo;
import com.ai.slp.order.api.orderlist.param.OrdProductVo;
import com.ai.slp.order.api.orderlist.param.OrderPayVo;
import com.ai.slp.order.api.orderlist.param.ProductImage;
import com.ai.slp.order.api.orderlist.param.QueryApiOrderRequest;
import com.ai.slp.order.api.orderlist.param.QueryApiOrderResponse;
import com.ai.slp.order.api.orderlist.param.QueryOrderListRequest;
import com.ai.slp.order.api.orderlist.param.QueryOrderListResponse;
import com.ai.slp.order.api.orderlist.param.QueryOrderRequest;
import com.ai.slp.order.api.orderlist.param.QueryOrderResponse;
import com.ai.slp.order.constants.ErrorCodeConstants;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.dao.mapper.attach.BehindOrdOrderAttach;
import com.ai.slp.order.dao.mapper.attach.OrdOrderAttach;
import com.ai.slp.order.dao.mapper.bo.OrdBalacneIf;
import com.ai.slp.order.dao.mapper.bo.OrdBalacneIfCriteria;
import com.ai.slp.order.dao.mapper.bo.OrdBalacneIfCriteria.Criteria;
import com.ai.slp.order.dao.mapper.bo.OrdOdFeeProd;
import com.ai.slp.order.dao.mapper.bo.OrdOdFeeProdCriteria;
import com.ai.slp.order.dao.mapper.bo.OrdOdFeeTotal;
import com.ai.slp.order.dao.mapper.bo.OrdOdFeeTotalCriteria;
import com.ai.slp.order.dao.mapper.bo.OrdOdInvoice;
import com.ai.slp.order.dao.mapper.bo.OrdOdLogistics;
import com.ai.slp.order.dao.mapper.bo.OrdOdProd;
import com.ai.slp.order.dao.mapper.bo.OrdOdProdCriteria;
import com.ai.slp.order.dao.mapper.bo.OrdOdProdExtend;
import com.ai.slp.order.dao.mapper.bo.OrdOdProdExtendCriteria;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.dao.mapper.bo.OrdOrderCriteria;
import com.ai.slp.order.service.atom.interfaces.IOrdBalacneIfAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdFeeProdAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdFeeTotalAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdInvoiceAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdLogisticsAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdProdAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdProdExtendAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAttachAtomSV;
import com.ai.slp.order.service.business.interfaces.IOrdOrderBusiSV;
import com.ai.slp.order.util.ChUserUtil;
import com.ai.slp.order.util.CommonCheckUtils;
import com.ai.slp.order.util.InfoTranslateUtil;
import com.ai.slp.order.vo.InfoJsonVo;
import com.ai.slp.order.vo.ProdAttrInfoVo;
import com.ai.slp.order.vo.ProdExtendInfoVo;
import com.ai.slp.product.api.product.interfaces.IProductServerSV;
import com.ai.slp.product.api.product.param.ProductSkuInfo;
import com.ai.slp.product.api.product.param.SkuInfoQuery;
import com.ai.slp.route.api.routemanage.interfaces.IRouteManageSV;
import com.ai.slp.route.api.routemanage.param.RouteIdParamRequest;
import com.ai.slp.route.api.routemanage.param.RouteResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


@Service
@Transactional
public class OrdOrderBusiSVImpl implements IOrdOrderBusiSV {

    private static final Logger logger=LoggerFactory.getLogger(OrdOrderBusiSVImpl.class);

    @Autowired
    private IOrdOrderAtomSV ordOrderAtomSV;

    @Autowired
    private IOrdOdFeeTotalAtomSV ordOdFeeTotalAtomSV;

    @Autowired
    private IOrdOdFeeProdAtomSV ordOdFeeProdAtomSV;

    @Autowired
    private IOrdOdProdAtomSV ordOdProdAtomSV;

    @Autowired
    private IOrdOdProdExtendAtomSV ordOdProdExtendAtomSV;

    @Autowired
    private IOrdOrderAttachAtomSV ordOrderAttachAtomSV;
    
    @Autowired
    private IOrdOdInvoiceAtomSV ordOdInvoiceAtomSV;
    
    @Autowired
    private IOrdOdLogisticsAtomSV ordOdLogisticsAtomSV; 
    
    @Autowired
    private IOrdBalacneIfAtomSV ordBalacneIfAtomSV;
    
    @Override
    public QueryOrderListResponse queryOrderList(QueryOrderListRequest orderListRequest)
            throws BusinessException, SystemException {
        logger.debug("开始订单列表查询..");
        /* 1.订单信息查询 */
        ICacheSV iCacheSV = DubboConsumerFactory.getService(ICacheSV.class);
        QueryOrderListResponse response = new QueryOrderListResponse();
        PageInfo<OrdOrderVo> pageInfo = new PageInfo<OrdOrderVo>();
        String states = "";
        StringBuffer sb = new StringBuffer("");
        List<String> stateList = orderListRequest.getStateList();
        if (!CollectionUtil.isEmpty(stateList)) {
            for (String state : stateList) {
                sb = sb.append(state).append(",");
            }
            states = sb.toString();
            states = states.substring(0, sb.length() - 1);
        }
        /* 2.多表查询订单个数 */
        int count = ordOrderAttachAtomSV.queryCount(OrdersConstants.OrdOrder.SubFlag.NO,
                orderListRequest, states);
        /* 3.多表查询订单信息 */
        List<OrdOrderAttach> list = ordOrderAttachAtomSV.queryOrderBySearch(
                OrdersConstants.OrdOrder.SubFlag.NO, orderListRequest, states);
        List<OrdOrderVo> ordOrderList = new ArrayList<OrdOrderVo>();
        for (OrdOrderAttach orderAttach : list) {
            OrdOrderVo ordOrderVo = new OrdOrderVo();
            ordOrderVo.setOrderId(orderAttach.getOrderId());
            ordOrderVo.setOrderType(orderAttach.getOrderType());
            ordOrderVo.setBusiCode(orderAttach.getBusiCode());
            SysParam sysParamBusiCode = InfoTranslateUtil.translateInfo(orderAttach.getTenantId(), "ORD_ORDER", "BUSI_CODE", 
            		orderAttach.getBusiCode(), iCacheSV);
            ordOrderVo.setBusiCodeName(sysParamBusiCode == null ? "" : sysParamBusiCode.getColumnDesc());
            ordOrderVo.setState(orderAttach.getState());
            SysParam sysParamState = InfoTranslateUtil.translateInfo(orderAttach.getTenantId(), "ORD_ORDER", "STATE", 
            		orderAttach.getState(), iCacheSV);
            ordOrderVo.setStateName(sysParamState == null ? "" : sysParamState.getColumnDesc());
            ordOrderVo.setOrderTime(orderAttach.getOrderTime());
            ordOrderVo.setAdjustFee(orderAttach.getAdjustFee());
            ordOrderVo.setDiscountFee(orderAttach.getDiscountFee());
            ordOrderVo.setPaidFee(orderAttach.getPaidFee());
            ordOrderVo.setPayFee(orderAttach.getPayFee());
            ordOrderVo.setPayStyle(orderAttach.getPayStyle());
            SysParam sysParamPayStyle = InfoTranslateUtil.translateInfo(orderAttach.getTenantId(), "ORD_OD_FEE_TOTAL",
                    "PAY_STYLE", orderAttach.getPayStyle(), iCacheSV);
            ordOrderVo.setPayStyleName(sysParamPayStyle == null ? "" : sysParamPayStyle.getColumnDesc());
            ordOrderVo.setPayTime(orderAttach.getPayTime());
            ordOrderVo.setTotalFee(orderAttach.getTotalFee());
            int phoneCount = this.getProdExtendInfo(orderListRequest.getTenantId(),
                    orderAttach.getOrderId());
            ordOrderVo.setPhoneCount(phoneCount);
            /* 4.订单费用明细查询 */
            List<OrderPayVo> orderFeeProdList = this.getOrderFeeProdList(iCacheSV,
                    orderAttach.getOrderId(),orderAttach.getTenantId());
            ordOrderVo.setPayDataList(orderFeeProdList);
            /* 5.订单商品明细查询 */
            List<OrdProductVo> productList = this.getOrdProductList(orderAttach.getTenantId(), orderAttach.getOrderId());
            ordOrderVo.setProductList(productList);
            ordOrderList.add(ordOrderVo);
        }
        pageInfo.setPageNo(orderListRequest.getPageNo());
        pageInfo.setPageSize(orderListRequest.getPageSize());
        pageInfo.setResult(ordOrderList);
        pageInfo.setCount(count);
        response.setPageInfo(pageInfo);
        return response;
    }

    /**
     * 订单费用信息查询
     * 
     * @param tenantId
     * @param orderId
     * @param payStyle
     * @return
     * @author zhangxw
     * @ApiDocMethod
     */
    private List<OrdOdFeeTotal> getOrderFeeTotalList(String tenantId, long orderId, String payStyle) {
        OrdOdFeeTotalCriteria example = new OrdOdFeeTotalCriteria();
        OrdOdFeeTotalCriteria.Criteria criteria = example.createCriteria();
        criteria.andTenantIdEqualTo(tenantId);
        criteria.andOrderIdEqualTo(orderId);
        if (!StringUtils.isBlank(payStyle)) {
            criteria.andPayStyleEqualTo(payStyle);
        }
        List<OrdOdFeeTotal> orderFeeTotalList = ordOdFeeTotalAtomSV.selectByExample(example);
        return orderFeeTotalList;
    }

    /**
     * 订单费用明细查询
     * 
     * @param orderId
     * @return
     * @author zhangxw
     * @param iCacheSV
     * @ApiDocMethod
     */
    private List<OrderPayVo> getOrderFeeProdList(ICacheSV iCacheSV, long orderId,String tenantId) {
        List<OrderPayVo> payDataList = null;
        OrdOdFeeProdCriteria example = new OrdOdFeeProdCriteria();
        OrdOdFeeProdCriteria.Criteria criteria = example.createCriteria();
        criteria.andOrderIdEqualTo(orderId);
        List<OrdOdFeeProd> orderFeeProdList = ordOdFeeProdAtomSV.selectByExample(example);
        if (!CollectionUtil.isEmpty(orderFeeProdList)) {
            for (OrdOdFeeProd ordOdFeeProd : orderFeeProdList) {
                payDataList = new ArrayList<OrderPayVo>();
                OrderPayVo orderPayVo = new OrderPayVo();
                orderPayVo.setPayStyle(ordOdFeeProd.getPayStyle());
                orderPayVo.setPaidFee(ordOdFeeProd.getPaidFee());
                SysParam sysParam = InfoTranslateUtil.translateInfo(tenantId,
                        "ORD_OD_FEE_TOTAL", "PAY_STYLE", ordOdFeeProd.getPayStyle(), iCacheSV);
                orderPayVo.setPayStyleName(sysParam == null ? "" : sysParam.getColumnDesc());
                payDataList.add(orderPayVo);
            }
        }
        return payDataList;
    }

    /**
     * 商品集合
     * 
     * @param orderId
     * @return
     * @author zhangxw
     * @param iCacheSV
     * @param tenantId
     * @ApiDocMethod
     */
    private List<OrdProductVo> getOrdProductList(String tenantId, long orderId) {
        List<OrdProductVo> productList = new ArrayList<OrdProductVo>();
        OrdOdProdCriteria example = new OrdOdProdCriteria();
        OrdOdProdCriteria.Criteria criteria = example.createCriteria();
        criteria.andTenantIdEqualTo(tenantId);
        criteria.andOrderIdEqualTo(orderId);
        List<OrdOdProd> ordOdProdList = ordOdProdAtomSV.selectByExample(example);
        if (!CollectionUtil.isEmpty(ordOdProdList)) {
            for (OrdOdProd ordOdProd : ordOdProdList) {
                OrdProductVo ordProductVo = new OrdProductVo();
                ordProductVo.setOrderId(orderId);
                ordProductVo.setProdDetalId(ordOdProd.getProdDetalId());
                ordProductVo.setSkuId(ordOdProd.getSkuId());
                ordProductVo.setState(ordOdProd.getState());
                ordProductVo.setProdName(ordOdProd.getProdName());
                ordProductVo.setSalePrice(ordOdProd.getSalePrice());
                ordProductVo.setBuySum(ordOdProd.getBuySum());
                ordProductVo.setTotalFee(ordOdProd.getTotalFee());
                ordProductVo.setDiscountFee(ordOdProd.getDiscountFee());
                ordProductVo.setAdjustFee(ordOdProd.getAdjustFee());
                ordProductVo.setOperDiscountFee(ordOdProd.getOperDiscountFee());
                ordProductVo.setCouponFee(ordOdProd.getCouponFee()); //优惠费用
                ordProductVo.setCusServiceFlag(ordOdProd.getCusServiceFlag());  //商品是否售后标识
                ordProductVo.setJfFee(ordOdProd.getJfFee()); //消费积分
                ordProductVo.setGiveJF(ordOdProd.getJf()); //赠送积分
                ordProductVo.setProdCode(ordOdProd.getProdCode()); //商品编码
                ordProductVo.setSkuStorageId(ordOdProd.getSkuStorageId());//倉庫ID
                ProductImage productImage = this.getProductImage(tenantId, ordOdProd.getSkuId());
                ordProductVo.setProductImage(productImage);
               /* ordProductVo.setProdExtendInfo(this.getProdExtendInfo(tenantId, orderId,
                        ordOdProd.getProdDetalId()));*/
                productList.add(ordProductVo);
            }

        }
        return productList;
    }

    /**
     * 
     * @param skuId
     * @return
     * @author zhangxw
     * @ApiDocMethod
     */
    private ProductImage getProductImage(String tenantId, String skuId) {
        ProductImage productImage = new ProductImage();
        SkuInfoQuery skuInfoQuery = new SkuInfoQuery();
        skuInfoQuery.setTenantId(tenantId);
        skuInfoQuery.setSkuId(skuId);
        IProductServerSV iProductServerSV = DubboConsumerFactory.getService(IProductServerSV.class);
        ProductSkuInfo productSkuInfo = iProductServerSV.queryProductSkuById(skuInfoQuery);
        productImage.setVfsId(productSkuInfo.getVfsId());
        productImage.setPicType(productSkuInfo.getPicType());
        return productImage;
    }

    /**
     * 获得手机号串
     * 
     * @param tenantId
     * @param orderId
     * @param prodDetailId
     * @return
     * @author zhangxw
     * @ApiDocMethod
     */
    private String getProdExtendInfo(String tenantId, long orderId, long prodDetailId) {
        /* 1.查询商品明细拓展表 */
        String prodExtendInfo = "";
        OrdOdProdExtendCriteria example = new OrdOdProdExtendCriteria();
        OrdOdProdExtendCriteria.Criteria criteria = example.createCriteria();
        criteria.andTenantIdEqualTo(tenantId);
        criteria.andOrderIdEqualTo(orderId);
        criteria.andProdDetalIdEqualTo(prodDetailId);
        List<OrdOdProdExtend> ordOdProdExtendList = ordOdProdExtendAtomSV.selectByExample(example);
        /* 2.遍历取出值信息 */
        StringBuffer sb = new StringBuffer();
        if (!CollectionUtil.isEmpty(ordOdProdExtendList)) {
            OrdOdProdExtend ordOdProdExtend = ordOdProdExtendList.get(0);
            String infoJson = ordOdProdExtend.getInfoJson();
            InfoJsonVo infoJsonVo = JSON.parseObject(infoJson, InfoJsonVo.class);
            List<ProdExtendInfoVo> prodExtendInfoVoList = infoJsonVo.getProdExtendInfoVoList();
            for (ProdExtendInfoVo prodExtendInfoVo : prodExtendInfoVoList) {
                sb.append(prodExtendInfoVo.getProdExtendInfoValue()).append(",");
            }
            prodExtendInfo = sb.substring(0, sb.length() - 1);
        }
        return prodExtendInfo;

    }

    /**
     * 获得手机号数量
     * 
     * @param tenantId
     * @param orderId
     * @param prodDetailId
     * @return
     * @author zhangxw
     * @ApiDocMethod
     */
    private int getProdExtendInfo(String tenantId, long orderId) {
        /* 1.查询商品明细拓展表 */
        OrdOdProdExtendCriteria example = new OrdOdProdExtendCriteria();
        OrdOdProdExtendCriteria.Criteria criteria = example.createCriteria();
        criteria.andTenantIdEqualTo(tenantId);
        criteria.andOrderIdEqualTo(orderId);
        List<OrdOdProdExtend> ordOdProdExtendList = ordOdProdExtendAtomSV.selectByExample(example);
        /* 2.遍历取出值信息 */
        int phoneNum = 0;
        if (!CollectionUtil.isEmpty(ordOdProdExtendList)) {
            for (OrdOdProdExtend ordOdProdExtend : ordOdProdExtendList) {
                String infoJson = ordOdProdExtend.getInfoJson();
                InfoJsonVo infoJsonVo = JSON.parseObject(infoJson, InfoJsonVo.class);
                List<ProdExtendInfoVo> prodExtendInfoVoList = infoJsonVo.getProdExtendInfoVoList();
                phoneNum = prodExtendInfoVoList.size() + phoneNum;
            }
        }
        return phoneNum;
    }

    @Override
    public QueryOrderResponse queryOrder(QueryOrderRequest orderRequest) throws BusinessException,
            SystemException {
        logger.debug("开始订单详情查询..");
        /* 1.订单信息查询 */
        ICacheSV iCacheSV = DubboConsumerFactory.getService(ICacheSV.class);
        QueryOrderResponse response = new QueryOrderResponse();
        OrdOrderCriteria example = new OrdOrderCriteria();
        OrdOrderCriteria.Criteria criteria = example.createCriteria();
        criteria.andTenantIdEqualTo(orderRequest.getTenantId());
        if (orderRequest.getOrderId() == 0) {
        	throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "订单id不能为空");
        }
        criteria.andOrderIdEqualTo(orderRequest.getOrderId());
        List<OrdOrder> list = ordOrderAtomSV.selectByExample(example);
        OrdOrderVo ordOrderVo = null;
        if (!CollectionUtil.isEmpty(list)) {
            OrdOrder order = list.get(0);
            ordOrderVo = new OrdOrderVo();
            ordOrderVo.setOrderId(order.getOrderId());
            ordOrderVo.setOrderType(order.getOrderType());
            SysParam sysParamOrderType = InfoTranslateUtil.translateInfo(order.getTenantId(), "ORD_ORDER",
                    "ORDER_TYPE", order.getOrderType(), iCacheSV);
            ordOrderVo.setOrderTypeName(sysParamOrderType == null ? "" : sysParamOrderType.getColumnDesc());
            ordOrderVo.setBusiCode(order.getBusiCode());
            ordOrderVo.setState(order.getState());
            SysParam sysParamState = InfoTranslateUtil.translateInfo(order.getTenantId(), "ORD_ORDER",
                    "STATE", ordOrderVo.getState(), iCacheSV);
            ordOrderVo.setStateName(sysParamState == null ? "" : sysParamState.getColumnDesc());
            ordOrderVo.setChlId(order.getChlId()); //订单来源
            ordOrderVo.setRouteId(order.getRouteId());//仓库ID
            IRouteManageSV iRouteManageSV = DubboConsumerFactory.getService(IRouteManageSV.class);
            RouteIdParamRequest routeRequest=new RouteIdParamRequest();
            if(order.getRouteId()!=null) {
            	routeRequest.setRouteId(order.getRouteId());
            	RouteResponse routeInfo = iRouteManageSV.findRouteInfo(routeRequest);
            	ordOrderVo.setRouteName(routeInfo.getRouteName()); //仓库信息
            }
            ordOrderVo.setParentOrderId(order.getParentOrderId());
            ordOrderVo.setUserId(order.getUserId());//买家帐号(用户号)
            ordOrderVo.setAccountId(order.getAccountId());
            ordOrderVo.setDownstreamOrderId(order.getDownstreamOrderId());
            JSONObject dataJson = ChUserUtil.getUserInfo(order.getUserId());
            //获取用户名
            Object userName =dataJson.get("userName");
            ordOrderVo.setUserName(userName==null?null:userName.toString()); 
            ordOrderVo.setRemark(order.getRemark());//买家留言(订单备注)
            ordOrderVo.setOrigOrderId(order.getOrigOrderId()); //原始订单号
            ordOrderVo.setOperId(order.getOperId());
            ordOrderVo.setAcctId(order.getAcctId()); 
            ordOrderVo.setOrderTime(order.getOrderTime());
            //获取业务类型
            SysParam sysParamBusiCode = InfoTranslateUtil.translateInfo(order.getTenantId(), "ORD_ORDER", "BUSI_CODE", 
            		order.getBusiCode(), iCacheSV);
            ordOrderVo.setBusiCodeName(sysParamBusiCode == null ? "" : sysParamBusiCode.getColumnDesc());
            /* 2.订单费用信息查询 */
            List<OrdOdFeeTotal> orderFeeTotalList = this.getOrderFeeTotalList(order.getTenantId(),
                    order.getOrderId(), "");
            if (!CollectionUtil.isEmpty(orderFeeTotalList)) {
                OrdOdFeeTotal ordOdFeeTotal = orderFeeTotalList.get(0);
                ordOrderVo.setAdjustFee(ordOdFeeTotal.getAdjustFee());
                ordOrderVo.setDiscountFee(ordOdFeeTotal.getDiscountFee());
                ordOrderVo.setOperDiscountFee(ordOdFeeTotal.getOperDiscountFee());
                ordOrderVo.setOperDiscountDesc(ordOdFeeTotal.getOperDiscountDesc());
                ordOrderVo.setPaidFee(ordOdFeeTotal.getPaidFee());
                ordOrderVo.setPayFee(ordOdFeeTotal.getPayFee());
                ordOrderVo.setPayStyle(ordOdFeeTotal.getPayStyle());
                SysParam sysParam = InfoTranslateUtil.translateInfo(ordOdFeeTotal.getTenantId(),
                        "ORD_OD_FEE_TOTAL", "PAY_STYLE", ordOdFeeTotal.getPayStyle(), iCacheSV);
                ordOrderVo.setPayStyleName(sysParam == null ? "" : sysParam.getColumnDesc());
                ordOrderVo.setPayTime(ordOdFeeTotal.getUpdateTime());
                ordOrderVo.setTotalFee(ordOdFeeTotal.getTotalFee());
                ordOrderVo.setFreight(ordOdFeeTotal.getFreight()); //运费
               /* int phoneCount = this.getProdExtendInfo(orderRequest.getTenantId(),
                        order.getOrderId());
                ordOrderVo.setPhoneCount(phoneCount);*/
                /* 3.订单发票信息查询*/
                OrdOdInvoice ordOdInvoice = ordOdInvoiceAtomSV.selectByPrimaryKey(order.getOrderId());
                if(ordOdInvoice !=null) {
                	ordOrderVo.setInvoiceTitle(ordOdInvoice.getInvoiceTitle());
                	ordOrderVo.setInvoiceType(ordOdInvoice.getInvoiceType());
                	SysParam sysParamInvoice = InfoTranslateUtil.translateInfo(order.getTenantId(),
                            "ORD_OD_INVOICE", "INVOICE_TYPE", ordOdInvoice.getInvoiceType(), iCacheSV);
                	ordOrderVo.setInvoiceTypeName(sysParamInvoice == null ? "" : sysParamInvoice.getColumnDesc());
                	ordOrderVo.setInvoiceContent(ordOdInvoice.getInvoiceContent());
                	ordOrderVo.setBuyerTaxpayerNumber(ordOdInvoice.getBuyerTaxpayerNumber());
                	ordOrderVo.setBuyerBankName(ordOdInvoice.getBuyerBankName());
                	ordOrderVo.setBuyerBankAccount(ordOdInvoice.getBuyerBankAccount());
                	ordOrderVo.setInvoiceStatus(ordOdInvoice.getInvoiceStatus());
                }
                /* 4.订单配送信息查询*/
                OrdOdLogistics ordOdLogistics =null;
                OrdOdLogistics afterLogistics =null;
                if(!OrdersConstants.OrdOrder.BusiCode.NORMAL_ORDER.equals(order.getBusiCode())) {
                	//售后单获取子订单配送信息
                	afterLogistics=ordOdLogisticsAtomSV.selectByOrd(order.getTenantId(), order.getOrigOrderId());
                	StringBuffer sbf=new StringBuffer();
                	sbf.append(afterLogistics.getProvinceCode()==null?"":iCacheSV.
                			getAreaName(afterLogistics.getProvinceCode()));
                	sbf.append(afterLogistics.getCityCode()==null?"":iCacheSV.
                			getAreaName(afterLogistics.getCityCode()));
                	sbf.append(afterLogistics.getCountyCode()==null?"":iCacheSV.
                			getAreaName(afterLogistics.getCountyCode()));
                	sbf.append(afterLogistics.getAddress());
                	ordOrderVo.setAftercontactTel(afterLogistics.getContactTel());
                	ordOrderVo.setAftercontactInfo(sbf.toString());
                }
                ordOdLogistics=ordOdLogisticsAtomSV.selectByOrd(order.getTenantId(), order.getOrderId());
                if(ordOdLogistics!=null) {
                	ordOrderVo.setExpressOddNumber(ordOdLogistics.getExpressOddNumber());
                	ordOrderVo.setContactCompany(ordOdLogistics.getContactCompany());
                	ordOrderVo.setLogisticsType(ordOdLogistics.getLogisticsType());
                	ordOrderVo.setContactName(ordOdLogistics.getContactName());
                	ordOrderVo.setContactTel(ordOdLogistics.getContactTel());
                	ordOrderVo.setProvinceCode(ordOdLogistics.getProvinceCode()==null?"":iCacheSV.
                			getAreaName(ordOdLogistics.getProvinceCode()));
                	ordOrderVo.setCityCode(ordOdLogistics.getCityCode()==null?"":iCacheSV.
                			getAreaName(ordOdLogistics.getCityCode()));
                	ordOrderVo.setCountyCode(ordOdLogistics.getCountyCode()==null?"":iCacheSV.
                			getAreaName(ordOdLogistics.getCountyCode()));
                	ordOrderVo.setPostCode(ordOdLogistics.getPostcode());
                	ordOrderVo.setAreaCode(ordOdLogistics.getAreaCode()==null?"":iCacheSV.
                			getAreaName(ordOdLogistics.getAreaCode()));
                	ordOrderVo.setAddress(ordOdLogistics.getAddress());
                	ordOrderVo.setExpressId(ordOdLogistics.getExpressId());
                }
                /* 5.订单费用明细查询 */
                if(OrdersConstants.OrdOrder.State.WAIT_PAY.equals(order.getState())||
                		OrdersConstants.OrdOrder.State.CANCEL.equals(order.getState())) {
                	List<OrderPayVo> orderFeeProdList = this.getOrderFeeProdList(iCacheSV,
                			order.getOrderId(),order.getTenantId());
                	ordOrderVo.setPayDataList(orderFeeProdList);
                }else {
                	List<OrderPayVo> orderFeeProdList = this.getOrderFeeProdList(iCacheSV,
                			order.getParentOrderId(),order.getTenantId());
                	ordOrderVo.setPayDataList(orderFeeProdList);
                }
                /* 6.订单商品明细查询 */
                List<OrdProductVo> productList = this.getOrdProductList(order.getTenantId(), order.getOrderId());
                ordOrderVo.setProductList(productList);
                /* 7.订单支付机构查询*/
                OrdBalacneIfCriteria exampleBalance=new OrdBalacneIfCriteria();
                Criteria criteriaBalance = exampleBalance.createCriteria();
                criteriaBalance.andTenantIdEqualTo(order.getTenantId());
                if(OrdersConstants.OrdOrder.State.WAIT_PAY.equals(order.getState())||
                		OrdersConstants.OrdOrder.State.CANCEL.equals(order.getState())) {
                	criteriaBalance.andOrderIdEqualTo(order.getOrderId());
                }else {
                	criteriaBalance.andOrderIdEqualTo(order.getParentOrderId());
                }
                List<OrdBalacneIf> ordBalacneIfs = ordBalacneIfAtomSV.selectByExample(exampleBalance);
                if(!CollectionUtil.isEmpty(ordBalacneIfs)) {
                	OrdBalacneIf ordBalacneIf = ordBalacneIfs.get(0);
                	ordOrderVo.setBalacneIfId(ordBalacneIf.getBalacneIfId());
                	ordOrderVo.setExternalId(ordBalacneIf.getExternalId());
                }
            }
        }
        response.setOrdOrderVo(ordOrderVo);
        return response;
    }
    
    @Override
    public QueryApiOrderResponse queryApiOrder(QueryApiOrderRequest orderRequest)
            throws BusinessException, SystemException {
    	logger.info("api查询业务...");
        QueryApiOrderResponse response= new QueryApiOrderResponse();
		OrdOrderCriteria example = new OrdOrderCriteria();
		OrdOrderCriteria.Criteria criteria = example.createCriteria();
		if(StringUtil.isBlank(orderRequest.getTenantId())) {
			throw new BusinessException(ErrorCodeConstants.REQUIRED_IS_EMPTY, "租户id为空");
		}
		criteria.andTenantIdEqualTo(orderRequest.getTenantId());
		if (StringUtil.isBlank(orderRequest.getDownstreamOrderId())) {
			throw new BusinessException(ErrorCodeConstants.REQUIRED_IS_EMPTY, "外部订单id(下游)为空");
		}
		criteria.andDownstreamOrderIdEqualTo(orderRequest.getDownstreamOrderId());
		if (StringUtil.isBlank(orderRequest.getUserId())) {
			throw new BusinessException(ErrorCodeConstants.REQUIRED_IS_EMPTY, "用户id为空");
		}
		criteria.andUserIdEqualTo(orderRequest.getUserId());
		criteria.andSubFlagEqualTo(OrdersConstants.OrdOrder.SubFlag.YES);
		List<OrdOrder> list = ordOrderAtomSV.selectByExample(example);
		List<OrdOrderApiVo> ordOrderApiVos=null;
		OrdOrderApiVo orderApiVo=null;
		if(CollectionUtil.isEmpty(list)) {
			throw new BusinessException(ErrorCodeConstants.Order.ORDER_NO_EXIST, "订单不存在");
		}else{
		    try {
		    	ordOrderApiVos=new ArrayList<OrdOrderApiVo>();
		    	for (OrdOrder ordOrder : list) {
		    		/* 1.订单费用信息查询 */
		    		List<OrdOdFeeTotal> orderFeeTotalList = this.getOrderFeeTotalList(ordOrder.getTenantId(),
		    				ordOrder.getOrderId(), "");
		    		if(!CollectionUtil.isEmpty(orderFeeTotalList)) {
		    			OrdOdFeeTotal ordOdFeeTotal = orderFeeTotalList.get(0);
		    			orderApiVo=new OrdOrderApiVo();
		    			orderApiVo.setTenantId(ordOrder.getTenantId());
		    			orderApiVo.setUserId(ordOrder.getUserId());
		    			orderApiVo.setAcctId(ordOrder.getAcctId());
		    			orderApiVo.setSubsId(ordOrder.getSubsId());
		    			orderApiVo.setOrderType(ordOrder.getOrderType());
		    			orderApiVo.setDeliveryFlag(ordOrder.getDeliveryFlag());
		    			orderApiVo.setOrderDesc(ordOrder.getOrderDesc());
		    			orderApiVo.setKeywords(ordOrder.getKeywords());
		    			orderApiVo.setRemark(ordOrder.getRemark());
		    			orderApiVo.setState(ordOrder.getState());
		    			orderApiVo.setBusiCode(ordOrder.getBusiCode());
		    			orderApiVo.setPayStyle(ordOdFeeTotal.getPayStyle());
		    			orderApiVo.setTotalFee(ordOdFeeTotal.getTotalFee());
		    			orderApiVo.setDiscountFee(ordOdFeeTotal.getDiscountFee());
		    			orderApiVo.setOperDiscountFee(ordOdFeeTotal.getOperDiscountFee());
		    			orderApiVo.setOperDiscountDesc(ordOdFeeTotal.getOperDiscountDesc());
		    			orderApiVo.setAdjustFee(ordOdFeeTotal.getAdjustFee());
		    			orderApiVo.setPaidFee(ordOdFeeTotal.getPaidFee());
		    			/*2.订单商品明细及扩展信息查询*/
		    			this.getOrdProductApiList(ordOrder.getTenantId(), ordOrder.getOrderId(),orderApiVo);
		    			ordOrderApiVos.add(orderApiVo);
		    		}
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
				throw new SystemException(ErrorCodeConstants.SYSTEM_ERROR, "系统异常");
			}
		}
		response.setOrderApiVo(ordOrderApiVos);
		return response;
     }
    
    
    @Override
    public BehindQueryOrderListResponse behindQueryOrderList(BehindQueryOrderListRequest orderListRequest)
            throws BusinessException, SystemException {
    	long start=System.currentTimeMillis();
    	logger.info("开始执行dubbo订单列表查询behindQueryOrderList，当前时间戳："+start);
        logger.debug("开始运营后台订单列表查询..");
        /* 参数校验*/
        if(orderListRequest==null) {
        	throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "参数对象不能为空");
        }
        CommonCheckUtils.checkTenantId(orderListRequest.getTenantId(), ExceptCodeConstants.Special.PARAM_IS_NULL);
        ICacheSV iCacheSV = DubboConsumerFactory.getService(ICacheSV.class);
        BehindQueryOrderListResponse response = new BehindQueryOrderListResponse();
        PageInfo<BehindParentOrdOrderVo> pageInfo = new PageInfo<BehindParentOrdOrderVo>();
        String states = "";
        StringBuffer sb = new StringBuffer("");
        List<String> stateList = orderListRequest.getStateList();
        if (!CollectionUtil.isEmpty(stateList)) {
            for (String state : stateList) {
                sb = sb.append(state).append(",");
            }
            states = sb.toString();
            states = states.substring(0, sb.length() - 1);
        }
        long countStart=System.currentTimeMillis();
    	logger.info("开始执行dubbo后场订单列表查询behindQueryOrderList，获取订单列表数量,当前时间戳："+countStart);
        int count=ordOrderAttachAtomSV.behindQueryCount(orderListRequest, states);
        long countEnd=System.currentTimeMillis();
    	logger.info("开始执行dubbo后场订单列表查询behindQueryOrderList，获取订单列表数量,当前时间戳："+countEnd+",用时:"+(countEnd-countStart)+"毫秒");
        List<BehindParentOrdOrderVo> orderVoList = 
        		this.getBehindOrdOrderVos(orderListRequest, states, iCacheSV);
        pageInfo.setPageNo(orderListRequest.getPageNo());
        pageInfo.setPageSize(orderListRequest.getPageSize());
        pageInfo.setResult(orderVoList);
        pageInfo.setCount(count);
        response.setPageInfo(pageInfo);
        return response;
    }
    
    
    /**
     * 商品集合API
     */
    private void getOrdProductApiList(String tenantId, long orderId,OrdOrderApiVo orderApiVo) { 
        OrdOdProdCriteria example = new OrdOdProdCriteria();
        OrdOdProdCriteria.Criteria criteria = example.createCriteria();
        criteria.andTenantIdEqualTo(tenantId);
        criteria.andOrderIdEqualTo(orderId);
        List<OrdOdProd> ordOdProdApiList = ordOdProdAtomSV.selectByExample(example);
        if(!CollectionUtil.isEmpty(ordOdProdApiList)) {
        	OrdOdProd ordOdProd = ordOdProdApiList.get(0);
        	OrdProductApiVo ordProductApiVo=new OrdProductApiVo();
        	ordProductApiVo.setProdId(ordOdProd.getProdId());
        	ordProductApiVo.setProdName(ordOdProd.getProdName());
        	ordProductApiVo.setBuySum(ordOdProd.getBuySum());
        	ordProductApiVo.setTotalFee(ordOdProd.getTotalFee());
        	ordProductApiVo.setDiscountFee(ordOdProd.getDiscountFee());
        	ordProductApiVo.setOperDiscountFee(ordOdProd.getOperDiscountFee());
        	ordProductApiVo.setOperDiscountDesc(ordOdProd.getOperDiscountDesc());
        	ordProductApiVo.setAdjustFee(ordOdProd.getAdjustFee());
        		//文档上返回报文体没有的
        		String extendInfo = ordOdProd.getExtendInfo();
        		ProdAttrInfoVo prodAttrInfoVo = JSON.parseObject(extendInfo, ProdAttrInfoVo.class);
        		ordProductApiVo.setBasicOrgId(prodAttrInfoVo.getBasicOrgId());
        		ICacheSV iCacheSV = DubboConsumerFactory.getService(ICacheSV.class);
        		SysParam sysParam = InfoTranslateUtil.translateInfo(ordOdProd.getTenantId(), "PRODUCT",
                        "BASIC_ORG_ID", prodAttrInfoVo.getBasicOrgId(), iCacheSV);
                ordProductApiVo.setBasicOrgName(sysParam == null ? "" : sysParam.getColumnDesc());
                String provinceName = "";
                if (!StringUtil.isBlank(prodAttrInfoVo.getProvinceCode())) {
                    provinceName = iCacheSV.getAreaName(prodAttrInfoVo.getProvinceCode());
                }
                ordProductApiVo.setProvinceName(provinceName);
                ordProductApiVo.setProdName(ordOdProd.getProdName());
                ordProductApiVo.setSkuId(ordOdProd.getSkuId());
                ordProductApiVo.setChargeFee(prodAttrInfoVo.getChargeFee());
                ProductImage productImage = this.getProductImage(tenantId, ordOdProd.getSkuId());
                ordProductApiVo.setProductImage(productImage);
                orderApiVo.setOrdProductApiVo(ordProductApiVo);
                //附加信息
                List<OrdOdProdExtend> ordOdProdExtendList = this.getOrdOdProdExtendList(tenantId,
                		orderId,ordOdProd.getProdDetalId());
                if(!CollectionUtil.isEmpty(ordOdProdExtendList)) {
                	OrdOdProdExtend ordOdProdExtend = ordOdProdExtendList.get(0);
                	orderApiVo.setInfoJson(ordOdProdExtend.getInfoJson());
                }
        }
    }
    
    /**
     * 订单商品明细信息扩展表
     */
    public List<OrdOdProdExtend> getOrdOdProdExtendList(String tenantId,
    		long orderId,long prodDetalId) {
    	OrdOdProdExtendCriteria example=new OrdOdProdExtendCriteria();
    	OrdOdProdExtendCriteria.Criteria criteria= example.createCriteria();
    	criteria.andTenantIdEqualTo(tenantId);
    	criteria.andOrderIdEqualTo(orderId);
    	criteria.andProdDetalIdEqualTo(prodDetalId);
    	List<OrdOdProdExtend> ordOdProdExtendList = 
    			ordOdProdExtendAtomSV.selectByExample(example);
    	return ordOdProdExtendList;
    }
    
    /**
     * 运营后台列表信息
     */
    private List<BehindParentOrdOrderVo> getBehindOrdOrderVos(BehindQueryOrderListRequest orderListRequest,
    		String states,ICacheSV iCacheSV) {
    	List<BehindParentOrdOrderVo> orderVoList=new ArrayList<BehindParentOrdOrderVo>();
    	long infoStart=System.currentTimeMillis();
     	logger.info("开始执行dubbo后场订单列表查询behindQueryOrderList，获取订单列表信息,当前时间戳："+infoStart);
    	List<BehindOrdOrderAttach> parentList = 
    			ordOrderAttachAtomSV.behindQueryOrderBySearch(orderListRequest, states);
    	long infoEnd=System.currentTimeMillis();
     	logger.info("开始执行dubbo后场订单列表查询behindQueryOrderList，获取订单列表信息,当前时间戳："+infoEnd+",用时:"+(infoEnd-infoStart)+"毫秒");
        if(!CollectionUtil.isEmpty(parentList)) {
        	for (BehindOrdOrderAttach behindOrdOrderAttach : parentList) {
        		BehindParentOrdOrderVo pOrderVo=new BehindParentOrdOrderVo();
        		List<BehindOrdOrderVo> orderList=new ArrayList<BehindOrdOrderVo>();
        		pOrderVo.setpOrderId(behindOrdOrderAttach.getOrderId());
        		pOrderVo.setChlId(behindOrdOrderAttach.getChlId());
        		SysParam sysParamChlId = InfoTranslateUtil.translateInfo(behindOrdOrderAttach.getTenantId(), 
        				"ORD_ORDER", "CHL_ID", behindOrdOrderAttach.getChlId(), iCacheSV);
        		pOrderVo.setChlIdName(sysParamChlId==null?"":sysParamChlId.getColumnDesc());
        		pOrderVo.setContactTel(behindOrdOrderAttach.getContactTel());
        		pOrderVo.setUserId(behindOrdOrderAttach.getUserId());
        		long userStart=System.currentTimeMillis();
        		logger.info("开始执行dubbo后场订单列表查询behindQueryOrderList,通过O2p获取用户信息，当前时间戳："+userStart);
        		JSONObject dataJson = ChUserUtil.getUserInfo(behindOrdOrderAttach.getUserId());
        		//获取用户名
        		Object userName =dataJson.get("userName");
        		pOrderVo.setUserName(userName==null?null:userName.toString()); 
        		//获取绑定手机号
       	        Object phone =dataJson.get("phone");
       	        long userEnd=System.currentTimeMillis();
       	        logger.info("开始执行dubbo后场订单列表查询behindQueryOrderList,通过O2p获取用户信息，当前时间戳："+userEnd+
       	        		",用时:"+(userEnd-userStart)+"毫秒");
       	        pOrderVo.setUserTel(phone==null?null:phone.toString());
        		pOrderVo.setDeliveryFlag(behindOrdOrderAttach.getDeliveryFlag());
        		SysParam sysParamDf = InfoTranslateUtil.translateInfo(behindOrdOrderAttach.getTenantId(), 
        				"ORD_ORDER", "ORD_DELIVERY_FLAG", behindOrdOrderAttach.getDeliveryFlag(), iCacheSV);
        		pOrderVo.setDeliveryFlagName(sysParamDf==null?"":sysParamDf.getColumnDesc());
        		String arr="21,212,213,312,22,23,31,92,93,94,95";  //售后状态
        		boolean flag=arr.equals(states);
        		if(!flag) {
        			pOrderVo.setAdjustFee(behindOrdOrderAttach.getAdjustFee());
        			pOrderVo.setDiscountFee(behindOrdOrderAttach.getDiscountFee());//优惠金额
        		}
    			OrdOrderCriteria exampleOrder=new OrdOrderCriteria();
    			OrdOrderCriteria.Criteria criteriaOrder = exampleOrder.createCriteria();
    			criteriaOrder.andParentOrderIdEqualTo(behindOrdOrderAttach.getOrderId());
    			criteriaOrder.andTenantIdEqualTo(orderListRequest.getTenantId());
    			if(!StringUtil.isBlank(orderListRequest.getRouteId())) {
    				criteriaOrder.andRouteIdEqualTo(orderListRequest.getRouteId());
    			}
    			if(!StringUtil.isBlank(states)) {
    				String[] strState = states.split(",");
            		List<String> asList = Arrays.asList(strState); 
    				criteriaOrder.andStateIn(asList);
    			}
    			if(!flag) {
    				criteriaOrder.andCusServiceFlagEqualTo(OrdersConstants.OrdOrder.cusServiceFlag.NO);
    			}else {
    				criteriaOrder.andCusServiceFlagEqualTo(OrdersConstants.OrdOrder.cusServiceFlag.YES); 
    			}
    			List<OrdOrder> orders= ordOrderAtomSV.selectByExample(exampleOrder);
    			int totalProdSize=0;
    			if(CollectionUtil.isEmpty(orders)) {  
    				BehindOrdOrderVo orderVo=new BehindOrdOrderVo();
        			/* 查询父订单下的商品信息*/
        			List<BehindOrdProductVo> prodList = this.getProdList(null,orderListRequest,behindOrdOrderAttach,null);
        			orderVo.setProductList(prodList);
        			orderVo.setProdSize(prodList.size());
        			totalProdSize=prodList.size()+totalProdSize;
        			orderVo.setState(behindOrdOrderAttach.getState());
        			orderVo.setParentOrderId(behindOrdOrderAttach.getOrderId());
        			SysParam sysParamState = InfoTranslateUtil.translateInfo(orderListRequest.getTenantId(), "ORD_ORDER",
    						"STATE", behindOrdOrderAttach.getState(), iCacheSV);
    				orderVo.setStateName(sysParamState == null ? "" : sysParamState.getColumnDesc());
        			orderList.add(orderVo);
        			pOrderVo.setOrderList(orderList);
    			}else {
    				for (OrdOrder ordOrder : orders) {
    					BehindOrdOrderVo orderVo=new BehindOrdOrderVo();
    					//订单查询OFC
    					if(OrdersConstants.OrdOrder.Flag.OFC.equals(ordOrder.getFlag())) {
    						String logisticsName = null;
							String logisticsNo = null;
    						JSONObject object = queryOFC(ordOrder);
    						//TODO 判断
    					/*	if(object==null) {
    							
    						}else {}*/
    						JSONArray jsonArray = object.getJSONArray("OrderList");
    						for (int i = 0; i < jsonArray.size(); i++) {
    							JSONObject  jsonObject = (JSONObject) jsonArray.get(i);
    							String state = jsonObject.getString("DeliveryState");
    							JSONArray shipArray = jsonObject.getJSONArray("ShipOrderList");
    							for (int j = 0; j < shipArray.size(); j++) {
    								JSONObject  shipObject = (JSONObject) jsonArray.get(i);
    								logisticsName = shipObject.getString("LogisticsName");
    								logisticsNo = shipObject.getString("LogisticsNo");
								}
    							if(OrdersConstants.OFCDeliveryState.ALREADY_DELIVER_GOODS.equals(state)||
    									OrdersConstants.OFCDeliveryState.ALREADY_RECEIVE_GOODS.equals(state)||
    									OrdersConstants.OFCDeliveryState.PART_DELIVER_GOODS.equals(state)) {
    								OrdOdLogistics ordOdLogistics = ordOdLogisticsAtomSV.selectByOrd(ordOrder.getTenantId(), 
    										ordOrder.getOrderId());
    								if(ordOdLogistics==null) {
    									logger.error("配送信息不存在");
    									throw new BusinessException(ExceptCodeConstants.Special.NO_RESULT, 
    											"配送信息不存在[订单id:"+ordOrder.getOrderId()+"]");
    								}
    								ordOrder.setState(OrdersConstants.OrdOrder.State.WAIT_CONFIRM);
    								ordOdLogistics.setExpressOddNumber(logisticsNo);
    								ordOdLogistics.setContactCompany(logisticsName);//物流商
    								ordOrderAtomSV.updateById(ordOrder);
    								ordOdLogisticsAtomSV.updateByPrimaryKey(ordOdLogistics);
    							}
    						}
    					}
    					orderVo.setOrderId(ordOrder.getOrderId());
    					orderVo.setState(ordOrder.getState());
    					orderVo.setBusiCode(ordOrder.getBusiCode());
    					SysParam sysParamState = InfoTranslateUtil.translateInfo(ordOrder.getTenantId(), "ORD_ORDER",
    							"STATE", ordOrder.getState(), iCacheSV);
    					orderVo.setStateName(sysParamState == null ? "" : sysParamState.getColumnDesc());
    					List<BehindOrdProductVo> prodList = this.getProdList(ordOrder,orderListRequest, behindOrdOrderAttach,ordOrder.getOrderId());
    					orderVo.setProdSize(prodList.size());
    					totalProdSize=prodList.size()+totalProdSize;
    					orderVo.setProductList(prodList);
    					orderList.add(orderVo);
    				}
    				pOrderVo.setOrderList(orderList);
    			}
        		if(!flag) {
        			OrdOdFeeProdCriteria exampleFeeProd = new OrdOdFeeProdCriteria();
        			OrdOdFeeProdCriteria.Criteria criteriaFeeProd = exampleFeeProd.createCriteria();
        			criteriaFeeProd.andOrderIdEqualTo(behindOrdOrderAttach.getOrderId()); //父订单id
        			List<OrdOdFeeProd> orderFeeProdList = ordOdFeeProdAtomSV.selectByExample(exampleFeeProd);
        			long points = 0; //积分
        			//long totalCouponFee=0;//优惠券
        			if (!CollectionUtil.isEmpty(orderFeeProdList)) {
        				for (OrdOdFeeProd ordOdFeeProd : orderFeeProdList) { 
        					if(OrdersConstants.OrdOdFeeProd.PayStyle.JF.equals(ordOdFeeProd.getPayStyle())) {
        						points+=ordOdFeeProd.getPaidFee();
        					}
        				/*	if(OrdersConstants.OrdOdFeeProd.PayStyle.COUPON.equals(ordOdFeeProd.getPayStyle())) {
        						totalCouponFee+=ordOdFeeProd.getPaidFee();
        					}*/
        				}
        			}
        			pOrderVo.setPoints(points);
        			//pOrderVo.setTotalCouponFee(totalCouponFee);
        		}
        	pOrderVo.setTotalProdSize(totalProdSize);
    		orderVoList.add(pOrderVo);
         }
       }
       return orderVoList;
    }
    
    
    /**
     * 订单下的商品明细信息
     */
    private List<BehindOrdProductVo> getProdList(OrdOrder order,BehindQueryOrderListRequest orderListRequest,
    		BehindOrdOrderAttach behindOrdOrderAttach,Long orderId) {
		List<BehindOrdProductVo> productList=new ArrayList<BehindOrdProductVo>();
		OrdOdProdCriteria example=new OrdOdProdCriteria();
		OrdOdProdCriteria.Criteria criteria = example.createCriteria();
		criteria.andTenantIdEqualTo(orderListRequest.getTenantId());
		if(order==null) {
			criteria.andOrderIdEqualTo(behindOrdOrderAttach.getOrderId());
		}else {
			criteria.andOrderIdEqualTo(orderId);
		}
		List<OrdOdProd> OrdOdProds = ordOdProdAtomSV.selectByExample(example);
		for (OrdOdProd ordOdProd : OrdOdProds) {
			BehindOrdProductVo vo=new BehindOrdProductVo();
			vo.setBuySum(ordOdProd.getBuySum());
			vo.setProdName(ordOdProd.getProdName());
			productList.add(vo);
		}
		return productList;
    }

	@Override
	public int updateOrder(OrdOrder request) throws BusinessException, SystemException {
		/* 获取售后订单*/
		OrdOrder afterOrdOrder = ordOrderAtomSV.selectByOrderId(request.getTenantId(),request.getOrderId());
		if(afterOrdOrder==null) {
			throw new BusinessException(ExceptCodeConstants.Special.NO_RESULT, 
					"订单信息不存在[订单id:"+afterOrdOrder.getOrigOrderId()+"]");
		}
		//设置售后订单状态
		afterOrdOrder.setState(request.getState()); 
		//处理中 退款失败状态 不修改子父订单状态
		if(!(OrdersConstants.OrdOrder.State.IN_PROCESS.equals(request.getState())||
				OrdersConstants.OrdOrder.State.REFUND_FAILD.equals(request.getState()))) {
			if(OrdersConstants.OrdOrder.BusiCode.UNSUBSCRIBE_ORDER.equals(afterOrdOrder.getBusiCode())) {
				if(OrdersConstants.OrdOrder.State.EXCHANGE_AUDIT.equals(request.getState())) {
					afterOrdOrder.setState(OrdersConstants.OrdOrder.State.FINISH_REFUND); //退货完成
				}
			}
			/* 获取子订单信息及子订单下的商品明细信息*/
			OrdOrder order = ordOrderAtomSV.selectByOrderId(request.getTenantId(), 
					afterOrdOrder.getOrigOrderId());
			List<OrdOdProd> prodList = ordOdProdAtomSV.selectByOrd(request.getTenantId(), 
					afterOrdOrder.getOrigOrderId());
			boolean cusFlag=false;
			for (OrdOdProd ordOdProd : prodList) {
				if(OrdersConstants.OrdOrder.cusServiceFlag.YES.equals(ordOdProd.getCusServiceFlag())) {
					cusFlag=true;
				}else {
					cusFlag=false;
					break;
				}
			}
			/* 获取子订单下的所有售后订单*/
			OrdOrderCriteria example=new OrdOrderCriteria();
			OrdOrderCriteria.Criteria criteria = example.createCriteria();
			criteria.andOrigOrderIdEqualTo(afterOrdOrder.getOrigOrderId());
			criteria.andOrderIdNotEqualTo(request.getOrderId());
			List<OrdOrder> orderList = ordOrderAtomSV.selectByExample(example);
			OrdOrder parentOrder = ordOrderAtomSV.selectByOrderId(request.getTenantId(), 
					order.getParentOrderId()); //父订单
			if(cusFlag) { 
				if(CollectionUtil.isEmpty(orderList)) {
					//一个商品时.没有售后订单,商品售后标识Y
					//1.无Y --无售后订单 商品Y标识
					order.setState(OrdersConstants.OrdOrder.State.COMPLETED);
					ordOrderAtomSV.updateById(order);
					parentOrder.setState(OrdersConstants.OrdOrder.State.COMPLETED);  
					ordOrderAtomSV.updateById(parentOrder); 
				}else {
					//2.有Y --有售后订单,商品标识Y
					//判断售后订单为已完成状态或者审核失败则改变状态
					for (OrdOrder ordOrder : orderList) {  
						String state = ordOrder.getState();
						if(OrdersConstants.OrdOrder.State.FINISH_REFUND.equals(state)||
								OrdersConstants.OrdOrder.State.EXCHANGE_AUDIT.equals(state)||
								OrdersConstants.OrdOrder.State.REFUND_AUDIT.equals(state)||
								OrdersConstants.OrdOrder.State.AUDIT_FAILURE.equals(state)||
								OrdersConstants.OrdOrder.State.AUDIT_AGAIN_FAILURE.equals(state)) { 
							order.setState(OrdersConstants.OrdOrder.State.COMPLETED);
							ordOrderAtomSV.updateById(order);
							parentOrder.setState(OrdersConstants.OrdOrder.State.COMPLETED);
							ordOrderAtomSV.updateById(parentOrder); 
						}
					}
				}
			}else if(!cusFlag) { 
				//发货后状态
				if(!(OrdersConstants.OrdOrder.State.WAIT_DISTRIBUTION.equals(order.getState())||
						OrdersConstants.OrdOrder.State.WAIT_DELIVERY.equals(order.getState())||
						OrdersConstants.OrdOrder.State.WAIT_SEND.equals(order.getState()))) {
					//1.无N --无售后订单,存在商品标识N
					//发货后改变状态
					if(CollectionUtil.isEmpty(orderList)) {
						order.setState(OrdersConstants.OrdOrder.State.COMPLETED);
						ordOrderAtomSV.updateById(order);
						parentOrder.setState(OrdersConstants.OrdOrder.State.COMPLETED);
						ordOrderAtomSV.updateById(parentOrder); 
						//4.有N --有售后订单 存在商品标识N
						//发货后状态 
						//判断售后订单为已完成状态或者审核失败则 改变状态
					}else {
						for (OrdOrder ordOrder : orderList) {  
							String state = ordOrder.getState();
							//表示售后订单为已完成状态或者审核失败
							if(OrdersConstants.OrdOrder.State.FINISH_REFUND.equals(state)||
									OrdersConstants.OrdOrder.State.EXCHANGE_AUDIT.equals(state)||
									OrdersConstants.OrdOrder.State.REFUND_AUDIT.equals(state)||
									OrdersConstants.OrdOrder.State.AUDIT_FAILURE.equals(state)||
									OrdersConstants.OrdOrder.State.AUDIT_AGAIN_FAILURE.equals(state)) { 
								order.setState(OrdersConstants.OrdOrder.State.COMPLETED);
								ordOrderAtomSV.updateById(order);
								parentOrder.setState(OrdersConstants.OrdOrder.State.COMPLETED);
								ordOrderAtomSV.updateById(parentOrder); 
							}
						}
					}
				}
			}
		}
		return ordOrderAtomSV.updateById(afterOrdOrder);
	}
	
	/**
	 * OFC订单查询
	 */
	public static JSONObject queryOFC(OrdOrder ordOrder) throws BusinessException, SystemException {
		List<String> orderNoList = new ArrayList<String>();
		orderNoList.add(String.valueOf(ordOrder.getOrderId()));
		Map<String,Object> mapField=new HashMap<String,Object>();
		mapField.put("OrderNoList", orderNoList);
		//mapField.put("ShopName", "长虹官方旗舰店");
		mapField.put("PageIndex", "1");
		mapField.put("PageSize", "1");
		String params=JSON.toJSONString(mapField);
		Map<String, String> header=new HashMap<String, String>(); 
		header.put("appkey", OrdersConstants.OFC_APPKEY);
		JSONObject object =null;
		//发送Post请求,并返回信息
		try {
			String strData = HttpClientUtil.sendPost(OrdersConstants.OFC_QUERY_URL, params, header);
			object = JSON.parseObject(strData);
			//TODO 是否判断
		/*	if(object==null) {
				
			}else {}*/
			boolean val = object.getBooleanValue("IsValid");//操作是否成功
			if(!val) {
				throw new BusinessException("", "OFC订单查询失败");
			}
		} catch (IOException | URISyntaxException e) {
			logger.error(e.getMessage());
			throw new SystemException("", "OFC订单查询出现异常");
		}
		return object;
	}
}
