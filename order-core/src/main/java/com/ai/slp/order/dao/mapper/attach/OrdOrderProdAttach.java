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
	  * 租户id
	  */
	 private String tenantId;
	
	/**
     * 商户编号
     */
	 private String skuId;
    
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
    
    /**
     * 仓库id
     */
    private String routeId;
    
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

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (buySum ^ (buySum >>> 32));
		result = prime * result + ((extendInfo == null) ? 0 : extendInfo.hashCode());
		result = prime * result + (int) (orderId ^ (orderId >>> 32));
		result = prime * result + ((prodName == null) ? 0 : prodName.hashCode());
		result = prime * result + ((routeId == null) ? 0 : routeId.hashCode());
		result = prime * result + ((skuId == null) ? 0 : skuId.hashCode());
		result = prime * result + ((tenantId == null) ? 0 : tenantId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrdOrderProdAttach other = (OrdOrderProdAttach) obj;
		if (buySum != other.buySum)
			return false;
		if (extendInfo == null) {
			if (other.extendInfo != null)
				return false;
		} else if (!extendInfo.equals(other.extendInfo))
			return false;
		if (orderId != other.orderId)
			return false;
		if (prodName == null) {
			if (other.prodName != null)
				return false;
		} else if (!prodName.equals(other.prodName))
			return false;
		if (routeId == null) {
			if (other.routeId != null)
				return false;
		} else if (!routeId.equals(other.routeId))
			return false;
		if (skuId == null) {
			if (other.skuId != null)
				return false;
		} else if (!skuId.equals(other.skuId))
			return false;
		if (tenantId == null) {
			if (other.tenantId != null)
				return false;
		} else if (!tenantId.equals(other.tenantId))
			return false;
		return true;
	}
}
