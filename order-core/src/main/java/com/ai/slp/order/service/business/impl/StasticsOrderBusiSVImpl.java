package com.ai.slp.order.service.business.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.sdk.util.BeanUtils;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.util.StringUtil;
import com.ai.slp.order.api.stasticsorder.param.StasticOrderVo;
import com.ai.slp.order.api.stasticsorder.param.StasticParentOrderVo;
import com.ai.slp.order.api.stasticsorder.param.StasticsOrderRequest;
import com.ai.slp.order.api.stasticsorder.param.StasticsProdVo;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.dao.mapper.bo.OrdOdProd;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.service.atom.interfaces.IOrdOdProdAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.ai.slp.order.service.atom.interfaces.IStasticsOrderAtomSV;
import com.ai.slp.order.service.business.interfaces.IStasticsOrderBusiSV;
@Service
@Transactional
public class StasticsOrderBusiSVImpl implements IStasticsOrderBusiSV {
	@Autowired
    private IStasticsOrderAtomSV iStasticsOrderAtomSV;
    @Autowired
    private IOrdOdProdAtomSV iOrdOdProdAtomSV;
    @Autowired
    private IOrdOrderAtomSV iOrdOrderAtomSV;
	@Override
	public PageInfo<StasticParentOrderVo> getStasticOrdPage(StasticsOrderRequest request) {
		//获取父订单信息
		PageInfo<StasticParentOrderVo> pageResult = new PageInfo<StasticParentOrderVo>();
		PageInfo<OrdOrder> pageInfo = iStasticsOrderAtomSV.getStasticOrdPage(request);
		List<OrdOrder> parentOrderList = pageInfo.getResult();
		List<OrdOrder> prodPOrderList = new ArrayList<OrdOrder>();
		//公共的父级订单
		List<OrdOrder> commonOrderList = new ArrayList<OrdOrder>();
		//返回的订单
		List<StasticParentOrderVo> staticParentOrderList = new ArrayList<StasticParentOrderVo>();
		if(!StringUtil.isBlank(request.getProdName())){
			//如果商品名称不为空，根据名称获取订单集合
			List<OrdOdProd> parentProList =iOrdOdProdAtomSV.selectByProdName(request.getTenantId(),request.getProdName()); 
			if(!CollectionUtil.isEmpty(parentProList)){
				for(OrdOdProd prod: parentProList){
					OrdOrder order = iOrdOrderAtomSV.selectByOrderId(request.getTenantId(), prod.getOrderId());
					if(!StringUtil.isBlank(order.getSubFlag())){
						if(OrdersConstants.OrdOrder.SubFlag.NO.equals(order.getSubFlag())){
							prodPOrderList.add(order);
						}
					}
					
				}
			}
			//如果商品查询出来的订单部位空空，那么取父订单集合与商品订单集合的余
			commonOrderList = (List<OrdOrder>)CollectionUtils.intersection(parentOrderList, prodPOrderList);
		}else{
			commonOrderList = pageInfo.getResult();
		}
		if(!CollectionUtil.isEmpty(commonOrderList)){
			for(OrdOrder order:commonOrderList){
				//返回的字订单
				List<StasticOrderVo> childOrderList =new ArrayList<StasticOrderVo>();
				StasticParentOrderVo parentOrderVo = new StasticParentOrderVo();
				BeanUtils.copyProperties(parentOrderVo, order);
				//获取子订单
				List<OrdOrder> childList = iOrdOrderAtomSV.selectChildOrder(parentOrderVo.getTenantId(),parentOrderVo.getOrderId());
				for(OrdOrder child:childList){
					List<StasticsProdVo> prodOrderList = new ArrayList<StasticsProdVo>();
					StasticOrderVo childOrderVo = new StasticOrderVo();
					BeanUtils.copyProperties(childOrderVo, child);
					//获取子订单的商品信息
					List<OrdOdProd>  childProList = iOrdOdProdAtomSV.selectByOrd(child.getTenantId(), child.getOrderId());
					for(OrdOdProd prod:childProList){
						StasticsProdVo staticProdVo = new StasticsProdVo();
						BeanUtils.copyProperties(staticProdVo, prod);
						prodOrderList.add(staticProdVo);
					}
					childOrderList.add(childOrderVo);
				}
				parentOrderVo.setChildOrderList(childOrderList);
				staticParentOrderList.add(parentOrderVo);
			}
			pageResult.setCount(commonOrderList.size());
		}
		pageResult.setPageSize(request.getPageSize());
		pageResult.setPageNo(request.getPageNo());
		pageResult.setResult(staticParentOrderList);
		return pageResult;
	}
}
