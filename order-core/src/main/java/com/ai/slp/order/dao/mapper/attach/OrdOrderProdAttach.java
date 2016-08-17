package com.ai.slp.order.dao.mapper.attach;


/**
 * 订单提货单需要的信息
 * @date 2016年8月10日 
 * @author caofz
 */
public class OrdOrderProdAttach {
	
	 /**
	  * 订单id
	  */
	 private long orderId;
	 
	 /**
	  * 商户编号
	  */
	 private String tenantId;
	
	/**
     * 商户编号
     */
    
    /**
     * 商品名称
     */
    private String prodName;
    
    /**
     * 规格
     */
    private String extendInfo;
    
    /**
     * 数量
     */
    private long buySum;
    
	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public String getExtendInfo() {
		return extendInfo;
	}

	public void setExtendInfo(String extendInfo) {
		this.extendInfo = extendInfo;
	}

	public long getBuySum() {
		return buySum;
	}

	public void setBuySum(long buySum) {
		this.buySum = buySum;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
}
