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
import com.ai.paas.ipaas.search.vo.Result;
import com.ai.paas.ipaas.search.vo.SearchCriteria;
import com.ai.slp.order.api.orderpricemodify.param.OrderModifyRequest;
import com.ai.slp.order.constants.SearchConstants;
import com.ai.slp.order.dao.mapper.bo.OrdOdFeeTotal;
import com.ai.slp.order.manager.ESClientManager;
import com.ai.slp.order.search.bo.OrdProdExtend;
import com.ai.slp.order.search.bo.OrderInfo;
import com.ai.slp.order.search.dto.SearchCriteriaStructure;
import com.ai.slp.order.service.atom.interfaces.IOrdOdFeeTotalAtomSV;
import com.ai.slp.order.service.business.impl.search.OrderSearchImpl;
import com.ai.slp.order.service.business.interfaces.INotPaidOrderModifyBusiSV;
import com.ai.slp.order.service.business.interfaces.search.IOrderSearch;

@Service
@Transactional
public class NotPaidOrderModifyBusiSVImpl implements INotPaidOrderModifyBusiSV {
	
	private static final Logger logger=LoggerFactory.getLogger(NotPaidOrderModifyBusiSVImpl.class);
	@Autowired
	private IOrdOdFeeTotalAtomSV ordOdFeeTotalAtomSV;
	
	/**
	 * 未支付订单金额修改
	 */
	@Override
	public void modify(OrderModifyRequest request) throws BusinessException, SystemException {
		/* 1.修改金额和备注*/
		OrdOdFeeTotal odFeeTotal = ordOdFeeTotalAtomSV.selectByOrderId(request.getTenantId(), 
					request.getOrderId());
		if(odFeeTotal==null) {
			logger.error("未能查询到指定的订单费用总表信息[订单id:"+request.getOrderId()+"]");
			throw new BusinessException(ExceptCodeConstants.Special.NO_RESULT,
					"未能查询到指定的订单费用总表信息[订单id:"+request.getOrderId()+"]");
		}
		long updateAmount = request.getUpdateAmount();
		if(updateAmount>odFeeTotal.getAdjustFee()) {
			throw new BusinessException("", "修改金额不能大于应付金额!");
		}
		if(updateAmount<odFeeTotal.getFreight()) {
			throw new BusinessException("", "修改金额不能小于运费!");
		}
		//总减免费用 
		long operDiscountFee=odFeeTotal.getAdjustFee()-updateAmount;
		odFeeTotal.setAdjustFee(updateAmount);
		odFeeTotal.setOperDiscountFee(odFeeTotal.getOperDiscountFee()+operDiscountFee);
		odFeeTotal.setOperDiscountDesc(request.getUpdateRemark());
		odFeeTotal.setDiscountFee(odFeeTotal.getDiscountFee()+operDiscountFee); //总优惠金额
		odFeeTotal.setUpdateOperId(request.getOperId());
		odFeeTotal.setPayFee(updateAmount);
		/* 2.修改金额和备注信息*/
		ordOdFeeTotalAtomSV.updateByOrderId(odFeeTotal);
		/* 3.修改es引擎数据*/
		this.refreshAfterData(odFeeTotal);
	}
	
	
	/**
	 * 刷新es引擎数据
	 * @param odFeeTotal
	 * @author caofz
	 * @ApiDocMethod
	 * @ApiCode 
	 * @RestRelativeURL
	 */
	private void  refreshAfterData(OrdOdFeeTotal odFeeTotal) {
		IOrderSearch orderSearch = new OrderSearchImpl();
		List<SearchCriteria> orderSearchCriteria = SearchCriteriaStructure.
				commonConditionsByOrderId(odFeeTotal.getOrderId());
		//查询es中符合的数据
		Result<OrderInfo> result = orderSearch.search(orderSearchCriteria, 0, 1, null);
		List<OrderInfo> ordList = result.getContents();
		if(CollectionUtil.isEmpty(ordList)) {
			logger.error("搜索引擎无数据! 父订单id为:"+odFeeTotal.getOrderId());
			throw new BusinessException("搜索引擎无数据! 父订单id为:"+odFeeTotal.getOrderId());
		}
		OrderInfo orderInfo = ordList.get(0);
		//修改金额(订单列表)
		orderInfo.setDiscountfee(odFeeTotal.getDiscountFee());
		orderInfo.setAdjustfee(odFeeTotal.getAdjustFee());
		//
		List<OrdProdExtend> ordextendes = orderInfo.getOrdextendes();
		if(!CollectionUtil.isEmpty(ordextendes)) {
			//订单详情修改
			OrdProdExtend ordProdExtend = ordextendes.get(0);
			ordProdExtend.setDiscountfee(odFeeTotal.getDiscountFee());
			ordProdExtend.setAdjustfee(odFeeTotal.getAdjustFee());
		}
		ESClientManager.getSesClient(SearchConstants.SearchNameSpace).bulkInsert(ordList);
	}
}
