package com.ai.slp.order.service.business.impl;

import java.util.ArrayList;
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
import com.ai.slp.common.api.cache.interfaces.ICacheSV;
import com.ai.slp.common.api.cache.param.SysParam;
import com.ai.slp.common.api.cache.param.SysParamSingleCond;
import com.ai.slp.order.api.orderlist.param.OrdOrderApiVo;
import com.ai.slp.order.api.orderlist.param.OrdOrderVo;
import com.ai.slp.order.api.orderlist.param.OrdProductApiVo;
import com.ai.slp.order.api.orderlist.param.OrdProductVo;
import com.ai.slp.order.api.orderlist.param.OrderPayVo;
import com.ai.slp.order.api.orderlist.param.ProductImage;
import com.ai.slp.order.api.orderlist.param.QueryApiOrderRequest;
import com.ai.slp.order.api.orderlist.param.QueryApiOrderResponse;
import com.ai.slp.order.api.orderlist.param.QueryExceptionOrderRequest;
import com.ai.slp.order.api.orderlist.param.QueryOrderListRequest;
import com.ai.slp.order.api.orderlist.param.QueryOrderListResponse;
import com.ai.slp.order.api.orderlist.param.QueryOrderRequest;
import com.ai.slp.order.api.orderlist.param.QueryOrderResponse;
import com.ai.slp.order.constants.ErrorCodeConstants;
import com.ai.slp.order.constants.OrdersConstants;
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
import com.ai.slp.order.vo.InfoJsonVo;
import com.ai.slp.order.vo.ProdAttrInfoVo;
import com.ai.slp.order.vo.ProdExtendInfoVo;
import com.ai.slp.product.api.product.interfaces.IProductServerSV;
import com.ai.slp.product.api.product.param.ProductSkuInfo;
import com.ai.slp.product.api.product.param.SkuInfoQuery;
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
            SysParamSingleCond sysParamSingleCond = new SysParamSingleCond("SLP", "ORD_ORDER",
                    "STATE", orderAttach.getState());
            SysParam sysParamState = iCacheSV.getSysParamSingle(sysParamSingleCond);
            ordOrderVo.setStateName(sysParamState == null ? "" : sysParamState.getColumnDesc());
            ordOrderVo.setOrderTime(orderAttach.getOrderTime());
            ordOrderVo.setAdjustFee(orderAttach.getAdjustFee());
            ordOrderVo.setDiscountFee(orderAttach.getDiscountFee());
            ordOrderVo.setPaidFee(orderAttach.getPaidFee());
            ordOrderVo.setPayFee(orderAttach.getPayFee());
            ordOrderVo.setPayStyle(orderAttach.getPayStyle());
            SysParamSingleCond sysParamPayStyle = new SysParamSingleCond("SLP", "ORD_OD_FEE_TOTAL",
                    "PAY_STYLE", orderAttach.getPayStyle());
            SysParam sysParam = iCacheSV.getSysParamSingle(sysParamPayStyle);
            ordOrderVo.setPayStyleName(sysParam == null ? "" : sysParam.getColumnDesc());
            ordOrderVo.setPayTime(orderAttach.getPayTime());
            ordOrderVo.setTotalFee(orderAttach.getTotalFee());
            int phoneCount = this.getProdExtendInfo(orderListRequest.getTenantId(),
                    orderAttach.getOrderId());
            ordOrderVo.setPhoneCount(phoneCount);
            /* 4.订单费用明细查询 */
            List<OrderPayVo> orderFeeProdList = this.getOrderFeeProdList(iCacheSV,
                    orderAttach.getOrderId());
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
    private List<OrderPayVo> getOrderFeeProdList(ICacheSV iCacheSV, long orderId) {
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
                SysParamSingleCond sysParamPayStyle = new SysParamSingleCond("SLP",
                        "ORD_OD_FEE_TOTAL", "PAY_STYLE", ordOdFeeProd.getPayStyle());
                SysParam sysParam = iCacheSV.getSysParamSingle(sysParamPayStyle);
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
                ordProductVo.setSkuId(ordOdProd.getSkuId());
                //TODO ?????  库存名称
                ordProductVo.setState(ordOdProd.getState());
                ordProductVo.setProdName(ordOdProd.getProdName());
                ordProductVo.setSalePrice(ordOdProd.getSalePrice());
                ordProductVo.setBuySum(ordOdProd.getBuySum());
                ordProductVo.setTotalFee(ordOdProd.getTotalFee());
                ordProductVo.setDiscountFee(ordOdProd.getDiscountFee());
                ordProductVo.setAdjustFee(ordOdProd.getAdjustFee());
                ordProductVo.setOperDiscountFee(ordOdProd.getOperDiscountFee());
                String extendInfo = ordOdProd.getExtendInfo();
                ProdAttrInfoVo prodAttrInfoVo = JSON.parseObject(extendInfo, ProdAttrInfoVo.class);
                ordProductVo.setBasicOrgId(prodAttrInfoVo.getBasicOrgId());
                SysParamSingleCond sysParamBasicOrg = new SysParamSingleCond("SLP", "PRODUCT",
                        "BASIC_ORG_ID", prodAttrInfoVo.getBasicOrgId());
                SysParam sysParam = iCacheSV.getSysParamSingle(sysParamBasicOrg);
                ordProductVo.setBasicOrgName(sysParam == null ? "" : sysParam.getColumnDesc());
                ordProductVo.setProvinceCode(prodAttrInfoVo.getProvinceCode());
                String provinceName = "";
                if (!StringUtil.isBlank(prodAttrInfoVo.getProvinceCode())) {
                    provinceName = iCacheSV.getAreaName(prodAttrInfoVo.getProvinceCode());
                }
                ordProductVo.setProvinceName(provinceName);
                ordProductVo.setChargeFee(prodAttrInfoVo.getChargeFee());
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
            ordOrderVo.setBusiCode(order.getBusiCode());
            ordOrderVo.setState(order.getState());
            ordOrderVo.setChlId(order.getChlId()); //订单来源
            ordOrderVo.setRouteId(order.getRouteId());//仓库ID
            //TODO ??? 仓库信息
            ordOrderVo.setParentOrderId(order.getParentOrderId());//父订单id
            ordOrderVo.setUserId(order.getUserId());//买家帐号(用户号)
            ordOrderVo.setRemark(order.getRemark());//买家留言(订单备注)
            //支付流水号
            
            SysParamSingleCond sysParamSingleCond = new SysParamSingleCond("SLP", "ORD_ORDER",
            		"STATE", order.getState());
            SysParam sysParamState = iCacheSV.getSysParamSingle(sysParamSingleCond);
            ordOrderVo.setStateName(sysParamState == null ? "" : sysParamState.getColumnDesc());
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
                SysParamSingleCond sysParamPayStyle = new SysParamSingleCond("SLP",
                        "ORD_OD_FEE_TOTAL", "PAY_STYLE", ordOdFeeTotal.getPayStyle());
                SysParam sysParam = iCacheSV.getSysParamSingle(sysParamPayStyle);
                ordOrderVo.setPayStyleName(sysParam == null ? "" : sysParam.getColumnDesc());
                ordOrderVo.setPayTime(ordOdFeeTotal.getUpdateTime());
                ordOrderVo.setTotalFee(ordOdFeeTotal.getTotalFee());
                int phoneCount = this.getProdExtendInfo(orderRequest.getTenantId(),
                        order.getOrderId());
                ordOrderVo.setPhoneCount(phoneCount);
                //TODO
                /* 3.订单发票信息查询*/
                OrdOdInvoice ordOdInvoice = ordOdInvoiceAtomSV.selectByPrimaryKey(order.getOrderId());
                if(ordOdInvoice !=null) {
                	ordOrderVo.setInvoiceTitle(ordOdInvoice.getInvoiceTitle());
                	ordOrderVo.setInvoiceType(ordOdInvoice.getInvoiceType());
                	ordOrderVo.setInvoiceContent(ordOrderVo.getInvoiceContent());
                }
                /* 订单配送信息查询*/
                OrdOdLogistics ordOdLogistics = this.getOrdOdLogistics(order.getTenantId(), order.getOrderId());
                if(ordOdLogistics!=null) {
                	ordOrderVo.setExpressOddNumber(ordOdLogistics.getExpressOddNumber());
                	ordOrderVo.setContactCompany(ordOdLogistics.getContactCompany());
                	ordOrderVo.setContactName(ordOdLogistics.getContactName());
                	ordOrderVo.setContactTel(ordOdLogistics.getContactTel());
                	ordOrderVo.setProvinceCode(ordOdLogistics.getProvinceCode());
                	ordOrderVo.setCityCode(ordOdLogistics.getCityCode());
                	ordOrderVo.setCountyCode(ordOdLogistics.getCountyCode());
                	ordOrderVo.setPostCode(ordOdLogistics.getPostcode());
                	ordOrderVo.setAreaCode(ordOdLogistics.getAreaCode());
                	ordOrderVo.setAddress(ordOdLogistics.getAddress());
                	ordOrderVo.setExpressId(ordOdLogistics.getExpressId());
                }
                /* 3.订单费用明细查询 */
                List<OrderPayVo> orderFeeProdList = this.getOrderFeeProdList(iCacheSV,
                        order.getOrderId());
                ordOrderVo.setPayDataList(orderFeeProdList);
                /* 4.订单商品明细查询 */
                List<OrdProductVo> productList = this.getOrdProductList(iCacheSV,
                        order.getTenantId(), order.getOrderId());
                ordOrderVo.setProductList(productList);
                /* 5.订单支付机构查询*/
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
    
    //TODO ????????
    /**
     * 异常订单的查询
     */
    @Override
	public QueryOrderListResponse queryExceptionOrderList(QueryExceptionOrderRequest orderListRequest)
			throws BusinessException, SystemException {
		return null;
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
                SysParamSingleCond sysParamBasicOrg = new SysParamSingleCond("SLP", "PRODUCT",
                          "BASIC_ORG_ID", prodAttrInfoVo.getBasicOrgId());
                ICacheSV iCacheSV = DubboConsumerFactory.getService(ICacheSV.class);
                SysParam sysParam = iCacheSV.getSysParamSingle(sysParamBasicOrg);
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
}
