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
import com.ai.platform.common.api.cache.interfaces.ICacheSV;
import com.ai.platform.common.api.cache.param.SysParam;
import com.ai.platform.common.api.cache.param.SysParamSingleCond;
import com.ai.slp.order.api.stasticsorder.param.StasticOrderVo;
import com.ai.slp.order.api.stasticsorder.param.StasticParentOrderVo;
import com.ai.slp.order.api.stasticsorder.param.StasticsOrderRequest;
import com.ai.slp.order.api.stasticsorder.param.StasticsProdVo;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.dao.mapper.attach.StasticOrdOrderAttach;
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
		List<StasticOrdOrderAttach> parentOrderList = iStasticsOrderAtomSV.getStasticOrd(request);
		List<StasticParentOrderVo> orderVoList = new ArrayList<StasticParentOrderVo>();
		if(!CollectionUtil.isEmpty(parentOrderList)){
			for(StasticOrdOrderAttach order:parentOrderList){
				//返回的子订单
				List<StasticOrderVo> childOrderList =new ArrayList<StasticOrderVo>();
				StasticParentOrderVo parentOrderVo = new StasticParentOrderVo();
				List<StasticsProdVo> parentProdList = new ArrayList<StasticsProdVo>();
				BeanUtils.copyProperties(parentOrderVo, order);
				//获取收货人信息
				OrdOdLogistics logistics = iOrdOdLogisticsAtomSV.selectByOrd(order.getTenantId(), order.getOrderId());
				if(logistics!=null){
					parentOrderVo.setContactTel(logistics.getContactTel());
				}
				//获取父订单商品
				List<OrdOdProd>  parentProList = iOrdOdProdAtomSV.selectByOrd(order.getTenantId(), order.getOrderId());
				for(OrdOdProd prod:parentProList){
					StasticsProdVo staticProdVo = new StasticsProdVo();
					BeanUtils.copyProperties(staticProdVo, prod);
					parentProdList.add(staticProdVo);
				}
				//获取绑定手机号
				JSONObject dataJson = ChUserUtil.getUserInfo(order.getUserId());
		        Object phone =dataJson.get("phone");
				parentOrderVo.setUserTel(phone==null?null:phone.toString());
				//获取用户名
        		Object userName =dataJson.get("userName");
        		parentOrderVo.setUserName(userName==null?null:userName.toString()); 
				//获取子订单
				List<OrdOrder> childList = iOrdOrderAtomSV.selectChildOrder(parentOrderVo.getTenantId(),parentOrderVo.getOrderId());
				int totalProdSize=0;
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
	        		//将父级订单号存入子订单中
	        		childVo.setParentOrderId(order.getOrderId());
	        		//将父商品信息存入子订单中
	        		childVo.setProList(parentProdList);
	        		childVo.setProdSize(parentProdList.size());
	        		totalProdSize=parentProdList.size()+totalProdSize;
	        		//总商品数量
					parentOrderVo.setProdTotal(totalProdSize);
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
						//子订单商品数量
						childOrderVo.setProdSize(prodOrderList.size());
						totalProdSize=prodOrderList.size()+totalProdSize;
						childOrderVo.setProList(prodOrderList);
						childOrderList.add(childOrderVo);
					}
					parentOrderVo.setChildOrderList(childOrderList);
					//总商品数量
					parentOrderVo.setProdTotal(totalProdSize);
				}
				orderVoList.add(parentOrderVo);
			}
		}
		int count=iStasticsOrderAtomSV.queryCount(request);
		pageResult.setPageSize(request.getPageSize());
		pageResult.setCount(count);
		pageResult.setPageNo(request.getPageNo());
		pageResult.setResult(orderVoList);
		return pageResult;
	}
}