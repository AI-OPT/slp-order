package com.ai.slp.order.service.business.impl;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.BeanUtils;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.util.DateUtil;
import com.ai.paas.ipaas.util.StringUtil;
import com.ai.slp.order.api.orderpay.param.OrderOidRequest;
import com.ai.slp.order.api.orderpay.param.OrderPayRequest;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.constants.OrdersConstants.OrdOdStateChg;
import com.ai.slp.order.dao.mapper.bo.*;
import com.ai.slp.order.service.atom.interfaces.*;
import com.ai.slp.order.service.business.interfaces.IOrderFrameCoreSV;
import com.ai.slp.order.service.business.interfaces.IOrderPayBusiSV;
import com.ai.slp.order.util.SequenceUtil;
import com.ai.slp.order.util.ValidateUtils;
import com.ai.slp.product.api.product.interfaces.IProductServerSV;
import com.ai.slp.product.api.product.param.ProductInfoQuery;
import com.ai.slp.product.api.product.param.ProductRoute;
import com.ai.slp.product.api.storageserver.interfaces.IStorageNumSV;
import com.ai.slp.product.api.storageserver.param.StorageNumUserReq;
import com.ai.slp.route.api.routemanage.interfaces.IRouteManageSV;
import com.ai.slp.route.api.routemanage.param.RouteQueryByGroupIdAndAreaRequest;
import com.ai.slp.route.api.routemanage.param.RouteQueryByGroupIdAndAreaResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private IOrdOdLogisticsAtomSV ordOdLogisticsAtomSV;
    
    @Autowired
    private IOrdOdInvoiceAtomSV ordOdInvoiceAtomSV;
    
    @Autowired
    private IOrdOdFeeProdAtomSV ordOdFeeProdAtomSV;
    
    /**
     * 订单收费
     * 
     * @throws Exception
     */
    @Override
    public void orderPay(OrderPayRequest request) throws BusinessException, SystemException {
        /* 1.处理费用信息 */
        OrdOrder ordOrder =null;
        Timestamp sysdate = DateUtil.getSysDate();
        this.orderCharge(request, sysdate);
        for (Long orderId : request.getOrderIds()) {
            ordOrder = ordOrderAtomSV.selectByOrderId(request.getTenantId(), orderId);
            if (ordOrder == null) {
                throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL,
                        "订单信息不存在[订单ID:" + orderId + "]");
            }
            /* 2.订单支付完成后，对订单进行处理 */
            this.execOrders(ordOrder, request.getTenantId(), sysdate);
        	/* 3.拆分子订单 */
        	this.resoleOrders(ordOrder, request.getTenantId(),request,sysdate);
        	/* 4.虚拟商品改变父订单状态*/
        	if(OrdersConstants.OrdOrder.OrderType.VIRTUAL_PROD.equals(ordOrder.getOrderType())) {
        		ordOrder.setState(OrdersConstants.OrdOrder.State.COMPLETED);
        		ordOrderAtomSV.updateById(ordOrder);
        	}
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
    	BigDecimal total = new BigDecimal(0);
        for (OrdOdFeeTotal bean : payFeeList) {
        	BigDecimal singelFee = BigDecimal.valueOf(bean.getPayFee()).divide(new BigDecimal(1000L),2,BigDecimal.ROUND_HALF_UP);
            total = total.add(singelFee);
        }
    	BigDecimal decimal = total.multiply(new BigDecimal(1000));
        return decimal.longValue();
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
        List<OrdOdProd> ordOdProds= ordOdProdAtomSV.selectByOrd(request.getTenantId(), orderId);
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
     * 拆分子订单
     * 
     * @param request
     * @author zhangxw
     * @param client
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @ApiDocMethod
     */
    private void resoleOrders(OrdOrder ordOrder, String tenantId,
    		OrderPayRequest request,Timestamp sysdate) {
        logger.debug("开始对订单[" + ordOrder.getOrderId() + "]进行拆分..");
    	/* 1.查询费用总表信息*/
    	OrdOdFeeTotal odFeeTotal = ordOdFeeTotalAtomSV.selectByOrderId(tenantId, ordOrder.getOrderId());
    	if(odFeeTotal==null) {
    		throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, 
    				"费用总表信息不存在[orderId:"+ordOrder.getOrderId()+"]");
    	}
    	/* 2.减免费用和总费用*/
    	long operDiscountFee = odFeeTotal.getOperDiscountFee();
    	long totalFee = odFeeTotal.getTotalFee();
    	long freight = odFeeTotal.getFreight();
    	/* 3.查询商品明细表*/
    	List<OrdOdProd> ordOdProdList =ordOdProdAtomSV.selectByOrd(tenantId, ordOrder.getOrderId());
    	if(CollectionUtil.isEmpty(ordOdProdList)) {
    		throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, 
    				"商品明细信息表不存在[orderId:"+ordOrder.getOrderId()+"]");
    	}
    	Map<String, Long> map=new HashMap<String,Long>();
    	for (OrdOdProd ordOdProd : ordOdProdList) {
    		/* 4.获取单个商品费用占总费用的比例*/
    		long discountFee = ordOdProd.getDiscountFee();
        	BigDecimal rate = BigDecimal.valueOf(ordOdProd.getTotalFee()).divide(new BigDecimal(totalFee-freight),5,BigDecimal.ROUND_HALF_UP);
        	long prodOperDiscountFee=(rate.multiply(new BigDecimal(operDiscountFee))).longValue();//商品的减免费用
    		ordOdProd.setOperDiscountFee(prodOperDiscountFee+ordOdProd.getOperDiscountFee()); //减免费用
    		ordOdProd.setDiscountFee(prodOperDiscountFee+discountFee); //优惠费用
    		long adjustFee=ordOdProd.getTotalFee()-(prodOperDiscountFee+discountFee);
    		ordOdProd.setAdjustFee(adjustFee<0?0:adjustFee); //应收费用
    		ordOdProdAtomSV.updateById(ordOdProd);
    		/* 5.生成子订单*/
    		this.createEntitySubOrder(tenantId, ordOrder,ordOdProd,map,request,sysdate);
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
    		OrdOdProd parentOrdOdProd,Map<String, Long> map,OrderPayRequest request,Timestamp sysdate) {
    	/* 1.根据商品信息获取routeId*/
    	String routeGroupId = this.getRouteGroupId(tenantId, parentOrdOdProd.getProdId(),parentOrdOdProd.getSupplierId());
    	IRouteManageSV iRouteManageSV = DubboConsumerFactory.getService(IRouteManageSV.class);
    	OrdOdLogistics ordOdLogistics = ordOdLogisticsAtomSV.selectByOrd(tenantId, parentOrdOrder.getOrderId());
        RouteQueryByGroupIdAndAreaRequest andAreaRequest=new RouteQueryByGroupIdAndAreaRequest();
        andAreaRequest.setProvinceCode(ordOdLogistics.getProvinceCode());
        andAreaRequest.setRouteGroupId(routeGroupId);
        andAreaRequest.setTenantId(tenantId);
        RouteQueryByGroupIdAndAreaResponse routeResponse = iRouteManageSV.queryRouteInfoByGroupIdAndArea(andAreaRequest);
        if(routeResponse==null||(routeResponse!=null&&!routeResponse.getResponseHeader().getIsSuccess())) {
        	throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "根据仓库组ID["
                    + routeGroupId + "]和省份编码[" + ordOdLogistics.getProvinceCode()+ "]未能查询到仓库id");
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
    		if (OrdersConstants.OrdOrder.OrderType.VIRTUAL_PROD.equals(parentOrdOrder.getOrderType())) {
    			childOrdOrder.setState(OrdersConstants.OrdOrder.State.COMPLETED); //虚拟类商品
	        }else {
	        	childOrdOrder.setState(OrdersConstants.OrdOrder.State.WAIT_DISTRIBUTION); //实物
	        }
    		childOrdOrder.setStateChgTime(sysdate);
    		childOrdOrder.setRouteId(routeId); 
    		ordOrderAtomSV.insertSelective(childOrdOrder);
    		/* 2.1.1.创建其它子信息表*/
    		ordOdProd = this.createTableInfo(subOrderId, parentOrdOrder, parentOrdOdProd, routeId,null);
    		this.createFeeTotal(subOrderId, parentOrdOrder, ordOdProd);
    		/* 2.1.2.把routeId和子订单Id放入集合中*/
    		map.put(routeId, subOrderId);
    	}else {
    		/* 2.2.同一个routeId的情况下,订单合并*/
    		subOrderId = map.get(routeId);
    		ordOdProd = this.updateFeeTotal(subOrderId, parentOrdOrder, parentOrdOdProd, routeId);
    	}
    	/* 3.写入订单状态变化轨迹表 */
    	String chgDesc=OrdersConstants.OrdOdStateChg.ChgDesc.ORDER_TO_WAIT_DISTRIBUTION;
    	orderFrameCoreSV.ordOdStateChg(subOrderId, tenantId, parentOrdOrder.getState(),
    			OrdersConstants.OrdOrder.State.WAIT_DISTRIBUTION, chgDesc, null, null, null, DateUtil.getSysDate());
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
        List<OrdOdProd> ordOdProds =ordOdProdAtomSV.selectByOrd(tenantId, ordOrder.getOrderId());
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
     * 根据商品ID查询路由组ID
     * @param tenantId
     * @param productId
     * @return
     * @author zhangxw
     * @ApiDocMethod
     */
    private static String getRouteGroupId(String tenantId, String productId,String supplierId) {
        ProductInfoQuery productInfoQuery = new ProductInfoQuery();
        productInfoQuery.setTenantId(tenantId);
        productInfoQuery.setProductId(productId);
        //TODO
        if(OrdersConstants.INVOICE_SUPPLIERID.equals(supplierId)) {
        	supplierId=OrdersConstants.PROD_SUPPLIERID;
        }
        productInfoQuery.setSupplierId(supplierId);
        IProductServerSV iProductServerSV = DubboConsumerFactory.getService(IProductServerSV.class);
        ProductRoute productRoute = iProductServerSV.queryRouteGroupOfProd(productInfoQuery);
        return productRoute.getRouteGroupId();
    }
    
    /**
     * 拆分时创建表信息
     */
    private OrdOdProd createTableInfo(long subOrderId,OrdOrder parentOrdOrder,OrdOdProd parentOrdOdProd,
    		String routeId,Long parentProdDetalId) {
    	OrdOdProd ordOdProd=null;
    	long prodDetailId = SequenceUtil.createProdDetailId();
    	long logisticsId=SequenceUtil.genLogisticsId();
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
			ordOdInvoiceAtomSV.insertSelective(invoice);
		}
		/*创建子订单-配送信息 */
		OrdOdLogistics odLogistics = ordOdLogisticsAtomSV.selectByOrd(parentOrdOrder.getTenantId(), 
				parentOrdOrder.getOrderId());
		OrdOdLogistics newLogistics=new OrdOdLogistics();
		BeanUtils.copyProperties(newLogistics, odLogistics);
		newLogistics.setOrderId(subOrderId);
		newLogistics.setLogisticsId(logisticsId);
		ordOdLogisticsAtomSV.insertSelective(newLogistics);
        return ordOdProd;
    }
    
    
    /**
     * 创建费用总表信息
     */
    private void createFeeTotal(long subOrderId,OrdOrder parentOrdOrder,OrdOdProd ordOdProd) {
    	/* 创建子订单-费用汇总表*/
    	long parentOrderId = parentOrdOrder.getOrderId();
    	OrdOdFeeTotal parentOrdOdFeeTotal = ordOdFeeTotalAtomSV.selectByOrderId(parentOrdOrder.getTenantId(), 
    			parentOrderId);
    	if(parentOrdOdFeeTotal==null) {
    		throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, 
    				"订单费用总表信息不存在[父订单Id:"+parentOrderId+"]");
    	}
    	OrdOdFeeTotal ordOdFeeTotal=new OrdOdFeeTotal();
    	BeanUtils.copyProperties(ordOdFeeTotal, parentOrdOdFeeTotal);
    	ordOdFeeTotal.setOrderId(subOrderId);
    	ordOdFeeTotal.setTotalFee(ordOdProd.getTotalFee());
    	ordOdFeeTotal.setDiscountFee(ordOdProd.getDiscountFee());
    	long apaidFee=ordOdProd.getTotalFee()-ordOdProd.getDiscountFee();
    	if(apaidFee<0) {
    		apaidFee=0;
    	}
    	ordOdFeeTotal.setAdjustFee(apaidFee);
    	ordOdFeeTotal.setPaidFee(apaidFee);
    	ordOdFeeTotal.setTotalJf(ordOdProd.getJf());
    	ordOdFeeTotal.setFreight(0);//TODO
    	ordOdFeeTotal.setUpdateTime(DateUtil.getSysDate());
    	ordOdFeeTotalAtomSV.insertSelective(ordOdFeeTotal);
    	/* 创建子订单-支付机构接口*/
    	OrdBalacneIf balacneIf = ordBalacneIfAtomSV.selectByOrderId(parentOrdOrder.getTenantId(), 
    			parentOrderId);
    	if(balacneIf==null) {
    		throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, 
    				"订单支付机构信息不存在[父订单Id:"+parentOrderId+"]");
    	}
    	OrdBalacneIf subBalacneIf=new OrdBalacneIf();
    	long balacneIfId = SequenceUtil.createBalacneIfId();
    	BeanUtils.copyProperties(subBalacneIf, balacneIf);
    	subBalacneIf.setBalacneIfId(balacneIfId);
    	subBalacneIf.setCreateTime(DateUtil.getSysDate());
    	subBalacneIf.setOrderId(subOrderId);
    	subBalacneIf.setPayFee(apaidFee);
    	ordBalacneIfAtomSV.insertSelective(subBalacneIf);
    	/* 创建子订单-费用明细信息*/
    	List<OrdOdFeeProd> OrdOdFeeProds = ordOdFeeProdAtomSV.selectByOrderId(parentOrderId);
    	if(CollectionUtil.isEmpty(OrdOdFeeProds)) {
    		throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, 
    				"订单费用明细信息不存在[父订单Id:"+parentOrderId+"]");
    	}
    	for (OrdOdFeeProd ordOdFeeProd : OrdOdFeeProds) {
			OrdOdFeeProd subOrdOdFeeProd=new OrdOdFeeProd();
			subOrdOdFeeProd.setOrderId(subOrderId);
			if(OrdersConstants.OrdOdFeeProd.PayStyle.JF.equals(ordOdFeeProd.getPayStyle())) {
				subOrdOdFeeProd.setPayStyle(ordOdFeeProd.getPayStyle());
				subOrdOdFeeProd.setPaidFee(ordOdProd.getJfFee());
				String rate = parentOrdOrder.getPointRate();
				if(!StringUtil.isBlank(rate)) {
					String[] split = rate.split(":");
					BigDecimal JfAmout=BigDecimal.valueOf(ordOdProd.getJfFee()).divide(new BigDecimal(split[0]),
							5,BigDecimal.ROUND_HALF_UP);
					subOrdOdFeeProd.setJfAmount(JfAmout.multiply(new BigDecimal(1000)).longValue());//积分对应的金额,并元转厘
				}
			}else if(OrdersConstants.OrdOdFeeProd.PayStyle.COUPON.equals(ordOdFeeProd.getPayStyle())) {
				subOrdOdFeeProd.setPayStyle(ordOdFeeProd.getPayStyle());
				subOrdOdFeeProd.setPaidFee(ordOdProd.getCouponFee());//优惠券
			}else {
				subOrdOdFeeProd.setPayStyle(ordOdFeeProd.getPayStyle());
				subOrdOdFeeProd.setPaidFee(ordOdProd.getAdjustFee());//运费不涉及
			}
			ordOdFeeProdAtomSV.insertSelective(subOrdOdFeeProd);
		}
    }
    
    /**
     * 更新费用总表信息(同一个routeId的情况下,订单费用总表合并)
     */
    private OrdOdProd updateFeeTotal(long subOrderId,OrdOrder parentOrdOrder,OrdOdProd parentOrdOdProd,String routeId) {
    	/* 创建子订单-商品明细信息*/ 
    	long prodDetailId = SequenceUtil.createProdDetailId();
		OrdOdProd ordOdProd = new OrdOdProd();
		BeanUtils.copyProperties(ordOdProd, parentOrdOdProd);
		ordOdProd.setProdDetalId(prodDetailId);
		ordOdProd.setOrderId(subOrderId);
		ordOdProd.setRouteId(routeId);
		ordOdProdAtomSV.insertSelective(ordOdProd);
    	/* 创建子订单-费用汇总表*/
    	OrdOdFeeTotal pOrdOdFeeTotal = ordOdFeeTotalAtomSV.selectByOrderId(parentOrdOrder.getTenantId(), 
    			subOrderId);
    	if(pOrdOdFeeTotal==null) {
    		throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, 
    				"订单费用总表不存在[orderId:"+parentOrdOrder.getOrderId()+"]");
    	}
    	OrdOdFeeTotal ordOdFeeTotal=new OrdOdFeeTotal();
    	BeanUtils.copyProperties(ordOdFeeTotal, pOrdOdFeeTotal);
    	ordOdFeeTotal.setTotalFee(pOrdOdFeeTotal.getTotalFee()+ordOdProd.getTotalFee());
    	ordOdFeeTotal.setDiscountFee(pOrdOdFeeTotal.getDiscountFee()+ordOdProd.getDiscountFee());
    	long apaidFee=ordOdProd.getTotalFee()-ordOdProd.getDiscountFee();
    	if(apaidFee<0) {
    		apaidFee=0;
    	}
    	ordOdFeeTotal.setAdjustFee(pOrdOdFeeTotal.getAdjustFee()+apaidFee);
    	ordOdFeeTotal.setPaidFee(pOrdOdFeeTotal.getPaidFee()+apaidFee);
    	ordOdFeeTotal.setTotalJf(pOrdOdFeeTotal.getTotalJf()+ordOdProd.getJf());
    	ordOdFeeTotal.setUpdateTime(DateUtil.getSysDate());
    	ordOdFeeTotalAtomSV.updateByOrderId(ordOdFeeTotal);
    	/* 更新子订单-支付机构接口*/
    	OrdBalacneIf balacneIf = ordBalacneIfAtomSV.selectByOrderId(parentOrdOrder.getTenantId(), 
    			subOrderId);
    	if(balacneIf==null) {
    		throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, 
    				"订单支付机构信息不存在[订单Id:"+subOrderId+"]");
    	}
    	balacneIf.setPayFee(balacneIf.getPayFee()+apaidFee);
    	ordBalacneIfAtomSV.updateByPrimaryKey(balacneIf);
    	/* 创建子订单-费用明细信息*/
    	List<OrdOdFeeProd> OrdOdFeeProds = ordOdFeeProdAtomSV.selectByOrderId(subOrderId);
    	if(CollectionUtil.isEmpty(OrdOdFeeProds)) {
    		throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, 
    				"订单费用明细信息不存在[订单Id:"+subOrderId+"]");
    	}
    	for (OrdOdFeeProd ordOdFeeProd : OrdOdFeeProds) {
    		OrdOdFeeProd subOrdOdFeeProd=new OrdOdFeeProd();
    		if(OrdersConstants.OrdOdFeeProd.PayStyle.JF.equals(ordOdFeeProd.getPayStyle())) {
				subOrdOdFeeProd.setPayStyle(ordOdFeeProd.getPayStyle());
				subOrdOdFeeProd.setPaidFee(ordOdFeeProd.getPaidFee()+ordOdProd.getJfFee());
				String rate = parentOrdOrder.getPointRate();
				if(!StringUtil.isBlank(rate)) {
					String[] split = rate.split(":");
					BigDecimal JfAmout=BigDecimal.valueOf(subOrdOdFeeProd.getPaidFee()).divide(new BigDecimal(split[0]),
							5,BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(split[1]),5,BigDecimal.ROUND_HALF_UP);
					long jfAmount=(JfAmout.multiply(new BigDecimal(1000))).longValue();
					subOrdOdFeeProd.setJfAmount(jfAmount);//积分对应的金额,并元转厘
				}
			}else if(OrdersConstants.OrdOdFeeProd.PayStyle.COUPON.equals(ordOdFeeProd.getPayStyle())) {
				subOrdOdFeeProd.setPayStyle(ordOdFeeProd.getPayStyle());
				subOrdOdFeeProd.setPaidFee(ordOdFeeProd.getPaidFee()+ordOdProd.getCouponFee());//优惠券
			}else {
				subOrdOdFeeProd.setPayStyle(ordOdFeeProd.getPayStyle());
				subOrdOdFeeProd.setPaidFee(ordOdFeeProd.getPaidFee()+ordOdProd.getAdjustFee());//运费不涉及
			}
    		subOrdOdFeeProd.setOrderId(subOrderId);
    		ordOdFeeProdAtomSV.updateByExample(subOrdOdFeeProd, subOrderId, ordOdFeeProd.getPayStyle());
		}
    	return ordOdProd;
    }
    
    
    /**
     * 用户消费积分返回oid
     */
	@Override
	public void returnOid(OrderOidRequest request) throws BusinessException, SystemException {
		/* 参数校验*/
		ValidateUtils.validateReturnOid(request);
		OrdOrder order = ordOrderAtomSV.selectByOrderId(request.getTenantId(), request.getOrderId());
		if(order==null) {
			throw new BusinessException("", "订单信息不存在[订单id:"+request.getOrderId()+
					",租户id:"+request.getTenantId()+"]");
		}
		order.setDownstreamOrderId(request.getOid());
		ordOrderAtomSV.updateById(order);
	}
}
