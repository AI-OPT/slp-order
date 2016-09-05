package com.ai.slp.order.service.business.impl;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.ai.opt.sdk.util.StringUtil;
import com.ai.platform.common.api.cache.interfaces.ICacheSV;
import com.ai.platform.common.api.cache.param.SysParam;
import com.ai.platform.common.api.cache.param.SysParamSingleCond;
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
import com.ai.slp.order.dao.mapper.bo.OrdOdLogisticsCriteria;
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
import com.ai.slp.order.util.CommonCheckUtils;
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
            ordOrderVo.setState(orderAttach.getState());
            SysParam sysParamState = this.translateInfo(orderAttach.getTenantId(), "ORD_ORDER", "STATE", 
            		orderAttach.getState(), iCacheSV);
            ordOrderVo.setStateName(sysParamState == null ? "" : sysParamState.getColumnDesc());
            ordOrderVo.setOrderTime(orderAttach.getOrderTime());
            ordOrderVo.setAdjustFee(orderAttach.getAdjustFee());
            ordOrderVo.setDiscountFee(orderAttach.getDiscountFee());
            ordOrderVo.setPaidFee(orderAttach.getPaidFee());
            ordOrderVo.setPayFee(orderAttach.getPayFee());
            ordOrderVo.setPayStyle(orderAttach.getPayStyle());
            SysParam sysParamPayStyle = this.translateInfo(orderAttach.getTenantId(), "ORD_OD_FEE_TOTAL",
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
            List<OrdProductVo> productList = this.getOrdProductList(iCacheSV,
                    orderAttach.getTenantId(), orderAttach.getOrderId());
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
                SysParam sysParam = this.translateInfo(tenantId,
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
    private List<OrdProductVo> getOrdProductList(ICacheSV iCacheSV, String tenantId, long orderId) {
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
                ordProductVo.setJfFee(ordOdProd.getJfFee()); //积分
                ProductImage productImage = this.getProductImage(tenantId, ordOdProd.getSkuId());
                ordProductVo.setProductImage(productImage);
                ordProductVo.setProdExtendInfo(this.getProdExtendInfo(tenantId, orderId,
                        ordOdProd.getProdDetalId()));
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
        logger.debug("开始订单详情询..");
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
            SysParam sysParamOrderType = this.translateInfo(order.getTenantId(), "ORD_ORDER",
                    "ORDER_TYPE", order.getOrderType(), iCacheSV);
            ordOrderVo.setOrderTypeName(sysParamOrderType == null ? "" : sysParamOrderType.getColumnDesc());
            ordOrderVo.setBusiCode(order.getBusiCode());
            ordOrderVo.setState(order.getState());
            SysParam sysParamState = this.translateInfo(order.getTenantId(), "ORD_ORDER",
                    "STATE", ordOrderVo.getState(), iCacheSV);
            ordOrderVo.setStateName(sysParamState == null ? "" : sysParamState.getColumnDesc());
            ordOrderVo.setChlId(order.getChlId()); //订单来源
            ordOrderVo.setRouteId(order.getRouteId());//仓库ID
            IRouteManageSV iRouteManageSV = DubboConsumerFactory.getService(IRouteManageSV.class);
            RouteIdParamRequest routeRequest=new RouteIdParamRequest();
            routeRequest.setRouteId(order.getRouteId());
            RouteResponse routeInfo = iRouteManageSV.findRouteInfo(routeRequest);
            ordOrderVo.setRouteName(routeInfo.getRouteName()); //仓库信息
            ordOrderVo.setParentOrderId(order.getParentOrderId());
            ordOrderVo.setUserId(order.getUserId());//买家帐号(用户号)
            ordOrderVo.setRemark(order.getRemark());//买家留言(订单备注)
            ordOrderVo.setOrigOrderId(order.getOrigOrderId()); //原始订单号
            ordOrderVo.setAcctId(order.getAcctId()); 
            ordOrderVo.setOrderTime(order.getOrderTime());
            /* 2.订单费用信息查询 */
            List<OrdOdFeeTotal> orderFeeTotalList = this.getOrderFeeTotalList(order.getTenantId(),
                    order.getOrderId(), "");
            if (!CollectionUtil.isEmpty(orderFeeTotalList)) {
                OrdOdFeeTotal ordOdFeeTotal = orderFeeTotalList.get(0);
                ordOrderVo.setAdjustFee(ordOdFeeTotal.getAdjustFee());
                ordOrderVo.setDiscountFee(ordOdFeeTotal.getDiscountFee());
                ordOrderVo.setOperDiscountFee(ordOdFeeTotal.getOperDiscountFee());
                ordOrderVo.setPaidFee(ordOdFeeTotal.getPaidFee());
                ordOrderVo.setPayFee(ordOdFeeTotal.getPayFee());
                ordOrderVo.setPayStyle(ordOdFeeTotal.getPayStyle());
                SysParam sysParam = this.translateInfo(ordOdFeeTotal.getTenantId(),
                        "ORD_OD_FEE_TOTAL", "PAY_STYLE", ordOdFeeTotal.getPayStyle(), iCacheSV);
                ordOrderVo.setPayStyleName(sysParam == null ? "" : sysParam.getColumnDesc());
                ordOrderVo.setPayTime(ordOdFeeTotal.getUpdateTime());
                ordOrderVo.setTotalFee(ordOdFeeTotal.getTotalFee());
                int phoneCount = this.getProdExtendInfo(orderRequest.getTenantId(),
                        order.getOrderId());
                ordOrderVo.setPhoneCount(phoneCount);
                /* 3.订单发票信息查询*/
                OrdOdInvoice ordOdInvoice = ordOdInvoiceAtomSV.selectByPrimaryKey(order.getParentOrderId());
                if(ordOdInvoice !=null) {
                	ordOrderVo.setInvoiceTitle(ordOdInvoice.getInvoiceTitle());
                	ordOrderVo.setInvoiceType(ordOdInvoice.getInvoiceType());
                	SysParam sysParamInvoice = this.translateInfo(order.getTenantId(),
                            "ORD_OD_INVOICE", "INVOICE_TYPE", ordOdInvoice.getInvoiceType(), iCacheSV);
                	ordOrderVo.setInvoiceTypeName(sysParamInvoice == null ? "" : sysParamInvoice.getColumnDesc());
                	ordOrderVo.setInvoiceContent(ordOrderVo.getInvoiceContent());
                }
                /* 4.订单配送信息查询*/
                OrdOdLogistics ordOdLogistics = this.getOrdOdLogistics(order.getTenantId(), order.getParentOrderId());
                if(ordOdLogistics!=null) {
                	ordOrderVo.setExpressOddNumber(ordOdLogistics.getExpressOddNumber());
                	ordOrderVo.setContactCompany(ordOdLogistics.getContactCompany());
                	ordOrderVo.setContactName(ordOdLogistics.getContactName());
                	ordOrderVo.setContactTel(ordOdLogistics.getContactTel());
                	ordOrderVo.setProvinceCode(ordOdLogistics.getProvinceCode()==null?"":iCacheSV.getAreaName(ordOdLogistics.getProvinceCode()));
                	ordOrderVo.setCityCode(ordOdLogistics.getCityCode()==null?"":iCacheSV.getAreaName(ordOdLogistics.getCityCode()));
                	ordOrderVo.setCountyCode(ordOdLogistics.getCountyCode()==null?"":iCacheSV.getAreaName(ordOdLogistics.getCountyCode()));
                	ordOrderVo.setPostCode(ordOdLogistics.getPostcode());
                	ordOrderVo.setAreaCode(ordOdLogistics.getAreaCode()==null?"":iCacheSV.getAreaName(ordOdLogistics.getAreaCode()));
                	ordOrderVo.setAddress(ordOdLogistics.getAddress());
                	ordOrderVo.setExpressId(ordOdLogistics.getExpressId());
                }
                /* 5.订单费用明细查询 */
                List<OrderPayVo> orderFeeProdList = this.getOrderFeeProdList(iCacheSV,
                        order.getParentOrderId(),order.getTenantId());
                ordOrderVo.setPayDataList(orderFeeProdList);
                /* 6.订单商品明细查询 */
                List<OrdProductVo> productList = this.getOrdProductList(iCacheSV,
                        order.getTenantId(), order.getOrderId());
                ordOrderVo.setProductList(productList);
                /* 7.订单支付机构查询*/
                OrdBalacneIfCriteria exampleBalance=new OrdBalacneIfCriteria();
                Criteria criteriaBalance = exampleBalance.createCriteria();
                criteriaBalance.andTenantIdEqualTo(order.getTenantId());
                criteriaBalance.andOrderIdEqualTo(order.getOrderId());
                List<OrdBalacneIf> ordBalacneIfs = ordBalacneIfAtomSV.selectByExample(exampleBalance);
                if(!CollectionUtil.isEmpty(ordBalacneIfs)) {
                	OrdBalacneIf ordBalacneIf = ordBalacneIfs.get(0);
                	ordOrderVo.setBalacneIfId(ordBalacneIf.getBalacneIfId());
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
        int count=ordOrderAttachAtomSV.behindQueryCount(orderListRequest, states);;
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
        		SysParam sysParam = this.translateInfo(ordOdProd.getTenantId(), "PRODUCT",
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
     * 获取配送信息
     */
    private OrdOdLogistics getOrdOdLogistics(String tenantId,
    		long orderId) {
    	OrdOdLogisticsCriteria example=new OrdOdLogisticsCriteria();
    	OrdOdLogisticsCriteria.Criteria criteria = example.createCriteria();
    	criteria.andTenantIdEqualTo(tenantId);
    	criteria.andOrderIdEqualTo(orderId);
    	List<OrdOdLogistics> list = ordOdLogisticsAtomSV.selectByExample(example);
    	OrdOdLogistics ordOdLogistics=null;
    	if(!CollectionUtil.isEmpty(list)) {
    		 ordOdLogistics = list.get(0);
    	}
    	return ordOdLogistics;
    }
    
    
    /**
     * 运营后台列表信息
     */
    private List<BehindParentOrdOrderVo> getBehindOrdOrderVos(BehindQueryOrderListRequest orderListRequest,
    		String states,ICacheSV iCacheSV) {
    	List<BehindParentOrdOrderVo> orderVoList=new ArrayList<BehindParentOrdOrderVo>();
    	List<BehindOrdOrderAttach> parentList = 
    			ordOrderAttachAtomSV.behindQueryOrderBySearch(orderListRequest, states);
        if(!CollectionUtil.isEmpty(parentList)) {
        	for (BehindOrdOrderAttach behindOrdOrderAttach : parentList) {
        		BehindParentOrdOrderVo pOrderVo=new BehindParentOrdOrderVo();
        		List<BehindOrdOrderVo> orderList=new ArrayList<BehindOrdOrderVo>();
        		pOrderVo.setpOrderId(behindOrdOrderAttach.getOrderId());
        		pOrderVo.setChlId(behindOrdOrderAttach.getChlId());
        		SysParam sysParamChlId = this.translateInfo(behindOrdOrderAttach.getTenantId(), 
        				"ORD_ORDER", "CHL_ID", behindOrdOrderAttach.getChlId(), iCacheSV);
        		pOrderVo.setChlIdName(sysParamChlId==null?"":sysParamChlId.getColumnDesc());
        		pOrderVo.setContactTel(behindOrdOrderAttach.getContactTel());
        		pOrderVo.setUserId(behindOrdOrderAttach.getUserId());
        		pOrderVo.setDeliveryFlag(behindOrdOrderAttach.getDeliveryFlag());
        		SysParam sysParamDf = this.translateInfo(behindOrdOrderAttach.getTenantId(), 
        				"ORD_ORDER", "ORD_DELIVERY_FLAG", behindOrdOrderAttach.getDeliveryFlag(), iCacheSV);
        		pOrderVo.setDeliveryFlagName(sysParamDf==null?"":sysParamDf.getColumnDesc());
        		String[] arr={"21","22","23","31","92","93","94"};  //售后状态
        		boolean flag = Arrays.asList(arr).contains(behindOrdOrderAttach.getState());
        		if(!flag) {
        			pOrderVo.setAdjustFee(behindOrdOrderAttach.getAdjustFee());
        			pOrderVo.setDiscountFee(behindOrdOrderAttach.getDiscountFee());
        		}
          	    //TODO 绑定手机号??
        		/* 判断查询条件是否为待付款 待付款的条件下不存在子订单信息*/
        		if(OrdersConstants.OrdOrder.State.WAIT_PAY.equals(behindOrdOrderAttach.getState())) {
        			BehindOrdOrderVo orderVo=new BehindOrdOrderVo();
        			/* 查询父订单下的商品信息*/
        			List<BehindOrdProductVo> prodList = this.getProdList(orderListRequest, behindOrdOrderAttach,behindOrdOrderAttach.getState(),null);
        			orderVo.setProductList(prodList);
        			orderVo.setProdSize(prodList.size());
        			orderVo.setState(behindOrdOrderAttach.getState());
        			SysParam sysParamState = this.translateInfo(orderListRequest.getTenantId(), "ORD_ORDER",
    						"STATE", behindOrdOrderAttach.getState(), iCacheSV);
    				orderVo.setStateName(sysParamState == null ? "" : sysParamState.getColumnDesc());
        			orderList.add(orderVo);
        			pOrderVo.setOrderList(orderList);
        		}else {
        			OrdOrderCriteria exampleOrder=new OrdOrderCriteria();
        			OrdOrderCriteria.Criteria criteriaOrder = exampleOrder.createCriteria();
        			criteriaOrder.andParentOrderIdEqualTo(behindOrdOrderAttach.getOrderId());
        			criteriaOrder.andTenantIdEqualTo(orderListRequest.getTenantId());
        			if(!flag) {
        				criteriaOrder.andCusServiceFlagEqualTo(OrdersConstants.OrdOrder.cusServiceFlag.NO);
        			}else {
        				criteriaOrder.andCusServiceFlagEqualTo(OrdersConstants.OrdOrder.cusServiceFlag.YES);  //售后订单
        			}
        			List<OrdOrder> orders= ordOrderAtomSV.selectByExample(exampleOrder);
        			for (OrdOrder ordOrder : orders) {
        				BehindOrdOrderVo orderVo=new BehindOrdOrderVo();
        				orderVo.setOrderId(ordOrder.getOrderId());
        				orderVo.setState(ordOrder.getState());
        				SysParam sysParamState = this.translateInfo(ordOrder.getTenantId(), "ORD_ORDER",
        						"STATE", ordOrder.getState(), iCacheSV);
        				orderVo.setStateName(sysParamState == null ? "" : sysParamState.getColumnDesc());
        				List<BehindOrdProductVo> prodList = this.getProdList(orderListRequest, behindOrdOrderAttach,null,ordOrder.getOrderId());
        				orderVo.setProdSize(prodList.size());
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
        			if (!CollectionUtil.isEmpty(orderFeeProdList)) {
        				for (OrdOdFeeProd ordOdFeeProd : orderFeeProdList) { 
        					if(OrdersConstants.OrdOdFeeProd.PayStyle.JF.equals(ordOdFeeProd.getPayStyle())) {
        						points+=ordOdFeeProd.getPaidFee();
        					}
        				}
        			}
        			pOrderVo.setPoints(points);
        		}
    		orderVoList.add(pOrderVo);
         }
       }
       return orderVoList;
    }
    
    
    /**
     * 订单下的商品明细信息
     */
    private List<BehindOrdProductVo> getProdList(BehindQueryOrderListRequest orderListRequest,
    		BehindOrdOrderAttach behindOrdOrderAttach,String states,Long orderId) {
		List<BehindOrdProductVo> productList=new ArrayList<BehindOrdProductVo>();
		OrdOdProdCriteria example=new OrdOdProdCriteria();
		OrdOdProdCriteria.Criteria criteria = example.createCriteria();
		criteria.andTenantIdEqualTo(orderListRequest.getTenantId());
		if(OrdersConstants.OrdOrder.State.WAIT_PAY.equals(states)) {
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
    
    /**
     * 信息翻译
     */
    private SysParam translateInfo(String tenantId, String typeCode, 
    		String paramCode, String columnValue,ICacheSV iCacheSV) {
    	SysParamSingleCond sysParamSingleCond = new SysParamSingleCond(
    			tenantId, typeCode,paramCode, columnValue);
    	SysParam sysParamInfo = iCacheSV.getSysParamSingle(sysParamSingleCond);
    	return sysParamInfo;
    }
}
