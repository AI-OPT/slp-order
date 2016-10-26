package com.ai.slp.order.service.business.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
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
import com.ai.slp.order.service.atom.interfaces.IOrdOdFeeProdAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdFeeTotalAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdLogisticsAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdProdAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdWarmAtomSV;
import com.ai.slp.order.service.business.interfaces.IOrdWarmBusiSV;
import com.ai.slp.order.util.ChUserUtil;
import com.ai.slp.product.api.product.interfaces.IProductServerSV;
import com.ai.slp.product.api.product.param.ProductSkuInfo;
import com.ai.slp.product.api.product.param.SkuInfoQuery;
import com.alibaba.fastjson.JSONObject;
@Service
@Transactional
public class OrdWarmBusiSVImpl implements IOrdWarmBusiSV {
	
	private static final Logger LOG = Logger.getLogger(OrdWarmBusiSVImpl.class);
	
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
	@Override
	public PageInfo<OrderWarmVo> selectWarmOrdPage(OrderWarmRequest request) {
		long start=System.currentTimeMillis();
		LOG.info("开始执行dubbo后场预警订单列表查询selectWarmOrdPage，当前时间戳："+start);
		PageInfo<OrderWarmVo> pageResult=new PageInfo<OrderWarmVo>();
		long dubboStart=System.currentTimeMillis();
		LOG.info("开始执行dubbo后场预警订单列表查询selectWarmOrdPage,获取预警订单列表信息，当前时间戳："+dubboStart);
        PageInfo<OrdOrder> pageInfo = iOrdWarmAtomSV.selectWarmOrdPage(request);
        long dubboEnd=System.currentTimeMillis();
		LOG.info("开始执行dubbo后场预警订单列表查询selectWarmOrdPage，获取预警订单列表信息,"
				+ "当前时间戳："+dubboEnd+",用时:"+(dubboEnd-dubboStart)+"毫秒");
        pageResult.setCount(pageInfo.getCount());
		pageResult.setPageSize(pageInfo.getPageSize());
		pageResult.setPageNo(pageInfo.getPageNo());
		List<OrderWarmVo> orderVoList=new ArrayList<OrderWarmVo>();
		if(pageInfo.getResult()!=null&&!CollectionUtil.isEmpty(pageInfo.getResult())){
			for(OrdOrder ord:pageInfo.getResult()){
				OrderWarmVo orderVo = new OrderWarmVo();
				List<ProductInfo> prodinfoList = new ArrayList<ProductInfo>();
				BeanUtils.copyProperties(orderVo, ord);
				long userStart=System.currentTimeMillis();
				LOG.info("开始执行dubbo后场预警订单列表查询selectWarmOrdPage,通过O2p获取用户信息，当前时间戳："+userStart);
				//获取绑定手机号
				JSONObject dataJson = ChUserUtil.getUserInfo(ord.getUserId());
		        Object phone =dataJson.get("phone");
		        orderVo.setUserTel(phone==null?null:phone.toString());
				//获取用户名
        		Object userName =dataJson.get("userName");
        		orderVo.setUserName(userName==null?null:userName.toString()); 
        		long userEnd=System.currentTimeMillis();
        		LOG.info("开始执行dubbo后场预警订单列表查询selectWarmOrdPage,通过O2p获取用户信息，当前时间戳："+userEnd+
        				",用时:"+(userEnd-userStart)+"毫秒");
				//获取商品信息
				if(orderVo.getOrderId()!=null){
					List<OrdOdProd>  proList = iOrdOdProdAtomSV.selectByOrd(ord.getTenantId(), ord.getOrderId());
					if(!CollectionUtil.isEmpty(proList)){
						for(OrdOdProd prod:proList){
							ProductInfo prodVo = new ProductInfo();
							BeanUtils.copyProperties(prodVo, prod);
							prodinfoList.add(prodVo);
						}
					}
					//获取收货人信息
					OrdOdLogistics logistics = iOrdOdLogisticsAtomSV.selectByOrd(orderVo.getTenantId(), orderVo.getOrderId());
					if(logistics!=null){
						orderVo.setContactTel(logistics.getContactTel());
						orderVo.setAddress(logistics.getAddress());
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
				orderWarmVo.setLogisticsType(logistics.getLogisticsType());
				orderWarmVo.setContactName(logistics.getContactName());
			}
			//获取绑定手机号
			JSONObject dataJson = ChUserUtil.getUserInfo(orderWarmVo.getUserId());
	        Object phone =dataJson.get("phone");
	        orderWarmVo.setUserTel(phone==null?null:phone.toString());
			//获取用户名
    		Object userName =dataJson.get("userName");
    		orderWarmVo.setUserName(userName==null?null:userName.toString());
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
