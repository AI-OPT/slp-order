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
import com.ai.opt.sdk.util.StringUtil;
import com.ai.opt.sdk.util.UUIDUtil;
import com.ai.slp.order.api.ofc.params.OfcCodeRequst;
import com.ai.slp.order.api.ofc.params.OrdOdProdVo;
import com.ai.slp.order.api.ofc.params.OrderOfcVo;
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
			try {
				ordOrderAtomSV.insertSelective(ordOrder);
			} catch (Exception e) {
				ordOrderAtomSV.updateByExampleSelective(ordOrder, orderNumExample);
			}
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
			try {
				ordOdFeeTotalAtomSV.insertSelective(ordOdFeeTotal);
			} catch (Exception e) {
				ordOdFeeTotalAtomSV.updateByExampleSelective(ordOdFeeTotal, ordOdFeeNumExample);
			}
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
		if (list.isEmpty()) {
			try {
				ordOdLogistics.setLogisticsId(UUIDUtil.genShortId());
				ordOdLogisticsAtomSV.insertSelective(ordOdLogistics);
			} catch (Exception e) {
				List<OrdOdLogistics> reList = ordOdLogisticsAtomSV.selectByExample(ordOdLogisticsExample);
				if (!reList.isEmpty()) {
					ordOdLogistics.setLogisticsId(list.get(0).getLogisticsId());
					ordOdLogisticsAtomSV.updateByExampleSelective(ordOdLogistics, ordOdLogisticsExample);
				}
			}
		} else {
			ordOdLogisticsAtomSV.updateByExampleSelective(ordOdLogistics, ordOdLogisticsExample);
		}

	}

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
		LOG.info("++++++++++++++++++请求数据+++++++++++++++" + JSON.toJSONString(ordOdProd));
		if (list.isEmpty()) {
			try {
				ordOdProd.setProdDetalId(UUIDUtil.genShortId());
				ordOdProdAtomSV.insertSelective(ordOdProd);
			} catch (Exception e) {
				List<OrdOdProd> reList = ordOdProdAtomSV.selectByExample(example);
				if (!reList.isEmpty()) {
					ordOdProd.setProdDetalId(reList.get(0).getProdDetalId());
					ordOdProdAtomSV.updateByExampleSelective(ordOdProd, example);
				}
			}
		} else {
			long prodDetalId = list.get(0).getProdDetalId();
			ordOdProd.setProdDetalId(prodDetalId);
			ordOdProdAtomSV.updateByExampleSelective(ordOdProd, example);
		}
	}

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

}
