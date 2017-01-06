package com.ai.slp.order.service.business.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.sdk.util.DateUtil;
import com.ai.slp.order.api.synchronize.params.OrderSynchronizeVo;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.dao.mapper.bo.OrdBalacneIf;
import com.ai.slp.order.dao.mapper.bo.OrdOdFeeTotal;
import com.ai.slp.order.dao.mapper.bo.OrdOdInvoice;
import com.ai.slp.order.dao.mapper.bo.OrdOdLogistics;
import com.ai.slp.order.dao.mapper.bo.OrdOdProd;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.service.atom.interfaces.ISyncronizeAtomSV;
import com.ai.slp.order.service.business.interfaces.ISyncronizeBusiSV;
import com.ai.slp.order.util.SequenceUtil;
import com.ai.slp.order.util.ValidateUtils;
import com.alibaba.fastjson.JSON;
import com.esotericsoftware.minlog.Log;

@Service
@Transactional
public class ISyncronizeBusiSVImpl implements ISyncronizeBusiSV {

	@Autowired
	private ISyncronizeAtomSV syncronizeAtomSV;

	@Override
	public int orderSynchronize(OrderSynchronizeVo request) throws BusinessException, SystemException {
		ValidateUtils.validateSynchronize(request);
		OrdOrder ordOrder = new OrdOrder();
		OrdOdProd ordOdProd = new OrdOdProd();
		OrdOdLogistics ordOdLogistics = new OrdOdLogistics();
		OrdOdInvoice ordOdInvoice = new OrdOdInvoice();
		OrdOdFeeTotal ordOdFeeTotal = new OrdOdFeeTotal();
		OrdBalacneIf ordBalacneIf = new OrdBalacneIf();
		try {
			if (request.getOrdOrderVo() != null) {
				BeanUtils.copyProperties(request.getOrdOrderVo(), ordOrder);
				ordOrder.setTenantId(OrdersConstants.TENANT_ID);
				// 长虹侧无子订单的概念
				ordOrder.setSubFlag(OrdersConstants.OrdOrder.SubFlag.NO);
				// 状态变化时间
				ordOrder.setStateChgTime(DateUtil.getSysDate());
				// 客户端显示状态
				ordOrder.setDisplayFlag(OrdersConstants.OrdOrder.DisplayFlag.USER_NORMAL_VISIABLE);
				ordOrder.setDisplayFlagChgTime(DateUtil.getSysDate());
				syncronizeAtomSV.insertSelective(ordOrder);
			}
			if (request.getOrdOdProdVo() != null) {
				BeanUtils.copyProperties(request.getOrdOdProdVo(), ordOdProd);
				// 订单商品主键
				long prodDetailId = SequenceUtil.createProdDetailId();
				ordOdProd.setTenantId(OrdersConstants.TENANT_ID);
				// 标准品id
				ordOdProd.setStandardProdId(OrdersConstants.OrdOdProd.StandProdId.STAND_PROD_ID);
				ordOdProd.setState(OrdersConstants.OrdOdProd.State.SELL);
				ordOdProd.setProdDetalId(prodDetailId);
				ordOdProd.setProdType(OrdersConstants.OrdOdProd.ProdType.PROD);
				ordOdProd.setValidTime(DateUtil.getSysDate());
				ordOdProd.setUpdateTime(DateUtil.getSysDate());
				syncronizeAtomSV.insertSelective(ordOdProd);
			}
			if (request.getOrdOdLogisticVo() != null) {
				BeanUtils.copyProperties(request.getOrdOdLogisticVo(), ordOdLogistics);
				// 订单物流主键
				ordOdLogistics.setLogisticsId(SequenceUtil.genLogisticsId());
				ordOdLogistics.setTenantId(OrdersConstants.TENANT_ID);
				syncronizeAtomSV.insertSelective(ordOdLogistics);
			}

			if (request.getOrdOdInvoiceVo() != null) {
				BeanUtils.copyProperties(request.getOrdOdInvoiceVo(), ordOdInvoice);
				ordOdInvoice.setTenantId(OrdersConstants.TENANT_ID);
				syncronizeAtomSV.insertSelective(ordOdInvoice);
			}

			if (request.getOrdOdFeeTotalVo() != null) {
				BeanUtils.copyProperties(request.getOrdOdFeeTotalVo(), ordOdFeeTotal);
				// 收退费标识
				ordOdFeeTotal.setTenantId(OrdersConstants.TENANT_ID);
				ordOdFeeTotal.setPayFlag(OrdersConstants.OrdOdFeeTotal.payFlag.IN);
				ordOdFeeTotal.setUpdateTime(DateUtil.getSysDate());
				syncronizeAtomSV.insertSelective(ordOdFeeTotal);
			}

			if (request.getOrdBalanceIfVo() != null) {
				BeanUtils.copyProperties(request.getOrdBalanceIfVo(), ordBalacneIf);
				// 订单支付主键
				ordBalacneIf.setBalacneIfId(SequenceUtil.createBalacneIfId());
				ordBalacneIf.setTenantId(OrdersConstants.TENANT_ID);
				ordBalacneIf.setCreateTime(DateUtil.getSysDate());
				syncronizeAtomSV.insertSelective(ordBalacneIf);
			}

		} catch (Exception e) {
			Log.error("同步订单数据失败:syncronize fail:" + DateUtil.getSysDate() + ":原因:" + JSON.toJSONString(e));
			throw new BusinessException("同步失败");
		}
		return 0;
	}

}
