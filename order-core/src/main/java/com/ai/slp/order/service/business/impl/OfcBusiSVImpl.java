package com.ai.slp.order.service.business.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.util.StringUtil;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.dao.mapper.bo.OrdOdFeeTotal;
import com.ai.slp.order.dao.mapper.bo.OrdOdFeeTotalCriteria;
import com.ai.slp.order.dao.mapper.bo.OrdOdLogistics;
import com.ai.slp.order.dao.mapper.bo.OrdOdLogisticsCriteria;
import com.ai.slp.order.dao.mapper.bo.OrdOdProd;
import com.ai.slp.order.dao.mapper.bo.OrdOdProdCriteria;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.dao.mapper.bo.OrdOrderCriteria;
import com.ai.slp.order.dao.mapper.bo.OrdParam;
import com.ai.slp.order.dao.mapper.bo.OrdParamCriteria;
import com.ai.slp.order.service.atom.interfaces.IOrdOdFeeTotalAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdLogisticsAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdProdAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdParamAtomSV;
import com.ai.slp.order.service.business.interfaces.IOfcBusiSV;
import com.ai.slp.order.util.SequenceUtil;
import com.ai.slp.order.vo.OfcCodeRequst;
import com.ai.slp.order.vo.OrdOdProdVo;
import com.ai.slp.order.vo.OrderOfcVo;
import com.alibaba.fastjson.JSON;

@Service
@Transactional
public class OfcBusiSVImpl implements IOfcBusiSV {

	private static final Logger LOG = LoggerFactory.getLogger(OfcBusiSVImpl.class);

	@Autowired
	private IOrdOrderAtomSV ordOrderAtomSV;

	@Autowired
	private IOrdOdFeeTotalAtomSV ordOdFeeTotalAtomSV;
	@Autowired
	private IOrdOdLogisticsAtomSV ordOdLogisticsAtomSV;

	@Autowired
	private IOrdOdProdAtomSV ordOdProdAtomSV;

	@Autowired
	private IOrdParamAtomSV ordParamAtomSV;
	
	
	//保存订单数据
	@Override
	public void insertOrdOrder(OrderOfcVo request) throws BusinessException, SystemException {
		if (StringUtil.isBlank(request.getOrOrderOfcVo().getTenantId() + request.getOrdOdLogisticsVo().getTenantId()
				+ request.getOrdOdFeeTotalVo().getTenantId())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "订单信息租户Id不能为空");
		}
		if (StringUtil.isBlank("" + request.getOrOrderOfcVo().getOrderId() + request.getOrdOdLogisticsVo().getOrderId()
				+ request.getOrOrderOfcVo().getOrderId())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "订单信息订单Id不能为空");
		}
		// 订单信息
		LOG.info("++++++++++++++++++请求数据+++++++++++++++" + JSON.toJSONString(request));
		OrdOrderCriteria orderNumExample = new OrdOrderCriteria();
		OrdOrderCriteria.Criteria orderNumCriteria = orderNumExample.createCriteria();
		orderNumCriteria.andTenantIdEqualTo(request.getOrOrderOfcVo().getTenantId());
		orderNumCriteria.andOrderIdEqualTo(request.getOrOrderOfcVo().getOrderId());
		int orderNum = ordOrderAtomSV.countByExample(orderNumExample);
		OrdOrder ordOrder = new OrdOrder();
		BeanUtils.copyProperties(request.getOrOrderOfcVo(), ordOrder);
		if (orderNum == 0) {
			ordOrderAtomSV.insertSelective(ordOrder);
		} else {
			ordOrderAtomSV.updateByExampleSelective(ordOrder, orderNumExample);
		}

		// 订单费用信息
		OrdOdFeeTotalCriteria ordOdFeeNumExample = new OrdOdFeeTotalCriteria();
		OrdOdFeeTotalCriteria.Criteria ordOdFeeNumCriteria = ordOdFeeNumExample.createCriteria();
		ordOdFeeNumCriteria.andTenantIdEqualTo(request.getOrdOdFeeTotalVo().getTenantId());
		ordOdFeeNumCriteria.andOrderIdEqualTo(request.getOrdOdFeeTotalVo().getOrderId());
		int ordOdFeeNum = ordOdFeeTotalAtomSV.countByExample(ordOdFeeNumExample);
		OrdOdFeeTotal ordOdFeeTotal = new OrdOdFeeTotal();
		BeanUtils.copyProperties(request.getOrdOdFeeTotalVo(), ordOdFeeTotal);
		if (ordOdFeeNum == 0) {
			ordOdFeeTotalAtomSV.insertSelective(ordOdFeeTotal);
		} else {
			ordOdFeeTotalAtomSV.updateByExampleSelective(ordOdFeeTotal, ordOdFeeNumExample);
		}

		// 订单出货信息
		OrdOdLogisticsCriteria ordOdLogisticsExample = new OrdOdLogisticsCriteria();
		OrdOdLogisticsCriteria.Criteria criteria = ordOdLogisticsExample.createCriteria();
		criteria.andTenantIdEqualTo(request.getOrdOdLogisticsVo().getTenantId());
		criteria.andOrderIdEqualTo(request.getOrdOdLogisticsVo().getOrderId());
		List<OrdOdLogistics> list = ordOdLogisticsAtomSV.selectByExample(ordOdLogisticsExample);
		OrdOdLogistics ordOdLogistics = new OrdOdLogistics();
		BeanUtils.copyProperties(request.getOrdOdLogisticsVo(), ordOdLogistics);
		ordOdLogistics.setLogisticsId(SequenceUtil.genLogisticsId());
		if (list.isEmpty()) {
			ordOdLogisticsAtomSV.insertSelective(ordOdLogistics);
		} else {
			ordOdLogistics.setLogisticsId(list.get(0).getLogisticsId());
			ordOdLogisticsAtomSV.updateByExampleSelective(ordOdLogistics, ordOdLogisticsExample);
		}

	}
	
	//保存订单商品信息
	@Override
	public void insertOrdOdProdOfc(OrdOdProdVo request) throws BusinessException, SystemException {
		if (StringUtil.isBlank(request.getTenantId())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "租户Id不能为空");
		}
		if (StringUtil.isBlank(request.getOrderId() + "")) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "订单Id不能为空");
		}
		if (StringUtil.isBlank(request.getProdCode())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "商品编码不能为空");
		}
		OrdOdProdCriteria example = new OrdOdProdCriteria();
		OrdOdProdCriteria.Criteria criteria = example.createCriteria();
		criteria.andTenantIdEqualTo(request.getTenantId());
		criteria.andOrderIdEqualTo(request.getOrderId());
		criteria.andProdCodeEqualTo(request.getProdCode());
		List<OrdOdProd> list = ordOdProdAtomSV.selectByExample(example);
		OrdOdProd ordOdProd = new OrdOdProd();
		BeanUtils.copyProperties(request, ordOdProd);
		ordOdProd.setProdDetalId(SequenceUtil.createProdDetailId());
		LOG.info("++++++++++++++++++请求数据+++++++++++++++" + JSON.toJSONString(ordOdProd));
		if (list.isEmpty()) {
			try {
				ordOdProdAtomSV.insertSelective(ordOdProd);
			} catch (Exception e) {
			}
		}
	}
	
	//解析ofcCode
	@Override
	public String parseOfcCode(OfcCodeRequst request) throws BusinessException, SystemException {
		OrdParamCriteria example = new OrdParamCriteria();
		OrdParamCriteria.Criteria criteria = example.createCriteria();
		criteria.andTenantIdEqualTo(request.getTenantId());
		criteria.andSystemIdEqualTo(request.getSystemId());
		criteria.andOutCodeEqualTo(request.getOutCode().trim());
		criteria.andParamCodeEqualTo(request.getParamCode());
		List<OrdParam> list = ordParamAtomSV.selectByExample(example);
		if (!list.isEmpty()) {
			return list.get(0).getCode();
		}
		return null;
	}
	
	//匹配订单Id
	@Override
	public long parseOrderId(String downstreamOrderId) throws BusinessException, SystemException {
		OrdOrderCriteria example = new OrdOrderCriteria();
		OrdOrderCriteria.Criteria criteria = example.createCriteria();
		criteria.andTenantIdEqualTo(OrdersConstants.TENANT_ID);
		criteria.andDownstreamOrderIdEqualTo(downstreamOrderId);
		List<OrdOrder>  list = ordOrderAtomSV.selectByExample(example);
		long orderId = 0;
		if(!CollectionUtil.isEmpty(list)){
			orderId = list.get(0).getOrderId();
		}
		return orderId;
	}

}
