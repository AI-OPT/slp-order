package com.ai.slp.order.api.orderlist.param;

import java.sql.Timestamp;
import java.util.List;

import com.ai.opt.base.vo.BaseInfo;

/**
 * 订单相关信息 Date: 2016年8月25日 <br>
 * Copyright (c) 2016 asiainfo.com <br>
 * 
 * @author caofz
 */
public class BehindOrdOrderVo extends BaseInfo {

    private static final long serialVersionUID = 1L;
    
    /**
     * 订单来源  受理渠道
     */
    private String chlId;
    
    /**
     * 父订单id
     */
    private Long parentOrderId;
    
    /**
     * 买家帐号      userid
     */
    private String userId;
    
    /**
     * 绑定手机号 ??
     */
    
    
    /**
     * 是否需要物流
     */
    private String deliveryFlag;
    
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
     * 下单时间
     */
    private Timestamp orderTime;


    /**
     * 总优惠金额
     */
    private Long discountFee;


    /**
     * 总实收费用
     */
    private Long adjustFee;

    
    /**
     * 收件人电话
     */
    private String contactTel;
    

    /**
     * 支付信息
     */
    private List<OrderPayVo> payDataList;

    /**
     * 商品集合
     */
    private List<BehindOrdProductVo> productList;

	public String getChlId() {
		return chlId;
	}

	public void setChlId(String chlId) {
		this.chlId = chlId;
	}

	public Long getParentOrderId() {
		return parentOrderId;
	}

	public void setParentOrderId(Long parentOrderId) {
		this.parentOrderId = parentOrderId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDeliveryFlag() {
		return deliveryFlag;
	}

	public void setDeliveryFlag(String deliveryFlag) {
		this.deliveryFlag = deliveryFlag;
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

	public Timestamp getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Timestamp orderTime) {
		this.orderTime = orderTime;
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

	public String getContactTel() {
		return contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}

	public List<OrderPayVo> getPayDataList() {
		return payDataList;
	}

	public void setPayDataList(List<OrderPayVo> payDataList) {
		this.payDataList = payDataList;
	}

	public List<BehindOrdProductVo> getProductList() {
		return productList;
	}

	public void setProductList(List<BehindOrdProductVo> productList) {
		this.productList = productList;
	}

}
