package com.ai.slp.order.service.business.impl;

import java.util.ArrayList;
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
import com.ai.slp.order.api.deliveryorderprint.param.DeliveryOrderPrintRequest;
import com.ai.slp.order.api.deliveryorderprint.param.DeliveryOrderPrintResponse;
import com.ai.slp.order.api.deliveryorderprint.param.DeliveryProdPrintVo;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.dao.mapper.bo.OrdOdLogistics;
import com.ai.slp.order.dao.mapper.bo.OrdOdLogisticsCriteria;
import com.ai.slp.order.dao.mapper.bo.OrdOdProd;
import com.ai.slp.order.dao.mapper.bo.OrdOdProdCriteria;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.service.atom.interfaces.IOrdOdLogisticsAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdProdAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.ai.slp.order.service.business.interfaces.IDeliveryOrderNoMergePrintBusiSV;
import com.ai.slp.order.service.business.interfaces.IOrderFrameCoreSV;
import com.ai.slp.order.util.CommonCheckUtils;

@Service
@Transactional
public class DeliveryOrderNoMergePrintBusiSVImpl implements IDeliveryOrderNoMergePrintBusiSV {
	
	private static final Logger logger =LoggerFactory.getLogger(DeliveryOrderNoMergePrintBusiSVImpl.class);
	
	@Autowired
	private IOrdOrderAtomSV ordOrderAtomSV;
	
	@Autowired
	private IOrdOdProdAtomSV ordOdProdAtomSV;
	
	@Autowired
	private IOrdOdLogisticsAtomSV ordOdLogisticsAtomSV;
	
	@Autowired
	private IOrderFrameCoreSV orderFrameCoreSV;
	
	@Override
	public DeliveryOrderPrintResponse noMergePrint(DeliveryOrderPrintRequest request)
			throws BusinessException, SystemException {
		DeliveryOrderPrintResponse response=new DeliveryOrderPrintResponse();
		/* 参数校验*/
		CommonCheckUtils.checkTenantId(request.getTenantId(), "");
		if(request.getOrderId()==0) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "订单id不能为空");
		}
		OrdOrder order = ordOrderAtomSV.selectByOrderId(request.getTenantId(), request.getOrderId());
		if(order==null) {
			logger.warn("未能查询到指定的订单信息[订单id:"+request.getOrderId()+"]");
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, 
					"未能查询到指定的订单信息[订单id:"+request.getOrderId()+"]");
		}
		OrdOdProdCriteria example=new OrdOdProdCriteria();
		OrdOdProdCriteria.Criteria criteria = example.createCriteria();
		criteria.andTenantIdEqualTo(request.getTenantId());
		criteria.andOrderIdEqualTo(request.getOrderId());
		/* 根据订单查询商品信息*/
		List<OrdOdProd> ordOdProds = ordOdProdAtomSV.selectByExample(example);
		if(CollectionUtil.isEmpty(ordOdProds)) {
			logger.warn("未能查询到指定的订单商品明细信息[订单id:"+request.getOrderId()+"]");
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, 
					"未能查询到指定的订单商品明细信息[订单id:"+request.getOrderId()+"]");
		}
		List<DeliveryProdPrintVo> list=new ArrayList<DeliveryProdPrintVo>();
		for (OrdOdProd ordOdProd : ordOdProds) {
			String orgState=order.getState();
			String newState=OrdersConstants.OrdOrder.State.LADING_BILL_FINISH_PRINT;
			order.setState(newState);
			order.setStateChgTime(DateUtil.getSysDate());
			/* 修改订单状态为提货单已打印*/
			ordOrderAtomSV.updateById(order);
			/* 写入订单状态变化轨迹表 */
	        orderFrameCoreSV.ordOdStateChg(order.getOrderId(), order.getTenantId(), orgState,
	                newState, OrdersConstants.OrdOdStateChg.ChgDesc.ORDER_TO_CANCEL, null, null, null, DateUtil.getSysDate());
	        DeliveryProdPrintVo dpVo=new DeliveryProdPrintVo();
	        dpVo.setProdName(ordOdProd.getProdName());
	        dpVo.setExtendInfo(ordOdProd.getExtendInfo());
	        dpVo.setBuySum(ordOdProd.getBuySum());
	        //TODO 
	        //设置商户编号
	        list.add(dpVo);
		}
		/* 查询订单配送信息*/
		List<OrdOdLogistics> logistics = getOrdOdLogistics(request.getOrderId(), request.getTenantId());
		if(CollectionUtil.isEmpty(logistics)) {
			logger.warn("未能查询到指定的订单配送信息[订单id:"+request.getOrderId()+"]");
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL,
					"未能查询到指定的订单配送信息[订单id:"+request.getOrderId()+"]");
		}
		OrdOdLogistics ordOdLogistics = logistics.get(0);
		response.setContactName(ordOdLogistics.getContactName());
		response.setOrderId(request.getOrderId());
		response.setDeliveryProdPrintVos(list);
		return response;
	}
	
	
	/**
	  * 查询订单配送信息
	  */
	 private List<OrdOdLogistics> getOrdOdLogistics(Long orderId,String tenantId) {
		OrdOdLogisticsCriteria exampleLogistics=new OrdOdLogisticsCriteria();
		OrdOdLogisticsCriteria.Criteria criteriaLogistics = exampleLogistics.createCriteria();
		criteriaLogistics.andTenantIdEqualTo(tenantId);
		criteriaLogistics.andOrderIdEqualTo(orderId);
		List<OrdOdLogistics> logistics = ordOdLogisticsAtomSV.selectByExample(exampleLogistics);
		return logistics;
	 }

}
