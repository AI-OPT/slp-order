package com.ai.slp.order.api.orderlist.param;

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
     * 订单来源 (受理渠道)
     */
    private String chlId;
    
    /**
     * 父订单id
     */
    private Long pOrderId;
    
    /**
     * 买家帐号 (userid)
     */
    private String userId;
    
    /**
     * 绑定手机号 (用户相关)
     */
    private String userTel;
    
    /**
     * 是否需要物流
     */
    private String deliveryFlag;
    
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
     * 积分
     */
    private Long points;
    
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

	public Long getpOrderId() {
		return pOrderId;
	}

	public void setpOrderId(Long pOrderId) {
		this.pOrderId = pOrderId;
	}

	public String getUserTel() {
		return userTel;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
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
	
	public Long getPoints() {
		return points;
	}

	public void setPoints(Long points) {
		this.points = points;
	}

	public List<BehindOrdProductVo> getProductList() {
		return productList;
	}

	public void setProductList(List<BehindOrdProductVo> productList) {
		this.productList = productList;
	}
}
