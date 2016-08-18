package com.ai.slp.order.service.business.impl;

import java.sql.Timestamp;
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
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.BeanUtils;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.util.DateUtil;
import com.ai.slp.common.api.cache.interfaces.ICacheSV;
import com.ai.slp.order.api.invoiceprint.param.InvoicePrintRequest;
import com.ai.slp.order.api.invoiceprint.param.InvoicePrintResponse;
import com.ai.slp.order.api.invoiceprint.param.InvoicePrintVo;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.constants.OrdersConstants.OrdOdStateChg;
import com.ai.slp.order.dao.mapper.bo.DeliverInfoProd;
import com.ai.slp.order.dao.mapper.bo.DeliverInfoProdCriteria;
import com.ai.slp.order.dao.mapper.bo.OrdOdDeliverInfo;
import com.ai.slp.order.dao.mapper.bo.OrdOdDeliverInfoCriteria;
import com.ai.slp.order.dao.mapper.bo.OrdOdLogistics;
import com.ai.slp.order.dao.mapper.bo.OrdOdLogisticsCriteria;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.service.atom.interfaces.IDeliveryOrderPrintAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdLogisticsAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.ai.slp.order.service.business.interfaces.IInvoicePrintBusiSV;
import com.ai.slp.order.service.business.interfaces.IOrderFrameCoreSV;
import com.ai.slp.order.util.CommonCheckUtils;
import com.ai.slp.order.util.SequenceUtil;

@Service
@Transactional
public class InvoicePrintBusiSVImpl implements IInvoicePrintBusiSV {
	
	private static final Logger logger=LoggerFactory.getLogger(DeliveryOrderPrintBusiSVImpl.class);

	@Autowired
	private IOrdOrderAtomSV ordOrderAtomSV;
	
	@Autowired
	private IOrdOdLogisticsAtomSV ordOdLogisticsAtomSV;
	
	@Autowired
	private IOrderFrameCoreSV orderFrameCoreSV;
	
	@Autowired
	private IDeliveryOrderPrintAtomSV deliveryOrderPrintAtomSV;
	
	@Override
	public InvoicePrintResponse print(InvoicePrintRequest request) throws BusinessException, SystemException {
		InvoicePrintResponse response=new InvoicePrintResponse();
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
		OrdOdDeliverInfoCriteria example=new OrdOdDeliverInfoCriteria();
		OrdOdDeliverInfoCriteria.Criteria criteria = example.createCriteria();
		criteria.andOrderIdEqualTo(order.getOrderId());
		criteria.andPrintInfoEqualTo(OrdersConstants.OrdOdDeliverInfo.printInfo.ONE);
		/* 查询发货单打印信息*/
		List<OrdOdDeliverInfo> infos = deliveryOrderPrintAtomSV.selectByExample(example);
		if(CollectionUtil.isEmpty(infos)) {
			logger.warn("未查询到相应的信息,请查看是否打印了提货单信息.");
			throw new BusinessException(ExceptCodeConstants.Special.NO_RESULT, "未查询到相应的信息,请查看是否打印了提货单信息.");
		}
		List<InvoicePrintVo> list=new ArrayList<InvoicePrintVo>();
		for (OrdOdDeliverInfo ordOdDeliverInfo : infos) {
			/* 创建发货单信息*/
			Long invoiceInfoId = SequenceUtil.createdeliverInfoId();
			OrdOdDeliverInfo invoiceInfo=new OrdOdDeliverInfo();
			BeanUtils.copyProperties(invoiceInfo, ordOdDeliverInfo);
			invoiceInfo.setDeliverInfoId(invoiceInfoId);
			invoiceInfo.setPrintInfo(OrdersConstants.OrdOdDeliverInfo.printInfo.TWO);
			invoiceInfo.setUpdateTime(DateUtil.getSysDate());
			deliveryOrderPrintAtomSV.insertSelective(invoiceInfo);
			/* 查询提发货明细信息*/
			DeliverInfoProdCriteria exampleInfo=new DeliverInfoProdCriteria();
			DeliverInfoProdCriteria.Criteria criteriaInfo = exampleInfo.createCriteria();
			criteriaInfo.andDeliverInfoIdEqualTo(ordOdDeliverInfo.getDeliverInfoId());
			List<DeliverInfoProd> deliverInfoProds = deliveryOrderPrintAtomSV.selectByExample(exampleInfo);
			if(!CollectionUtil.isEmpty(deliverInfoProds)) {
				DeliverInfoProd deliverInfoProd = deliverInfoProds.get(0);
				InvoicePrintVo invoicePrintVo=new InvoicePrintVo();
				invoicePrintVo.setSkuId(deliverInfoProd.getSkuId());
				invoicePrintVo.setProdName(deliverInfoProd.getProdName());
				invoicePrintVo.setExtendInfo(deliverInfoProd.getExtendInfo());
				invoicePrintVo.setBuySum(deliverInfoProd.getBuySum());
				invoicePrintVo.setSalePrice(deliverInfoProd.getSalePrice());
				deliverInfoProd.setDeliverInfoId(invoiceInfoId);
				deliveryOrderPrintAtomSV.insertSelective(deliverInfoProd);
				list.add(invoicePrintVo);
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
		ICacheSV cacheSv = DubboConsumerFactory.getService("iCacheSV");
		/* 更新订单状态并写入订单状态变化轨迹*/
		this.updateOrderState(order, DateUtil.getSysDate());
		response.setRouteId(order.getRouteId());
		response.setContactName(ordOdLogistics.getContactName());
	    response.setContactTel(ordOdLogistics.getContactTel());
	    response.setProvinceCode(cacheSv.getAreaName(ordOdLogistics.getProvinceCode()));
	    response.setCityCode(cacheSv.getAreaName(ordOdLogistics.getCityCode()));
	    response.setCountyCode(cacheSv.getAreaName(ordOdLogistics.getCountyCode()));
	    response.setAddress(ordOdLogistics.getAddress());
		response.setOrderId(request.getOrderId());
		response.setExpressOddNumber(ordOdLogistics.getExpressOddNumber());
		response.setInvoiceDate(DateUtil.getDate());
		response.setInvoicePrintVos(list);
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
	 
	 
	 private void updateOrderState(OrdOrder ordOrder, Timestamp sysDate) {
	        String orgState = ordOrder.getState();
	        String state1 = OrdersConstants.OrdOrder.State.INVOICE_FINISH_PRINT;
	        String state2 = OrdersConstants.OrdOrder.State.FINISH_DELIVERY;
	        String newState = OrdersConstants.OrdOrder.State.WAIT_SEND;
	        ordOrder.setState(newState);
	        ordOrder.setStateChgTime(sysDate);
	        ordOrderAtomSV.updateById(ordOrder);
	        // 写入订单状态变化轨迹表
	        orderFrameCoreSV.ordOdStateChg(ordOrder.getOrderId(), ordOrder.getTenantId(), orgState, state1,
	                OrdOdStateChg.ChgDesc.INVOICE_ORDER_TO_PRINT, null, null, null, sysDate);
	        orderFrameCoreSV.ordOdStateChg(ordOrder.getOrderId(), ordOrder.getTenantId(), state1, state2,
	                OrdOdStateChg.ChgDesc.ORDER_TO_FINISH_LOGISTICS_DELIVERY, null, null, null, sysDate);
	        //TODO  ??
	        orderFrameCoreSV.ordOdStateChg(ordOrder.getOrderId(), ordOrder.getTenantId(), state2, newState,
	                OrdOdStateChg.ChgDesc.ORDER_TO_WAIT_DELIVERY, null, null, null, sysDate);
		 }
}
