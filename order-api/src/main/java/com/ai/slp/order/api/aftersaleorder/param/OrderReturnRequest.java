package com.ai.slp.order.api.aftersaleorder.param;

import com.ai.opt.base.vo.BaseInfo;

public class OrderReturnRequest extends BaseInfo{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 订单id
	 */
	private Long orderId;
	
	/**
	 * 商品明细id
	 */
	private Long prodDetalId;
	
	/**
	 * 商品数量
	 */
	private long prodSum;
	
	/**
	 * 受理工号
	 */
	private String operId;
	
	/**
	 * 退货 换货 退款理由
	 */
	private String afterSaleReason;
	
	/**
	 * 图片id
	 */
	private String imageId;
	
	/**
	 * 图片类型
	 */
	private String imageType;
	
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getProdDetalId() {
		return prodDetalId;
	}

	public void setProdDetalId(Long prodDetalId) {
		this.prodDetalId = prodDetalId;
	}

	public String getOperId() {
		return operId;
	}

	public void setOperId(String operId) {
		this.operId = operId;
	}

	public long getProdSum() {
		return prodSum;
	}

	public void setProdSum(long prodSum) {
		this.prodSum = prodSum;
	}

	public String getAfterSaleReason() {
		return afterSaleReason;
	}

	public void setAfterSaleReason(String afterSaleReason) {
		this.afterSaleReason = afterSaleReason;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}
}
