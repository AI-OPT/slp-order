package com.ai.slp.order.service.business.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.util.DateUtil;
import com.ai.slp.common.api.cache.interfaces.ICacheSV;
import com.ai.slp.order.api.invoiceprint.param.InvoicePrintRequest;
import com.ai.slp.order.api.invoiceprint.param.InvoicePrintResponse;
import com.ai.slp.order.api.invoiceprint.param.InvoicePrintVo;
import com.ai.slp.order.constants.OrdRuleConstants;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.dao.mapper.attach.OrdOrderProdAttach;
import com.ai.slp.order.dao.mapper.bo.OrdOdLogistics;
import com.ai.slp.order.dao.mapper.bo.OrdOdLogisticsCriteria;
import com.ai.slp.order.dao.mapper.bo.OrdOdProd;
import com.ai.slp.order.dao.mapper.bo.OrdOdProdCriteria;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.dao.mapper.bo.OrdRule;
import com.ai.slp.order.service.atom.interfaces.IInvoicePrintAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdLogisticsAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdProdAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdRuleAtomSV;
import com.ai.slp.order.service.business.interfaces.IInvoicePrintBusiSV;
import com.ai.slp.order.service.business.interfaces.IOrderFrameCoreSV;
import com.ai.slp.order.util.CommonCheckUtils;

@Service
@Transactional
public class InvoicePrintBusiSVImpl implements IInvoicePrintBusiSV {
	
	private static final Logger logger=LoggerFactory.getLogger(DeliveryOrderPrintBusiSVImpl.class);

	@Autowired
	private IOrdOrderAtomSV ordOrderAtomSV;
	
	@Autowired
	private IOrdOdProdAtomSV ordOdProdAtomSV;
	
	@Autowired
	private IOrdOdLogisticsAtomSV ordOdLogisticsAtomSV;
	
	@Autowired
	private IInvoicePrintAtomSV invoicePrintAtomSV;
	
	@Autowired
	private IOrdRuleAtomSV ordRuleAtomSV;
	
	@Autowired
	private IOrderFrameCoreSV orderFrameCoreSV;
	
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
		long buySum=0;
		String routeId=null;
		/* 根据订单规则获取合并时间*/
		Timestamp time = getOrderListInTime(OrdRuleConstants.MERGE_ORDER_SETTING_ID,order.getOrderTime());
		List<InvoicePrintVo> list=new ArrayList<InvoicePrintVo>();
		for (OrdOdProd ordOdProd : ordOdProds) {
			/* 多表多条件查询*/
			List<OrdOrderProdAttach> orderProdAttachs = invoicePrintAtomSV.query(request.getUserId(),
					request.getTenantId(),ordOdProd.getSkuId(),ordOdProd.getRouteId(),
					OrdersConstants.OrdOrder.State.WAIT_DELIVERY,time,order.getOrderTime());
			if(!CollectionUtil.isEmpty(orderProdAttachs)) {
				for (OrdOrderProdAttach ordOrderProdAttach : orderProdAttachs) {
					buySum+=ordOrderProdAttach.getBuySum();
					OrdOrder ordOrder = ordOrderAtomSV.selectByOrderId(ordOrderProdAttach.getTenantId(), ordOrderProdAttach.getOrderId());
					String orgState=ordOrder.getState();
					String newState=OrdersConstants.OrdOrder.State.INVOICE_FINISH_PRINT;
					ordOrder.setState(newState);
					ordOrder.setStateChgTime(DateUtil.getSysDate());
					/* 修改订单状态为提货单已打印*/
					//TODO 发货单打印状态是否需要更改 ???????????????????????
					ordOrderAtomSV.updateById(ordOrder);
					/* 写入订单状态变化轨迹表 */
			        orderFrameCoreSV.ordOdStateChg(ordOrder.getOrderId(), ordOrder.getTenantId(), orgState,
			                newState, OrdersConstants.OrdOdStateChg.ChgDesc.ORDER_TO_WAIT_DELIVERY, null, null, null, DateUtil.getSysDate());
				}
			}
			routeId=ordOdProd.getRouteId();
			InvoicePrintVo dpVo=new InvoicePrintVo();
			dpVo.setProdName(ordOdProd.getProdName());
			dpVo.setExtendInfo(ordOdProd.getExtendInfo());
			dpVo.setBuySum(buySum);
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
		ICacheSV cacheSv = DubboConsumerFactory.getService("iCacheSV");
		response.setRouteId(routeId);
		response.setContactName(ordOdLogistics.getContactName());
	    response.setContactTel(ordOdLogistics.getContactTel());
	    response.setProvinceCode(cacheSv.getAreaName(ordOdLogistics.getProvinceCode()));
	    response.setCityCode(cacheSv.getAreaName(ordOdLogistics.getCityCode()));
	    response.setCountyCode(cacheSv.getAreaName(ordOdLogistics.getCountyCode()));
	    response.setAddress(ordOdLogistics.getAddress());
		response.setOrderId(request.getOrderId());
		response.setInvoicePrintVos(list);
		return response;
	}
	
	 /**
	  * 根据订单规则获取合并时间
	  */
	 public Timestamp getOrderListInTime(String buyIpMonitorId,Timestamp time) {
		OrdRule ordRule = ordRuleAtomSV.getOrdRule(buyIpMonitorId);
		if(ordRule==null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, 
					"未能查询到指定的订单规则信息[订单规则id:"+buyIpMonitorId+"]");
		}
	 	Calendar calendar = Calendar.getInstance();
	 	calendar.setTime(time);
	 	switch(ordRule.getTimeType()) {
	 		case "D":
	 			calendar.add(Calendar.DAY_OF_MONTH, -(ordRule.getMonitorTime()));
	 			break;
	 		case "H":
	 			calendar.add(Calendar.HOUR, -(ordRule.getMonitorTime()));
	 			break;
	 		default:
	 			calendar.add(Calendar.MINUTE, -(ordRule.getMonitorTime()));
	 			break;
	 	}
       return new Timestamp(calendar.getTimeInMillis());
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
