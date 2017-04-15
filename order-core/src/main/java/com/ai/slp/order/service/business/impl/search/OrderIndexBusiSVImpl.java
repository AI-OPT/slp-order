package com.ai.slp.order.service.business.impl.search;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.sdk.components.ses.SESClientFactory;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.paas.ipaas.search.vo.Result;
import com.ai.paas.ipaas.search.vo.SearchCriteria;
import com.ai.platform.common.api.cache.interfaces.ICacheSV;
import com.ai.platform.common.api.cache.param.SysParam;
import com.ai.slp.order.api.sesdata.param.SesDataByPageRequest;
import com.ai.slp.order.api.sesdata.param.SesDataRequest;
import com.ai.slp.order.api.sesdata.param.SesDataResponse;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.constants.SearchConstants;
import com.ai.slp.order.dao.mapper.bo.OrdOdFeeProd;
import com.ai.slp.order.dao.mapper.bo.OrdOdFeeTotal;
import com.ai.slp.order.dao.mapper.bo.OrdOdLogistics;
import com.ai.slp.order.dao.mapper.bo.OrdOdProd;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.search.bo.OrdProdExtend;
import com.ai.slp.order.search.bo.OrderInfo;
import com.ai.slp.order.search.bo.ProdInfo;
import com.ai.slp.order.search.dto.SearchCriteriaStructure;
import com.ai.slp.order.service.atom.interfaces.IOrdOdFeeProdAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdFeeTotalAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdLogisticsAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdProdAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.ai.slp.order.service.business.interfaces.search.IOrderIndexBusiSV;
import com.ai.slp.order.service.business.interfaces.search.IOrderSearch;
import com.ai.slp.order.util.InfoTranslateUtil;


@Service
public class OrderIndexBusiSVImpl implements IOrderIndexBusiSV {
	
	private static Logger logger=LoggerFactory.getLogger(OrderIndexBusiSVImpl.class);

	@Autowired
	private IOrdOdProdAtomSV ordOdProdAtomSV;
	@Autowired
	private IOrdOdLogisticsAtomSV ordOdLogisticsAtomSV;
	@Autowired
	private IOrdOdFeeTotalAtomSV ordOdFeeTotalAtomSV;
	@Autowired
	private IOrdOrderAtomSV ordOrderAtomSV;
	@Autowired
	private IOrdOdFeeProdAtomSV ordOdFeeProdAtomSV;
	
	/**
	 * 刷新搜索引擎全部数据
	 */
	@Override
    @Transactional
	public boolean insertSesData(SesDataRequest request) throws BusinessException, SystemException {
		 try {	
			ICacheSV iCacheSV = DubboConsumerFactory.getService(ICacheSV.class);
		 	String tenantId = request.getTenantId();
		 	Long parentOrderId = request.getParentOrderId();
		 	//父订单信息
		 	OrdOrder ord = ordOrderAtomSV.selectByOrderId(tenantId,parentOrderId);
			List<OrderInfo> orderList = new ArrayList<OrderInfo>();
			if(ord!=null){
				OrderInfo ordInfo = new OrderInfo();
				ordInfo.setTenantid(tenantId);
				ordInfo.setChlid(ord.getChlId());
				//翻译渠道来源
				SysParam sysParamChlId = InfoTranslateUtil.translateInfo(tenantId,
						"ORD_ORDER", "CHL_ID", ord.getChlId(), iCacheSV);
				ordInfo.setChlidname(sysParamChlId == null ? "" : sysParamChlId.getColumnDesc());
				ordInfo.setPorderid(ord.getOrderId());
				ordInfo.setUsername(ord.getUserName());
				ordInfo.setUsertel(ord.getUserTel());
				ordInfo.setDeliveryflag(ord.getDeliveryFlag());
				ordInfo.setFlag(ord.getFlag());
				//翻译是否需要物流
				SysParam sysParamDf = InfoTranslateUtil.translateInfo(tenantId, "ORD_ORDER",
						"ORD_DELIVERY_FLAG", ord.getDeliveryFlag(), iCacheSV);
				ordInfo.setDeliveryflagname(sysParamDf == null ? "" : sysParamDf.getColumnDesc());
				ordInfo.setOrdertime(ord.getOrderTime());
				ordInfo.setParentorderstate(ord.getState());
				ordInfo.setSupplierid(ord.getSupplierId());
				// 获取手机号
				OrdOdLogistics ordOdLogistics = ordOdLogisticsAtomSV.selectByOrd(tenantId, parentOrderId);
				if(ordOdLogistics!=null) {
					ordInfo.setContacttel(ordOdLogistics.getContactTel());
				}
				// 获取积分
				List<OrdOdFeeProd> orderFeeProdList = ordOdFeeProdAtomSV.selectByOrderId(parentOrderId);
				long points = 0; 
				if (!CollectionUtil.isEmpty(orderFeeProdList)) {
					for (OrdOdFeeProd ordOdFeeProd : orderFeeProdList) {
						if (OrdersConstants.OrdOdFeeProd.PayStyle.JF.equals(ordOdFeeProd.getPayStyle())) {
							points += ordOdFeeProd.getPaidFee();
							break;
						}
					}
				}
				ordInfo.setPoints(points);
				// 查询费用信息
				OrdOdFeeTotal ordOdFeeTotal = ordOdFeeTotalAtomSV.selectByOrderId(tenantId, parentOrderId);
				if (ordOdFeeTotal != null) {
					ordInfo.setAdjustfee(ordOdFeeTotal.getAdjustFee());
					ordInfo.setDiscountfee(ordOdFeeTotal.getDiscountFee());
				}
				// 查询订单其它信息
				ordInfo = this.queryOrdProdExtends(ordInfo, ord, 
						 iCacheSV, parentOrderId);
				orderList.add(ordInfo);
				SESClientFactory.getSearchClient(SearchConstants.SearchNameSpace).bulkInsert(orderList);
			}
		 }catch(Exception e){
			 throw new SystemException("","订单信息加入搜索引擎失败,订单ID:"+request.getParentOrderId());
		 }
            return true;
	}

	private OrderInfo queryOrdProdExtends(OrderInfo ordInfo,OrdOrder ord,ICacheSV iCacheSV,
			Long parentOrderId) {
		List<OrdProdExtend> prodExtends=new ArrayList<OrdProdExtend>();
		//子订单
		List<OrdOrder> subOrders = ordOrderAtomSV.selectChildOrder(ord.getTenantId(), parentOrderId);
		int totalprodsize = 0;
		if(!CollectionUtil.isEmpty(subOrders)) {
			//存在子订单
			for (OrdOrder ordOrder : subOrders) {
				OrdProdExtend prodExtend=new OrdProdExtend();
				List<ProdInfo> prodInfos=new ArrayList<ProdInfo>();
				prodExtend.setState(ordOrder.getState());
				//订单状态翻译
				SysParam sysParamState = InfoTranslateUtil.translateInfo(ordOrder.getTenantId(),
						"ORD_ORDER", "STATE",ordOrder.getState(), iCacheSV);
				prodExtend.setStatename(sysParamState == null ? "" : sysParamState.getColumnDesc());
				prodExtend.setBusicode(ordOrder.getBusiCode());
				prodExtend.setParentorderid(parentOrderId);
				prodExtend.setOrderid(ordOrder.getOrderId());
				prodExtend.setRouteid(ordOrder.getRouteId());
				// 查询商品信息
				prodInfos = this.queryOrdProd(prodInfos,ord.getTenantId(),
						ordOrder.getOrderId());
				prodExtend.setProdsize(prodInfos.size());
				totalprodsize=prodInfos.size()+totalprodsize;
				prodExtend.setProdinfos(prodInfos);
				prodExtends.add(prodExtend);
			}
		}else {
			//不存在子订单
			List<ProdInfo> prodInfos=new ArrayList<ProdInfo>();
			OrdProdExtend prodExtend=new OrdProdExtend();
			prodExtend.setState(ord.getState());
			//订单状态翻译
			SysParam sysParamState = InfoTranslateUtil.translateInfo(ord.getTenantId(),
					"ORD_ORDER", "STATE",ord.getState(), iCacheSV);
			prodExtend.setStatename(sysParamState == null ? "" : sysParamState.getColumnDesc());
			prodExtend.setBusicode(ord.getBusiCode());//父订单
			prodExtend.setParentorderid(parentOrderId);
			// 查询商品信息
			prodInfos = this.queryOrdProd(prodInfos,ord.getTenantId(),
					parentOrderId);
			prodExtend.setProdsize(prodInfos.size());
			totalprodsize=prodInfos.size()+totalprodsize;
			prodExtend.setProdinfos(prodInfos);
			prodExtends.add(prodExtend);
		}
		ordInfo.setTotalprodsize(totalprodsize);
		ordInfo.setOrdextendes(prodExtends);
		return ordInfo;
	}

	private List<ProdInfo>  queryOrdProd(List<ProdInfo> prodInfos,String tenantId, 
			Long orderId) {
		// TODO Auto-generated method stub
		List<OrdOdProd> ordOdProds = ordOdProdAtomSV.selectByOrd(tenantId, orderId);
		if (!CollectionUtil.isEmpty(ordOdProds)) {
			for (OrdOdProd ordOdProd : ordOdProds) {
				ProdInfo prodInfo=new ProdInfo();
				prodInfo.setBuysum(ordOdProd.getBuySum());
				prodInfo.setProdname(ordOdProd.getProdName());
				prodInfos.add(prodInfo);
			}
		}
		return prodInfos;
	}

	
	/**
	 * 刷新搜索引擎状态数据
	 */
	@Override
	public void refreshStateData(OrdOrder ordOrder,OrdOrder pOrder) throws BusinessException, SystemException {
		ICacheSV iCacheSV = DubboConsumerFactory.getService(ICacheSV.class);
  		IOrderSearch orderSearch = new OrderSearchImpl();
		List<SearchCriteria> orderSearchCriteria = SearchCriteriaStructure.
				commonConditionsByOrderId(ordOrder.getParentOrderId());
		Result<OrderInfo> result = orderSearch.search(orderSearchCriteria, 0, 1, null);
		List<OrderInfo> ordList = result.getContents();
		if(CollectionUtil.isEmpty(ordList)) {
			throw new BusinessException("搜索引擎无数据! 父订单id为:"+ordOrder.getParentOrderId());
		}
		OrderInfo orderInfo = ordList.get(0);
		//更新父订单状态
		if(pOrder!=null) {
			orderInfo.setParentorderstate(pOrder.getState());
		}
		List<OrdProdExtend> ordextendes = orderInfo.getOrdextendes();
		for (OrdProdExtend ordProdExtend : ordextendes) {
			if(ordOrder.getOrderId()==ordProdExtend.getOrderid()) {
				ordProdExtend.setState(ordOrder.getState());
				//订单状态翻译
				SysParam sysParamState = InfoTranslateUtil.translateInfo(ordOrder.getTenantId(),
						"ORD_ORDER", "STATE",ordOrder.getState(), iCacheSV);
				ordProdExtend.setStatename(sysParamState == null ? "" : sysParamState.getColumnDesc());
			}
		}
		SESClientFactory.getSearchClient(SearchConstants.SearchNameSpace).bulkInsert(ordList);
	}

	
	@Override
	public SesDataResponse insertSesDataByPage(SesDataByPageRequest request) throws BusinessException, SystemException {
		long orderId=0;
		int startSize = 1;
		int maxSize = 1;
		// 最大条数设置
		int pageNo = request.getPageNo();
		int size = request.getPageSize();
		if (pageNo < 1) {
			startSize = 0;
		} else {
			startSize = (pageNo - 1) * size;
		}
		maxSize = size;
		SesDataResponse response=new SesDataResponse();
		ICacheSV iCacheSV = DubboConsumerFactory.getService(ICacheSV.class);
	 	String tenantId = request.getTenantId();
	 	//性能数据
	 	int queryCount = ordOrderAtomSV.countForSes();
	 	int failCount=0;
	 	int shareParentCount=0;
	 	List<OrdOrder> ordOrderDatas = ordOrderAtomSV.selectSesData(startSize,maxSize);
		List<OrderInfo> orderList = new ArrayList<OrderInfo>();
		if(!CollectionUtil.isEmpty(ordOrderDatas)){
		for (OrdOrder order: ordOrderDatas) {
			OrdOrder ord = ordOrderAtomSV.selectByOrderId(tenantId, order.getParentOrderId());
			if(ord==null) {
				failCount++;
				logger.error(">>>>>>>>>>不存在订单主表信息! 父订单id:"+ order.getParentOrderId());
				continue;
			}
			//父订单
			if(orderId==ord.getOrderId()) {
				shareParentCount++;
			}
			orderId = ord.getOrderId();
			OrderInfo ordInfo = new OrderInfo();
			ordInfo.setTenantid(tenantId);
			ordInfo.setChlid(ord.getChlId());
			//翻译渠道来源
			SysParam sysParamChlId = InfoTranslateUtil.translateInfo(tenantId,
					"ORD_ORDER", "CHL_ID", ord.getChlId(), iCacheSV);
			ordInfo.setChlidname(sysParamChlId == null ? "" : sysParamChlId.getColumnDesc());
			ordInfo.setPorderid(orderId);
			ordInfo.setUsername(ord.getUserName());
			ordInfo.setUsertel(ord.getUserTel());
			ordInfo.setDeliveryflag(ord.getDeliveryFlag());
			ordInfo.setFlag(ord.getFlag());
			//翻译是否需要物流
			SysParam sysParamDf = InfoTranslateUtil.translateInfo(tenantId, "ORD_ORDER",
					"ORD_DELIVERY_FLAG", ord.getDeliveryFlag(), iCacheSV);
			ordInfo.setDeliveryflagname(sysParamDf == null ? "" : sysParamDf.getColumnDesc());
			ordInfo.setOrdertime(ord.getOrderTime());
			ordInfo.setParentorderstate(ord.getState());
			ordInfo.setSupplierid(ord.getSupplierId());
			// 获取手机号
			OrdOdLogistics ordOdLogistics = ordOdLogisticsAtomSV.selectByOrd(tenantId, orderId);
			if(ordOdLogistics==null) {
				failCount++;
				logger.error(">>>>>>>>>>不存在订单运费信息! 父订单id:"+ orderId);
				continue;
			}
			ordInfo.setContacttel(ordOdLogistics.getContactTel());
			// 获取积分
			List<OrdOdFeeProd> orderFeeProdList = ordOdFeeProdAtomSV.selectByOrderId(orderId);
			if(CollectionUtil.isEmpty(orderFeeProdList)) {
				failCount++;
				logger.error(">>>>>>>>>>不存在订单费用明细信息! 父订单id:"+ orderId);
				continue;
			}
			//存在的情况下
			long points = 0; 
			for (OrdOdFeeProd ordOdFeeProd : orderFeeProdList) {
				if (OrdersConstants.OrdOdFeeProd.PayStyle.JF.equals(ordOdFeeProd.getPayStyle())) {
					points += ordOdFeeProd.getPaidFee();
					break;
				}
			}
			ordInfo.setPoints(points);
			// 查询费用信息
			OrdOdFeeTotal ordOdFeeTotal = ordOdFeeTotalAtomSV.selectByOrderId(tenantId, orderId);
			if (ordOdFeeTotal == null) {
				failCount++;
				logger.error(">>>>>>>>>>不存在订单费用主表信息! 父订单id:"+ orderId);
				continue;
			}
			//存在情况下
			ordInfo.setDiscountfee(ordOdFeeTotal.getDiscountFee());
			ordInfo.setAdjustfee(ordOdFeeTotal.getAdjustFee());
			// 查询订单其它信息
			ordInfo = this.queryOrdProdExtends(ordInfo, ord, 
					iCacheSV, orderId);
			orderList.add(ordInfo);
		}
		try{
			SESClientFactory.getSearchClient(SearchConstants.SearchNameSpace).bulkInsert(orderList);
		}catch(Exception e){
			throw new SystemException("","订单信息加入搜索引擎失败,订单ID:"+orderId);
		}
		}
		response.setFailCount(failCount);
		response.setQueryCount(queryCount);
		response.setShareParentCount(shareParentCount);
		return response;
	}
}
