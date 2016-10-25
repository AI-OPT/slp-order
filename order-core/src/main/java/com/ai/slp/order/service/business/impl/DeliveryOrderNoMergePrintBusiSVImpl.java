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
import com.ai.slp.order.api.deliveryorderprint.param.DeliveryOrderPrintRequest;
import com.ai.slp.order.api.deliveryorderprint.param.DeliveryOrderPrintResponse;
import com.ai.slp.order.api.deliveryorderprint.param.DeliveryProdPrintVo;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.dao.mapper.bo.OrdOdLogistics;
import com.ai.slp.order.dao.mapper.bo.OrdOdProd;
import com.ai.slp.order.dao.mapper.bo.OrdOdProdCriteria;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.service.atom.interfaces.IOrdOdLogisticsAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdProdAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.ai.slp.order.service.business.interfaces.IDeliveryOrderNoMergePrintBusiSV;
import com.ai.slp.order.util.ValidateUtils;

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
	
	@Override
	public DeliveryOrderPrintResponse noMergePrint(DeliveryOrderPrintRequest request)
			throws BusinessException, SystemException {
		DeliveryOrderPrintResponse response=new DeliveryOrderPrintResponse();
		/* 参数校验*/
		ValidateUtils.validateDeliveryOrderPrintRequest(request);
		OrdOrder order = ordOrderAtomSV.selectByOrderId(request.getTenantId(), request.getOrderId());
		if(order==null) {
			logger.warn("未能查询到指定的订单信息[订单id:"+request.getOrderId()+"]");
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, 
					"未能查询到指定的订单信息[订单id:"+request.getOrderId()+"]");
		}
		List<DeliveryProdPrintVo> list=new ArrayList<DeliveryProdPrintVo>();
		long sum = 0;
		OrdOdProdCriteria example=new OrdOdProdCriteria();
		OrdOdProdCriteria.Criteria criteria = example.createCriteria();
		criteria.andTenantIdEqualTo(request.getTenantId());
		criteria.andOrderIdEqualTo(request.getOrderId());
		criteria.andCusServiceFlagEqualTo(OrdersConstants.OrdOrder.cusServiceFlag.NO);
		/* 根据订单查询商品信息*/
		List<OrdOdProd> ordOdProds = ordOdProdAtomSV.selectByExample(example);
		if(CollectionUtil.isEmpty(ordOdProds)) {
			logger.warn("未能查询到指定的订单商品明细信息[订单id:"+request.getOrderId()+"]");
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, 
					"未能查询到指定的订单商品明细信息[订单id:"+request.getOrderId()+"]");
		}
		/* 组装订单提货明细信息*/
		for (OrdOdProd ordOdProd : ordOdProds) {
			sum+=ordOdProd.getBuySum();
			DeliveryProdPrintVo dpVo = this.createDeliverInfoProd(ordOdProd, ordOdProd.getBuySum());
			list.add(dpVo);
		}
		/* 查询订单配送信息*/
		OrdOdLogistics ordOdLogistics = ordOdLogisticsAtomSV.selectByOrd(order.getTenantId(),order.getOrderId());
		if(ordOdLogistics==null) {
			logger.warn("未能查询到指定的订单配送信息[订单id:"+request.getOrderId()+"]");
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL,
					"未能查询到指定的订单配送信息[订单id:"+request.getOrderId()+"]");
		}
		response.setContactName(ordOdLogistics.getContactName());
		response.setOrderId(request.getOrderId());
		response.setDeliveryProdPrintVos(list);
		response.setSum(sum);
		return response;
	}
	
	  /**
	   * 组装提货单信息明细
	   */
	  private DeliveryProdPrintVo createDeliverInfoProd(OrdOdProd ordOdProd,long buySum) {
		  DeliveryProdPrintVo dpVo=new DeliveryProdPrintVo();
		  dpVo.setBuySum(buySum);
		  dpVo.setExtendInfo(ordOdProd.getExtendInfo());
		  dpVo.setProdName(ordOdProd.getProdName());
		  dpVo.setSkuId(ordOdProd.getSkuId());
		  dpVo.setSalePrice(ordOdProd.getSalePrice());
		  return dpVo;
	  }
}
