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
import com.ai.opt.sdk.util.BeanUtils;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.util.DateUtil;
import com.ai.slp.order.api.aftersaleorder.impl.OrderAfterSaleSVImpl;
import com.ai.slp.order.api.aftersaleorder.param.OrderReturnRequest;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.constants.OrdersConstants.OrdOdStateChg;
import com.ai.slp.order.dao.mapper.bo.OrdOdProd;
import com.ai.slp.order.dao.mapper.bo.OrdOdProdCriteria;
import com.ai.slp.order.dao.mapper.bo.OrdOdProdCriteria.Criteria;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.service.atom.interfaces.IOrdOdProdAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.ai.slp.order.service.business.interfaces.IOrderAfterSaleBusiSV;
import com.ai.slp.order.service.business.interfaces.IOrderFrameCoreSV;
import com.ai.slp.order.util.CommonCheckUtils;
import com.ai.slp.order.util.SequenceUtil;

@Service
@Transactional
public class OrderAfterSaleBusiSVImpl implements IOrderAfterSaleBusiSV {
	
	private static final Logger logger=LoggerFactory.getLogger(OrderAfterSaleSVImpl.class);
	
	@Autowired
	private IOrdOrderAtomSV ordOrderAtomSV;
	
	@Autowired
	private IOrdOdProdAtomSV ordOdProdAtomSV;
	
	@Autowired
	private IOrderFrameCoreSV orderFrameCoreSV;

	@Override
	public void back(OrderReturnRequest request) throws BusinessException, SystemException {
		/* 1.参数校验及返回子订单下的商品信息*/
		List<OrdOdProd> ordOdProds = this.checkParams(request);
		/* 2.查询该商品的子订单*/
		OrdOrder order = this.getOrdOrder(request);
		/* 3.商品明细信息查询*/
		OrdOdProd ordOdProd = this.getOrdOdProd(request);
		if(!ordOdProds.contains(ordOdProd)) {
			throw new BusinessException("", 
					"商品与订单内容不一致,[订单id:"+request.getOrderId()+""
							+ ",商品明细id:"+request.getProdDetalId()+"]");
		}
		/* 4.生成退货订单*/
		OrdOrder backOrder=new OrdOrder();
		BeanUtils.copyProperties(backOrder, order);
		String orgState=order.getState();
		long backOrderId=SequenceUtil.createOrderId();
		Timestamp sysDate = DateUtil.getSysDate();
		backOrder.setBusiCode(OrdersConstants.OrdOrder.BusiCode.UNSUBSCRIBE_ORDER); //退货单
		backOrder.setOrderId(backOrderId); //退货单id
		backOrder.setOperId("");//TODO 受理工号 ??
		backOrder.setState(OrdersConstants.OrdOrder.State.REVOKE_WAIT_AUDIT);
		backOrder.setOrigOrderId(order.getOrderId());
		ordOrderAtomSV.insertSelective(backOrder);
		orderFrameCoreSV.ordOdStateChg(backOrder.getOrderId(), backOrder.getTenantId(), orgState, 
				OrdersConstants.OrdOrder.State.REVOKE_WAIT_AUDIT,
                OrdOdStateChg.ChgDesc.ORDER_TO_AUDIT, backOrder.getOperId(), null, null,sysDate);
		/* 5.生成退货商品明细信息*/
		OrdOdProd backOrdOdProd =new OrdOdProd();
		BeanUtils.copyProperties(backOrdOdProd, ordOdProd);
		backOrdOdProd.setState(OrdersConstants.OrdOdProd.State.RETURN);
		backOrdOdProd.setUpdateTime(DateUtil.getSysDate());
		backOrdOdProd.setProdDetalId(SequenceUtil.createProdDetailId());
		backOrdOdProd.setOrderId(backOrderId);
		backOrdOdProd.setUpdateOperId("");//TODO 变更工号 ?
		ordOdProdAtomSV.insertSelective(backOrdOdProd);
	}

	@Override
	public void exchange(OrderReturnRequest request) throws BusinessException, SystemException {
		/* 1.参数校验及返回子订单下的商品信息*/
		List<OrdOdProd> ordOdProds = this.checkParams(request);
		/* 2.查询该商品的子订单*/
		OrdOrder order = this.getOrdOrder(request);
		/* 3.商品明细信息查询*/
		OrdOdProd ordOdProd = this.getOrdOdProd(request);
		if(!ordOdProds.contains(ordOdProd)) {
			throw new BusinessException("", 
					"商品与订单内容不一致,[订单id:"+request.getOrderId()+""
							+ ",商品明细id:"+request.getProdDetalId()+"]");
		}
		/* 4.生成换货订单*/
		OrdOrder exOrder=new OrdOrder();
		BeanUtils.copyProperties(exOrder, order);
		String orgState=order.getState();
		long exOrderId=SequenceUtil.createOrderId();
		Timestamp sysDate = DateUtil.getSysDate();
		exOrder.setBusiCode(OrdersConstants.OrdOrder.BusiCode.EXCHANGE_ORDER); //换货单
		exOrder.setOrderId(exOrderId); //换货单id
		exOrder.setOperId("");//TODO 受理工号 ??
		exOrder.setOrigOrderId(order.getOrderId());
		ordOrderAtomSV.insertSelective(exOrder);
		orderFrameCoreSV.ordOdStateChg(exOrder.getOrderId(), exOrder.getTenantId(), orgState, 
				OrdersConstants.OrdOrder.State.REVOKE_WAIT_AUDIT,
                OrdOdStateChg.ChgDesc.ORDER_TO_AUDIT, exOrder.getOperId(), null, null,sysDate);
		/* 5.生成换货商品明细信息*/
		OrdOdProd backOrdOdProd =new OrdOdProd();
		BeanUtils.copyProperties(backOrdOdProd, ordOdProd);
		backOrdOdProd.setState(OrdersConstants.OrdOdProd.State.EXCHANGE);
		backOrdOdProd.setUpdateTime(DateUtil.getSysDate());
		backOrdOdProd.setProdDetalId(SequenceUtil.createProdDetailId());
		backOrdOdProd.setOrderId(exOrderId);
		backOrdOdProd.setUpdateOperId("");//TODO 变更工号 ?
		ordOdProdAtomSV.insertSelective(backOrdOdProd);
	}

	@Override
	public void refund(OrderReturnRequest request) throws BusinessException, SystemException {
		/* 1.参数校验及返回子订单下的商品信息*/
		List<OrdOdProd> ordOdProds = this.checkParams(request);
		/* 2.查询该商品的子订单*/
		OrdOrder order = this.getOrdOrder(request);
		/* 3.商品明细信息查询*/
		OrdOdProd ordOdProd = this.getOrdOdProd(request);
		if(!ordOdProds.contains(ordOdProd)) {
			throw new BusinessException("", 
					"商品与订单内容不一致,[订单id:"+request.getOrderId()+""
							+ ",商品明细id:"+request.getProdDetalId()+"]");
		}
		/* 4.生成退款订单信息*/
		OrdOrder rdOrder=new OrdOrder();
		BeanUtils.copyProperties(rdOrder, order);
		String orgState=order.getState();
		long rdOrderId=SequenceUtil.createOrderId();
		Timestamp sysDate = DateUtil.getSysDate();
		rdOrder.setBusiCode(OrdersConstants.OrdOrder.BusiCode.CANCEL_ORDER); //退款单
		rdOrder.setOrderId(rdOrderId); //退款单id
		rdOrder.setOperId("");//TODO 受理工号 ??
		rdOrder.setOrigOrderId(order.getOrderId());
		ordOrderAtomSV.insertSelective(rdOrder);
		orderFrameCoreSV.ordOdStateChg(rdOrder.getOrderId(), rdOrder.getTenantId(), orgState, 
				OrdersConstants.OrdOrder.State.REVOKE_WAIT_AUDIT,
                OrdOdStateChg.ChgDesc.ORDER_TO_AUDIT, rdOrder.getOperId(), null, null,sysDate);
		/* 5.生成退款商品明细信息*/
		OrdOdProd backOrdOdProd =new OrdOdProd();
		BeanUtils.copyProperties(backOrdOdProd, ordOdProd);
		backOrdOdProd.setState(OrdersConstants.OrdOdProd.State.RETURN);
		backOrdOdProd.setUpdateTime(DateUtil.getSysDate());
		backOrdOdProd.setProdDetalId(SequenceUtil.createProdDetailId());
		backOrdOdProd.setOrderId(rdOrderId);
		backOrdOdProd.setUpdateOperId("");//TODO 变更工号 ?
		
		ordOdProdAtomSV.insertSelective(backOrdOdProd);
	}
	
	
	/**
	 * 参数校验及获取子订单下的商品信息
	 */
	private List<OrdOdProd> checkParams(OrderReturnRequest request) {
		/* 租户非空校验*/
		CommonCheckUtils.checkTenantId(request.getTenantId(), ExceptCodeConstants.Special.PARAM_IS_NULL);
		/* 订单id非空检验*/
		if(request.getOrderId()==0) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "订单id不能为空");
		}
		/* 商户明细id非空校验*/
		if(request.getProdDetalId()==0) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "商品明细id不能为空");
		}
		OrdOdProdCriteria example=new OrdOdProdCriteria();
		Criteria criteria = example.createCriteria();
		criteria.andTenantIdEqualTo(request.getTenantId());
		criteria.andOrderIdEqualTo(request.getOrderId());
		List<OrdOdProd> ordOdProds = ordOdProdAtomSV.selectByExample(example);
		if(CollectionUtil.isEmpty(ordOdProds)) {
			logger.error("未能查询到指定的订单商品明细信息[订单id:"+request.getOrderId()+"]");
			throw new BusinessException(ExceptCodeConstants.Special.NO_RESULT, 
					"未能查询到指定的订单商品明细信息[订单id:"+request.getOrderId()+"]");
		}
		return ordOdProds;
	}
	
	
	/**
	 * 订单信息
	 */
	private OrdOrder getOrdOrder(OrderReturnRequest request) {
		OrdOrder order = ordOrderAtomSV.selectByOrderId(request.getTenantId(), request.getOrderId());
		if(order==null) {
			logger.error("订单信息不存在[订单id:"+request.getOrderId()+"]");
			throw new BusinessException(ExceptCodeConstants.Special.NO_RESULT, 
					"订单信息不存在[订单id:"+request.getOrderId()+"]");
		}
		return order;
	}
	
	/**
	 * 订单商品信息
	 */
	private OrdOdProd getOrdOdProd(OrderReturnRequest request) {
		OrdOdProd ordOdProd = ordOdProdAtomSV.selectByPrimaryKey(request.getProdDetalId());
		if(ordOdProd==null) {
			logger.error("订单商品明细不存在[订单id:"+request.getProdDetalId()+"]");
			throw new BusinessException(ExceptCodeConstants.Special.NO_RESULT, 
					"订单商品明细不存在[订单id:"+request.getProdDetalId()+"]");
		}
		return ordOdProd;
	}
	
}
