package com.ai.slp.order.vo;

import java.io.Serializable;
import java.util.List;

public class OFCAfterSaleOrderCreateRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 申请单信息
	 */
	private OrderAfterSaleApplyVo orderAfterSaleApplyVo;
	
	/**
	 * 申请单产品明细
	 */
	private List<OrderAfterSaleApplyItemsVo> OrderAfterSaleApplyItemsVoList;

	public OrderAfterSaleApplyVo getOrderAfterSaleApplyVo() {
		return orderAfterSaleApplyVo;
	}

	public void setOrderAfterSaleApplyVo(OrderAfterSaleApplyVo orderAfterSaleApplyVo) {
		this.orderAfterSaleApplyVo = orderAfterSaleApplyVo;
	}

	public List<OrderAfterSaleApplyItemsVo> getOrderAfterSaleApplyItemsVoList() {
		return OrderAfterSaleApplyItemsVoList;
	}

	public void setOrderAfterSaleApplyItemsVoList(List<OrderAfterSaleApplyItemsVo> orderAfterSaleApplyItemsVoList) {
		OrderAfterSaleApplyItemsVoList = orderAfterSaleApplyItemsVoList;
	}

}
