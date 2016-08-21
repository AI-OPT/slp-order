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
import com.ai.opt.sdk.util.BeanUtils;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.util.DateUtil;
import com.ai.slp.order.api.deliveryorderprint.param.DeliveryOrderPrintRequest;
import com.ai.slp.order.api.deliveryorderprint.param.DeliveryOrderPrintResponse;
import com.ai.slp.order.api.deliveryorderprint.param.DeliveryProdPrintVo;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.dao.mapper.bo.DeliverInfoProd;
import com.ai.slp.order.dao.mapper.bo.OrdOdDeliverInfo;
import com.ai.slp.order.dao.mapper.bo.OrdOdLogistics;
import com.ai.slp.order.dao.mapper.bo.OrdOdLogisticsCriteria;
import com.ai.slp.order.dao.mapper.bo.OrdOdProd;
import com.ai.slp.order.dao.mapper.bo.OrdOdProdCriteria;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.service.atom.interfaces.IDeliveryOrderPrintAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdLogisticsAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdProdAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.ai.slp.order.service.business.interfaces.IDeliveryOrderNoMergePrintBusiSV;
import com.ai.slp.order.util.CommonCheckUtils;
import com.ai.slp.order.util.SequenceUtil;

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
	private IDeliveryOrderPrintAtomSV deliveryOrderPrintAtomSV;
	
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
		/* 创建订单提货信息*/
		Long deliverInfoId = this.createDeliveryOrderInfo(order.getOrderId(),null);
		long sum = 0;
		for (OrdOdProd ordOdProd : ordOdProds) {
			sum+=ordOdProd.getBuySum();
			DeliverInfoProd deliverInfoProd = this.createDeliverInfoProd(ordOdProd, deliverInfoId, ordOdProd.getBuySum());
	        DeliveryProdPrintVo dpVo=new DeliveryProdPrintVo();
	        BeanUtils.copyProperties(dpVo, deliverInfoProd);
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
		response.setSum(sum);
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
	 
	  /**
	   * 创建提货单打印信息
	   */
	  private Long createDeliveryOrderInfo(long orderId, OrdOrder ordOrder) {
		  Long deliverInfoId = SequenceUtil.createdeliverInfoId();
		  OrdOdDeliverInfo record=new OrdOdDeliverInfo();
		  record.setOrderId(orderId);
		  record.setDeliverInfoId(deliverInfoId);
		  record.setHorOrderId(ordOrder==null?0:ordOrder.getOrderId());
		  record.setPrintInfo(OrdersConstants.OrdOdDeliverInfo.printInfo.ONE);
		  record.setUpdateTime(DateUtil.getSysDate());
		  deliveryOrderPrintAtomSV.insertSelective(record);
		  return deliverInfoId;
	  }
	  
	  
	  /**
	   * 创建提货单信息明细
	   */
	  private DeliverInfoProd createDeliverInfoProd(OrdOdProd ordOdProd,Long deliverInfoId,long buySum) {
		  DeliverInfoProd deliverInfoProd=new DeliverInfoProd();
		  deliverInfoProd.setDeliverInfoId(deliverInfoId);
		  deliverInfoProd.setBuySum(buySum);
		  deliverInfoProd.setExtendInfo(ordOdProd.getExtendInfo());
		  deliverInfoProd.setProdName(ordOdProd.getProdName());
		  deliverInfoProd.setSkuId(ordOdProd.getSkuId());
		  deliveryOrderPrintAtomSV.insertSelective(deliverInfoProd);
		  return deliverInfoProd;
	  }
}
