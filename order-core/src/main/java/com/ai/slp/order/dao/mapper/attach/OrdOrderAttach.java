package com.ai.slp.order.dao.mapper.attach;

import java.sql.Timestamp;

import com.ai.opt.base.vo.BaseInfo;

/**
 * 订单扩展类
 * @author caofz
 *
 */
public class OrdOrderAttach extends BaseInfo{
	
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
     * 支付方式
     */
    private String payStyle;

    /**
     * 支付时间
     */
    private Timestamp payTime;

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
     * 总实收费用
     */
    private Long paidFee;

    /**
     * 总待收费用
     */
    private Long payFee;

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

	public String getPayStyle() {
		return payStyle;
	}

	public void setPayStyle(String payStyle) {
		this.payStyle = payStyle;
	}

	public Timestamp getPayTime() {
		return payTime;
	}

	public void setPayTime(Timestamp payTime) {
		this.payTime = payTime;
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

	public Long getPaidFee() {
		return paidFee;
	}

	public void setPaidFee(Long paidFee) {
		this.paidFee = paidFee;
	}

	public Long getPayFee() {
		return payFee;
	}

	public void setPayFee(Long payFee) {
		this.payFee = payFee;
	}

}
