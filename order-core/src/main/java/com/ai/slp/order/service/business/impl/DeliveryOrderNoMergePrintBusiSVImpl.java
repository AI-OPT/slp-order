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
import com.ai.slp.order.api.deliveryorderprint.param.DeliveryOrderPrintRequest;
import com.ai.slp.order.api.deliveryorderprint.param.DeliveryOrderPrintResponse;
import com.ai.slp.order.api.deliveryorderprint.param.DeliveryProdPrintVo;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.dao.mapper.bo.DeliverInfoProd;
import com.ai.slp.order.dao.mapper.bo.DeliverInfoProdCriteria;
import com.ai.slp.order.dao.mapper.bo.OrdOdDeliverInfo;
import com.ai.slp.order.dao.mapper.bo.OrdOdDeliverInfoCriteria;
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
		/* 参数校验及判断是否存在提货单打印信息*/
		List<OrdOdDeliverInfo> deliverInfos = this.checkParamAndQueryInfos(request);
		List<DeliveryProdPrintVo> list=new ArrayList<DeliveryProdPrintVo>();
		long sum = 0;
		if(CollectionUtil.isEmpty(deliverInfos)) { 
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
			/* 组装订单提货明细信息*/
			for (OrdOdProd ordOdProd : ordOdProds) {
				sum+=ordOdProd.getBuySum();
				DeliveryProdPrintVo dpVo = this.createDeliverInfoProd(ordOdProd, ordOdProd.getBuySum());
				list.add(dpVo);
			}
		}else {
			/*表示提货单打印信息已存在*/
			for (OrdOdDeliverInfo ordOdDeliverInfo : deliverInfos) {
				DeliverInfoProdCriteria example=new DeliverInfoProdCriteria();
				DeliverInfoProdCriteria.Criteria criteria = example.createCriteria();
				criteria.andDeliverInfoIdEqualTo(ordOdDeliverInfo.getDeliverInfoId());
				List<DeliverInfoProd> deliverInfoProds= deliveryOrderPrintAtomSV.selectByExample(example);
				for (DeliverInfoProd deliverInfoProd : deliverInfoProds) {
					sum+=deliverInfoProd.getBuySum();
					DeliveryProdPrintVo dpVo=new DeliveryProdPrintVo();
					BeanUtils.copyProperties(dpVo, deliverInfoProd);
					list.add(dpVo);
				}
			}
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
	   * 组装提货单信息明细
	   */
	  private DeliveryProdPrintVo createDeliverInfoProd(OrdOdProd ordOdProd,long buySum) {
		  DeliveryProdPrintVo dpVo=new DeliveryProdPrintVo();
		  dpVo.setBuySum(buySum);
		  dpVo.setExtendInfo(ordOdProd.getExtendInfo());
		  dpVo.setProdName(ordOdProd.getProdName());
		  dpVo.setSkuId(ordOdProd.getSkuId());
		  return dpVo;
	  }
	  
  	/**
	 * 参数检验
	 */
	  private List<OrdOdDeliverInfo> checkParamAndQueryInfos(DeliveryOrderPrintRequest request) {
			CommonCheckUtils.checkTenantId(request.getTenantId(), "");
			if(request.getOrderId()==0) {
				throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "订单id不能为空");
			}
			/* 判断是否存在提货单打印信息*/
			OrdOdDeliverInfoCriteria exampleDeliver=new OrdOdDeliverInfoCriteria();
			OrdOdDeliverInfoCriteria.Criteria criteriaDeliver = exampleDeliver.createCriteria();
			criteriaDeliver.andOrderIdEqualTo(request.getOrderId());
			criteriaDeliver.andPrintInfoEqualTo(OrdersConstants.OrdOdDeliverInfo.printInfo.ONE);
			List<OrdOdDeliverInfo> deliverInfos = deliveryOrderPrintAtomSV.selectByExample(exampleDeliver);
			return deliverInfos;
	  }
}
