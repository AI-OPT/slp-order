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
import com.ai.opt.sdk.util.StringUtil;
import com.ai.slp.order.api.delivergoods.param.DeliverGoodsRequest;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.constants.OrdersConstants.OrdOdStateChg;
import com.ai.slp.order.dao.mapper.bo.OrdOdLogistics;
import com.ai.slp.order.dao.mapper.bo.OrdOdProd;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.service.atom.interfaces.IOrdOdLogisticsAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdProdAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.ai.slp.order.service.business.interfaces.IDeliverGoodsBusiSV;
import com.ai.slp.order.service.business.interfaces.IOrderFrameCoreSV;
import com.ai.slp.order.util.ValidateUtils;

@Service
@Transactional
public class DeliverGoodsBusiSVImpl implements IDeliverGoodsBusiSV {
	
	private static final Logger logger=LoggerFactory.getLogger(DeliverGoodsBusiSVImpl.class);

	@Autowired
	private IOrdOdLogisticsAtomSV ordOdLogisticsAtomSV;
	
	@Autowired
	private IOrdOrderAtomSV ordOrderAtomSV;
	
	@Autowired
	private IOrderFrameCoreSV orderFrameCoreSV;
	
	@Autowired
	private IOrdOdProdAtomSV ordOdProdAtomSV;
	
	@Override
	public void deliverGoods(DeliverGoodsRequest request) throws BusinessException, SystemException {
		/* 参数校验*/
		ValidateUtils.validateDeliverGoodsRequest(request);
		OrdOrder ordOrder = ordOrderAtomSV.selectByOrderId(request.getTenantId(), request.getOrderId());
		if(ordOrder==null) {
			logger.error("未能查询到指定的订单主表信息[订单id:"+request.getOrderId()+" ,租户id:"+request.getTenantId()+"]");
			throw new BusinessException(ExceptCodeConstants.Special.NO_RESULT, 
					"未能查询到指定的订单主表信息[订单id:"+request.getOrderId()+" ,租户id:"+request.getTenantId()+"]");
		}
		List<OrdOdProd> ordOdProds = this.getOrdOdProds(request);
		for (OrdOdProd ordOdProd : ordOdProds) {
			if(OrdersConstants.OrdOrder.cusServiceFlag.YES.equals(ordOdProd.getCusServiceFlag())) {
				//该商品为售后标识 不可打印
				throw new BusinessException("", "订单下商品处于售后状态,不可打印");
			}
		}
		/* 查询父订单对应的配送信息*/
		OrdOdLogistics ordOdLogistics = ordOdLogisticsAtomSV.selectByOrd(ordOrder.getTenantId(), ordOrder.getParentOrderId());
		if(ordOdLogistics==null) {
			logger.error("未能查询到指定的配送信息[父订单id:"+ordOrder.getParentOrderId()+" ,租户id:"+ordOrder.getTenantId()+"]");
			throw new BusinessException(ExceptCodeConstants.Special.NO_RESULT, 
					"未能查询到指定的配送信息[订单id:"+ordOrder.getParentOrderId()+" ,租户id:"+ordOrder.getTenantId()+"]");
		}
		if(!StringUtil.isBlank(ordOdLogistics.getExpressOddNumber())) {
			throw new BusinessException("", "订单不能重复发货");
		}
		ordOdLogistics.setExpressId(request.getExpressId());
		ordOdLogistics.setExpressOddNumber(request.getExpressOddNumber());
		ordOdLogisticsAtomSV.updateByPrimaryKey(ordOdLogistics);
		/* 更新订单状态和订单轨迹信息*/
		this.updateOrderState(ordOrder, ordOrder.getOperId());
	}
	
	
	/**
     * 更新订单状态,并写入订单状态变化轨迹表
     * 
     */
    private void updateOrderState(OrdOrder ordOrder,String operId) {
        String orgState = ordOrder.getState();
        String newState = OrdersConstants.OrdOrder.State.WAIT_CONFIRM;
        ordOrder.setState(newState);
        Timestamp sysDate=DateUtil.getSysDate();
        ordOrder.setStateChgTime(sysDate);
        ordOrderAtomSV.updateById(ordOrder);
        // 写入订单状态变化轨迹表
        orderFrameCoreSV.ordOdStateChg(ordOrder.getOrderId(), ordOrder.getTenantId(), orgState, newState,
                OrdOdStateChg.ChgDesc.ORDER_TO_FINISH_LOGISTICS_DELIVERY, null, operId, null, sysDate);
    }
    
    /**
	  * 获取订单下的商品信息
	  */
	private List<OrdOdProd> getOrdOdProds(DeliverGoodsRequest request) {
		List<OrdOdProd> ordOdProds = ordOdProdAtomSV.selectByOrd(request.getTenantId(), request.getOrderId());
		if(CollectionUtil.isEmpty(ordOdProds)) {
			logger.warn("未能查询到指定的订单商品明细信息[订单id:"+request.getOrderId()+"]");
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, 
					"未能查询到指定的订单商品明细信息[订单id:"+request.getOrderId()+"]");
		}
		return ordOdProds;
	}
}
