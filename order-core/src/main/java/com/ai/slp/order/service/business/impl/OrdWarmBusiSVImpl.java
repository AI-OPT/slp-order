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
import com.ai.slp.order.api.warmorder.param.OrderWarmRequest;
import com.ai.slp.order.api.warmorder.param.OrderWarmVo;
import com.ai.slp.order.api.warmorder.param.ProductImage;
import com.ai.slp.order.api.warmorder.param.ProductInfo;
import com.ai.slp.order.dao.mapper.bo.OrdOdFeeTotal;
import com.ai.slp.order.dao.mapper.bo.OrdOdLogistics;
import com.ai.slp.order.dao.mapper.bo.OrdOdProd;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.service.atom.interfaces.IOrdOdFeeTotalAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdLogisticsAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdProdAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdWarmAtomSV;
import com.ai.slp.order.service.business.interfaces.IOrdWarmBusiSV;
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
	@Override
	public PageInfo<OrderWarmVo> selectWarmOrdPage(OrderWarmRequest request) {
		PageInfo<OrderWarmVo> pageResult=new PageInfo<OrderWarmVo>();
        PageInfo<OrdOrder> pageInfo = iOrdWarmAtomSV.selectWarmOrdPage(request);
        List<ProductInfo> prodinfoList = new ArrayList<ProductInfo>();
        pageResult.setCount(pageInfo.getCount());
		pageResult.setPageSize(pageInfo.getPageSize());
		pageResult.setPageNo(pageInfo.getPageNo());
		List<OrderWarmVo> orderVoList=new ArrayList<OrderWarmVo>();
		if(pageInfo.getResult()!=null&&!CollectionUtil.isEmpty(pageInfo.getResult())){
			for(OrdOrder ord:pageInfo.getResult()){
				OrderWarmVo orderVo = new OrderWarmVo();
				BeanUtils.copyProperties(orderVo, ord);
				//获取商品信息
				if(orderVo.getOrderId()!=null){
					List<OrdOdProd>  proList = iOrdOdProdAtomSV.selectByOrd(ord.getTenantId(), ord.getOrderId());
					if(!CollectionUtil.isEmpty(proList)){
						for(OrdOdProd prod:proList){
							ProductInfo prodVo = new ProductInfo();
							BeanUtils.copyProperties(prodVo, prod);
							//获取图片信息
							ProductImage productImage = this.getProductImage(prod.getTenantId(), prod.getSkuId());
							prodVo.setProductImage(productImage);
							prodinfoList.add(prodVo);
						}
					}
					//获取费用信息
					OrdOdFeeTotal fee = iOrdOdFeeTotalAtomSV.selectByOrderId(orderVo.getTenantId(), orderVo.getOrderId());
					if(fee!=null){
						orderVo.setDiscountFee(fee.getDiscountFee());
						orderVo.setPaidFee(fee.getPaidFee());
					}
					//获取收货人信息
					OrdOdLogistics logistics = iOrdOdLogisticsAtomSV.selectByOrd(orderVo.getTenantId(), orderVo.getOrderId());
					if(logistics!=null){
						orderVo.setContactTel(logistics.getContactTel());
						orderVo.setLogisticsType(logistics.getLogisticsType());
						orderVo.setContactName(logistics.getContactName());
					}
				}
				if(!CollectionUtil.isEmpty(prodinfoList)){
					orderVo.setProdInfo(prodinfoList);
				}
				orderVoList.add(orderVo);
			}
			pageResult.setResult(orderVoList);
		}
		return pageResult;
	}
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
			}
			if(!CollectionUtil.isEmpty(prodinfoList)){
				orderWarmVo.setProdInfo(prodinfoList);
			}
		}
		
		return orderWarmVo;
	}
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
}
