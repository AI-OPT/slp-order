package com.ai.slp.order.service.business.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.BeanUtils;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.util.StringUtil;
import com.ai.platform.common.api.cache.interfaces.ICacheSV;
import com.ai.platform.common.api.cache.param.SysParam;
import com.ai.platform.common.api.cache.param.SysParamSingleCond;
import com.ai.slp.order.api.stasticsorder.param.StasticOrderVo;
import com.ai.slp.order.api.stasticsorder.param.StasticParentOrderVo;
import com.ai.slp.order.api.stasticsorder.param.StasticsOrderRequest;
import com.ai.slp.order.api.stasticsorder.param.StasticsProdVo;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.dao.mapper.bo.OrdOdLogistics;
import com.ai.slp.order.dao.mapper.bo.OrdOdProd;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.service.atom.interfaces.IOrdOdLogisticsAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdProdAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.ai.slp.order.service.atom.interfaces.IStasticsOrderAtomSV;
import com.ai.slp.order.service.business.interfaces.IStasticsOrderBusiSV;
import com.ai.slp.order.util.ChUserUtil;
import com.alibaba.fastjson.JSONObject;
@Service
@Transactional
public class StasticsOrderBusiSVImpl implements IStasticsOrderBusiSV {
	@Autowired
    private IStasticsOrderAtomSV iStasticsOrderAtomSV;
    @Autowired
    private IOrdOdProdAtomSV iOrdOdProdAtomSV;
    @Autowired
    private IOrdOrderAtomSV iOrdOrderAtomSV;
    @Autowired
	IOrdOdLogisticsAtomSV iOrdOdLogisticsAtomSV;
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
					if(order!=null){
						if(!StringUtil.isBlank(order.getSubFlag())){
							if(OrdersConstants.OrdOrder.SubFlag.NO.equals(order.getSubFlag())){
								prodPOrderList.add(order);
							}
						}
					}
				}
			}
			//如果商品查询出来的订单部位空空，那么取父订单集合与商品订单集合的余
			commonOrderList = (List<OrdOrder>)CollectionUtils.intersection(parentOrderList, prodPOrderList);
			pageResult.setCount(commonOrderList.size());
		}else{
			commonOrderList = pageInfo.getResult();
			pageResult.setCount(pageInfo.getCount());
		}
		if(!CollectionUtil.isEmpty(commonOrderList)){
			for(OrdOrder order:commonOrderList){
				//返回的字订单
				List<StasticOrderVo> childOrderList =new ArrayList<StasticOrderVo>();
				StasticParentOrderVo parentOrderVo = new StasticParentOrderVo();
				List<StasticsProdVo> parentProdList = new ArrayList<StasticsProdVo>();
				BeanUtils.copyProperties(parentOrderVo, order);
				//获取父级订单的商品信息
				List<OrdOdProd>  parentProList = iOrdOdProdAtomSV.selectByOrd(order.getTenantId(), order.getOrderId());
				for(OrdOdProd prod:parentProList){
					StasticsProdVo staticProdVo = new StasticsProdVo();
					BeanUtils.copyProperties(staticProdVo, prod);
					parentProdList.add(staticProdVo);
				}
				//parentOrderVo.setProList(parentProdList);
				//获取收货人信息
				OrdOdLogistics logistics = iOrdOdLogisticsAtomSV.selectByOrd(order.getTenantId(), order.getOrderId());
				if(logistics!=null){
					parentOrderVo.setContactTel(logistics.getContactTel());
				}
				//获取绑定手机号
				JSONObject dataJson = ChUserUtil.getUserInfo(order.getUserId());
		        Object phone =dataJson.get("phone");
				parentOrderVo.setUserTel(phone==null?null:phone.toString());
				//获取子订单
				List<OrdOrder> childList = iOrdOrderAtomSV.selectChildOrder(parentOrderVo.getTenantId(),parentOrderVo.getOrderId());
				if(CollectionUtil.isEmpty(childList)){
					//将父级菜单信息存入子订单中方便前台展示
					List<StasticOrderVo> childsList = new ArrayList<StasticOrderVo>();
					StasticOrderVo childVo = new StasticOrderVo();
					//翻译订单状态
					ICacheSV iCacheSV = DubboConsumerFactory.getService(ICacheSV.class);
					SysParamSingleCond param = new SysParamSingleCond();
	        		param = new SysParamSingleCond();
	        		param.setTenantId(OrdersConstants.Sate.TENANT_ID);
	        		param.setColumnValue(order.getState());
	        		param.setTypeCode(OrdersConstants.Sate.TYPE_CODE);
	        		param.setParamCode(OrdersConstants.Sate.ORD_STATE);
	        		SysParam stateOrder = iCacheSV.getSysParamSingle(param);
	        		if(stateOrder!=null){
	        			childVo.setStateName(stateOrder.getColumnDesc());
	        		}
	        		childVo.setState(order.getState());
	        		childVo.setOrderId(order.getOrderId());
	        		//将父级订单号存入子订单中
	        		childVo.setParentOrderId(order.getOrderId());
	        		//将父商品信息存入子订单中
	        		childVo.setProList(parentProdList);
	        		childsList.add(childVo);
	        		parentOrderVo.setChildOrderList(childsList);
				}else{
					for(OrdOrder child:childList){
						List<StasticsProdVo> prodOrderList = new ArrayList<StasticsProdVo>();
						StasticOrderVo childOrderVo = new StasticOrderVo();
						BeanUtils.copyProperties(childOrderVo, child);
						//将父级订单号存入子订单中
						childOrderVo.setParentOrderId(order.getOrderId());
						//翻译订单状态
						ICacheSV iCacheSV = DubboConsumerFactory.getService(ICacheSV.class);
						SysParamSingleCond param = new SysParamSingleCond();
		        		param = new SysParamSingleCond();
		        		param.setTenantId(OrdersConstants.Sate.TENANT_ID);
		        		param.setColumnValue(child.getState());
		        		param.setTypeCode(OrdersConstants.Sate.TYPE_CODE);
		        		param.setParamCode(OrdersConstants.Sate.ORD_STATE);
		        		SysParam stateOrder = iCacheSV.getSysParamSingle(param);
		        		if(stateOrder!=null){
		        			childOrderVo.setStateName(stateOrder.getColumnDesc());
		        		}
						//获取子订单的商品信息
						List<OrdOdProd>  childProList = iOrdOdProdAtomSV.selectByOrd(child.getTenantId(), child.getOrderId());
						for(OrdOdProd prod:childProList){
							StasticsProdVo staticProdVo = new StasticsProdVo();
							BeanUtils.copyProperties(staticProdVo, prod);
							prodOrderList.add(staticProdVo);
						}
						childOrderVo.setProList(prodOrderList);
						childOrderList.add(childOrderVo);
					}
					parentOrderVo.setChildOrderList(childOrderList);
				}
				staticParentOrderList.add(parentOrderVo);
			}
		}
		pageResult.setPageSize(request.getPageSize());
		pageResult.setPageNo(request.getPageNo());
		pageResult.setResult(staticParentOrderList);
		return pageResult;
	}
}