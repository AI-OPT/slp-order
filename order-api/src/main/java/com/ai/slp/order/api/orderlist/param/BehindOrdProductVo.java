package com.ai.slp.order.api.orderlist.param;

import com.ai.opt.base.vo.BaseInfo;

public class BehindOrdProductVo extends BaseInfo{

	private static final long serialVersionUID = 1L;
	
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
    
    
}
