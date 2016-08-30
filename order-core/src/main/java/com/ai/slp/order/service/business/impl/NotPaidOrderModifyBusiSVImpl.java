package com.ai.slp.order.service.business.impl;

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
import com.ai.slp.order.api.ordermodify.param.OrderModifyRequest;
import com.ai.slp.order.dao.mapper.bo.OrdOdFeeTotal;
import com.ai.slp.order.dao.mapper.bo.OrdOdProd;
import com.ai.slp.order.dao.mapper.bo.OrdOdProdCriteria;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.service.atom.interfaces.IOrdOdFeeTotalAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdProdAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.ai.slp.order.service.business.interfaces.INotPaidOrderModifyBusiSV;
import com.ai.slp.order.util.CommonCheckUtils;

@Service
@Transactional
public class NotPaidOrderModifyBusiSVImpl implements INotPaidOrderModifyBusiSV {
	
	private static final Logger logger=LoggerFactory.getLogger(NotPaidOrderModifyBusiSVImpl.class);
	
	@Autowired
	private IOrdOdFeeTotalAtomSV ordOdFeeTotalAtomSV;
	
	@Autowired
	private IOrdOrderAtomSV ordOrderAtomSV;
	
	@Autowired
	private IOrdOdProdAtomSV ordOdProdAtomSV;
	
	@Override
	public void modify(OrderModifyRequest request) throws BusinessException, SystemException {
		/* 1.检验参数*/
		if(request==null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "参数不能为空");
		}
		if(request.getOrderId()==0) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "订单id不能为空");
		}
		if(request.getAdjustFee()<=0) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "未支付订单修改金额必须大于0");
		}
		CommonCheckUtils.checkTenantId(request.getTenantId(), "");
		/* 2.修改金额和备注*/
		OrdOdFeeTotal odFeeTotal = ordOdFeeTotalAtomSV.selectByOrderId(request.getTenantId(), request.getOrderId());
		if(odFeeTotal==null) {
			logger.warn("未能查询到指定的订单费用总表信息[订单id:"+request.getOrderId()+"]");
			throw new BusinessException("","未能查询到指定的订单费用总表信息[订单id:"+request.getOrderId()+"]");
		}
		/* 3.查询订单商品明细信息*/
		OrdOdProdCriteria example=new OrdOdProdCriteria();
		OrdOdProdCriteria.Criteria criteria = example.createCriteria();
		criteria.andTenantIdEqualTo(request.getTenantId());
		criteria.andOrderIdEqualTo(request.getOrderId());
		List<OrdOdProd> ordOdProdList = ordOdProdAtomSV.selectByExample(example);
		if(CollectionUtil.isEmpty(ordOdProdList)) {
			logger.warn("未能查询到指定的订单商品明细表信息[订单id:"+request.getOrderId()+"]");
			throw new BusinessException("","未能查询到指定的订单商品明细表信息[订单id:"+request.getOrderId()+"]");
		}
		long subValue=odFeeTotal.getAdjustFee()-request.getAdjustFee();
		if(subValue>=0) { //降价
			for (OrdOdProd ordOdProd : ordOdProdList) {
				ordOdProd.setAdjustFee(ordOdProd.getAdjustFee()-subValue/ordOdProdList.size());
				ordOdProdAtomSV.updateById(ordOdProd);
			}
		}else {  //涨价
			for (OrdOdProd ordOdProd : ordOdProdList) {
				ordOdProd.setAdjustFee(ordOdProd.getAdjustFee()+(-subValue)/ordOdProdList.size());
				ordOdProdAtomSV.updateById(ordOdProd);
			}
		}
		OrdOrder order = ordOrderAtomSV.selectByOrderId(request.getTenantId(), request.getOrderId());
		if(order==null) {
			logger.warn("未能查询到指定的订单表信息[订单id:"+request.getOrderId()+"]");
			throw new BusinessException("","未能查询到指定的订单表信息[订单id:"+request.getOrderId()+"]");
		}
		odFeeTotal.setAdjustFee(request.getAdjustFee());
		order.setRemark(request.getRemark());
		/* 3.修改金额和备注信息*/
		ordOdFeeTotalAtomSV.updateByOrderId(odFeeTotal);
		ordOrderAtomSV.updateById(order);
	}
}
