package com.ai.slp.order.api.orderlist.param;

import java.util.List;

import com.ai.opt.base.vo.BaseInfo;

public class BehindOrdOrderVo extends BaseInfo{

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
     * 商品SIZE
     */
    private int prodSize;
    
    
    private List<BehindOrdProductVo> productList;
    
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

	public List<BehindOrdProductVo> getProductList() {
		return productList;
	}

	public void setProductList(List<BehindOrdProductVo> productList) {
		this.productList = productList;
	}

	public int getProdSize() {
		return prodSize;
	}

	public void setProdSize(int prodSize) {
		this.prodSize = prodSize;
	}
}
