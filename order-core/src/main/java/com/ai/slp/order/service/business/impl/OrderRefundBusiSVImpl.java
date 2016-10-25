package com.ai.slp.order.service.business.impl;

import java.sql.Timestamp;
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
import com.ai.slp.order.api.orderrefund.param.OrderRefundRequest;
import com.ai.slp.order.api.orderrefund.param.OrderRefuseRefundRequest;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.constants.OrdersConstants.OrdOdStateChg;
import com.ai.slp.order.dao.mapper.bo.OrdOdFeeTotal;
import com.ai.slp.order.dao.mapper.bo.OrdOdProd;
import com.ai.slp.order.dao.mapper.bo.OrdOdProdCriteria;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.dao.mapper.bo.OrdOrderCriteria;
import com.ai.slp.order.dao.mapper.bo.OrdOdProdCriteria.Criteria;
import com.ai.slp.order.service.atom.interfaces.IOrdOdFeeTotalAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdProdAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.ai.slp.order.service.business.interfaces.IOrderFrameCoreSV;
import com.ai.slp.order.service.business.interfaces.IOrderRefundBusiSV;
import com.ai.slp.order.util.ValidateUtils;

@Service
@Transactional
public class OrderRefundBusiSVImpl implements IOrderRefundBusiSV {

	private static Logger logger = LoggerFactory.getLogger(OrderRefundBusiSVImpl.class);
	
	@Autowired
	private IOrdOrderAtomSV ordOrderAtomSV;
	
	@Autowired
	private IOrderFrameCoreSV orderFrameCoreSV;
	
	@Autowired
	private IOrdOdProdAtomSV ordOdProdAtomSV;
	
	@Autowired
	private IOrdOdFeeTotalAtomSV  ordOdFeeTotalAtomSV;
	
	public void partRefund(OrderRefundRequest request) throws BusinessException, SystemException {
		/* 参数检验*/
		ValidateUtils.validateOrderRefundRequest(request);
		OrdOrder ordOrder = ordOrderAtomSV.selectByOrderId(request.getTenantId(), request.getOrderId());
		if(ordOrder==null) {
			logger.error("订单信息不存在[订单id:"+request.getOrderId()+"租户id:"+request.getTenantId()+"]");
			throw new BusinessException(ExceptCodeConstants.Special.NO_RESULT, 
					"订单信息不存在[订单id:"+request.getOrderId()+"租户id:"+request.getTenantId()+"]");
		}
		OrdOdFeeTotal ordOdFeeTotal = ordOdFeeTotalAtomSV.selectByOrderId(ordOrder.getTenantId(), 
				ordOrder.getOrderId());
		long updateMoney = request.getUpdateMoney()*1000;
		/*判断输入费用是否大于之前存在的费用*/
		if(updateMoney>ordOdFeeTotal.getAdjustFee()) {
			logger.error("输入的费用不能大于实际应收的费用,实际应收费用为:"+ordOdFeeTotal.getAdjustFee());
			throw new BusinessException("", "输入的费用不能大于实际应收的费用");
		}
		ordOdFeeTotal.setOperDiscountDesc(request.getUpdateReason());
		ordOdFeeTotal.setPaidFee(updateMoney);
		ordOdFeeTotal.setUpdateOperId(request.getOperId());
		ordOdFeeTotalAtomSV.updateByOrderId(ordOdFeeTotal);
		ordOrder.setReasonDesc(request.getUpdateReason());
		ordOrder.setOperId(request.getOperId());
		ordOrderAtomSV.updateById(ordOrder);
	}
	
	@Override
	public void refuseRefund(OrderRefuseRefundRequest request) throws BusinessException, SystemException {
		/* 参数校验*/
		ValidateUtils.validateOrderRefuseRefundRequest(request);
		OrdOrder ordOrder = ordOrderAtomSV.selectByOrderId(request.getTenantId(), request.getOrderId());
		if(ordOrder==null) {
			logger.error("订单信息不存在[订单id:"+request.getOrderId()+"租户id:"+request.getTenantId()+"]");
			throw new BusinessException(ExceptCodeConstants.Special.NO_RESULT, 
					"订单信息不存在[订单id:"+request.getOrderId()+"租户id:"+request.getTenantId()+"]");
		}
		/* 更新订单状态和写入订单轨迹*/
		String orgState = ordOrder.getState();
		String newState=OrdersConstants.OrdOrder.State.AUDIT_AGAIN_FAILURE;
		String chgDesc=OrdOdStateChg.ChgDesc.ORDER_AUDIT_NOT_PASS;
		Timestamp sysDate=DateUtil.getSysDate();
        ordOrder.setReasonDesc(request.getRefuseReason());
        ordOrder.setState(newState);
        ordOrder.setStateChgTime(sysDate);
        ordOrder.setOperId(request.getOperId());
        ordOrderAtomSV.updateById(ordOrder);
        //订单业务类型为退货的话,第二次审核失败,则判断子订单信息
        if(OrdersConstants.OrdOrder.BusiCode.UNSUBSCRIBE_ORDER.equals(ordOrder.getBusiCode())) {
        	/* 获取子订单信息及子订单下的商品明细信息*/
    		OrdOrder order = ordOrderAtomSV.selectByOrderId(request.getTenantId(), 
    				ordOrder.getOrigOrderId());
    		List<OrdOdProd> prodList = ordOdProdAtomSV.selectByOrd(request.getTenantId(), 
    				ordOrder.getOrigOrderId());
    		boolean cusFlag=false;
    		for (OrdOdProd ordOdProd : prodList) {
    			if(OrdersConstants.OrdOrder.cusServiceFlag.YES.equals(ordOdProd.getCusServiceFlag())) {
    				cusFlag=true;
    			}else {
    				cusFlag=false;
    			}
    		}
    		/* 获取子订单下的所有售后订单*/
    		OrdOrderCriteria example=new OrdOrderCriteria();
    		OrdOrderCriteria.Criteria criteria = example.createCriteria();
    		criteria.andOrigOrderIdEqualTo(ordOrder.getOrigOrderId());
    		criteria.andOrderIdNotEqualTo(request.getOrderId());
    		List<OrdOrder> orderList = ordOrderAtomSV.selectByExample(example);
    		OrdOrder parentOrder = ordOrderAtomSV.selectByOrderId(request.getTenantId(), 
    				 order.getParentOrderId()); //父订单
    		boolean flag=false;
    		for (OrdOrder afterOrdOrder : orderList) {  //表示有售后订单存在
    			String state = afterOrdOrder.getState();
    			if(OrdersConstants.OrdOrder.State.FINISH_REFUND.equals(state)||
    					OrdersConstants.OrdOrder.State.EXCHANGE_AUDIT.equals(state)||
    					OrdersConstants.OrdOrder.State.REFUND_AUDIT.equals(state)||
    					OrdersConstants.OrdOrder.State.AUDIT_FAILURE.equals(state)||
    					OrdersConstants.OrdOrder.State.AUDIT_AGAIN_FAILURE.equals(state)) { //表示售后订单为已完成状态或者审核失败
    				flag=true;
    			}else {
    				flag=false;
    			}
    		}
    		//未发货状态时
    		if(OrdersConstants.OrdOrder.State.WAIT_DISTRIBUTION.equals(order.getState())||
    				 OrdersConstants.OrdOrder.State.WAIT_DELIVERY.equals(order.getState())||
    				 OrdersConstants.OrdOrder.State.WAIT_SEND.equals(order.getState())) {
    			 if(!CollectionUtil.isEmpty(orderList)) { //有售后订单 
    				 //TODO
    				if(flag&&cusFlag) {
    					order.setState(OrdersConstants.OrdOrder.State.COMPLETED);
    					ordOrderAtomSV.updateById(order);
    					parentOrder.setState(OrdersConstants.OrdOrder.State.COMPLETED);
    					ordOrderAtomSV.updateById(parentOrder); 
    				} 
    			 }
    		 }else { //已发货状态
    			 if(!CollectionUtil.isEmpty(orderList)&&flag) {
    				 order.setState(OrdersConstants.OrdOrder.State.COMPLETED);
    				 ordOrderAtomSV.updateById(order);
    				 parentOrder.setState(OrdersConstants.OrdOrder.State.COMPLETED);
    				 ordOrderAtomSV.updateById(parentOrder);
    			 }
    		 }
        }else {
        	/* 退款业务类型时  拒绝  改变原始订单的商品售后标识状态*/
    		this.updateProdCusServiceFlag(ordOrder);
        }
        // 写入订单状态变化轨迹表
        this.updateOrderState(ordOrder, orgState, newState, chgDesc, request.getOperId());
	}
	
    /**
     * 更新订单状态
     */
    private void updateOrderState(OrdOrder ordOrder,String 
    		orgState,String newState,String chgDesc,String operId) {
        orderFrameCoreSV.ordOdStateChg(ordOrder.getOrderId(), ordOrder.getTenantId(), orgState, newState,
        		chgDesc, null, operId, null, DateUtil.getSysDate());
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
