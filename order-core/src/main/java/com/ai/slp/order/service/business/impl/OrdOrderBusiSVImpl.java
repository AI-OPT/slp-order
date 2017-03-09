package com.ai.slp.order.service.business.impl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.ai.opt.sdk.util.BeanUtils;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.util.ParseO2pDataUtil;
import com.ai.opt.sdk.util.StringUtil;
import com.ai.paas.ipaas.search.vo.Result;
import com.ai.paas.ipaas.search.vo.SearchCriteria;
import com.ai.paas.ipaas.search.vo.SearchOption;
import com.ai.paas.ipaas.search.vo.Sort;
import com.ai.paas.ipaas.search.vo.Sort.SortOrder;
import com.ai.platform.common.api.cache.interfaces.ICacheSV;
import com.ai.platform.common.api.cache.param.SysParam;
import com.ai.slp.order.api.orderlist.param.BehindOrdOrderVo;
import com.ai.slp.order.api.orderlist.param.BehindOrdProductVo;
import com.ai.slp.order.api.orderlist.param.BehindParentOrdOrderVo;
import com.ai.slp.order.api.orderlist.param.BehindQueryOrderListRequest;
import com.ai.slp.order.api.orderlist.param.BehindQueryOrderListResponse;
import com.ai.slp.order.api.orderlist.param.OrdOrderVo;
import com.ai.slp.order.api.orderlist.param.OrdProductVo;
import com.ai.slp.order.api.orderlist.param.ProductImage;
import com.ai.slp.order.api.orderlist.param.QueryOrderRequest;
import com.ai.slp.order.api.orderlist.param.QueryOrderResponse;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.constants.SearchFieldConfConstants;
import com.ai.slp.order.dao.mapper.attach.BehindOrdOrderAttach;
import com.ai.slp.order.dao.mapper.bo.OrdBalacneIf;
import com.ai.slp.order.dao.mapper.bo.OrdBalacneIfCriteria;
import com.ai.slp.order.dao.mapper.bo.OrdBalacneIfCriteria.Criteria;
import com.ai.slp.order.dao.mapper.bo.OrdOdFeeProd;
import com.ai.slp.order.dao.mapper.bo.OrdOdFeeProdCriteria;
import com.ai.slp.order.dao.mapper.bo.OrdOdFeeTotal;
import com.ai.slp.order.dao.mapper.bo.OrdOdInvoice;
import com.ai.slp.order.dao.mapper.bo.OrdOdLogistics;
import com.ai.slp.order.dao.mapper.bo.OrdOdProd;
import com.ai.slp.order.dao.mapper.bo.OrdOdProdCriteria;
import com.ai.slp.order.dao.mapper.bo.OrdOdProdExtend;
import com.ai.slp.order.dao.mapper.bo.OrdOdProdExtendCriteria;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.dao.mapper.bo.OrdOrderCriteria;
import com.ai.slp.order.search.bo.OrdProdExtend;
import com.ai.slp.order.search.bo.OrderInfo;
import com.ai.slp.order.search.bo.ProdInfo;
import com.ai.slp.order.service.atom.interfaces.IOrdBalacneIfAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdFeeProdAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdFeeTotalAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdInvoiceAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdLogisticsAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdProdAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdProdExtendAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAttachAtomSV;
import com.ai.slp.order.service.business.impl.search.OrderSearchImpl;
import com.ai.slp.order.service.business.interfaces.IOrdOrderBusiSV;
import com.ai.slp.order.service.business.interfaces.search.IOrderSearch;
import com.ai.slp.order.util.CommonCheckUtils;
import com.ai.slp.order.util.InfoTranslateUtil;
import com.ai.slp.order.util.ValidateUtils;
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

	private static final Logger logger = LoggerFactory.getLogger(OrdOrderBusiSVImpl.class);

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

	/**
	 * 商品集合
	 * 
	 * @param orderId
	 * @return
	 * @author zhangxw
	 * @param tenantId
	 * @ApiDocMethod
	 */
	private List<OrdProductVo> getOrdProductList(String tenantId, long orderId) {
		List<OrdProductVo> productList = new ArrayList<OrdProductVo>();
		List<OrdOdProd> ordOdProdList=ordOdProdAtomSV.selectByOrd(tenantId, orderId);
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
				ordProductVo.setCouponFee(ordOdProd.getCouponFee()); // 优惠费用
				ordProductVo.setCusServiceFlag(ordOdProd.getCusServiceFlag()); // 商品是否售后标识
				ordProductVo.setJfFee(ordOdProd.getJfFee()); // 消费积分
				ordProductVo.setGiveJF(ordOdProd.getJf()); // 赠送积分
				ordProductVo.setProdCode(ordOdProd.getProdCode()); // 商品编码
				ordProductVo.setSkuStorageId(ordOdProd.getSkuStorageId());// 仓库id
				long start = System.currentTimeMillis();
				logger.info("开始执行商品信息查询>>>>>>>>>>>>>>：" + start);
				ProductImage productImage = this.getProductImage(tenantId, ordOdProd.getSkuId());
				long end = System.currentTimeMillis();
				logger.info("结束执行商品信息查询>>>>>>>>>>>>>>:" + end + ",用时:"+ (end - start) + "毫秒");
				ordProductVo.setProductImage(productImage);
				ordProductVo.setImageUrl(ordOdProd.getProdDesc()); // 图片id
				ordProductVo.setProdExtendInfo(ordOdProd.getProdSn()); // 图片类型
				productList.add(ordProductVo);
			}
		}
		return productList;
	}

	/**
	 * 获取图片信息
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
		ProductSkuInfo productSkuInfo =iProductServerSV.queryProductSkuById4ShopCart(skuInfoQuery);
		productImage.setVfsId(productSkuInfo.getVfsId());
		productImage.setPicType(productSkuInfo.getPicType());
		return productImage;
	}

	//订单详情查询
	@Override
	public QueryOrderResponse queryOrder(QueryOrderRequest orderRequest) throws BusinessException, SystemException {
		logger.debug("开始订单详情查询..");
		/* 1.订单信息查询-参数校验 */
		ValidateUtils.validateQueryOrder(orderRequest);
		ICacheSV iCacheSV = DubboConsumerFactory.getService(ICacheSV.class);
		QueryOrderResponse response = new QueryOrderResponse();
		OrdOrder order = ordOrderAtomSV.selectByOrderId(orderRequest.getTenantId(),
				orderRequest.getOrderId());
		OrdOrderVo ordOrderVo = null;
		if (order!=null) {
			ordOrderVo = new OrdOrderVo();
			ordOrderVo.setOrderId(order.getOrderId());
			ordOrderVo.setOrderType(order.getOrderType());
			SysParam sysParamOrderType = InfoTranslateUtil.translateInfo(order.getTenantId(), "ORD_ORDER", "ORDER_TYPE",
					order.getOrderType(), iCacheSV);
			ordOrderVo.setOrderTypeName(sysParamOrderType == null ? "" : sysParamOrderType.getColumnDesc());
			ordOrderVo.setBusiCode(order.getBusiCode());
			ordOrderVo.setState(order.getState());
			SysParam sysParamState = InfoTranslateUtil.translateInfo(order.getTenantId(), "ORD_ORDER", "STATE",
					ordOrderVo.getState(), iCacheSV);
			ordOrderVo.setStateName(sysParamState == null ? "" : sysParamState.getColumnDesc());
			ordOrderVo.setChlId(order.getChlId()); // 订单来源
			ordOrderVo.setRouteId(order.getRouteId());// 仓库ID
			IRouteManageSV iRouteManageSV = DubboConsumerFactory.getService(IRouteManageSV.class);
			RouteIdParamRequest routeRequest = new RouteIdParamRequest();
			if (order.getRouteId() != null) {
				routeRequest.setRouteId(order.getRouteId());
				RouteResponse routeInfo = iRouteManageSV.findRouteInfo(routeRequest);
				ordOrderVo.setRouteName(routeInfo.getRouteName()); // 仓库信息
			}
			ordOrderVo.setParentOrderId(order.getParentOrderId());
			ordOrderVo.setUserId(order.getUserId());// 买家帐号(用户号)
			ordOrderVo.setAccountId(order.getAccountId());
			ordOrderVo.setToken(order.getTokenId());// 积分令牌
			ordOrderVo.setDownstreamOrderId(order.getDownstreamOrderId());
			ordOrderVo.setUserName(order.getUserName());
			ordOrderVo.setRemark(order.getRemark());// 买家留言(订单备注)
			ordOrderVo.setOrigOrderId(order.getOrigOrderId()); // 原始订单号
			ordOrderVo.setOperId(order.getOperId());
			ordOrderVo.setAcctId(order.getAcctId());
			ordOrderVo.setOrderTime(order.getOrderTime());
			// 获取业务类型
			SysParam sysParamBusiCode = InfoTranslateUtil.translateInfo(order.getTenantId(), "ORD_ORDER", "BUSI_CODE",
					order.getBusiCode(), iCacheSV);
			ordOrderVo.setBusiCodeName(sysParamBusiCode == null ? "" : sysParamBusiCode.getColumnDesc());
			/* 2.订单费用信息查询 */
			OrdOdFeeTotal ordOdFeeTotal = ordOdFeeTotalAtomSV.selectByOrderId(order.getTenantId(), 
					order.getOrderId());
			if (ordOdFeeTotal!=null) {
				ordOrderVo.setAdjustFee(ordOdFeeTotal.getAdjustFee());
				ordOrderVo.setDiscountFee(ordOdFeeTotal.getDiscountFee());
				ordOrderVo.setOperDiscountFee(ordOdFeeTotal.getOperDiscountFee());
				ordOrderVo.setOperDiscountDesc(ordOdFeeTotal.getOperDiscountDesc());
				ordOrderVo.setPaidFee(ordOdFeeTotal.getPaidFee());
				ordOrderVo.setPayFee(ordOdFeeTotal.getPayFee());
				ordOrderVo.setPayStyle(ordOdFeeTotal.getPayStyle());
				SysParam sysParam = InfoTranslateUtil.translateInfo(ordOdFeeTotal.getTenantId(), "ORD_OD_FEE_TOTAL",
						"PAY_STYLE", ordOdFeeTotal.getPayStyle(), iCacheSV);
				ordOrderVo.setPayStyleName(sysParam == null ? "" : sysParam.getColumnDesc());
				ordOrderVo.setPayTime(ordOdFeeTotal.getUpdateTime());
				ordOrderVo.setTotalFee(ordOdFeeTotal.getTotalFee());
				ordOrderVo.setFreight(ordOdFeeTotal.getFreight()); // 运费
				/* 3.订单发票信息查询 */
				OrdOdInvoice ordOdInvoice = ordOdInvoiceAtomSV.selectByPrimaryKey(order.getOrderId());
				if (ordOdInvoice != null) {
					ordOrderVo.setInvoiceTitle(ordOdInvoice.getInvoiceTitle());
					ordOrderVo.setInvoiceType(ordOdInvoice.getInvoiceType());
					SysParam sysParamInvoice = InfoTranslateUtil.translateInfo(order.getTenantId(), "ORD_OD_INVOICE",
							"INVOICE_TYPE", ordOdInvoice.getInvoiceType(), iCacheSV);
					ordOrderVo.setInvoiceTypeName(sysParamInvoice == null ? "" : sysParamInvoice.getColumnDesc());
					ordOrderVo.setInvoiceContent(ordOdInvoice.getInvoiceContent());
					ordOrderVo.setBuyerTaxpayerNumber(ordOdInvoice.getBuyerTaxpayerNumber());
					ordOrderVo.setBuyerBankName(ordOdInvoice.getBuyerBankName());
					ordOrderVo.setBuyerBankAccount(ordOdInvoice.getBuyerBankAccount());
					ordOrderVo.setInvoiceStatus(ordOdInvoice.getInvoiceStatus());
				}
				/* 4.订单配送信息查询 */
				OrdOdLogistics ordOdLogistics = null;
				OrdOdLogistics afterLogistics = null;
				if (!OrdersConstants.OrdOrder.BusiCode.NORMAL_ORDER.equals(order.getBusiCode())) {
					// 售后单获取子订单配送信息
					afterLogistics = ordOdLogisticsAtomSV.selectByOrd(order.getTenantId(), order.getOrigOrderId());
					StringBuffer sbf = new StringBuffer();
					sbf.append(afterLogistics.getProvinceCode() == null ? ""
							: iCacheSV.getAreaName(afterLogistics.getProvinceCode()));
					sbf.append(afterLogistics.getCityCode() == null ? ""
							: iCacheSV.getAreaName(afterLogistics.getCityCode()));
					sbf.append(afterLogistics.getCountyCode() == null ? ""
							: iCacheSV.getAreaName(afterLogistics.getCountyCode()));
					sbf.append(afterLogistics.getAddress());
					ordOrderVo.setAftercontactTel(afterLogistics.getContactTel());
					ordOrderVo.setAftercontactInfo(sbf.toString());
				}
				ordOdLogistics = ordOdLogisticsAtomSV.selectByOrd(order.getTenantId(), order.getOrderId());
				if (ordOdLogistics != null) {
					ordOrderVo.setExpressOddNumber(ordOdLogistics.getExpressOddNumber());
					ordOrderVo.setContactCompany(ordOdLogistics.getContactCompany());
					ordOrderVo.setLogisticsType(ordOdLogistics.getLogisticsType());
					ordOrderVo.setContactName(ordOdLogistics.getContactName());
					ordOrderVo.setContactTel(ordOdLogistics.getContactTel());
					ordOrderVo.setProvinceCode(ordOdLogistics.getProvinceCode() == null ? ""
							: iCacheSV.getAreaName(ordOdLogistics.getProvinceCode()));
					ordOrderVo.setCityCode(ordOdLogistics.getCityCode() == null ? ""
							: iCacheSV.getAreaName(ordOdLogistics.getCityCode()));
					ordOrderVo.setCountyCode(ordOdLogistics.getCountyCode() == null ? ""
							: iCacheSV.getAreaName(ordOdLogistics.getCountyCode()));
					ordOrderVo.setPostCode(ordOdLogistics.getPostcode());
					ordOrderVo.setAreaCode(ordOdLogistics.getAreaCode() == null ? ""
							: iCacheSV.getAreaName(ordOdLogistics.getAreaCode()));
					ordOrderVo.setAddress(ordOdLogistics.getAddress());
					ordOrderVo.setExpressId(ordOdLogistics.getExpressId());
				}
				/* 5.订单商品明细查询 */
				List<OrdProductVo> productList = this.getOrdProductList(order.getTenantId(), order.getOrderId());
				ordOrderVo.setProductList(productList);
				/* 6.订单支付机构查询 */
				OrdBalacneIfCriteria exampleBalance = new OrdBalacneIfCriteria();
				Criteria criteriaBalance = exampleBalance.createCriteria();
				criteriaBalance.andTenantIdEqualTo(order.getTenantId());
				if (OrdersConstants.OrdOrder.State.WAIT_PAY.equals(order.getState())
						|| OrdersConstants.OrdOrder.State.CANCEL.equals(order.getState())
						|| (OrdersConstants.OrdOrder.State.COMPLETED.equals(order.getState())&& 
								OrdersConstants.OrdOrder.Flag.JFSYNCH.equals(order.getFlag()))) {
					criteriaBalance.andOrderIdEqualTo(order.getOrderId());
				} else {
					criteriaBalance.andOrderIdEqualTo(order.getParentOrderId());
				}
				List<OrdBalacneIf> ordBalacneIfs = ordBalacneIfAtomSV.selectByExample(exampleBalance);
				if (!CollectionUtil.isEmpty(ordBalacneIfs)) {
					OrdBalacneIf ordBalacneIf = ordBalacneIfs.get(0);
					ordOrderVo.setBalacneIfId(ordBalacneIf.getBalacneIfId());
					ordOrderVo.setExternalId(ordBalacneIf.getExternalId());
				}
			}
		}
		response.setOrdOrderVo(ordOrderVo);
		return response;
	}

	//订单列表查询
	@Override
	public BehindQueryOrderListResponse behindQueryOrderList(BehindQueryOrderListRequest orderListRequest)
			throws BusinessException, SystemException {
		// 调用搜索引擎进行查询
		int startSize = 1;
		int maxSize = 1;
		// 最大条数设置
		int pageNo = orderListRequest.getPageNo();
		int size = orderListRequest.getPageSize();
		if (pageNo == 1) {
			startSize = 0;
		} else {
			startSize = (pageNo - 1) * size;
		}
		maxSize = size;
		BehindQueryOrderListResponse response=new BehindQueryOrderListResponse();
		PageInfo<BehindParentOrdOrderVo> pageInfo=new PageInfo<BehindParentOrdOrderVo>();
		List<BehindParentOrdOrderVo> results = new ArrayList<BehindParentOrdOrderVo>();
	//	List<BehindOrdOrderVo> behindOrdOrderVos = new ArrayList<BehindOrdOrderVo>();
	//	List<BehindOrdProductVo> ordProductVos = new ArrayList<BehindOrdProductVo>();
		
		IOrderSearch orderSearch = new OrderSearchImpl();
		List<SearchCriteria> orderSearchCriteria = commonConditions(orderListRequest);
		//排序
		List<Sort> sortList = new ArrayList<Sort>();
		Sort sort = new Sort(SearchFieldConfConstants.ORDER_TIME, SortOrder.DESC);
		sortList.add(sort);
		Result<OrderInfo> result = orderSearch.search(orderSearchCriteria, startSize, maxSize, sortList);
		List<OrderInfo> ordList = result.getContents();
		/*for (OrderInfo orderInfo : ordList) {
			BehindParentOrdOrderVo parentOrdOrderVo=new BehindParentOrdOrderVo();
			BeanUtils.copyProperties(parentOrdOrderVo, orderInfo);
			List<OrdProdExtend> ordextendes = orderInfo.getOrdextendes();
			for (OrdProdExtend ordProdExtend : ordextendes) {
				BehindOrdOrderVo ordOrderVo=new BehindOrdOrderVo();
				BeanUtils.copyProperties(ordOrderVo, ordProdExtend);
				List<ProdInfo> prodinfos = ordProdExtend.getProdinfos();
				for (ProdInfo prodInfo : prodinfos) {
					BehindOrdProductVo ordProductVo=new BehindOrdProductVo();
					BeanUtils.copyProperties(ordProductVo, prodInfo);
					ordProductVos.add(ordProductVo);
				}
				ordOrderVo.setProductList(ordProductVos);
				behindOrdOrderVos.add(ordOrderVo);
			}
			parentOrdOrderVo.setOrderList(behindOrdOrderVos);
			results.add(parentOrdOrderVo);
		}*/
		pageInfo.setPageNo(pageNo);
		pageInfo.setPageSize(maxSize);
		pageInfo.setResult(results);
		pageInfo.setCount(Long.valueOf(result.getCount()).intValue());
		response.setPageInfo(pageInfo);
		return response;
		
		/*
		long start = System.currentTimeMillis();
		logger.info("开始执行dubbo订单列表查询behindQueryOrderList，当前时间戳：" + start);
		logger.debug("开始运营后台订单列表查询..");
		 参数校验 
		if (orderListRequest == null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "参数对象不能为空");
		}
		CommonCheckUtils.checkTenantId(orderListRequest.getTenantId(), ExceptCodeConstants.Special.PARAM_IS_NULL);
		ICacheSV iCacheSV = DubboConsumerFactory.getService(ICacheSV.class);
		BehindQueryOrderListResponse response = new BehindQueryOrderListResponse();
		PageInfo<BehindParentOrdOrderVo> pageInfo = new PageInfo<BehindParentOrdOrderVo>();
		String states = "";
		StringBuffer sb = new StringBuffer("");
		List<Object> stateList = orderListRequest.getStateList();
		if (!CollectionUtil.isEmpty(stateList)) {
			for (Object state : stateList) {
				sb = sb.append(state).append(",");
			}
			states = sb.toString();
			states = states.substring(0, sb.length() - 1);
		}
		long countStart = System.currentTimeMillis();
		logger.info("开始执行dubbo后场订单列表查询behindQueryOrderList，获取订单列表数量,当前时间戳：" + countStart);
		int count = ordOrderAttachAtomSV.behindQueryCount(orderListRequest, states);
		long countEnd = System.currentTimeMillis();
		logger.info("开始执行dubbo后场订单列表查询behindQueryOrderList，获取订单列表数量,当前时间戳：" + countEnd + ",用时:"
				+ (countEnd - countStart) + "毫秒");
		List<BehindParentOrdOrderVo> orderVoList = this.getBehindOrdOrderVos(orderListRequest, states, iCacheSV);
		pageInfo.setPageNo(orderListRequest.getPageNo());
		pageInfo.setPageSize(orderListRequest.getPageSize());
		pageInfo.setResult(orderVoList);
		pageInfo.setCount(count);
		response.setPageInfo(pageInfo);
		return response;*/
	}

	/**
	 * 订单商品明细信息扩展表
	 */
	public List<OrdOdProdExtend> getOrdOdProdExtendList(String tenantId, long orderId, long prodDetalId) {
		OrdOdProdExtendCriteria example = new OrdOdProdExtendCriteria();
		OrdOdProdExtendCriteria.Criteria criteria = example.createCriteria();
		criteria.andTenantIdEqualTo(tenantId);
		criteria.andOrderIdEqualTo(orderId);
		criteria.andProdDetalIdEqualTo(prodDetalId);
		List<OrdOdProdExtend> ordOdProdExtendList = ordOdProdExtendAtomSV.selectByExample(example);
		return ordOdProdExtendList;
	}

	/**
	 * 运营后台列表信息
	 */
	/*private List<BehindParentOrdOrderVo> getBehindOrdOrderVos(BehindQueryOrderListRequest orderListRequest,
			String states, ICacheSV iCacheSV) {
		List<BehindParentOrdOrderVo> orderVoList = new ArrayList<BehindParentOrdOrderVo>();
		long infoStart = System.currentTimeMillis();
		logger.info("开始执行dubbo后场订单列表查询behindQueryOrderList，获取订单列表信息,当前时间戳：" + infoStart);
		List<BehindOrdOrderAttach> parentList = ordOrderAttachAtomSV.behindQueryOrderBySearch(orderListRequest, states);
		long infoEnd = System.currentTimeMillis();
		logger.info("开始执行dubbo后场订单列表查询behindQueryOrderList，获取订单列表信息,当前时间戳：" + infoEnd + ",用时:" + (infoEnd - infoStart)
				+ "毫秒");
		if (!CollectionUtil.isEmpty(parentList)) {
			for (BehindOrdOrderAttach behindOrdOrderAttach : parentList) {
				BehindParentOrdOrderVo pOrderVo = new BehindParentOrdOrderVo();
				List<BehindOrdOrderVo> orderList = new ArrayList<BehindOrdOrderVo>();
				pOrderVo.setpOrderId(behindOrdOrderAttach.getOrderId());
				pOrderVo.setChlId(behindOrdOrderAttach.getChlId());
				SysParam sysParamChlId = InfoTranslateUtil.translateInfo(behindOrdOrderAttach.getTenantId(),
						"ORD_ORDER", "CHL_ID", behindOrdOrderAttach.getChlId(), iCacheSV);
				pOrderVo.setChlIdName(sysParamChlId == null ? "" : sysParamChlId.getColumnDesc());
				pOrderVo.setContactTel(behindOrdOrderAttach.getContactTel());
				pOrderVo.setUserId(behindOrdOrderAttach.getUserId());
				pOrderVo.setUserName(behindOrdOrderAttach.getUserName());
				pOrderVo.setUserTel(behindOrdOrderAttach.getUserTel());
				pOrderVo.setDeliveryFlag(behindOrdOrderAttach.getDeliveryFlag());
				SysParam sysParamDf = InfoTranslateUtil.translateInfo(behindOrdOrderAttach.getTenantId(), "ORD_ORDER",
						"ORD_DELIVERY_FLAG", behindOrdOrderAttach.getDeliveryFlag(), iCacheSV);
				pOrderVo.setDeliveryFlagName(sysParamDf == null ? "" : sysParamDf.getColumnDesc());
				String arr = "21,212,213,312,22,23,31,92,93,94,95"; // 售后状态
				String[] str = arr.split(",");
				List<String> list = Arrays.asList(str);
				boolean flag = arr.equals(states);
				if (!(list.contains(states) || flag)) {
					pOrderVo.setAdjustFee(behindOrdOrderAttach.getAdjustFee());
					pOrderVo.setDiscountFee(behindOrdOrderAttach.getDiscountFee());// 优惠金额
				}
				OrdOrderCriteria exampleOrder = new OrdOrderCriteria();
				OrdOrderCriteria.Criteria criteriaOrder = exampleOrder.createCriteria();
				criteriaOrder.andParentOrderIdEqualTo(behindOrdOrderAttach.getOrderId());
				criteriaOrder.andTenantIdEqualTo(orderListRequest.getTenantId());
				if (!StringUtil.isBlank(orderListRequest.getRouteId())) {
					criteriaOrder.andRouteIdEqualTo(orderListRequest.getRouteId());
				}
				if (!StringUtil.isBlank(states)) {
					String[] strState = states.split(",");
					List<String> asList = Arrays.asList(strState);
					criteriaOrder.andStateIn(asList);
				}
				if (!(list.contains(states) || flag)) {
					criteriaOrder.andCusServiceFlagEqualTo(OrdersConstants.OrdOrder.cusServiceFlag.NO);
				} else {
					criteriaOrder.andCusServiceFlagEqualTo(OrdersConstants.OrdOrder.cusServiceFlag.YES);
				}
				List<OrdOrder> orders = ordOrderAtomSV.selectByExample(exampleOrder);
				int totalProdSize = 0;
				if (CollectionUtil.isEmpty(orders)) {
					BehindOrdOrderVo orderVo = new BehindOrdOrderVo();
					 查询父订单下的商品信息 
					List<BehindOrdProductVo> prodList = this.getProdList(null, orderListRequest, behindOrdOrderAttach,
							null);
					orderVo.setProductList(prodList);
					orderVo.setProdSize(prodList.size());
					totalProdSize = prodList.size() + totalProdSize;
					orderVo.setState(behindOrdOrderAttach.getState());
					orderVo.setParentOrderId(behindOrdOrderAttach.getOrderId());
					SysParam sysParamState = InfoTranslateUtil.translateInfo(orderListRequest.getTenantId(),
							"ORD_ORDER", "STATE", behindOrdOrderAttach.getState(), iCacheSV);
					orderVo.setStateName(sysParamState == null ? "" : sysParamState.getColumnDesc());
					orderList.add(orderVo);
					pOrderVo.setOrderList(orderList);
				} else {
					for (OrdOrder ordOrder : orders) {
						BehindOrdOrderVo orderVo = new BehindOrdOrderVo();
						// 订单查询OFC
						if (OrdersConstants.OrdOrder.Flag.OFC_ACTUAL_TIME.equals(ordOrder.getFlag())) {
							String logisticsName = null;
							String logisticsNo = null;
							JSONObject object = queryOFC(ordOrder);
							// TODO 判断
							if(object!=null) {
								JSONArray jsonArray = object.getJSONArray("OrderList");
								for (int i = 0; i < jsonArray.size(); i++) {
									JSONObject jsonObject = (JSONObject) jsonArray.get(i);
									JSONObject obj = jsonObject.getJSONObject("Order");
									String state = obj.getString("DeliveryState");
									JSONArray shipArray = jsonObject.getJSONArray("ShipOrderList");
									for (int j = 0; j < shipArray.size(); j++) {
										JSONObject shipObject = (JSONObject) jsonArray.get(i);
										logisticsName = shipObject.getString("LogisticsName");
										logisticsNo = shipObject.getString("LogisticsNo");
									}
									if (OrdersConstants.OFCDeliveryState.ALREADY_DELIVER_GOODS.equals(state)
											|| OrdersConstants.OFCDeliveryState.ALREADY_RECEIVE_GOODS.equals(state)
											|| OrdersConstants.OFCDeliveryState.PART_DELIVER_GOODS.equals(state)) {
										OrdOdLogistics ordOdLogistics = ordOdLogisticsAtomSV
												.selectByOrd(ordOrder.getTenantId(), ordOrder.getOrderId());
										if (ordOdLogistics == null) {
											logger.error("配送信息不存在");
											throw new BusinessException(ExceptCodeConstants.Special.NO_RESULT,
													"配送信息不存在[订单id:" + ordOrder.getOrderId() + "]");
										}
										ordOrder.setState(OrdersConstants.OrdOrder.State.WAIT_CONFIRM);
										ordOdLogistics.setExpressOddNumber(logisticsNo);
										ordOdLogistics.setContactCompany(logisticsName);// 物流商
										ordOrderAtomSV.updateById(ordOrder);
										ordOdLogisticsAtomSV.updateByPrimaryKey(ordOdLogistics);
									}
								}
							}
							 
						}
						orderVo.setOrderId(ordOrder.getOrderId());
						orderVo.setState(ordOrder.getState());
						orderVo.setBusiCode(ordOrder.getBusiCode());
						SysParam sysParamState = InfoTranslateUtil.translateInfo(ordOrder.getTenantId(), "ORD_ORDER",
								"STATE", ordOrder.getState(), iCacheSV);
						orderVo.setStateName(sysParamState == null ? "" : sysParamState.getColumnDesc());
						List<BehindOrdProductVo> prodList = this.getProdList(ordOrder, orderListRequest,
								behindOrdOrderAttach, ordOrder.getOrderId());
						orderVo.setProdSize(prodList.size());
						totalProdSize = prodList.size() + totalProdSize;
						orderVo.setProductList(prodList);
						orderList.add(orderVo);
					}
					pOrderVo.setOrderList(orderList);
				}
				if (!(list.contains(states) || flag)) {
					OrdOdFeeProdCriteria exampleFeeProd = new OrdOdFeeProdCriteria();
					OrdOdFeeProdCriteria.Criteria criteriaFeeProd = exampleFeeProd.createCriteria();
					criteriaFeeProd.andOrderIdEqualTo(behindOrdOrderAttach.getOrderId()); // 父订单id
					List<OrdOdFeeProd> orderFeeProdList = ordOdFeeProdAtomSV.selectByExample(exampleFeeProd);
					long points = 0; // 积分
					if (!CollectionUtil.isEmpty(orderFeeProdList)) {
						for (OrdOdFeeProd ordOdFeeProd : orderFeeProdList) {
							if (OrdersConstants.OrdOdFeeProd.PayStyle.JF.equals(ordOdFeeProd.getPayStyle())) {
								points += ordOdFeeProd.getPaidFee();
							}
						}
					}
					pOrderVo.setPoints(points);
				}
				pOrderVo.setTotalProdSize(totalProdSize);
				orderVoList.add(pOrderVo);
			}
		}
		return orderVoList;
	}*/

	/**
	 * 订单下的商品明细信息
	 */
	private List<BehindOrdProductVo> getProdList(OrdOrder order, BehindQueryOrderListRequest orderListRequest,
			BehindOrdOrderAttach behindOrdOrderAttach, Long orderId) {
		List<BehindOrdProductVo> productList = new ArrayList<BehindOrdProductVo>();
		OrdOdProdCriteria example = new OrdOdProdCriteria();
		OrdOdProdCriteria.Criteria criteria = example.createCriteria();
		criteria.andTenantIdEqualTo(orderListRequest.getTenantId());
		if (order == null) {
			criteria.andOrderIdEqualTo(behindOrdOrderAttach.getOrderId());
		} else {
			criteria.andOrderIdEqualTo(orderId);
		}
		List<OrdOdProd> OrdOdProds = ordOdProdAtomSV.selectByExample(example);
		for (OrdOdProd ordOdProd : OrdOdProds) {
			BehindOrdProductVo vo = new BehindOrdProductVo();
			vo.setBuySum(ordOdProd.getBuySum());
			vo.setProdName(ordOdProd.getProdName());
			productList.add(vo);
		}
		return productList;
	}

	
	
	//订单判断及状态修改
	@Override
	public int updateOrder(OrdOrder request) throws BusinessException, SystemException {
		/* 获取售后订单 */
		OrdOrder afterOrdOrder = ordOrderAtomSV.selectByOrderId(request.getTenantId(), request.getOrderId());
		if (afterOrdOrder == null) {
			throw new BusinessException(ExceptCodeConstants.Special.NO_RESULT,
					"订单信息不存在[订单id:" + request.getOrderId() + "]");
		}
		// 设置售后订单状态
		afterOrdOrder.setState(request.getState());
		// 处理中 退款失败状态 不修改子父订单状态
		if (!(OrdersConstants.OrdOrder.State.IN_PROCESS.equals(request.getState())
				|| OrdersConstants.OrdOrder.State.REFUND_FAILD.equals(request.getState()))) {
			if (OrdersConstants.OrdOrder.BusiCode.UNSUBSCRIBE_ORDER.equals(afterOrdOrder.getBusiCode())) {
				if (OrdersConstants.OrdOrder.State.EXCHANGE_AUDIT.equals(request.getState())) {
					afterOrdOrder.setState(OrdersConstants.OrdOrder.State.FINISH_REFUND); // 退货完成
				}
			}
			/* 获取子订单信息及子订单下的商品明细信息 */
			OrdOrder order = ordOrderAtomSV.selectByOrderId(request.getTenantId(), afterOrdOrder.getOrigOrderId());
			List<OrdOdProd> prodList = ordOdProdAtomSV.selectByOrd(request.getTenantId(),
					afterOrdOrder.getOrigOrderId());
			boolean cusFlag = false;
			for (OrdOdProd ordOdProd : prodList) {
				if (OrdersConstants.OrdOrder.cusServiceFlag.YES.equals(ordOdProd.getCusServiceFlag())) {
					cusFlag = true;
				} else {
					cusFlag = false;
					break;
				}
			}
			/* 获取子订单下的所有售后订单 */
			OrdOrderCriteria example = new OrdOrderCriteria();
			OrdOrderCriteria.Criteria criteria = example.createCriteria();
			criteria.andOrigOrderIdEqualTo(afterOrdOrder.getOrigOrderId());
			criteria.andOrderIdNotEqualTo(request.getOrderId());
			List<OrdOrder> orderList = ordOrderAtomSV.selectByExample(example);
			OrdOrder parentOrder = ordOrderAtomSV.selectByOrderId(request.getTenantId(), order.getParentOrderId()); // 父订单
			if (cusFlag) {
				if (CollectionUtil.isEmpty(orderList)) {
					// 一个商品时.没有售后订单,商品售后标识Y
					// 1.无Y --无售后订单 商品Y标识
					order.setState(OrdersConstants.OrdOrder.State.COMPLETED);
					ordOrderAtomSV.updateById(order);
					//判断父订单下的其它子订单状态  
					// 完成则为 父订单完成,否则父订单不变
					boolean stateFlag = this.judgeState(order);
					if(stateFlag) {
						parentOrder.setState(OrdersConstants.OrdOrder.State.COMPLETED);
						ordOrderAtomSV.updateById(parentOrder); 
					}
				} else {
					// 2.有Y --有售后订单,商品标识Y
					// 判断售后订单为已完成状态或者审核失败则改变状态
					boolean flag=false;
					for (OrdOrder ordOrder : orderList) {
						String state = ordOrder.getState();
						if (OrdersConstants.OrdOrder.State.FINISH_REFUND.equals(state)
								|| OrdersConstants.OrdOrder.State.EXCHANGE_AUDIT.equals(state)
								|| OrdersConstants.OrdOrder.State.REFUND_AUDIT.equals(state)
								|| OrdersConstants.OrdOrder.State.AUDIT_FAILURE.equals(state)
								|| OrdersConstants.OrdOrder.State.AUDIT_AGAIN_FAILURE.equals(state)) {
							flag=true;
		    			}else {
		    				flag=false;
		    				break;
		    			}
					}
					if(flag) {
						order.setState(OrdersConstants.OrdOrder.State.COMPLETED);
						ordOrderAtomSV.updateById(order);
						//判断父订单下的其它子订单状态  
    					// 完成则为 父订单完成,否则父订单不变
    					boolean stateFlag = this.judgeState(order);
    					if(stateFlag) {
    						parentOrder.setState(OrdersConstants.OrdOrder.State.COMPLETED);
    						ordOrderAtomSV.updateById(parentOrder); 
    					}
					}
				}
			} else if (!cusFlag) {
				// 发货后状态
				if (!(OrdersConstants.OrdOrder.State.WAIT_DISTRIBUTION.equals(order.getState())
						|| OrdersConstants.OrdOrder.State.WAIT_DELIVERY.equals(order.getState())
						|| OrdersConstants.OrdOrder.State.WAIT_SEND.equals(order.getState()))) {
					// 1.无N --无售后订单,存在商品标识N
					// 发货后改变状态
					if (CollectionUtil.isEmpty(orderList)) {
						order.setState(OrdersConstants.OrdOrder.State.COMPLETED);
						ordOrderAtomSV.updateById(order);
						//判断父订单下的其它子订单状态  
    					// 完成则为 父订单完成,否则父订单不变
    					boolean stateFlag = this.judgeState(order);
    					if(stateFlag) {
    						parentOrder.setState(OrdersConstants.OrdOrder.State.COMPLETED);
    						ordOrderAtomSV.updateById(parentOrder); 
    					}
						// 4.有N --有售后订单 存在商品标识N
						// 发货后状态
						// 判断售后订单为已完成状态或者审核失败则 改变状态
					} else {
						for (OrdOrder ordOrder : orderList) {
							String state = ordOrder.getState();
							// 表示售后订单为已完成状态或者审核失败
							if (OrdersConstants.OrdOrder.State.FINISH_REFUND.equals(state)
									|| OrdersConstants.OrdOrder.State.EXCHANGE_AUDIT.equals(state)
									|| OrdersConstants.OrdOrder.State.REFUND_AUDIT.equals(state)
									|| OrdersConstants.OrdOrder.State.AUDIT_FAILURE.equals(state)
									|| OrdersConstants.OrdOrder.State.AUDIT_AGAIN_FAILURE.equals(state)) {
								order.setState(OrdersConstants.OrdOrder.State.COMPLETED);
								ordOrderAtomSV.updateById(order);
								//判断父订单下的其它子订单状态  
		    					// 完成则为 父订单完成,否则父订单不变
		    					boolean stateFlag = this.judgeState(order);
		    					if(stateFlag) {
		    						parentOrder.setState(OrdersConstants.OrdOrder.State.COMPLETED);
		    						ordOrderAtomSV.updateById(parentOrder); 
		    					}
							}
						}
					}
				}
			}
		}
		return ordOrderAtomSV.updateById(afterOrdOrder);
	}
	
	
	 /**
     * 判断父订单下面其它子订单状态
     */
    private boolean judgeState(OrdOrder order) {
    	//父订单下的其它子订单
        OrdOrderCriteria example = new OrdOrderCriteria();
        OrdOrderCriteria.Criteria criteria = example.createCriteria();
        criteria.andTenantIdEqualTo(order.getTenantId()).andOrderIdNotEqualTo(order.getOrderId());
        criteria.andParentOrderIdEqualTo(order.getParentOrderId());
        criteria.andBusiCodeEqualTo(OrdersConstants.OrdOrder.BusiCode.NORMAL_ORDER);
        List<OrdOrder> childOrders = ordOrderAtomSV.selectByExample(example);
	    if(!CollectionUtil.isEmpty(childOrders)) {
	    	for (OrdOrder ordOrder : childOrders) {
	    		//其它子订单状态不是'完成'
				if(!OrdersConstants.OrdOrder.State.COMPLETED.equals(ordOrder.getState())) {
					return false;
				}
			}
	    }
	    return true;
    }

	/**
	 * OFC订单查询
	 */
	public static JSONObject queryOFC(OrdOrder ordOrder) throws BusinessException, SystemException {
		List<String> orderNoList = new ArrayList<String>();
		orderNoList.add(String.valueOf(ordOrder.getOrderId()));
		Map<String, Object> mapField = new HashMap<String, Object>();
		mapField.put("OrderNoList", orderNoList);
		// mapField.put("ShopName", "长虹官方旗舰店");
		mapField.put("PageIndex", "1");
		mapField.put("PageSize", "1");
		String params = JSON.toJSONString(mapField);
		Map<String, String> header = new HashMap<String, String>();
		header.put("appkey", OrdersConstants.OFC_APPKEY);
		JSONObject object = null;
		// 发送Post请求,并返回信息
		try {
			String strData = HttpClientUtil.sendPost(OrdersConstants.OFC_QUERY_URL, params, header);
			object = ParseO2pDataUtil.getData(strData);
			// TODO 是否判断
			/*
			 * if(object==null) {
			 * 
			 * }else {}
			 */
			boolean val = object.getBooleanValue("IsValid");// 操作是否成功
			if (!val) {
				throw new BusinessException("", "OFC订单查询失败");
			}
		} catch (IOException | URISyntaxException e) {
			logger.error(e.getMessage());
			throw new SystemException("", "OFC订单查询出现异常");
		}
		return object;
	}
	
	// 搜索引擎数据公共查询条件
		public List<SearchCriteria> commonConditions(BehindQueryOrderListRequest request) {
			List<SearchCriteria> searchfieldVos = new ArrayList<SearchCriteria>();
			// 如果用户名称不为空
			if (!StringUtil.isBlank(request.getUserName())) {
				searchfieldVos.add(new SearchCriteria(SearchFieldConfConstants.USER_NAME, request.getUserName(),
						new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
			}
			// 如果订单id不为空
			if (request.getOrderId() != null) {
				searchfieldVos.add(new SearchCriteria(SearchFieldConfConstants.PORDER_ID, request.getOrderId().toString(),
						new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
			}
			// 如果渠道来源不为空
			if (!StringUtil.isBlank(request.getChlId())) {
				searchfieldVos.add(new SearchCriteria(SearchFieldConfConstants.CHL_ID, request.getChlId(),
						new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
			}
			// 如果是否需要物流不为空
			if (!StringUtil.isBlank(request.getDeliveryFlag())) {
				searchfieldVos.add(new SearchCriteria(SearchFieldConfConstants.DELIVERY_FLAG, request.getDeliveryFlag(),
						new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
			}
			// 如果仓库id不为空
			if (!StringUtil.isBlank(request.getRouteId())) {
				searchfieldVos.add(new SearchCriteria(SearchFieldConfConstants.ROUTE_ID, request.getRouteId(),
						new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
			}
			// 如果收货人联系电话不为空
			if (!StringUtil.isBlank(request.getContactTel())) {
				searchfieldVos.add(new SearchCriteria(SearchFieldConfConstants.CONTACT_TEL, request.getContactTel(),
						new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
			}


			/*// 如果lsp名称不为空
			if (!StringUtil.isBlank(request.getLspName())) {
				searchfieldVos.add(new SearchCriteria(SearchFieldConfConstants.LSP_NAME, request.getLspName(),
						new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.match,
								SearchOption.TermOperator.AND)));
			}*/

			// 如果状态变化开始、结束时间不为空
			if (!StringUtil.isBlank(request.getOrderTimeBegin())  && !StringUtil.isBlank(request.getOrderTimeEnd())) {
				/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZ");
				String start = sdf.format(request.getStateChgTimeStart());
				String end = sdf.format(request.getStateChgTimeEnd());*/
				SearchCriteria searchCriteria = new SearchCriteria();
				searchCriteria.setOption(new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.range));
				searchCriteria.setField(SearchFieldConfConstants.ORDER_TIME);
				searchCriteria.addFieldValue(request.getOrderTimeBegin());
				searchCriteria.addFieldValue(request.getOrderTimeEnd());
				searchfieldVos.add(searchCriteria);
			}
			// 下单开始时间不为空
			if(!StringUtil.isBlank(request.getOrderTimeBegin())  && StringUtil.isBlank(request.getOrderTimeEnd())) {
			//if (request.getOrderTimeStart() != null && request.getOrderTimeEnd() == null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZ");
				/*String start = sdf.format(request.getOrderTimeStart());*/
				String end = sdf.format(new Date());
				SearchCriteria searchCriteria = new SearchCriteria();
				searchCriteria.setOption(new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.range));
				searchCriteria.setField(SearchFieldConfConstants.ORDER_TIME);
				searchCriteria.addFieldValue(request.getOrderTimeBegin());
				searchCriteria.addFieldValue(end);
				searchfieldVos.add(searchCriteria);
			}
			// 下单结束时间不为空
			if(StringUtil.isBlank(request.getOrderTimeBegin())  && !StringUtil.isBlank(request.getOrderTimeEnd())){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZ");
				String end = sdf.format(request.getOrderTimeEnd());
				Timestamp sTime = Timestamp.valueOf(OrdersConstants.START_TIME);
				String start = sdf.format(sTime);
				SearchCriteria searchCriteria = new SearchCriteria();
				searchCriteria.setOption(new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.range));
				searchCriteria.setField(SearchFieldConfConstants.ORDER_TIME);
				searchCriteria.addFieldValue(start);
				searchCriteria.addFieldValue(end);
				searchfieldVos.add(searchCriteria);
			}
		
			// 状态集合不为空
			if (!CollectionUtil.isEmpty(request.getStateList())) {
				SearchCriteria searchCriteria = new SearchCriteria();
				SearchOption option = new SearchOption();
				option.setSearchLogic(SearchOption.SearchLogic.must);
				option.setSearchType(SearchOption.SearchType.term);
				searchCriteria.setFieldValue(request.getStateList());
				searchCriteria.setField(SearchFieldConfConstants.ORD_EXTENDES_STATE);
				searchCriteria.setOption(option);
				searchfieldVos.add(searchCriteria);
			}
			return searchfieldVos;
		}
}
