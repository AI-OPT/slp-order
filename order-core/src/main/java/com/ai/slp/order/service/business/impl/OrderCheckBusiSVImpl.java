package com.ai.slp.order.service.business.impl;


import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.util.DateUtil;
import com.ai.paas.ipaas.util.StringUtil;
import com.ai.slp.order.api.aftersaleorder.impl.OrderAfterSaleSVImpl;
import com.ai.slp.order.api.ordercheck.param.OrderCheckRequest;
import com.ai.slp.order.api.sesdata.param.SesDataRequest;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.constants.OrdersConstants.OrdOdStateChg;
import com.ai.slp.order.dao.mapper.bo.OrdOdProd;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.service.atom.interfaces.IOrdOdProdAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.ai.slp.order.service.business.interfaces.IOrderCheckBusiSV;
import com.ai.slp.order.service.business.interfaces.IOrderFrameCoreSV;
import com.ai.slp.order.service.business.interfaces.search.IOrderIndexBusiSV;
import com.ai.slp.product.api.storageserver.interfaces.IStorageNumSV;
import com.ai.slp.product.api.storageserver.param.StorageNumBackReq;
import com.ai.slp.product.api.storageserver.param.StorageNumUserReq;
import com.alibaba.fastjson.JSON;

@Service
@Transactional
public class OrderCheckBusiSVImpl implements IOrderCheckBusiSV {
	
	private static final Logger logger=LoggerFactory.getLogger(OrderAfterSaleSVImpl.class);
	
	@Autowired
	private IOrdOrderAtomSV ordOrderAtomSV;
	@Autowired
	private IOrderFrameCoreSV orderFrameCoreSV;
	@Autowired
	private IOrdOdProdAtomSV ordOdProdAtomSV;
	@Autowired
	private IOrderIndexBusiSV orderIndexBusiSV;
	
	//订单审核
	@Override
	public void check(OrderCheckRequest request) throws BusinessException, SystemException {
		OrdOrder ordOrder = ordOrderAtomSV.selectByOrderId(request.getTenantId(), request.getOrderId());
		if(ordOrder==null) {
			logger.error("未能查询到相对应的订单信息[订单id:"+request.getOrderId()+"租户id:"+request.getTenantId()+"]");
			throw new BusinessException(ExceptCodeConstants.Special.NO_RESULT, 
					"未能查询到相对应的订单信息[订单id:"+request.getOrderId()+"租户id:"+request.getTenantId()+"]");
		}
		OrdOrder subOrdOrder = ordOrderAtomSV.selectByOrderId(ordOrder.getTenantId(), ordOrder.getOrigOrderId());
		if(subOrdOrder==null) {
			logger.error("未能查询到相对应的子订单信息[订单id:"+ordOrder.getOrigOrderId()+"租户id:"+ordOrder.getTenantId()+"]");
			throw new BusinessException(ExceptCodeConstants.Special.NO_RESULT, 
					"未能查询到相对应的子订单信息[订单id:"+ordOrder.getOrigOrderId()+"租户id:"+ordOrder.getTenantId()+"]");
		}
		/* 审核结果STATE检验*/
		String state = request.getState();
		String subState = subOrdOrder.getState();//子订单状态
		if(!OrdersConstants.OrdOrder.State.REVOKE_FINISH_AUDITED.equals(state)
				&&!OrdersConstants.OrdOrder.State.AUDIT_FAILURE.equals(state)) {
			throw new BusinessException("", "订单审核结果入参有误");
		}
		String orgState = ordOrder.getState();
		if(!OrdersConstants.OrdOrder.State.REVOKE_WAIT_AUDIT.equals(orgState)) {
			throw new BusinessException("", "此订单不处于待审核状态");
		}
		if(OrdersConstants.OrdOrder.State.REVOKE_FINISH_AUDITED.equals(state)) {//表示审核通过
			String transitionState=OrdersConstants.OrdOrder.State.REVOKE_FINISH_AUDITED; //订单轨迹记录状态
			String transitionChgDesc=OrdOdStateChg.ChgDesc.ORDER_AUDITED;
			String newState=null;
			String chgDesc=null;
			if(OrdersConstants.OrdOrder.State.WAIT_DISTRIBUTION.equals(subState)||
					OrdersConstants.OrdOrder.State.WAIT_DELIVERY.equals(subState)||
					OrdersConstants.OrdOrder.State.WAIT_SEND.equals(subState)) {
				newState=OrdersConstants.OrdOrder.State.WAIT_REPAY;
				chgDesc=OrdOdStateChg.ChgDesc.ORDER_SELLER_CONFIRMED_WAIT_PAY;
				// 减少商品销售量
				List<OrdOdProd> prodList = ordOdProdAtomSV.selectByOrd(ordOrder.getTenantId(), ordOrder.getOrderId());
				if(CollectionUtil.isEmpty(prodList)) {
					throw new BusinessException(ExceptCodeConstants.Special.NO_RESULT, 
							"未能查询到相关商品信息[订单id:"+ordOrder.getOrderId()+"]");
				}
				IStorageNumSV iStorageNumSV = DubboConsumerFactory.getService(IStorageNumSV.class);
				for (OrdOdProd prod : prodList) {
					StorageNumUserReq storageReq = new StorageNumUserReq();
					storageReq.setSkuId(prod.getSkuId());
					storageReq.setSkuNum(new Long(prod.getBuySum()).intValue());
					storageReq.setTenantId(prod.getTenantId());
					BaseResponse baseResponse = iStorageNumSV.backSaleNumOfProduct(storageReq);
					//增加库存量
					StorageNumBackReq backReq = new StorageNumBackReq();
					backReq.setSkuId(prod.getSkuId());
					 Map<String, Integer> storageNum = JSON.parseObject(prod.getSkuStorageId(),
			                    new com.alibaba.fastjson.TypeReference<Map<String, Integer>>(){});
					backReq.setTenantId(prod.getTenantId());
					backReq.setStorageNum(storageNum);
					BaseResponse backResponse = iStorageNumSV.backStorageNum(backReq);
					if (baseResponse.getResponseHeader().getIsSuccess() == false) {
						throw new BusinessException("", "减少商品销量失败");
					}
					if (backResponse.getResponseHeader().getIsSuccess() == false) {
						throw new BusinessException("", "增加库存失败");
					}
				}
			}else {
				newState=OrdersConstants.OrdOrder.State.REVOKE_WAIT_CONFIRM;
				chgDesc=OrdOdStateChg.ChgDesc.ORDER_BUYERS_TO_RETURN;
			}
			ordOrder.setState(newState);
	        ordOrder.setStateChgTime(DateUtil.getSysDate());
	        ordOrder.setOperId(request.getOperId());//审核工号
	        ordOrderAtomSV.updateById(ordOrder);
	        // 写入订单状态变化轨迹表
			this.updateOrderState(ordOrder, orgState,transitionState, transitionChgDesc, request);
			this.updateOrderState(ordOrder, transitionState,newState, chgDesc, request);
		}else {
			//审核拒绝 
			String remark = request.getRemark();
			if(StringUtil.isBlank(remark)) {
				throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "审核理由不能为空");
			}
			if(remark.length()>200) {
				throw new BusinessException("", "审核理由不能超过200字");
			}
			//改变原始订单的商品售后标识状态
			this.updateProdCusServiceFlag(ordOrder);
			String newState=OrdersConstants.OrdOrder.State.AUDIT_FAILURE;
			String chgDesc=OrdOdStateChg.ChgDesc.ORDER_AUDIT_NOT_PASS;
			ordOrder.setState(newState);
	        ordOrder.setStateChgTime(DateUtil.getSysDate());
	        ordOrder.setOrderDesc(remark); //拒绝理由
	        ordOrder.setOperId(request.getOperId());
	        ordOrderAtomSV.updateById(ordOrder);
			this.updateOrderState(ordOrder, orgState,newState, chgDesc, request);
		}
		// 刷新搜索引擎数据
    	SesDataRequest sesReq=new SesDataRequest();
    	sesReq.setTenantId(request.getTenantId());
    	sesReq.setParentOrderId(ordOrder.getParentOrderId());
    	this.orderIndexBusiSV.insertSesData(sesReq);
	}
	
	/**
     * 更新订单状态
     */
    private void updateOrderState(OrdOrder ordOrder,String 
    		orgState,String newState,String chgDesc,OrderCheckRequest request) {
        orderFrameCoreSV.ordOdStateChg(ordOrder.getOrderId(), ordOrder.getTenantId(), orgState, newState,
        		chgDesc, null, request.getOperId(), null, DateUtil.getSysDate());
    }
    
    /**
     * 审核拒绝  改变原始订单的商品售后标识状态
     * 
     */
    private void updateProdCusServiceFlag(OrdOrder ordOrder) {
		List<OrdOdProd> prodList = ordOdProdAtomSV.selectByOrd(ordOrder.getTenantId(), ordOrder.getOrderId());
		if(CollectionUtil.isEmpty(prodList)) {
			throw new BusinessException(ExceptCodeConstants.Special.NO_RESULT, 
					"未能查询到相关商品信息[订单id:"+ordOrder.getOrderId()+"]");
		}
		OrdOdProd ordOdProd = prodList.get(0);
		List<OrdOdProd> origProdList =ordOdProdAtomSV.selectSaleProd(ordOdProd.getTenantId(), 
				ordOrder.getOrigOrderId(), ordOdProd.getSkuId());
		if(CollectionUtil.isEmpty(origProdList)) {
			throw new BusinessException(ExceptCodeConstants.Special.NO_RESULT, 
					"未能查询到相关商品信息[原始订单id:"+ordOrder.getOrigOrderId()+" ,skuId:"+ordOdProd.getSkuId()+"]");
		}
		OrdOdProd prod = origProdList.get(0);  //单个订单对应单个商品(售后)
		prod.setCusServiceFlag(OrdersConstants.OrdOrder.cusServiceFlag.NO);
		ordOdProdAtomSV.updateById(prod);
    }
}
