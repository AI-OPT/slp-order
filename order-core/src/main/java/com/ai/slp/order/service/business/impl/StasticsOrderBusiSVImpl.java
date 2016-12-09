package com.ai.slp.order.service.business.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@Service
@Transactional
public class StasticsOrderBusiSVImpl implements IStasticsOrderBusiSV {
	
	private static final Logger LOG = LoggerFactory.getLogger(StasticsOrderBusiSVImpl.class);
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
		long dubboStart=System.currentTimeMillis();
    	LOG.error("====开始执行StasticsOrderBusiSVImpl中的getStasticOrdPage订单查询dubbo服务，当前时间戳："+dubboStart);
		//获取父订单信息
		PageInfo<StasticParentOrderVo> pageResult = new PageInfo<StasticParentOrderVo>();
		long queryStart=System.currentTimeMillis();
    	LOG.error("开始执行iStasticsOrderAtomSV.getStasticOrd的查询服务，当前时间戳："+queryStart);
		List<StasticOrdOrderAttach> parentOrderList = iStasticsOrderAtomSV.getStasticOrd(request);
		long queryEnd=System.currentTimeMillis();
    	LOG.error("完成执行iStasticsOrderAtomSV.getStasticOrd的查询服务，当前时间戳："+queryEnd+",用时:"+(queryEnd-queryStart)+"毫秒");
    	List<StasticParentOrderVo> orderVoList = new ArrayList<StasticParentOrderVo>();
		if(!CollectionUtil.isEmpty(parentOrderList)){
			for(StasticOrdOrderAttach order:parentOrderList){
				//返回的子订单
				List<StasticOrderVo> childOrderList =new ArrayList<StasticOrderVo>();
				StasticParentOrderVo parentOrderVo = new StasticParentOrderVo();
				List<StasticsProdVo> parentProdList = new ArrayList<StasticsProdVo>();
				BeanUtils.copyProperties(parentOrderVo, order);
				//获取收货人信息
				long logisticsStart=System.currentTimeMillis();
		    	LOG.error("开始执行iOrdOdLogisticsAtomSV.selectByOrd的查询服务，当前时间戳："+logisticsStart);
				OrdOdLogistics logistics = iOrdOdLogisticsAtomSV.selectByOrd(order.getTenantId(), order.getOrderId());
				long logisticsEnd=System.currentTimeMillis();
				LOG.error("完成执行iOrdOdLogisticsAtomSV.selectByOrd的查询服务，当前时间戳："+logisticsEnd+",用时:"+(logisticsEnd-logisticsStart)+"毫秒");
				
				if(logistics!=null){
					parentOrderVo.setContactTel(logistics.getContactTel());
				}
				//获取父订单商品
				
				long parentOrderListStart=System.currentTimeMillis();
		    	LOG.error("开始执行iOrdOdProdAtomSV.selectByOrd的查询服务，当前时间戳："+parentOrderListStart);
				List<OrdOdProd>  parentProList = iOrdOdProdAtomSV.selectByOrd(order.getTenantId(), order.getOrderId());
				long parentOrderListEnd=System.currentTimeMillis();
				LOG.error("完成执行iOrdOdProdAtomSV.selectByOrd的查询服务，当前时间戳："+parentOrderListEnd+",用时:"+(parentOrderListEnd-parentOrderListStart)+"毫秒");
				
				for(OrdOdProd prod:parentProList){
					StasticsProdVo staticProdVo = new StasticsProdVo();
					BeanUtils.copyProperties(staticProdVo, prod);
					parentProdList.add(staticProdVo);
				}
				/*long userStart=System.currentTimeMillis();
				LOG.info("开始执行ChUserUtil.getUserInfo的查询服务,通过O2p获取用户信息，当前时间戳："+userStart);
				//获取绑定手机号
				JSONObject dataJson = ChUserUtil.getUserInfo(order.getUserId());
				Object userName=null;
        		Object phone=null;
                if(dataJson!=null) {
                  	//获取用户名,绑定手机号
                  	userName =dataJson.get("userName");
                  	phone =dataJson.get("phone");
                 }
				parentOrderVo.setUserTel(phone==null?null:phone.toString());
        		parentOrderVo.setUserName(userName==null?null:userName.toString()); 
        		long userEnd=System.currentTimeMillis();
        		LOG.info("完成执行ChUserUtil.getUserInfo的查询服务,通过O2p获取用户信息，当前时间戳："+userEnd+
						",用时:"+(userEnd-userStart)+"毫秒");*/
				//获取子订单
        		long childListStart=System.currentTimeMillis();
		    	LOG.error("开始执行iOrdOrderAtomSV.selectChildOrder的查询服务，当前时间戳："+childListStart);
				List<OrdOrder> childList = iOrdOrderAtomSV.selectChildOrder(parentOrderVo.getTenantId(),parentOrderVo.getOrderId());
				long childListEnd=System.currentTimeMillis();
				LOG.error("完成执行iOrdOrderAtomSV.selectChildOrder的查询服务，当前时间戳："+childListEnd+",用时:"+(childListEnd-childListStart)+"毫秒");
				
				int totalProdSize=0;
				if(CollectionUtil.isEmpty(childList)){
					//将父级菜单信息存入子订单中方便前台展示
					List<StasticOrderVo> childsList = new ArrayList<StasticOrderVo>();
					StasticOrderVo childVo = new StasticOrderVo();
					long cacheStart=System.currentTimeMillis();
			    	LOG.info("开始执行iCacheSV.getSysParamSingle服务，获取公共中心缓存服务,当前时间戳："+cacheStart);
					//翻译订单状态
					ICacheSV iCacheSV = DubboConsumerFactory.getService(ICacheSV.class);
					SysParamSingleCond param = new SysParamSingleCond();
	        		param = new SysParamSingleCond();
	        		param.setTenantId(OrdersConstants.Sate.TENANT_ID);
	        		param.setColumnValue(order.getState());
	        		param.setTypeCode(OrdersConstants.Sate.TYPE_CODE);
	        		param.setParamCode(OrdersConstants.Sate.ORD_STATE);
	        		SysParam stateOrder = iCacheSV.getSysParamSingle(param);
	        		long cacheEnd=System.currentTimeMillis();
	            	LOG.info("完成iCacheSV.getSysParamSingle服务，获取公共中心缓存服务,当前时间戳："+cacheEnd+",用时:"+(cacheEnd-cacheStart)+"毫秒");
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
						long cacheStart=System.currentTimeMillis();
				    	LOG.error("开始执行后场dubbo中iCacheSV.getSysParamSingle的缓存dubbo服务，当前时间戳："+cacheStart);
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
		        		long cacheEnd=System.currentTimeMillis();
		            	LOG.error("开始执行后场dubbo中iCacheSV.getSysParamSingle的缓存dubbo服务，当前时间戳："+cacheEnd+",用时:"+(cacheEnd-cacheStart)+"毫秒");
						//获取子订单的商品信息
		            	long childProListStart=System.currentTimeMillis();
				    	LOG.error("开始执行iOrdOdProdAtomSV.selectByOrd的查询服务，当前时间戳："+childProListStart);
						List<OrdOdProd>  childProList = iOrdOdProdAtomSV.selectByOrd(child.getTenantId(), child.getOrderId());
						long childProListEnd=System.currentTimeMillis();
						LOG.error("完成执行iOrdOdProdAtomSV.selectByOrd的查询服务，当前时间戳："+childProListEnd+",用时:"+(childProListEnd-childProListStart)+"毫秒");
						
						
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
		long countStart=System.currentTimeMillis();
    	LOG.error("开始执行iStasticsOrderAtomSV.queryCount中的获取订单数量，当前时间戳："+countStart);
		int count=iStasticsOrderAtomSV.queryCount(request);
		long countEnd=System.currentTimeMillis();
    	LOG.error("完成执行iStasticsOrderAtomSV.queryCount中的获取订单数量，当前时间戳："+countEnd+",用时:"+(countEnd-countStart)+"毫秒");
		pageResult.setPageSize(request.getPageSize());
		pageResult.setCount(count);
		pageResult.setPageNo(request.getPageNo());
		pageResult.setResult(orderVoList);
		
		LOG.error("====完成执行StasticsOrderBusiSVImpl中的getStasticOrdPage订单查询dubbo服务，当前时间戳："+countEnd+",用时:"+(countEnd-dubboStart)+"毫秒");
		return pageResult;
	}
}