package com.ai.slp.order.api.orderlist.param;

import java.sql.Timestamp;
import java.util.List;

import com.ai.opt.base.vo.BaseInfo;

/**
 * 订单表 Date: 2016年5月3日 <br>
 * Copyright (c) 2016 asiainfo.com <br>
 * 
 * @author zhangqiang7
 */
public class OrdOrderParams extends BaseInfo {

    private static final long serialVersionUID = 1L;

    /**
     * 业务订单ID
     */
    private Long orderId;

    /**
     * 业务类型
     */
    private String busiCode;

    /**
     * 订单类型
     */
    private String orderType;

    /**
     * 订单状态(后厂)
     */
    private String state;

    /**
     * 下单时间
     */
    private Timestamp orderTime;
    
    /**
     * 总费用
     */
    private Long totalFee;
    
    /**
     * 总优惠金额
     */
    private Long discountFee;
    
    /**
     * 总应收费用
     */
    private Long adjustFee;
    
    /**
     * 商品集合
     */
    private List<OrdProductVo> productList;
    
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getBusiCode() {
        return busiCode;
    }

    public void setBusiCode(String busiCode) {
        this.busiCode = busiCode;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Timestamp getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Timestamp orderTime) {
		this.orderTime = orderTime;
	}

	public Long getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Long totalFee) {
		this.totalFee = totalFee;
	}

	public Long getDiscountFee() {
		return discountFee;
	}

	public void setDiscountFee(Long discountFee) {
		this.discountFee = discountFee;
	}

	public Long getAdjustFee() {
		return adjustFee;
	}

	public void setAdjustFee(Long adjustFee) {
		this.adjustFee = adjustFee;
	}

	public List<OrdProductVo> getProductList() {
		return productList;
	}

	public void setProductList(List<OrdProductVo> productList) {
		this.productList = productList;
	}
}
