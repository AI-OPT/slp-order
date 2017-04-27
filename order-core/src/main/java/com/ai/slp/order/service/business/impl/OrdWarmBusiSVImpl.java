package com.ai.slp.order.service.business.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.BeanUtils;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.paas.ipaas.search.vo.Result;
import com.ai.paas.ipaas.search.vo.SearchCriteria;
import com.ai.paas.ipaas.search.vo.Sort;
import com.ai.paas.ipaas.search.vo.Sort.SortOrder;
import com.ai.slp.order.api.warmorder.param.OrdProductVo;
import com.ai.slp.order.api.warmorder.param.OrderWarmListVo;
import com.ai.slp.order.api.warmorder.param.OrderWarmRequest;
import com.ai.slp.order.api.warmorder.param.OrderWarmVo;
import com.ai.slp.order.api.warmorder.param.ProductImage;
import com.ai.slp.order.api.warmorder.param.ProductInfo;
import com.ai.slp.order.api.warmorder.param.ProductListInfo;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.constants.SearchFieldConfConstants;
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
import com.ai.slp.order.service.atom.interfaces.IOrdWarmAtomSV;
import com.ai.slp.order.service.business.impl.search.OrderSearchImpl;
import com.ai.slp.order.service.business.interfaces.IOrdWarmBusiSV;
import com.ai.slp.order.service.business.interfaces.search.IOrderSearch;
import com.ai.slp.product.api.product.interfaces.IProductServerSV;
import com.ai.slp.product.api.product.param.ProductSkuInfo;
import com.ai.slp.product.api.product.param.SkuInfoQuery;
@Service
@Transactional
public class OrdWarmBusiSVImpl implements IOrdWarmBusiSV {
	
	@Autowired
    private IOrdWarmAtomSV iOrdWarmAtomSV;
	@Autowired
	IOrdOdProdAtomSV iOrdOdProdAtomSV;
	@Autowired
	IOrdOdFeeTotalAtomSV iOrdOdFeeTotalAtomSV;
	@Autowired
	IOrdOdLogisticsAtomSV iOrdOdLogisticsAtomSV;
	@Autowired
	IOrdOdFeeProdAtomSV iOrdOdFeeProdAtomSV;
	
	//预警订单列表
	@Override
	public PageInfo<OrderWarmListVo> selectWarmOrdPage(OrderWarmRequest request) {
		
		// 调用搜索引擎进行查询
		int startSize = 1;
		int maxSize = 1;
		// 最大条数设置
		int pageNo = request.getPageNo();
		int size = request.getPageSize();
		if (pageNo == 1) {
			startSize = 0;
		} else {
			startSize = (pageNo - 1) * size;
		}
		maxSize = size;
		PageInfo<OrderWarmListVo> pageInfo=new PageInfo<OrderWarmListVo>();
		List<OrderWarmListVo> results = new ArrayList<OrderWarmListVo>();
		IOrderSearch orderSearch = new OrderSearchImpl();
		List<SearchCriteria> orderSearchCriteria = SearchCriteriaStructure.commonConditionsByOrderTime(request);
		//排序
		List<Sort> sortList = new ArrayList<Sort>();
		Sort sort = new Sort(SearchFieldConfConstants.ORDER_TIME, SortOrder.DESC);
		sortList.add(sort);
		Result<OrderInfo> result = orderSearch.search(orderSearchCriteria, startSize, maxSize, sortList);
		List<OrderInfo> ordList = result.getContents();
		
		for (OrderInfo orderInfo : ordList) {
			OrderWarmListVo vo=new OrderWarmListVo();
			BeanUtils.copyProperties(vo, orderInfo);
			
			List<OrdProdExtend> ordextendes = orderInfo.getOrdextendes();
			List<ProductListInfo> destOrdextendes=new ArrayList<ProductListInfo>();
			for (OrdProdExtend ordProdExtend : ordextendes) {
				ProductListInfo destOrdOrderVo=new ProductListInfo();
				BeanUtils.copyProperties(destOrdOrderVo, ordProdExtend);
				
				List<ProdInfo> prodinfos = ordProdExtend.getProdinfos();
				List<OrdProductVo> destOrdProductVos=new ArrayList<OrdProductVo>();
				for (ProdInfo prodInfo : prodinfos) {
					OrdProductVo destProdVo=new OrdProductVo();
					BeanUtils.copyProperties(destProdVo, prodInfo);
					destOrdProductVos.add(destProdVo);
				}
				destOrdOrderVo.setProdinfos(destOrdProductVos);
				destOrdextendes.add(destOrdOrderVo);
			}
			vo.setOrdextendes(destOrdextendes);
			vo.setTenantId(OrdersConstants.TENANT_ID);
			results.add(vo);
		}
		pageInfo.setPageNo(pageNo);
		pageInfo.setPageSize(maxSize);
		pageInfo.setResult(results);
		pageInfo.setCount(Long.valueOf(result.getCount()).intValue());
		return pageInfo;
		
		
	}
	
	//预警订单详情查看
	@Override
	public OrderWarmVo selectWarmOrdDetail(String tenantId, long orderId) {
		OrderWarmVo orderWarmVo = new OrderWarmVo();
		List<ProductInfo> prodinfoList = new ArrayList<ProductInfo>();
		OrdOrder orderInfo = iOrdWarmAtomSV.selectWarmOrde(tenantId, orderId);
		if(orderInfo!=null){
			BeanUtils.copyProperties(orderWarmVo, orderInfo);
			List<OrdOdProd>  proList = iOrdOdProdAtomSV.selectByOrd(tenantId, orderId);
			if(!CollectionUtil.isEmpty(proList)){
				for(OrdOdProd prod:proList){
					ProductInfo prodVo = new ProductInfo();
					BeanUtils.copyProperties(prodVo, prod);
					//获取图片信息
					 ProductImage productImage = this.getProductImage(tenantId, prod.getSkuId());
					 prodVo.setProductImage(productImage);
					 prodinfoList.add(prodVo);
				}
			}
				//获取费用信息
			OrdOdFeeTotal fee = iOrdOdFeeTotalAtomSV.selectByOrderId(tenantId, orderId);
			if(fee!=null){
				orderWarmVo.setDiscountFee(fee.getDiscountFee());
				orderWarmVo.setPaidFee(fee.getPaidFee());
			}
			//获取收货人信息
			OrdOdLogistics logistics = iOrdOdLogisticsAtomSV.selectByOrd(tenantId, orderId);
			if(logistics!=null){
				orderWarmVo.setContactTel(logistics.getContactTel());
				orderWarmVo.setAddress(logistics.getAddress());
				orderWarmVo.setLogisticsType(logistics.getLogisticsType());
				orderWarmVo.setContactName(logistics.getContactName());
			}
			if(!CollectionUtil.isEmpty(prodinfoList)){
				orderWarmVo.setProdInfo(prodinfoList);
			}
		}
		return orderWarmVo;
	}
	
	/**
	 * 获取图片信息
	 */
	private ProductImage getProductImage(String tenantId, String skuId) {
        ProductImage productImage = new ProductImage();
        SkuInfoQuery skuInfoQuery = new SkuInfoQuery();
        skuInfoQuery.setTenantId(tenantId);
        skuInfoQuery.setSkuId(skuId);
        IProductServerSV iProductServerSV = DubboConsumerFactory.getService(IProductServerSV.class);
        ProductSkuInfo productSkuInfo = iProductServerSV.queryProductSkuById4ShopCart(skuInfoQuery);
        productImage.setVfsId(productSkuInfo.getVfsId());
        productImage.setPicType(productSkuInfo.getPicType());
        return productImage;
    }
}
