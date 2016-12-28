package com.ai.slp.order.api.orderlist.param;

import java.util.List;


import com.ai.opt.base.vo.BaseInfo;

public class BehindQueryOrderListRequest extends BaseInfo{

	private static final long serialVersionUID = 1L;
	
	 /**
     * 用户名称
     */
    private String userName;

    /**
     * 订单号
     */
    private Long orderId;

    /**
     * 订单来源(受理渠道)
     */
    private String chlId;
    
    /**
     * 是否需要物流
     */
    private String deliveryFlag;
    
    /**
     * 仓库
     */
    private String routeId;
    
    /**
     * 收货人手机号
     */
    private String contactTel;

    /**
     * 订单状态
     */
    private List<String> stateList;
    
    /**
     * 订单生成时间开始
     */
    private String orderTimeBegin;

    /**
     * 订单生成时间结束
     */
    private String orderTimeEnd;

    /**
     * pageNo
     */
    private Integer pageNo;

    /**
     * pageSize
     */
    private Integer pageSize;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}

	public String getContactTel() {
		return contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getChlId() {
		return chlId;
	}

	public void setChlId(String chlId) {
		this.chlId = chlId;
	}

	public String getDeliveryFlag() {
		return deliveryFlag;
	}

	public void setDeliveryFlag(String deliveryFlag) {
		this.deliveryFlag = deliveryFlag;
	}

	public List<String> getStateList() {
		return stateList;
	}

	public void setStateList(List<String> stateList) {
		this.stateList = stateList;
	}

	public String getOrderTimeBegin() {
		return orderTimeBegin;
	}

	public void setOrderTimeBegin(String orderTimeBegin) {
		this.orderTimeBegin = orderTimeBegin;
	}

	public String getOrderTimeEnd() {
		return orderTimeEnd;
	}

	public void setOrderTimeEnd(String orderTimeEnd) {
		this.orderTimeEnd = orderTimeEnd;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

}
