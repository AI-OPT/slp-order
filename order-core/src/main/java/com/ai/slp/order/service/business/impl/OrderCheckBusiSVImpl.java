package com.ai.slp.order.service.business.impl;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.util.DateUtil;
import com.ai.slp.order.api.aftersaleorder.impl.OrderAfterSaleSVImpl;
import com.ai.slp.order.api.ordercheck.param.OrderCheckRequest;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.constants.OrdersConstants.OrdOdStateChg;
import com.ai.slp.order.dao.mapper.bo.OrdOdProd;
import com.ai.slp.order.dao.mapper.bo.OrdOdProdCriteria;
import com.ai.slp.order.dao.mapper.bo.OrdOdProdCriteria.Criteria;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.service.atom.interfaces.IOrdOdProdAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.ai.slp.order.service.business.interfaces.IOrderCheckBusiSV;
import com.ai.slp.order.service.business.interfaces.IOrderFrameCoreSV;
import com.ai.slp.order.util.CommonCheckUtils;

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

	@Override
	public void check(OrderCheckRequest request) throws BusinessException, SystemException {
		/* 租户非空校验*/
		CommonCheckUtils.checkTenantId(request.getTenantId(), ExceptCodeConstants.Special.PARAM_IS_NULL);
		/* 订单id非空检验*/
		if(request.getOrderId()==0) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "订单id不能为空");
		}
		OrdOrder ordOrder = ordOrderAtomSV.selectByOrderId(request.getTenantId(), request.getOrderId());
		if(ordOrder==null) {
			logger.error("未能查询到相对应的订单信息[订单id:"+request.getOrderId()+"租户id:"+request.getTenantId()+"]");
			throw new BusinessException(ExceptCodeConstants.Special.NO_RESULT, 
					"未能查询到相对应的订单信息[订单id:"+request.getOrderId()+"租户id:"+request.getTenantId()+"]");
		}
		/* 审核结果STATE检验*/
		String state = request.getState();
		if(!OrdersConstants.OrdOrder.State.REVOKE_FINISH_AUDITED.equals(state)
				||!OrdersConstants.OrdOrder.State.AUDIT_FAILURE.equals(state)) {
			throw new BusinessException("", "订单审核结果入参有误");
		}
		String orgState = ordOrder.getState();
		if(!OrdersConstants.OrdOrder.State.REVOKE_WAIT_AUDIT.equals(orgState)) {
			throw new BusinessException("", "此订单不处于待审核状态");
		}
		if(OrdersConstants.OrdOrder.State.REVOKE_FINISH_AUDITED.equals(state)) {//表示审核通过
			String transitionState=OrdersConstants.OrdOrder.State.REVOKE_FINISH_AUDITED; //订单轨迹记录状态
			String newState=OrdersConstants.OrdOrder.State.REVOKE_WAIT_CONFIRM;
			String transitionChgDesc=OrdOdStateChg.ChgDesc.ORDER_AUDITED;
			String chgDesc=OrdOdStateChg.ChgDesc.ORDER_BUYERS_TO_RETURN;
			ordOrder.setState(newState);
	        ordOrder.setStateChgTime(DateUtil.getSysDate());
	        ordOrder.setRemark(request.getRemark());
	        ordOrderAtomSV.updateById(ordOrder);
	        // 写入订单状态变化轨迹表
			this.updateOrderState(ordOrder, orgState,transitionState, transitionChgDesc, request);
			this.updateOrderState(ordOrder, transitionState,newState, chgDesc, request);
		}else {
			//审核拒绝  改变原始订单的商品售后标识状态
			this.updateProdCusServiceFlag(ordOrder);
			String newState=OrdersConstants.OrdOrder.State.AUDIT_FAILURE;
			String chgDesc=OrdOdStateChg.ChgDesc.ORDER_AUDIT_NOT_PASS;
			ordOrder.setState(newState);
	        ordOrder.setStateChgTime(DateUtil.getSysDate());
	        ordOrder.setRemark(request.getRemark());
	        ordOrderAtomSV.updateById(ordOrder);
			this.updateOrderState(ordOrder, orgState,newState, chgDesc, request);
		}
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
		OrdOdProdCriteria example=new OrdOdProdCriteria();
		Criteria criteria = example.createCriteria();
		criteria.andOrderIdEqualTo(ordOrder.getOrigOrderId());
		criteria.andSkuIdEqualTo(ordOdProd.getSkuId());
		criteria.andTenantIdEqualTo(ordOdProd.getTenantId());
		List<OrdOdProd> origProdList = ordOdProdAtomSV.selectByExample(example);
		if(CollectionUtil.isEmpty(origProdList)) {
			throw new BusinessException(ExceptCodeConstants.Special.NO_RESULT, 
					"未能查询到相关商品信息[原始订单id:"+ordOrder.getOrigOrderId()+" ,skuId:"+ordOdProd.getSkuId()+"]");
		}
		OrdOdProd prod = origProdList.get(0);  //单个订单对应单个商品(售后)
		prod.setCusServiceFlag(OrdersConstants.OrdOrder.cusServiceFlag.NO);
		ordOdProdAtomSV.updateById(prod);
    }
}
