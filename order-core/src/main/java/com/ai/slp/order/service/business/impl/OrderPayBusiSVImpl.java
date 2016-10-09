package com.ai.slp.order.service.business.impl;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.sdk.components.ccs.CCSClientFactory;
import com.ai.opt.sdk.components.dss.DSSClientFactory;
import com.ai.opt.sdk.components.mds.MDSClientFactory;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.dubbo.util.HttpClientUtil;
import com.ai.opt.sdk.util.BeanUtils;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.util.DateUtil;
import com.ai.paas.ipaas.ccs.constants.ConfigException;
import com.ai.paas.ipaas.dss.base.interfaces.IDSSClient;
import com.ai.paas.ipaas.mds.IMessageSender;
import com.ai.paas.ipaas.util.StringUtil;
import com.ai.platform.common.api.cache.interfaces.ICacheSV;
import com.ai.platform.common.api.cache.param.SysParam;
import com.ai.slp.order.api.orderlist.interfaces.IOrderListSV;
import com.ai.slp.order.api.orderlist.param.OrdOrderVo;
import com.ai.slp.order.api.orderlist.param.OrdProductVo;
import com.ai.slp.order.api.orderlist.param.QueryOrderRequest;
import com.ai.slp.order.api.orderlist.param.QueryOrderResponse;
import com.ai.slp.order.api.orderpay.param.OrderPayRequest;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.constants.OrdersConstants.OrdOdStateChg;
import com.ai.slp.order.dao.mapper.bo.*;
import com.ai.slp.order.service.atom.interfaces.*;
import com.ai.slp.order.service.business.interfaces.IOrderFrameCoreSV;
import com.ai.slp.order.service.business.interfaces.IOrderPayBusiSV;
import com.ai.slp.order.util.InfoTranslateUtil;
import com.ai.slp.order.util.SequenceUtil;
import com.ai.slp.order.vo.InfoJsonVo;
import com.ai.slp.order.vo.OFCOrderCreateRequest;
import com.ai.slp.order.vo.OrderCouponVo;
import com.ai.slp.order.vo.OrderItemsVo;
import com.ai.slp.order.vo.ProdAttrInfoVo;
import com.ai.slp.order.vo.ProdExtendInfoVo;
import com.ai.slp.order.vo.RouteServReqVo;
import com.ai.slp.product.api.product.interfaces.IProductServerSV;
import com.ai.slp.product.api.product.param.ProductInfoQuery;
import com.ai.slp.product.api.product.param.ProductRoute;
import com.ai.slp.product.api.storageserver.interfaces.IStorageNumSV;
import com.ai.slp.product.api.storageserver.param.StorageNumUserReq;
import com.ai.slp.route.api.core.interfaces.IRouteCoreService;
import com.ai.slp.route.api.core.params.SaleProductInfo;
import com.ai.slp.route.api.routemanage.interfaces.IRouteManageSV;
import com.ai.slp.route.api.routemanage.param.RouteQueryByGroupIdAndAreaRequest;
import com.ai.slp.route.api.routemanage.param.RouteQueryByGroupIdAndAreaResponse;
import com.ai.slp.route.api.server.params.IRouteServerRequest;
import com.ai.slp.route.api.supplyproduct.interfaces.ISupplyProductServiceSV;
import com.ai.slp.route.api.supplyproduct.param.SupplyProduct;
import com.ai.slp.route.api.supplyproduct.param.SupplyProductQueryVo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
    
    @Autowired
    private  IOrderListSV orderListSV;
    
    @Autowired
    private IOrdOdLogisticsAtomSV ordOdLogisticsAtomSV;
    
    @Autowired
    private IOrdOdInvoiceAtomSV ordOdInvoiceAtomSV;
    
    /**
     * 订单收费
     * 
     * @throws Exception
     */
    @Override
    public void orderPay(OrderPayRequest request) throws BusinessException, SystemException {
        /* 1.处理费用信息 */
        Timestamp sysdate = DateUtil.getSysDate();
        //IDSSClient client = DSSClientFactory.getDSSClient(OrdersConstants.ORDER_PHONENUM_DSS);
        IDSSClient client = null;
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
            /*if(Flag=="0") {
            	
            /* 同步长虹OFC,获取参数*/
	            String params = this.getOFCRequestParams(request, orderId,sysdate);
	            Map<String, String> header=new HashMap<String, String>(); 
	            header.put("appkey", OrdersConstants.OFC_APPKEY);
	            //发送Post请求,并返回信息
	            try {
	            	String strData = HttpClientUtil.sendPost(OrdersConstants.OFC_ORDER_CREATE_URL, params, header);
	            	JSONObject object = JSON.parseObject(strData);
	            	boolean val = object.getBooleanValue("IsValid");
	            	/*if(!val) {
						throw new BusinessException("", "销售订单创建同步到OFC错误");
					}*/
	            } catch (IOException | URISyntaxException e) {
	            	logger.error(e.getMessage());
	            	throw new SystemException("", "OFC同步出现异常");
	            }
            //}else {
	            /* 4.拆分子订单 */
	            this.resoleOrders(ordOrder, request.getTenantId(), client);
        	}
        }

   // }

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
            throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "待收费订单号为空");
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
        if (StringUtil.isBlank(request.getPayType())) {
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
        /* 1.获取该订单的商品明细信息,如果不存在商品明细，则报错 */
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
     * @param client
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @ApiDocMethod
     */
    private void resoleOrders(OrdOrder ordOrder, String tenantId, IDSSClient client) {
        logger.debug("开始对订单[" + ordOrder.getOrderId() + "]进行拆分..");
        /* 购买实物类商品 */
        if(OrdersConstants.OrdOrder.OrderType.BUG_MATERIAL_PROD.equals(ordOrder.getOrderType())) {
        	/* 查询费用总表信息*/
        	OrdOdFeeTotal odFeeTotal = ordOdFeeTotalAtomSV.selectByOrderId(tenantId, ordOrder.getOrderId());
        	if(odFeeTotal==null) {
        		throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, 
        				"费用总表信息不存在[orderId:"+ordOrder.getOrderId()+"]");
        	}
        	//减免费用和总费用
        	long operDiscountFee = odFeeTotal.getOperDiscountFee();
        	long totalFee = odFeeTotal.getTotalFee();
        	/* 1.查询商品明细表*/
        	OrdOdProdCriteria example = new OrdOdProdCriteria();
        	OrdOdProdCriteria.Criteria criteria = example.createCriteria();
        	criteria.andTenantIdEqualTo(tenantId);
        	if (ordOrder.getOrderId() == 0) {
        		throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL,"订单id不能为空");        
        	}
        	criteria.andOrderIdEqualTo(ordOrder.getOrderId());
        	List<OrdOdProd> ordOdProdList = ordOdProdAtomSV.selectByExample(example);
        	if(CollectionUtil.isEmpty(ordOdProdList)) {
        		throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "商品明细信息表不存在[orderId:"+ordOrder.getOrderId()+"]");
        	}
        	Map<String, Long> map=new HashMap<String,Long>();
        	for (OrdOdProd ordOdProd : ordOdProdList) {
        		long prodOperDiscountFee=(ordOdProd.getTotalFee()/totalFee)*operDiscountFee;//商品的减免费用
        		ordOdProd.setOperDiscountFee(prodOperDiscountFee+ordOdProd.getOperDiscountFee()); //减免费用
        		ordOdProd.setDiscountFee(prodOperDiscountFee+ordOdProd.getDiscountFee()); //优惠费用
        		ordOdProd.setAdjustFee(ordOdProd.getTotalFee()-ordOdProd.getDiscountFee()); //应收费用
        		ordOdProdAtomSV.updateById(ordOdProd);
        		/* 2.生成子订单*/
        		this.createEntitySubOrder(tenantId, ordOrder,ordOdProd,map);
        	}
        }else {
        	/* 购买虚拟类商品*/
        	/* 1.查询商品明细拓展表 */
            OrdOdProdExtendCriteria example = new OrdOdProdExtendCriteria();
            OrdOdProdExtendCriteria.Criteria criteria = example.createCriteria();
            criteria.andTenantIdEqualTo(tenantId);
            if (ordOrder.getOrderId() != 0) {
                criteria.andOrderIdEqualTo(ordOrder.getOrderId());
            }
            List<OrdOdProdExtend> ordOdProdExtendList = ordOdProdExtendAtomSV.selectByExample(example);
            if (CollectionUtil.isEmpty(ordOdProdExtendList)) {
                throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "商品明细信息扩展表不存在[orderId:"+ordOrder.getOrderId()+"]");
            }
            /* 2.遍历取出值信息 */
            for (OrdOdProdExtend ordOdProdExtend : ordOdProdExtendList) {
                String infoJson = ordOdProdExtend.getInfoJson();
                String batchFlag = ordOdProdExtend.getBatchFlag();
                if (OrdersConstants.OrdOdProdExtend.BatchFlag.YES.equals(batchFlag)) {
                    byte[] filebytes = client.read(infoJson);
                    infoJson = new String(filebytes);
                }
                InfoJsonVo infoJsonVo = JSON.parseObject(infoJson, InfoJsonVo.class);
                List<ProdExtendInfoVo> prodExtendInfoVoList = infoJsonVo.getProdExtendInfoVoList();
                for (ProdExtendInfoVo prodExtendInfoVo : prodExtendInfoVoList) {
                    /* 3.生成子订单 */
                    this.createSubOrder(tenantId, ordOrder, ordOdProdExtend.getProdDetalId(),
                            prodExtendInfoVo.getProdExtendInfoValue());
                }
            }
        }
    }
    
    
    /**
     * 实物体生成子订单
     * @param tenantId
     * @param parentOrdOrder
     * @param parentOrdOdProd
     * @param map
     * @author caofz
     */
    private void createEntitySubOrder(String tenantId, OrdOrder parentOrdOrder,
    		OrdOdProd parentOrdOdProd,Map<String, Long> map) {
    	/* 1.根据商品信息获取routeId*/
    	String routeGroupId = this.getRouteGroupId(tenantId, parentOrdOdProd.getProdId(),Long.parseLong(parentOrdOdProd.getSupplierId()));
    	IRouteManageSV iRouteManageSV = DubboConsumerFactory.getService(IRouteManageSV.class);
    	OrdOdLogistics ordOdLogistics = ordOdLogisticsAtomSV.selectByOrd(tenantId, parentOrdOrder.getOrderId());
        RouteQueryByGroupIdAndAreaRequest andAreaRequest=new RouteQueryByGroupIdAndAreaRequest();
        andAreaRequest.setProvinceCode(ordOdLogistics.getProvinceCode());
        andAreaRequest.setRouteGroupId(routeGroupId);
        andAreaRequest.setTenantId(tenantId);
        RouteQueryByGroupIdAndAreaResponse routeResponse = iRouteManageSV.queryRouteInfoByGroupIdAndArea(andAreaRequest);
        boolean isSuccess = routeResponse.getResponseHeader().getIsSuccess();
        if(!isSuccess||routeResponse==null){
        	throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "根据路由组ID["
                    + routeGroupId + "]省份编码[" + ordOdLogistics.getProvinceCode()+ "]未能找到供货路由");
    	}
    	String routeId = routeResponse.getRouteId();
    	Long subOrderId=null;
    	OrdOdProd ordOdProd =null;
    	/* 2.判断routeId是否在map集合中*/
    	if(!map.containsKey(routeId)) {
    		/* 2.1.不包含的话,创建子订单表*/
    		subOrderId = SequenceUtil.createOrderId();
    		OrdOrder childOrdOrder = new OrdOrder();
    		BeanUtils.copyProperties(childOrdOrder, parentOrdOrder);
    		childOrdOrder.setOrderId(subOrderId);
    		childOrdOrder.setSubFlag(OrdersConstants.OrdOrder.SubFlag.YES);
    		childOrdOrder.setParentOrderId(parentOrdOrder.getOrderId());
    		childOrdOrder.setState(OrdersConstants.OrdOrder.State.WAIT_DISTRIBUTION);
    		childOrdOrder.setStateChgTime(DateUtil.getSysDate());
    		childOrdOrder.setRouteId(routeId); 
    		ordOrderAtomSV.insertSelective(childOrdOrder);
    		/* 2.1.1.实物的情况下,创建其它子信息表*/
    		ordOdProd = this.createTableInfo(subOrderId, parentOrdOrder, parentOrdOdProd, routeId,null);
    		this.createFeeTotal(subOrderId, parentOrdOrder, ordOdProd);
    		/* 2.1.2.把routeId和子订单Id放入集合中*/
    		map.put(routeId, subOrderId);
    	}else {
    		/* 2.2.同一个routeId的情况下,订单合并*/
    		subOrderId = map.get(routeId);
    		ordOdProd = this.createTableInfo(subOrderId, parentOrdOrder, parentOrdOdProd, routeId,null);
    		this.updateFeeTotal(subOrderId, parentOrdOrder, ordOdProd);
    	}
        /* 3.写入订单状态变化轨迹表 */
        String chgDesc=OrdersConstants.OrdOdStateChg.ChgDesc.ORDER_TO_WAIT_DISTRIBUTION;
		orderFrameCoreSV.ordOdStateChg(subOrderId, tenantId, parentOrdOrder.getState(),
				OrdersConstants.OrdOrder.State.WAIT_DISTRIBUTION, chgDesc, null, null, null, DateUtil.getSysDate());
        /* 4.调用路由,并更新订单明细表*/ 
		this.callRoute(tenantId,ordOdProd,null,routeId,parentOrdOrder.getOrderType());
    }
    
    
    /**
     * 虚拟类生成子订单
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
        childOrdOrder.setState(OrdersConstants.OrdOrder.State.WAIT_CHARGE);
        childOrdOrder.setStateChgTime(DateUtil.getSysDate());
        ordOrderAtomSV.insertSelective(childOrdOrder);
        /* 2.虚拟物品的情况下,创建其它子信息表*/
        OrdOdProd ordOdProd = this.createTableInfo(subOrderId, parentOrdOrder, null, null,parentProdDetalId);
        /* 3.创建子订单商品明细信息扩展表*/
        Long prodDetailExtendId = SequenceUtil.createProdDetailExtendId();
        OrdOdProdExtend subOrdOdProdExtend=new OrdOdProdExtend();
        subOrdOdProdExtend.setInfoJson(prodExtendInfoValue);
        subOrdOdProdExtend.setOrderId(subOrderId);
        subOrdOdProdExtend.setProdDetalId(ordOdProd.getProdDetalId());
        subOrdOdProdExtend.setProdDetalExtendId(prodDetailExtendId);
        subOrdOdProdExtend.setTenantId(parentOrdOrder.getOrderType());
        subOrdOdProdExtend.setBatchFlag(OrdersConstants.OrdOdProdExtend.BatchFlag.NO);
        ordOdProdExtendAtomSV.insertSelective(subOrdOdProdExtend);
        /* 4.写入订单状态变化轨迹表 */
        String chgDesc=OrdersConstants.OrdOdStateChg.ChgDesc.ORDER_TO_CHARGE;
		orderFrameCoreSV.ordOdStateChg(childOrdOrder.getOrderId(), childOrdOrder.getTenantId(), parentOrdOrder.getState(),
				OrdersConstants.OrdOrder.State.WAIT_CHARGE, chgDesc, null, null, null, DateUtil.getSysDate());
        /* 5.调用路由,并更新订单明细表 */
		this.callRoute(tenantId, ordOdProd, prodDetailExtendId, null, parentOrdOrder.getOrderType());
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
     * @param prodId
     * @param salePrice
     * @author zhangxw
     * @param prodDetailId
     * @param string
     * @ApiDocMethod
     */
    private void callRoute(String tenantId, OrdOdProd ordOdProd,Long prodDetailExtendId,
    		String routeId,String orderType) {
    	String rid=null;
    	/* 1.非实物类的情况下*/
    	if(!OrdersConstants.OrdOrder.OrderType.BUG_MATERIAL_PROD.equals(orderType)) {
    		/* 1.1.获取路由组ID */
    		String routeGroupId = this.getRouteGroupId(tenantId, ordOdProd.getProdId(),0);
    		/* 1.2.路由计算获取路由ID */
    		IRouteCoreService iRouteCoreService = DubboConsumerFactory.getService(IRouteCoreService.class);
    		SaleProductInfo saleProductInfo = new SaleProductInfo();
    		saleProductInfo.setTenantId(tenantId);
    		saleProductInfo.setRouteGroupId(routeGroupId);
    		saleProductInfo.setTotalConsumption(ordOdProd.getSalePrice());
    		rid = iRouteCoreService.findRoute(saleProductInfo);
    		if (StringUtil.isBlank(rid)) {
    			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "根据路由组ID["
    					+ routeGroupId + "]订单金额[" + ordOdProd.getSalePrice() + "]未能找到供货路由");
    		}
    	}else {
    		rid=routeId;
    	}
        /* 2.根据路由ID查询相关信息 */
        SupplyProductQueryVo supplyProductQueryVo = new SupplyProductQueryVo();
        supplyProductQueryVo.setTenantId(tenantId);
        supplyProductQueryVo.setRouteId(rid);
        supplyProductQueryVo.setSaleCount(1);
        supplyProductQueryVo.setStandardProductId(ordOdProd.getStandardProdId());
        ISupplyProductServiceSV iSupplyProductServiceSV = DubboConsumerFactory
                .getService(ISupplyProductServiceSV.class);
        SupplyProduct supplyProduct = iSupplyProductServiceSV.updateSupplyProductSaleCount(supplyProductQueryVo);
        /* 3.更新订单商品明细表字段 */
        ordOdProd.setRouteId(routeId);
        ordOdProd.setSupplyId(supplyProduct.getSupplyId());
        ordOdProd.setSellerId(supplyProduct.getSellerId());
        ordOdProd.setCostPrice(supplyProduct.getCostPrice());
        ordOdProdAtomSV.updateById(ordOdProd);
        //TODO
        if(OrdersConstants.OrdOrder.OrderType.BUG_MATERIAL_PROD.equals(orderType)) {
        	return;
        }
        OrdOdProdExtend ordOdProdExtend = ordOdProdExtendAtomSV.selectByPrimaryKey(prodDetailExtendId);
        if(ordOdProdExtend==null) {
        	throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "商品明细信息[prodDetailExtendId:"+prodDetailExtendId+"]");
        }
        /* 4.充值路由 */
        IRouteServerRequest request = new IRouteServerRequest();
        request.setTenantId(tenantId);
        request.setRouteId(rid);
        RouteServReqVo routeServReqVo = new RouteServReqVo();
        routeServReqVo.setOrderId(String.valueOf(ordOdProd.getOrderId()));
        routeServReqVo.setBizType(ordOdProd.getProdType());
        routeServReqVo.setAccountVal(ordOdProdExtend.getInfoJson());
        routeServReqVo.setBuyNum(1);
        routeServReqVo.setNotifyUrl(this.getNotifyUrl(OrdersConstants.O2P_NOTIFYURL));
        routeServReqVo.setProId(ordOdProd.getProdId());
        routeServReqVo.setUnitPrice(ordOdProd.getSalePrice()*30);
        routeServReqVo.setCoSysId(ordOdProd.getSellerId());
        String extendInfo = ordOdProd.getExtendInfo();
      	ProdAttrInfoVo prodAttrInfoVo = JSON.parseObject(extendInfo,ProdAttrInfoVo.class);
      	routeServReqVo.setOperatorId(prodAttrInfoVo.getBasicOrgId()); 
        request.setRequestData(JSON.toJSONString(routeServReqVo));
        chargeMds(request);
    }


    /**
     * 根据商品ID查询路由组ID
     * @param tenantId
     * @param productId
     * @return
     * @author zhangxw
     * @ApiDocMethod
     */
    private String getRouteGroupId(String tenantId, String productId,long supplierId) {
        ProductInfoQuery productInfoQuery = new ProductInfoQuery();
        productInfoQuery.setTenantId(tenantId);
        productInfoQuery.setProductId(productId);
        productInfoQuery.setSupplierId(String.valueOf(supplierId));
        IProductServerSV iProductServerSV = DubboConsumerFactory.getService(IProductServerSV.class);
        ProductRoute productRoute = iProductServerSV.queryRouteGroupOfProd(productInfoQuery);
        return productRoute.getRouteGroupId();
    }

    /**
     * 获取o2p回调通知url
     * 
     * @param urlParams
     * @return
     * @author zhangxw
     * @ApiDocMethod
     */
    public String getNotifyUrl(String urlParams) {
        String ccsParams = urlParams;
        if (!urlParams.startsWith("/"))
            ccsParams = "/" + ccsParams;
        String notifyUrl = "";
        try {
            notifyUrl = CCSClientFactory.getDefaultConfigClient().get(ccsParams);
        } catch (ConfigException e) {
            logger.error("获取配置信息失败", e);
            e.printStackTrace();
        }
        return notifyUrl;
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
                .getSenderClient(OrdersConstants.SLP_CHARGE_TOPIC);
        String context = JSON.toJSONString(request);
        logger.info("\r\n{}",context);
        msgSender.send(context, new Random(10000).nextLong());// 第二个参数为分区键，如果不分区，传入0
        logger.info("send sucess...");
    }
    
    /**
     * 拆分时创建表信息
     */
    private OrdOdProd createTableInfo(long subOrderId,OrdOrder parentOrdOrder,OrdOdProd parentOrdOdProd,
    		String routeId,Long parentProdDetalId) {
    	OrdOdProd ordOdProd=null;
    	long prodDetailId = SequenceUtil.createProdDetailId();
    	if(OrdersConstants.OrdOrder.OrderType.BUG_MATERIAL_PROD.equals(
    			parentOrdOrder.getOrderType())) {
    		/* 创建子订单-商品明细信息 */
    		ordOdProd = new OrdOdProd();
    		BeanUtils.copyProperties(ordOdProd, parentOrdOdProd);
    		ordOdProd.setProdDetalId(prodDetailId);
    		ordOdProd.setOrderId(subOrderId);
    		ordOdProd.setRouteId(routeId);
    		ordOdProdAtomSV.insertSelective(ordOdProd);
    		/* 判断是否有发票信息*/
    		OrdOdInvoice odInvoice = ordOdInvoiceAtomSV.selectByPrimaryKey(parentOrdOrder.getOrderId());
    		if(odInvoice!=null) {
    			OrdOdInvoice invoice=new OrdOdInvoice();
    			BeanUtils.copyProperties(invoice, odInvoice);
    			invoice.setOrderId(subOrderId);
    		}
    	}else {
	        OrdOdProdCriteria example = new OrdOdProdCriteria();
	        OrdOdProdCriteria.Criteria criteria = example.createCriteria();
	        criteria.andTenantIdEqualTo(parentOrdOrder.getTenantId());
	        if (parentOrdOrder.getOrderId() != 0) {
	            criteria.andOrderIdEqualTo(parentOrdOrder.getOrderId());
	        }
	        if (parentProdDetalId != 0) {
	            criteria.andProdDetalIdEqualTo(parentProdDetalId);
	        }
	        List<OrdOdProd> ordOdProdList = ordOdProdAtomSV.selectByExample(example);
	        if (CollectionUtil.isEmpty(ordOdProdList)) {
	            throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, 
	            		"商品明细信息不存在[orderId:"+parentOrdOrder.getOrderId()+"prodDetalId:"+parentProdDetalId+"]");
	        }
	        OrdOdProd parentOd = ordOdProdList.get(0);
	        ordOdProd = new OrdOdProd();
	        BeanUtils.copyProperties(ordOdProd, parentOd);
	        ordOdProd.setProdDetalId(prodDetailId);
	        ordOdProd.setOrderId(subOrderId);
	        ordOdProdAtomSV.insertSelective(ordOdProd);
	    }
        return ordOdProd;
    }
    
    
    /**
     * 创建费用总表信息
     */
    private void createFeeTotal(long subOrderId,OrdOrder parentOrdOrder,OrdOdProd ordOdProd) {
    	/* 创建子订单-费用汇总表*/
    	OrdOdFeeTotal parentOrdOdFeeTotal = ordOdFeeTotalAtomSV.selectByOrderId(parentOrdOrder.getTenantId(), 
    			parentOrdOrder.getOrderId());
    	if(parentOrdOdFeeTotal==null) {
    		throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, 
    				"订单费用总表[orderId:"+parentOrdOrder.getOrderId()+"]");
    	}
    	OrdOdFeeTotal ordOdFeeTotal=new OrdOdFeeTotal();
    	BeanUtils.copyProperties(ordOdFeeTotal, parentOrdOdFeeTotal);
    	ordOdFeeTotal.setOrderId(subOrderId);
    	ordOdFeeTotal.setTotalFee(ordOdProd.getTotalFee());
    	ordOdFeeTotal.setDiscountFee(ordOdProd.getDiscountFee());
    	long apaidFee=ordOdProd.getTotalFee()-ordOdProd.getDiscountFee()+ordOdFeeTotal.getFreight();
    	ordOdFeeTotal.setAdjustFee(apaidFee);
    	ordOdFeeTotal.setPaidFee(apaidFee);
    	ordOdFeeTotal.setTotalJf(ordOdProd.getJf());
    	ordOdFeeTotalAtomSV.insertSelective(ordOdFeeTotal);
    }
    
    /**
     * 更新费用总表信息(同一个routeId的情况下,订单费用总表合并)
     */
    private void updateFeeTotal(long subOrderId,OrdOrder parentOrdOrder,OrdOdProd ordOdProd) {
    	/* 创建子订单-费用汇总表*/
    	OrdOdFeeTotal pOrdOdFeeTotal = ordOdFeeTotalAtomSV.selectByOrderId(parentOrdOrder.getTenantId(), 
    			subOrderId);
    	if(pOrdOdFeeTotal==null) {
    		throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, 
    				"订单费用总表[orderId:"+parentOrdOrder.getOrderId()+"]");
    	}
    	OrdOdFeeTotal ordOdFeeTotal=new OrdOdFeeTotal();
    	BeanUtils.copyProperties(ordOdFeeTotal, pOrdOdFeeTotal);
    	ordOdFeeTotal.setTotalFee(pOrdOdFeeTotal.getTotalFee()+ordOdProd.getTotalFee());
    	ordOdFeeTotal.setDiscountFee(pOrdOdFeeTotal.getDiscountFee()+ordOdProd.getDiscountFee());
    	long apaidFee=ordOdProd.getTotalFee()-ordOdProd.getDiscountFee();
    	ordOdFeeTotal.setAdjustFee(pOrdOdFeeTotal.getAdjustFee()+apaidFee);
    	ordOdFeeTotal.setPaidFee(pOrdOdFeeTotal.getPaidFee()+apaidFee);
    	ordOdFeeTotal.setTotalJf(pOrdOdFeeTotal.getTotalJf()+ordOdProd.getJf());
    	ordOdFeeTotalAtomSV.updateByOrderId(ordOdFeeTotal);
    }
    
    /**
     * 组装OCF订单创建请求参数
     */
    private  String getOFCRequestParams(OrderPayRequest request,Long orderId,Timestamp sysdate) {
        //封装数据查询该订单下的详细数据
    	ICacheSV iCacheSV = DubboConsumerFactory.getService(ICacheSV.class);
    	QueryOrderRequest orderRequest=new QueryOrderRequest();
        orderRequest.setOrderId(orderId);
        orderRequest.setTenantId(request.getTenantId());
        QueryOrderResponse queryOrder = orderListSV.queryOrder(orderRequest);
        OrdOrderVo ordOrderVo = queryOrder.getOrdOrderVo();
        OFCOrderCreateRequest paramsRequest=new OFCOrderCreateRequest();
        paramsRequest.setOrderNo(String.valueOf(orderId));
        SysParam sysParamChlId = InfoTranslateUtil.translateInfo(request.getTenantId(), 
				"ORD_ORDER", "CHL_ID", ordOrderVo.getChlId(), iCacheSV);
        paramsRequest.setShopName(sysParamChlId==null?"":sysParamChlId.getColumnDesc()); 
        paramsRequest.setReceiverContact(ordOrderVo.getContactName());
        paramsRequest.setReceiverPhone(ordOrderVo.getContactTel());
        paramsRequest.setProvince(ordOrderVo.getProvinceCode()==null?"":iCacheSV.
        		getAreaName(ordOrderVo.getProvinceCode())); 
        paramsRequest.setCity(ordOrderVo.getCityCode()==null?"":iCacheSV.
    			getAreaName(ordOrderVo.getCityCode()));
        paramsRequest.setRegion(ordOrderVo.getCountyCode()==null?"":iCacheSV.
    			getAreaName(ordOrderVo.getCountyCode()));
        paramsRequest.setReceiverAddress(ordOrderVo.getAddress());
        paramsRequest.setPostCode(ordOrderVo.getPostCode());
        paramsRequest.setPayTime(sysdate.toString());
        paramsRequest.setPayNo(String.valueOf(ordOrderVo.getAcctId())); //支付帐号
        paramsRequest.setPayType(Long.parseLong(request.getPayType())); //TODO 待验证
        paramsRequest.setOrderAmout(ordOrderVo.getTotalFee()/10); //分为单位,订单总金额 ??
        paramsRequest.setPayAmount(ordOrderVo.getPayFee()/10);//支付金额
        paramsRequest.setCoupAmount(ordOrderVo.getDiscountFee()/10);//优惠金额
        paramsRequest.setReceiveAmount(ordOrderVo.getPayFee()/10);
        if(!StringUtil.isBlank(ordOrderVo.getInvoiceType())) {
        	paramsRequest.setNeedInvoice(true);
        	paramsRequest.setInvoiceType(Long.parseLong(ordOrderVo.getInvoiceType()));
        	paramsRequest.setCompanyName("");
        	paramsRequest.setTaxNo("");
        	paramsRequest.setRegisterAddress("");
        	paramsRequest.setRegisterTel("");
        	paramsRequest.setBank("");
        	paramsRequest.setBankNo("");
        }else {  //发票类型为空的话,表示无需发票信息
        	paramsRequest.setNeedInvoice(false);
        }
        paramsRequest.setBuyerRemark(ordOrderVo.getRemark());
        paramsRequest.setSellerRemark(ordOrderVo.getOperDiscountDesc()); //TODO 商家备注 减免原因 ?? 
        //订单明细
        List<OrderItemsVo> orderItemsVoList=new ArrayList<OrderItemsVo>();
        List<OrdProductVo> productList = ordOrderVo.getProductList();
        for (OrdProductVo ordProductVo : productList) {
        	OrderItemsVo orderItemsVo=new OrderItemsVo();
        	orderItemsVo.setProductName(ordProductVo.getProdName());
        	orderItemsVo.setProductCode(""); //TODO
        	orderItemsVo.setProductNo("");; //TODO
        	orderItemsVo.setPrice(ordProductVo.getSalePrice());
        	orderItemsVo.setQuanlity(ordProductVo.getBuySum());
        	orderItemsVoList.add(orderItemsVo);
		}
        List<OrderCouponVo> orderCouponVoList=new ArrayList<OrderCouponVo>();
        OrderCouponVo couponVo=new OrderCouponVo();
        couponVo.setCouponName("");
        couponVo.setCouponCode("");
        couponVo.setProductCode(""); //TODO
        couponVo.setAmount(0); //TODO  单个商品的优惠券还是优惠总金额 ???
        orderCouponVoList.add(couponVo);
        //封装参数,转化为json形式
        paramsRequest.setItems(orderItemsVoList);
        paramsRequest.setCouponList(orderCouponVoList);
    	return JSON.toJSONString(paramsRequest);
    }
    
    //TODO 测试
/*    public static void main(String[] args) {
    	OrderPayRequest request=new OrderPayRequest();
    	request.setTenantId("changhong");
    	request.setPayType("28");
    	long orderId=2000000988564944l;
    	 同步长虹OFC,获取参数
        String params = getOFCRequestParams(request, orderId,DateUtil.getSysDate());
        String url="http://10.19.13.16:28151/opaas/http/srv_slp_externalorder_create"; 
        Map<String, String> header=new HashMap<String, String>(); 
        header.put("appkey", "33a6e8c78f71a9f45bc09c572d3491ce");
        //发送Post请求,并返回信息
        try {
			String strData = HttpClientUtil.sendPost(url, params, header);
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}*/
    
}
