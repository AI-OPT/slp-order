package com.ai.slp.order.api.orderlist.param;

import com.ai.opt.base.vo.BaseInfo;

public class BehindOrdProductVo extends BaseInfo{

	private static final long serialVersionUID = 1L;
	
    /**
     * 业务订单ID
     */
    private Long orderId;
    

    /**
     * 订单状态(后厂)
     */
    private String state;

    /**
     * 订单状态展示
     */
    private String stateName;
	
    /**
     * 商品名称
     */
    private String prodName;
    
    /**
     * 购买数量
     */
    private Long buySum;
    
	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public Long getBuySum() {
		return buySum;
	}

	public void setBuySum(Long buySum) {
		this.buySum = buySum;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	
}
