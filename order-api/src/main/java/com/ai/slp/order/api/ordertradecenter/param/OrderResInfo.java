package com.ai.slp.order.api.ordertradecenter.param;

import java.io.Serializable;
import java.util.List;

public class OrderResInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private long orderId;

	private List<OrdProductResInfo> ordProductResList;

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public List<OrdProductResInfo> getOrdProductResList() {
		return ordProductResList;
	}

	public void setOrdProductResList(List<OrdProductResInfo> ordProductResList) {
		this.ordProductResList = ordProductResList;
	}
	
	
}
